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
import java.util.Map;
import java.util.Stack;
import java.util.Arrays;

// class definition
public class BudgetBase extends JPanel {    // based on Swing JPanel

    // high level UI stuff
    JFrame topLevelFrame;  // top-level JFrame
    GridBagConstraints layoutConstraints = new GridBagConstraints(); // used to control layout
   
    // 
    boolean undoIsActive = false;
    public Stack<double[]> sheetHistory = new Stack<>();
    public Stack<String[]> periodsHistory = new Stack<>();
    String[] periods = {"Year", "Month", "Week"};
    Map<String, Integer> periodsMap = Map.of(
        "Year", 1,
        "Month", 12,
        "Week", 52);

    // widgets which may have listeners and/or values
    private JButton calculateButton;   // Calculate button
    private JButton undoButton;        // Undo button
    private JButton exitButton;        // Exit button
    private JTextField wagesField;     // Wages text field
    private JTextField loansField;     // Loans text field
    private JTextField otherInField;     // Other income text field
    private JTextField foodField;     // Food text field
    private JTextField rentField;     // Rent text field
    private JTextField otherOutField;     // Other outgoings text field
    private JTextField subsField;     // Subscriptions text field
    private JTextField totalIncomeField; // Total Outgoings field
    private JTextField totalOutgoingsField; // Total Outgoings field
    private JTextField netIncomeField; // Total Outgoings field
    private JComboBox<String> wageCombo;
    private JComboBox<String> loanCombo;
    private JComboBox<String> otherInCombo;
    private JComboBox<String> foodCombo;
    private JComboBox<String> rentCombo;
    private JComboBox<String> subsCombo;
    private JComboBox<String> otherOutCombo;


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
        wagesField = new JTextField("0.00", 10);   // blank initially, with 10 columns
        wagesField.setHorizontalAlignment(JTextField.RIGHT) ;    // number is at right end of field
        addComponent(wagesField, 1, 1);  

        // Row 2 - Loans label followed by loans textbox
        JLabel loansLabel = new JLabel("Loans");
        addComponent(loansLabel, 2, 0);

        // set up text box for entering loans
        loansField = new JTextField("0.00", 10);   // blank initially, with 10 columns
        loansField.setHorizontalAlignment(JTextField.RIGHT) ;    // number is at right end of field
        addComponent(loansField, 2, 1); 

        // Row 3 - Other income label followed by textbox
        JLabel otherInLabel = new JLabel("Other");
        addComponent(otherInLabel, 3, 0);

        // set up text box for entering other income
        otherInField = new JTextField("0.00", 10);
        otherInField.setHorizontalAlignment(JTextField.RIGHT);
        addComponent(otherInField, 3, 1);

        // Row 4 - "OUTGOINGS" label
        JLabel outgoigsLabel = new JLabel("OUTGOINGS");
        addComponent(outgoigsLabel, 4, 0);

        // Row 5 - Food label followed by textbox
        JLabel foodLabel = new JLabel("Food");
        addComponent(foodLabel, 5, 0);

        // set up text box for entering food spending
        foodField = new JTextField("0.00", 10);
        foodField.setHorizontalAlignment(JTextField.RIGHT);
        addComponent(foodField, 5, 1);
                
        // Row 6 - 'Rent' label followed by textbox
        JLabel rentLabel = new JLabel("Rent");
        addComponent(rentLabel, 6, 0);

        // set up text box for entering rent spending
        rentField = new JTextField("0.00", 10);
        rentField.setHorizontalAlignment(JTextField.RIGHT);
        addComponent(rentField, 6, 1);
        
        // Row 7 - 'Subscriptions' label followed by textbox
        JLabel subsLabel = new JLabel("Subscriprions");
        addComponent(subsLabel, 7, 0);

        // set up text box for entering subscription spending
        subsField = new JTextField("0.00", 10);
        subsField.setHorizontalAlignment(JTextField.RIGHT);
        addComponent(subsField, 7, 1);
        
        // Row 8 - Other outgoing label followed by textbox
        JLabel otherOutLabel = new JLabel("Other");
        addComponent(otherOutLabel, 8, 0);

