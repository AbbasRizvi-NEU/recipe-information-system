import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TestRecipeDatabase {
    public static void main(String[] args) {
        System.out.println("Testing RecipeDatabase Class\n");

        // Test 1: Test constructor and toString with empty database
        System.out.println("Test 1 - Constructor and toString (Empty Database):");
        RecipeDatabase db1 = new RecipeDatabase();
        System.out.println("  Expected: Recipe Database Information:\nTotal Recipes: 0\nNo recipes available in the database.");
        System.out.println("  Actual:   \n" + db1.toString().trim());
        System.out.println("  Is Empty: " + db1.isDatabaseEmpty() + "\n");

        // Test 2: Test addRecipe and getRecipes
        System.out.println("Test 2 - Add Recipe and Get Recipes:");
        RecipeDatabase db2 = createSampleDatabase();
        System.out.println("  Expected: 2 recipes");
        System.out.println("  Actual:   " + db2.getRecipes().size() + " recipes");
        System.out.println("  First Recipe: " + db2.getRecipes().get(0).getName());
        System.out.println("  toString:\n" + db2.toString().trim() + "\n");

        // Test 3: Test searchRecipe
        System.out.println("Test 3 - Search Recipe:");
        RecipeDatabase db3 = createSampleDatabase();
        System.out.println("  Searching for 'Pasta':");
        Recipe found = db3.searchRecipe("Pasta");
        System.out.println("  Expected: Pasta found");
        System.out.println("  Actual:   " + (found != null ? found.getName() : "Not found"));
        System.out.println("  Searching for 'Soup':");
        found = db3.searchRecipe("Soup");
        System.out.println("  Expected: Not found");
        System.out.println("  Actual:   " + (found == null ? "Not found" : found.getName()) + "\n");

        // Test 4: Test updateRecipe
        System.out.println("Test 4 - Update Recipe:");
        RecipeDatabase db4 = createSampleDatabase();
        System.out.println("  Updating 'Pasta' to 'Spaghetti':");
        boolean updated = db4.updateRecipe("Pasta", "Spaghetti");
        System.out.println("  Expected: true");
        System.out.println("  Actual:   " + updated);
        System.out.println("  New Name: " + (db4.searchRecipe("Spaghetti") != null ? db4.searchRecipe("Spaghetti").getName() : "Not found"));
        System.out.println("  Updating non-existent 'Soup':");
        updated = db4.updateRecipe("Soup", "Chowder");
        System.out.println("  Expected: false");
        System.out.println("  Actual:   " + updated + "\n");

        // Test 5: Test removeRecipe
        System.out.println("Test 5 - Remove Recipe:");
        RecipeDatabase db5 = new RecipeDatabase();
        Recipe pasta = createSampleRecipe();
        db5.addRecipe(pasta);
        System.out.println("  Removing 'Pasta':");
        boolean removed = db5.removeRecipe("Pasta");
        System.out.println("  Expected: true");
        System.out.println("  Actual:   " + removed);
        System.out.println("  Database Empty: " + db5.isDatabaseEmpty());
        System.out.println("  Removing non-existent 'Soup':");
        removed = db5.removeRecipe("Soup");
        System.out.println("  Expected: false");
        System.out.println("  Actual:   " + removed + "\n");

        // Test 6: Test listRecipes
        System.out.println("Test 6 - List Recipes:");
        RecipeDatabase db6 = createSampleDatabase();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        db6.listRecipes();
        System.setOut(originalOut);
        String listOutput = outContent.toString().trim();
        System.out.println("  Expected: Contains 'Pasta' and 'Salad'");
        System.out.println("  Actual:   \n" + listOutput);
        System.out.println();

        // Test 7: Test addUserInteraction
        System.out.println("Test 7 - Add User Interaction:");
        RecipeDatabase db7 = new RecipeDatabase();
        Recipe pastaForInteraction = createSampleRecipe();
        db7.addRecipe(pastaForInteraction);
        db7.addUserInteraction("user2", "Pasta", 4.0, false, "Good");
        Recipe pastaCheck = db7.searchRecipe("Pasta");
        if (pastaCheck != null) {
            System.out.println("  Expected: 2 interactions");
            System.out.println("  Actual:   " + pastaCheck.getUserInteractions().size());
            System.out.println("  New Interaction: " + pastaCheck.getUserInteractions().get(1).toString());
        } else {
            System.out.println("  Failed: Recipe 'Pasta' not found");
        }
        System.out.println("  Adding interaction to non-existent recipe:");
        db7.addUserInteraction("user2", "Soup", 3.0, true, "Okay");
        System.out.println();

        // Test 8: Test displayUserInteractions
        System.out.println("Test 8 - Display User Interactions:");
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        db7.displayUserInteractions("Pasta");
        System.setOut(originalOut);
        String interactionsOutput = outContent.toString().trim();
        System.out.println("  Expected: Contains 'user1' and 'user2' interactions");
        System.out.println("  Actual:   \n" + interactionsOutput);
        System.out.println();

        // Test 9: Test updateUserInteraction
        System.out.println("Test 9 - Update User Interaction:");
        boolean interactionUpdated = db7.updateUserInteraction("user1", "Pasta", 5.0, false, "Amazing!");
        System.out.println("  Expected: true");
        System.out.println("  Actual:   " + interactionUpdated);
        if (pastaCheck != null) {
            System.out.println("  Updated Rating: " + pastaCheck.getUserInteractions().get(0).getRating());
            System.out.println("  Updated Favorite: " + pastaCheck.getUserInteractions().get(0).isFavorite());
            System.out.println("  Updated Comment: " + pastaCheck.getUserInteractions().get(0).getComment());
        }
        System.out.println("  Updating non-existent interaction:");
        interactionUpdated = db7.updateUserInteraction("user3", "Pasta", 3.0, true, "Okay");
        System.out.println("  Expected: false");
        System.out.println("  Actual:   " + interactionUpdated + "\n");

        // Test 10: Test displayFavoriteRecipes
        System.out.println("Test 10 - Display Favorite Recipes:");
        RecipeDatabase db8 = createSampleDatabase();
        String simulatedInput = "1\n"; // Selects first favorite recipe (Pasta)
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        Scanner scanner = new Scanner(System.in);
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        db8.displayFavoriteRecipes("user1", scanner);
        System.setOut(originalOut);
        String favoriteOutput = outContent.toString().trim();
        System.out.println("  Expected: Contains 'Pasta' details");
        System.out.println("  Actual:   \n" + favoriteOutput);
        System.setIn(System.in);
        System.out.println();

        // Test 11: Test setRecipes and defensive copying
        System.out.println("Test 11 - Set Recipes and Defensive Copying:");
        RecipeDatabase db9 = new RecipeDatabase();
        List<Recipe> newRecipes = new ArrayList<>(createSampleDatabase().getRecipes());
        db9.setRecipes(newRecipes);
        newRecipes.add(new Recipe("Soup", "Non-Veg", "45 mins"));
        System.out.println("  Expected: 2 recipes (original unchanged)");
        System.out.println("  Actual:   " + db9.getRecipes().size());
        System.out.println("  toString:\n" + db9.toString().trim() + "\n");

        // Test 12: Test isDatabaseEmpty with non-empty database
        System.out.println("Test 12 - Is Database Empty (Non-Empty):");
        System.out.println("  Expected: false");
        System.out.println("  Actual:   " + db9.isDatabaseEmpty() + "\n");
    }

    // Helper method to create a sample RecipeDatabase with two recipes
    private static RecipeDatabase createSampleDatabase() {
        RecipeDatabase db = new RecipeDatabase();
        Recipe pasta = createSampleRecipe();
        Recipe salad = new Recipe("Salad", "Vegan", "20 mins");
        salad.setCuisineType("Greek");
        salad.addIngredient(new Ingredient("Cucumber", 1.0, "unit"));
        UserInteraction user2 = new UserInteraction("user1", "Salad", 3.5, false, "Fresh");
        salad.addUserInteraction(user2);
        db.addRecipe(pasta);
        db.addRecipe(salad);
        return db;
    }

    // Helper method to create a sample Pasta recipe
    private static Recipe createSampleRecipe() {
        Recipe pasta = new Recipe("Pasta", "Vegetarian", "30 mins");
        pasta.setCuisineType("Italian");
        pasta.addIngredient(new Ingredient("Tomato", 1.0, "kg"));
        HealthInfo pastaHealth = new HealthInfo("Low Calorie");
        pastaHealth.addAllergy("Gluten");
        pasta.setHealthInfo(pastaHealth);
        UserInteraction user1 = new UserInteraction("user1", "Pasta", 4.5, true, "Delicious!");
        pasta.addUserInteraction(user1);
        return pasta;
    }
}