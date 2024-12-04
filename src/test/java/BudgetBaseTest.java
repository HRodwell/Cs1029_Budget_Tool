
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import hamish.BudgetBase;

// Swing imports
import javax.swing.*;
import java.awt.event.*;
import java.util.Stack;
import java.awt.*;

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

        // Initialise an instace of budget tool and 
        JFrame frame = new JFrame();
        BudgetBase bb = new BudgetBase(frame);
        Stack<double[]> testSheetHistory = new Stack<>();
        Stack<String[]> testPeriodsHistrory = new Stack<>();

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

        bb.sheetHistory = testSheetHistory;  
        bb.periodsHistory = testPeriodsHistrory;

        // 

        // perform first undo and check that totals are as expected
        bb.undoAction();
        assertArrayEquals(testPeriodsHistrory.peek(), bb.periodsHistory.peek());
        assertArrayEquals(testSheetHistory.peek(), bb.sheetHistory.peek());
        assertEquals(300*52 + 800*12 + 0*12, bb.calculateTotalIncome());  
        assertEquals(70*52 + 450*12 + 12.99*12 + 0*12, bb.calculateTotalOutgoings());  

        
        bb.undoAction();
        assertArrayEquals(testPeriodsHistrory.peek(), bb.periodsHistory.peek());
        assertArrayEquals(testSheetHistory.peek(), bb.sheetHistory.peek());
        assertEquals(300*52 + 800*12 + 0*12, bb.calculateTotalIncome());  
        assertEquals(70*52 + 450*12 + 12.99*12 + 0*12, bb.calculateTotalOutgoings());        
    }
}
