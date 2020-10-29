/*
 * Assignment.java - This class represents the Assignment object. 
 * Date: 14/04/2020
 * @author Fetya Juhar
 *
 * Final Project: Student Task Managment System
 */
package studenttaskmanagmentsystem;


public class Assignment {
    
    private String  assignmentName, dueDate, etaTime, status, labelColor, description, priority;
    private Integer assignmentID;
    private boolean scheduled;
    
    // Assignment status is set as "Open" when a new assignment object is created by default
    // The status will be changed automatically to "In Progress" as soon as the assignment pass the start date and time. 
    // The status of the assignment will be automatically set as "Overdue" if it is not completed before the end date and time.
    // User will be able to change the other remaining status manually, viz. "Completed" & "Cancelled". 
    public static final String[] STATUS_VALUES = {"Open", "In Progress", "Completed", "Overdue", "Cancelled"};
   
    // These numbers will be used in the Estimated Time to Complete selection
    public static final String[] ETA_VALUES = {"0.5","1.0", "1.5", "2.0", "2.5", "3.0", "3.5","4.0", "4.5", "5.0","5.5", "6.0"};
    
    // These colors will enable to identify the assignment easily.
    public static final String[] COLOR_VALUES = {"Red", "Yellow", "Blue","Green","Orange","Pink"};

    public static final String[] PRIORITY_VALUES = {"1", "2", "3"};

    public static final String[] DUE_DATE_VALUES = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    public static final String OVERDUE = "Overdue";
    
    public static int ASSIGNMENT_ID_COUNT = 1;
   
    
    /*
     * This constructor takes a list of parameters to build the <code>Assignment</code> object.
     * 
     * @param assignmentName of the type <code>String</code>
     * @param dueDate of the type <code>String</code>
     * @param etaTime of the type <code>String</code>
     * @param status of the type <code>String</code>
     * @param labelColor of the type <code>String</code>
     * @param description of the type <code>String</code>
     */
    Assignment(Integer assignmentID,String assignmentName, String dueDate, String etaTime, String labelColor, String description,String status, String priority){
        
        this.assignmentID = assignmentID;
        this.assignmentName = assignmentName;
        this.dueDate = dueDate;
        this.etaTime = etaTime;
        this.status = status;
        this.labelColor = labelColor;
        this.description = description;
        this.priority = priority;
        this.scheduled = false;
    
    }
    
    /*
     * This method set the <code>assignmentName</code> variable
     *
     * @param assignmentName of type <code>String</code>
     * @return void
     */
    public void setAssignmentName(String assignmentName){
        this.assignmentName = assignmentName;
    }
    
    /*
     * This method set the <code>startDate</code> variable
     *
     * @param startDate of type <code>String</code>
     * @return void
     */
    public void setdueDate(String dueDate){
        this.dueDate = dueDate;
    }
   
    
    /*
     * This method set the <code>etaTime</code> variable
     *
     * @param etaTime of type <code>String</code>
     * @return void
     */
    public void setETATime(String etaTime){
        this.etaTime = etaTime;
    }
    
    /*
     * This method set the <code>status</code> variable
     *
     * @param status of type <code>String</code>
     * @return void
     */
    public void setStatus(String status){
        this.status = status;
    }
    
    /*
     * This method set the <code>labelColor</code> variable
     *
     * @param labelColor of type <code>String</code>
     * @return void
     */
    public void setLabelColor(String labelColor){
        this.labelColor = labelColor;
    }
    
    /*
     * This method set the <code>description</code> variable
     *
     * @param description of type <code>String</code>
     * @return void
     */
    public void setDescription(String description){
        this.description = description;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
    
     public void setAssignmentID(int assignmentID){
        this.assignmentID = assignmentID;
    }

    public void setScheduled(boolean scheduled){
        this.scheduled = scheduled;
    }

    /*
     * This method return the <code>assignmentName</code> variable
     *
     * @return assignmentName of type <code>String</code>
     */
    public String getAssignmentName(){
        return this.assignmentName;
    }
    
    /*
     * This method return the <code>dueDate</code> variable
     *
     * @return dueDate of type <code>String</code>
     */
    public String getDueDate(){
        return this.dueDate;
    }
    
   
    
    /*
     * This method return the <code>etaTime</code> variable
     *
     * @return etaTime of type <code>String</code>
     */
    public String getETATime(){
        return this.etaTime;
    }
    
    /*
     * This method return the <code>status</code> variable
     *
     * @return status of type <code>String</code>
     */
    public String getStatus(){
       return this.status;
    }
    
    /*
     * This method return the <code>labelColor</code> variable
     *
     * @return labelColor of type <code>String</code>
     */
    public String getLabelColor(){
        return this.labelColor;
    }
    
    /*
     * This method return the <code>description</code> variable
     *
     * @return description of type <code>String</code>
     */
    public String getDescription(){
        return this.description;
    }

    public String getPriority(){
        return this.priority;
    }

    public Integer getAssignmentID(){
        return this.assignmentID;
    }

    public boolean getScheduled(){
        return this.scheduled;
    }
    
    
    public static void setAssignmentIDCounter(int idCounter){
        ASSIGNMENT_ID_COUNT = idCounter;
    }

    /*
     * This method return the String value of the Assignment object.
     *
     * @return <code>String</code>
     */
    @Override
    public String toString(){ 
        return "Assignment Name: " + this.assignmentName +
                "\nAssignment ID: " + this.assignmentID +
                "\nStart Date: " + this.dueDate +
                "\nETA Time: " + this.etaTime +
                "\nStatus: " + this.status +
                "\nLabel Color: " + this.labelColor +
                "\nPriority: " + this.priority +
                "\nDescription: " + this.description;

    }
}
