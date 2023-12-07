// Your submission must be a maven project, and must be submitted via Codio, and run in Codio
// run in Codio 
// To see GUI, run with java and select Box Url from Codio top line menu
package Budget;

// import statements
import javax.swing.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
import java.awt.*;

// class definition
public class BudgetBase extends JPanel { // based on Swing JPanel

    JFrame topLevelFrame; // top-level JFrame
    GridBagConstraints layoutConstraints = new GridBagConstraints(); // used to control layout

    // widgets which may have listeners and/or values
    private JButton exitButton; // Exit button
    private JButton undoButton; // Undo button
    private JTextField totalIncomeField; // Total Income field
    private JTextField totalExpensesField; // Total Expenses field
    private JTextField netIncomeField; // Net Income field
    private List<JTextField> incomeInputFields = new ArrayList<>(); // List of input fields for income
    private List<JComboBox<String>> incomeComboBoxes = new ArrayList<>(); // List of combo boxes for income
    private List<JTextField> spendingInputFields = new ArrayList<>(); // List of input fields for spending
    private List<JComboBox<String>> spendingComboBoxes = new ArrayList<>(); // List of combo boxes for spending
    private UndoActions undoActions; // Undo actions
    private JPanel incomePanel; // Panel to add or remove income box
    private JPanel spendingPanel; // Panel to add or remove spending box

    // Constructor - create UI
    public BudgetBase(JFrame frame) {
        topLevelFrame = frame; // keep track of top-level frame
        setLayout(new GridBagLayout()); // Using grid bag layout
        initComponents(); // initalise components
        this.undoActions = new UndoActions(this); // Initialise undo actions
    }

    // Onitialise componenents
    private void initComponents() {
        // Row 0 Col 0 - Income fields label
        JLabel incomeTitleLabel = new JLabel("INCOME FIELDS");
        addComponent(incomeTitleLabel, 0, 0); // Add component to (0,0)
        // Row 1 Col 0 - Amount label
        JLabel incomeAmountLabel = new JLabel("$ Amount");
        addComponent(incomeAmountLabel, 1, 0); // Add component to (1,0)
        // Row 1 Col 1 - Time Period label
        JLabel incomeTimeLabel = new JLabel("Time Period");
        addComponent(incomeTimeLabel, 1, 1); // Add component to (1,1)
        addInputAndDropdown(2, 0, true); // This will add input field in (2,0) and dropdown for time in (2,1)

        // Empty space as will be compacted without
        addComponent(Box.createHorizontalStrut(100), 0, 2);

        // Row 0 Col 3 - Spending fields label
        JLabel spendingTitleLabel = new JLabel("SPENDING FIELDS");
        addComponent(spendingTitleLabel, 0, 3); // Add component to (0,3)
        // Row 1 Col 3 - Amount label
        JLabel spendingAmountLabel = new JLabel("$ Amount");
        addComponent(spendingAmountLabel, 1, 3); // Add component to (1,3)
        // Row 1 Col 4 - Time Period label
        JLabel spendingTimeLabel = new JLabel("Time Period");
        addComponent(spendingTimeLabel, 1, 4); // Add component to (1,4)
        addInputAndDropdown(2, 3, false); // This will add input field in (2,3) and dropdown for time in (2,4)

        // Empty space as will be compacted without
        addComponent(Box.createHorizontalStrut(100), 0, 5);

        // Row 0 Col 6 - Yearly label
        JLabel yearlyLabel = new JLabel("↓Yearly↓");
        addComponent(yearlyLabel, 0, 6); // Add component to (0,6)

        // Row 1 Col 6 - Total label
        JLabel totalIncomeLabel = new JLabel("Total Income");
        addComponent(totalIncomeLabel, 1, 6); // Add component to (1,6)
        // Row 1 Col 7 - Total Income Field (displays users total income for the year)
        totalIncomeField = new JTextField("0.00", 10); // 0.00 initial value with 10 columns
        totalIncomeField.setHorizontalAlignment(JTextField.RIGHT); // number is at right end of field
        totalIncomeField.setEditable(false); // user cannot directly edit this field (ie, it is read-only)
        addComponent(totalIncomeField, 1, 7); // Add component to (1,7)

        // Row 3 Col 6 - Total Expenses label
        JLabel totalExpensesLabel = new JLabel("Total Expenses");
        addComponent(totalExpensesLabel, 3, 6);
        // Row 3 Col 7 - Total Expenses Field (displays users total expenses for the
        // year)
        totalExpensesField = new JTextField("0.00", 10); // 0.00 initial value with 10 columns
        totalExpensesField.setHorizontalAlignment(JTextField.RIGHT); // number is at right end of field
        totalExpensesField.setEditable(false); // user cannot directly edit this field (ie, it is read-only)
        addComponent(totalExpensesField, 3, 7); // Add component to (3,7)

        // Row 4 Col 7 - Create Vertical space to seperate fields
        addComponent(Box.createVerticalStrut(25), 4, 7); // Empty space as will be compacted without

        // Row 5 Col 6 - Net Income label
        JLabel netIncomeLabel = new JLabel("Net Income");
        addComponent(netIncomeLabel, 5, 6); // Add component to (5,6)
        // Row 5 Col 7 - Net Income Field (displays users net income for the year)
        netIncomeField = new JTextField("0.00", 10); // 0.00 initial value with 10 columns
        netIncomeField.setHorizontalAlignment(JTextField.RIGHT); // number is at right end of field
        netIncomeField.setEditable(false); // user cannot directly edit this field (ie, it is read-only)
        addComponent(netIncomeField, 5, 7); // Add component to (5,7)

        // Row 6 Col 7 - Create Vertical space to seperate fields
        addComponent(Box.createVerticalStrut(25), 6, 7); // Empty space as will be compacted without

        // Row 7 Col 7 - Undo button
        undoButton = new JButton("Undo");
        addComponent(undoButton, 7, 7);

        // Row 8 Col 7 - Create Vertical space to seperate fields
        addComponent(Box.createVerticalStrut(25), 8, 7);

        // Row 9 Col 7 - Exit button
        exitButton = new JButton("Exit");
        addComponent(exitButton, 9, 7); // Add component to (9,7)

        // set up listeners
        initListeners();
    }

