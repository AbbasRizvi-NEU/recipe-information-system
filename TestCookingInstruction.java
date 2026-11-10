public class TestCookingInstruction {
    public static void main(String[] args) {
        System.out.println("Testing CookingInstruction Class\n");

        // Test 1: Create a valid CookingInstruction
        try {
            CookingInstruction instruction1 = new CookingInstruction(1, "Chop the onions finely");
            System.out.println("Test 1 - Valid Creation:");
            System.out.println("  Expected: Step 1: Chop the onions finely");
            System.out.println("  Actual:   " + instruction1.toString());
            System.out.println("  Sequence: " + instruction1.getSequence());
            System.out.println("  Step:     " + instruction1.getStep() + "\n");
        } catch (IllegalArgumentException e) {
            System.out.println("Test 1 Failed: " + e.getMessage() + "\n");
        }

        // Test 2: Test setters with valid inputs
        try {
            CookingInstruction instruction2 = new CookingInstruction(2, "Boil water");
            System.out.println("Test 2 - Valid Setters:");
            System.out.println("  Original: " + instruction2.toString());
            instruction2.setSequence(3);
            instruction2.setStep("Simmer the sauce for 10 minutes");
            System.out.println("  After Update: " + instruction2.toString());
            System.out.println("  Sequence: " + instruction2.getSequence());
            System.out.println("  Step:     " + instruction2.getStep() + "\n");
        } catch (IllegalArgumentException e) {
            System.out.println("Test 2 Failed: " + e.getMessage() + "\n");
        }

        // Test 3: Test constructor with invalid sequence (negative)
        try {
            System.out.println("Test 3 - Invalid Sequence (Negative):");
            CookingInstruction instruction3 = new CookingInstruction(-1, "Mix ingredients");
            System.out.println("Test 3 Failed: Should have thrown an exception\n");
        } catch (IllegalArgumentException e) {
            System.out.println("  Caught expected exception: " + e.getMessage() + "\n");
        }

        // Test 4: Test constructor with empty step
        try {
            System.out.println("Test 4 - Invalid Step (Empty):");
            CookingInstruction instruction4 = new CookingInstruction(1, "");
            System.out.println("Test 4 Failed: Should have thrown an exception\n");
        } catch (IllegalArgumentException e) {
            System.out.println("  Caught expected exception: " + e.getMessage() + "\n");
        }

        // Test 5: Test constructor with null step
        try {
            System.out.println("Test 5 - Invalid Step (Null):");
            CookingInstruction instruction5 = new CookingInstruction(1, null);
            System.out.println("Test 5 Failed: Should have thrown an exception\n");
        } catch (IllegalArgumentException e) {
            System.out.println("  Caught expected exception: " + e.getMessage() + "\n");
        }

        // Test 6: Test setters with invalid inputs
        try {
            CookingInstruction instruction6 = new CookingInstruction(1, "Stir the mixture");
            System.out.println("Test 6 - Invalid Setter (Negative Sequence):");
            instruction6.setSequence(0);
            System.out.println("Test 6 Failed: Should have thrown an exception\n");
        } catch (IllegalArgumentException e) {
            System.out.println("  Caught expected exception: " + e.getMessage() + "\n");
        }

        // Test 7: Test setter with empty step
        try {
            CookingInstruction instruction7 = new CookingInstruction(1, "Stir the mixture");
            System.out.println("Test 7 - Invalid Setter (Empty Step):");
            instruction7.setStep("  ");
            System.out.println("Test 7 Failed: Should have thrown an exception\n");
        } catch (IllegalArgumentException e) {
            System.out.println("  Caught expected exception: " + e.getMessage() + "\n");
        }
    }
}