        // set up text box for entering other outgoings
        otherOutField = new JTextField("0.00", 10);
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

        // Row 10 - total outgoings label followed by textbox
        JLabel totalOutgoingsLabel = new JLabel("Total Outgoings");
        addComponent(totalOutgoingsLabel, 10, 0);

        // set up text box for displaying total outgoings.  Users cam view, but cannot directly edit it
        totalOutgoingsField = new JTextField("0", 10);   // 0 initially, with 10 columns
        totalOutgoingsField.setHorizontalAlignment(JTextField.RIGHT) ;    // number is at right end of field
        totalOutgoingsField.setEditable(false);    // user cannot directly edit this field (ie, it is read-only)
        addComponent(totalOutgoingsField, 10, 1);  

        // Row 11 - net income label followed by textbox
        JLabel netIncomeLabel = new JLabel("Net Income");
        addComponent(netIncomeLabel, 11, 0);

        // set up text box for displaying total outgoings.  Users cam view, but cannot directly edit it
        netIncomeField = new JTextField("0", 10);   // 0 initially, with 10 columns
        netIncomeField.setHorizontalAlignment(JTextField.RIGHT) ;    // number is at right end of field
        netIncomeField.setEditable(false);    // user cannot directly edit this field (ie, it is read-only)
        addComponent(netIncomeField, 11, 1);  

        // Row 12 - Calculate and Undo Buttons
        calculateButton = new JButton("Calculate");
        addComponent(calculateButton, 12, 0);  
        undoButton = new JButton("Undo");
        addComponent(undoButton, 12, 1);  

        // Row 13 - Exit Button
        exitButton = new JButton("Exit");
        addComponent(exitButton, 13, 0);  
        
        // set up combo boxes for period of each field
        wageCombo = createComboBox(periods, 1, 2);
        loanCombo = createComboBox(periods, 2, 2);
        otherInCombo = createComboBox(periods, 3, 2);
        foodCombo = createComboBox(periods, 5, 2);
        rentCombo = createComboBox(periods, 6, 2);
        subsCombo = createComboBox(periods, 7, 2);
        otherOutCombo = createComboBox(periods, 8, 2);

        // set first state in history to defaults
        sheetHistory.push(new double[] {0, 0, 0, 0, 0, 0, 0});
        periodsHistory.push(new String[] {"Year", "Year", "Year", "Year", "Year", "Year", "Year"});
        // periodsHistory.push(new Object[] {wageCombo.getSelectedItem(), loanCombo.getSelectedItem(), otherInCombo.getSelectedItem(), foodCombo.getSelectedItem(), rentCombo.getSelectedItem(), subsCombo.getSelectedItem(), otherOutCombo.getSelectedItem()});

        System.out.println(Arrays.toString(periodsHistory.peek()));

