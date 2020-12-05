package Entity;

import Controller.StudentControl;
import java.io.Console;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;


/** Entity class for handling AU calculation of courses
 * 
 */
public class AUCalculator {
	
	int AUsum;
	String courseCode, indexCode;

	/** Data method for reading total number of AUs student is registered for, and checking if registering desired index number puts total AUs over the limit (21).
	 * 
	 * @param user User name of student
	 * @param currentIndex Index number that student wishes to register.
	 * @throws IOException
	 */
	public static int check21AU(String user, String currentIndex) throws IOException {
		String[] coursesTaken;
		int courseCounter=0,arrayCounter =0, AUcount=0;
		CSVReader registerReader = new CSVReader(new FileReader("database/registered_courses.txt"),',');
		List<String[]> csvBody = registerReader.readAll();
		//Loop through to check if the user have already registered for the course.
		for(int i=0; i<csvBody.size(); i++) {
			if(csvBody.get(i)[0].equals(user)) {
			 courseCounter++;	// count courses student takes
			}
		}
		System.out.println("Taken courses: " + courseCounter);
		coursesTaken = new String[courseCounter]; //initialize string array with number of courses
		for(int i=0; i<csvBody.size(); i++) {
			if(csvBody.get(i)[0].equals(user)) {
				coursesTaken[arrayCounter] = csvBody.get(i)[1];
				arrayCounter++;	// count courses student takes
			}
		}
		 System.out.println(currentIndex);
	    CSVReader reader = new CSVReader(new FileReader("database/course_index.txt"),',');
        List<String[]> csvBody2 = reader.readAll();
        for(int i=0; i<csvBody2.size(); i++){
        	for(int j=0; j<courseCounter; j++) {
        	if(csvBody2.get(i)[0].equals(coursesTaken[j])||csvBody2.get(i)[2].equals(currentIndex)) {
        			AUcount += Integer.parseInt(csvBody2.get(i)[12]);
        			coursesTaken[j] = "";
        		}
        	}
        	}
        if(AUcount>21) {
        	return 1;
        }
		return 0;
	}
	
}