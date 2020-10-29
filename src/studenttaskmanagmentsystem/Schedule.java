/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studenttaskmanagmentsystem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;
import java.util.HashMap;
import javax.swing.JOptionPane;

/**
 *
 * @author Fetya Muzeyen
 */
public class Schedule extends Thread{
    
    private int userID;
    private String day;
    private int startTimeHH;
    private int startTimeMM;
    private int assignmentID;
    private Assignment assignment;
    
    public static final String[] DATE_VALUES = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    
    Schedule(int userID, String day, int startTimeHH,int startTimeMM, int assignmentID)
    {
        this.userID = userID;
        this.day = day;
        this.startTimeHH = startTimeHH;
        this.startTimeMM = startTimeMM;
        this.assignmentID = assignmentID;
    }
    
    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }
    
    public void setDay(String day) {
        this.day = day;
    }
    
    public void setStartTimeHH(int startTimeHH){
        this.startTimeHH = startTimeHH;
    }
    
    public void setStartTimeMM(int startTimeMM){
        this.startTimeMM = startTimeMM;
    }
    
    public Assignment getAssignment() {
        return this.assignment;
    }
    
    public int getUserID(){
        return this.userID;
    }
    
    public String getDay(){
        return this.day;
    }
    
    public int getStartTimeHH(){
        return this.startTimeHH;
    }
    
    public int getStartTimeMM(){
        return this.startTimeMM;
    }
    
    public int getAssignmentID(){
        return this.assignmentID;
    }
    

    
    public static DayOfWeek getCurrentDayOfWeek()
    {
        return DayOfWeek.from(LocalDate.now());
    }


    
    public String getStartTimeHHmm() {
        return String.valueOf(this.getStartTimeHH())
                + String.valueOf(this.getStartTimeMM());
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
    
    
    @Override
    public void run() {       
        int startTimeMM;
        
        String currentTimeHH, currentTimeMM, assignmentStartTime, message;
        
        boolean countdownStart = false;
        boolean FIVE = true;
        boolean TEN = true;
        boolean FIFTEEN = true;
        boolean THIRTY = true;
               
        // Date format to display the Hour and Minutes only
        DateFormat currentHourFormat = new SimpleDateFormat("HH");
        DateFormat currentMinuteFormat = new SimpleDateFormat("mm");
        
        synchronized (this) {

            while(assignment.getStatus().equalsIgnoreCase("Open")) {
                
            // Store the current Time Hour only
            currentTimeHH = currentHourFormat.format(new Date(System.currentTimeMillis()));

            // Store the current Time Minute only
            currentTimeMM = currentMinuteFormat.format(new Date(System.currentTimeMillis()));
                                
                if(Integer.parseInt(currentTimeHH) > this.getStartTimeHH())
                    assignment.setStatus("In Progress"); 
                else if(Integer.parseInt(currentTimeHH) == this.getStartTimeHH()) {
                    
                    countdownStart = true;
                            
                    if( Integer.parseInt(currentTimeMM) >= this.getStartTimeMM()) {
                            assignment.setStatus("In Progress"); 
                        }
                    else 
                        assignment.setStatus("Open");
                }
                else
                    assignment.setStatus("Open");
                
                startTimeMM = this.getStartTimeMM() - Integer.parseInt(currentTimeMM);
                
                if(countdownStart) {                    
                    if(startTimeMM == 5 && FIVE){
                        popAlert("Assignment " + assignment.getAssignmentName() + " starts in " + startTimeMM + " minutes.");
                        FIVE  = false;
                    }
                    else if(startTimeMM == 10 && TEN) {
                        popAlert("Assignment " + assignment.getAssignmentName() + " starts in " + startTimeMM + " minutes.");
                        TEN  = false;
                    } 
                    else if(startTimeMM == 15 && FIFTEEN) {
                        popAlert("Assignment " + assignment.getAssignmentName() + " starts in " + startTimeMM + " minutes.");
                        FIFTEEN  = false;
                    } 
                    else if(startTimeMM == 30 && THIRTY) {
                        popAlert("Assignment " + assignment.getAssignmentName() + " starts in " + startTimeMM + " minutes.");
                        THIRTY  = false;
                    }                    
                }                
            }            
        }
    }
    
    public void popAlert(String message) {        
        JOptionPane.showMessageDialog(null, message, "Assgnment Deadline Alert", JOptionPane.INFORMATION_MESSAGE);
    }
}
