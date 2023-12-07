package Budget;

// import statements
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import javax.swing.*;

public class UndoActionsTest {

    private BudgetBase bb;
    private UndoActions undoActions;

    // Before each test, create a new budget base and undo actions
    @BeforeEach
    public void setUp() {
        bb = new BudgetBase(new JFrame());
    }

    /**
     * Undo Actions Tests
     */

    // Test that the undo actions list has action after action is added
    @Test
    public void testAddAction() {
        JTextField textField = new JTextField("100"); // Create a new input field
        JComboBox<String> comboBox = new JComboBox<>(new String[] { "Month" }); // Create a new combo box
        List<JTextField> textFields = new ArrayList<>(List.of(textField)); // Create a list of inputs
        List<JComboBox<String>> comboBoxes = new ArrayList<>(List.of(comboBox)); // Create a list of combos

        undoActions.addAction(ActionType.ADD_INPUT_AND_DROPDOWN, true, textFields, comboBoxes); // Add the action
        // Expected: The undo actions list should have one action
        assertEquals(1, undoActions.getActions().size()); // Check that the action was added
    }

    // Test that the input and combo are removed after undoing the adding
    @Test
    public void testUndoLastActionAfterAdd() {
        bb.addInputAndDropdown(3, 0, true, false); // Add an input and combo
        undoActions.undoLastAction(); // Undo the action
        // Expected: The input and combo should be empty
        assertTrue(bb.getInputs(true).isEmpty());
        assertTrue(bb.getCombos(true).isEmpty());
    }

    // Test that the input and combo are added after undoing the removing
    @Test
    public void testUndoLastActionAfterRemove() {
        bb.addInputAndDropdown(2, 0, true, false); // Add an input and combo
        JTextField incomeField = new JTextField("10.00");
        JComboBox<String> incomeCombo = new JComboBox<>(new String[] { "Month" });
        bb.getInputs(true).add(incomeField);
        bb.getCombos(true).add(incomeCombo);
        bb.removeInputAndDropdown(true, false); // Remove the input and combo
        undoActions.undoLastAction(); // Undo the action

        // Expected: The input with same value should be added back and should not be empty
        assertEquals("10.00", bb.getInputs(true).get(0).getText()); 
        assertFalse(bb.getInputs(true).isEmpty());
    }

    // Test that the input reverts to original value after change
    @Test
    public void testUndoAfterChangingInputValue() {
        JTextField inputField = new JTextField("0.00"); // Create a new input field
        bb.getInputs(true).add(inputField); // Add the input field
        inputField.setText("100"); // Change the input field
        undoActions.undoLastAction(); // Undo the action
        // Expected: The input field should revert to original value
        assertEquals("0.00", inputField.getText()); 
    }

    // Test that the combo reverts to original value after change
    @Test
    public void testUndoAfterChangingComboBoxSelection() {
        JComboBox<String> comboBox = new JComboBox<>(new String[] { "Month", "Year" });
        bb.getCombos(true).add(comboBox);
        comboBox.setSelectedItem("Year");
        undoActions.undoLastAction();
        assertEquals("Month", comboBox.getSelectedItem());
    }

    // Test that there is no input and combo after undoing the adding
    @Test
    public void testUndoWithoutAnyActions() {
        undoActions.undoLastAction();
        assertTrue(bb.getInputs(true).isEmpty());
        assertTrue(bb.getCombos(true).isEmpty());
    }

    // Test no error after undoing when there is no action to undo
    @Test
    public void testUndoBeyondActionCount() {
        bb.addInputAndDropdown(2, 0, true, false);
        undoActions.undoLastAction();
        undoActions.undoLastAction(); // Extra undo
        assertTrue(bb.getInputs(true).isEmpty());
        assertTrue(bb.getCombos(true).isEmpty());
    }

    // Test that you can undo invalid statement
    @Test
    public void testUndoWithInvalidInput() {
        JTextField inputField = new JTextField("invalid");
        bb.getInputs(true).add(inputField);
        undoActions.undoLastAction();
        assertEquals("0.00", inputField.getText());
    }

    // Test you can undo after same actions
    @Test
    public void testUndoAfterMultipleSimilarActions() {
        bb.addInputAndDropdown(2, 0, true, false);
        bb.addInputAndDropdown(3, 0, true, false);
        undoActions.undoLastAction();
        assertEquals(1, bb.getInputs(true).size());
        assertEquals(1, bb.getCombos(true).size());
    }

    // Test you can undo after mixed actions
    @Test
    public void testUndoAfterMixedActions() {
        bb.addInputAndDropdown(2, 0, true, false);
        JTextField inputField = new JTextField("0.00");
        bb.getInputs(true).add(inputField);
        inputField.setText("100");
        bb.removeInputAndDropdown(true, false);
        undoActions.undoLastAction();
        assertEquals(1, bb.getInputs(true).size());
        assertEquals("100", inputField.getText());
    }
}
