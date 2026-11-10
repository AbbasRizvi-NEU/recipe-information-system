public class TestIngredient {
    public static void main(String[] args) {
        System.out.println("Testing Ingredient Class\n");

        // Test 1: Create a valid Ingredient
        try {
            Ingredient ingredient1 = new Ingredient("Flour", 2.5, "cups");
            System.out.println("Test 1 - Valid Creation:");
            System.out.println("  Expected: Name: Flour, Quantity: 2.5, Unit: cups");
            System.out.println("  Actual:   " + ingredient1.toString());
            System.out.println("  Name:     " + ingredient1.getName());
            System.out.println("  Quantity: " + ingredient1.getQuantity());
            System.out.println("  Unit:     " + ingredient1.getUnit() + "\n");
        } catch (IllegalArgumentException e) {
            System.out.println("Test 1 Failed: " + e.getMessage() + "\n");
        }

        // Test 2: Test setters with valid inputs
        try {
            Ingredient ingredient2 = new Ingredient("Sugar", 1.0, "tbsp");
            System.out.println("Test 2 - Valid Setters:");
            System.out.println("  Expected: Name: Sugar, Quantity: 1.0, Unit: tbsp");
            System.out.println("  Original: " + ingredient2.toString());
            ingredient2.setName("Brown Sugar");
            ingredient2.setQuantity(1.5);
            ingredient2.setUnit("tsp");
            System.out.println("  After Update: " + ingredient2.toString());
            System.out.println("  Name:     " + ingredient2.getName());
            System.out.println("  Quantity: " + ingredient2.getQuantity());
            System.out.println("  Unit:     " + ingredient2.getUnit() + "\n");
        } catch (IllegalArgumentException e) {
            System.out.println("Test 2 Failed: " + e.getMessage() + "\n");
        }

        // Test 3: Test constructor with null name
        try {
            System.out.println("Test 3 - Invalid Name (Null):");
            Ingredient ingredient3 = new Ingredient(null, 1.0, "grams");
            System.out.println("Test 3 Failed: Should have thrown an exception\n");
        } catch (IllegalArgumentException e) {
            System.out.println("  Caught expected exception: " + e.getMessage() + "\n");
        }

        // Test 4: Test constructor with empty name
        try {
            System.out.println("Test 4 - Invalid Name (Empty):");
            Ingredient ingredient4 = new Ingredient("  ", 1.0, "grams");
            System.out.println("Test 4 Failed: Should have thrown an exception\n");
        } catch (IllegalArgumentException e) {
            System.out.println("  Caught expected exception: " + e.getMessage() + "\n");
        }

        // Test 5: Test constructor with non-positive quantity
        try {
            System.out.println("Test 5 - Invalid Quantity (Zero):");
            Ingredient ingredient5 = new Ingredient("Salt", 0.0, "pinch");
            System.out.println("Test 5 Failed: Should have thrown an exception\n");
        } catch (IllegalArgumentException e) {
            System.out.println("  Caught expected exception: " + e.getMessage() + "\n");
        }

        // Test 6: Test setters with invalid inputs
        try {
            Ingredient ingredient6 = new Ingredient("Butter", 100.0, "grams");
            System.out.println("Test 6 - Invalid Setter (Null Name):");
            ingredient6.setName(null);
            System.out.println("Test 6 Failed: Should have thrown an exception\n");
        } catch (IllegalArgumentException e) {
            System.out.println("  Caught expected exception: " + e.getMessage() + "\n");
        }

        // Test 7: Test setter with negative quantity
        try {
            Ingredient ingredient7 = new Ingredient("Butter", 100.0, "grams");
            System.out.println("Test 7 - Invalid Setter (Negative Quantity):");
            ingredient7.setQuantity(-1.0);
            System.out.println("Test 7 Failed: Should have thrown an exception\n");
        } catch (IllegalArgumentException e) {
            System.out.println("  Caught expected exception: " + e.getMessage() + "\n");
        }

        // Test 8: Test constructor and setter with null unit
        try {
            Ingredient ingredient8 = new Ingredient("Olive Oil", 2.0, null);
            System.out.println("Test 8 - Null Unit:");
            System.out.println("  Expected: Name: Olive Oil, Quantity: 2.0, Unit: N/A");
            System.out.println("  Actual:   " + ingredient8.toString());
            ingredient8.setUnit(null);
            System.out.println("  After Null Unit Set: " + ingredient8.toString() + "\n");
        } catch (IllegalArgumentException e) {
            System.out.println("Test 8 Failed: " + e.getMessage() + "\n");
        }
    }
}