/*
 * RegisterUserGUI.java - This class builds the GUI interface for User Registration. 
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
import java.io.IOException;
import java.util.ArrayList;

/*
 * Class extends <code>JInternalFrame</code> and implements <code>ActionListener</code>
 */
public class RegisterUserGUI extends JInternalFrame implements ActionListener {
    
    // GUI component variables
    private JLabel firstNameLabel, lastNameLabel, dobLabel, emailLabel, passwordLabel, confirmPasswordLabel;
    private JTextField firstNameField, lastNameField, dobField, emailField;
    private JPasswordField passwordField, confirmPasswordField;
    private JButton registerUserButton;
    
    private StudentTaskManagmentSystem initSTMS;
    private StudentTaskManagmentSystemGUI initSTMSGUI;
    
    /*
     * This constructor takes two parameters, initSTMSGUI and initSTMS in order to push any amendments to the main GUI frame
     * and access from the data layer respectively.
     * 
     * @param initSTMSGUI of type <code>StudentTaskManagmentSystemGUI</code>
     * @param initSTMS of type <code>StudentTaskManagmentSystem</code>
     */
    RegisterUserGUI(StudentTaskManagmentSystemGUI initSTMSGUI, StudentTaskManagmentSystem initSTMS){
        super("Register User",  true, // resizable
                                true, // closable
                                true, // maximizable
                                true);// iconifiable
        
        this.initSTMSGUI = initSTMSGUI; // main window frame
        this.initSTMS = initSTMS;       // object that access from the data layer
        
        setLayout(new FlowLayout());
        
        // initialize and add the GUI interface
        add(initGUI());        
        setSize(370, 250);
        
    }
    
    /* 
     * This method initialise and build the GUI interface, and hook event handler for the Register User button
     * 
     * @return mainPanel of type <code>JPanel</code>
     */
    public JPanel initGUI(){
                
        firstNameLabel = new JLabel("First Name:");
        lastNameLabel = new JLabel("Last Name:");
        dobLabel = new JLabel("DOB(MM/DD/YYYY):");
        emailLabel = new JLabel("E-Mail:");
        passwordLabel = new JLabel("Password:");
        confirmPasswordLabel = new JLabel("Confirm Password:");
        
        firstNameField = new JTextField(20);
        lastNameField = new JTextField(20);
        dobField = new JTextField(20);
        emailField = new JTextField(20);
        passwordField=new JPasswordField(20);
        confirmPasswordField=new JPasswordField(20);
        
        registerUserButton = new JButton("Register User"); 
        registerUserButton.addActionListener(this);
        
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new GridLayout(6,1));
        labelPanel.add(firstNameLabel);
        labelPanel.add(lastNameLabel);
        labelPanel.add(dobLabel);
        labelPanel.add(emailLabel);
        labelPanel.add(passwordLabel);
        labelPanel.add(confirmPasswordLabel);
        
        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new GridLayout(6,1));
        fieldPanel.add(firstNameField);
        fieldPanel.add(lastNameField);
        fieldPanel.add(dobField);
        fieldPanel.add(emailField);
        fieldPanel.add(passwordField);
        fieldPanel.add(confirmPasswordField);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(registerUserButton);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(labelPanel, BorderLayout.WEST);
        mainPanel.add(fieldPanel, BorderLayout.EAST);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.setBorder(new EmptyBorder(20,10,10,10));

        return mainPanel;
    }
    

    /* 
     * Event handler method of the class for the Register User button
     * 
     * @param e of type <code>ActionEvent</code>
     * @return void
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        
        String action = e.getActionCommand();
        ArrayList<String> messages = initSTMS.isInputValid(firstNameField.getText(), 
                                    lastNameField.getText(),
                                    dobField.getText(),
                                    emailField.getText(),
                                    passwordField.getText());
        
        if(action.equalsIgnoreCase("Register User")) {
            
            // check if the password and the confirm password fields matches
            if(passwordField.getText().equals(confirmPasswordField.getText())){
                
                if (!messages.isEmpty()){
                    StringBuilder builder = new StringBuilder(messages.size());
                    
                    for (int i=0;i<messages.size();builder.append(messages.get(i++))) builder.append("\n");
                        JOptionPane.showMessageDialog(null, builder.toString(), "Invalid Input", 
                                                        JOptionPane.ERROR_MESSAGE);
                } 
                else {
                    try {
                        initSTMS.createUser(firstNameField.getText(),
                                lastNameField.getText(),
                                dobField.getText(),
                                emailField.getText(),
                                passwordField.getText(), null);

                        initSTMSGUI.disposeInternalFrame("User Registration");
                        JOptionPane.showMessageDialog(this, "User is registered Successfully", "Success Registration", JOptionPane.INFORMATION_MESSAGE);
                        
                        initSTMSGUI.displayLog("NOTIFICATION: " + firstNameField.getText() + " " + lastNameField.getText() + " is registered Successfully.");
                    } 
                    catch (IOException ioexception){
                        initSTMSGUI.displayLog("EXCEPTION: " + ioexception.toString());
                    }
                }                
            }            
            else {
                JOptionPane.showMessageDialog(this, "Incorrect Password Match", "Password Mismatch", JOptionPane.ERROR_MESSAGE);
            }
        }

    }
 }


