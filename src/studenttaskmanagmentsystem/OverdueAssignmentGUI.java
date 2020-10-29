/*
 * OverdueAssignmentGUI.java - This class builds the GUI interface for the Overdue Assignment. 
 * Date: 16/04/2020
 * @author Fetya Juhar
 *
 * Final Project: Student Task Managment System
 */
package studenttaskmanagmentsystem;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


/*
 * Class extends <code>JInternalFrame</code> and implements <code>ListSelectionListener</code>
 */
public class OverdueAssignmentGUI extends JInternalFrame implements ListSelectionListener{
    
    // GUI component variables
    private JLabel assignmentLabel;
    private JList assignmentList;
    private JTextArea assignmentDetailTextArea;
    
    private DefaultListModel assignmentNames = new DefaultListModel();
    
    private StudentTaskManagmentSystem initSTMS;
    private StudentTaskManagmentSystemGUI initSTMSGUI;
    
    private ArrayList <Assignment> overdueAssignments;
    
    /*
     * This constructor takes two parameters, initSTMSGUI and initSTMS in order to push any amendments to the main GUI frame
     * and access from the data layer respectively.
     * 
     * @param initSTMSGUI of type <code>StudentTaskManagmentSystemGUI</code>
     * @param initSTMS of type <code>StudentTaskManagmentSystem</code>
     */
    OverdueAssignmentGUI(StudentTaskManagmentSystemGUI initSTMSGUI, StudentTaskManagmentSystem initSTMS){
        
        super("Overdue Assignment", true, // resizable
                                    true, // closable
                                    true, // maximizable
                                    true);// iconifiable

        this.initSTMSGUI = initSTMSGUI; // main window frame
        this.initSTMS = initSTMS;       // object that access from the data layer
        
        setLayout(new GridLayout(1,1));
        
        // initialize and add the GUI interface.
        add(initGUI());
        setSize(600, 325);
        
        // populates the overdue assignment names in the JList
        populateOverdueAssignmentsList();
    }
    
    /* 
     * This method initialise and build the GUI interface, and hook event handler for the overdue assignment JList
     * 
     * @return mainPanel of type <code>JPanel</code>
     */
    public JPanel initGUI(){
        
        assignmentLabel = new JLabel("Overdue Assignment List");
        assignmentLabel.setForeground(Color.WHITE);
        assignmentLabel.setBackground(Color.BLACK);
        
        assignmentList = new JList(assignmentNames);
        assignmentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        assignmentList.setSelectedIndex(0);
        assignmentList.setVisibleRowCount(3);   
        assignmentList.addListSelectionListener(this);

        JScrollPane assignmentListScrollPane = new JScrollPane(assignmentList);
        
        Dimension listWidth = assignmentList.getPreferredSize();
        listWidth.width = 150;
        assignmentListScrollPane.setPreferredSize(listWidth);
        
        assignmentDetailTextArea = new JTextArea(10, 10);
        assignmentDetailTextArea.setEditable(false);
        JScrollPane assignmentDetailScrollPane = new JScrollPane(assignmentDetailTextArea);
        
        JPanel assignmentListPanel = new JPanel();
        assignmentListPanel.setLayout(new BorderLayout());
        assignmentListPanel.add(assignmentLabel, BorderLayout.NORTH);
        assignmentListPanel.add(assignmentListScrollPane, BorderLayout.CENTER);
        assignmentListPanel.setBackground(Color.BLACK);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(assignmentListPanel, BorderLayout.WEST);
        mainPanel.add(assignmentDetailScrollPane, BorderLayout.CENTER);
        
        return mainPanel;
    }
    
    /* 
     * TO DO: This method adds the list of the overdue assignment names in the assignmentList of type <code>JList</code>
     * 
     * @return void
     */
    public void populateOverdueAssignmentsList(){
        
        overdueAssignments = initSTMS.getLoggedUser().getOverdueAssignmentList();
        
        DefaultListModel dml= new DefaultListModel();
        
        for (Assignment assignment : overdueAssignments) {
            
                dml.addElement(assignment.getAssignmentName());
        }
        
        assignmentList.setModel(dml);  
    }

    /* 
     * Event handler method of the class for the JList
     * 
     * @param e of type <code>ActionEvent</code>
     * @return void
     */
    @Override
    public void valueChanged(ListSelectionEvent e) {
        
        int selectedIndex= assignmentList.getSelectedIndex();
        
        Assignment assignment = (Assignment) overdueAssignments.get(selectedIndex);
        
        assignmentDetailTextArea.setText(assignment.toString());
    }

}