        // set up  listeners (in a spearate method)
        initListeners();
    }


    // initialise combo box
    private JComboBox<String> createComboBox(String[] items, int gridx, int gridy) {
        JComboBox<String> comboBox = new JComboBox<>(items); 
        addComponent(comboBox, gridx, gridy);                
        return comboBox;
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
                calculateAllTotals();
            }
        });

        // undoButton - call calculateTotalIncome() when pressed
        undoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                undoAction();
            }
        });

        

        addFieldListeners();
        addComboListeners();
       
    }

    private void addFieldListeners() {

        ActionListener textFieldActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!undoIsActive) {
                    updateSheet();
                }
        }};

        FocusListener textFieldFocuListener = new FocusListener() {
            public void focusGained(FocusEvent e) {
                JTextField source = (JTextField) e.getSource();
                source.selectAll();
            }
            public void focusLost(FocusEvent e) {
                if (!undoIsActive) {
                    updateSheet();
                }
            }
        };

        wagesField.addActionListener(textFieldActionListener);
        wagesField.addFocusListener(textFieldFocuListener);
        loansField.addActionListener(textFieldActionListener);
        loansField.addFocusListener(textFieldFocuListener);
        otherInField.addActionListener(textFieldActionListener);
        otherInField.addFocusListener(textFieldFocuListener);
        foodField.addActionListener(textFieldActionListener);
        foodField.addFocusListener(textFieldFocuListener);
        rentField.addActionListener(textFieldActionListener);
        rentField.addFocusListener(textFieldFocuListener);
        subsField.addActionListener(textFieldActionListener);
        subsField.addFocusListener(textFieldFocuListener);
        otherOutField.addActionListener(textFieldActionListener);
        otherOutField.addFocusListener(textFieldFocuListener);

    }

    private void addComboListeners() {

        ActionListener periodComboListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!undoIsActive) {
                    updateSheet();
                }
            }};

        wageCombo.addActionListener(periodComboListener);
        loanCombo.addActionListener(periodComboListener);
        otherInCombo.addActionListener(periodComboListener);
        foodCombo.addActionListener(periodComboListener);
        rentCombo.addActionListener(periodComboListener);
        subsCombo.addActionListener(periodComboListener);
        otherOutCombo.addActionListener(periodComboListener);
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

        // clear total field and return if any value is NaN (error)
        if (Double.isNaN(wages) || Double.isNaN(loans) || Double.isNaN(otherIn)) {
            totalIncomeField.setText("");  // clear total income field
            return Double.NaN;   // exit method and do nothing
        }

        // otherwise calculate total income and update text field
        double totalIncome = wages * periodMultiplier(wageCombo) + loans * periodMultiplier(loanCombo)+ otherIn * periodMultiplier(otherInCombo);
        totalIncomeField.setText(String.format("%.2f",totalIncome));  // format with 2 digits after the .
        return totalIncome;
    }

    // update totalOutgoingsField (eg, when Calculate is pressed)
    // use Object to represent combobox selection
    public double calculateTotalOutgoings() {

        // get values from Outgoings comboboxes.
        double food = getTextFieldValue(foodField);
        double rent = getTextFieldValue(rentField);
        double subscriptions = getTextFieldValue(subsField);
        double otherOut = getTextFieldValue(otherOutField);

        // clear total field and return if any value is NaN (error)
        if (Double.isNaN(food) || Double.isNaN(rent) || Double.isNaN(subscriptions) || Double.isNaN(otherOut)) {
            totalOutgoingsField.setText("");  // clear total outgoings field
            return Double.NaN;   // exit method and do nothing
        }

        // otherwise calculate total outgoings and update text field
        double totalOutgoings = food * periodMultiplier(foodCombo) + rent * periodMultiplier(rentCombo)+ subscriptions * periodMultiplier(subsCombo) + otherOut * periodMultiplier(otherOutCombo);
        totalIncomeField.setText(String.format("%.2f",totalOutgoings));  // format with 2 digits after the .
        return totalOutgoings;
    }


    // call the methods that calculate the totals and update the text fields
    public void calculateAllTotals() {

        double totalIncome = calculateTotalIncome();
        double totalOutgoings = calculateTotalOutgoings();
        double netIncome = totalIncome - totalOutgoings;

        totalIncomeField.setText(String.format("%.2f",totalIncome));
        totalOutgoingsField.setText(String.format("%.2f",totalOutgoings)); 
        netIncomeField.setText(String.format("%.2f",netIncome)); 

        if (netIncome < 0) {
            netIncomeField.setForeground(Color.red);
        }
        else {
            netIncomeField.setForeground(Color.black);
        }
    }

    // update totalIncomeField (eg, when Calculate is pressed)
    // use double to hold numbers, so user can type fractional amounts such as 134.50
    public void updateSheet() {
      
        System.out.println("updateSheet() called");

        // get values from income text fields.  valie is NaN if an error occurs
        double wages = getTextFieldValue(wagesField);
        double loans = getTextFieldValue(loansField);
        double otherIn = getTextFieldValue(otherInField);
        double food = getTextFieldValue(foodField);
        double rent = getTextFieldValue(rentField);
        double subscriptions = getTextFieldValue(subsField);
        double otherOut = getTextFieldValue(otherOutField);
        String wagePeriod = wageCombo.getSelectedItem().toString();
        String loanPeriod = loanCombo.getSelectedItem().toString();
        String otherInPeriod = otherInCombo.getSelectedItem().toString();
        String foodPeriod = foodCombo.getSelectedItem().toString();
        String rentPeriod = rentCombo.getSelectedItem().toString();
        String subsPeriod = subsCombo.getSelectedItem().toString();
        String otherOutPeriod = otherOutCombo.getSelectedItem().toString();

        double[] sheetState = {wages, loans, otherIn, food, rent, subscriptions, otherOut};                                     // create a snapshot of the board
        String[] periodsState = {wagePeriod, loanPeriod, otherInPeriod, foodPeriod, rentPeriod, subsPeriod, otherOutPeriod};    // including the combo boxes

        // update the sheet to show doubles
        wagesField.setText(String.format("%.2f", sheetState[0]));
        loansField.setText(String.format("%.2f", sheetState[1]));
        otherInField.setText(String.format("%.2f", sheetState[2]));
        foodField.setText(String.format("%.2f", sheetState[3]));
        rentField.setText(String.format("%.2f", sheetState[4]));
        subsField.setText(String.format("%.2f", sheetState[5]));
        otherOutField.setText(String.format("%.2f", sheetState[6]));

        if (!Arrays.equals(sheetState, sheetHistory.peek())) {
            sheetHistory.push(sheetState);
            periodsHistory.push(periodsState);
            System.out.println("Sheet has changed");
            System.out.println(periodsHistory.size());
        }
        else {
            System.out.println("Sheet Unchanged");    

            if (!Arrays.equals(periodsState, periodsHistory.peek())) {
                System.out.println("Periods have changed");;
                System.out.println(Arrays.toString(periodsState));
                System.out.println(Arrays.toString(periodsHistory.peek()));
                sheetHistory.push(sheetState);
                periodsHistory.push(periodsState);
            }

            // for (int i = 0; i < periodsState.length; i++) {     
            //     if (periodsState[i] != periodsHistory.peek()[i]) {
            //         sheetHistory.push(sheetState);
            //         periodsHistory.push(periodsState);
            //         break;
            //     }
            // }
        }

        // otherwise calculate totals and update text field
        calculateAllTotals();
    }

    public void undoAction() {
        

        undoIsActive = true;
        System.out.println("Undo action started");

        if (sheetHistory.size() > 1) {
            // Remove the current state
            sheetHistory.pop();
            periodsHistory.pop();

            System.out.println("Popped for undo");
            System.out.println(periodsHistory.size());
    
            // Now get the previous state
            double[] previousState = sheetHistory.peek();
            String[] previousPeriods = periodsHistory.peek();
    
            wagesField.setText(String.format("%.2f", previousState[0]));
            loansField.setText(String.format("%.2f", previousState[1]));
            otherInField.setText(String.format("%.2f", previousState[2]));
            foodField.setText(String.format("%.2f", previousState[3]));
            rentField.setText(String.format("%.2f", previousState[4]));
            subsField.setText(String.format("%.2f", previousState[5]));
            otherOutField.setText(String.format("%.2f", previousState[6]));
    
            wageCombo.setSelectedItem(previousPeriods[0]);
            loanCombo.setSelectedItem(previousPeriods[1]);
            otherInCombo.setSelectedItem(previousPeriods[2]);
            foodCombo.setSelectedItem(previousPeriods[3]);
            rentCombo.setSelectedItem(previousPeriods[4]);
            subsCombo.setSelectedItem(previousPeriods[5]);
            otherOutCombo.setSelectedItem(previousPeriods[6]);
    
            // Recalculate totals based on the previous state
            calculateAllTotals();;
        }
        else {
            JOptionPane.showMessageDialog(topLevelFrame, "There's no more to undo");
        } 
        undoIsActive = false;
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

    // return the value to be muliplied by for the selected preiod
    private int periodMultiplier(JComboBox<String> box) {
        String boxString = box.getSelectedItem().toString();
        switch (boxString) {
            case "Year":
                return periodsMap.get(boxString);
            case "Month":
                return periodsMap.get(boxString);
            case "Week":
                return periodsMap.get(boxString);
            default:
            return 0;
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