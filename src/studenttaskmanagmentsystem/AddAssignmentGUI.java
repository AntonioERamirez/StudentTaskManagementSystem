/*
 * AddAssignmentGUI.java - This class builds the GUI interface for adding Assignment. 
 * Date: 14/04/2020
 * @author Fetya Juhar
 *
 * Final Project: Student Task Managment System
 */
package studenttaskmanagmentsystem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/*
 * Class extends <code>JInternalFrame</code> and implements <code>ActionListener</code>
 */
public class AddAssignmentGUI extends JInternalFrame implements ActionListener{
    
    // GUI component variables
    private JLabel assignmentNameLabel, dueDateLabel, ETALabel, statusLabel, colorLabel, descriptionLabel, priorityLabel;
    private JTextField assignmentNameField;
    private JComboBox statusComboBox, colorComboBox, ETAComboBox, dueDateComboBox, priorityComboBox;
    private JTextArea descriptionTextArea;
    private JButton addAssignmentButton;
    
    private String sDD, sMM, sYY, dueDate;
    private int assignmentID;
    
    private StudentTaskManagmentSystem initSTMS;
    private StudentTaskManagmentSystemGUI initSTMSGUI;
    
    /*
     * This constructor takes two parameters, initSTMSGUI and initSTMS in order to push any amendments to the main GUI frame
     * and access from the data layer respectively.
     * 
     * @param initSTMSGUI of type <code>StudentTaskManagmentSystemGUI</code>
     * @param initSTMS of type <code>StudentTaskManagmentSystem</code>
     */
    AddAssignmentGUI(StudentTaskManagmentSystemGUI initSTMSGUI, StudentTaskManagmentSystem initSTMS){
        super("Add Assignment", true, // resizable
                                true, // closable
                                true, // maximizable
                                true);// iconifiable
        
        
        this.initSTMSGUI = initSTMSGUI; // main window frame
        this.initSTMS = initSTMS;       // object that access from the data layer
        
        setLayout(new FlowLayout());
        
        // initialize and add the GUI interface
        add(initGUI());   
        setSize(600, 325);
    }
    
    /* 
     * This method initialise and build the GUI interface, and hook event handler for the add assignment button
     * 
     * @return mainPanel of type <code>JPanel</code>
     */
    public JPanel initGUI(){
        
        assignmentNameLabel = new JLabel("Assignment Name:");
        dueDateLabel = new JLabel("Due Date:");
        ETALabel = new JLabel("Time Needed(hours):");
        statusLabel = new JLabel("Status:");
        colorLabel = new JLabel("Color Label:");
        descriptionLabel = new JLabel("Description:");
        priorityLabel = new JLabel("Priority:");
    
        assignmentNameField = new JTextField();
       
        statusComboBox = new JComboBox(Assignment.STATUS_VALUES);
        colorComboBox = new JComboBox(Assignment.COLOR_VALUES);
        ETAComboBox = new JComboBox(Assignment.ETA_VALUES);
        priorityComboBox = new JComboBox(Assignment.PRIORITY_VALUES);
        
        dueDateComboBox = new JComboBox(Assignment.DUE_DATE_VALUES);
        
        descriptionTextArea = new JTextArea(4, 1);
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionTextArea);
       
        
        addAssignmentButton = new JButton("Add Assignment");
        addAssignmentButton.addActionListener(this);    // hook the action listener for add assignment button
        
        // Build the layout for the GUI interface.
        JPanel dueDatePanel = new JPanel();
        dueDatePanel.setLayout(new GridLayout(1,3));
        dueDatePanel.add(dueDateComboBox);

        
        JPanel leftLabelPanel = new JPanel();
        leftLabelPanel.setLayout(new GridLayout(3,1));
        leftLabelPanel.add(assignmentNameLabel);
        leftLabelPanel.add(dueDateLabel);
        leftLabelPanel.add(ETALabel); 
        
        JPanel leftFieldPanel = new JPanel();
        leftFieldPanel.setLayout(new GridLayout(3,1));
        leftFieldPanel.add(assignmentNameField);
        leftFieldPanel.add(dueDatePanel);
        leftFieldPanel.add(ETAComboBox);
        

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(1,2));
        leftPanel.add(leftLabelPanel);
        leftPanel.add(leftFieldPanel);
        leftPanel.setBorder(new EmptyBorder(20,10,10,10));
        
        JPanel rightLabelPanel = new JPanel();
        rightLabelPanel.setLayout(new GridLayout(3,1));
        rightLabelPanel.add(statusLabel);
        rightLabelPanel.add(colorLabel);
        rightLabelPanel.add(priorityLabel);
       
        
        JPanel rightFieldPanel = new JPanel();
        rightFieldPanel.setLayout(new GridLayout(3,1));
        rightFieldPanel.add(statusComboBox);
        rightFieldPanel.add(colorComboBox);
        rightFieldPanel.add(priorityComboBox);
    
        
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(1,2));
        rightPanel.add(rightLabelPanel);
        rightPanel.add(rightFieldPanel); 
        rightPanel.setBorder(new EmptyBorder(20,10,10,10));
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(addAssignmentButton);
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(3,2));
        bottomPanel.add(descriptionLabel);
        bottomPanel.add(descriptionScrollPane);
        bottomPanel.add(buttonPanel);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(1,2));
        centerPanel.add(leftPanel);
        centerPanel.add(rightPanel);        

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        //mainPanel.setBorder(new EmptyBorder(10,10,10,10));
        
        return mainPanel;   // return the final organized panel
    }

    /* 
     * Event handler method of the class for the Add Assignment button
     * 
     * @param e of type <code>ActionEvent</code>
     * @return void
     */
    @Override
    public void actionPerformed(ActionEvent e) { 
                
        boolean addStatus = false;
        
        String action = e.getActionCommand();

        assignmentID = Assignment.ASSIGNMENT_ID_COUNT++;

        if(action.equalsIgnoreCase("Add Assignment")){
            
            if(assignmentNameField.getText().trim().equals("")) {
                JOptionPane.showMessageDialog(this, "Assignment Name value can't be empty.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                initSTMSGUI.displayLog("ERROR MESSAGE: Assignment Name value can't be empty.");
                
                return ;
            }
            
            // a call to the middle layer to add the assignment
            addStatus = initSTMS.addAssignment(assignmentID,
                                                        assignmentNameField.getText(), 
                                                        String.valueOf(dueDateComboBox.getSelectedItem()),
                                                        String.valueOf(ETAComboBox.getSelectedItem()),
                                                        String.valueOf(colorComboBox.getSelectedItem()), 
                                                        descriptionTextArea.getText(),
                                                        String.valueOf(statusComboBox.getSelectedItem()), 
                                                        (priorityComboBox.getSelectedItem().toString()));
            
            if(addStatus){                
                
                // dispose the the Add Assigment GUI Internal Frame
                initSTMSGUI.disposeInternalFrame("Add Assignment");               
                JOptionPane.showMessageDialog(this, "Assignment is added successfully.", "Success Registration", JOptionPane.INFORMATION_MESSAGE);
                
                initSTMSGUI.displayLog("NOTIFICATION: " + assignmentNameField.getText() + " assignment is registered Successfully.");
            }
            else {
                JOptionPane.showMessageDialog(this, "Error occured whilst adding the assignment.", "Error Registration", JOptionPane.ERROR_MESSAGE);
                initSTMSGUI.displayLog("ERROR MESSAGE: Error occured whilst adding the assignment " + assignmentNameField.getText());
            }
        }
    }
}
