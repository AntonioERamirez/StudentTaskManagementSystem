/*
 * StudentTaskManagmentSystem.java - This class is used to validate and load data from the file data layer.
 * Date: 14/04/2020
 * @author Fetya Juhar
 *
 * Final Project: Student Task Managment System
 */
package studenttaskmanagmentsystem;

import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;


public class StudentTaskManagmentSystem{

    private User loggedUser;
    private ArrayList<User> usersList;
    private ArrayList<Schedule> scheduleList;   
    
    private StudentTaskManagmentSystemGUI initSTMSGUI;
    

    StudentTaskManagmentSystem() throws IOException {
        LoadTempUserData();
        LoadUserData();
        
        if(loggedUser != null)
            LoadAssignmentData(getLoggedUser().getUserID());
        
    }
    
    StudentTaskManagmentSystem(StudentTaskManagmentSystemGUI initSTMSGUI) throws IOException {
        LoadTempUserData();
        LoadUserData();
        
        if(loggedUser != null)
            LoadAssignmentData(getLoggedUser().getUserID());
        
        this.initSTMSGUI = initSTMSGUI;
    }

    /*
     * This method build the temporary data, and can be extended/amended to retrive data from the file system
     *
     * @return void
     */
    public void LoadTempUserData() {

//        Assignment oracleAssignment = new Assignment("Oracle", "5/11/2020", "3","Open","Red","Oracle Assignment", 1);
//        Assignment javaAssignment = new Assignment("Java", "10/12/2020", "10","Open","Blue","Java Assignment", 2);
//        Assignment cAssignment = new Assignment("C++", "12/10/2020", "13","Open","Red","C++ Assignment", 3);

//        ArrayList <Assignment> assignmentList = new ArrayList<Assignment>();
//        assignmentList.add(oracleAssignment);
//        assignmentList.add(javaAssignment);
//        assignmentList.add(cAssignment);
//        
//        User adem = new User("Adem", "Ali", "2/1/1987", "adem.ali@gmail.com", "Password", null);
//        User fetya = new User("Fetya", "Juhar", "28/11/1984", "fetya.juhar@gmail.com", "Password", null);
//        User neja = new User("Neja", "Ali", "21/10/1980", "neja.ali@gmail.com", "Password", null);

        User adminUser = new User("Admin", "Admin", "01/01/2000", "admin@admin.com", "adminpassword", null);

        // add the list of the assignment for the first user
//        adem.setAssignmentList(assignmentList);

        usersList = new ArrayList<User>();
        usersList.add(adminUser);
//        usersList.add(adem);
//        usersList.add(fetya);
//        usersList.add(neja);
    }

    public void LoadAssignmentData(String userid) throws IOException {

        ArrayList<Assignment> assignmentList = new ArrayList<>();
        String row;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("C:\\StudentAlert\\profiles\\" + userid + "\\assignments.csv"));
            
            while ((row = reader.readLine()) != null) {
                Integer assignmentID;
                String[] data = row.split(",");
                assignmentID = Integer.parseInt(data[0]);
                Assignment loadedAssignment = new Assignment(assignmentID, data[1], data[2], data[3], data[4], data[5], data[6], data[7]);
                assignmentList.add(loadedAssignment);
                
                Assignment.setAssignmentIDCounter(++assignmentID);
            }
            
