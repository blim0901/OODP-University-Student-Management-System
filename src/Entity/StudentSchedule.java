package Entity;

import Controller.AdminControl;
import java.io.Console;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;


/** Entity class for handling student course schedule functions.
 * 
 */
public class StudentSchedule {
	
	private String courseNumberID; 
	private String id; 
	private String indexNumber; 


    /** Data method for reading list of courses student has registered for.
	 * 
	 * @param addUser User name of student.
	 * @throws IOException
	 */
	public static void getRegdCourse(String addUser) throws IOException {
  	    
		String studentuser;
		studentuser = addUser;
		
		CSVReader reader = new CSVReader(new FileReader("database/registered_courses.txt"),',');
    	List<String[]> csvBody = reader.readAll();
    	for(int i=0; i<csvBody.size(); i++){
            System.out.println("Your successfully registered courses are: " );
        	if(csvBody.get(i)[0].equals(studentuser)) {
				System.out.println(csvBody.get(i)[1] + " " + csvBody.get(i)[2]);
        	}
        }
    	reader.close();
	}
	
	
	/** Data method for reading list of index numbers that student is in waitlist for.
	 * 
	 * @param addUser User name of student.
	 * @throws IOException
	 */
	public static void getIndexWaitList(String addUser) throws IOException {
  	    
		String studentuser;
		studentuser = addUser;
		
		CSVReader reader = new CSVReader(new FileReader("database/waiting_list.txt"),',');
    	List<String[]> csvBody = reader.readAll();
    	for(int i=0; i<csvBody.size(); i++){
            String[] strArray = csvBody.get(i);
            System.out.println("Your courses in waiting lists are: " );
            for(int j=0; j<strArray.length; j++){
            	if(csvBody.get(i)[j].equals(studentuser)) {
    				System.out.println(csvBody.get(i)[0] + " " + csvBody.get(i)[1]);
            	}
            }
        }
    	reader.close();
	}
	
}