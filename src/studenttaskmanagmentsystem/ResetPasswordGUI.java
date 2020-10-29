/*
 * UserLoginGUI.java - This class builds the GUI interface for User Login. 
 * Date: 15/04/2020
 * @author Fetya Juhar
 *
 * Final Project: Student Task Managment System
 */
package studenttaskmanagmentsystem;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

/*
 * Class extends <code>JInternalFrame</code> and implements <code>ActionListener</code>
 */
public class ResetPasswordGUI extends JInternalFrame implements ActionListener{
    
    // GUI component variables
    private JLabel emailLabel;
    private JTextField emailField;
    private JButton resetButton;
    
    private StudentTaskManagmentSystem initSTMS;
    private StudentTaskManagmentSystemGUI initSTMSGUI;
    
    /*
     * This constructor takes two parameters, initSTMSGUI and initSTMS in order to push any amendments to the main GUI frame
     * and access from the data layer respectively.
     * 
     * @param initSTMSGUI of type <code>StudentTaskManagmentSystemGUI</code>
     * @param initSTMS of type <code>StudentTaskManagmentSystem</code>
     */
    ResetPasswordGUI(StudentTaskManagmentSystemGUI initSTMSGUI, StudentTaskManagmentSystem initSTMS){
        
        super("Reset Password", true, // resizable
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
     * This method initialise and build the GUI interface, and hook event handler for the Reset Password button
     * 
     * @return mainPanel of type <code>JPanel</code>
     */
    public JPanel initGUI(){
                
        emailLabel = new JLabel("E-Mail:");
        
        emailField = new JTextField(20);
        
        resetButton = new JButton("Reset Password");        
        resetButton.addActionListener(this);
        
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new GridLayout(1,1));
        labelPanel.add(emailLabel);
        
        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new GridLayout(1,1));
        fieldPanel.add(emailField);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(resetButton);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(labelPanel, BorderLayout.WEST);
        mainPanel.add(fieldPanel, BorderLayout.EAST);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.setBorder(new EmptyBorder(25,10,10,10));
        
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
        
        if(action.equalsIgnoreCase("Reset Password")){
            
            User user = initSTMS.getUser(emailField.getText());
            
            // check if the user exist, with the email provided
            if(user != null){
                
                initSTMSGUI.disposeInternalFrame("Reset Password");
                JOptionPane.showMessageDialog(this, "Password Sent for " + user.getFirstName() +
                                                    " " + user.getLastName(), 
                                                    "Passowrd Sent", 
                                                    JOptionPane.INFORMATION_MESSAGE);
                
                // TO DO: Password Reset is sent via Email feature
                // String password = user.getPassword();
                
                initSTMSGUI.displayLog("NOTIFICATION: Password is sent to " + emailField.getText());
            }
            else 
            {
                JOptionPane.showMessageDialog(this, "User E-mail couldn't be Found!", 
                                                    "User Not Found", 
                                                    JOptionPane.ERROR_MESSAGE);
                                                    
                initSTMSGUI.displayLog("ERROR MESSAGE: user E-mail " + emailField.getText() + " couldn't be found.");
            }
        }
    }
    
}
