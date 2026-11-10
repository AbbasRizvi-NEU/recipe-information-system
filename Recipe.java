import java.util.ArrayList;
import java.util.List;

public class Recipe {
    private String name;
    private String cuisineType; // e.g., Indian, Italian
    private String dietType;   // Category, e.g., Veg, Non-Veg
    private String duration;   // e.g., 30 mins, 1 hour
    private List<Ingredient> ingredients;
    private List<CookingInstruction> cookingInstructions;
    private HealthInfo healthInfo;
    private List<UserInteraction> userInteractions;

    // Constructor
    public Recipe(String name, String dietType, String duration) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Recipe name cannot be null or empty");
        }
        this.name = name.trim();
        this.dietType = dietType != null ? dietType.trim() : null;
        this.duration = duration != null ? duration.trim() : null;
        this.cuisineType = null;
        this.ingredients = new ArrayList<>();
        this.cookingInstructions = new ArrayList<>();
        this.userInteractions = new ArrayList<>();
        this.healthInfo = null;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getCuisineType() {
        return cuisineType;
    }

    public String getDietType() {
        return dietType;
    }

    public String getDuration() {
        return duration;
    }

    public Ingredient[] getIngredients() {
        return ingredients.toArray(new Ingredient[0]);
    }

    public int getIngredientCount() {
        return ingredients.size();
    }

    public CookingInstruction[] getCookingInstructions() {
        return cookingInstructions.toArray(new CookingInstruction[0]);
    }

    public int getInstructionCount() {
        return cookingInstructions.size();
    }

    public HealthInfo getHealthInfo() {
        return healthInfo;
    }

    public List<UserInteraction> getUserInteractions() {
        return new ArrayList<>(userInteractions);
    }

    // Setters
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Recipe name cannot be null or empty");
        }
        this.name = name.trim();
    }

    public void setCuisineType(String cuisineType) {
        this.cuisineType = cuisineType != null ? cuisineType.trim() : null;
    }

    public void setDietType(String dietType) {
        this.dietType = dietType != null ? dietType.trim() : null;
    }

    public void setDuration(String duration) {
        this.duration = duration != null ? duration.trim() : null;
    }

    public void setIngredients(Ingredient[] ingredients) {
        this.ingredients = new ArrayList<>();
        if (ingredients != null) {
            for (int i = 0; i < ingredients.length; i++) {
                if (ingredients[i] != null) {
                    addIngredient(ingredients[i]);
                }
            }
        }
    }

    public void setCookingInstructions(CookingInstruction[] instructions) {
        this.cookingInstructions = new ArrayList<>();
        if (instructions != null) {
            for (int i = 0; i < instructions.length; i++) {
                if (instructions[i] != null) {
                    addCookingInstruction(instructions[i]);
                }
            }
        }
    }

    public void setHealthInfo(HealthInfo healthInfo) {
        this.healthInfo = healthInfo;
    }

    // Instance Methods
    public void addIngredient(Ingredient ingredient) {
        if (ingredient != null && ingredient.getName() != null && 
            !ingredient.getName().trim().isEmpty() && ingredient.getQuantity() > 0) {
            ingredients.add(ingredient);
        }
    }

    public void addCookingInstruction(CookingInstruction instruction) {
        if (instruction != null && instruction.getStep() != null && 
            !instruction.getStep().trim().isEmpty() && instruction.getSequence() > 0) {
            cookingInstructions.add(instruction);
        }
    }

    public void addUserInteraction(UserInteraction interaction) {
        if (interaction != null && interaction.getUsername() != null && 
            !interaction.getUsername().trim().isEmpty()) {
            userInteractions.add(interaction);
        }
    }

    public String toString() {
        String result = "Name: " + name + "\n";
        result = result + "Cuisine Type: " + (cuisineType != null ? cuisineType : "N/A") + "\n";
        result = result + "Category: " + (dietType != null ? dietType : "N/A") + "\n";
        result = result + "Duration: " + (duration != null ? duration : "N/A") + "\n";
        result = result + "Ingredients:\n";
        if (ingredients.isEmpty()) {
            result = result + "  None\n";
        } else {
            for (int i = 0; i < ingredients.size(); i++) {
                Ingredient ing = ingredients.get(i);
                result = result + "  " + (i + 1) + ". Name: " + ing.getName() +
                         ", Quantity: " + ing.getQuantity() +
                         ", Unit: " + (ing.getUnit() != null ? ing.getUnit() : "N/A") + "\n";
            }
        }
        result = result + "Cooking Instructions:\n";
        if (cookingInstructions.isEmpty()) {
            result = result + "  None\n";
        } else {
            for (int i = 0; i < cookingInstructions.size(); i++) {
                CookingInstruction instr = cookingInstructions.get(i);
                result = result + "  Step " + instr.getSequence() + ": " + instr.getStep() + "\n";
            }
        }
        result = result + "Health Info:\n";
        if (healthInfo != null) {
            result = result + "  Nutrition Value: " + 
                     (healthInfo.getNutritionValue() != null ? healthInfo.getNutritionValue() : "None") + "\n";
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
        return result;
    }
}