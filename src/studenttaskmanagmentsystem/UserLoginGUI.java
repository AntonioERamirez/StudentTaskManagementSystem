/*
 * UserLoginGUI.java - This class builds the GUI interface for User Login. 
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
import java.io.FileNotFoundException;
import java.io.IOException;

/*
 * Class extends <code>JInternalFrame</code> and implements <code>ActionListener</code>
 */
public class UserLoginGUI extends JInternalFrame implements ActionListener{
    
    // GUI component variables
    private JLabel emailLabel, passwordLabel;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    
    private StudentTaskManagmentSystem initSTMS;
    private StudentTaskManagmentSystemGUI initSTMSGUI;
    
    /*
     * This constructor takes two parameters, initSTMSGUI and initSTMS in order to push any amendments to the main GUI frame
     * and access from the data layer respectively.
     * 
     * @param initSTMSGUI of type <code>StudentTaskManagmentSystemGUI</code>
     * @param initSTMS of type <code>StudentTaskManagmentSystem</code>
     */
    UserLoginGUI(StudentTaskManagmentSystemGUI initSTMSGUI, StudentTaskManagmentSystem initSTMS){
        
        super("User Login", true, // resizable
                            true, // closable
                            true, // maximizable
                            true);// iconifiable
        
        this.initSTMSGUI = initSTMSGUI; // main window frame
        this.initSTMS = initSTMS;       // object that access from the data layer

        setLayout(new FlowLayout());
        
        // initialize and add the GUI interface
        add(initGUI());        
        setSize(320, 150);
    }           

    /* 
     * This method initialise and build the GUI interface, and hook event handler for the login button
     * 
     * @return mainPanel of type <code>JPanel</code>
     */
    public JPanel initGUI(){
                
        emailLabel = new JLabel("E-Mail:");
        passwordLabel = new JLabel("Password:");
        
        emailField = new JTextField(20);
        passwordField=new JPasswordField();
        
        loginButton = new JButton("Login"); 
        loginButton.addActionListener(this);
        
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new GridLayout(2,1));
        labelPanel.add(emailLabel);
        labelPanel.add(passwordLabel);
        
        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new GridLayout(2,1));
        fieldPanel.add(emailField);
        fieldPanel.add(passwordField);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(loginButton);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(labelPanel, BorderLayout.WEST);
        mainPanel.add(fieldPanel, BorderLayout.EAST);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.setBorder(new EmptyBorder(15,10,10,10));
        
        return mainPanel;
    }
    
    /* 
     * Event handler method of the class for the Login button
     * 
     * @param e of type <code>ActionEvent</code>
     * @return void
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        
        String action = e.getActionCommand();
        
        if(action.equalsIgnoreCase("Login")) {

            // check the user credentials, viz. email & password combination
            try{
                
                if (initSTMS.checkUserCredentials(emailField.getText(), passwordField.getText())) {
                    
                    initSTMSGUI.visibility(false);
                    initSTMSGUI.disposeInternalFrames();
                    initSTMSGUI.displayLog("NOTIFICATION: User successfully logged into the system with E-mail " + emailField.getText());
                    
                    try {
                        initSTMS.LoadAssignmentData(initSTMS.getLoggedUser().getUserID());
                    } 
                    catch (FileNotFoundException ex) {
                        initSTMSGUI.displayLog("ERROR MESSAGE: Assignment file could NOT be found.");
                    } 
                    catch (IOException ex) {                        
                        initSTMSGUI.displayLog("ERROR MESSAGE: Assignment file could NOT be found.");
                    }
                } 
                else {
                    JOptionPane.showMessageDialog(this, "Incorrect E-mail & Password Provided", "Wrong Credentials", 
                                                        JOptionPane.ERROR_MESSAGE);
                    initSTMSGUI.displayLog("ERROR MESSAGE: Incorrect E-mail & Password Provided.");
                }
            } 
            catch (IOException ex){
                initSTMSGUI.displayLog("Error occured whilst trying to login.");
            }
        }
    }
}
