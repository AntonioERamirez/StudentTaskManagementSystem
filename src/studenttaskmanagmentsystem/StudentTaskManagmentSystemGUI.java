/*
 * StudentTaskManagmentSystemGUI.java - This class is used to build the GUI interface for the main Frame. 
 * Date: 14/04/2020
 * @author Fetya Juhar
 *
 * Final Project: Student Task Managment System
 */
package studenttaskmanagmentsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

/*
 * Class extends <code>JFrame</code> and implements <code>ActionListener</code>
 */
public class StudentTaskManagmentSystemGUI extends JFrame implements ActionListener {
    
    // GUI component variables
    private JMenu fileMenu, assignmentMenu, availabilityMenu, myaccountMenu, helpMenu;
    private JMenuItem registrationMenuItem, resetPasswordMenuItem, loginMenuItem, dashboardMenuItem, saveToFileMenuItem,
            loadFromFileMenuItem, logoutMenuItem, editAvailabilityMenuItem, addAssignmentMenuItem, editAssignmentMenuItem, deleteAssignmentMenuItem,
            overdueAssignmentMenuItem, accountMenuItem, userGuideMenuItem, aboutMenuItem;
    private JDesktopPane jdpDesktop;
    private JTabbedPane tabbedPane;
    private JTextArea logTextArea;
    
    private StudentTaskManagmentSystem initSTMS;
    
    // variable for the Internal Frames
    private UserLoginGUI userLoginGUI;
    private RegisterUserGUI registerUserGUI;
    private ResetPasswordGUI resetPasswordGUI;
    private EditAvailabilityGUI editAvailabilityGUI;
    private AddAssignmentGUI addAssignmentGUI;
    private EditAssignmentGUI editAssignmentGUI;
    private DeleteAssignmentGUI deleteAssignmentGUI;
    private OverdueAssignmentGUI overdueAssignmentGUI;
    private MyAccountGUI myAccountGUI;
    private DashboardGUI dashboardGUI;
    
    StudentTaskManagmentSystemGUI() throws IOException {
        super("Students Schedule Alert System");
        
        initSTMS = new StudentTaskManagmentSystem(this);
                
        // Make the main window positioned as 50 pixels from each edge of the
        // screen.
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset, screenSize.width - inset * 2, screenSize.height - inset * 2);
        
