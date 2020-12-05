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


/** Entity class for handling course registration functions.
 * 
 */
public class CourseRegistration {
	
	private String id;  
	private String indexNumber;
	private String usernameInput; 


	/** Data method for writing new course that student registered for.
	 * 
	 * @param addUser User name of student adding course.
	 * @throws IOException
	 */
	public static int registerCourse(String addUser) throws IOException {
  	    
		Scanner courseAdder = new Scanner(System.in);
		String studentuser, courseId, indexNo,strVacancy;
		int vacancy,intVacancy,maxAUcheck;
		studentuser = addUser;
		
	
		
		System.out.println("Please enter the course ID that you want to register: ");
		courseId = courseAdder.next();
		
		if(StudentControl.checkValidCourseID(courseId)==1) {
			return 0;
		}	
		
		//Reading information from registered course file
		CSVReader registerReader = new CSVReader(new FileReader("database/registered_courses.txt"),',');
			List<String[]> csvBody = registerReader.readAll();
			//Loop through to check if the user have already registered for the course.
			for(int i=0; i<csvBody.size(); i++) {
				if(csvBody.get(i)[0].equals(studentuser) && csvBody.get(i)[1].equals(courseId)) {
					return 2;
            	}
            	
			}
		System.out.println("Please enter the course index number that you want to register: ");
		indexNo = courseAdder.next();
		if(StudentControl.checkValidCourseIndex(indexNo)==1) {
			return 1;
		}
		maxAUcheck= AUCalculator.check21AU(studentuser,indexNo); // check aus taken
		if(maxAUcheck == 1) {
			return 5;
		}
		//Add codes to check for valid index number
		
		//Add codes here to get the vacancy of the course index
		CSVReader vacancyReader = new CSVReader(new FileReader("database/course_index.txt"),',');
			List<String[]> csvMain = vacancyReader.readAll();
		//If the course index is full vacancy, then add student to wait list
			for(int i=0; i<csvMain.size(); i++) {
				String[] strArray2 = csvMain.get(i);
				for(int j=0; j<strArray2.length; j++) {
					if(csvMain.get(i)[0].equals(courseId) && csvMain.get(i)[2].equals(indexNo)) {
	            		vacancy = Integer.parseInt(csvMain.get(i)[10]);  //possibly change table values so that it is integer instead of string, then can skip this step
	            		if (vacancy == 0) {   //If course index vacancy is full
	            			System.out.println("This course index has reached it's full capacity. Enter 0 to register for another index. Enter 1 to be added to waitlist. ");
	        				int choice = courseAdder.nextInt(); 
	        				if (choice == 0) {   //if choice is 0, go back up to the loop to register for another index
	        					return 3;
	        				}
	        				else if (choice == 1) {   //if choice is 1, add student into course wait list
	        					//add student into wait list******
	        					FileWriter writer1 = new FileWriter("database/waiting_list.txt",true); 
	        					writer1.append('"'+courseId+'"'+','); 
	        					writer1.append('"'+indexNo+'"'+',');
	        					writer1.append('"'+studentuser+'"'+',');
	        					writer1.append("\n");
	        					writer1.close();
	        					return 4;
	        				}
	            		 }
	            	}
				}
	            
			}
		//LOGIC ERROR SHOULD ONLY ALLOW TO APPEND IF COURSE NUMBER AND INDEX NO FOUND IN COURSE INDEX FILE
			
		//If student did not register for course yet, and the index is available, go ahead to add course index for student to his/her registered courses.
		FileWriter writer = new FileWriter("database/registered_courses.txt",true); 
		writer.append('"'+studentuser+'"'+','); 
		writer.append('"'+courseId+'"'+',');
		writer.append('"'+indexNo+'"');
		writer.append("\n");
		writer.close();
		
		vacancyMinus(indexNo,1);
		System.out.println("Congratulations! Your course has been registered.");
		return -1;
	}



