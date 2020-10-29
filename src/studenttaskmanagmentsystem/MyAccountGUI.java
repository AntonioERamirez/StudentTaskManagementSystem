/*
 * MyAccount.java - This class builds the GUI interface for My Account Internal Frame. 
 * Date: 15/04/2020
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
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/*
 * Class extends <code>JInternalFrame</code> and implements <code>ActionListener</code>
 */
public class MyAccountGUI extends JInternalFrame implements ActionListener {
    ArrayList<User> list = new ArrayList<>();
    
    // GUI component variables
    private JLabel firstNameLabel, lastNameLabel, dobLabel, emailLabel, passwordLabel, confirmPasswordLabel, comboBoxLabel;
    private JTextField firstNameField, lastNameField, dobField, emailField;
    private JTextField passwordField, confirmPasswordField;
    private JButton updateAccountButton, deleteAccountButton;
    private JComboBox<String> usersComboBox = new JComboBox<>();
    
    private StudentTaskManagmentSystem initSTMS;
    private StudentTaskManagmentSystemGUI initSTMSGUI;
    
    /*
     * This constructor takes two parameters, initSTMSGUI and initSTMS in order to push any amendments to the main GUI frame
     * and access from the data layer respectively.
     * 
     * @param initSTMSGUI of type <code>StudentTaskManagmentSystemGUI</code>
     * @param initSTMS of type <code>StudentTaskManagmentSystem</code>
     */
    MyAccountGUI(StudentTaskManagmentSystemGUI initSTMSGUI, StudentTaskManagmentSystem initSTMS){
        super("My Account", true, // resizable
                            true, // closable
                            true, // maximizable
                            true);// iconifiable
        
        this.initSTMSGUI = initSTMSGUI; // main window frame
        this.initSTMS = initSTMS;       // object that access from the data layer
        
        setLayout(new FlowLayout());
        
        // initialize and add the GUI interface
        add(initGUI());        
        setSize(400, 300);
        
        populateMyAccount();
    }
    
