import java.util.List;

public class TestRecipe {
    public static void main(String[] args) {
        System.out.println("Testing Recipe Class\n");

        // Test 1: Create a valid Recipe
        try {
            Recipe recipe1 = new Recipe("Pasta", "Vegetarian", "30 mins");
            System.out.println("Test 1 - Valid Creation:");
            System.out.println("  Expected Name: Pasta");
            System.out.println("  Actual Name:   " + recipe1.getName());
            System.out.println("  Diet Type:     " + recipe1.getDietType());
            System.out.println("  Duration:      " + recipe1.getDuration());
            System.out.println("  Ingredients:   " + recipe1.getIngredientCount());
            System.out.println("  Instructions:  " + recipe1.getInstructionCount());
            System.out.println("  toString:\n" + recipe1.toString() + "\n");
        } catch (IllegalArgumentException e) {
            System.out.println("Test 1 Failed: " + e.getMessage() + "\n");
        }

        // Test 2: Add ingredients, instructions, health info, and user interactions
        try {
            Recipe recipe2 = new Recipe("Salad", "Vegan", "20 mins");
            Ingredient ingredient = new Ingredient("Cucumber", 1.0, "unit");
            CookingInstruction instruction = new CookingInstruction(1, "Chop vegetables");
            HealthInfo healthInfo = new HealthInfo("High Vitamin C");
            healthInfo.addAllergy("Nuts");
            UserInteraction interaction = new UserInteraction("user1", "Salad", 4.5, true, "Fresh!");
            
            recipe2.addIngredient(ingredient);
            recipe2.addCookingInstruction(instruction);
            recipe2.setHealthInfo(healthInfo);
            recipe2.addUserInteraction(interaction);
            
            System.out.println("Test 2 - Add Components:");
            System.out.println("  Ingredients:   " + recipe2.getIngredientCount());
            System.out.println("  Instructions:  " + recipe2.getInstructionCount());
            System.out.println("  Health Info:   " + (recipe2.getHealthInfo() != null ? recipe2.getHealthInfo().getNutritionValue() : "None"));
            System.out.println("  Interactions:  " + recipe2.getUserInteractions().size());
            System.out.println("  toString:\n" + recipe2.toString() + "\n");
        } catch (IllegalArgumentException e) {
            System.out.println("Test 2 Failed: " + e.getMessage() + "\n");
        }

        // Test 3: Test constructor with invalid name (null)
        try {
            System.out.println("Test 3 - Invalid Name (Null):");
            Recipe recipe3 = new Recipe(null, "Vegetarian", "30 mins");
            System.out.println("Test 3 Failed: Should have thrown an exception\n");
        } catch (IllegalArgumentException e) {
            System.out.println("  Caught expected exception: " + e.getMessage() + "\n");
        }

        // Test 4: Test constructor with empty name
        try {
            System.out.println("Test 4 - Invalid Name (Empty):");
            Recipe recipe4 = new Recipe("  ", "Vegetarian", "30 mins");
            System.out.println("Test 4 Failed: Should have thrown an exception\n");
        } catch (IllegalArgumentException e) {
            System.out.println("  Caught expected exception: " + e.getMessage() + "\n");
        }

        // Test 5: Test setters with valid and null inputs
        try {
            Recipe recipe5 = new Recipe("Soup", "Non-Veg", "45 mins");
            System.out.println("Test 5 - Valid Setters:");
            System.out.println("  Original Name: " + recipe5.getName());
            recipe5.setName("Chicken Soup");
            recipe5.setCuisineType("Italian");
            recipe5.setDietType("Non-Vegetarian");
            recipe5.setDuration("1 hour");
            System.out.println("  Updated Name:  " + recipe5.getName());
            System.out.println("  Cuisine Type:  " + recipe5.getCuisineType());
            System.out.println("  Diet Type:    " + recipe5.getDietType());
            System.out.println("  Duration:     " + recipe5.getDuration());
            recipe5.setCuisineType(null);
            recipe5.setDietType(null);
            recipe5.setDuration(null);
            System.out.println("  After Null Set - Cuisine: " + recipe5.getCuisineType());
            System.out.println("  After Null Set - Diet:    " + recipe5.getDietType());
            System.out.println("  After Null Set - Duration: " + recipe5.getDuration() + "\n");
        } catch (IllegalArgumentException e) {
            System.out.println("Test 5 Failed: " + e.getMessage() + "\n");
        }

        // Test 6: Test adding invalid components
        try {
            Recipe recipe6 = new Recipe("Pizza", "Vegetarian", "40 mins");
            System.out.println("Test 6 - Invalid Components:");
            recipe6.addIngredient(null);
            recipe6.addCookingInstruction(null);
            recipe6.addUserInteraction(null);
            System.out.println("  Ingredients:   " + recipe6.getIngredientCount());
            System.out.println("  Instructions:  " + recipe6.getInstructionCount());
            System.out.println("  Interactions:  " + recipe6.getUserInteractions().size());
            System.out.println("  Expected: No components added (all null)");
            System.out.println("  toString:\n" + recipe6.toString() + "\n");
        } catch (IllegalArgumentException e) {
            System.out.println("Test 6 Failed: " + e.getMessage() + "\n");
        }

        // Test 7: Test setIngredients and setCookingInstructions
        try {
            Recipe recipe7 = new Recipe("Cake", "Dessert", "1 hour");
            Ingredient[] ingredients = {new Ingredient("Flour", 2.0, "cups"), new Ingredient("Sugar", 1.0, "cup")};
            CookingInstruction[] instructions = {new CookingInstruction(1, "Mix ingredients"), new CookingInstruction(2, "Bake at 350°F")};
            recipe7.setIngredients(ingredients);
            recipe7.setCookingInstructions(instructions);
            System.out.println("Test 7 - Set Ingredients and Instructions:");
            System.out.println("  Ingredients:   " + recipe7.getIngredientCount());
            System.out.println("  Instructions:  " + recipe7.getInstructionCount());
            System.out.println("  First Ingredient:  " + recipe7.getIngredients()[0].toString());
            System.out.println("  First Instruction: " + recipe7.getCookingInstructions()[0].toString());
            System.out.println("  toString:\n" + recipe7.toString() + "\n");
        } catch (IllegalArgumentException e) {
            System.out.println("Test 7 Failed: " + e.getMessage() + "\n");
        }

        // Test 8: Test defensive copying of getUserInteractions
        try {
            Recipe recipe8 = new Recipe("Pasta", "Vegetarian", "30 mins");
            UserInteraction interaction = new UserInteraction("user1", "Pasta", 4.0, true, "Great!");
            recipe8.addUserInteraction(interaction);
            List<UserInteraction> interactions = recipe8.getUserInteractions();
            interactions.add(new UserInteraction("user2", "Pasta", 3.5, false, "Okay"));
            System.out.println("Test 8 - Defensive Copying of User Interactions:");
            System.out.println("  Expected Interactions: 1");
            System.out.println("  Actual Interactions:   " + recipe8.getUserInteractions().size());
            System.out.println("  toString:\n" + recipe8.toString() + "\n");
        } catch (IllegalArgumentException e) {
            System.out.println("Test 8 Failed: " + e.getMessage() + "\n");
        }
    }
}