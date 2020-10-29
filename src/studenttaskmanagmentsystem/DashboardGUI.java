/*
 * DashboardGUI.java - This class builds the GUI interface for dashboard 
 * Date: 17/04/2020
 * @author Fetya Juhar
 *
 * Final Project: Student Task Managment System
 */
package studenttaskmanagmentsystem;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javax.swing.border.EmptyBorder;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;

/*
 * Class extends <code>JInternalFrame</code> and implements <code>ListSelectionListener</code>
 */
public class DashboardGUI extends JInternalFrame{
    
    private JLabel filterLabel;
    private JTable dashboardTable;
    private JComboBox filterComboBox;
            
    private StudentTaskManagmentSystem initSTMS;
    private StudentTaskManagmentSystemGUI initSTMSGUI;
    
    private ArrayList<Schedule> scheduleList;
    
    /*
     * This constructor takes two parameters, initSTMSGUI and initSTMS in order to push any amendments to the main GUI frame
     * and access from the data layer respectively.
     * 
     * @param initSTMSGUI of type <code>StudentTaskManagmentSystemGUI</code>
     * @param initSTMS of type <code>StudentTaskManagmentSystem</code>
     */
    DashboardGUI(StudentTaskManagmentSystemGUI initSTMSGUI, StudentTaskManagmentSystem initSTMS){
        
        super("My Dashboard",   true, // resizable
                                true, // closable
                                true, // maximizable
                                true);// iconifiable
        
        
        this.initSTMSGUI = initSTMSGUI; // main window frame
        this.initSTMS = initSTMS;       // object that access from the data layer
        
        setLayout(new GridLayout(1,1));
        
        // initialize and add the GUI interface.
        add(initGUI());
        setSize(800, 300);
        
    }

    /* 
     * This method initialise and build the GUI interface, and hook event handler for the JList
     * 
     * @return mainPanel of type <code>JPanel</code>
     */
    public JPanel initGUI(){
               
        filterLabel = new JLabel("Filter By Scheduled Date:");
        filterComboBox = new JComboBox(Schedule.DATE_VALUES);
        
        dashboardTable = new JTable(initSTMS.loadSchedule(filterComboBox.getSelectedItem().toString()));
        
        filterComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent arg0) {
                
                String dueDateFilter = filterComboBox.getSelectedItem().toString();
                
                dashboardTable.setModel(initSTMS.loadSchedule(dueDateFilter));
            }
        });

        JScrollPane scheduleDashboardScrollPane = new JScrollPane(dashboardTable);
        
        JPanel filterLabelPanel = new JPanel();
        filterLabelPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        filterLabelPanel.add(filterLabel);
        filterLabelPanel.add(filterComboBox);        
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(scheduleDashboardScrollPane, BorderLayout.CENTER);
        mainPanel.add(filterLabelPanel, BorderLayout.NORTH);
        
        return mainPanel;
    }    

    
    /*
     * This method converts the Day String into integer to make it easier for comparison. 
     *
     * @param day of type <code>String</code>
     * @return int
     */
    public int covertDaytoInt(String day){
        
        // holds the Day and integer equivalence in a HashMap
        HashMap<String, Integer> dayToIntMap = new HashMap<>();
        dayToIntMap.put("monday", 1);
        dayToIntMap.put("tuesday", 2);
        dayToIntMap.put("wednesday", 3);
        dayToIntMap.put("thursday", 4);
        dayToIntMap.put("friday", 5);
        dayToIntMap.put("saturday", 6);
        dayToIntMap.put("sunday", 7);
        
        return dayToIntMap.get(day);
    }
}