    /** Data method for deleting course that student de-registers.
	 * 
	 * @param user User name of de-registering student.
	 * @throws IOException
	 */
	public static int deRegisterCourse(String user) throws IOException {
  	    
		Scanner courseRemover = new Scanner(System.in);
		String studentuser, courseId, indexNo;
		int vacancy;
		studentuser = user;
		
		System.out.println("Please enter the course ID of the course you would like to drop: ");
		courseId = courseRemover.next();
		if(StudentControl.checkValidCourseID(courseId)==1) {
			return 0;
		}
		
		System.out.println("Please enter the index number that you would like to drop: ");
		indexNo = courseRemover.next();
		if(StudentControl.checkValidCourseIndex(indexNo)==1) {
			return 1;
		}
		
		CSVReader reader = new CSVReader(new FileReader("database/registered_courses.txt"),',');
        	List<String[]> csvBody = reader.readAll();
        	for(int i=0; i<csvBody.size(); i++){
            		if(csvBody.get(i)[0].equals(studentuser) && csvBody.get(i)[1].equals(courseId) && csvBody.get(i)[2].equals(indexNo)) {
        					vacancyAdd(indexNo,2);            				
            				csvBody.remove(i);  
            				CSVWriter writer = new CSVWriter(new FileWriter("database/registered_courses.txt"), ',');
            			    writer.writeAll(csvBody);
            			    writer.flush();
            			    writer.close();
            				System.out.println("The course has been dropped from your registered list.");
            				WaitList.checkWaitlist(indexNo);
            				return -1;
            			
            		}	
	            
	        }

    		return 2;
	}

	

    
	/** Data method for writing vacancy of a particular index number that has been de-registered (-1).
	 * 
	 * @param indexNo Index number to write.
	 * @param addOrMinus Indicator for increasing or decreasing vacancy.
	 * @throws IOException
	 */
	public static void vacancyMinus(String indexNo, int addOrMinus) throws IOException {
		int intVacancy = 0;
		String strVacancy = "";
		CSVReader vacancyMinus = new CSVReader(new FileReader("database/course_index.txt"));
		List<String[]> csvMain2 = vacancyMinus.readAll();
			for(int i=0; i<csvMain2.size(); i++) {
				if(csvMain2.get(i)[2].equals(indexNo)) {
					intVacancy = Integer.parseInt(csvMain2.get(i)[10]);
					intVacancy--;
					strVacancy=String.valueOf(intVacancy);
					csvMain2.get(i)[10] = strVacancy;
					System.out.println("Current vacancies: "+ strVacancy);
				}
			}
		CSVWriter writer2 = new CSVWriter(new FileWriter("database/course_index.txt"), ',');
		writer2.writeAll(csvMain2);
		writer2.flush();
		writer2.close();
		
	}
	
	
	/** Data method for writing vacancy of a particular index number that a student has registered for (+1).
	 * 
	 * @param indexNo Index number to write.
	 * @param addOrMinus Indicator for increasing or decreasing vacancy.
	 * @throws IOException
	 */
	public static void vacancyAdd(String indexNo, int addOrMinus) throws IOException {
		int intVacancy = 0;
		String strVacancy = "";
		CSVReader vacancyMinus = new CSVReader(new FileReader("database/course_index.txt"));
		List<String[]> csvMain2 = vacancyMinus.readAll();
			for(int i=0; i<csvMain2.size(); i++) {
				if(csvMain2.get(i)[2].equals(indexNo)) {
					intVacancy = Integer.parseInt(csvMain2.get(i)[10]);
					intVacancy++;
					strVacancy=String.valueOf(intVacancy);
					csvMain2.get(i)[10] = strVacancy;
					System.out.println("Current vacancies: "+strVacancy);
				}
			}
		CSVWriter writer2 = new CSVWriter(new FileWriter("database/course_index.txt"), ',');
		writer2.writeAll(csvMain2);
		writer2.flush();
		writer2.close();
		
	}


    
	/** Data method for overwriting particular index number that student is changing.
	 * 
	 * @param username User name of student.
	 * @param courseId Course code of index numbers.
	 * @param oldIndex Index number to change from.
	 * @param newIndex Index number to change to.
	 * @throws IOException
	 */
	public static void updateRegdIndex(String username, String courseId, String oldIndex, String newIndex) throws IOException {
  	    
		CSVReader reader = new CSVReader(new FileReader("database/registered_courses.txt"),',');
        List<String[]> csvBody = reader.readAll();
        for(int i=0; i<csvBody.size(); i++){
        	if(csvBody.get(i)[0].equals(username) & csvBody.get(i)[1].equals(courseId) & csvBody.get(i)[2].equals(oldIndex)) {
        		if(CourseIndex.isVacant(newIndex) == 1) {  //check if the new index slot is full. If there is still vacancy, then go into loop
        			csvBody.get(i)[2] = newIndex;
        			System.out.println("Index changed successfully.");
        		     vacancyAdd(oldIndex,1);
        		     vacancyMinus(newIndex,1);
        		     WaitList.checkWaitlist(oldIndex);
        		}
        		else {
        			System.out.println("Sorry the index that you have chosen is full. Please choose another index.");
        		}
            }
     
	     reader.close();
	     CSVWriter writer = new CSVWriter(new FileWriter("database/registered_courses.txt"), ',');
	     writer.writeAll(csvBody);
	     writer.flush();
	     writer.close();
	     
        }

	}
	
	
	/** Data method for overwriting particular index number of students that are swapping.
	 * 
	 * @param user User student.
	 * @param peerUser Swapping peer student.
	 * @param courseId Course code of index numbers to swap.
	 * @param yourIndex Index number that user wishes to swap.
	 * @param peerIndex Index number that peer wishes to swap.
	 * @throws IOException
	 */
	public static void updateSwopIndex(String user, String peerUser, String courseId, String yourIndex, String peerIndex) throws IOException {
  	    
		//write code to check course index validity
		//check login.txt if user/pass of peer is correct
		//first find peer student check his index if it matches peerIndex and set flag (1st loop) Exception: peer index not found return error
		//second find your index  your index with his. Save your index(2nd loop)  Exception: your index is wrongly typed return error
		//thirdly find his index and replace his index with yours
		int peerIndexFlag = 0,swapFlag = 0;
	    CSVReader reader = new CSVReader(new FileReader("database/registered_courses.txt"),',');
	        List<String[]> csvBody = reader.readAll();
	        for(int i=0; i<csvBody.size(); i++){
            	if(csvBody.get(i)[0].equals(peerUser) && csvBody.get(i)[2].equals(peerIndex)) {
            		peerIndexFlag=1;
            		}
	            }
	        if(peerIndexFlag==0) {
	        	System.out.println("Error peer index does not exist");
	        }
	        for(int i=0; i<csvBody.size(); i++){
            	if(csvBody.get(i)[0].equals(user) && csvBody.get(i)[2].equals(yourIndex)) {
            		csvBody.get(i)[2] = peerIndex;
            		swapFlag = 1;
            	}
	        }
	        if(swapFlag == 0) {
	        	System.out.println("Error your index does not exist");
	        }
	        for(int i=0; i<csvBody.size(); i++){
            	if(csvBody.get(i)[0].equals(peerUser) && csvBody.get(i)[2].equals(peerIndex)) {
            		csvBody.get(i)[2] = yourIndex;
            	}
	        }
	        System.out.println("Sucessfully swapped");
	        //Add email functionality
	     reader.close();
	     CSVWriter writer = new CSVWriter(new FileWriter("database/registered_courses.txt"), ',');
	     writer.writeAll(csvBody);
	     writer.flush();
	     writer.close();
	   //add codes to update course index vacancy
        
	}


}