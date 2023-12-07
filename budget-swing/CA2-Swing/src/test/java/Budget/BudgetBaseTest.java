package Budget;

// import statements
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import javax.swing.*;
import java.awt.*;

/**
 * Unit test for swing application.
 * We can't test the GUI so we have to test methods that do the work.
 * Add more tests for any manipulations of values in your application.
 * Rewrite methods so that they are not void, but return values, and can be
 * tested.
 */
public class BudgetBaseTest {

    private BudgetBase bb;

    @BeforeEach
    public void setUp() {
        JFrame frame = new JFrame();
        bb = new BudgetBase(frame);
    }

    /**
     * Basic System tests and Extension tests
     */
    @Test
    public void testNoIncomeOrExpenses() {
        // No data added for income and expenses
        // Expected: All values should be zero and color black
        assertResultsHandleUpdateEvent(0.0, 0.0, 0.0, Color.BLACK);
    }

    @Test
    public void testPositiveNetIncome() {
        // Three different incomes and expenses, with varied periods
        setInputAndCombos(new String[] { "3000", "2500", "2000" }, new String[] { "Year", "Month", "Week" },
                new String[] { "1500", "1000", "500" }, new String[] { "Month", "Month", "Month" });
        // Expected: Should calculate the net income correctly
        assertResultsHandleUpdateEvent(136992, 36000, 100992, Color.BLACK);
    }

    @Test
    public void testNegativeNetIncome() {
        // Inputs with lower income and higher expenses
        setInputAndCombos(new String[] { "1000", "800", "600" }, new String[] { "Month", "Month", "Month" },
                new String[] { "2000", "2500", "3000" }, new String[] { "Month", "Month", "Month" });
        // Expected: Expenses are higher than income so net income should be negative
        assertResultsHandleUpdateEvent(28800, 90000, -61200, Color.RED);
    }

    @Test
    public void testZeroNetIncome() {
        // Inputs and Combos with same values
        setInputAndCombos(new String[] { "2000", "1000", "500" }, new String[] { "Month", "Month", "Month" },
                new String[] { "2000", "1000", "500" }, new String[] { "Month", "Month", "Month" });
        // Expected: Should calculate the net income correctly
        assertResultsHandleUpdateEvent(42000.0, 42000.0, 0.0, Color.BLACK);
    }

    @Test
    public void testHighNetIncome() {
        // Inputs with high values
        setInputAndCombos(new String[] { "10000", "8000", "6000" }, new String[] { "Month", "Month", "Month" },
                new String[] { "1000", "800", "600" }, new String[] { "Month", "Month", "Month" });
        // Expected: Should work as normal
        assertResultsHandleUpdateEvent(288000.0, 28800.0, 259200.0, Color.BLACK);
    }

    @Test
    public void testInvalidIncomeFormat() {
        // Inputs with invalid values
        setInputAndCombos(new String[] { "invalid", "2000", "3000" }, new String[] { "Month", "Month", "Month" },
                new String[] { "500", "400", "300" }, new String[] { "Month", "Month", "Month" });
        // Expected: Should handle invalid values as 0 and calculate the rest of the
        // values
        assertResultsHandleUpdateEvent(60000.0, 14400.0, 45600.0, Color.BLACK);
    }

    @Test
    public void testNegativeExpenses() {
        // Inputs with negative values
        setInputAndCombos(new String[] { "1000", "1500", "2000" }, new String[] { "Month", "Month", "Month" },
                new String[] { "-500", "-700", "-800" }, new String[] { "Month", "Month", "Month" });
        // Expected: Since the expenses are negative the net income should be higher
        // than the income
        assertResultsHandleUpdateEvent(54000.0, -24000.0, 78000.0, Color.BLACK);
    }

    @Test
    public void testNonExistentTimePeriod() {
        // Inputs with non-existent or unsupported time periods
        setInputAndCombos(new String[] { "1000", "1500", "2000" }, new String[] { "Decade", "Decade", "Decade" },
                new String[] { "500", "700", "800" }, new String[] { "Century", "Century", "Century" });
        // Expected: Since the time periods are non existent the values should be zero
        assertResultsHandleUpdateEvent(0.0, 0.0, 0.0, Color.BLACK);
    }

    @Test
    public void testInputValidationValid() {
        // Test with valid input
        Boolean result = bb.isValidNumber("123.12");
        // Expected: Should return true as the input is valid
        assertEquals(true, result, "No error message should be displayed for valid input");
    }

    @Test
    public void testInputValidationInvalid() {
        // Test with invalid input as should be int
        Boolean result = bb.isValidNumber("invalid");
        // Expected: Should return false as the input is invalid
        assertEquals(false, result, "Error message should be displayed for invalid input");
    }

    /**
     * Helper Methods
     */
    private void setInputAndCombos(String[] incomeAmounts, String[] incomePeriods,
            String[] expenseAmounts, String[] expensePeriods) {
        for (int i = 0; i < incomeAmounts.length; i++) {
            JTextField incomeField = new JTextField(incomeAmounts[i]);
            JComboBox<String> incomeCombo = new JComboBox<>(new String[] { incomePeriods[i] });
            bb.getInputs(true).add(incomeField);
            bb.getCombos(true).add(incomeCombo);
        }

        for (int i = 0; i < expenseAmounts.length; i++) {
            JTextField expenseField = new JTextField(expenseAmounts[i]);
            JComboBox<String> expenseCombo = new JComboBox<>(new String[] { expensePeriods[i] });
            bb.getInputs(false).add(expenseField);
            bb.getCombos(false).add(expenseCombo);
        }
    }

    // Helper method to assert the results of the handleUpdateEvent method which is
    // called when focus of input is lost or combo boxes are focused or changed and
    // when the input an combo pair are added or removed. Thus, this is testing the
    // functionality for basic system tests and extension tests at the same time.
    // Also focus events is to do more with end-end testing and not unit testing.
    private void assertResultsHandleUpdateEvent(double expectedIncome, double expectedExpenses,
            double expectedNetIncome,
            Color expectedColor) {
        Object[] results = bb.handleUpdateEvent();

        assertEquals(expectedIncome, (Double) results[0], 0.01, "Total Income Mismatch");
        assertEquals(expectedExpenses, (Double) results[1], 0.01, "Total Expenses Mismatch");
        assertEquals(expectedNetIncome, (Double) results[2], 0.01, "Net Income Mismatch");
        assertEquals(expectedColor, results[3], "Net Income Color Mismatch");
    }
}
