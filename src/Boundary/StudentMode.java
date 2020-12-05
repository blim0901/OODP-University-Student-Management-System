
package Boundary;
import Controller.StudentControl;
import Entity.Student;
import Controller.LoginControl;
import java.io.Console;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Scanner;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

/** Boundary class that handles student's STARS access user interface.
 * 
 */
public class StudentMode implements Modes {

	private String id; 
	private String pw; 
	/** Method loaded upon student login. Initialises student access UI
	 * 
	 * @param username User name of student.
	 * @throws IOException
	 * @throws ParseException
	 */
	public static void studentMenu(String username) throws IOException, ParseException {
		
		String user = username;
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("1.Print Course Index Schedule \n2.Add Courses \n3.Drop Courses "
					+ "\n4.Print My Registered Courses And Wait List \n5.Print Course Index Vacancy \n6.Change Course Index  \n7.Swop Course Index \n8.Quit\nPlease enter your choice: ");
			int selector = 0;
			if(sc.hasNextInt()) {
				selector = sc.nextInt();
			}
			else {
				System.out.println("Invalid entry for menu. Please enter only numeric values.");
				studentMenu(user);
			} 
			
			switch(selector) {
				//Calls StudentMode class, via modes Interface
				case 1:
					StudentMode.printIndex(user);
					break;
				case 2:
					StudentMode.registerCourses(user);
					break;
				case 3:
					StudentMode.deregisterCourses(user);
					break;
				case 4:
					StudentMode.printStudentCourses(user);
					break;
				case 5:
					StudentMode.printCourseVacancy(user);
					break;
				case 6:
					StudentMode.changeIndex(user);
					break;
				case 7:
					StudentMode.swopIndex(user);
					break;     
				case 8:
					System.out.println("Session ended. Thank you.");
					System.exit(0);
					break;
		
				default:
					System.out.println("Please enter a valid choice!");
					break;
			
			}
		}
	}
	
	/** Interaction handler for viewing class schedules of a particular course.
	 * 
	 * @param user User name of student.
	 * @throws IOException
	 * @throws ParseException
	 */
	//1 
	public static void printIndex(String user) throws IOException, ParseException {
		
		String courseId;
		Console cons = System.console();
		courseId = cons.readLine("Please enter the course ID to view the course schedule: "); 	
		if(StudentControl.checkValidCourseID(courseId)==1) {
			studentMenu(user);
		}
		StudentControl.printCourseDetails(courseId);
    	studentMenu(user);
	}
	
	
	/** Interaction handler for registering a course.
     * 
     * @param user User name of student.
     * @throws IOException
     * @throws ParseException
     */
	//2 
	public static void registerCourses(String user) throws IOException, ParseException {  //first time registering for courses
		String addUser;
		int courseFlag;
		addUser = user;
		//Transfer codes here to ask for inputs instead of in Students class ****************
		courseFlag = StudentControl.addCourse(addUser);
		
		//Need to enter a valid course code
		if(courseFlag == 0) {
			System.out.println("Please enter a valid course code");
			//restart process
			registerCourses(user);
		}
		
		//Need to enter a valid course index
		if(courseFlag == 1) {
			System.out.println("Please enter a valid course index");
			//restart process
			registerCourses(user);
		}
		
		//If student has already registered for the course
		if(courseFlag == 2) {
			System.out.println("You have already registered for this course. Please register for another course.");
			//restart process
			registerCourses(user);
		}
		
		//If course index vacancy is full and student have opted to choose another index
		if(courseFlag == 3) {
			System.out.println("The chosen course index has reached full capacity. Please choose another index.");
			//restart process
			registerCourses(user);
		}
		
		//If course index vacancy is full and student have opted to be added to wait list
		if(courseFlag == 4) {
			System.out.println("The chosen course index has reached full capacity. You have been placed into the wait list. A notification will be sent to you if you are allocated the slot.");
			//restart process
			studentMenu(user);
		}
		if(courseFlag == 5) {
			System.out.println("Sorry, registering for this course will go over the 21 AU limit");
			studentMenu(user);
		}
		
		
		studentMenu(user);
	
		
	}


	/** Interaction handler for deregistering a course.
	 * 
	 * @param user User name of student.
	 * @throws IOException
	 * @throws ParseException
	 */
	//3 
	public static void deregisterCourses(String user) throws IOException, ParseException {  //first time registering for courses
		String courseId, indexNo ;
		int courseFlag;
		courseFlag = StudentControl.dropCourse(user);
		//Transfer codes here ask for inputs instead of in Student class ****************
		
		//Need to enter a valid course code
		if(courseFlag == 0) {
			System.out.println("Please enter a valid course code");
			//restart process
			deregisterCourses(user);
		}
		
		//Need to enter a valid course index
		if(courseFlag == 1) {
			System.out.println("Please enter a valid course index");
			//restart process
			deregisterCourses(user);
		}
		
		//If student did not register for the course in the first place
		if(courseFlag == 2) {
			System.out.println("You did not register for this course. Please choose another course to drop.");
			//restart process
			deregisterCourses(user);
		}
		
		studentMenu(user);
	
		
	}
	
	
	/** Interaction handler for viewing registered courses.
	 * 
	 * @param user User name of student.
	 * @throws IOException
	 * @throws ParseException
	 */
	//4 
	public static void printStudentCourses(String user) throws IOException, ParseException {

		String addUser = user;
		StudentControl.printRegdCourse(addUser);
		StudentControl.printWaitlist(addUser);
		studentMenu(user);
	}
	
	
	/** Interaction handler for viewing the vacancies available for a particular course.
	 * 
	 * @param user User name of student
	 * @throws IOException
	 * @throws ParseException
	 */
	//5 
	public static void printCourseVacancy(String user) throws IOException, ParseException {
		String courseId;
		Console cons = System.console();
		courseId = cons.readLine("Please enter the course ID to view the course index vacancy: ");
		if(StudentControl.checkValidCourseID(courseId)==1) {
			studentMenu(user);
		}
		StudentControl.printVacancyList(courseId);
    	studentMenu(user);
	}
	

	/** Interaction handler for changing index number of a registered course.
	 * 
	 * @param user User name of student.
	 * @throws IOException
	 * @throws ParseException
	 */
	//6 
	public static void changeIndex(String user) throws IOException, ParseException {
		Scanner indexUpdater = new Scanner(System.in);
		String username, courseId, oldIndex, newIndex;
		username = user;

		
		System.out.println("Please enter the course code you would like to update: ");
		courseId = indexUpdater.next();
		if(StudentControl.checkValidCourseID(courseId)==1) {
			studentMenu(user);
		}
		
		System.out.println("Please enter the course index you would like to update: ");
		oldIndex = indexUpdater.next();
		if(StudentControl.checkValidCourseIndex(oldIndex)==1) {
			studentMenu(user);
		}
		
		System.out.println("Please enter the new index number to enroll in: ");
		newIndex = indexUpdater.next();
		if(StudentControl.checkValidCourseIndex(oldIndex)==1) {
			studentMenu(user);
		}
		
	    StudentControl.changeRegdIndex(username, courseId, oldIndex, newIndex);
     	studentMenu(user);
	}
	        
	
	/** Interaction handler for swapping an index number of a registered course with another student.
	 *    	
	 * @param username User name of student.
	 * @throws IOException
	 * @throws ParseException
	 */    	
	//7
	public static void swopIndex(String username) throws IOException, ParseException {
		
		Scanner indexUpdater = new Scanner(System.in);
		String user, peerUser, courseId, yourIndex, peerIndex, peerPassword;
		user = username;
		
		
		System.out.println("Please enter the course ID you would like to swap: ");
		courseId = indexUpdater.next();
		if(StudentControl.checkValidCourseID(courseId)==1) {
			studentMenu(user);
		}
		
		System.out.println("Please enter the course index you would like to swap: ");
		yourIndex = indexUpdater.next();
		if(StudentControl.checkValidCourseIndex(yourIndex)==1) {
			studentMenu(user);
		}
		
		System.out.println("Please enter the ID of the student you want to swop index with: ");
		peerUser = indexUpdater.next();
		
		Console cons = System.console();
		char[] passwordch = cons.readPassword("Please enter your password: ");
		peerPassword = new String(passwordch);
		
		System.out.println("Please enter the course index of your peer: ");
		peerIndex = indexUpdater.next();
		
		String hashedpassword = LoginControl.hashPassword(peerPassword);
		if(Login.userValidation(username,hashedpassword)==1){
			System.out.println("Peer password validated");
		}else {
			System.out.println("Please retry with valid peer details.");
			swopIndex(user);
		}
		
		
		StudentControl.swopPeerIndex(user, peerUser, courseId, yourIndex, peerIndex);
     	studentMenu(username);
	}
		
	
	/*public static int testAddFunc(String addUser) throws IOException {
  	    
		Scanner courseAdder = new Scanner(System.in);
		String studentuser, courseId, indexNo,strVacancy;
		int vacancy,intVacancy;
		studentuser = addUser;
		
		System.out.println("Please enter the course ID that you want to register: ");
		courseId = courseAdder.next();
		
		//Add code to check for valid course ID
		
		//Reading information from registered course file
		CSVReader registerReader = new CSVReader(new FileReader("database/registered_courses.txt"),',');
			List<String[]> csvBody = registerReader.readAll();
			//Loop through to check if the user have already registered for the course.
			for(int i=0; i<csvBody.size(); i++) {
				if(csvBody.get(i)[0].equals(studentuser) & csvBody.get(i)[1].equals(courseId)) {
					registerReader.close();
					return 2;
            	}
            	
			}
			
		registerReader.close();
		
		System.out.println("Please enter the course index number that you want to register: ");
		indexNo = courseAdder.next();
		//Add codes to check for valid index number
		
		//Add codes here to get the vacancy of the course index
		CSVReader vacancyReader = new CSVReader(new FileReader("database/course_index.txt"),',');
			List<String[]> csvMain = vacancyReader.readAll();
		//If the course index is full vacancy, then add student to wait list
			for(int i=0; i<csvMain.size(); i++) {
				String[] strArray2 = csvMain.get(i);
				for(int j=0; j<strArray2.length; j++) {
					if(csvMain.get(i)[0].equals(courseId) & csvMain.get(i)[2].equals(indexNo)) {
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
	        					//add code here to append student user name to the end of the queue in waiting list csv
	        					//**********
	        					writer1.close();
	        					return 4;
	        				}
	            		 }
	            	}
				}
	            
			}
						

		//If student did not register for course yet, and the index is available, go ahead to add course index for student to his/her registered courses.
		FileWriter writer = new FileWriter("database/registered_courses.txt",true); 
		writer.append('"'+studentuser+'"'+','); 
		writer.append('"'+courseId+'"'+',');
		writer.append('"'+indexNo+'"'+',');
		writer.append("\n");
		
		
		vacancyMinus(indexNo);
		System.out.println("Congratulations! Your course has been registered.");
		return -1;
	}
	
	public static void vacancyMinus(String indexNo) throws IOException {
		int intVacancy = 0;
		String strVacancy = "";

		CSVReader vacancyMinus = new CSVReader(new FileReader("database/course_index.txt"));
		List<String[]> csvMain2 = vacancyMinus.readAll();
			for(int i=0; i<csvMain2.size(); i++) {
				System.out.println(indexNo);
				System.out.println(csvMain2.get(i)[1]);
				if(csvMain2.get(i)[1].equals(indexNo)) {
					intVacancy = Integer.parseInt(csvMain2.get(i)[9]);
					intVacancy--;
					strVacancy=String.valueOf(intVacancy);
					csvMain2.get(i)[9] = strVacancy;
					System.out.println(strVacancy);
				}
			}
		CSVWriter writer2 = new CSVWriter(new FileWriter("database/course_index.txt"), ',');
		writer2.writeAll(csvMain2);
		writer2.flush();
		writer2.close();
	}*/
	


	
}