    /* 
     * This method initialise and build the GUI interface, and hook event handler for the update account button
     * 
     * @return mainPanel of type <code>JPanel</code>
     */
    public JPanel initGUI(){
        firstNameLabel = new JLabel("First Name:");
        lastNameLabel = new JLabel("Last Name:");
        dobLabel = new JLabel("DOB:");
        emailLabel = new JLabel("E-Mail:");
        passwordLabel = new JLabel("Password:");
        confirmPasswordLabel = new JLabel("Confirm Password:");
        comboBoxLabel = new JLabel("Select User:");
        
        firstNameField = new JTextField(20);
        lastNameField = new JTextField(20);
        dobField = new JTextField(20);
        emailField = new JTextField(20);
        passwordField=new JTextField(20);
        confirmPasswordField=new JTextField(20);
        //Populate combobox from user list
        try{
            for (User user : initSTMS.geUsersList()){
                usersComboBox.addItem(user.getFirstName() + " " + user.getLastName());
            }
        } catch (NullPointerException ex){
            System.out.println(initSTMS.geUsersList());
        }

        
        updateAccountButton = new JButton("Update Account"); 
        updateAccountButton.addActionListener(this);
        //TODO
        deleteAccountButton = new JButton("Delete Account");
        deleteAccountButton.addActionListener(deleteButtonListener);
        
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new GridLayout(7,1));
        labelPanel.add(firstNameLabel);
        labelPanel.add(lastNameLabel);
        labelPanel.add(dobLabel);
        labelPanel.add(emailLabel);
        labelPanel.add(passwordLabel);
        labelPanel.add(confirmPasswordLabel);
        
        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new GridLayout(7,1));
        fieldPanel.add(firstNameField);
        fieldPanel.add(lastNameField);
        fieldPanel.add(dobField);
        fieldPanel.add(emailField);
        fieldPanel.add(passwordField);
        fieldPanel.add(confirmPasswordField);

        if (initSTMS.getLoggedUser().getEmail() == "admin@admin.com"){
            labelPanel.add(comboBoxLabel);
            fieldPanel.add(usersComboBox);
            usersComboBox.addActionListener(comboBoxListener);
        }
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(updateAccountButton);
        buttonPanel.add(deleteAccountButton);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(labelPanel, BorderLayout.WEST);
        mainPanel.add(fieldPanel, BorderLayout.EAST);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.setBorder(new EmptyBorder(20,10,10,10));
        
        return mainPanel;
    }
    
    /* 
     * This method populate the user paramters into the GUI fields
     * 
     * @return void
     */
    public void populateMyAccount(){

        //If for some reason combobox is empty, prevents NullPointerException
        if (usersComboBox.getItemCount() == 0){
            try{
                for (User user : initSTMS.geUsersList()){
                    usersComboBox.addItem(user.getFirstName() + " " + user.getLastName());
                }
            } catch (NullPointerException ex){
                System.out.println(initSTMS.geUsersList());
            }
        }

        String[] currentSelection = usersComboBox.getSelectedItem().toString().split(" ");

        //To populate the fields based on combobox selection only if admin
        if (initSTMS.getLoggedUser().getFirstName().equals("Admin")){
            for (User user : initSTMS.geUsersList()){
                if (currentSelection[0].equals(user.getFirstName()) && currentSelection[1].equals(user.getLastName())){
                    //Add user object to list to transfer object outside the scope of this loop/if statement
                    list.clear();
                    list.add(user);
                }
            }
        } else{
            //If it is a non admin, just add the current loggedUser to the list
            list.add(initSTMS.getLoggedUser());
        }

        //Make sure list is not empty, populate fields
        if (!list.isEmpty()){
            firstNameField.setText(list.get(0).getFirstName());
            lastNameField.setText(list.get(0).getLastName());
            dobField.setText(list.get(0).getDOB());
            emailField.setText(list.get(0).getEmail());
            passwordField.setText(list.get(0).getPassword());
        }

        //Make sure admin account cannot be deleted on accident
        if (list.get(0).getFirstName().equals("Admin")){
            deleteAccountButton.setEnabled(false);
        } else{
            deleteAccountButton.setEnabled(true);
        }
    }

    /* 
     * Event handler method of the class for the update account button
     * 
     * @param e of type <code>ActionEvent</code>
     * @return void
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        String action = e.getActionCommand();

        if(action.equalsIgnoreCase("Update Account")){

            // check if the password and the confirm password fields matches
            if(passwordField.getText().equals(confirmPasswordField.getText())){
                try {
                    boolean updateStatus = initSTMS.updateUser(list.get(0), firstNameField.getText(),
                            lastNameField.getText(),
                            dobField.getText(),
                            emailField.getText(),
                            passwordField.getText());

                    if (updateStatus) {
                        initSTMSGUI.disposeInternalFrame("Update Account");
                        JOptionPane.showMessageDialog(this, "Account is updated Successfully!", "Success Updating", JOptionPane.INFORMATION_MESSAGE);
                    } else
                        JOptionPane.showMessageDialog(this, "Error occured whilst updating the account.", "Error Updating", JOptionPane.ERROR_MESSAGE);

                } catch (IOException ex){
                    System.out.println(ex);
                }
            }
            else {
                JOptionPane.showMessageDialog(this, "The passwords provided doesn't match.", "Password Missmatch", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    //Listener for the combobox, updates when a new item is selected
    private ActionListener comboBoxListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            populateMyAccount();
        }
    };

    //Listener for the delete button, activates when clicked
    private ActionListener deleteButtonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            // check if the password and the confirm password fields matches
            if(passwordField.getText().equals(confirmPasswordField.getText())){
                //Set the directory for the account to be deleted
                File directoryToDelete = new File("C:\\StudentAlert\\profiles\\" + list.get(0).getUserID());
                //All premade files that must be deleted prior to deleting directory
                String[] filenameArray = {"assignments.csv", "availability.csv", "schedule.csv", "user.csv"};

                //Iterate through all premade files and delete one by one
                for (String filename : filenameArray){
                    File currentFile = new File("C:\\StudentAlert\\profiles\\" + list.get(0).getUserID() + "\\" +  filename);

                    if (currentFile.delete()){
                        System.out.println("Deleted: " + filename);
                    } else{
                        System.out.println("Could not delete: " + filename);
                    }
                }

                //If directory is successfully deleted
                if (directoryToDelete.delete()){
                    //Remove deleted user from combobox
                    usersComboBox.remove(usersComboBox.getSelectedIndex());
                    //Remove user from main user list
                    initSTMS.geUsersList().remove(list.get(0));


                    //If not an admin, must logout to finish process
                    if (!initSTMS.getLoggedUser().getFirstName().equals("Admin")){
                        JOptionPane.showMessageDialog(null, "User Deleted Successfully. Please logout to finish the process.", "User Delete", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "User Deleted Successfully", "User Delete", JOptionPane.INFORMATION_MESSAGE);
                    }


                } else {
                    JOptionPane.showMessageDialog(null, "User Could Not Be Deleted Successfully\n" + directoryToDelete.getPath(), "User Delete", JOptionPane.WARNING_MESSAGE);
                }
                //Clear the combobox to reset the values
                usersComboBox.removeAllItems();
                //Repopulate combobox and text fields
                populateMyAccount();
            }
            else {
                JOptionPane.showMessageDialog(null, "The passwords provided doesn't match.", "Password Mismatch", JOptionPane.ERROR_MESSAGE);
            }
        }
    };
}
