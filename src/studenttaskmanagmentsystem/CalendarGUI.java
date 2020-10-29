/*
 * CalendarGUI.java - This class builds the GUI interface to display a month calendar in a JPanel
 * Date: 14/04/2020
 *
 * @author: Fetya Juhar
 * @Reference: http://www.darwinsys.com/
 *
 * Final Project: Student Task Managment System
 */
package studenttaskmanagmentsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/*
 * Class extends <code>JPanel</code>
 */
public class CalendarGUI extends JPanel {
    
    private JComboBox monthChoice, yearChoice;
    private  JButton labs[][], b0;  // The buttons to be displayed
    
    private int yy, mm, dd;
    private int leadGap = 0;    // The number of day squares to leave blank at the start of this month
    private int activeDay = -1;

    private Calendar calendar = new GregorianCalendar();
    private final int thisYear = calendar.get(Calendar.YEAR);   // Today's year
    private final int thisMonth = calendar.get(Calendar.MONTH); // Today's month 
    
    private String[] months = { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };
    public final static int dom[] = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 }; // number of days for each months specified above
    
    private StudentTaskManagmentSystem initSTMS;
    private StudentTaskManagmentSystemGUI initSTMSGUI;

    /*
     * This constructor takes two parameters, initSTMSGUI and initSTMS in order to push any amendments to the main GUI frame
     * and access from the data layer respectively.
     * 
     * @param initSTMSGUI of type <code>StudentTaskManagmentSystemGUI</code>
     * @param initSTMS of type <code>StudentTaskManagmentSystem</code>
     */
    CalendarGUI(StudentTaskManagmentSystemGUI initSTMSGUI, StudentTaskManagmentSystem initSTMS) {
        
        super();
    
        this.initSTMSGUI = initSTMSGUI; // main window frame
        this.initSTMS = initSTMS;       // object that access from the data layer
        
        setYYMMDD(calendar.get(Calendar.YEAR), 
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));
    
        initGUI();
        recompute();
    }

    /*
     * This method set the <code>yy</code>, <code>mm</code> & <code>dd</code> variable
     *
     * @param year of type <code>int</code>
     * @param month of type <code>int</code>
     * @param today of type <code>int</code>
     * @return void
     */
    public void setYYMMDD(int year, int month, int today) {
      yy = year;
      mm = month;
      dd = today;
    }

    /* 
     * This method build the GUI. Assumes that setYYMMDD has been called
     * 
     * @return void 
     */
    public void initGUI() {
        
        getAccessibleContext().setAccessibleDescription("Calendar not accessible yet. Sorry!");
        setBorder(BorderFactory.createEtchedBorder());

        setLayout(new BorderLayout());

        JPanel tp = new JPanel();
        tp.add(monthChoice = new JComboBox());
        
        for (int i = 0; i < months.length; i++)
            monthChoice.addItem(months[i]);
            monthChoice.setSelectedItem(months[mm]);
            monthChoice.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    int i = monthChoice.getSelectedIndex();
        
                    if (i >= 0) {
                        mm = i;
            
                        recompute(); // System.out.println("Month=" + mm);
                    }
                }
            });
            
        monthChoice.getAccessibleContext().setAccessibleName("Months");
        monthChoice.getAccessibleContext().setAccessibleDescription("Choose a month of the year");

        tp.add(yearChoice = new JComboBox());
        yearChoice.setEditable(true);
        
        for (int i = yy - 5; i < yy + 5; i++)
            yearChoice.addItem(Integer.toString(i));
            yearChoice.setSelectedItem(Integer.toString(yy));
            yearChoice.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    int i = yearChoice.getSelectedIndex();
          
                    if (i >= 0) {
                        yy = Integer.parseInt(yearChoice.getSelectedItem().toString());
            
                        recompute();
                    }
                }
            });
            
        add(BorderLayout.CENTER, tp);

        JPanel bp = new JPanel();
        bp.setLayout(new GridLayout(7, 7));
        labs = new JButton[6][7]; // first row is days

        bp.add(b0 = new JButton("S"));
        bp.add(new JButton("M"));
        bp.add(new JButton("T"));
        bp.add(new JButton("W"));
        bp.add(new JButton("R"));
        bp.add(new JButton("F"));
        bp.add(new JButton("S"));

        ActionListener dateSetter = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String num = e.getActionCommand();
          
                if (!num.equals("")) {
                    // set the current day highlighted
                    setDayActive(Integer.parseInt(num));
                    
                    // TO DO: code snipt to launch DateChanged events and populate the JList is implemented here
                }

            JOptionPane.showMessageDialog(initSTMSGUI, "This feature is under construction", "Under Construction", JOptionPane.INFORMATION_MESSAGE);
 
            }
        };

        // Construct all the buttons, and add them.
        for (int i = 0; i < 6; i++)
          for (int j = 0; j < 7; j++) {
            bp.add(labs[i][j] = new JButton(""));
            labs[i][j].addActionListener(dateSetter);
          }

        add(BorderLayout.SOUTH, bp);
    }
 
    /* 
     * This method compute which days to put where, in the Cal panel
     * 
     * @return void 
     */
    public void recompute() {
        
        if (mm < 0 || mm > 11)
          throw new IllegalArgumentException("Month " + mm + " bad, must be 0-11");
      
        clearDayActive();
        calendar = new GregorianCalendar(yy, mm, dd);

        // Compute how much to leave before the first.
        // getDay() returns 0 for Sunday, which is just right.
        leadGap = new GregorianCalendar(yy, mm, 1).get(Calendar.DAY_OF_WEEK) - 1;

        int daysInMonth = dom[mm];
        
        if (isLeap(calendar.get(Calendar.YEAR)) && mm == 1)
            ++daysInMonth;

        // Blank out the labels before 1st day of month
        for (int i = 0; i < leadGap; i++)
            labs[0][i].setText("");

        // Fill in numbers for the day of month.
        for (int i = 1; i <= daysInMonth; i++) {
            JButton b = labs[(leadGap + i - 1) / 7][(leadGap + i - 1) % 7];
            b.setText(Integer.toString(i));
        }

        // 7 days/week * up to 6 rows
        for (int i = leadGap + 1 + daysInMonth; i < 6 * 7; i++)
            labs[(i) / 7][(i) % 7].setText("");

        
        // Shade current day, only if current month
        if (thisYear == yy && mm == thisMonth)
            setDayActive(dd);   // shade the box for today

      
        repaint();      // Say we need to be drawn on the screen
    }

    /* 
     * This method return true if the given year is a Leap Year
     * NB: "a year is a leap year if it is divisible by 4 but not by 100, except that years divisible by 400 *are*
     * leap years." -- Kernighan & Ritchie, "The C Programming Language", p 37.
     * 
     * @return <code>boolean</code>
     */
    public boolean isLeap(int year) {
        
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0)
            return true;
        
        return false;
    }

    /* 
     * This method set the year, month, and day
     *
     * @param yy of type <code>int</code>
     * @param mm of type <code>int</code>
     * @param dd of type <code>int</code>
     *
     * @return void
     */
    public void setDate(int yy, int mm, int dd) {
        
        this.yy = yy;
        this.mm = mm; 
        this.dd = dd;

        recompute();
    }

    /* 
     * This method unset any previously highlighted day
     *
     * @return void
     */
    public void clearDayActive() {
        
        JButton b;

        // First un-shade the previously-selected square, if any
        if (activeDay > 0) {
            
            b = labs[(leadGap + activeDay - 1) / 7][(leadGap + activeDay - 1) % 7];
            b.setBackground(b0.getBackground());
            b.setForeground(Color.BLACK);
            
            b.repaint();
            
            activeDay = -1;
        }
    }

    /* 
     * This method set just the day, on the current month
     *
     * @param newDay of type <code>int</code>
     *
     * @return void
     */
    public void setDayActive(int newDay) {

        clearDayActive();

        // Set the new one
        if (newDay <= 0)
            dd = new GregorianCalendar().get(Calendar.DAY_OF_MONTH);
        else
            dd = newDay;
        
        // Now shade the correct square
        Component square = labs[(leadGap + newDay - 1) / 7][(leadGap + newDay - 1) % 7];
        
        square.setBackground(Color.black);
        square.setForeground(Color.WHITE);
        
        square.repaint();
        
        activeDay = newDay;
    }
  
}
