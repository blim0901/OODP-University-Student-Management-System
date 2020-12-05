package Boundary;
import Controller.LoginControl;
import java.io.BufferedReader;
import java.io.Console;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDate;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import au.com.bytecode.opencsv.CSVReader;
//SINCE WE USE EXTERNAL JAR WE NEED TO INCLUDE IT WHILE RUNNING x
//Use CMD to run 1)javac -cp ".;./opencsv-2.4.jar;javax.mail.jar;activation-1.1.1.jar" Entity/*.java
//1.1)javac -cp ".;./opencsv-2.4.jar;javax.mail.jar;activation-1.1.1.jar" Boundary/*.java
//javac -cp ".;./opencsv-2.4.jar;javax.mail.jar;activation-1.1.1.jar" Controller/*.java
//2)java -cp ".;./opencsv-2.4.jar;javax.mail.jar;activation-1.1.1.jar" Boundary/Login.java


/** Boundary class that handles login user interface.
 * 
 */
public class Login implements Modes {

	private String id; 
	private String pw; 
	/** Main method, loaded upon application execution. Initialises login UI.
	 * 
	 * @param args
	 * @throws IOException
	 * @throws ParseException
	 */
	public static void main(String[] args) throws IOException, ParseException {
	String username, passwordStr, hashedpassword;
	char[] passwordch;
	Console cons = System.console();
	boolean x = true;
	while(x = true) {
	username = cons.readLine("Please input username: ");
	passwordch = cons.readPassword("Please enter your password: ");
	passwordStr = new String(passwordch);
	hashedpassword = LoginControl.hashPassword(passwordStr);
	if(userValidation(username,hashedpassword)==2){
		AdminMode.adminMenu();
	}
	if(userValidation(username,hashedpassword)==1){
		System.out.println("Login successful, welcome Student!");
		if(checkStudentAccessDate(username)==1) {
			System.out.println("Today is your access day good luck!");
			StudentMode.studentMenu(username);
		}else{
			System.out.println("Today is not your access day");

		}


	}
	if(userValidation(username,hashedpassword)==3){
		System.out.println("Error wrong password or username please enter again \n");
	}
	}


	}
	//Checks if User is student or admin (if user is student it will check his/her access day)
	
	/**Read "database/login.txt" file to check user/pass and access privileges (Student/Admin). 
	 * 
	 * @param username Entered user name.
	 * @param passwordStr Entered password.
	 * @throws IOException
	 * @throws ParseException
	 */
	public static int userValidation(String username,String passwordStr) throws IOException, ParseException {
		CSVReader reader = new CSVReader(new FileReader("database/login.txt"),',');
	        List<String[]> csvBody = reader.readAll();
	        for(int i=0; i<csvBody.size(); i++){
	            String[] strArray = csvBody.get(i);
	            for(int j=0; j<strArray.length; j++){
	            	if(csvBody.get(i)[0].equals(username) && csvBody.get(i)[1].equals(passwordStr) && csvBody.get(i)[2].equals("Admin")) {
	        		    reader.close();
	        		    return 2;
	        		    }
	            	if(csvBody.get(i)[0].equals(username) && csvBody.get(i)[1].equals(passwordStr) && csvBody.get(i)[2].equals("Student")) {
	            			return 1;
	            		}

	        		    
	        		    }
	            }
	       return 3;
	   
		
	}
	
	
	/**Reads student's period of access under "database/students.txt" to check if access is allowed at present.
	 * 
	 * @param username Entered user name.
	 * @throws IOException
	 * @throws ParseException
	 */
	public static int checkStudentAccessDate(String username) throws IOException, ParseException {
		CSVReader reader = new CSVReader(new FileReader("database/students.txt"),',');
        List<String[]> csvBody = reader.readAll();
        for(int i=0; i<csvBody.size(); i++){
            String[] strArray = csvBody.get(i);
    	if(csvBody.get(i)[0].equals(username)) {
    		LocalDate currentDate = java.time.LocalDate.now();
    		Date currentDateConv = Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    		Date accessDate=new SimpleDateFormat("dd-MM-yyyy").parse(csvBody.get(i)[5]);
    		if(currentDateConv.compareTo(accessDate)==0) {
    			System.out.println("!!");
    			return 1;
    		}
    		
		  }
        }
        return 0;
      
	}
		
}
