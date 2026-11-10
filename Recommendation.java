public class Recommendation {
    private String cuisine;
    private Ingredient keyIngredient;
    private Recipe recipe; // New field to link to a Recipe

    // Default constructor
    public Recommendation() {
        this.cuisine = "";
        this.keyIngredient = null;
        this.recipe = null;
    }

    // Parameterized constructor with validation
    public Recommendation(String cuisine, Ingredient keyIngredient, Recipe recipe) {
        if (cuisine == null || cuisine.trim().isEmpty()) {
            this.cuisine = "";
        } else {
            this.cuisine = cuisine.trim();
        }
        this.keyIngredient = keyIngredient; // Allow null for flexibility
        this.recipe = recipe; // Allow null, validated in setter if needed
    }

    // Getters
    public String getCuisine() {
        return cuisine;
    }

    public Ingredient getKeyIngredient() {
        return keyIngredient;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    // Setters
    public void setCuisine(String cuisine) {
        if (cuisine != null && !cuisine.trim().isEmpty()) {
            this.cuisine = cuisine.trim();
        }
    }

    public void setKeyIngredient(Ingredient keyIngredient) {
        this.keyIngredient = keyIngredient;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    // toString with concatenation
    public String toString() {
        String result = cuisine;
        if (cuisine.isEmpty()) {
            result = "Unknown Cuisine";
        }
        result = result + "|";
        if (keyIngredient != null) {
            result = result + keyIngredient.toString();
        } else {
            result = result + "none";
        }
        result = result + "|";
        if (recipe != null) {
            result = result + recipe.getName();
        } else {
            result = result + "no recipe";
        }
        return result;
    }
}