    // Set up event listeners
    private void initListeners() {
        // exitButton - exit program when pressed
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) { // When button is pressed -> action performed
                System.exit(0);
            }
        });

        // undoButton - undo last action when pressed
        undoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) { // When button is pressed -> action performed
                undoActions.undoLastAction(); // calls method to undo last action
                revalidateAndRepaint(); // Revalidate and repaint to update UI
            }
        });
    }

    // Add a component at specified row and column in UI. (0,0) is top-left corner
    private void addComponent(Component component, int gridrow, int gridcol, int width) {
        layoutConstraints.fill = GridBagConstraints.HORIZONTAL; // fill horizontally
        layoutConstraints.gridx = gridcol; // column to add to
        layoutConstraints.gridy = gridrow; // row to add to
        layoutConstraints.gridwidth = width; // number of columns to span
        add(component, layoutConstraints); // add component
    }

    // Overloading method to add component without needing to specify width
    private void addComponent(Component component, int gridrow, int gridcol) {
        addComponent(component, gridrow, gridcol, 1); // default width is 1
    }

    // Method that adds input and dropdown to UI given the row and column, wether it
    // is income or spending and wether it is from undo action
    void addInputAndDropdown(int row, int col, boolean isIncome, boolean isUndo) {
        // Remove the income or spending panel if it exists
        if (isIncome && this.incomePanel != null) {
            remove(this.incomePanel);
        } else if (this.spendingPanel != null) {
            remove(this.spendingPanel);
        }

        // Capture the correct state of input fields and combo boxes depending on if it
        // needs income or spending
        List<JTextField> inputFields = isIncome ? incomeInputFields : spendingInputFields;
        List<JComboBox<String>> comboBoxes = isIncome ? incomeComboBoxes : spendingComboBoxes;

        // Add action to undoActions unless it is initialisation or action being undone
        if (inputFields.size() > 0 && comboBoxes.size() > 0 && !isUndo) {
            undoActions.addAction(ActionType.ADD_INPUT_AND_DROPDOWN, isIncome, inputFields, comboBoxes);
        }

        // Add input field and combo box
        JTextField inputField = new JTextField("0.00", 10); // 0.00 initial value with 10 columns
        inputField.setHorizontalAlignment(JTextField.RIGHT); // number is at right end of field
        addComponent(inputField, row, col, 1); // Add component to (row,col)

        JComboBox<String> periodComboBox = new JComboBox<>(new String[] { "Month", "Week", "Year" }); // Create combo
                                                                                                      // box with
                                                                                                      // options
        addComponent(periodComboBox, row, col + 1, 1); // Add component to (row,col+1)

        // Add input field and combo box to correct list
        if (isIncome) {
            incomeInputFields.add(inputField); // Add input field to incomeInputFields
            incomeComboBoxes.add(periodComboBox); // Add combo box to incomeComboBoxes
        } else {
            spendingInputFields.add(inputField); // Add input field to spendingInputFields
            spendingComboBoxes.add(periodComboBox); // Add combo box to spendingComboBoxes
        }
        // Create error label next to input field that is hidden by default
        JLabel errorLabel = new JLabel("Invalid number"); // Create error label
        errorLabel.setForeground(Color.RED); // Set text to red
        errorLabel.setVisible(false); // Initially invisible
        addComponent(errorLabel, row, col + 2, 1); // Add component to (row,col+1)

        // Add focus listener to the input field
        inputField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                // When input field is focused send current state to undoActions
                undoActions.addAction(ActionType.CHANGE_INPUT_BOXES, isIncome, inputFields, comboBoxes);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (!isValidNumber(inputField.getText())) {
                    errorLabel.setVisible(true);
                } else {
                    errorLabel.setVisible(false);
                }
                revalidateAndRepaint();
            }
        });

        // Add focus listener to the combo box
        periodComboBox.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                // When Focus of combo box is gained send current state to undoActions
                undoActions.addAction(ActionType.CHANGE_COMBO_VALUES, isIncome, inputFields, comboBoxes);
            }
        });

        // Add event listner to trigger calculation when period is changed
        periodComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) { // When item state is changed
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    revalidateAndRepaint(); // Revalidate and repaint to update UI
                }
            }
        });

        // Add input and dropdown manager (Add or remove the last input field and combo)
        addInputAmountManager(row, col, isIncome);
    }

    // Overloading method to add input and dropdown without needing to declare
    // wether it is from undo, false undo by default
    void addInputAndDropdown(int row, int col, boolean isIncome) {
        addInputAndDropdown(row, col, isIncome, false);
    }

    // Method to add controls to add or remove inpuit and dropdown
    private void addInputAmountManager(int row, int col, boolean isIncome) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0)); // Create panel to hold buttons
        JButton addButton = new JButton("+"); // Create add button
        JButton removeButton = new JButton("-"); // Create remove button

        // Add buttons to panel
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);

        // Add panel to appropriate panel
        if (isIncome) {
            this.incomePanel = buttonPanel;
        } else {
            this.spendingPanel = buttonPanel;
        }

        addComponent(buttonPanel, row + 1, col, 2); // Add component to (row+1,col)

        // Add action listeners to add button to add input and dropdown
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { // When button is pressed -> action performed
                addInputAndDropdown(row + 1, col, isIncome); // Add input and dropdown
                revalidateAndRepaint(); // Revalidate and repaint to update UI
            }
        });
        // Add action listeners to remove button to remove input and dropdown
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { // When button is pressed -> action performed
                removeInputAndDropdown(isIncome); // Remove input and dropdown
                revalidateAndRepaint(); // Revalidate and repaint to update UI
            }
        });
    }

    // Method to remove input and dropdown from spending or income
    void removeInputAndDropdown(boolean isIncome, boolean isUndo) {
        // Capture the correct state of input fields and combo boxes depending on if it
        List<JTextField> inputFields = isIncome ? incomeInputFields : spendingInputFields;
        List<JComboBox<String>> comboBoxes = isIncome ? incomeComboBoxes : spendingComboBoxes;

        // Only remove if there is more than one input and dropdown
        if (inputFields.size() > 1 && comboBoxes.size() > 1) {
            if (!isUndo) { // Add action to undoActions unless it is action being undone
                undoActions.addAction(ActionType.REMOVE_INPUT_AND_DROPDOWN, isIncome, inputFields, comboBoxes);
            }
            // Get the last input field and combo box
            JTextField lastInputField = inputFields.remove(inputFields.size() - 1);
            JComboBox<String> lastComboBox = comboBoxes.remove(comboBoxes.size() - 1);
            // Remove the last input field and combo box
            remove(lastInputField);
            remove(lastComboBox);

            // Remove the appropriate input and dropdown manager
            if (isIncome) {
                remove(this.incomePanel);
            } else {
                remove(this.spendingPanel);
            }

            // Add input and dropdown manager back to UI
            int row = inputFields.size() + 1;
            int col = isIncome ? 0 : 3;
            addInputAmountManager(row, col, isIncome);
        }
    }

    // Overloading method to remove input and dropdown without needing to declare
    // wether it is from undo
    void removeInputAndDropdown(boolean isIncome) {
        removeInputAndDropdown(isIncome, false); // Default isUndo to false
    }

    // Method to revalidate and repaint to update UI
    private void revalidateAndRepaint() {
        handleUpdateEvent(); // Handle update event to recalculate totals
        topLevelFrame.revalidate(); // Revalidate top level frame
        topLevelFrame.repaint(); // Repaint top level frame
    }

    // Method to handle update event to recalculate totals
    public Object[] handleUpdateEvent() {
        double totalIncome = 0; // Initialise total income, double
        double totalExpenses = 0; // Initialise total expenses, double
        double netIncome = 0; // Initialise net income, double
        totalIncome = ValueCalculation.calculateTotalInputAndCombo(incomeInputFields, incomeComboBoxes); // Call method
                                                                                                         // to calculate
                                                                                                         // total income
                                                                                                         // using inputs
                                                                                                         // and combos
        totalExpenses = ValueCalculation.calculateTotalInputAndCombo(spendingInputFields, spendingComboBoxes); // Call
                                                                                                               // method
                                                                                                               // to
                                                                                                               // calculate
                                                                                                               // total
                                                                                                               // expenses
                                                                                                               // using
                                                                                                               // inputs
                                                                                                               // and
                                                                                                               // combos
        netIncome = totalIncome - totalExpenses; // Calculate net income
        totalIncomeField.setText(String.format("%.2f", totalIncome)); // Set total income field to total income
        totalExpensesField.setText(String.format("%.2f", totalExpenses)); // Set total expenses field to total expenses
        netIncomeField.setText(String.format("%.2f", netIncome)); // Set net income field to net income
        Color netIncomeColor = netIncome < 0 ? Color.RED : Color.BLACK; // Set net income field to red if net income is
                                                                        // negative else black
        netIncomeField.setForeground(netIncomeColor); // Set net income field text color

        return new Object[] { totalIncome, totalExpenses, netIncome, netIncomeColor };
    }

    // Method to check if input is a valid number
    boolean isValidNumber(String input) {
        try {
            Double.parseDouble(input); // Try parsing the input to a number
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // below is standard code to set up Swing
    private static void createAndShowGUI() {
        // Create and set up the window.
        JFrame topLevelFrame = new JFrame("Budget Calculator");
        topLevelFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        topLevelFrame.setSize(900, 900); // Set the size to 900px by 900px
        // Create and set up the content pane.
        BudgetBase newContentPane = new BudgetBase(topLevelFrame);
        newContentPane.setOpaque(true); // content panes must be opaque
        topLevelFrame.setContentPane(newContentPane);
        // Make frame focusable so when clicked unfocus inputs to allow for update
        topLevelFrame.setFocusable(true);
        topLevelFrame.addMouseListener(new MouseAdapter() { // Add mouse listener
            @Override
            public void mouseClicked(MouseEvent e) { // When mouse is clicked
                topLevelFrame.requestFocusInWindow(); // Request focus in window
            }
        });
        // Display the window.
        topLevelFrame.setVisible(true);
    }

    // Method to get inputs depending on if it is income or spending
    public List<JTextField> getInputs(boolean isIncome) {
        if (isIncome) {
            return incomeInputFields;
        } else {
            return spendingInputFields;
        }
    }

    // Method to get combos depending on if it is income or spending
    public List<JComboBox<String>> getCombos(boolean isIncome) {
        if (isIncome) {
            return incomeComboBoxes;
        } else {
            return spendingComboBoxes;
        }
    }

    // Method to get combos depending on if it is income or spending
    public UndoActions getUndoActions() {
        return undoActions;

    }

    // standard main class to set up Swing UI
    public static void main(String[] args) {
        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}