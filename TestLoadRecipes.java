import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class TestLoadRecipes {
    public static void main(String[] args) {
        System.out.println("Testing LoadRecipes Class\n");

        // Test 1: Test loadRecipesFromFile with a valid file
        System.out.println("Test 1 - Load Recipes from Valid File:");
        String recipeFileContent = "Pasta|Vegetarian|Italian|Flour,2.5,cups;Tomato,1.0,kg|1,Boil water#COMMA# add salt;2,Add pasta|Low Calorie#Nuts,Gluten|30 mins\n" +
                                  "Salad|Vegan|Greek|Cucumber,1.0,unit;Tomato,2.0,unit|1,Chop vegetables|High Vitamin C|20 mins";
        File recipeFile = createTempFile("recipes.txt", recipeFileContent);
        try {
            List<Recipe> recipes = LoadRecipes.loadRecipesFromFile(recipeFile.getAbsolutePath(), false);
            System.out.println("  Expected: 2 recipes loaded");
            System.out.println("  Actual:   " + recipes.size() + " recipes loaded");
            if (!recipes.isEmpty()) {
                System.out.println("  First Recipe: " + recipes.get(0).getName());
                System.out.println("  Ingredients:  " + recipes.get(0).getIngredientCount());
                System.out.println("  Instructions: " + recipes.get(0).getInstructionCount());
                System.out.println("  Health Info:  " + (recipes.get(0).getHealthInfo() != null ? recipes.get(0).getHealthInfo().getNutritionValue() : "None"));
                // Verify comma in instruction
                CookingInstruction[] instructions = recipes.get(0).getCookingInstructions();
                if (instructions != null && instructions.length > 0) {
                    System.out.println("  First Instruction: " + instructions[0].getStep());
                }
            }
        } catch (Exception e) {
            System.out.println("Test 1 Failed: " + e.getMessage());
        }
        System.out.println();

        // Test 2: Test loadRecipesFromFile with invalid file format
        System.out.println("Test 2 - Load Recipes from Invalid File Format:");
        String invalidRecipeContent = "Pasta|Vegetarian|Italian|Flour,invalid,cups|1,Boil water|Low Calorie";
        File invalidRecipeFile = createTempFile("invalid_recipes.txt", invalidRecipeContent);
        try {
            List<Recipe> recipes = LoadRecipes.loadRecipesFromFile(invalidRecipeFile.getAbsolutePath(), false);
            System.out.println("  Expected: 0 recipes loaded (due to invalid format)");
            System.out.println("  Actual:   " + recipes.size() + " recipes loaded");
        } catch (Exception e) {
            System.out.println("Test 2 Failed: " + e.getMessage());
        }
        System.out.println();

        // Test 3: Test loadUserInteractionsFromFile with valid data
        System.out.println("Test 3 - Load User Interactions from Valid File:");
        String userInteractionContent = "user1,Pasta,4.5,true,Delicious#COMMA# highly recommended!\n" +
                                       "user2,Pasta,3.0,false,Too salty";
        File userFile = createTempFile("userdata.txt", userInteractionContent);
        List<Recipe> recipesForInteractions = List.of(new Recipe("Pasta", "Vegetarian", "30 mins"));
        try {
            LoadRecipes.loadUserInteractionsFromFile(recipesForInteractions, userFile.getAbsolutePath(), false);
            System.out.println("  Expected: 2 interactions loaded");
            System.out.println("  Actual Interactions: " + recipesForInteractions.get(0).getUserInteractions().size());
            if (!recipesForInteractions.get(0).getUserInteractions().isEmpty()) {
                System.out.println("  First Interaction: " + recipesForInteractions.get(0).getUserInteractions().get(0).toString());
            }
        } catch (Exception e) {
            System.out.println("Test 3 Failed: " + e.getMessage());
        }
        System.out.println();

        // Test 4: Test loadIngredientsFromFile with valid data
        System.out.println("Test 4 - Load Ingredients from Valid File:");
        String ingredientContent = "Flour,2.5,cups\n" +
                                  "Sugar,1.0,tbsp";
        File ingredientFile = createTempFile("ingredients.txt", ingredientContent);
        try {
            List<Ingredient> ingredients = LoadRecipes.loadIngredientsFromFile(ingredientFile.getAbsolutePath());
            System.out.println("  Expected: 2 ingredients loaded");
            System.out.println("  Actual:   " + ingredients.size() + " ingredients loaded");
            if (!ingredients.isEmpty()) {
                System.out.println("  First Ingredient: " + ingredients.get(0).toString());
            }
        } catch (Exception e) {
            System.out.println("Test 4 Failed: " + e.getMessage());
        }
        System.out.println();

        // Test 5: Test loadCookingInstructionsFromFile with valid data
        System.out.println("Test 5 - Load Cooking Instructions from Valid File:");
        String instructionContent = "1,Boil water\n" +
                                   "2,Add pasta";
        File instructionFile = createTempFile("instructions.txt", instructionContent);
        try {
            List<CookingInstruction> instructions = LoadRecipes.loadCookingInstructionsFromFile(instructionFile.getAbsolutePath());
            System.out.println("  Expected: 2 instructions loaded");
            System.out.println("  Actual:   " + instructions.size() + " instructions loaded");
            if (!instructions.isEmpty()) {
                System.out.println("  First Instruction: " + instructions.get(0).toString());
            }
        } catch (Exception e) {
            System.out.println("Test 5 Failed: " + e.getMessage());
        }
        System.out.println();

        // Test 6: Test loadHealthInfoFromFile with valid data
        System.out.println("Test 6 - Load Health Info from Valid File:");
        String healthInfoContent = "Low Calorie#Nuts,Gluten\n" +
                                  "High Protein";
        File healthInfoFile = createTempFile("healthinfo.txt", healthInfoContent);
        try {
            List<HealthInfo> healthInfos = LoadRecipes.loadHealthInfoFromFile(healthInfoFile.getAbsolutePath());
            System.out.println("  Expected: 2 health info records loaded");
            System.out.println("  Actual:   " + healthInfos.size() + " health info records loaded");
            if (!healthInfos.isEmpty()) {
                System.out.println("  First Health Info: " + healthInfos.get(0).toString());
            }
        } catch (Exception e) {
            System.out.println("Test 6 Failed: " + e.getMessage());
        }
        System.out.println();

        // Test 7: Test loadRecipesFromFile with non-existent file
        System.out.println("Test 7 - Load Recipes from Non-Existent File:");
        try {
            List<Recipe> recipes = LoadRecipes.loadRecipesFromFile("nonexistent.txt", false);
            System.out.println("  Expected: 0 recipes loaded (file not found)");
            System.out.println("  Actual:   " + recipes.size() + " recipes loaded");
        } catch (Exception e) {
            System.out.println("  Caught expected exception: " + e.getMessage());
        }
        System.out.println();

        // Test 8: Test loadCookingInstructionsFromFile with commas in instructions
        System.out.println("Test 8 - Load Cooking Instructions with Commas:");
        String commaInstructionContent = "1,Boil water#COMMA# add salt\n" +
                                        "2,Add pasta#COMMA# stir gently";
        File commaInstructionFile = createTempFile("comma_instructions.txt", commaInstructionContent);
        try {
            List<CookingInstruction> instructions = LoadRecipes.loadCookingInstructionsFromFile(commaInstructionFile.getAbsolutePath());
            System.out.println("  Expected: 2 instructions loaded");
            System.out.println("  Actual:   " + instructions.size() + " instructions loaded");
            if (!instructions.isEmpty()) {
                System.out.println("  First Instruction: " + instructions.get(0).getStep());
                System.out.println("  Second Instruction: " + instructions.get(1).getStep());
            }
        } catch (Exception e) {
            System.out.println("Test 8 Failed: " + e.getMessage());
        }
        System.out.println();

        // Clean up temporary files
        deleteTempFile(recipeFile);
        deleteTempFile(invalidRecipeFile);
        deleteTempFile(userFile);
        deleteTempFile(ingredientFile);
        deleteTempFile(instructionFile);
        deleteTempFile(healthInfoFile);
        deleteTempFile(commaInstructionFile);
    }

    // Helper method to create a temporary file with given content
    private static File createTempFile(String filename, String content) {
        try {
            File file = File.createTempFile(filename, null);
            try (BufferedWriter writer = new BufferedWriter(
                    new FileWriter(file, StandardCharsets.UTF_8))) {
                writer.write(content);
            }
            return file;
        } catch (IOException e) {
            System.err.println("Failed to create temp file: " + e.getMessage());
            return null;
        }
    }

    // Helper method to delete a temporary file
    private static void deleteTempFile(File file) {
        if (file != null && file.exists()) {
            file.delete();
        }
    }
}