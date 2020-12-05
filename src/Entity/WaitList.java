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


/** Entity class for handling waiting list functions.
 * 
 */
public class WaitList {


	private String courseNumberID; 
	private String id; 
	private String indexNumber; 
	
	/** Data method for reading and writing index number vacancy when a student is registered from waitlist.
	 *  
	 * @param indexNo Index number to update.
	 * @throws IOException
	 */
	public static void checkWaitlist(String indexNo) throws IOException {
		String userWait="",courseCode="";
		CSVReader reader = new CSVReader(new FileReader("database/waiting_list.txt"),',');
    	List<String[]> csvBody = reader.readAll();
    	for(int i=0; i<csvBody.size(); i++){ //go into waiting list and take out first user
        		if(csvBody.get(i)[1].equals(indexNo)) {
        			courseCode = csvBody.get(i)[0];
        			userWait = csvBody.get(i)[2];
        			csvBody.remove(i);  
    				CSVWriter writer = new CSVWriter(new FileWriter("database/waiting_list.txt"), ',');
    			    writer.writeAll(csvBody);
    			    writer.flush();
    			    writer.close();
    			    System.out.println("1");
        			CourseRegistration.vacancyMinus(indexNo, 1);
        			System.out.println("1");
        			FileWriter writer1 = new FileWriter("database/waiting_list.txt",true); 
        			System.out.println("1");
        			writer1.append('"'+userWait+'"'+','); 
        			writer1.append('"'+courseCode+'"'+',');
        			writer1.append('"'+indexNo+'"');
        			writer1.append("\n");
        			writer1.close();
        			SendEmail.sendMail(userWait);
        			return;
        		}

    	}

		
		return;
	}
	
}