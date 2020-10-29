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
/*
 * Assignment.java - This class represents the Assignment object. 
 * Date: 14/04/2020
 * @author Fetya Juhar
 *
 * Final Project: Student Task Managment System
 */


public class Availability {
    
    private String weekDay,startTime, endTime;
    
    /*
     * This constructor takes a list of parameters to build the <code>Availability</code> object.
     * 
     * @param weekDay of the type <code>String</code>
     * @param startTime of the type <code>String</code>
     * @param endTime of the type <code>String</code>
     */
    Availability(String weekDay, String startTime, String endTime){
        
        this.weekDay = weekDay;
        this.startTime = startTime;
        this.endTime = endTime;
      
    }
    
    /*
     * This method set the <code>weekDay</code> variable
     *
     * @param weekDay of type <code>String</code>
     * @return void
     */
    public void setweekDay(String weekDay){
        this.weekDay = weekDay;
    }
    
   
    /*
     * This method set the <code>startTime</code> variable
     *
     * @param startTime of type <code>String</code>
     * @return void
     */
    public void setStartTime(String startTime){
        this.startTime = startTime;
    }
    
    
    /*
     * This method set the <code>endTime</code> variable
     *
     * @param endTime of type <code>String</code>
     * @return void
     */
    public void setEndTime(String endTime){
        this.endTime = endTime;
    }
    
  
    
    /*
     * This method return the <code>weekDay</code> variable
     *
     * @return weekDay of type <code>String</code>
     */
    public String getweekDay(){
        return this.weekDay;
    }
    
   
    /*
     * This method return the <code>startTime</code> variable
     *
     * @return startTime of type <code>String</code>
     */
    public String getStartTime(){
        return this.startTime;
    }
    
   
    
    /*
     * This method return the <code>endTime</code> variable
     *
     * @return endTime of type <code>String</code>
     */
    public String getEndTime(){
        return this.endTime;
    }
   
    
    /*
     * This method return the String value of the Availability object.
     *
     * @return <code>String</code>
     */
    @Override
    public String toString(){ 
        return  this.weekDay +
              
               this.startTime +
             
               this.endTime;
    }
}
