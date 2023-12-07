package Budget;

// import statements
import java.util.List;
import javax.swing.*;

// Class to handle the calculation of the total value of a list of input fields and combo boxes
public class ValueCalculation {
    // Calculates the total value of a list of input fields and combo boxes
    public static double calculateTotalInputAndCombo(List<JTextField> inputFields, List<JComboBox<String>> comboBoxes) {
        double total = 0.0; // Stores the total value of the inputs and combos
        for (int i = 0; i < inputFields.size(); i++) { // Loops through each input and combo
            JTextField field = inputFields.get(i); // Gets the input at the current index
            JComboBox<String> comboBox = comboBoxes.get(i); // Gets the combo at the current index

            double value = parseDouble(field.getText()); // Converts text to double, handling any non-numeric input
            double multiplier = getMultiplier(comboBox.getSelectedItem().toString()); // Gets the multiplier for the combo

            total += value * multiplier; // Adds the value of the input * multiplier to the total
        }
        return total;
    }
    // Parses a string to a double, handling any non-numeric input
    private static double parseDouble(String text) {
        try {
            return Double.parseDouble(text); // Returns the parsed double if the input is a valid number
        } catch (NumberFormatException e) {
            return 0.0; // Returns 0.0 if the input is not a valid number
        }
    }
    // Gets the multiplier for a given period
    private static double getMultiplier(String period) {
        switch (period) { // Switch statement to handle different periods
            case "Month":
                return 12; // Multiplies by 12 for monthly values
            case "Week":
                return 4.333 * 12; // Multiplies by (approximately 4.333 weeks in a month) * 12 for weekly values
            case "Year":
                return 1; // No multiplication for yearly values
            default:
                return 0; // Default case to handle unexpected inputs
        }
    }
}