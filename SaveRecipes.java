import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class SaveRecipes {
    
    public static void saveRecipesToFile(List<Recipe> recipes, String filename) throws IOException {
        int validRecipeCount = 0;
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(filename, StandardCharsets.UTF_8))) {
            for (int i = 0; i < recipes.size(); i++) {
                Recipe recipe = recipes.get(i);
                if (recipe == null || recipe.getName() == null || recipe.getName().trim().isEmpty()) {
                    System.err.println("Skipping invalid recipe: null or empty name");
                    continue;
                }

                String saveString = recipe.getName().trim() + "|";
                saveString = saveString + (recipe.getDietType() != null ? recipe.getDietType().trim() : "") + "|";
                saveString = saveString + (recipe.getCuisineType() != null ? recipe.getCuisineType().trim() : "") + "|";

                // Ingredients
                Ingredient[] ingredients = recipe.getIngredients();
                String ingredientString = "";
                int validIngredients = 0;
                if (ingredients != null) {
                    for (int j = 0; j < ingredients.length; j++) {
                        Ingredient ingredient = ingredients[j];
                        if (ingredient != null && ingredient.getName() != null && 
                            !ingredient.getName().trim().isEmpty() && ingredient.getQuantity() > 0) {
                            if (validIngredients > 0) {
                                ingredientString = ingredientString + ";";
                            }
                            ingredientString = ingredientString + ingredient.getName().trim() + "," +
                                               ingredient.getQuantity() + "," +
                                               (ingredient.getUnit() != null ? ingredient.getUnit().trim() : "");
                            validIngredients++;
                        }
                    }
                }

                // Cooking Instructions
                CookingInstruction[] instructions = recipe.getCookingInstructions();
                String instructionString = "";
                int validInstructions = 0;
                if (instructions != null) {
                    for (int j = 0; j < instructions.length; j++) {
                        CookingInstruction instruction = instructions[j];
                        if (instruction != null && instruction.getStep() != null && 
                            !instruction.getStep().trim().isEmpty() && instruction.getSequence() > 0) {
                            String safeStep = instruction.getStep().trim().replace(",", "#COMMA#").replace("|", "||");
                            if (validInstructions > 0) {
                                instructionString = instructionString + ";";
                            }
                            instructionString = instructionString + instruction.getSequence() + "," + safeStep;
                            validInstructions++;
                        }
                    }
                }

                // Combine ingredients and instructions
                if (!ingredientString.isEmpty()) {
                    saveString = saveString + ingredientString + "|";
                    saveString = saveString + instructionString + "|";
                } else if (!instructionString.isEmpty()) {
                    saveString = saveString + "|" + instructionString + "|";
                } else {
                    saveString = saveString + "||";
                }

                // Health Info
                HealthInfo healthInfo = recipe.getHealthInfo();
                String healthInfoString = "";
                if (healthInfo != null && healthInfo.getNutritionValue() != null && !healthInfo.getNutritionValue().trim().isEmpty()) {
                    healthInfoString = healthInfo.getNutritionValue().trim();
                    List<String> allergies = healthInfo.getAllergies();
                    if (allergies != null && !allergies.isEmpty()) {
                        boolean hasValidAllergy = false;
                        String allergyString = "";
                        for (int j = 0; j < allergies.size(); j++) {
                            String allergy = allergies.get(j);
                            if (allergy != null && !allergy.trim().isEmpty()) {
                                if (hasValidAllergy) {
                                    allergyString = allergyString + ",";
                                }
                                allergyString = allergyString + allergy.trim();
                                hasValidAllergy = true;
                            }
                        }
                        if (hasValidAllergy) {
                            healthInfoString = healthInfoString + "#" + allergyString;
                        }
                    }
                } else {
                    healthInfoString = recipe.getDietType() != null ? recipe.getDietType().trim() : "";
                }
                saveString = saveString + healthInfoString + "|";

                // Duration
                saveString = saveString + (recipe.getDuration() != null ? recipe.getDuration().trim() : "");

                writer.write(saveString);
                writer.newLine();

                String allergiesOutput = "None";
                if (healthInfo != null && healthInfo.getAllergies() != null && 
                    !healthInfo.getAllergies().isEmpty()) {
                    allergiesOutput = "";
                    for (int j = 0; j < healthInfo.getAllergies().size(); j++) {
                        allergiesOutput = allergiesOutput + healthInfo.getAllergies().get(j);
                        if (j < healthInfo.getAllergies().size() - 1) {
                            allergiesOutput = allergiesOutput + ", ";
                        }
                    }
                }
                String nutritionOutput = healthInfo != null && healthInfo.getNutritionValue() != null ? 
                                        healthInfo.getNutritionValue() : "None";
                String durationOutput = recipe.getDuration() != null ? recipe.getDuration() : "N/A";
                System.out.println("Saved recipe: " + recipe.getName() + 
                                  ", Cuisine: " + (recipe.getCuisineType() != null ? recipe.getCuisineType() : "N/A") + 
                                  ", Category: " + (recipe.getDietType() != null ? recipe.getDietType() : "N/A") + 
                                  ", Duration: " + durationOutput +
                                  ", Nutrition: " + nutritionOutput + 
                                  ", Allergies: " + allergiesOutput);

                validRecipeCount++;
            }
            System.out.println("Recipes saved successfully to " + filename + ": " + validRecipeCount + " recipes");
        }
    }
    
    public static void saveUserInteractionsToFile(List<Recipe> recipes, String filename) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(filename, StandardCharsets.UTF_8))) {
            int count = 0;
            for (int i = 0; i < recipes.size(); i++) {
                Recipe recipe = recipes.get(i);
                if (recipe == null) {
                    continue;
                }
                List<UserInteraction> interactions = recipe.getUserInteractions();
                if (interactions != null) {
                    for (int j = 0; j < interactions.size(); j++) {
                        UserInteraction interaction = interactions.get(j);
                        if (interaction != null && interaction.getUsername() != null && 
                            interaction.getRecipeName() != null) {
                            String saveString = interaction.getUsername().trim() + "," +
                                               interaction.getRecipeName().trim() + "," +
                                               interaction.getRating() + "," +
                                               interaction.isFavorite() + "," +
                                               (interaction.getComment() != null ? 
                                                interaction.getComment().trim().replace(",", "#COMMA#") : "");
                            
                            writer.write(saveString);
                            writer.newLine();
                            count++;
                        }
                    }
                }
            }
            System.out.println("User interactions saved successfully to " + filename + ": " + count + " interactions");
        } catch (IOException e) {
            System.err.println("Error writing to file " + filename + ": " + e.getMessage());
        }
    }
    
    public static void saveIngredientsToFile(List<Ingredient> ingredients, String filename) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(filename, StandardCharsets.UTF_8))) {
            int count = 0;
            for (int i = 0; i < ingredients.size(); i++) {
                Ingredient ingredient = ingredients.get(i);
                if (ingredient != null && ingredient.getName() != null && 
                    !ingredient.getName().trim().isEmpty() && ingredient.getQuantity() > 0) {
                    String saveString = ingredient.getName().trim() + "," +
                                       ingredient.getQuantity() + "," +
                                       (ingredient.getUnit() != null ? ingredient.getUnit().trim() : "");
                    
                    writer.write(saveString);
                    writer.newLine();
                    count++;
                }
            }
            System.out.println("Ingredients saved successfully to " + filename + ": " + count + " ingredients");
        } catch (IOException e) {
            System.err.println("Error writing to file " + filename + ": " + e.getMessage());
        }
    }
    
    public static void saveCookingInstructionsToFile(List<CookingInstruction> instructions, String filename) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(filename, StandardCharsets.UTF_8))) {
            int count = 0;
            for (int i = 0; i < instructions.size(); i++) {
                CookingInstruction instruction = instructions.get(i);
                if (instruction != null && instruction.getStep() != null && 
                    !instruction.getStep().trim().isEmpty() && instruction.getSequence() > 0) {
                    String saveString = instruction.getSequence() + "," +
                                       instruction.getStep().trim().replace(",", "#COMMA#").replace("|", "||");
                    
                    writer.write(saveString);
                    writer.newLine();
                    count++;
                }
            }
            System.out.println("Cooking instructions saved successfully to " + filename + ": " + count + " instructions");
        } catch (IOException e) {
            System.err.println("Error writing to file " + filename + ": " + e.getMessage());
        }
    }
    
    public static void saveHealthInfoToFile(List<HealthInfo> healthInfos, String filename) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(filename, StandardCharsets.UTF_8))) {
            int count = 0;
            for (int i = 0; i < healthInfos.size(); i++) {
                HealthInfo healthInfo = healthInfos.get(i);
                if (healthInfo != null) {
                    String saveString = (healthInfo.getNutritionValue() != null ? 
                                        healthInfo.getNutritionValue().trim() : "");
                    List<String> allergies = healthInfo.getAllergies();
                    if (allergies != null && !allergies.isEmpty()) {
                        boolean hasValidAllergy = false;
                        String allergyString = "";
                        for (int j = 0; j < allergies.size(); j++) {
                            String allergy = allergies.get(j);
                            if (allergy != null && !allergy.trim().isEmpty()) {
                                if (hasValidAllergy) {
                                    allergyString = allergyString + ",";
                                }
                                allergyString = allergyString + allergy.trim();
                                hasValidAllergy = true;
                            }
                        }
                        if (hasValidAllergy) {
                            saveString = saveString + "#" + allergyString;
                        }
                    }
                    
                    if (saveString.length() > 0) {
                        writer.write(saveString);
                        writer.newLine();
                        count++;
                    }
                }
            }
            System.out.println("Health information saved successfully to " + filename + ": " + count + " records");
        } catch (IOException e) {
            System.err.println("Error writing to file " + filename + ": " + e.getMessage());
        }
    }
}