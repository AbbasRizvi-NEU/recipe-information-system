import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class TestSaveRecipes {
    public static void main(String[] args) {
        System.out.println("Testing SaveRecipes Class\n");

        // Setup sample data for testing
        Recipe pasta = new Recipe("Pasta", "Vegetarian", "30 mins");
        pasta.setCuisineType("Italian");
        Ingredient tomato = new Ingredient("Tomato", 1.0, "kg");
        pasta.addIngredient(tomato);
        CookingInstruction boil = new CookingInstruction(1, "Boil water, add salt");
        pasta.addCookingInstruction(boil);
        HealthInfo pastaHealth = new HealthInfo("Low Calorie");
        pastaHealth.addAllergy("Gluten");
        pasta.setHealthInfo(pastaHealth);
        UserInteraction user1 = new UserInteraction("user1", "Pasta", 4.5, true, "Delicious, highly recommended!");
        pasta.addUserInteraction(user1);

        // Test 1: Test saveRecipesToFile with valid data
        System.out.println("Test 1 - Save Recipes to File:");
        List<Recipe> recipes = Arrays.asList(pasta);
        File recipeFile = createTempFile("recipes.txt");
        try {
            SaveRecipes.saveRecipesToFile(recipes, recipeFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error saving recipes to file: " + e.getMessage());
        }
        String recipeContent = readFile(recipeFile);
        System.out.println("  Expected: Pasta|Vegetarian|Italian|Tomato,1.0,kg|1,Boil water#COMMA# add salt|Low Calorie#Gluten|30 mins");
        System.out.println("  Actual:   " + recipeContent.trim());
        System.out.println();

        // Test 2: Test saveRecipesToFile with null recipe
        System.out.println("Test 2 - Save Recipes with Null Recipe:");
        Recipe validRecipe = new Recipe("Salad", "Vegan", "20 mins");
        List<Recipe> invalidRecipes = Arrays.asList(validRecipe, null);
        File invalidRecipeFile = createTempFile("invalid_recipes.txt");
        try {
            SaveRecipes.saveRecipesToFile(invalidRecipes, invalidRecipeFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error saving recipes to file: " + e.getMessage());
        }
        String invalidContent = readFile(invalidRecipeFile);
        System.out.println("  Expected: Salad|Vegan|||Vegan|20 mins");
        System.out.println("  Actual:   " + invalidContent.trim());
        System.out.println();

        // Test 3: Test saveUserInteractionsToFile with valid data
        System.out.println("Test 3 - Save User Interactions to File:");
        File userFile = createTempFile("userdata.txt");
        SaveRecipes.saveUserInteractionsToFile(recipes, userFile.getAbsolutePath());
        String userContent = readFile(userFile);
        System.out.println("  Expected: user1,Pasta,4.5,true,Delicious#COMMA# highly recommended!");
        System.out.println("  Actual:   " + userContent.trim());
        System.out.println();

        // Test 4: Test saveIngredientsToFile with valid data
        System.out.println("Test 4 - Save Ingredients to File:");
        List<Ingredient> ingredients = Arrays.asList(tomato);
        File ingredientFile = createTempFile("ingredients.txt");
        SaveRecipes.saveIngredientsToFile(ingredients, ingredientFile.getAbsolutePath());
        String ingredientContent = readFile(ingredientFile);
        System.out.println("  Expected: Tomato,1.0,kg");
        System.out.println("  Actual:   " + ingredientContent.trim());
        System.out.println();

        // Test 5: Test saveCookingInstructionsToFile with valid data
        System.out.println("Test 5 - Save Cooking Instructions to File:");
        List<CookingInstruction> instructions = Arrays.asList(boil);
        File instructionFile = createTempFile("instructions.txt");
        SaveRecipes.saveCookingInstructionsToFile(instructions, instructionFile.getAbsolutePath());
        String instructionContent = readFile(instructionFile);
        System.out.println("  Expected: 1,Boil water#COMMA# add salt");
        System.out.println("  Actual:   " + instructionContent.trim());
        System.out.println();

        // Test 6: Test saveHealthInfoToFile with valid data
        System.out.println("Test 6 - Save Health Info to File:");
        List<HealthInfo> healthInfos = Arrays.asList(pastaHealth);
        File healthInfoFile = createTempFile("healthinfo.txt");
        SaveRecipes.saveHealthInfoToFile(healthInfos, healthInfoFile.getAbsolutePath());
        String healthInfoContent = readFile(healthInfoFile);
        System.out.println("  Expected: Low Calorie#Gluten");
        System.out.println("  Actual:   " + healthInfoContent.trim());
        System.out.println();

        // Test 7: Test saveRecipesToFile with empty list
        System.out.println("Test 7 - Save Empty Recipes List:");
        List<Recipe> emptyRecipes = Arrays.asList();
        File emptyRecipeFile = createTempFile("empty_recipes.txt");
        try {
            SaveRecipes.saveRecipesToFile(emptyRecipes, emptyRecipeFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error saving recipes to file: " + e.getMessage());
        }
        String emptyContent = readFile(emptyRecipeFile);
        System.out.println("  Expected: (empty file)");
        System.out.println("  Actual:   " + (emptyContent.trim().isEmpty() ? "(empty)" : emptyContent.trim()));
        System.out.println();

        // Test 8: Test handling of invalid file path
        System.out.println("Test 8 - Save to Invalid File Path:");
        try {
            SaveRecipes.saveRecipesToFile(recipes, "/invalid/path/recipes.txt");
            System.out.println("Test 8 Failed: Should have thrown an exception");
        } catch (Exception e) {
            System.out.println("  Caught expected exception: " + e.getMessage());
        }
        System.out.println();

        // Test 9: Test saveCookingInstructionsToFile with commas in instructions
        System.out.println("Test 9 - Save Cooking Instructions with Commas:");
        CookingInstruction commaInstruction = new CookingInstruction(1, "Boil water, add salt");
        CookingInstruction commaInstruction2 = new CookingInstruction(2, "Add pasta, stir gently");
        List<CookingInstruction> commaInstructions = Arrays.asList(commaInstruction, commaInstruction2);
        File commaInstructionFile = createTempFile("comma_instructions.txt");
        SaveRecipes.saveCookingInstructionsToFile(commaInstructions, commaInstructionFile.getAbsolutePath());
        String commaInstructionContent = readFile(commaInstructionFile);
        System.out.println("  Expected: 1,Boil water#COMMA# add salt\n2,Add pasta#COMMA# stir gently");
        System.out.println("  Actual:   " + commaInstructionContent.trim());
        System.out.println();

        // Clean up temporary files
        deleteTempFile(recipeFile);
        deleteTempFile(userFile);
        deleteTempFile(ingredientFile);
        deleteTempFile(instructionFile);
        deleteTempFile(healthInfoFile);
        deleteTempFile(emptyRecipeFile);
        deleteTempFile(commaInstructionFile);
    }

    // Helper method to create a temporary file
    private static File createTempFile(String filename) {
        try {
            return File.createTempFile(filename, null);
        } catch (IOException e) {
            System.err.println("Failed to create temp file: " + e.getMessage());
            return null;
        }
    }

    // Helper method to read file content
    private static String readFile(File file) {
        String content = "";
        try (BufferedReader reader = new BufferedReader(
                new FileReader(file, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content = content + line + "\n";
            }
        } catch (IOException e) {
            System.err.println("Error reading file " + file.getAbsolutePath() + ": " + e.getMessage());
        }
        return content;
    }

    // Helper method to delete a temporary file
    private static void deleteTempFile(File file) {
        if (file != null && file.exists()) {
            file.delete();
        }
    }
}