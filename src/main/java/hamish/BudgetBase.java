// base code for student budget assessment
// Students do not need to use this code in their assessment, fine to junk it and do something different!
//
// Your submission must be a maven project, and must be submitted via Codio, and run in Codio
//
// user can enter in wages and loans and calculate total income
//
// run in Codio 
// To see GUI, run with java and select Box Url from Codio top line menu
//
// Layout - Uses GridBag layout in a straightforward way, every component has a (column, row) position in the UI grid
// Not the prettiest layout, but relatively straightforward
// Students who use IntelliJ or Eclipse may want to use the UI designers in these IDEs , instead of GridBagLayout
package hamish;

// Swing imports
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.Stack;

// class definition
public class BudgetBase extends JPanel {    // based on Swing JPanel

    // high level UI stuff
    JFrame topLevelFrame;  // top-level JFrame
    GridBagConstraints layoutConstraints = new GridBagConstraints(); // used to control layout
    Stack<double[]> sheetHistory = new Stack<>();

    // widgets which may have listeners and/or values
    private JButton calculateButton;   // Calculate button
    private JButton exitButton;        // Exit button
    private JTextField wagesField;     // Wages text field
    private JTextField loansField;     // Loans text field
    private JTextField otherInField;     // Other income text field
    private JTextField totalIncomeField; // Total Income field
    private JTextField foodField;     // Food text field
    private JTextField rentField;     // Rent text field
    private JTextField otherOutField;     // Other outgoings text field
    private JTextField subsField;     // Subscriptions text field
    private JTextField totalOutgoingsField; // Total Outgoings field

    // constructor - create UI  (dont need to change this)
    public BudgetBase(JFrame frame) {
        topLevelFrame = frame; // keep track of top-level frame
        setLayout(new GridBagLayout());  // use GridBag layout
        initComponents();  // initalise components
    }

    // initialise componenents
    // Note that this method is quite long.  Can be shortened by putting Action Listener stuff in a separate method
    // will be generated automatically by IntelliJ, Eclipse, etc
    private void initComponents() { 

        // Top row (0) - "INCOME" label
        JLabel incomeLabel = new JLabel("INCOME");
        addComponent(incomeLabel, 0, 0);

        // Row 1 - Wages label followed by wages textbox
        JLabel wagesLabel = new JLabel("Wages");
        addComponent(wagesLabel, 1, 0);

        // set up text field for entering wages
        // Could create method to do below (since this is done several times)
        wagesField = new JTextField("", 10);   // blank initially, with 10 columns
        wagesField.setHorizontalAlignment(JTextField.RIGHT) ;    // number is at right end of field
        addComponent(wagesField, 1, 1);   

        // Row 2 - Loans label followed by loans textbox
        JLabel loansLabel = new JLabel("Loans");
        addComponent(loansLabel, 2, 0);

        // set up text box for entering loans
        loansField = new JTextField("", 10);   // blank initially, with 10 columns
        loansField.setHorizontalAlignment(JTextField.RIGHT) ;    // number is at right end of field
        addComponent(loansField, 2, 1); 

        // Row 3 - Other income label followed by textbox
        JLabel otherInLabel = new JLabel("Other");
        addComponent(otherInLabel, 3, 0);

        // set up text box for entering other income
        otherInField = new JTextField("", 10);
        otherInField.setHorizontalAlignment(JTextField.RIGHT);
        addComponent(otherInField, 3, 1);

        // Row 4 - "OUTGOINGS" label
        JLabel outgoigsLabel = new JLabel("OUTGOINGS");
        addComponent(outgoigsLabel, 4, 0);

        // Row 5 - Food label followed by textbox
        JLabel foodLabel = new JLabel("Food");
        addComponent(foodLabel, 5, 0);

        // set up text box for entering food spending
        foodField = new JTextField("", 10);
        foodField.setHorizontalAlignment(JTextField.RIGHT);
        addComponent(foodField, 5, 1);
                
        // Row 6 - 'Rent' label followed by textbox
        JLabel rentLabel = new JLabel("Rent");
        addComponent(rentLabel, 6, 0);

        // set up text box for entering rent spending
        rentField = new JTextField("", 10);
        rentField.setHorizontalAlignment(JTextField.RIGHT);
        addComponent(rentField, 6, 1);
        
        // Row 7 - 'Subscriptions' label followed by textbox
        JLabel subsLabel = new JLabel("Subscriprions");
        addComponent(subsLabel, 7, 0);

        // set up text box for entering subscription spending
        subsField = new JTextField("", 10);
        subsField.setHorizontalAlignment(JTextField.RIGHT);
        addComponent(subsField, 7, 1);
        
        // Row 8 - Other outgoing label followed by textbox
        JLabel otherOutLabel = new JLabel("Other");
        addComponent(otherOutLabel, 8, 0);

        // set up text box for entering other income
        otherOutField = new JTextField("", 10);
        otherOutField.setHorizontalAlignment(JTextField.RIGHT);
        addComponent(otherOutField, 8, 1);
        
        // Row 9 - Total Income label followed by total income field
        JLabel totalIncomeLabel = new JLabel("Total Income");
        addComponent(totalIncomeLabel, 9, 0);

        // set up text box for displaying total income.  Users cam view, but cannot directly edit it
        totalIncomeField = new JTextField("0", 10);   // 0 initially, with 10 columns
        totalIncomeField.setHorizontalAlignment(JTextField.RIGHT) ;    // number is at right end of field
        totalIncomeField.setEditable(false);    // user cannot directly edit this field (ie, it is read-only)
        addComponent(totalIncomeField, 9, 1);  

        // Row 10 - Calculate Button
        calculateButton = new JButton("Calculate");
        addComponent(calculateButton, 10, 0);  

        // Row 11 - Exit Button
        exitButton = new JButton("Exit");
        addComponent(exitButton, 11, 0);  

        // set up  listeners (in a spearate method)
        initListeners();
    }

