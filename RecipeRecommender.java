import java.util.ArrayList;
import java.util.List;

public class RecipeRecommender {
    private List<Recipe> recipes;

    // Constructor
    public RecipeRecommender(List<Recipe> recipes) {
        this.recipes = recipes != null ? new ArrayList<>(recipes) : new ArrayList<>();
    }

    // Getter
    public List<Recipe> getRecipes() {
        return new ArrayList<>(recipes);
    }

    // Setter
    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes != null ? new ArrayList<>(recipes) : new ArrayList<>();
    }

    public List<Recommendation> recommendByCuisine(String cuisine) {
        List<Recommendation> suggestions = new ArrayList<>();
        if (cuisine == null || cuisine.trim().isEmpty()) {
            System.out.println("Cuisine search: Empty or null input, returning no matches.");
            return suggestions;
        }
        String searchCuisine = cuisine.trim().toLowerCase();
        for (int i = 0; i < recipes.size(); i++) {
            Recipe recipe = recipes.get(i);
            if (recipe != null && recipe.getCuisineType() != null && 
                recipe.getCuisineType().trim().toLowerCase().contains(searchCuisine)) {
                Ingredient keyIngredient = recipe.getIngredients() != null && recipe.getIngredients().length > 0 ? 
                                          recipe.getIngredients()[0] : null;
                suggestions.add(new Recommendation(recipe.getCuisineType(), keyIngredient, recipe));
                System.out.println("Recommended: " + recipe.getName() + " (" + recipe.getCuisineType() + ")");
            }
        }
        System.out.println("Cuisine search for \"" + cuisine + "\": Found " + suggestions.size() + " matches.");
        return suggestions;
    }

    public List<Recommendation> recommendByDietType(String dietType) {
        List<Recommendation> suggestions = new ArrayList<>();
        if (dietType == null || dietType.trim().isEmpty()) {
            System.out.println("Category search: Empty or null input, returning no matches.");
            return suggestions;
        }
        String searchDietType = dietType.trim().toLowerCase();
        for (int i = 0; i < recipes.size(); i++) {
            Recipe recipe = recipes.get(i);
            if (recipe != null && recipe.getDietType() != null && 
                recipe.getDietType().trim().toLowerCase().contains(searchDietType)) {
                Ingredient keyIngredient = recipe.getIngredients() != null && recipe.getIngredients().length > 0 ? 
                                          recipe.getIngredients()[0] : null;
                suggestions.add(new Recommendation(recipe.getCuisineType(), keyIngredient, recipe));
                System.out.println("Recommended: " + recipe.getName() + " (" + recipe.getDietType() + ")");
            }
        }
        System.out.println("Category search for \"" + dietType + "\": Found " + suggestions.size() + " matches.");
        return suggestions;
    }

    public List<Recommendation> recommendByIngredient(String ingredient) {
        List<Recommendation> suggestions = new ArrayList<>();
        if (ingredient == null || ingredient.trim().isEmpty()) {
            System.out.println("Ingredient search: Empty or null input, returning no matches.");
            return suggestions;
        }
        String searchIngredient = ingredient.trim().toLowerCase();
        for (int i = 0; i < recipes.size(); i++) {
            Recipe recipe = recipes.get(i);
            if (recipe != null) {
                Ingredient[] ingredients = recipe.getIngredients();
                if (ingredients != null) {
                    for (int j = 0; j < ingredients.length; j++) {
                        Ingredient ing = ingredients[j];
                        if (ing != null && ing.getName() != null && 
                            ing.getName().trim().toLowerCase().contains(searchIngredient)) {
                            System.out.println("Found ingredient \"" + ing.getName() + "\" in recipe \"" + recipe.getName() + "\"");
                            suggestions.add(new Recommendation(recipe.getCuisineType(), ing, recipe));
                            break;
                        }
                    }
                }
            }
        }
        System.out.println("Ingredient search for \"" + ingredient + "\": Found " + suggestions.size() + " matches.");
        return suggestions;
    }

    public List<Recommendation> recommendByNutritionValue(String nutritionValue) {
        List<Recommendation> suggestions = new ArrayList<>();
        if (nutritionValue == null || nutritionValue.trim().isEmpty()) {
            System.out.println("Nutrition search: Empty or null input, returning no matches.");
            return suggestions;
        }
        String searchNutrition = nutritionValue.trim().toLowerCase();
        for (int i = 0; i < recipes.size(); i++) {
            Recipe recipe = recipes.get(i);
            if (recipe != null) {
                HealthInfo healthInfo = recipe.getHealthInfo();
                if (healthInfo != null && healthInfo.getNutritionValue() != null && 
                    healthInfo.getNutritionValue().trim().toLowerCase().contains(searchNutrition)) {
                    System.out.println("Found nutrition \"" + healthInfo.getNutritionValue() + "\" in recipe \"" + recipe.getName() + "\"");
                    Ingredient keyIngredient = recipe.getIngredients() != null && recipe.getIngredients().length > 0 ? 
                                              recipe.getIngredients()[0] : null;
                    suggestions.add(new Recommendation(recipe.getCuisineType(), keyIngredient, recipe));
                }
            }
        }
        System.out.println("Nutrition search for \"" + nutritionValue + "\": Found " + suggestions.size() + " matches.");
        return suggestions;
    }

    public List<Recommendation> recommendByAllergens(List<String> allergens) {
        List<Recommendation> suggestions = new ArrayList<>();
        if (allergens == null || allergens.isEmpty()) {
            System.out.println("Allergen recommendation: Empty or null input, returning all recipes.");
            for (int i = 0; i < recipes.size(); i++) {
                if (recipes.get(i) != null) {
                    Recipe recipe = recipes.get(i);
                    Ingredient keyIngredient = recipe.getIngredients() != null && recipe.getIngredients().length > 0 ? 
                                              recipe.getIngredients()[0] : null;
                    suggestions.add(new Recommendation(recipe.getCuisineType(), keyIngredient, recipe));
                }
            }
            return suggestions;
        }

        List<String> searchAllergens = new ArrayList<>();
        for (int i = 0; i < allergens.size(); i++) {
            String allergen = allergens.get(i);
            if (allergen != null && !allergen.trim().isEmpty()) {
                searchAllergens.add(allergen.trim().toLowerCase());
            }
        }

        for (int i = 0; i < recipes.size(); i++) {
            Recipe recipe = recipes.get(i);
            if (recipe == null) {
                continue;
            }
            boolean isSafe = true;
            HealthInfo healthInfo = recipe.getHealthInfo();
            if (healthInfo != null && healthInfo.getAllergies() != null) {
                for (int j = 0; j < healthInfo.getAllergies().size(); j++) {
                    String allergy = healthInfo.getAllergies().get(j);
                    if (allergy != null) {
                        for (int k = 0; k < searchAllergens.size(); k++) {
                            String searchAllergen = searchAllergens.get(k);
                            if (allergy.trim().toLowerCase().contains(searchAllergen)) {
                                isSafe = false;
                                break;
                            }
                        }
                        if (!isSafe) {
                            break;
                        }
                    }
                }
            }
            if (isSafe) {
                Ingredient keyIngredient = recipe.getIngredients() != null && recipe.getIngredients().length > 0 ? 
                                          recipe.getIngredients()[0] : null;
                suggestions.add(new Recommendation(recipe.getCuisineType(), keyIngredient, recipe));
            }
        }
        String allergenOutput = "";
        for (int i = 0; i < searchAllergens.size(); i++) {
            allergenOutput = allergenOutput + searchAllergens.get(i);
            if (i < searchAllergens.size() - 1) {
                allergenOutput = allergenOutput + ", ";
            }
        }
        System.out.println("Allergen recommendation for avoiding \"" + allergenOutput + 
                          "\": Found " + suggestions.size() + " safe recipes.");
        return suggestions;
    }

    public List<Recommendation> searchByAllergens(List<String> allergens) {
        List<Recommendation> suggestions = new ArrayList<>();
        if (allergens == null || allergens.isEmpty()) {
            System.out.println("Allergen search: Empty or null input, returning no matches.");
            return suggestions;
        }

        List<String> searchAllergens = new ArrayList<>();
        for (int i = 0; i < allergens.size(); i++) {
            String allergen = allergens.get(i);
            if (allergen != null && !allergen.trim().isEmpty()) {
                searchAllergens.add(allergen.trim().toLowerCase());
            }
        }

        for (int i = 0; i < recipes.size(); i++) {
            Recipe recipe = recipes.get(i);
            if (recipe == null) {
                continue;
            }
            HealthInfo healthInfo = recipe.getHealthInfo();
            if (healthInfo != null && healthInfo.getAllergies() != null) {
                for (int j = 0; j < healthInfo.getAllergies().size(); j++) {
                    String allergy = healthInfo.getAllergies().get(j);
                    if (allergy != null) {
                        for (int k = 0; k < searchAllergens.size(); k++) {
                            String searchAllergen = searchAllergens.get(k);
                            if (allergy.trim().toLowerCase().contains(searchAllergen)) {
                                Ingredient keyIngredient = recipe.getIngredients() != null && recipe.getIngredients().length > 0 ? 
                                                          recipe.getIngredients()[0] : null;
                                suggestions.add(new Recommendation(recipe.getCuisineType(), keyIngredient, recipe));
                                System.out.println("Found allergen \"" + allergy + "\" in recipe \"" + recipe.getName() + "\"");
                                break;
                            }
                        }
                        if (suggestions.contains(new Recommendation(recipe.getCuisineType(), 
                                                                   recipe.getIngredients() != null && recipe.getIngredients().length > 0 ? 
                                                                   recipe.getIngredients()[0] : null, recipe))) {
                            break;
                        }
                    }
                }
            }
        }
        String allergenOutput = "";
        for (int i = 0; i < searchAllergens.size(); i++) {
            allergenOutput = allergenOutput + searchAllergens.get(i);
            if (i < searchAllergens.size() - 1) {
                allergenOutput = allergenOutput + ", ";
            }
        }
        System.out.println("Allergen search for \"" + allergenOutput + 
                          "\": Found " + suggestions.size() + " recipes containing these allergens.");
        return suggestions;
    }

    public String toString() {
        String result = "RecipeRecommender with " + recipes.size() + " recipes:\n";
        for (int i = 0; i < recipes.size(); i++) {
            if (recipes.get(i) != null) {
                result = result + "  " + (i + 1) + ". " + recipes.get(i).getName() + "\n";
            }
        }
        return result;
    }
}