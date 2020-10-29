/*
 * User.java - This class represents the User object. 
 * Date: 14/04/2020
 * @author Fetya Juhar
 *
 * Final Project: Student Task Managment System
 */
package studenttaskmanagmentsystem;

import java.util.*;

public class User {
    
    private String firstName, lastName, DOB, email, password, userID;
    
    private ArrayList <Assignment> assignmentList;

    Random random = new Random();

    /*
     * This constructor takes a list of parameters to build the <code>User</code> object.
     * 
     * @param firstName of the type <code>String</code>
     * @param lastName of the type <code>String</code>
     * @param DOB of the type <code>String</code>
     * @param email of the type <code>String</code>
     * @param password of the type <code>String</code>
     * @param assignmentList of the type <code>ArrayList <Assignment></code>
     */
    User(String firstName, String lastName, String DOB, String email, String password, 
            ArrayList <Assignment> assignmentList){
        
        this.firstName = firstName;
        this.lastName = lastName;
        this.DOB = DOB;
        this.email = email;
        this.password = password;
        this.assignmentList = new ArrayList<Assignment>();
        this.userID = firstName.charAt(0) + lastName.charAt(0) + Integer.toString(random.nextInt(100));
    }

    User(String firstName, String lastName, String DOB, String email, String password,
         ArrayList <Assignment> assignmentList, String userID){

        this.firstName = firstName;
        this.lastName = lastName;
        this.DOB = DOB;
        this.email = email;
        this.password = password;
        this.assignmentList = new ArrayList<Assignment>();
        this.userID = userID;
    }
    
    /*
     * This method set the <code>firstName</code> variable
     *
     * @param firstName of type <code>String</code>
     * @return void
     */
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    
    /*
     * This method set the <code>lastName</code> variable
     *
     * @param firstName of type <code>String</code>
     * @return void
     */
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    
    /*
     * This method set the <code>DOB</code> variable
     *
     * @param firstName of type <code>String</code>
     * @return void
     */
    public void setDOB(String DOB){
        this.DOB = DOB;
    }
    
    /*
     * This method set the <code>email</code> variable
     *
     * @param firstName of type <code>String</code>
     * @return void
     */
    public void setEmail(String email){
        this.email = email;
    }
    
    /*
     * This method set the <code>password</code> variable
     *
     * @param firstName of type <code>String</code>
     * @return void
     */
    public void setPassword(String password){
        this.password = password;
    }
    
    /*
     * This method set the <code>assignmentList</code> variable
     *
     * @param assignmentList of type <code>ArrayList <Assignment></code>
     * @return void
     */
    public void setAssignmentList(ArrayList <Assignment> assignmentList){
        this.assignmentList = assignmentList;
    }
    
    /*
     * This method add Assignment to the <code>assignmentList</code> variable
     *
     * @param assignment of type <code>Assignment</code>
     * @return void
     */
    public void addAssignment(Assignment assignment){
        assignmentList.add(assignment);
    }
    
    /*
     * This method return the <code>assignment</code> variable, if the assgnment name matches
     *
     * @param assignmentName of type <code>String</code>
     * @return assignment of type <code>Assignment</code>
     */
    public Assignment getAssignmentByName(String assignmentName){
        
        for(Assignment assignment : this.getAssignmentList()){
            if(assignment.getAssignmentName().equals(assignmentName))
                return assignment;
        }
        
        return null;
    }
    
    /*
     * This method return the <code>assignment</code> variable, if the assgnment name matches
     *
     * @param assignmentID of type <code>int</code>
     * @return assignment of type <code>Assignment</code>
     */
    public Assignment getAssignmentByID(int assignmentID){
        
        for(Assignment assignment : this.getAssignmentList()){
            if(assignment.getAssignmentID() == assignmentID)
                return assignment;
        }
        
        return null;
    }
    
    /*
     * This method return the <code>firstName</code> variable
     *
     * @return firstName of type <code>String</code>
     */
    public String getFirstName(){
        return firstName;
    }
    
    /*
     * This method return the <code>lastName</code> variable
     *
     * @return lastName of type <code>String</code>
     */
    public String getLastName(){
        return lastName;
    }
    
    /*
     * This method return the <code>DOB</code> variable
     *
     * @return DOB of type <code>String</code>
     */
    public String getDOB(){
        return DOB;
    }
    
    /*
     * This method return the <code>email</code> variable
     *
     * @return email of type <code>String</code>
     */
    public String getEmail(){
        return email;
    }
    
    /*
     * This method return the <code>password</code> variable
     *
     * @return password of type <code>String</code>
     */
    public String getPassword(){
        return password;
    }
    
    /*
     * This method return the <code>assignmentList</code> variable
     *
     * @return assignmentList of type <code>ArrayList <Assignment></code>
     */
    public ArrayList <Assignment> getAssignmentList(){
        return this.assignmentList;
    }

    public String getUserID(){
        return this.userID;
    }
    
    
    public ArrayList<Assignment> getOverdueAssignmentList() {
        
        ArrayList<Assignment> overdueAssignmentList = new ArrayList<Assignment>();
        
        for(Assignment assignment: this.assignmentList){
            if(assignment.getStatus().equalsIgnoreCase(Assignment.OVERDUE))
                overdueAssignmentList.add(assignment);
        }
        
        return overdueAssignmentList;
    }
    
    /*
     * This method return the String value of the User object.
     *
     * @return <code>String</code>
     */
    @Override
    public String toString(){ 
        return "First Name: " + this.firstName + 
                "\nLast Name: " + this.lastName +
                "\nDOB: " + this.DOB +
                "\nE-Mail: " + this.email +
                "\nUser ID: " + this.userID +
                "\nAssignment List:\n" + this.assignmentList.toString();
                
    }
}
