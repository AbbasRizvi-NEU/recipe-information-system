import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class RecipeDatabase {
    private List<Recipe> recipes;
    private static final String RECIPES_FILE = "Recipes.txt";
    private static final String INGREDIENTS_FILE = "Ingredients.txt";
    private static final String COOKING_INSTRUCTIONS_FILE = "CookingInstructions.txt";
    private static final String HEALTH_INFO_FILE = "HealthInfo.txt";
    private static final String USER_DATA_FILE = "UserData.txt";
    private RecipeRecommender recommender;

    public RecipeDatabase() {
        this.recipes = new ArrayList<>();
        this.recommender = new RecipeRecommender(recipes);
    }

    public List<Recipe> getRecipes() {
        return new ArrayList<>(recipes); // Defensive copy
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = new ArrayList<>(recipes != null ? recipes : new ArrayList<>()); // Defensive copy, handle null
        this.recommender = new RecipeRecommender(this.recipes);
    }

    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
        this.recommender = new RecipeRecommender(recipes);
        System.out.println("\nRecipe \"" + recipe.getName() + "\" added successfully.");
    }

    public Recipe searchRecipe(String name) {
        for (int i = 0; i < recipes.size(); i++) {
            if (recipes.get(i).getName().equalsIgnoreCase(name)) {
                return recipes.get(i);
            }
        }
        return null;
    }

    public boolean updateRecipe(String oldName, String newName) {
        Recipe recipe = searchRecipe(oldName);
        if (recipe != null) {
            recipe.setName(newName);
            this.recommender = new RecipeRecommender(recipes);
            return true;
        }
        return false;
    }

    public boolean removeRecipe(String name) {
        Recipe recipe = searchRecipe(name);
        if (recipe != null) {
            recipes.remove(recipe);
            this.recommender = new RecipeRecommender(recipes);
            return true;
        }
        return false;
    }

    public void listRecipes() {
        if (recipes.isEmpty()) {
            System.out.println("\nNo recipes available.");
        } else {
            System.out.println("\n--- Recipe List ---");
            System.out.println("ID | Name");
            System.out.println("---+-------------------");
            for (int i = 0; i < recipes.size(); i++) {
                System.out.println((i + 1) + "  | " + recipes.get(i).getName());
            }
            System.out.println("-------------------");
        }
    }

    public void displayUserInteractions(String recipeName) {
        Recipe recipe = searchRecipe(recipeName);
        if (recipe != null) {
            List<UserInteraction> interactions = recipe.getUserInteractions();
            if (interactions.isEmpty()) {
                System.out.println("\nNo user interactions yet for \"" + recipeName + "\".");
            } else {
                System.out.println("\nUser Interactions for \"" + recipeName + "\":");
                for (int i = 0; i < interactions.size(); i++) {
                    System.out.println("  " + (i + 1) + ". " + interactions.get(i).toString());
                }
            }
        } else {
            System.out.println("Recipe \"" + recipeName + "\" not found.");
        }
    }

    public void addUserInteraction(String username, String recipeName, double rating, boolean favorite, String comment) {
        Recipe recipe = searchRecipe(recipeName);
        if (recipe != null) {
            UserInteraction interaction = new UserInteraction(username, recipeName, rating, favorite, comment);
            recipe.addUserInteraction(interaction);
            System.out.println("\nUser interaction added successfully to \"" + recipeName + "\".");
        } else {
            System.out.println("Recipe \"" + recipeName + "\" not found.");
        }
    }

    public boolean updateUserInteraction(String username, String recipeName, Double rating, Boolean favorite, String comment) {
        Recipe recipe = searchRecipe(recipeName);
        if (recipe != null) {
            List<UserInteraction> interactions = recipe.getUserInteractions();
            for (int i = 0; i < interactions.size(); i++) {
                UserInteraction interaction = interactions.get(i);
                if (interaction.getUsername().equalsIgnoreCase(username)) {
                    if (rating != null) {
                        interaction.setRating(rating);
                    }
                    if (favorite != null) {
                        interaction.setFavorite(favorite);
                    }
                    if (comment != null) {
                        interaction.setComment(comment);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public void displayFavoriteRecipes(String username, Scanner scanner) {
        List<Recipe> favoriteRecipes = new ArrayList<>();
        for (int i = 0; i < recipes.size(); i++) {
            List<UserInteraction> interactions = recipes.get(i).getUserInteractions();
            if (interactions != null) { // Add null check
                for (int j = 0; j < interactions.size(); j++) {
                    if (interactions.get(j).getUsername().equalsIgnoreCase(username) && interactions.get(j).isFavorite()) {
                        favoriteRecipes.add(recipes.get(i));
                        break;
                    }
                }
            }
        }
        if (favoriteRecipes.isEmpty()) {
            System.out.println("\nNo favorite recipes found for user \"" + username + "\".");
        } else {
            System.out.println("\nFavorite Recipes for \"" + username + "\":");
            for (int i = 0; i < favoriteRecipes.size(); i++) {
                System.out.println("[" + (i + 1) + "] " + favoriteRecipes.get(i).getName());
            }
            System.out.print("\nEnter recipe number for details (0 to skip): ");
            String input = scanner.nextLine().trim();
            int recipeNum;
            try {
                recipeNum = Integer.parseInt(input);
                if (recipeNum > 0 && recipeNum <= favoriteRecipes.size()) {
                    System.out.println("\nRecipe Details:");
                    displayRecipeDetails(favoriteRecipes.get(recipeNum - 1));
                } else if (recipeNum != 0) {
                    System.out.println("\nInvalid recipe number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid input. Back to menu.");
            }
        }
    }

    public boolean isDatabaseEmpty() {
        return recipes.isEmpty();
    }

    public String toString() {
        String result = "Recipe Database Information:\n";
        result = result + "Total Recipes: " + recipes.size() + "\n";
        if (!recipes.isEmpty()) {
            result = result + "Available Recipes:\n";
            for (int i = 0; i < recipes.size(); i++) {
                result = result + "  " + (i + 1) + ". Recipe Name: " + recipes.get(i).getName() + "\n";
            }
        } else {
            result = result + "No recipes available in the database.\n";
        }
        return result;
    }

    public static void main(String[] args) {
        RecipeDatabase recipeDatabase = new RecipeDatabase();
        loadData(recipeDatabase);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            displayMainMenu();
            System.out.print("\nSelect option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    manageRecipesMenu(scanner, recipeDatabase);
                    break;
                case "2":
                    searchRecipesMenu(scanner, recipeDatabase);
                    break;
                case "3":
                    recommendationMenu(scanner, recipeDatabase.recommender);
                    break;
                case "4":
                    userInteractionsMenu(scanner, recipeDatabase);
                    break;
                case "5":
                    saveData(recipeDatabase);
                    System.out.println("\nData has been saved successfully!");
                    break;
                case "6":
                    bulkLoadAndSaveMenu(scanner, recipeDatabase);
                    break;
                case "7":
                    System.out.print("\nSave changes before exiting? (yes/no): ");
                    String saveChoice = scanner.nextLine().trim();
                    if (saveChoice.equalsIgnoreCase("yes")) {
                        saveData(recipeDatabase);
                        System.out.println("\nChanges saved. Exiting...\n");
                    } else {
                        System.out.println("\nExiting without saving. Goodbye!\n");
                    }
                    scanner.close();
                    return;
                default:
                    System.out.println("\nInvalid option. Please select (1-7).\n");
            }
        }
    }

    private static void displayMainMenu() {
        System.out.println("\n*************************");
        System.out.println("   R E C I P E  A P P    ");
        System.out.println("*************************");
        System.out.println("1. Manage Recipe Database");
        System.out.println("2. Recipe Search");
        System.out.println("3. Recipe Recommendation");
        System.out.println("4. User Interaction");
        System.out.println("5. Save Recipes");
        System.out.println("6. Bulk Load and Save");
        System.out.println("7. Exit");
        System.out.println("*************************");
    }

    private static void bulkLoadAndSaveMenu(Scanner scanner, RecipeDatabase recipeDatabase) {
        while (true) {
            System.out.println("\n**************************");
            System.out.println("   BULK LOAD AND SAVE     ");
            System.out.println("**************************");
            System.out.println("1. Load All Data from Files");
            System.out.println("2. Save All Data to Files");
            System.out.println("3. Back");
            System.out.println("**************************");
            System.out.print("Select option (1-3): ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    loadAllData(recipeDatabase);
                    System.out.println("\nAll data loaded successfully!");
                    break;
                case "2":
                    saveData(recipeDatabase);
                    System.out.println("\nAll data saved successfully!");
                    break;
                case "3":
                    return;
                default:
                    System.out.println("\nInvalid option. Please select (1-3).\n");
            }
        }
    }

    private static void loadAllData(RecipeDatabase recipeDatabase) {
        List<Recipe> loadedRecipes = LoadRecipes.loadRecipesFromFile(RECIPES_FILE, true);
        recipeDatabase.setRecipes(loadedRecipes);
        LoadRecipes.loadUserInteractionsFromFile(loadedRecipes, USER_DATA_FILE, true);

        System.out.println("\nLoaded " + loadedRecipes.size() + " recipes from " + RECIPES_FILE);
        for (int i = 0; i < loadedRecipes.size(); i++) {
            Recipe recipe = loadedRecipes.get(i);
            HealthInfo healthInfo = recipe.getHealthInfo();
            String allergies = "None";
            if (healthInfo != null && healthInfo.getAllergies() != null && !healthInfo.getAllergies().isEmpty()) {
                allergies = "";
                for (int j = 0; j < healthInfo.getAllergies().size(); j++) {
                    allergies = allergies + healthInfo.getAllergies().get(j);
                    if (j < healthInfo.getAllergies().size() - 1) {
                        allergies = allergies + ", ";
                    }
                }
            }
            String nutrition = healthInfo != null && healthInfo.getNutritionValue() != null ? healthInfo.getNutritionValue() : "None";
            String duration = recipe.getDuration() != null ? recipe.getDuration() : "N/A";
            System.out.println("Recipe: " + recipe.getName() + 
                              ", Cuisine: " + (recipe.getCuisineType() != null ? recipe.getCuisineType() : "N/A") + 
                              ", Category: " + (recipe.getDietType() != null ? recipe.getDietType() : "N/A") + 
                              ", Duration: " + duration +
                              ", Nutrition: " + nutrition + 
                              ", Allergies: " + allergies);
        }
        System.out.println("Loaded user interactions from " + USER_DATA_FILE);
    }

    private static void userInteractionsMenu(Scanner scanner, RecipeDatabase recipeDatabase) {
        while (true) {
            System.out.println("\n**************************");
            System.out.println("      USER INTERACTION    ");
            System.out.println("**************************");
            System.out.println("1. Submit a Review");
            System.out.println("2. Show Reviews for Recipe");
            System.out.println("3. Favourite List");
            System.out.println("4. Update a Review");
            System.out.println("5. Back");
            System.out.println("**************************");
            System.out.print("Choose an option (1-5): ");
            String input = scanner.nextLine().trim();

            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("\nPlease enter a valid number.\n");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("\nEnter your username: ");
                    String username = scanner.nextLine().trim();
                    System.out.print("\nEnter the recipe title: ");
                    String recipeName = scanner.nextLine().trim();

                    if (recipeDatabase.searchRecipe(recipeName) != null) {
                        System.out.print("\nRate the recipe (0.0 to 5.0): ");
                        double rating;
                        try {
                            rating = Double.parseDouble(scanner.nextLine().trim());
                            if (rating < 0.0 || rating > 5.0) {
                                throw new NumberFormatException();
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("\nInvalid rating. Please enter a number between 0.0 and 5.0.");
                            continue;
                        }
                        System.out.print("\nMark as favorite? (yes/no): ");
                        boolean favorite = scanner.nextLine().trim().equalsIgnoreCase("yes");
                        System.out.print("\nAdd a comment: ");
                        String comment = scanner.nextLine().trim();

                        recipeDatabase.addUserInteraction(username, recipeName, rating, favorite, comment);
                    } else {
                        System.out.println("\nRecipe not found!");
                    }
                    break;
                case 2:
                    System.out.print("\nPlease type recipe name to view reviews: ");
                    String viewRecipeName = scanner.nextLine().trim();
                    recipeDatabase.displayUserInteractions(viewRecipeName);
                    break;
                case 3:
                    System.out.print("\nEnter your username: ");
                    String favUsername = scanner.nextLine().trim();
                    recipeDatabase.displayFavoriteRecipes(favUsername, scanner);
                    break;
                case 4:
                    System.out.print("\nEnter your username: ");
                    String updateUsername = scanner.nextLine().trim();
                    System.out.print("\nEnter the recipe title: ");
                    String updateRecipeName = scanner.nextLine().trim();
                    Recipe recipe = recipeDatabase.searchRecipe(updateRecipeName);
                    if (recipe == null) {
                        System.out.println("\nRecipe not found!");
                        break;
                    }
                    boolean hasInteraction = false;
                    for (int i = 0; i < recipe.getUserInteractions().size(); i++) {
                        if (recipe.getUserInteractions().get(i).getUsername().equalsIgnoreCase(updateUsername)) {
                            hasInteraction = true;
                            break;
                        }
                    }
                    if (!hasInteraction) {
                        System.out.println("\nNo review found for this user and recipe.");
                        break;
                    }
                    System.out.println("\nUpdate Review Options:");
                    System.out.println("1. Update Rating");
                    System.out.println("2. Update Favorite Status");
                    System.out.println("3. Update Comment");
                    System.out.println("4. Update All");
                    System.out.println("5. Cancel");
                    System.out.print("Select option (1-5): ");
                    String updateChoice = scanner.nextLine().trim();
                    Double newRating = null;
                    Boolean newFavorite = null;
                    String newComment = null;
                    switch (updateChoice) {
                        case "1":
                            System.out.print("\nEnter new rating (0.0 to 5.0): ");
                            try {
                                newRating = Double.parseDouble(scanner.nextLine().trim());
                                if (newRating < 0.0 || newRating > 5.0) {
                                    throw new NumberFormatException();
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("\nInvalid rating. Update cancelled.");
                                break;
                            }
                            break;
                        case "2":
                            System.out.print("\nMark as favorite? (yes/no): ");
                            newFavorite = scanner.nextLine().trim().equalsIgnoreCase("yes");
                            break;
                        case "3":
                            System.out.print("\nEnter new comment: ");
                            newComment = scanner.nextLine().trim();
                            break;
                        case "4":
                            System.out.print("\nEnter new rating (0.0 to 5.0): ");
                            try {
                                newRating = Double.parseDouble(scanner.nextLine().trim());
                                if (newRating < 0.0 || newRating > 5.0) {
                                    throw new NumberFormatException();
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("\nInvalid rating. Update cancelled.");
                                break;
                            }
                            System.out.print("\nMark as favorite? (yes/no): ");
                            newFavorite = scanner.nextLine().trim().equalsIgnoreCase("yes");
                            System.out.print("\nEnter new comment: ");
                            newComment = scanner.nextLine().trim();
                            break;
                        case "5":
                            System.out.println("\nUpdate cancelled.");
                            break;
                        default:
                            System.out.println("\nInvalid option. Update cancelled.");
                            break;
                    }
                    if (newRating != null || newFavorite != null || newComment != null) {
                        boolean updated = recipeDatabase.updateUserInteraction(updateUsername, updateRecipeName, newRating, newFavorite, newComment);
                        if (updated) {
                            System.out.println("\nReview updated successfully!");
                        } else {
                            System.out.println("\nFailed to update review.");
                        }
                    }
                    break;
                case 5:
                    return;
                default:
                    System.out.println("\nInvalid choice. Select (1-5).");
            }
        }
    }

    private static void manageRecipesMenu(Scanner scanner, RecipeDatabase recipeDatabase) {
        while (true) {
            System.out.println("\n**************************");
            System.out.println("  MANAGE RECIPE DATABASE  ");
            System.out.println("**************************");
            System.out.println("1. Add a recipe");
            System.out.println("2. Update recipe");
            System.out.println("3. Remove recipe");
            System.out.println("4. List all recipes");
            System.out.println("5. Back");
            System.out.println("**************************");
            System.out.print("Select action (1-5): ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    addRecipeWithDetails(scanner, recipeDatabase);
                    break;
                case "2":
                    updateRecipeMenu(scanner, recipeDatabase);
                    break;
                case "3":
                    System.out.print("\nEnter the recipe title to delete: ");
                    String removeName = scanner.nextLine().trim();
                    boolean removed = recipeDatabase.removeRecipe(removeName);
                    if (removed) {
                        System.out.println("\nRecipe \"" + removeName + "\" removed successfully.");
                    } else {
                        System.out.println("\nCould not remove. Recipe not found.");
                    }
                    break;
                case "4":
                    recipeDatabase.listRecipes();
                    if (!recipeDatabase.getRecipes().isEmpty()) {
                        System.out.print("\nEnter recipe number for details (0 to skip): ");
                        String input = scanner.nextLine().trim();
                        int recipeNum;
                        try {
                            recipeNum = Integer.parseInt(input);
                            if (recipeNum > 0 && recipeNum <= recipeDatabase.getRecipes().size()) {
                                System.out.println("\nRecipe Details:");
                                displayRecipeDetails(recipeDatabase.getRecipes().get(recipeNum - 1));
                            } else if (recipeNum != 0) {
                                System.out.println("\nInvalid recipe number.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("\nInvalid input. Back to menu.");
                        }
                    }
                    break;
                case "5":
                    return;
                default:
                    System.out.println("\nInvalid option. Please choose (1-5).");
            }
        }
    }

    private static void updateRecipeMenu(Scanner scanner, RecipeDatabase recipeDatabase) {
        System.out.print("\nEnter the recipe title to update: ");
        String recipeName = scanner.nextLine().trim();
        Recipe recipe = recipeDatabase.searchRecipe(recipeName);
        if (recipe == null) {
            System.out.println("\nRecipe not found!");
            return;
        }
        while (true) {
            System.out.println("\n**************************");
            System.out.println("  UPDATE RECIPE MENU      ");
            System.out.println("**************************");
            System.out.println("1. Update Recipe Name");
            System.out.println("2. Add/Update Ingredients");
            System.out.println("3. Add/Update Cooking Instructions");
            System.out.println("4. Add/Update Health Info");
            System.out.println("5. Update Duration");
            System.out.println("6. Back");
            System.out.println("**************************");
            System.out.print("Select option (1-6): ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.print("\nEnter the new recipe title: ");
                    String newName = scanner.nextLine().trim();
                    if (!newName.isEmpty()) {
                        recipe.setName(newName);
                        recipeDatabase.recommender = new RecipeRecommender(recipeDatabase.getRecipes());
                        System.out.println("\nRecipe name updated successfully!");
                    } else {
                        System.out.println("\nRecipe name cannot be empty.");
                    }
                    break;
                case "2":
                    System.out.print("\nReplace existing ingredients? (y/n): ");
                    String replaceIngredients = scanner.nextLine().trim();
                    if (replaceIngredients.equalsIgnoreCase("y")) {
                        recipe.setIngredients(null);
                    }
                    System.out.print("\nNumber of ingredients to add: ");
                    String input = scanner.nextLine().trim();
                    int numIngredients;
                    try {
                        numIngredients = Integer.parseInt(input);
                        if (numIngredients <= 0) {
                            throw new NumberFormatException();
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("\nInvalid number. Skipping ingredients.");
                        break;
                    }
                    for (int i = 1; i <= numIngredients; i++) {
                        System.out.println("\nIngredient " + i + ":");
                        System.out.print("\nName: ");
                        String ingredientName = scanner.nextLine().trim();
                        if (ingredientName.isEmpty()) {
                            System.out.println("\nIngredient name cannot be empty. Skipping.");
                            continue;
                        }
                        System.out.print("\nQuantity: ");
                        String quantityInput = scanner.nextLine().trim();
                        double ingredientQuantity;
                        try {
                            ingredientQuantity = Double.parseDouble(quantityInput);
                            if (ingredientQuantity <= 0) {
                                throw new NumberFormatException();
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("\nInvalid quantity. Skipping ingredient.");
                            continue;
                        }
                        System.out.print("\nUnit: ");
                        String ingredientUnit = scanner.nextLine().trim();
                        Ingredient ingredient = new Ingredient(ingredientName, ingredientQuantity, ingredientUnit);
                        recipe.addIngredient(ingredient);
                    }
                    System.out.println("\nIngredients updated successfully!");
                    recipeDatabase.recommender = new RecipeRecommender(recipeDatabase.getRecipes());
                    break;
                case "3":
                    System.out.print("\nReplace existing instructions? (y/n): ");
                    String replaceInstructions = scanner.nextLine().trim();
                    int nextSequence = 1;
                    if (!replaceInstructions.equalsIgnoreCase("y")) {
                        CookingInstruction[] existingInstructions = recipe.getCookingInstructions();
                        if (existingInstructions != null) {
                            for (int i = 0; i < existingInstructions.length; i++) {
                                if (existingInstructions[i] != null && existingInstructions[i].getSequence() >= nextSequence) {
                                    nextSequence = existingInstructions[i].getSequence() + 1;
                                }
                            }
                        }
                    } else {
                        recipe.setCookingInstructions(null);
                    }
                    System.out.print("\nEnter number of steps: ");
                    input = scanner.nextLine().trim();
                    int numSteps;
                    try {
                        numSteps = Integer.parseInt(input);
                        if (numSteps <= 0) {
                            throw new NumberFormatException();
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("\nInvalid number. Skipping instructions.");
                        break;
                    }
                    for (int i = 0; i < numSteps; i++) {
                        System.out.print("\nStep " + (nextSequence + i) + ": ");
                        String stepDesc = scanner.nextLine().trim();
                        if (stepDesc.isEmpty()) {
                            System.out.println("\nInstruction cannot be empty. Skipping.");
                            continue;
                        }
                        boolean isDuplicate = false;
                        CookingInstruction[] existingInstructions = recipe.getCookingInstructions();
                        if (existingInstructions != null) {
                            for (int j = 0; j < existingInstructions.length; j++) {
                                if (existingInstructions[j] != null && 
                                    existingInstructions[j].getStep().equalsIgnoreCase(stepDesc)) {
                                    isDuplicate = true;
                                    break;
                                }
                            }
                        }
                        if (isDuplicate) {
                            System.out.println("\nThis step already exists. Skipping.");
                            continue;
                        }
                        CookingInstruction instruction = new CookingInstruction(nextSequence + i, stepDesc);
                        recipe.addCookingInstruction(instruction);
                    }
                    System.out.println("\nCooking instructions updated successfully!");
                    recipeDatabase.recommender = new RecipeRecommender(recipeDatabase.getRecipes());
                    break;
                case "4":
                    System.out.print("\nReplace existing health info? (y/n): ");
                    String replaceHealthInfo = scanner.nextLine().trim();
                    if (replaceHealthInfo.equalsIgnoreCase("y")) {
                        recipe.setHealthInfo(null);
                    }
                    System.out.print("\nEnter nutrition value (e.g., low calorie, high protein): ");
                    String nutritionValue = scanner.nextLine().trim();
                    HealthInfo healthInfo = new HealthInfo(nutritionValue.isEmpty() ? null : nutritionValue);
                    System.out.print("\nWould you like to add medical condition/allergy? (y/n): ");
                    String addAllergies = scanner.nextLine().trim();
                    if (addAllergies.equalsIgnoreCase("y")) {
                        System.out.print("\nEnter medical condition/allergy (e.g., Nuts Allergy): ");
                        String allergiesInput = scanner.nextLine().trim();
                        if (!allergiesInput.isEmpty()) {
                            String[] allergies = allergiesInput.split(",");
                            for (int i = 0; i < allergies.length; i++) {
                                String trimmedAllergy = allergies[i].trim();
                                if (!trimmedAllergy.isEmpty()) {
                                    healthInfo.addAllergy(trimmedAllergy);
                                }
                            }
                        }
                    }
                    recipe.setHealthInfo(healthInfo);
                    System.out.println("\nHealth info updated successfully!");
                    recipeDatabase.recommender = new RecipeRecommender(recipeDatabase.getRecipes());
                    break;
                case "5":
                    System.out.print("\nEnter new duration (e.g., 30 mins): ");
                    String newDuration = scanner.nextLine().trim();
                    if (newDuration.isEmpty()) {
                        System.out.println("\nDuration cannot be empty. Setting to N/A.");
                        newDuration = null;
                    }
                    recipe.setDuration(newDuration);
                    System.out.println("\nDuration updated successfully!");
                    break;
                case "6":
                    return;
                default:
                    System.out.println("\nInvalid option. Please select (1-6).");
            }
        }
    }

    private static void searchRecipesMenu(Scanner scanner, RecipeDatabase recipeDatabase) {
        while (true) {
            System.out.println("\n**********************");
            System.out.println("    RECIPE SEARCH     ");
            System.out.println("**********************");
            System.out.println("1. By Name");
            System.out.println("2. By Ingredient");
            System.out.println("3. By Cuisine");
            System.out.println("4. By Category");
            System.out.println("5. By Allergy/Medical");
            System.out.println("6. Back");
            System.out.println("**********************");
            System.out.print("Select option (1-6): ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.print("\nEnter recipe name: ");
                    String name = scanner.nextLine().trim();
                    Recipe recipe = recipeDatabase.searchRecipe(name);
                    if (recipe != null) {
                        System.out.println("\nFound: ");
                        displayRecipeDetails(recipe);
                    } else {
                        System.out.println("\nRecipe not found.");
                    }
                    break;
                case "2":
                    System.out.print("\nEnter ingredient: ");
                    String ingredient = scanner.nextLine().trim();
                    List<Recommendation> ingredientResults = recipeDatabase.recommender.recommendByIngredient(ingredient);
                    displayRecommendations(ingredientResults, "Ingredient: " + ingredient, scanner);
                    break;
                case "3":
                    System.out.print("\nEnter cuisine (e.g., Indian, Italian): ");
                    String cuisine = scanner.nextLine().trim();
                    List<Recommendation> cuisineResults = recipeDatabase.recommender.recommendByCuisine(cuisine);
                    displayRecommendations(cuisineResults, "Cuisine: " + cuisine, scanner);
                    break;
                case "4":
                    System.out.print("\nEnter category (e.g., vegan, vegetarian, non-veg): ");
                    String category = scanner.nextLine().trim();
                    List<Recommendation> categoryResults = recipeDatabase.recommender.recommendByDietType(category);
                    displayRecommendations(categoryResults, "Category: " + category, scanner);
                    break;
                case "5":
                    System.out.print("\nEnter Allergy/Medical (e.g., nuts, gluten, diabetes): ");
                    String allergensInput = scanner.nextLine().trim();
                    List<String> allergens = new ArrayList<>();
                    if (!allergensInput.isEmpty()) {
                        String[] allergenArray = allergensInput.split(",");
                        for (int i = 0; i < allergenArray.length; i++) {
                            String trimmedAllergen = allergenArray[i].trim();
                            if (!trimmedAllergen.isEmpty()) {
                                allergens.add(trimmedAllergen);
                            }
                        }
                    }
                    List<Recommendation> allergenResults = recipeDatabase.recommender.searchByAllergens(allergens);
                    displayRecommendations(allergenResults, "Recipes containing allergens: " + allergensInput, scanner);
                    break;
                case "6":
                    return;
                default:
                    System.out.println("\nInvalid option. Please select (1-6).");
            }
        }
    }

    private static void recommendationMenu(Scanner scanner, RecipeRecommender recommender) {
        while (true) {
            System.out.println("\n*************************");
            System.out.println("  RECIPE RECOMMENDATION  ");
            System.out.println("*************************");
            System.out.println("1. By Cuisine");
            System.out.println("2. By Ingredient");
            System.out.println("3. By Medical/Allergy");
            System.out.println("4. By Nutrition");
            System.out.println("5. By Category");
            System.out.println("6. Back");
            System.out.println("*************************");
            System.out.print("Select option (1-6): ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.print("\nEnter cuisine type (e.g., Indian, Italian): ");
                    String cuisine = scanner.nextLine().trim();
                    List<Recommendation> cuisineRecommendations = recommender.recommendByCuisine(cuisine);
                    displayRecommendations(cuisineRecommendations, "Cuisine: " + cuisine, scanner);
                    break;
                case "2":
                    System.out.print("\nEnter ingredient to search: ");
                    String ingredient = scanner.nextLine().trim();
                    List<Recommendation> ingredientRecommendations = recommender.recommendByIngredient(ingredient);
                    displayRecommendations(ingredientRecommendations, "Ingredient: " + ingredient, scanner);
                    break;
                case "3":
                    System.out.print("\nEnter allergens to avoid (comma-separated, e.g., nuts,gluten): ");
                    String allergensInput = scanner.nextLine().trim();
                    List<String> allergens = new ArrayList<>();
                    if (!allergensInput.isEmpty()) {
                        String[] allergenArray = allergensInput.split(",");
                        for (int i = 0; i < allergenArray.length; i++) {
                            String trimmedAllergen = allergenArray[i].trim();
                            if (!trimmedAllergen.isEmpty()) {
                                allergens.add(trimmedAllergen);
                            }
                        }
                    }
                    List<Recommendation> allergenRecommendations = recommender.recommendByAllergens(allergens);
                    displayRecommendations(allergenRecommendations, "Safe recipes avoiding allergens: " + allergensInput, scanner);
                    break;
                case "4":
                    System.out.print("\nEnter nutrition preference (e.g., low carb, high protein): ");
                    String nutritionPreference = scanner.nextLine().trim();
                    List<Recommendation> nutritionRecommendations = recommender.recommendByNutritionValue(nutritionPreference);
                    displayRecommendations(nutritionRecommendations, "Nutrition: " + nutritionPreference, scanner);
                    break;
                case "5":
                    System.out.print("\nEnter category (e.g., vegan, vegetarian, non-veg): ");
                    String category = scanner.nextLine().trim();
                    List<Recommendation> categoryRecommendations = recommender.recommendByDietType(category);
                    displayRecommendations(categoryRecommendations, "Category: " + category, scanner);
                    break;
                case "6":
                    return;
                default:
                    System.out.println("\nInvalid option. Please select (1-6).");
            }
        }
    }

    private static void displayRecipeDetails(Recipe recipe) {
        String result = "Name: " + recipe.getName() + "\n";
        result = result + "Cuisine Type: " + (recipe.getCuisineType() != null ? recipe.getCuisineType() : "N/A") + "\n";
        result = result + "Category: " + (recipe.getDietType() != null ? recipe.getDietType() : "N/A") + "\n";
        result = result + "Duration: " + (recipe.getDuration() != null ? recipe.getDuration() : "N/A") + "\n";
        result = result + "Ingredients:\n";
        Ingredient[] ingredients = recipe.getIngredients();
        if (ingredients != null && ingredients.length > 0) {
            for (int i = 0; i < ingredients.length; i++) {
                if (ingredients[i] != null) {
                    result = result + "  " + (i + 1) + ". " + ingredients[i].toString() + "\n";
                }
            }
        } else {
            result = result + "  None\n";
        }
        result = result + "Cooking Instructions:\n";
        CookingInstruction[] instructions = recipe.getCookingInstructions();
        if (instructions != null && instructions.length > 0) {
            for (int i = 0; i < instructions.length; i++) {
                if (instructions[i] != null) {
                    result = result + "  " + instructions[i].toString() + "\n";
                }
            }
        } else {
            result = result + "  None\n";
        }
        result = result + "Health Info:\n";
        HealthInfo healthInfo = recipe.getHealthInfo();
        if (healthInfo != null) {
            result = result + "  Nutrition Value: " + (healthInfo.getNutritionValue() != null ? healthInfo.getNutritionValue() : "None") + "\n";
            List<String> allergies = healthInfo.getAllergies();
            if (allergies != null && !allergies.isEmpty()) {
                result = result + "  Allergies: ";
                for (int i = 0; i < allergies.size(); i++) {
                    result = result + allergies.get(i);
                    if (i < allergies.size() - 1) {
                        result = result + ", ";
                    }
                }
                result = result + "\n";
            } else {
                result = result + "  Allergies: None\n";
            }
        } else {
            result = result + "  None\n";
        }
        System.out.println(result);
    }

    private static void displayRecommendations(List<Recommendation> recommendations, String criteria, Scanner scanner) {
        System.out.println("\nResults for " + criteria + ": ");
        if (recommendations.isEmpty()) {
            System.out.println("\nNo recommendations found.");
        } else {
            System.out.println("\nFound " + recommendations.size() + " recommendation(s):");
            for (int i = 0; i < recommendations.size(); i++) {
                Recommendation rec = recommendations.get(i);
                String ingredient = rec.getKeyIngredient() != null ? rec.getKeyIngredient().toString() : "None";
                System.out.println("[" + (i + 1) + "] " + rec.getRecipe().getName() + 
                                  " (Cuisine: " + rec.getCuisine() + ", Key Ingredient: " + ingredient + ")");
            }
            System.out.print("\nEnter recommendation number for details (0 to skip): ");
            String input = scanner.nextLine().trim();
            int recNum;
            try {
                recNum = Integer.parseInt(input);
                if (recNum > 0 && recNum <= recommendations.size()) {
                    System.out.println("\nRecipe Details:");
                    displayRecipeDetails(recommendations.get(recNum - 1).getRecipe());
                } else if (recNum != 0) {
                    System.out.println("\nInvalid recommendation number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid input. Back to menu.");
            }
        }
    }

    private static void addRecipeWithDetails(Scanner scanner, RecipeDatabase recipeDatabase) {
        System.out.println("\n--- Add Recipe ---");
        System.out.print("\nEnter recipe name: ");
        String newRecipeName = scanner.nextLine().trim();
        if (newRecipeName.isEmpty()) {
            System.out.println("\nRecipe name cannot be empty.");
            return;
        }
        System.out.print("\nEnter cuisine type (e.g., Indian, Italian, Chinese): ");
        String cuisineType = scanner.nextLine().trim();
        System.out.print("\nEnter category (e.g., vegan, vegetarian, non-veg): ");
        String category = scanner.nextLine().trim();
        System.out.print("\nEnter duration (e.g., 30 mins): ");
        String duration = scanner.nextLine().trim();
        if (duration.isEmpty()) {
            System.out.println("\nDuration cannot be empty. Setting to N/A.");
            duration = null;
        }

        Recipe recipe = new Recipe(newRecipeName, category.isEmpty() ? null : category, duration);
        if (!cuisineType.isEmpty()) {
            recipe.setCuisineType(cuisineType);
        }
        recipeDatabase.addRecipe(recipe);

        System.out.print("\nAdd ingredients? (y/n): ");
        String addIngredientsNow = scanner.nextLine().trim();
        if (addIngredientsNow.equalsIgnoreCase("y")) {
            System.out.print("\nNumber of ingredients: ");
            String input = scanner.nextLine().trim();
            int numIngredients;
            try {
                numIngredients = Integer.parseInt(input);
                if (numIngredients <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid number. Skipping ingredients.");
                return;
            }
            for (int i = 1; i <= numIngredients; i++) {
                System.out.println("\nIngredient " + i + ":");
                System.out.print("\nName: ");
                String ingredientName = scanner.nextLine().trim();
                if (ingredientName.isEmpty()) {
                    System.out.println("\nIngredient name cannot be empty. Skipping.");
                    continue;
                }
                System.out.print("\nQuantity: ");
                String quantityInput = scanner.nextLine().trim();
                double ingredientQuantity;
                try {
                    ingredientQuantity = Double.parseDouble(quantityInput);
                    if (ingredientQuantity <= 0) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException e) {
                    System.out.println("\nInvalid quantity. Skipping ingredient.");
                    continue;
                }
                System.out.print("\nUnit: ");
                String ingredientUnit = scanner.nextLine().trim();
                Ingredient ingredient = new Ingredient(ingredientName, ingredientQuantity, ingredientUnit);
                recipe.addIngredient(ingredient);
            }
        }
        System.out.print("\nWould you like to add cooking instructions? (y/n): ");
        String addInstructionsNow = scanner.nextLine().trim();
        if (addInstructionsNow.equalsIgnoreCase("y")) {
            System.out.print("\nEnter number of steps: ");
            String input = scanner.nextLine().trim();
            int numSteps;
            try {
                numSteps = Integer.parseInt(input);
                if (numSteps <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid number. Skipping instructions.");
                return;
            }
            for (int i = 1; i <= numSteps; i++) {
                System.out.print("\nStep " + i + ": ");
                String stepDesc = scanner.nextLine().trim();
                if (stepDesc.isEmpty()) {
                    System.out.println("\nInstruction cannot be empty. Skipping.");
                    continue;
                }
                CookingInstruction instruction = new CookingInstruction(i, stepDesc);
                recipe.addCookingInstruction(instruction);
            }
        }
        System.out.print("\nWould you like to add health info? (y/n): ");
        String addHealthInfoNow = scanner.nextLine().trim();
        if (addHealthInfoNow.equalsIgnoreCase("y")) {
            System.out.print("\nEnter nutrition value (e.g., low calorie, high protein): ");
            String nutritionValue = scanner.nextLine().trim();
            HealthInfo healthInfo = new HealthInfo(nutritionValue.isEmpty() ? null : nutritionValue);
            System.out.print("\nWould you like to add medical condition/allergy? (y/n): ");
            String addAllergies = scanner.nextLine().trim();
            if (addAllergies.equalsIgnoreCase("y")) {
                System.out.print("\nEnter medical condition/allergy (e.g., Nuts Allergy): ");
                String allergiesInput = scanner.nextLine().trim();
                if (!allergiesInput.isEmpty()) {
                    String[] allergies = allergiesInput.split(",");
                    for (int i = 0; i < allergies.length; i++) {
                        String trimmedAllergy = allergies[i].trim();
                        if (!trimmedAllergy.isEmpty()) {
                            healthInfo.addAllergy(trimmedAllergy);
                        }
                    }
                }
            }
            recipe.setHealthInfo(healthInfo);
        }
        System.out.println("\nRecipe added: " + newRecipeName);
    }

    private static void loadData(RecipeDatabase recipeDatabase) {
        List<Recipe> loadedRecipes = LoadRecipes.loadRecipesFromFile(RECIPES_FILE, false);
        recipeDatabase.setRecipes(loadedRecipes);
        if (Files.exists(Path.of(USER_DATA_FILE))) {
            LoadRecipes.loadUserInteractionsFromFile(loadedRecipes, USER_DATA_FILE, false);
        }
        System.out.println("\nStarted with " + loadedRecipes.size() + " recipes from existing files.");
    }

    private static void saveData(RecipeDatabase recipeDatabase) {
        List<Recipe> recipes = recipeDatabase.getRecipes();
        for (int i = 0; i < recipes.size(); i++) {
            Recipe recipe = recipes.get(i);
            HealthInfo healthInfo = recipe.getHealthInfo();
            String allergies = "None";
            if (healthInfo != null && healthInfo.getAllergies() != null && !healthInfo.getAllergies().isEmpty()) {
                allergies = "";
                for (int j = 0; j < healthInfo.getAllergies().size(); j++) {
                    allergies = allergies + healthInfo.getAllergies().get(j);
                    if (j < healthInfo.getAllergies().size() - 1) {
                        allergies = allergies + ", ";
                    }
                }
            }
            String duration = recipe.getDuration() != null ? recipe.getDuration() : "N/A";
            System.out.println("Saving recipe: " + recipe.getName() + 
                              ", Cuisine: " + (recipe.getCuisineType() != null ? recipe.getCuisineType() : "N/A") + 
                              ", Category: " + (recipe.getDietType() != null ? recipe.getDietType() : "N/A") + 
                              ", Duration: " + duration +
                              ", Allergies: " + allergies);
        }
        try {
            SaveRecipes.saveRecipesToFile(recipes, RECIPES_FILE);
            SaveRecipes.saveUserInteractionsToFile(recipes, USER_DATA_FILE);
            List<Ingredient> allIngredients = new ArrayList<>();
            List<CookingInstruction> allInstructions = new ArrayList<>();
            List<HealthInfo> allHealthInfo = new ArrayList<>();
            for (int i = 0; i < recipes.size(); i++) {
                Ingredient[] ingredients = recipes.get(i).getIngredients();
                if (ingredients != null) {
                    for (int j = 0; j < ingredients.length; j++) {
                        if (ingredients[j] != null) {
                            allIngredients.add(ingredients[j]);
                        }
                    }
                }
                CookingInstruction[] instructions = recipes.get(i).getCookingInstructions();
                if (instructions != null) {
                    for (int j = 0; j < instructions.length; j++) {
                        if (instructions[j] != null) {
                            allInstructions.add(instructions[j]);
                        }
                    }
                }
                if (recipes.get(i).getHealthInfo() != null) {
                    allHealthInfo.add(recipes.get(i).getHealthInfo());
                }
            }
            SaveRecipes.saveIngredientsToFile(allIngredients, INGREDIENTS_FILE);
            SaveRecipes.saveCookingInstructionsToFile(allInstructions, COOKING_INSTRUCTIONS_FILE);
            SaveRecipes.saveHealthInfoToFile(allHealthInfo, HEALTH_INFO_FILE);
            System.out.println("Saved " + recipes.size() + " recipes to " + RECIPES_FILE);
            System.out.println("Saved " + allIngredients.size() + " ingredients to " + INGREDIENTS_FILE);
            System.out.println("Saved " + allInstructions.size() + " instructions to " + COOKING_INSTRUCTIONS_FILE);
            System.out.println("Saved " + allHealthInfo.size() + " health info records to " + HEALTH_INFO_FILE);
        } catch (IOException e) {
            System.err.println("Error saving data to files: " + e.getMessage());
        }
    }
}