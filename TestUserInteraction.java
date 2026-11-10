public class TestUserInteraction {
    public static void main(String[] args) {
        System.out.println("Testing UserInteraction Class\n");

        // Test 1: Test constructor with valid inputs
        try {
            UserInteraction interaction1 = new UserInteraction("user1", "Pasta", 4.5, true, "Delicious!");
            System.out.println("Test 1 - Valid Constructor:");
            System.out.println("  Expected: User: user1\nRating: 4.5/5.0\nFavorite: Yes\nComment: \"Delicious!\"");
            System.out.println("  Actual:   \n" + interaction1.toString());
            System.out.println("  Username: " + interaction1.getUsername());
            System.out.println("  Recipe:   " + interaction1.getRecipeName());
            System.out.println("  Rating:   " + interaction1.getRating());
            System.out.println("  Favorite: " + interaction1.isFavorite());
            System.out.println("  Comment:  " + interaction1.getComment() + "\n");
        } catch (IllegalArgumentException e) {
            System.out.println("Test 1 Failed: " + e.getMessage() + "\n");
        }

        // Test 2: Test setters with valid inputs
        try {
            UserInteraction interaction2 = new UserInteraction("user2", "Salad", 3.0, false, "Okay");
            System.out.println("Test 2 - Valid Setters:");
            System.out.println("  Original: \n" + interaction2.toString());
            interaction2.setUsername("newUser");
            interaction2.setRecipeName("Caesar Salad");
            interaction2.setRating(4.0);
            interaction2.setFavorite(true);
            interaction2.setComment("Really fresh!");
            System.out.println("  After Update: \n" + interaction2.toString());
            System.out.println("  Username: " + interaction2.getUsername());
            System.out.println("  Recipe:   " + interaction2.getRecipeName());
            System.out.println("  Rating:   " + interaction2.getRating());
            System.out.println("  Favorite: " + interaction2.isFavorite());
            System.out.println("  Comment:  " + interaction2.getComment() + "\n");
        } catch (IllegalArgumentException e) {
            System.out.println("Test 2 Failed: " + e.getMessage() + "\n");
        }

        // Test 3: Test constructor with null username
        try {
            System.out.println("Test 3 - Invalid Username (Null):");
            UserInteraction interaction3 = new UserInteraction(null, "Pasta", 4.0, true, "Good");
            System.out.println("Test 3 Failed: Should have thrown an exception\n");
        } catch (IllegalArgumentException e) {
            System.out.println("  Caught expected exception: " + e.getMessage() + "\n");
        }

        // Test 4: Test constructor with empty recipe name
        try {
            System.out.println("Test 4 - Invalid Recipe Name (Empty):");
            UserInteraction interaction4 = new UserInteraction("user1", "  ", 4.0, true, "Good");
            System.out.println("Test 4 Failed: Should have thrown an exception\n");
        } catch (IllegalArgumentException e) {
            System.out.println("  Caught expected exception: " + e.getMessage() + "\n");
        }

        // Test 5: Test constructor with invalid rating (negative)
        try {
            System.out.println("Test 5 - Invalid Rating (Negative):");
            UserInteraction interaction5 = new UserInteraction("user1", "Pasta", -1.0, true, "Good");
            System.out.println("Test 5 Failed: Should have thrown an exception\n");
        } catch (IllegalArgumentException e) {
            System.out.println("  Caught expected exception: " + e.getMessage() + "\n");
        }

        // Test 6: Test constructor with invalid rating (above 5.0)
        try {
            System.out.println("Test 6 - Invalid Rating (Above 5.0):");
            UserInteraction interaction6 = new UserInteraction("user1", "Pasta", 6.0, true, "Good");
            System.out.println("Test 6 Failed: Should have thrown an exception\n");
        } catch (IllegalArgumentException e) {
            System.out.println("  Caught expected exception: " + e.getMessage() + "\n");
        }

        // Test 7: Test setters with invalid inputs
        try {
            UserInteraction interaction7 = new UserInteraction("user1", "Pasta", 4.0, true, "Good");
            System.out.println("Test 7 - Invalid Setter (Null Username):");
            interaction7.setUsername(null);
            System.out.println("Test 7 Failed: Should have thrown an exception\n");
        } catch (IllegalArgumentException e) {
            System.out.println("  Caught expected exception: " + e.getMessage() + "\n");
        }

        // Test 8: Test setter with invalid rating
        try {
            UserInteraction interaction8 = new UserInteraction("user1", "Pasta", 4.0, true, "Good");
            System.out.println("Test 8 - Invalid Setter (Rating Above 5.0):");
            interaction8.setRating(5.1);
            System.out.println("Test 8 Failed: Should have thrown an exception\n");
        } catch (IllegalArgumentException e) {
            System.out.println("  Caught expected exception: " + e.getMessage() + "\n");
        }

        // Test 9: Test constructor and setter with null/empty comment
        try {
            UserInteraction interaction9 = new UserInteraction("user1", "Pasta", 4.0, true, null);
            System.out.println("Test 9 - Null/Empty Comment:");
            System.out.println("  Expected Comment: None");
            System.out.println("  Actual Comment:   " + interaction9.getComment());
            interaction9.setComment("  ");
            System.out.println("  After Empty Comment Set: " + interaction9.getComment());
            System.out.println("  toString:\n" + interaction9.toString() + "\n");
        } catch (IllegalArgumentException e) {
            System.out.println("Test 9 Failed: " + e.getMessage() + "\n");
        }
    }
}