            reader.close();
        } catch (FileNotFoundException fnfException) {
            System.out.println("File not Found.");
        }

        loggedUser.setAssignmentList(assignmentList);
        scheduleBuilder();
    }


    public void LoadUserData() throws NullPointerException, IOException {
        String row;
        File[] userFiles = new File("C:\\StudentAlert\\profiles").listFiles();
        if (userFiles != null) {
            for (File file : userFiles) {
                try {
                    BufferedReader reader = new BufferedReader(new FileReader("C:\\StudentAlert\\profiles\\" + file.getName() + "\\user.csv"));
                    while ((row = reader.readLine()) != null) {
                        String[] data = row.split(",");
                        User loadedUser = new User(data[1], data[2], data[3], data[4], data[5], null, data[0]);
                        usersList.add(loadedUser);
                    }
                    reader.close();
                } catch (FileNotFoundException fnfException) {
                    System.out.println("File not Found.");
                }
            }
        }
    }

    /*
     * This method set the logged users
     *
     * @param loggedUser of type <code>User</code>
     * @return void
     */
    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    /*
     * This method return the <code>usersList</code> variable
     *
     * @return firstName of type <code>String</code>
     */
    public ArrayList<User> geUsersList() {
        return usersList;
    }

    /*
     * This method return the <code>loggedUser</code> variable
     *
     * @return firstName of type <code>String</code>
     */
    public User getLoggedUser() {
        return loggedUser;
    }

    /*
     * This method checks the user crednetials, and return either <code>true</code> if found
     * or it returns <code>false</code> if the user is not in the system.
     *
     * @param email of type <code>String</code>
     * @param password of type <code>String</code>
     * @return <code>boolean</code>
     */
    public boolean checkUserCredentials(String email, String password) throws IOException{

        for (User user : usersList) {

            // check if the user credentials matches
            if (user.getEmail().equalsIgnoreCase(email) && user.getPassword().equals(password)) {
                this.setLoggedUser(user);
                return true;
            }

        }
        return false;
    }

    /*
     * This method is used to add user in the list
     *
     * @param firstName of type <code>String</code>
     * @param lastName of type <code>String</code>
     * @param DOB of type <code>String</code>
     * @param email of type <code>String</code>
     * @param password of type <code>String</code>
     * @param assignmentList of type <code>ArrayList <Assignment></code>
     * @return void
     */
    public void createUser(String firstName, String lastName, String DOB, String email, String password,
                           ArrayList<Assignment> assignmentList) throws IOException {
        User newUser = new User(firstName, lastName, DOB, email, password, assignmentList);

        usersList.add(newUser);

        createDefaultFiles(newUser);

        FileWriter writer = new FileWriter("C:\\StudentAlert\\profiles\\" + newUser.getUserID() + "\\user.csv");

        writer.append(newUser.getUserID());
        writer.append(",");
        writer.append(newUser.getFirstName());
        writer.append(",");
        writer.append(newUser.getLastName());
        writer.append(",");
        writer.append(newUser.getDOB());
        writer.append(",");
        writer.append(newUser.getEmail());
        writer.append(",");
        writer.append(newUser.getPassword());
        writer.append(",");

        writer.flush();
        writer.close();

        populateDefaultAvailability(newUser.getUserID());
    }

    /*
     * This method is used to create the default files for the new user
     *
     * @param firstName of type <code>String</code>
     * @param lastName of type <code>String</code>
     */

    public void createDefaultFiles(User user) {

        String[] filename = {"\\availability.csv", "\\assignments.csv", "\\schedule.csv", "\\user.csv"};
        String path = "C:\\StudentAlert\\profiles\\" + user.getUserID();

        File newFolder = new File(path);
        boolean bool = newFolder.mkdirs();

        if (bool) {
            System.out.println("Successfully created new folder");
        } else {
            System.out.println("Folder not created.");
        }

        for (String file : filename) {
            try {
                File currentFile = new File(path + file);
                currentFile.createNewFile();
            } catch (IOException e) {
                /* exception to go here */

            }
        }
    }

    /*
     * This method is used to create the default availability records which is daily from 8:00 am to 6:00 pm
     */

    public void populateDefaultAvailability(String userID) {

        String filename = "C:\\StudentAlert\\profiles\\" + userID + "\\availability.csv";
        String days[] = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        String record = ",8:00 AM,6:00 PM";
        try {
            try (BufferedWriter out = new BufferedWriter(new FileWriter(filename))) {
                for (String day : days) {
                    out.write(day + record + "\n");

                }
                out.close();
            }
        } catch (IOException e) {
        }
    }

    /*
     * This method is used to update the logged user data
     *
     * @param firstName of type <code>String</code>
     * @param lastName of type <code>String</code>
     * @param DOB of type <code>String</code>
     * @param email of type <code>String</code>
     * @param password of type <code>String</code>
     * @return <code>boolean</code>
     */
    public boolean updateUser(User user, String firstName, String lastName, String DOB, String email, String password) throws IOException {

//        User user = this.getUser(loggedUser.getEmail());

        // check if the user is already logged in
        if (user != null) {
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setDOB(DOB);
            user.setEmail(email);
            user.setPassword(password);

            FileWriter writer = new FileWriter("C:\\StudentAlert\\profiles\\" + user.getUserID() + "\\user.csv");

            writer.append(user.getUserID());
            writer.append(",");
            writer.append(user.getFirstName());
            writer.append(",");
            writer.append(user.getLastName());
            writer.append(",");
            writer.append(user.getDOB());
            writer.append(",");
            writer.append(user.getEmail());
            writer.append(",");
            writer.append(user.getPassword());
            writer.append(",");

            writer.flush();
            writer.close();


            return true;
        }

        return false;
    }

    /*
     * This method return user object if it is found in the userList based on the provided email
     *
     * @param email of type <code>String</code>
     * @return user of type <code>User</code>
     */
    public User getUser(String email) {

        for (User user : usersList) {
            if (user.getEmail().equalsIgnoreCase(email))
                return user;
        }

        return null;
    }

    /*
     * TO DO:" This method will be used to save the data to a file
     *
     * @return <code>boolean</code>
     */
    public boolean saveToFile() {

        // TO DO: function to save data to a files

        return true;
    }

    /*
     * TO DO:" This method will be used to load assignment data from a file
     *
     * @return <code>boolean</code>
     */
    public boolean loadFromFile() {

        // TO DO: function to Load data from files

        return true;
    }

    /*
     * This method is used to add assignment for the logged user
     *
     * @param assignmentName of type <code>String</code>
     * @param startDate of type <code>String</code>
     * @param startTime of type <code>String</code>
     * @param endDate of type <code>String</code>
     * @param endTime of type <code>String</code>
     * @param etaTime of type <code>String</code>
     * @param status of type <code>String</code>
     * @param labelColor of type <code>String</code>
     * @param description of type <code>String</code>
     * @return <code>boolean</code>
     */
    public boolean addAssignment(Integer assignmentID, String assignmentName, String dueDate, String etaTime, String labelColor, String description, String status, String priority) {

        Assignment assignment = new Assignment(assignmentID, assignmentName, dueDate, etaTime, labelColor, description, status, priority);

        // add the assignment for the currently logged user
        loggedUser.addAssignment(assignment);

        String filename = "C:\\StudentAlert\\profiles\\" + loggedUser.getUserID() + "\\assignments.csv";

        //append to existing file    
        FileWriter fileWriter;
        
        try {
            fileWriter = new FileWriter(filename, true);

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(assignmentID + "," + assignmentName + "," + dueDate + "," + etaTime + "," + labelColor + "," + description + "," + status + "," + priority + "\n");
            
            this.scheduleBuilder();
            
            bufferedWriter.close();
        } 
        catch (IOException ex) {
            Logger.getLogger(StudentTaskManagmentSystem.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (User user : usersList) {
            if (user.getEmail().equalsIgnoreCase(loggedUser.getEmail())) {

                // update the user from the list
                user.setAssignmentList(loggedUser.getAssignmentList());

                return true;
            }
        }

        return false;
    }

    /*
     * TO DO: This method is used to Update assignment for the logged user
     *
     * @param assignmentName of type <code>String</code>
     * @param startDate of type <code>String</code>
     * @param startTime of type <code>String</code>
     * @param endDate of type <code>String</code>
     * @param endTime of type <code>String</code>
     * @param etaTime of type <code>String</code>
     * @param status of type <code>String</code>
     * @param labelColor of type <code>String</code>
     * @param description of type <code>String</code>
     * @return <code>boolean</code>
     */
    public boolean editAssignment(Integer assignmentID, String assignmentName, String dueDate, String etaTime, String labelColor, String description, String status, String priority) {

        ArrayList<Assignment> assignmentList = getLoggedUser().getAssignmentList();

        for (Assignment assignment : assignmentList) {
            if (assignment.getAssignmentID() != null && assignment.getAssignmentID().equals(assignmentID)) {
                assignment.setAssignmentName(assignmentName);
                assignment.setdueDate(dueDate);
                assignment.setETATime(etaTime);
                assignment.setLabelColor(labelColor);
                assignment.setDescription(description);
                assignment.setStatus(status);
                assignment.setPriority(priority);
            }
        }
        loggedUser.setAssignmentList(assignmentList);

        writeAssignmentToFile();

        try{
           scheduleBuilder();
        } catch (IOException ex){
            System.out.println(ex);
        }


        return true;
    }

    /*
     * TO DO: This method is used to delete assignment for the logged user
     *
     * @param assignmentName of type <code>String</code>
     * @return <code>boolean</code>
     */
    public boolean deleteAssignment(Integer assignmentID) {

        ArrayList<Assignment> assignmentList = getLoggedUser().getAssignmentList();

        assignmentList.removeIf(assignment -> assignment.getAssignmentID().equals(assignmentID));

        loggedUser.setAssignmentList(assignmentList);

        writeAssignmentToFile();

        try{
            scheduleBuilder();
        } catch (IOException ex){
            System.out.println(ex);
        }

        return true;

    }

    public static ArrayList isInputValid(String firstName, String lastName, String dob, String email, String password) {

        String emailRegex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        String dobRegex = "^(1[0-2]|0[1-9])/(3[01]|[12][0-9]|0[1-9])/[0-9]{4}$";
        ArrayList<String> messages = new ArrayList<String>();
        String nameRegex = "^[\\p{L} .'-]+$";


        if (!email.matches(emailRegex)) {
            messages.add("Invalid email address entered");
        }

        if (!dob.matches(dobRegex)) {
            messages.add("Invalid date of birth entered");
        }

        if (!firstName.matches(nameRegex)) {
            messages.add("Invalid firstname entered");
        }

        if (!lastName.matches(nameRegex)) {
            messages.add("Invalid lastname entered");
        }

        if (password.length() < 8) {
            messages.add("Password must be 8 characters or more");
        }

        return messages;
    }

    public void writeAssignmentToFile(){

        
        
        ArrayList<Assignment> assignmentList = getLoggedUser().getAssignmentList();

        try {
            String filename = "C:\\StudentAlert\\profiles\\" + getLoggedUser().getUserID() + "\\assignments.csv";
            BufferedWriter out = new BufferedWriter(new FileWriter(
                    filename));

            StringBuilder sb = new StringBuilder();

//                         Append strings from array
            for (Assignment assignment : assignmentList) {
                sb.append(String.valueOf(assignment.getAssignmentID()));
                sb.append(",");
                sb.append(assignment.getAssignmentName());
                sb.append(",");
                sb.append(assignment.getDueDate());
                sb.append(",");
                sb.append(assignment.getETATime());
                sb.append(",");
                sb.append(assignment.getLabelColor());
                sb.append(",");
                sb.append(assignment.getDescription());
                sb.append(",");
                sb.append(assignment.getStatus());
                sb.append(",");
                sb.append(assignment.getPriority());
                sb.append("\n");
            }
            out.write(sb.toString());

            out.close();

        } catch (IOException ex) {

        }
    }

    //Method to calculate the schedule and write to the schedule.csv
    public void scheduleBuilder() throws IOException{
        //Holds the current row from the availability file reader
        String currentRow;
        double startTime, endTime, totalTime;
        //Copy of the assignment list that we can modify without affecting the rest of the program
        ArrayList<Assignment> assignmentList = (ArrayList<Assignment>)loggedUser.getAssignmentList().clone();
        //Hashmaps to store values for easier access
        //Stores the total hours available for each day
        HashMap<String, String> totalMap = new HashMap<>();
        //Stores the start times for each day
        HashMap<String, String> startMap = new HashMap<>();
        //Stores the end times for each day
        HashMap<String, String> endMap = new HashMap<>();
        //Reads the availability file
        BufferedReader availReader = new BufferedReader(new FileReader("C:\\StudentAlert\\profiles\\" + loggedUser.getUserID() + "\\availability.csv"));
        //Writes to the schedule file
        FileWriter writer = new FileWriter("C:\\StudentAlert\\profiles\\" + loggedUser.getUserID() + "\\schedule.csv");

        //Hashmaps to convert day strings and their int values back and forth for calculations
        HashMap<Integer, String> intToDayMap = new HashMap<>();
        intToDayMap.put(1, "Monday");
        intToDayMap.put(2, "Tuesday");
        intToDayMap.put(3, "Wednesday");
        intToDayMap.put(4, "Thursday");
        intToDayMap.put(5, "Friday");
        intToDayMap.put(6, "Saturday");
        intToDayMap.put(7, "Sunday");

        HashMap<String, Integer> dayToIntMap = new HashMap<>();
        dayToIntMap.put("Monday", 1);
        dayToIntMap.put("Tuesday", 2);
        dayToIntMap.put("Wednesday", 3);
        dayToIntMap.put("Thursday", 4);
        dayToIntMap.put("Friday", 5);
        dayToIntMap.put("Saturday", 6);
        dayToIntMap.put("Sunday", 7);


        //While the availability file has more data
        while ((currentRow = availReader.readLine()) != null){
            //Take the current row, and remove the commas, store the values in an array
            String[] data = currentRow.split(",");

            //Check the start time, if its PM add 12 hours to convert to a 24hr format
            if (data[1].contains("PM")){
                startTime = Character.getNumericValue(data[1].charAt(0)) + 12;
            } else {
                startTime = Character.getNumericValue(data[1].charAt(0));
            }

            //Same for the end time
            if (data[2].contains("PM")){
                endTime = Character.getNumericValue(data[2].charAt(0)) + 12;
            } else {
                endTime = Character.getNumericValue(data[2].charAt(0));
            }

            //Total time available for the day calculation using the new 24hr times
            totalTime = endTime - startTime;

            //Store our values in the proper hashmaps corresponding to the correct day
            totalMap.put(data[0], String.valueOf(totalTime));
            startMap.put(data[0], String.valueOf(startTime));
            endMap.put(data[0], String.valueOf(endTime));
        }

        //Counter to keep track of the current priority being scheduled
        int currentPrio = 1;
        //Counter to track the current day being scheduled
        int currentDay = 1;

        //While priority is 1 through 3
        while(currentPrio < 4){
            //For each assignment in the assignment list, iterating as long as there is another in the list
            //Iterator used to prevent exception being thrown when assignment is removed from the list
            for (Iterator<Assignment> iterator = assignmentList.iterator(); iterator.hasNext();){
                //Current object in iterator
                Assignment assignment = iterator.next();
                //If the assignment meets the current priority being scheduled
                if (Integer.parseInt(assignment.getPriority()) == currentPrio){
                    //Check to ensure we have not iterated through all the days yet
                    while (currentDay <= 8){
                        //If the assignment due date is further out than the current day
                        //dayToIntMap used to convert the assignment due date string to an int that can be compared using relational operators
                        if (dayToIntMap.get(assignment.getDueDate()) >= currentDay){
                            //If the assignments time to complete is less than or equal to the time available for the current day
                            if (Double.parseDouble(assignment.getETATime()) <= Double.parseDouble(totalMap.get(intToDayMap.get(currentDay)))){
                                //All requirements met to schedule the assignment on the current day
                                //Write to schedule.csv in the determined format
                                writer.write(loggedUser.getUserID() + "," + intToDayMap.get(currentDay) + "," + startMap.get(intToDayMap.get(currentDay)) + "," + assignment.getAssignmentID() + "\n");
                                //Confirmation of scheduling success to the console
                                System.out.println("Scheduled " + assignment.getAssignmentName());

                                //Establish a new start time of availability for the day after scheduling to prevent 2 assignments being scheduled at the same time
                                double newStart = Double.parseDouble(startMap.get(intToDayMap.get(currentDay))) + Double.parseDouble(assignment.getETATime());
                                //Establish a new total time available for the current day after scheduling
                                double newTotal = Double.parseDouble(totalMap.get(intToDayMap.get(currentDay))) - Double.parseDouble(assignment.getETATime());

                                //Store our new total and start times in the hashmaps
                                startMap.replace(intToDayMap.get(currentDay), String.valueOf(newStart));
                                totalMap.replace(intToDayMap.get(currentDay), String.valueOf(newTotal));

                                //Assignment has been scheduled, remove from iterator
                                iterator.remove();
                                break;
                            }
                            //If none of the days have ample time available to schedule the assignment
                            //And we have iterated through all 7 days
                            if (currentDay == 8){
                                //reset the day counter
                                currentDay = 1;
                                //Console message stating an assignment could not be scheduled
                                //TODO replace with popup window to inform user
                                System.out.println("Could not schedule " + assignment.getAssignmentName());
                                //Remove the assignment from the iterator
                                iterator.remove();
                            }
                        }
                        //If the assignments due date is earlier than the current day, go to next day
                        currentDay++;
                    }
                }
            }
            //After all assignments have been iterated through, and the assignments for the given priority have been scheduled, increase the priority and iterate again
            currentPrio++;
        }

        writer.flush();
        writer.close();
    }
    
    /* This method travers the Schedule List update the status of each <code>Assignment</code> as well as build a 
     * <code>DefaultTableModel</code> to be displayed ina JTable.
     *
     * @param dueDate of type <code>String</code>
     * @return DefaultTableModel
     */
    public DefaultTableModel loadSchedule(String dueDate){
        
        int currentDay, scheduleDueDay, assignmentDueDate;
        String currentTimeHH, currentTimeMM, assignmentStartTime;
        
        this.readScheduleFile();
            
        // builds the header Title of the DefaultTableModel
        ArrayList<String> columnsTitle = new ArrayList();
        columnsTitle.add("Assignment Name");
        columnsTitle.add("Schedule Start Date");
        columnsTitle.add("Start Time (24hr)");
        columnsTitle.add("Assignment Due Date");        
        columnsTitle.add("Priority");
        columnsTitle.add("Status");
                
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnsTitle.toArray());
        
        // Date format to display the Hour and Minutes only
        DateFormat currentHourFormat = new SimpleDateFormat("HH");
        DateFormat currentMinuteFormat = new SimpleDateFormat("mm");
        
        // Store the current Time Hour only
        currentTimeHH = currentHourFormat.format(new Date(System.currentTimeMillis()));
        
        // Store the current Time Minute only
        currentTimeMM = currentMinuteFormat.format(new Date(System.currentTimeMillis()));
        
        // Store the current Day of the Week only
        currentDay = covertDaytoInt(Schedule.getCurrentDayOfWeek().toString().toLowerCase());
        
        for(Schedule schedule : scheduleList){     
            
            // Store the Assignment objects of the particular Schedule object
            Assignment assignment = this.getLoggedUser().getAssignmentByID(schedule.getAssignmentID());
            
            if(assignment != null)
            {
                schedule.setAssignment(assignment);
                
                scheduleDueDay = covertDaytoInt(schedule.getDay().toLowerCase());
                assignmentDueDate = covertDaytoInt(assignment.getDueDate().toLowerCase());
              
                // Format the Time in HH:mm for proper display
                assignmentStartTime = String.valueOf(schedule.getStartTimeHH()) + ":" + new DecimalFormat("00").format(schedule.getStartTimeMM());
                
                // Do not change if the status of the assignment is Completed or Cancelled
                if(!assignment.getStatus().equals("Completed") && !assignment.getStatus().equals("Cancelled")) {
                    
                    // if the assignment due date is passed the current date, the status is set as Overdue by default
                    // Otherwise, the status is labelled as "In Progress" based on the current date, schedule date and time
                    if(assignmentDueDate < currentDay)
                        assignment.setStatus("Overdue"); 
                    else if(currentDay > scheduleDueDay) {
                        assignment.setStatus("In Progress");
                    }
                    else if(currentDay == scheduleDueDay) {                       
                        
                        schedule.start();
                        
                        if(Integer.parseInt(currentTimeHH) > schedule.getStartTimeHH())
                            assignment.setStatus("In Progress"); 
                        else if(Integer.parseInt(currentTimeHH) == schedule.getStartTimeHH()) {
                            if( Integer.parseInt(currentTimeMM) >= schedule.getStartTimeMM()) {
                                assignment.setStatus("In Progress"); 
                            }
                            else 
                                assignment.setStatus("Open");
                        }
                        else
                            assignment.setStatus("Open");
                    }
                    else {
                        assignment.setStatus("Open");
                    }
                    
                    
                    schedule.setAssignment(assignment);
                    initSTMSGUI.displayLog("NOTIFICATION: " + assignment.getAssignmentName() + " assignment status is set " + assignment.getStatus());
                }
                    
                // This condition enable to filter the Schedule for only a particular Date of the Week
                if(schedule.getDay().equals(dueDate)) {
                    
                    Object[] data = {   assignment.getAssignmentName(),
                                        schedule.getDay(), 
                                        assignmentStartTime,
                                        assignment.getDueDate(),
                                        assignment.getPriority(),
                                        assignment.getStatus()};      

                    model.addRow(data); 
                }
            }
        }
        
        // Write back to the  Assignment File if there is any update with the status of the assignments.
        this.writeAssignmentToFile();
        
        return model;
    }
    
    /* This method reads from the schedule.csv file and build the <code>Schedule</code> List of objects stored in 
     * <code>ArrayList<Schedule></code>.
     *
     * @return void
     */
    public void readScheduleFile() {
        
        // used to store the Hour and Minutes into two different variables. 
        int HH, MM;
        String currentRow;
        
        try {            
            BufferedReader scheduleReader = new BufferedReader(new FileReader("C:\\StudentAlert\\profiles\\" + loggedUser.getUserID() + "\\schedule.csv"));

            scheduleList = new ArrayList<Schedule>();
            
             while ((currentRow = scheduleReader.readLine()) != null){
                
                String[] data = currentRow.split(",");
                                
                double startTemp = Double.parseDouble(data[2]);  
                
                // calculate the 30 minutes if the input is double
                if( startTemp % 1 != 0) 
                    MM = 30;
                else 
                    MM = 0;   
                
                // capture the integrate disregarding the decimal place
                HH = (int) startTemp;

                Schedule schedule = new Schedule(Integer.parseInt(data[0]), 
                                                    data[1], 
                                                    HH, MM,  // register the hour and the minutes in two different variables
                                                    Integer.parseInt(data[3]));
                
                schedule.setAssignment(this.getLoggedUser().getAssignmentByID(schedule.getAssignmentID()));
                scheduleList.add(schedule); 
            } 
            
            /*
            Test Schedule List
            
            Schedule s1 = new Schedule(20684, "Monday", 21, 14, 1);
            Schedule s2 = new Schedule(20684, "Thursday", 10, 12, 2);
            Schedule s3 = new Schedule(20684, "Friday", 9, 0, 3);
            
            scheduleList.add(s1);
            scheduleList.add(s2);
            scheduleList.add(s3); */
            
        }
        catch(Exception e) {
            initSTMSGUI.displayLog("ERROR MESSAGE: Couldn't be able to load the schedule.csv file.");
        }
        
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

    public ArrayList<Schedule> getScheduleList() {
        return scheduleList;
    }
}
