import java.util.ArrayList;
import java.util.List;

public class HealthInfo {
    private String nutritionValue;
    private List<String> allergies;

    public HealthInfo(String nutritionValue) {
        this.nutritionValue = nutritionValue != null ? nutritionValue.trim() : null;
        this.allergies = new ArrayList<>();
    }

    public String getNutritionValue() {
        return nutritionValue;
    }

    public void setNutritionValue(String nutritionValue) {
        this.nutritionValue = nutritionValue != null ? nutritionValue.trim() : null;
    }

    public void addAllergy(String allergy) {
        if (allergy != null && !allergy.trim().isEmpty()) {
            allergies.add(allergy.trim());
        }
    }

    public List<String> getAllergies() {
        return new ArrayList<>(allergies);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Nutrition Value: ")
              .append(nutritionValue != null ? nutritionValue : "None")
              .append("\n");
        result.append("Allergies: ");
        if (allergies.isEmpty()) {
            result.append("None");
        } else {
            result.append(String.join(", ", allergies));
        }
        return result.toString();
    }
}