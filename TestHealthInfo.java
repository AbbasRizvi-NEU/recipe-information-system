import java.util.List;

public class TestHealthInfo {
    public static void main(String[] args) {
        System.out.println("Testing HealthInfo Class\n");

        // Test 1: Create a HealthInfo with valid nutrition value
        System.out.println("Test 1 - Valid Creation:");
        HealthInfo healthInfo1 = new HealthInfo("High Protein");
        System.out.println("  Expected Nutrition: High Protein");
        System.out.println("  Actual Nutrition:   " + healthInfo1.getNutritionValue());
        System.out.println("  Expected Allergies: None");
        System.out.println("  Actual Allergies:   " + healthInfo1.getAllergies());
        System.out.println("  toString:\n" + healthInfo1.toString() + "\n");

        // Test 2: Add allergies and verify
        System.out.println("Test 2 - Add Allergies:");
        HealthInfo healthInfo2 = new HealthInfo("Low Calorie");
        healthInfo2.addAllergy("Nuts");
        healthInfo2.addAllergy("Gluten");
        System.out.println("  Expected Nutrition: Low Calorie");
        System.out.println("  Actual Nutrition:   " + healthInfo2.getNutritionValue());
        System.out.println("  Expected Allergies: [Nuts, Gluten]");
        System.out.println("  Actual Allergies:   " + healthInfo2.getAllergies());
        System.out.println("  toString:\n" + healthInfo2.toString() + "\n");

        // Test 3: Test constructor with null nutrition value
        System.out.println("Test 3 - Null Nutrition Value:");
        HealthInfo healthInfo3 = new HealthInfo(null);
        System.out.println("  Expected Nutrition: null");
        System.out.println("  Actual Nutrition:   " + healthInfo3.getNutritionValue());
        System.out.println("  Expected Allergies: []");
        System.out.println("  Actual Allergies:   " + healthInfo3.getAllergies());
        System.out.println("  toString:\n" + healthInfo3.toString() + "\n");

        // Test 4: Test setNutritionValue with valid and null inputs
        System.out.println("Test 4 - Set Nutrition Value:");
        HealthInfo healthInfo4 = new HealthInfo("High Fiber");
        System.out.println("  Original Nutrition: " + healthInfo4.getNutritionValue());
        healthInfo4.setNutritionValue("Vitamin C");
        System.out.println("  After Valid Set:    " + healthInfo4.getNutritionValue());
        healthInfo4.setNutritionValue(null);
        System.out.println("  After Null Set:     " + healthInfo4.getNutritionValue());
        System.out.println("  toString:\n" + healthInfo4.toString() + "\n");

        // Test 5: Test addAllergy with invalid inputs (null and empty)
        System.out.println("Test 5 - Invalid Allergy Inputs:");
        HealthInfo healthInfo5 = new HealthInfo("Low Carb");
        healthInfo5.addAllergy(null);
        healthInfo5.addAllergy("");
        healthInfo5.addAllergy("  ");
        System.out.println("  Expected Allergies: []");
        System.out.println("  Actual Allergies:   " + healthInfo5.getAllergies());
        System.out.println("  toString:\n" + healthInfo5.toString() + "\n");

        // Test 6: Test defensive copying of getAllergies
        System.out.println("Test 6 - Defensive Copying of Allergies:");
        HealthInfo healthInfo6 = new HealthInfo("High Protein");
        healthInfo6.addAllergy("Dairy");
        List<String> allergies = healthInfo6.getAllergies();
        allergies.add("Eggs"); // Modify the returned list
        System.out.println("  Expected Allergies: [Dairy]");
        System.out.println("  Actual Allergies:   " + healthInfo6.getAllergies());
        System.out.println("  toString:\n" + healthInfo6.toString() + "\n");
    }
}