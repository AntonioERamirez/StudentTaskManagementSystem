/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studenttaskmanagmentsystem;

/**
 *
 * @author nldalrymple
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Class extends <code>JInternalFrame</code> and implements <code>ActionListener</code>
 */
public class EditAvailabilityGUI  extends JInternalFrame  implements ActionListener{
    
    // GUI component variables
    private final String[] header = {"Day of the Week","Start Time","End Time"};
    private JButton editAvailabilityButton;
    private JTable availabilityTable;
 
    private DefaultTableModel model; 
   
    private StudentTaskManagmentSystem initSTMS;
    private StudentTaskManagmentSystemGUI initSTMSGUI;
    
    /*
     * This constructor takes two parameters, initSTMSGUI and initSTMS in order to push any amendments to the main GUI frame
     * and access from the data layer respectively.
     * 
     * @param initSTMSGUI of type <code>StudentTaskManagmentSystemGUI</code>
     * @param initSTMS of type <code>StudentTaskManagmentSystem</code>
     */
    EditAvailabilityGUI(StudentTaskManagmentSystemGUI initSTMSGUI, StudentTaskManagmentSystem initSTMS) {
        
        super("Edit Availability", true, // resizable
                                true, // closable
                                true, // maximizable
                                true);// iconifiable
        
        this.initSTMSGUI = initSTMSGUI; // main window frame
        this.initSTMS = initSTMS;       // object that access from the data layer
        
        setLayout(new GridLayout(1,1));
        
        // initialize and add the GUI interface.
        add(initGUI());     
        setSize(600, 325);
    }
    
    /* 
     * This method initialise and build the GUI interface
     * 
     * @return mainPanel of type <code>JPanel</code>
     */
    public JPanel initGUI(){
        
        model = new DefaultTableModel(header, 0);
        
        availabilityTable = new JTable(model){
        @Override
        public boolean isCellEditable(int row, int column) {
              return column == 1 || column == 2;
            }
        };
        
        JScrollPane anotherSP = new JScrollPane(availabilityTable);
        anotherSP.getViewport().setViewPosition(new Point(0,0));
          
        try {
            populateAvailability();
        } 
        catch (FileNotFoundException ex) {
             JOptionPane.showMessageDialog(this, "No availability file found.", "Error", JOptionPane.ERROR_MESSAGE);
             initSTMSGUI.displayLog("ERROR MESSAGE: No availability file found.");
        }
        
        editAvailabilityButton = new JButton("Edit Availability");        
        editAvailabilityButton.addActionListener(this);   // hook the action listener for 
        
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1,1));
        topPanel.add(anotherSP);
   
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(editAvailabilityButton);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(topPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        return mainPanel;   // return the final organized panel
    }
    
    public void populateAvailability() throws FileNotFoundException {
        
        Scanner input = new Scanner(new File("C:\\StudentAlert\\profiles\\" +   
                initSTMS.getLoggedUser().getUserID() +"\\availability.csv"));
        
        input.useDelimiter("[,\\n]");
   
        Availability[] records = new Availability[0];
        
        while(input.hasNext()) {
            String weekDay = input.next();
            String startTime = input.next();
            String endTime = input.next();

            Object[] rows = {weekDay,startTime,endTime};
            model.addRow(rows);

            Availability newRecord = new Availability(weekDay,startTime,endTime);
            records = addRecord(records, newRecord);
        }

 }


    private static Availability[] addRecord(Availability[] records, Availability recordToAdd) {
        
        Availability[] newRecords = new Availability[records.length + 1];
        System.arraycopy(records, 0, newRecords, 0, records.length);
        newRecords[newRecords.length - 1] = recordToAdd;

        return newRecords;
    }
         

    public boolean  validateInput() {
        
        String regex = "(\\d{1,2}\\:\\d{2}\\s(?:AM|PM|am|pm))";

        Pattern pattern = Pattern.compile(regex);
        boolean matches = true;
        Integer invalid = 0;

        for(int i = 0; i < availabilityTable.getRowCount(); i++){
            
            //loop for jtable column
            for(int j = 1; j < availabilityTable.getColumnCount(); j++){
                  Matcher matcher = pattern.matcher(availabilityTable.getModel().getValueAt(i,j).toString()); 
                 matches = matcher.matches();
                       if(!matches){
                      invalid += 1;  
                }
            }
       } 
        
        return invalid <= 0;
     }
              
    /* 
     * Event handler method of the class for the Edit Assignment button
     * 
     * @param e of type <code>ActionEvent</code>
     * @return void
     */
    @Override
    public void actionPerformed(ActionEvent e) { 
                
         boolean editStatus = false;
         String action = e.getActionCommand();
        
        
        if (!availabilityTable.isEditing()) {
        } 
        else {
            availabilityTable.getCellEditor().stopCellEditing();
        }          

        if(action.equalsIgnoreCase("Edit Availability")){

        //  Validate input before writing to file    
        if (!validateInput()) {
            JOptionPane.showMessageDialog(this, "Values must be in time format (##:## AM/PM).", "Error Updating", JOptionPane.ERROR_MESSAGE);
            initSTMSGUI.displayLog("ERROR MESSAGE: Values must be in time format (##:## AM/PM).");
        }
        else {

            try {

                String filename = "C:\\StudentAlert\\profiles\\" + initSTMS.getLoggedUser().getUserID() + "\\availability.csv";

                BufferedWriter out = new BufferedWriter(new FileWriter(filename));

                //loop for jtable rows
                for(int i = 0; i < availabilityTable.getRowCount(); i++){
                   //loop for jtable column
                   for(int j = 0; j < availabilityTable.getColumnCount(); j++){
                       out.write(availabilityTable.getModel().getValueAt(i,j).toString());
                       if(j < availabilityTable.getColumnCount()-1) {
                         out.write(","); 
                       }
                   }

                out.write("\n");
                }

                out.close();
                editStatus = true;

            } 
            catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error updating availability.", "Error Updating", JOptionPane.ERROR_MESSAGE);
                initSTMSGUI.displayLog("ERROR MESSAGE: Error updating availability.");
            }

                if(editStatus){
                    try{
                        initSTMS.scheduleBuilder();
                    } catch (IOException ex){
                        System.out.println(ex);
                    }
                    initSTMSGUI.disposeInternalFrame("Edit Availability");    // dispose the Edit Availability Internal Frame
                    JOptionPane.showMessageDialog(this, "Availability is edited successfully", "Success Updating", JOptionPane.INFORMATION_MESSAGE);
                    initSTMSGUI.displayLog("NOTIFICATION: Availability is edited successfully.");
                }
                else {     
                    JOptionPane.showMessageDialog(this, "Error occured whilst Editing the availability.", "Error Updating", JOptionPane.ERROR_MESSAGE);
                    initSTMSGUI.displayLog("ERROR MESSAGE: Error occured whilst editing the availability.");
                }
            }
        }
    }  
}