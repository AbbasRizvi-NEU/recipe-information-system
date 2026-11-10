import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class LoadRecipes {
    
    public static List<Recipe> loadRecipesFromFile(String filename, boolean verbose) {
        System.out.println("\nloadRecipesFromFile: ");
        List<Recipe> tempRecipes = new ArrayList<>();
        boolean valid = true;
        
        try (BufferedReader reader = new BufferedReader(
                new FileReader(filename, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] parts = line.split("\\|", -1);
                if (parts.length < 6) {
                    System.err.println("Invalid recipe format: " + line);
                    valid = false;
                    break;
                }

                String name = parts[0].trim();
                String category = parts[1].trim();
                String cuisine = parts[2].trim();
                String ingredientsStr = parts[3].trim();
                String instructionsStr = parts[4].trim();
                String healthInfoStr = parts[5].trim();
                String duration = parts.length > 6 ? parts[6].trim() : null;

                if (name.isEmpty()) {
                    System.err.println("Skipping recipe with empty name.");
                    continue;
                }

                Recipe recipe = new Recipe(name, category.isEmpty() ? null : category, (duration != null && !duration.trim().isEmpty()) ? duration.trim() : null);
                if (!cuisine.isEmpty()) {
                    recipe.setCuisineType(cuisine);
                }

                // Load ingredients if available
                if (!ingredientsStr.isEmpty()) {
                    String[] ingredientStrings = ingredientsStr.split(";", -1);
                    for (int i = 0; i < ingredientStrings.length; i++) {
                        String ingredientString = ingredientStrings[i];
                        String[] ingredientParts = ingredientString.split(",", -1);
                        if (ingredientParts.length >= 3) {
                            try {
                                String ingredientName = ingredientParts[0].trim();
                                double quantity = Double.parseDouble(ingredientParts[1].trim());
                                String unit = ingredientParts[2].trim();
                                if (!ingredientName.isEmpty() && quantity > 0) {
                                    Ingredient ingredient = new Ingredient(ingredientName, quantity, unit);
                                    recipe.addIngredient(ingredient);
                                }
                            } catch (NumberFormatException e) {
                                System.err.println("Invalid ingredient quantity for " + name + ": " + ingredientString);
                                valid = false;
                                break;
                            }
                        } else {
                            System.err.println("Invalid ingredient format for " + name + ": " + ingredientString);
                            valid = false;
                            break;
                        }
                    }
                    if (!valid) break;
                }

                // Load cooking instructions if available
                if (!instructionsStr.isEmpty()) {
                    String[] instructionStrings = instructionsStr.split(";", -1);
                    for (int i = 0; i < instructionStrings.length; i++) {
                        String instructionString = instructionStrings[i];
                        String[] instructionParts = instructionString.split(",", 2);
                        if (instructionParts.length >= 2) {
                            try {
                                int sequence = Integer.parseInt(instructionParts[0].trim());
                                String step = instructionParts[1].trim().replace("#COMMA#", ",");
                                if (!step.isEmpty() && sequence > 0) {
                                    CookingInstruction instruction = new CookingInstruction(sequence, step);
                                    recipe.addCookingInstruction(instruction);
                                }
                            } catch (NumberFormatException e) {
                                System.err.println("Invalid instruction sequence for " + name + ": " + instructionString);
                                valid = false;
                                break;
                            }
                        } else {
                            System.err.println("Invalid instruction format for " + name + ": " + instructionString);
                            valid = false;
                            break;
                        }
                    }
                    if (!valid) break;
                }

                // Load health info if available
                if (!healthInfoStr.isEmpty()) {
                    String[] healthInfoParts = healthInfoStr.split("#", -1);
                    String nutritionValue = healthInfoParts[0].trim();
                    HealthInfo healthInfo = new HealthInfo(nutritionValue.isEmpty() ? null : nutritionValue);
                    
                    // Load allergies if available
                    if (healthInfoParts.length >= 2 && !healthInfoParts[1].trim().isEmpty()) {
                        String[] allergies = healthInfoParts[1].split(",", -1);
                        for (int i = 0; i < allergies.length; i++) {
                            String trimmedAllergy = allergies[i].trim();
                            if (!trimmedAllergy.isEmpty()) {
                                healthInfo.addAllergy(trimmedAllergy);
                            }
                        }
                    }
                    
                    recipe.setHealthInfo(healthInfo);
                }

                tempRecipes.add(recipe);
                
                if (verbose) {
                    System.out.println("Recipe: " + name);
                    System.out.println("Category: " + (recipe.getDietType() != null ? recipe.getDietType() : "N/A"));
                    System.out.println("Cuisine: " + (recipe.getCuisineType() != null ? recipe.getCuisineType() : "N/A"));
                    System.out.println("Duration: " + (recipe.getDuration() != null ? recipe.getDuration() : "N/A"));

                    // Ingredients
                    String ingredientsOutput = "";
                    Ingredient[] ingredients = recipe.getIngredients();
                    if (ingredients != null && ingredients.length > 0) {
                        for (int i = 0; i < ingredients.length; i++) {
                            if (ingredients[i] != null && ingredients[i].getName() != null) {
                                if (!ingredientsOutput.isEmpty()) {
                                    ingredientsOutput = ingredientsOutput + ", ";
                                }
                                ingredientsOutput = ingredientsOutput + ingredients[i].getName() + 
                                                    " (" + ingredients[i].getQuantity() + " " + 
                                                    (ingredients[i].getUnit() != null ? ingredients[i].getUnit() : "") + ")";
                            }
                        }
                    } else {
                        ingredientsOutput = "None";
                    }
                    System.out.println("Ingredients: " + ingredientsOutput);

                    // Cooking Instructions
                    System.out.println("Cooking Instructions:");
                    CookingInstruction[] instructions = recipe.getCookingInstructions();
                    if (instructions != null && instructions.length > 0) {
                        for (int i = 0; i < instructions.length; i++) {
                            if (instructions[i] != null && instructions[i].getStep() != null) {
                                System.out.println("Step " + instructions[i].getSequence() + ": " + instructions[i].getStep());
                            }
                        }
                    } else {
                        System.out.println("None");
                    }

                    // Nutritional Info and Allergies
                    String nutritionOutput = recipe.getHealthInfo() != null && 
                                            recipe.getHealthInfo().getNutritionValue() != null ? 
                                            recipe.getHealthInfo().getNutritionValue() : "None";
                    System.out.println("Nutritional Info: " + nutritionOutput);

                    String allergiesOutput = "None";
                    if (recipe.getHealthInfo() != null && recipe.getHealthInfo().getAllergies() != null && 
                        !recipe.getHealthInfo().getAllergies().isEmpty()) {
                        allergiesOutput = "";
                        for (int i = 0; i < recipe.getHealthInfo().getAllergies().size(); i++) {
                            allergiesOutput = allergiesOutput + recipe.getHealthInfo().getAllergies().get(i);
                            if (i < recipe.getHealthInfo().getAllergies().size() - 1) {
                                allergiesOutput = allergiesOutput + ", ";
                            }
                        }
                    }
                    System.out.println("Potential Allergies: " + allergiesOutput);
                    System.out.println("______________________________________________________________________________________");
                }
            }
            if (valid) {
                System.out.println("\nLoaded " + tempRecipes.size() + " recipes from " + filename);
                return tempRecipes;
            } else {
                System.out.println("\nLoaded 0 recipes from " + filename + " due to invalid format");
                return new ArrayList<>();
            }
        } catch (IOException e) {
            System.err.println("Error reading file " + filename + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    public static void loadUserInteractionsFromFile(List<Recipe> recipes, String filename, boolean verbose) {
        System.out.println("\nloadUserInteractionsFromFile: ");
        try (BufferedReader reader = new BufferedReader(
                new FileReader(filename, StandardCharsets.UTF_8))) {
            String line;
            int count = 0;
            
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] parts = line.split(",", -1);
                if (parts.length < 5) {
                    System.err.println("Invalid user interaction format: " + line);
                    continue;
                }

                try {
                    String username = parts[0].trim();
                    String recipeName = parts[1].trim();
                    double rating = Double.parseDouble(parts[2].trim());
                    boolean favorite = Boolean.parseBoolean(parts[3].trim());
                    String comment = parts[4].trim().replace("#COMMA#", ",");

                    if (username.isEmpty() || recipeName.isEmpty()) {
                        System.err.println("Skipping invalid user interaction for recipe: " + recipeName);
                        continue;
                    }

                    UserInteraction interaction = new UserInteraction(
                        username, recipeName, rating, favorite, comment);
                    
                    for (int i = 0; i < recipes.size(); i++) {
                        Recipe recipe = recipes.get(i);
                        if (recipe.getName().equalsIgnoreCase(recipeName)) {
                            recipe.addUserInteraction(interaction);
                            count++;
                            break;
                        }
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Invalid rating in user interaction: " + line);
                }
            }
            System.out.println("Loaded " + count + " user interactions from " + filename);
        } catch (IOException e) {
            System.err.println("Error reading file " + filename + ": " + e.getMessage());
        }
    }
    
    public static List<Ingredient> loadIngredientsFromFile(String filename) {
        System.out.println("\nloadIngredientsFromFile: ");
        List<Ingredient> ingredients = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(
                new FileReader(filename, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] parts = line.split(",", -1);
                if (parts.length >= 3) {
                    try {
                        String name = parts[0].trim();
                        double quantity = Double.parseDouble(parts[1].trim());
                        String unit = parts[2].trim();
                        
                        if (!name.isEmpty() && quantity > 0) {
                            Ingredient ingredient = new Ingredient(name, quantity, unit);
                            ingredients.add(ingredient);
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid ingredient quantity: " + line);
                    }
                }
            }
            System.out.println("Loaded " + ingredients.size() + " ingredients from " + filename);
        } catch (IOException e) {
            System.err.println("Error reading file " + filename + ": " + e.getMessage());
        }
        
        return ingredients;
    }
    
    public static List<CookingInstruction> loadCookingInstructionsFromFile(String filename) {
        System.out.println("\nloadCookingInstructionsFromFile: ");
        List<CookingInstruction> instructions = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(
                new FileReader(filename, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] parts = line.split(",", 2);
                if (parts.length >= 2) {
                    try {
                        int sequence = Integer.parseInt(parts[0].trim());
                        String step = parts[1].trim().replace("#COMMA#", ",");
                        
                        if (!step.isEmpty() && sequence > 0) {
                            CookingInstruction instruction = new CookingInstruction(sequence, step);
                            instructions.add(instruction);
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid instruction sequence: " + line);
                    }
                }
            }
            System.out.println("Loaded " + instructions.size() + " cooking instructions from " + filename);
        } catch (IOException e) {
            System.err.println("Error reading file " + filename + ": " + e.getMessage());
        }
        
        return instructions;
    }
    
    public static List<HealthInfo> loadHealthInfoFromFile(String filename) {
        System.out.println("\nloadHealthInfoFromFile: ");
        List<HealthInfo> healthInfos = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(
                new FileReader(filename, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] parts = line.split("#", -1);
                if (parts.length >= 1) {
                    String nutritionValue = parts[0].trim();
                    HealthInfo healthInfo = new HealthInfo(nutritionValue.isEmpty() ? null : nutritionValue);
                    
                    if (parts.length >= 2 && !parts[1].trim().isEmpty()) {
                        String[] allergies = parts[1].split(",", -1);
                        for (int i = 0; i < allergies.length; i++) {
                            String trimmedAllergy = allergies[i].trim();
                            if (!trimmedAllergy.isEmpty()) {
                                healthInfo.addAllergy(trimmedAllergy);
                            }
                        }
                    }
                    
                    healthInfos.add(healthInfo);
                }
            }
            System.out.println("Loaded " + healthInfos.size() + " health information records from " + filename);
        } catch (IOException e) {
            System.err.println("Error reading file " + filename + ": " + e.getMessage());
        }
        
        return healthInfos;
    }
}