    // set up listeners
    // initially just for buttons, can add listeners for text fields
    private void initListeners() {

        // exitButton - exit program when pressed
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // calculateButton - call calculateTotalIncome() when pressed
        calculateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calculateTotalIncome();
            }
        });

        // wagesField - update the sheet
        wagesField.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                updateSheet();
            }
            public void focusLost(FocusEvent e) {
                updateSheet();
            }
        });

    }

    // add a component at specified row and column in UI.  (0,0) is top-left corner
    private void addComponent(Component component, int gridrow, int gridcol) {
        layoutConstraints.fill = GridBagConstraints.HORIZONTAL;   // always use horixontsl filll
        layoutConstraints.gridx = gridcol;
        layoutConstraints.gridy = gridrow;
        add(component, layoutConstraints);

    }

    // update totalIncomeField (eg, when Calculate is pressed)
    // use double to hold numbers, so user can type fractional amounts such as 134.50
    public double calculateTotalIncome() {

        // get values from income text fields.  valie is NaN if an error occurs
        double wages = getTextFieldValue(wagesField);
        double loans = getTextFieldValue(loansField);
        double otherIn = getTextFieldValue(otherInField);
        double food = getTextFieldValue(foodField);
        double rent = getTextFieldValue(rentField);
        double subscriptions = getTextFieldValue(subsField);
        double otherOut = getTextFieldValue(otherOutField);

        // clear total field and return if any value is NaN (error)
        if (Double.isNaN(wages) || Double.isNaN(loans)) {
            totalIncomeField.setText("");  // clear total income field
            wages = 0.0;
            return wages;   // exit method and do nothing
        }

        // otherwise calculate total income and update text field
        double totalIncome = wages + loans + otherIn - (food + rent + subscriptions + otherOut);
        totalIncomeField.setText(String.format("%.2f",totalIncome));  // format with 2 digits after the .
        return totalIncome;
    }

    // update totalIncomeField (eg, when Calculate is pressed)
    // use double to hold numbers, so user can type fractional amounts such as 134.50
    public double updateSheet() {

        // get values from income text fields.  valie is NaN if an error occurs
        double wages = getTextFieldValue(wagesField);
        double loans = getTextFieldValue(loansField);
        double otherIn = getTextFieldValue(otherInField);
        double food = getTextFieldValue(foodField);
        double rent = getTextFieldValue(rentField);
        double subscriptions = getTextFieldValue(subsField);
        double otherOut = getTextFieldValue(otherOutField);

        // clear total field and return if any value is NaN (error)
        if (Double.isNaN(wages) || Double.isNaN(loans)) {
            totalIncomeField.setText("");  // clear total income field
            wages = 0.0;
            return wages;   // exit method and do nothing
        }

        double[] sheetState = {wages, loans, otherIn, food, rent, subscriptions, otherOut};  // Create a snapshot of the board
        sheetHistory.push(sheetState);  // Push the snapshot of the board on to the stack

        // otherwise calculate total income and update text field
        double totalIncome = wages + loans + otherIn - (food + rent + subscriptions + otherOut);
        totalIncomeField.setText(String.format("%.2f",totalIncome));  // format with 2 digits after the .
        return totalIncome;
    }

    private void undoAction() {
        
    }

    // return the value if a text field as a double
    // --return 0 if field is blank
    // --return NaN if field is not a number
    private double getTextFieldValue(JTextField field) {

        // get value as String from field
        String fieldString = field.getText();  // get text from text field

        if (fieldString.isBlank()) {   // if text field is blank, return 0
            return 0;
        }

        else {  // if text field is not blank, parse it into a double
            try {
                return Double.parseDouble(fieldString);  // parse field number into a double
             } catch (java.lang.NumberFormatException ex) {  // catch invalid number exception
                JOptionPane.showMessageDialog(topLevelFrame, "Please enter a valid number");  // show error message
                return Double.NaN;  // return NaN to show that field is not a number
            }
        }
    }


// below is standard code to set up Swing, which students shouldnt need to edit much
    // standard mathod to show UI
    private static void createAndShowGUI() {
 
        //Create and set up the window.
        JFrame frame = new JFrame("Budget Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Create and set up the content pane.
        BudgetBase newContentPane = new BudgetBase(frame);
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
 
    // standard main class to set up Swing UI
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(); 
            }
        });
    }


}