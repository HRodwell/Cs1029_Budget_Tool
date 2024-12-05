
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import hamish.BudgetBase;

// Swing imports
import javax.swing.*;
import java.util.Stack;

/**
 * Unit test for simple App.
 */
public class BudgetBaseTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldTotal()
    {
        JFrame frame = new JFrame();
        BudgetBase bb = new BudgetBase(frame);
        double value = 0.0;

        assertEquals(value, bb.calculateTotalIncome() );
    }

    // test undoAction
    @Test
    public void shouldUndo(){

        // Initialise an instace of budget tool
        JFrame frame = new JFrame();
        BudgetBase bb = new BudgetBase(frame);
        Stack<double[]> testSheetHistory = new Stack<>();
        Stack<String[]> testPeriodsHistrory = new Stack<>();


        // create some test data
        testSheetHistory.push(new double[] {300, 0, 0, 0, 0, 0, 0});
        testSheetHistory.push(new double[] {300, 0, 0, 0, 0, 0, 0});
        testSheetHistory.push(new double[] {300, 800, 0, 0, 0, 0, 0});
        testSheetHistory.push(new double[] {300, 800, 0, 0, 0, 0, 0});
        testSheetHistory.push(new double[] {300, 800, 0, 70, 0, 0, 0});
        testSheetHistory.push(new double[] {300, 800, 0, 70, 0, 0, 0});
        testSheetHistory.push(new double[] {300, 800, 0, 70, 450, 0, 0});
        testSheetHistory.push(new double[] {300, 800, 0, 70, 450, 0, 0});
        testSheetHistory.push(new double[] {300, 800, 0, 70, 450, 12.99, 0});
        testSheetHistory.push(new double[] {300, 800, 0, 70, 450, 12.99, 0});
        testSheetHistory.push(new double[] {300, 800, 0, 70, 450, 12.99, 200});

        testPeriodsHistrory.push(new String[] {"Year", "Year", "Year", "Year", "Year", "Year", "Year"});
        testPeriodsHistrory.push(new String[] {"Week", "Year", "Year", "Year", "Year", "Year", "Year"});
        testPeriodsHistrory.push(new String[] {"Week", "Year", "Year", "Year", "Year", "Year", "Year"});
        testPeriodsHistrory.push(new String[] {"Week", "Month", "Year", "Year", "Year", "Year", "Year"});
        testPeriodsHistrory.push(new String[] {"Week", "Month", "Year", "Year", "Year", "Year", "Year"});
        testPeriodsHistrory.push(new String[] {"Week", "Month", "Year", "Week", "Year", "Year", "Year"});
        testPeriodsHistrory.push(new String[] {"Week", "Month", "Year", "Week", "Year", "Year", "Year"});
        testPeriodsHistrory.push(new String[] {"Week", "Month", "Year", "Week", "Month", "Year", "Year"});
        testPeriodsHistrory.push(new String[] {"Week", "Month", "Year", "Week", "Month", "Year", "Year"});
        testPeriodsHistrory.push(new String[] {"Week", "Month", "Year", "Week", "Month", "Month", "Year"});
        testPeriodsHistrory.push(new String[] {"Week", "Month", "Year", "Week", "Month", "Month", "Year"});

        // inject the data
        bb.sheetHistory = testSheetHistory;  
        bb.periodsHistory = testPeriodsHistrory;

        // perform first undo and check that totals are as expected
        bb.undoAction();
        assertArrayEquals(testPeriodsHistrory.peek(), bb.periodsHistory.peek());
        assertArrayEquals(testSheetHistory.peek(), bb.sheetHistory.peek());
        assertEquals(300*52 + 800*12 + 0*1, bb.calculateTotalIncome());  
        assertEquals(70*52 + 450*12 + 12.99*12 + 0*1, bb.calculateTotalOutgoings());  

        // perform another 5 and check again
        bb.undoAction();
        bb.undoAction();
        bb.undoAction();
        bb.undoAction();
        bb.undoAction();
        assertArrayEquals(testPeriodsHistrory.peek(), bb.periodsHistory.peek());
        assertArrayEquals(testSheetHistory.peek(), bb.sheetHistory.peek());
        assertEquals(300*52 + 800*12 + 0*1, bb.calculateTotalIncome());  
        assertEquals(70*1 + 0*1 + 0*1 + 0*1, bb.calculateTotalOutgoings());   
    }
}
