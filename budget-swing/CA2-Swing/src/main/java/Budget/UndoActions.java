package Budget;

// import statements
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.util.stream.Collectors;

// Class to handle the undoing of actions
public class UndoActions {
    private List<Action> actions; // Stores the list of actions
    private BudgetBase budgetBase; // Stores the budget base

    // Constructor to initialise the list of actions and the budget base
    public UndoActions(BudgetBase budgetBase) {
        this.actions = new ArrayList<>(); // Initialise the list of actions
        this.budgetBase = budgetBase; // Initialise the budget base
    }

    // Adds an action to the list of actions
    public void addAction(ActionType actionType, boolean isIncome, List<JTextField> inputs,
            List<JComboBox<String>> combos) {
        // Store the current values as strings
        List<String> inputValues = inputs.stream().map(JTextField::getText).collect(Collectors.toList());
        List<String> comboValues = combos.stream().map(combo -> (String) combo.getSelectedItem())
                .collect(Collectors.toList());

        this.actions.add(new Action(actionType, isIncome, inputValues, comboValues));// Add the action to the list
    }

    // Undoes the last action
    public void undoLastAction() {
        if (!actions.isEmpty()) {
            Action lastAction = actions.remove(actions.size() - 1); // Pops the last action from the list
            List<String> inputValues = lastAction.getInputValues(); // Gets the input values from the action
            List<String> comboValues = lastAction.getComboValues(); // Gets the combo values from the action
            List<JTextField> currentInputs = budgetBase.getInputs(lastAction.getIsIncome()); // Gets the current inputs
            List<JComboBox<String>> currentCombos = budgetBase.getCombos(lastAction.getIsIncome()); // Gets the current
                                                                                                    // combos

            switch (lastAction.getActionType()) { // Switch statement to handle different action types
                case ADD_INPUT_AND_DROPDOWN:
                    budgetBase.removeInputAndDropdown(lastAction.getIsIncome(), true); // Removes the last input and
                                                                                       // combo
                    break;
                case REMOVE_INPUT_AND_DROPDOWN:
                    // Gets the row and col to add the input and combo
                    int row = inputValues.size() + 1;
                    int col = lastAction.getIsIncome() ? 0 : 3;
                    budgetBase.addInputAndDropdown(row, col, lastAction.getIsIncome(), true); // Adds the input and
                                                                                              // combo at the row and
                                                                                              // col
                    // Restore the state of the last input and combo
                    if (!currentInputs.isEmpty() && !currentCombos.isEmpty()) { // Check that there are inputs and
                                                                                // combos to restore
                        JTextField lastCurrentInput = currentInputs.get(currentInputs.size() - 1); // Get the last
                                                                                                   // input
                        JComboBox<String> lastCurrentCombo = currentCombos.get(currentCombos.size() - 1); // Get the
                                                                                                          // last
                                                                                                          // combo
                        lastCurrentInput.setText(inputValues.get(inputValues.size() - 1)); // Set the last positioned
                                                                                           // current input to the
                                                                                           // last input value stored
                        lastCurrentCombo.setSelectedItem(comboValues.get(comboValues.size() - 1)); // Set the last
                                                                                                   // positioned current
                                                                                                   // combo to the
                                                                                                   // last combo value
                                                                                                   // stored
                    }
                    break;
                case CHANGE_INPUT_BOXES:
                    for (int i = 0; i < inputValues.size() && i < currentInputs.size(); i++) { // Loops through each
                                                                                               // input
                        currentInputs.get(i).setText(inputValues.get(i)); // Sets the input text to the stored value
                    }
                    break;
                case CHANGE_COMBO_VALUES:
                    for (int i = 0; i < comboValues.size() && i < currentCombos.size(); i++) { // Loops through each
                                                                                               // combo
                        currentCombos.get(i).setSelectedItem(comboValues.get(i)); // Sets the combo selected item to the
                                                                                  // stored value
                    }
                    break;
            }
        }
    }
}

// Enum to store the different action types
enum ActionType {
    ADD_INPUT_AND_DROPDOWN,
    REMOVE_INPUT_AND_DROPDOWN,
    CHANGE_INPUT_BOXES,
    CHANGE_COMBO_VALUES
}

// Class to store an action
class Action {
    private ActionType actionType; // Stores the action type
    private boolean isIncome; // Stores whether the action is for income or expenses
    private List<String> inputValues; // Stores current state of input values
    private List<String> comboValues; // Stores current state of combo values

    // Constructor to initialise the action
    public Action(ActionType actionType, boolean isIncome, List<String> inputValues, List<String> comboValues) {
        this.actionType = actionType; // Initialise the action type
        this.isIncome = isIncome; // Initialise whether the action is for income or expenses
        this.inputValues = new ArrayList<>(inputValues); // Initialise the input values
        this.comboValues = new ArrayList<>(comboValues); // Initialise the combo values
    }

    // Returns a string representation of the action for debugging
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Action Type: ").append(actionType);
        sb.append(", Is Income: ").append(isIncome);
        sb.append(", Input Values: ").append(String.join(", ", inputValues));
        sb.append(", Combo Values: ").append(String.join(", ", comboValues));
        return sb.toString();
    }

    // Getters for the action type
    public ActionType getActionType() {
        return actionType;
    }

    // Getters for whether the action is for income or expenses
    public boolean getIsIncome() {
        return isIncome;
    }

    // Getters for the input values
    public List<String> getInputValues() {
        return new ArrayList<>(inputValues);
    }

    // Getters for the combo values
    public List<String> getComboValues() {
        return new ArrayList<>(comboValues);
    }
}
