public class TestRecommendation {
    public static void main(String[] args) {
        System.out.println("Testing Recommendation Class\n");

        // Test 1: Test default constructor
        System.out.println("Test 1 - Default Constructor:");
        Recommendation rec1 = new Recommendation();
        System.out.println("  Expected: Unknown Cuisine|none|no recipe");
        System.out.println("  Actual:   " + rec1.toString());
        System.out.println("  Cuisine:  " + rec1.getCuisine());
        System.out.println("  Ingredient: " + rec1.getKeyIngredient());
        System.out.println("  Recipe:   " + rec1.getRecipe() + "\n");

        // Test 2: Test parameterized constructor with valid inputs
        System.out.println("Test 2 - Parameterized Constructor (Valid Inputs):");
        Ingredient ingredient = new Ingredient("Tomato", 1.0, "kg");
        Recipe recipe = new Recipe("Pasta", "Vegetarian", "30 mins");
        Recommendation rec2 = new Recommendation("Italian", ingredient, recipe);
        System.out.println("  Expected: Italian|Name: Tomato, Quantity: 1.0, Unit: kg|Pasta");
        System.out.println("  Actual:   " + rec2.toString());
        System.out.println("  Cuisine:  " + rec2.getCuisine());
        System.out.println("  Ingredient: " + rec2.getKeyIngredient().toString());
        System.out.println("  Recipe:   " + rec2.getRecipe().getName() + "\n");

        // Test 3: Test parameterized constructor with null/empty inputs
        System.out.println("Test 3 - Parameterized Constructor (Null/Empty Inputs):");
        Recommendation rec3 = new Recommendation("", null, null);
        System.out.println("  Expected: Unknown Cuisine|none|no recipe");
        System.out.println("  Actual:   " + rec3.toString());
        System.out.println("  Cuisine:  " + rec3.getCuisine());
        System.out.println("  Ingredient: " + rec3.getKeyIngredient());
        System.out.println("  Recipe:   " + rec3.getRecipe() + "\n");

        // Test 4: Test setters with valid inputs
        System.out.println("Test 4 - Setters (Valid Inputs):");
        Recommendation rec4 = new Recommendation();
        rec4.setCuisine("Greek");
        Ingredient newIngredient = new Ingredient("Cucumber", 1.0, "unit");
        Recipe newRecipe = new Recipe("Salad", "Vegan", "20 mins");
        rec4.setKeyIngredient(newIngredient);
        rec4.setRecipe(newRecipe);
        System.out.println("  Expected: Greek|Name: Cucumber, Quantity: 1.0, Unit: unit|Salad");
        System.out.println("  Actual:   " + rec4.toString());
        System.out.println("  Cuisine:  " + rec4.getCuisine());
        System.out.println("  Ingredient: " + rec4.getKeyIngredient().toString());
        System.out.println("  Recipe:   " + rec4.getRecipe().getName() + "\n");

        // Test 5: Test setters with null/empty inputs
        System.out.println("Test 5 - Setters (Null/Empty Inputs):");
        Recommendation rec5 = new Recommendation("Italian", ingredient, recipe);
        rec5.setCuisine(null);
        rec5.setCuisine("");
        rec5.setKeyIngredient(null);
        rec5.setRecipe(null);
        System.out.println("  Expected: Italian|none|no recipe");
        System.out.println("  Actual:   " + rec5.toString());
        System.out.println("  Cuisine:  " + rec5.getCuisine());
        System.out.println("  Ingredient: " + rec5.getKeyIngredient());
        System.out.println("  Recipe:   " + rec5.getRecipe() + "\n");

        // Test 6: Test toString with partial data
        System.out.println("Test 6 - toString with Partial Data:");
        Recommendation rec6 = new Recommendation("Chinese", null, recipe);
        System.out.println("  Expected: Chinese|none|Pasta");
        System.out.println("  Actual:   " + rec6.toString());
        System.out.println("  Cuisine:  " + rec6.getCuisine());
        System.out.println("  Ingredient: " + rec6.getKeyIngredient());
        System.out.println("  Recipe:   " + rec6.getRecipe().getName() + "\n");

        // Test 7: Test setting invalid cuisine (empty string)
        System.out.println("Test 7 - Set Invalid Cuisine (Empty):");
        Recommendation rec7 = new Recommendation("French", ingredient, recipe);
        rec7.setCuisine("  ");
        System.out.println("  Expected: French (unchanged due to empty input)");
        System.out.println("  Actual:   " + rec7.getCuisine());
        System.out.println("  toString: " + rec7.toString() + "\n");
    }
}