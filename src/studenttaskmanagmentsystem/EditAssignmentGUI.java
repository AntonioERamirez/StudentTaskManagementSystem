/*
 * EditAssignmentGUI.java - This class builds the GUI interface for Editing Assignment. 
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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

/*
 * Class extends <code>JInternalFrame</code> and implements <code>ActionListener</code>
 */
public class EditAssignmentGUI  extends JInternalFrame  implements ActionListener{
    
    // GUI component variables
    private JLabel assignmentNameLabel, dueDateLabel, ETALabel, statusLabel, colorLabel, descriptionLabel, priorityLabel;
    private JComboBox assignmentNameComboBox, statusComboBox, colorComboBox, dueDateComboBox, ETAComboBox,priorityComboBox;
    private JTextArea descriptionTextArea;
    private JButton editAssignmentButton;
    
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
    EditAssignmentGUI(StudentTaskManagmentSystemGUI initSTMSGUI, StudentTaskManagmentSystem initSTMS){
        super("Edit Assignment", true, // resizable
                                true, // closable
                                true, // maximizable
                                true);// iconifiable
        
        this.initSTMSGUI = initSTMSGUI; // main window frame
        this.initSTMS = initSTMS;       // object that access from the data layer
        
         setLayout(new FlowLayout());
        
        // initialize and add the GUI interface.
        add(initGUI());
        setSize(600, 325);
        
        // populate the assignment names in the dropdown combobox
        populateAssignmentNames();
    }
    
    /* 
     * This method initialise and build the GUI interface, and hook event handler for the edit assignment button
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
    
        ETAComboBox = new JComboBox(Assignment.ETA_VALUES);
       
        assignmentNameComboBox = new JComboBox();
        
        statusComboBox = new JComboBox(Assignment.STATUS_VALUES);
        colorComboBox = new JComboBox(Assignment.COLOR_VALUES);
        priorityComboBox = new JComboBox(Assignment.PRIORITY_VALUES);
        
        dueDateComboBox = new JComboBox(Assignment.DUE_DATE_VALUES);
        
        
        // Hook and event listener to the Assignment dropdown combobox and re-popluate 
        // GUI interface with the assignment data
        assignmentNameComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent arg0) {
                fillFields();   // populate hte GUI fields with the assignment data
            }
        });
        
        descriptionTextArea = new JTextArea(4, 1);
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionTextArea);
        descriptionScrollPane.setSize(200,100);
        
        editAssignmentButton = new JButton("Update Assignment");        
        editAssignmentButton.addActionListener(this);   // hook the action listener for edit assignment button
        
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
        leftFieldPanel.add(assignmentNameComboBox);
        leftFieldPanel.add(dueDateComboBox);
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
        buttonPanel.add(editAssignmentButton);
        
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
        
        return mainPanel;   // return the final organized panel
    }
    
    /* 
     * This method adds the list of the Assignment Names in the assignmentNameComboBox of type <code>JComboBox</code>
     * 
     * @return void
     */
    public void populateAssignmentNames(){
        
        ArrayList <Assignment> assignments = initSTMS.getLoggedUser().getAssignmentList();
        
        // The default model for combo boxes
        DefaultComboBoxModel dml= new DefaultComboBoxModel();
        
        // Add the assignment names in the list
        for (Assignment assignment : assignments) {
            dml.addElement(assignment.getAssignmentName());
        }

        assignmentNameComboBox.setModel(dml);  // attach the default combobox model with the combobox 
        
        fillFields();   // populate the assignment fields with its respective variables
    }
    
    /* 
     * This method populate the assignment GUI fields with its respective variables
     * 
     * @return void
     */
    public void fillFields(){

        int selectedIndex;
        
        ArrayList <Assignment> assignments = initSTMS.getLoggedUser().getAssignmentList();   
        
        selectedIndex = assignmentNameComboBox.getSelectedIndex();
        Assignment assignment = (Assignment) assignments.get(selectedIndex);


        dueDateComboBox.setSelectedItem(assignment.getDueDate());
        ETAComboBox.setSelectedItem(assignment.getETATime());
        statusComboBox.setSelectedItem(assignment.getStatus());
        colorComboBox.setSelectedItem(assignment.getLabelColor()); 
        assignmentID = assignment.getAssignmentID();
        priorityComboBox.setSelectedItem(assignment.getPriority());
        descriptionTextArea.setText(assignment.getDescription());
    }

    /* 
     * Event handler method of the class for the Edit Assignment button
     * 
     * @param e of type <code>ActionEvent</code>
     * @return void
     */
    @Override
    public void actionPerformed(ActionEvent e) { 

        String action = e.getActionCommand();
        
        boolean editStatus = false;
       
        
        if(action.equalsIgnoreCase("Update Assignment")){
          
          
            editStatus = initSTMS.editAssignment(assignmentID,
                                                String.valueOf(assignmentNameComboBox.getSelectedItem()), 
                                                String.valueOf(dueDateComboBox.getSelectedItem()),
                                                String.valueOf(ETAComboBox.getSelectedItem()),
                                                String.valueOf(colorComboBox.getSelectedItem()), 
                                                descriptionTextArea.getText(),
                                                String.valueOf(statusComboBox.getSelectedItem()), 
                                                String.valueOf(priorityComboBox.getSelectedItem()));
            
            if(editStatus){
                
                JOptionPane.showMessageDialog(this, "Assignment is updated Successfully", "Success Updating", JOptionPane.INFORMATION_MESSAGE);
                initSTMSGUI.displayLog("NOTIFICATION: " + String.valueOf(assignmentNameComboBox.getSelectedItem()) + " assignment is updated successfully.");
                initSTMSGUI.disposeInternalFrame("Edit Assignment");    // dispose the Edit Assignment Internal Frame 
                populateAssignmentNames();  // re-populate the assignment fields
            }
            else {
                JOptionPane.showMessageDialog(this, "Error occured whilst updating the assignment.", "Error Updating", JOptionPane.ERROR_MESSAGE);
                initSTMSGUI.displayLog("ERROR MESSAGE: Error occured whilst updating the assignment " + String.valueOf(assignmentNameComboBox.getSelectedItem()));
            }
        }
    }
}