        // Add a Window Exit Listener
        addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                        System.exit(0);
                }
        });
        
        // Create and Set up the GUI.
        jdpDesktop = new JDesktopPane();
        // A specialized layered pane to be used with JInternalFrames
        
        //setContentPane(jdpDesktop);
        
        logTextArea = new JTextArea(4, 1);
        logTextArea.setEditable(false);
        logTextArea.setFont(new Font("Courier", Font.PLAIN, 11));
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.addTab("Log Status", new JScrollPane(logTextArea));
        
        this.setLayout(new BorderLayout());
        this.add(jdpDesktop, BorderLayout.CENTER);
        this.add(tabbedPane, BorderLayout.SOUTH);
        this.setJMenuBar(createMenuBar());        
        
        // Make dragging faster by setting drag mode to Outline
        jdpDesktop.putClientProperty("JDesktopPane.dragMode", "outline");
    }
   
    /*
     * This method build the Menubar of the Frame
     *
     * @return lastName of type <code>String</code>
     */
    public JMenuBar createMenuBar() {      
        
        JMenuBar menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        assignmentMenu = new JMenu("Assignment");
        availabilityMenu = new JMenu("Availability");
        myaccountMenu = new JMenu("My Account");
        helpMenu = new JMenu("Help");
        
        // add keyboard event trigers for the Menu
        fileMenu.setMnemonic(KeyEvent.VK_F);
        assignmentMenu.setMnemonic(KeyEvent.VK_A);
        availabilityMenu.setMnemonic(KeyEvent.VK_V);
        myaccountMenu.setMnemonic(KeyEvent.VK_M);
        helpMenu.setMnemonic(KeyEvent.VK_H);       
        
        
        registrationMenuItem = new JMenuItem("User Registration");
        resetPasswordMenuItem = new JMenuItem("Reset Password");
        loginMenuItem = new JMenuItem("Login");
        dashboardMenuItem = new JMenuItem("Dashboard");
        saveToFileMenuItem = new JMenuItem("Save to File");
        loadFromFileMenuItem = new JMenuItem("Load from File");
        logoutMenuItem = new JMenuItem("Logout");

        
        editAvailabilityMenuItem = new JMenuItem("Edit Availability");
        addAssignmentMenuItem = new JMenuItem("Add Assignment");
        editAssignmentMenuItem = new JMenuItem("Edit Assignment");
        deleteAssignmentMenuItem = new JMenuItem("Delete Assignment");
        overdueAssignmentMenuItem = new JMenuItem("Overdue Assignment");

        accountMenuItem = new JMenuItem("Edit/View Account");        
        
        userGuideMenuItem = new JMenuItem("User Guide");
        aboutMenuItem = new JMenuItem("About this Application");
        
        // add keyboard event triger for the MenuItems
        registrationMenuItem.setMnemonic(KeyEvent.VK_U);
        resetPasswordMenuItem.setMnemonic(KeyEvent.VK_R);
        loginMenuItem.setMnemonic(KeyEvent.VK_L);
        dashboardMenuItem.setMnemonic(KeyEvent.VK_D);
        saveToFileMenuItem.setMnemonic(KeyEvent.VK_S);
        loadFromFileMenuItem.setMnemonic(KeyEvent.VK_O);
        logoutMenuItem.setMnemonic(KeyEvent.VK_L);
        addAssignmentMenuItem.setMnemonic(KeyEvent.VK_T);
        editAssignmentMenuItem.setMnemonic(KeyEvent.VK_E);
        deleteAssignmentMenuItem.setMnemonic(KeyEvent.VK_D);
        overdueAssignmentMenuItem.setMnemonic(KeyEvent.VK_O);        
        accountMenuItem.setMnemonic(KeyEvent.VK_A);
        userGuideMenuItem.setMnemonic(KeyEvent.VK_U);
        aboutMenuItem.setMnemonic(KeyEvent.VK_A);
        
        // add action listners for the MenuItems
        loginMenuItem.addActionListener(this);
        registrationMenuItem.addActionListener(this);
        resetPasswordMenuItem.addActionListener(this);
        loginMenuItem.addActionListener(this);
        dashboardMenuItem.addActionListener(this);
        saveToFileMenuItem.addActionListener(this);
        loadFromFileMenuItem.addActionListener(this);
        logoutMenuItem.addActionListener(this);
        editAvailabilityMenuItem.addActionListener(this);
        addAssignmentMenuItem.addActionListener(this);
        editAssignmentMenuItem.addActionListener(this);
        deleteAssignmentMenuItem.addActionListener(this);
        overdueAssignmentMenuItem.addActionListener(this);       
        accountMenuItem.addActionListener(this);
        userGuideMenuItem.addActionListener(this);
        aboutMenuItem.addActionListener(this);
                                     
        fileMenu.add(registrationMenuItem);
        fileMenu.add(resetPasswordMenuItem);
        fileMenu.add(loginMenuItem);
        fileMenu.add(dashboardMenuItem);
        fileMenu.add(saveToFileMenuItem);
        fileMenu.add(loadFromFileMenuItem);
        fileMenu.add(logoutMenuItem);

        availabilityMenu.add(editAvailabilityMenuItem);
        
        assignmentMenu.add(addAssignmentMenuItem);
        assignmentMenu.add(editAssignmentMenuItem);
        assignmentMenu.add(deleteAssignmentMenuItem);
        assignmentMenu.add(overdueAssignmentMenuItem); 
        
        myaccountMenu.add(accountMenuItem);
        
        helpMenu.add(userGuideMenuItem);
        helpMenu.add(aboutMenuItem);
        
        menuBar.add(fileMenu);
        menuBar.add(assignmentMenu);
        menuBar.add(availabilityMenu);
        menuBar.add(myaccountMenu);
        menuBar.add(helpMenu);
        
        // set the visibility of the Menu Bar prior to the user login
        visibility(true);
        
        return menuBar;
        
    }
        
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException{
        
        StudentTaskManagmentSystemGUI frame = new StudentTaskManagmentSystemGUI();
	frame.setVisible(true);

        
        /*
        Notification notification = new Notification("STMS Alerts");
        new Thread(notification,"notification").start();
        */
    }
    

    /* 
     * Event handler method of the class for the MenuItems
     * 
     * @param e of type <code>ActionEvent</code>
     * @return void
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        
        String action = e.getActionCommand();
   
        if(action.equalsIgnoreCase("User Registration")){
            registerUserGUI = new RegisterUserGUI(this, initSTMS);
            LaunchInternalFrame(registerUserGUI);
        }
        else if(action.equalsIgnoreCase("Reset Password")){
            resetPasswordGUI = new ResetPasswordGUI(this, initSTMS);
            LaunchInternalFrame(resetPasswordGUI);
        }
        else if(action.equalsIgnoreCase("Login")){
            userLoginGUI = new UserLoginGUI(this, initSTMS);
            LaunchInternalFrame(userLoginGUI);         
        }
        else if(action.equalsIgnoreCase("Dashboard")){
            dashboardGUI = new DashboardGUI(this, initSTMS);
            LaunchInternalFrame(dashboardGUI);
        }
        else if(action.equalsIgnoreCase("Save to File")){
            // TO DO function
            // initSTMS.saveToFile();
            JOptionPane.showMessageDialog(this, "This functionality is under construction.", 
                                                "Save To File", 
                                                JOptionPane.INFORMATION_MESSAGE);            
        }
        else if(action.equalsIgnoreCase("Load from File")){
            // TO DO function
            // initSTMS.loadFromFile();
            JOptionPane.showMessageDialog(this, "This functionality is under construction.", 
                                                "Load From File", 
                                                JOptionPane.INFORMATION_MESSAGE); 
        }
        else if(action.equalsIgnoreCase("Logout")){
            visibility(true);
            disposeInternalFrames();
            
            this.displayLog("NOTIFICATION: " + initSTMS.getLoggedUser().getEmail() + " logged out successfully.");
        }
         else if(action.equalsIgnoreCase("Edit Availability")){
            editAvailabilityGUI = new EditAvailabilityGUI(this, initSTMS);
            LaunchInternalFrame(editAvailabilityGUI);

        }
        else if(action.equalsIgnoreCase("Add Assignment")){
            addAssignmentGUI = new AddAssignmentGUI(this, initSTMS);
            LaunchInternalFrame(addAssignmentGUI);
        }
        else if(action.equalsIgnoreCase("Edit Assignment")){
            editAssignmentGUI = new EditAssignmentGUI(this, initSTMS);
            LaunchInternalFrame(editAssignmentGUI);
        }
        else if(action.equalsIgnoreCase("Delete Assignment")){
            deleteAssignmentGUI = new DeleteAssignmentGUI(this, initSTMS);
            LaunchInternalFrame(deleteAssignmentGUI);
        }
        else if(action.equalsIgnoreCase("Overdue Assignment")){
            overdueAssignmentGUI = new OverdueAssignmentGUI(this, initSTMS);
            LaunchInternalFrame(overdueAssignmentGUI);
        }
        else if(action.equalsIgnoreCase("Edit/View Account")){
            myAccountGUI = new MyAccountGUI(this, initSTMS);
            LaunchInternalFrame(myAccountGUI);
        }
        else if(action.equalsIgnoreCase("User Guide")){
            // TO DO function
            JOptionPane.showMessageDialog(this, "User Guide Compiled HTML underconstruction", "User Guide", JOptionPane.INFORMATION_MESSAGE);
        }
        else if(action.equalsIgnoreCase("About this Application")){
            // TO DO function 
            JOptionPane.showMessageDialog(this, "Student Task Managment System v1.0\n Java: 10.0.1; \nRuntime: Java(TM) SE Runtime Environment 10.0.1+", "About this Application", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /*
     * This method is used to launch the internal frames
     *
     * @param frame of type <code>JInternalFrame</code>
     * @return void
     */
    public void LaunchInternalFrame(JInternalFrame frame){
        
       
        frame.show();
        jdpDesktop.add(frame);
        Dimension desktopSize = this.getSize();
        Dimension jInternalFrameSize = frame.getSize();
        frame.setLocation((desktopSize.width - jInternalFrameSize.width)/2, (desktopSize.height- jInternalFrameSize.height)/3);
        
        try 
        {
            frame.setSelected(true);
        } 
        catch (java.beans.PropertyVetoException e) {
            this.displayLog("Error occured whilst openning the internal frame.");
        }
    }
    
    /*
     * This method is used to set the visiblity of the Menu and MenuItem prior and after login
     * or it used to set the visiblity after logout
     *
     * @param visibility of type <code>boolean</code>
     * @return void
     */
    public void visibility(boolean visibility){
        
        registrationMenuItem.setVisible(visibility);
        resetPasswordMenuItem.setVisible(visibility);
        loginMenuItem.setVisible(visibility);
        
        dashboardMenuItem.setVisible(!visibility);
        saveToFileMenuItem.setVisible(!visibility);
        loadFromFileMenuItem.setVisible(!visibility);
        logoutMenuItem.setVisible(!visibility);                
        assignmentMenu.setVisible(!visibility);
        myaccountMenu.setVisible(!visibility);
        availabilityMenu.setVisible(!visibility);        
    }
    
    /*
     * This method is used to remove all the internal frames from the desktop pane
     *
     * @return void
     */
    public void disposeInternalFrames(){
        jdpDesktop.removeAll();
        jdpDesktop.updateUI();
    }
    
    /*
     * This method is used to remove a particular internal frames from the desktop pane
     *
     * @param frameName of type <code>String</code>
     * @return void
     */
    public void disposeInternalFrame(String frameName){
        
        if(frameName.equals("User Registration"))
            jdpDesktop.remove(registerUserGUI);
        else if(frameName.equals("Reset Password"))
            jdpDesktop.remove(resetPasswordGUI);
        else if(frameName.equals("Login"))
            jdpDesktop.remove(userLoginGUI);
        else if(frameName.equals("Edit Availability"))
            jdpDesktop.remove(editAvailabilityGUI);
        else if(frameName.equals("Add Assignment"))
            jdpDesktop.remove(addAssignmentGUI);
        else if(frameName.equals("Edit Assignment"))
            jdpDesktop.remove(editAssignmentGUI);
        else if(frameName.equals("Delete Assignment"))
            jdpDesktop.remove(deleteAssignmentGUI);
        else if(frameName.equals("Update Account"))
            jdpDesktop.remove(myAccountGUI);
        
        jdpDesktop.updateUI();        

    }

    public void displayLog(String logText) {
        
        logTextArea.append(logText + "\n");
    }
}