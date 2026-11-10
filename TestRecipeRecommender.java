import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestRecipeRecommender {
    public static void main(String[] args) {
        System.out.println("Testing RecipeRecommender Class\n");

        // Setup sample recipes for testing
        Recipe pasta = new Recipe("Pasta", "Vegetarian", "30 mins");
        pasta.setCuisineType("Italian");
        pasta.addIngredient(new Ingredient("Tomato", 1.0, "kg"));
        HealthInfo pastaHealth = new HealthInfo("Low Calorie");
        pastaHealth.addAllergy("Gluten");
        pasta.setHealthInfo(pastaHealth);

        Recipe salad = new Recipe("Salad", "Vegan", "20 mins");
        salad.setCuisineType("Greek");
        salad.addIngredient(new Ingredient("Cucumber", 1.0, "unit"));
        HealthInfo saladHealth = new HealthInfo("High Vitamin C");
        salad.setHealthInfo(saladHealth);

        List<Recipe> recipeList = Arrays.asList(pasta, salad);
        RecipeRecommender recommender = new RecipeRecommender(recipeList);

        // Test 1: Test constructor and toString
        System.out.println("Test 1 - Constructor and toString:");
        System.out.println("  Expected: RecipeRecommender with 2 recipes");
        System.out.println("  Actual:\n" + recommender.toString());
        System.out.println("  Recipe Count: " + recommender.getRecipes().size() + "\n");

        // Test 2: Test recommendByCuisine
        System.out.println("Test 2 - Recommend by Cuisine (Italian):");
        List<Recommendation> cuisineRecs = recommender.recommendByCuisine("Italian");
        System.out.println("  Expected: 1 recommendation (Pasta)");
        System.out.println("  Actual:   " + cuisineRecs.size() + " recommendations");
        if (!cuisineRecs.isEmpty()) {
            System.out.println("  First Recommendation: " + cuisineRecs.get(0).getRecipe().getName());
        }
        System.out.println();

        // Test 3: Test recommendByDietType
        System.out.println("Test 3 - Recommend by Diet Type (Vegan):");
        List<Recommendation> dietRecs = recommender.recommendByDietType("Vegan");
        System.out.println("  Expected: 1 recommendation (Salad)");
        System.out.println("  Actual:   " + dietRecs.size() + " recommendations");
        if (!dietRecs.isEmpty()) {
            System.out.println("  First Recommendation: " + dietRecs.get(0).getRecipe().getName());
        }
        System.out.println();

        // Test 4: Test recommendByIngredient
        System.out.println("Test 4 - Recommend by Ingredient (Cucumber):");
        List<Recommendation> ingredientRecs = recommender.recommendByIngredient("Cucumber");
        System.out.println("  Expected: 1 recommendation (Salad)");
        System.out.println("  Actual:   " + ingredientRecs.size() + " recommendations");
        if (!ingredientRecs.isEmpty()) {
            System.out.println("  First Recommendation: " + ingredientRecs.get(0).getRecipe().getName());
        }
        System.out.println();

        // Test 5: Test recommendByNutritionValue
        System.out.println("Test 5 - Recommend by Nutrition Value (Low Calorie):");
        List<Recommendation> nutritionRecs = recommender.recommendByNutritionValue("Low Calorie");
        System.out.println("  Expected: 1 recommendation (Pasta)");
        System.out.println("  Actual:   " + nutritionRecs.size() + " recommendations");
        if (!nutritionRecs.isEmpty()) {
            System.out.println("  First Recommendation: " + nutritionRecs.get(0).getRecipe().getName());
        }
        System.out.println();

        // Test 6: Test recommendByAllergens (avoid Gluten)
        System.out.println("Test 6 - Recommend by Allergens (Avoid Gluten):");
        List<String> allergens = Arrays.asList("Gluten");
        List<Recommendation> allergenRecs = recommender.recommendByAllergens(allergens);
        System.out.println("  Expected: 1 recommendation (Salad)");
        System.out.println("  Actual:   " + allergenRecs.size() + " recommendations");
        if (!allergenRecs.isEmpty()) {
            System.out.println("  First Recommendation: " + allergenRecs.get(0).getRecipe().getName());
        }
        System.out.println();

        // Test 7: Test searchByAllergens (find Gluten)
        System.out.println("Test 7 - Search by Allergens (Find Gluten):");
        List<Recommendation> allergenSearch = recommender.searchByAllergens(allergens);
        System.out.println("  Expected: 1 recommendation (Pasta)");
        System.out.println("  Actual:   " + allergenSearch.size() + " recommendations");
        if (!allergenSearch.isEmpty()) {
            System.out.println("  First Recommendation: " + allergenSearch.get(0).getRecipe().getName());
        }
        System.out.println();

        // Test 8: Test null/empty inputs
        System.out.println("Test 8 - Null/Empty Inputs:");
        System.out.println("  Cuisine (null):");
        List<Recommendation> nullCuisine = recommender.recommendByCuisine(null);
        System.out.println("  Expected: 0 recommendations");
        System.out.println("  Actual:   " + nullCuisine.size() + " recommendations");

        System.out.println("  Diet Type (empty):");
        List<Recommendation> emptyDiet = recommender.recommendByDietType("");
        System.out.println("  Expected: 0 recommendations");
        System.out.println("  Actual:   " + emptyDiet.size() + " recommendations");

        System.out.println("  Ingredient (null):");
        List<Recommendation> nullIngredient = recommender.recommendByIngredient(null);
        System.out.println("  Expected: 0 recommendations");
        System.out.println("  Actual:   " + nullIngredient.size() + " recommendations");

        System.out.println("  Nutrition (empty):");
        List<Recommendation> emptyNutrition = recommender.recommendByNutritionValue("  ");
        System.out.println("  Expected: 0 recommendations");
        System.out.println("  Actual:   " + emptyNutrition.size() + " recommendations");

        System.out.println("  Allergens (empty list):");
        List<Recommendation> emptyAllergens = recommender.recommendByAllergens(new ArrayList<>());
        System.out.println("  Expected: 2 recommendations (all recipes)");
        System.out.println("  Actual:   " + emptyAllergens.size() + " recommendations");
        System.out.println();

        // Test 9: Test setRecipes with null
        System.out.println("Test 9 - Set Recipes with Null:");
        recommender.setRecipes(null);
        System.out.println("  Expected: 0 recipes");
        System.out.println("  Actual:   " + recommender.getRecipes().size() + " recipes");
        System.out.println("  toString:\n" + recommender.toString() + "\n");

        // Test 10: Test defensive copying of getRecipes
        System.out.println("Test 10 - Defensive Copying of Recipes:");
        recommender.setRecipes(recipeList);
        List<Recipe> recipes = recommender.getRecipes();
        recipes.add(new Recipe("Soup", "Non-Veg", "45 mins"));
        System.out.println("  Expected: 2 recipes (original unchanged)");
        System.out.println("  Actual:   " + recommender.getRecipes().size() + " recipes");
        System.out.println("  toString:\n" + recommender.toString());
    }
}