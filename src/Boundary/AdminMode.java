package Boundary;

import Controller.AdminControl;
import Controller.LoginControl;
import java.io.Console;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Scanner;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;


/** Boundary class that handles admin's STARS access user interface.
 * 
 */
public class AdminMode implements Modes{

	private String id; 
	private String pw; 
	
	/** Method loaded upon administrator login. Initialises admin access UI.
	 * 
	 * @throws IOException
	 */
	public static void adminMenu() throws IOException {
		Scanner sc = new Scanner(System.in);
		System.out.println("--------------------------------------------------------------");
		System.out.println("--------------------------------------------------------------");
		System.out.println("Login successful, welcome Admin!");
		while (true) {
		System.out.println("1.Edit student access period \n2.Add Student Accounts \n3.Manage Courses "
				+ "\n4.Check slots for an index \n5.Print student list by index no. \n6.Print student list by course\n7.Quit\nPlease enter your choice: ");
		int selector = 0;
		if(sc.hasNextInt()) {
			selector = sc.nextInt();
		}
		else {
			System.out.println("Invalid entry for menu. Please enter only numeric values.");
			adminMenu();
		} 
		System.out.println("--------------------------------------------------------------");
		System.out.println("--------------------------------------------------------------");
		
		switch(selector) {
		//Calls adminMode class, via modes Interface
		case 1:
			AdminMode.editAccessPeriod();
			break;
		case 2:
			AdminMode.manageStudent();
			break;
		case 3:
			AdminMode.manageCourses();
			break;
		case 4:
			AdminMode.checkSlots();
			break;
		case 5:
			AdminMode.indexNoList();
			break;
		case 6:
			AdminMode.courseNoList();
			break;
		case 7:
			System.out.println("Session ended. Thank you.");
			System.exit(0);
			break;

		default:
			System.out.println("Please enter a valid choice!");
			break;
		
		}
		}
	}
	
	/** Interaction handler for editing the allowed access period of a student.
	 * 
	 * @throws IOException
	 */
	//1 
		public static void editAccessPeriod() throws IOException{
			AdminControl.getStudents();
			int studentNo=0;
			Scanner accessSelector = new Scanner(System.in);
			System.out.println("Select the number of the student you would like to add an access timing for: ");
			if(accessSelector.hasNextInt()) {
				studentNo = accessSelector.nextInt();
			}
			else {
				System.out.println("Invalid entry for student number. Please enter only integer values.");
				adminMenu();
			} 
			String dateStr;
			System.out.println("Please enter a access date for the user: (dd-mm-yyyy)");
			dateStr = accessSelector.next();
			if(!AdminControl.validateDate(dateStr)) {
				System.out.println("Please re enter a valid date.");
				editAccessPeriod();
			}
			AdminControl.addAccesstime(studentNo,dateStr);
			adminMenu();
		
		}
	
		
	/** Interaction handler for adding a new student into the STARS system.
	 * 
	 * @throws IOException
	 */
	//2 
		public static void manageStudent() throws IOException {
		String addUser, addPasswordStr, name, matricNo, gender, nationality, hashedPassword;
		Scanner sc = new Scanner(System.in);
		char[] addPasswordch;
		AdminControl.getStudents();
		Console cons = System.console();
		addUser = cons.readLine("Please type the username of the student you would like to add: ");
		if (AdminControl.existingUsername(addUser)==1) {
		    manageStudent();
		}
		addPasswordch = cons.readPassword("Please type the password of the student you would like to add: ");
		System.out.println("Please type the name of the student you would like to add: ");
		if (!sc.hasNext("[A-Za-z]+")) {
		    System.out.println("Invalid name, only accept characters in this field. ");
		    manageStudent();
		}
		name = sc.next();
		matricNo = cons.readLine("Please type the matriculation number of the student you would like to add: ");
		//check for existing matric number in database************
		if (AdminControl.existingMatricNumber(matricNo)==1) {
		    manageStudent();
		}
		gender = cons.readLine("Please type the gender of the student you would like to add: ");
		if (LoginControl.checkValidGender(gender)==1) {
		    manageStudent();
		}
		nationality = cons.readLine("Please type the nationality of the student you would like to add: ");
		addPasswordStr = new String(addPasswordch);
		hashedPassword = LoginControl.hashPassword(addPasswordStr);

		if(LoginControl.checkEmptyField(addUser, addPasswordStr, name, matricNo, nationality)==1) {
			manageStudent();
		}
		//possible feature add validation for countries code by importing it in and crosschecking with country code
		if(AdminControl.checkValidCountry(nationality)==1) {
			manageStudent();
		}
		AdminControl.addStudents(addUser,hashedPassword,name,matricNo,gender,nationality);
		adminMenu();

	}
		
	
	/** Interaction handler for adding or editing a course code.
	 * 
	 * @throws IOException
	 */
	//3 
	public static void manageCourses() throws IOException {
		int courseOpt=0,courseUpdate=-1;
		String courseCode, school, indexNo, groupNo,
		startTime,endTime,location;
		String lessonType,day,oddEven,vacancy,size,acadUnits;
		Scanner courseOptions = new Scanner(System.in);
		System.out.println("Select an option 1. Add Course   2. Update course");
		if(courseOptions.hasNextInt()) {
			courseOpt = courseOptions.nextInt();
		}
		else {
			System.out.println("Invalid entry for choice. Please enter only numeric values.");
			manageCourses();
		} 
		if(courseOpt==1) {
			System.out.println("Current Courses: ");
			AdminControl.getCourse();
			// Course code,School,Index, lesson type, group,day,odd/even/all,start time, 
			//end time, location, vacancy, total slots, AUs
			// into course_index.txt
			System.out.println("Please enter the course code you would like to add: ");
			if (!courseOptions.hasNext("[A-Za-z0-9]+")) {
			    System.out.println("Invalid course code, only accept alphanumeric characters in this field. ");
			    manageCourses();
			}
			courseCode = courseOptions.next();
			System.out.println("Please enter the school of the course: ");
			if (!courseOptions.hasNext("[A-Za-z]+")) {
			    System.out.println("Invalid school, only accept characters in this field. ");
			    manageCourses();
			}
			school = courseOptions.next();
			System.out.println("Please enter the index number of the course: ");
			if (!courseOptions.hasNext("[A-Za-z0-9]+")) {
			    System.out.println("Invalid index number, only accept alphanumeric characters in this field. ");
			    manageCourses();
			}
			indexNo = courseOptions.next();
			System.out.println("Enter lesson type \n1.Lecture \n2. Tutorial\n3. Lab");
			lessonType = courseOptions.next();
			if(AdminControl.checkValidLesson(lessonType)==1) {
				manageCourses();
			}
			System.out.println("Enter grouping number: ");
			if (!courseOptions.hasNext("[A-Za-z0-9]+")) {
			    System.out.println("Invalid grouping number, only accept alphanumeric characters in this field. ");
			    manageCourses();
			}
			groupNo = courseOptions.next();
			System.out.println("Enter day of lesson: \n1. Monday\n2.Tuesday\n3.Wednesday \n4.Thursday \n5. Friday");
			day = courseOptions.next();
			if(AdminControl.checkValidDay(day)==1) {
				manageCourses();
			}
			System.out.println("Enter which week does this class occur in \n1.ODD \n2.EVEN \n3.ALL");
			oddEven = courseOptions.next();
			if(AdminControl.checkValidWeek(oddEven)==1) {
				manageCourses();
			}
			System.out.println("Enter what time does the class start (24hour format HH:MM)");
			startTime = courseOptions.next();
			if (!AdminControl.isValidTime(startTime)) {
				System.out.println("Invalid 24 hour timing. Please enter in format HH:MM ");
				manageCourses();
			}
			System.out.println("Enter what time does the class end (24hour format HH:MM)");
			endTime = courseOptions.next();
			if (!AdminControl.isValidTime(endTime)) {
				System.out.println("Invalid 24 hour timing. Please enter in format HH:MM ");
				manageCourses();
			}
			System.out.println("Enter location of lesson");
			if (!courseOptions.hasNext("[A-Za-z0-9]+")) {
			    System.out.println("Invalid location, only accept alphanumeric characters in this field. ");
			    manageCourses();
			}
			location = courseOptions.next();
			System.out.println("Please enter the course vacancy: ");
			if (!courseOptions.hasNext("[A-Za-z0-9]+")) {
			    System.out.println("Invalid course vacancy, only accept numeric characters in this field. ");
			    manageCourses();
			}
			vacancy = courseOptions.next();
			System.out.println("Please enter the course size: ");
			if (!courseOptions.hasNext("[A-Za-z0-9]+")) {
			    System.out.println("Invalid course size, only accept alphanumeric characters in this field. ");
			    manageCourses();
			}
			size = courseOptions.next();
			System.out.println("Please enter how many Academic Units is this mod: ");
			if (!courseOptions.hasNext("[A-Za-z0-9]+")) {
			    System.out.println("Invalid academic size, only accept alphanumeric characters in this field. ");
			    manageCourses();
			}
			acadUnits = courseOptions.next();
			AdminControl.addCourse(courseCode, school, indexNo, groupNo, startTime, endTime, location, lessonType, day, oddEven, vacancy, size, acadUnits);
			adminMenu();
		}
		else if(courseOpt==2) {
			AdminControl.getCourse();
			System.out.println("Please enter the course number you would like to update");
			if(courseOptions.hasNextInt()) {
				courseUpdate = courseOptions.nextInt();
			}
			else {
				System.out.println("Invalid course number. Please enter only numeric values.");
				adminMenu();
			} 
			System.out.println("Please enter the new course code you would like to update to: ");
			if (!courseOptions.hasNext("[A-Za-z0-9]+")) {
			    System.out.println("Invalid course code, only accept alphanumeric characters in this field. ");
			    manageCourses();
			}
			courseCode = courseOptions.next();
			System.out.println("Please enter the new school you would like to update to: ");
			if (!courseOptions.hasNext("[A-Za-z]+")) {
			    System.out.println("Invalid school, only accept characters in this field. ");
			    manageCourses();
			}
			school = courseOptions.next();
			System.out.println("Please enter the new index number of the course you would like to update to: ");
			if (!courseOptions.hasNext("[A-Za-z0-9]+")) {
			    System.out.println("Invalid index number, only accept alphanumeric characters in this field. ");
			    manageCourses();
			}
			indexNo = courseOptions.next();
			System.out.println("Please enter the new course vacancy you would like to update to: ");
			if (!courseOptions.hasNext("[A-Za-z0-9]+")) {
			    System.out.println("Invalid course vacancy, only accept numeric characters in this field. ");
			    manageCourses();
			}
			vacancy = courseOptions.next();
			System.out.println("Please enter the new course size you would like to update to: ");
			if (!courseOptions.hasNext("[A-Za-z0-9]+")) {
			    System.out.println("Invalid course size, only accept alphanumeric characters in this field. ");
			    manageCourses();
			}
			size = courseOptions.next();
			AdminControl.updateCourse(courseUpdate, courseCode, school, indexNo, vacancy, size);
			adminMenu();
		}
		else {
			System.out.println("Please enter a valid choice, 1 or 2.");
			adminMenu();
		}
	}
	
	/** Interaction handler for checking the vacancies for a particular course.
	 * 
	 * @throws IOException
	 */
	//4 
	public static void checkSlots() throws IOException {
		String courseCode;
		Scanner courseChecker = new Scanner(System.in);
		System.out.println("Please enter the course code you would like to check vacancy for: ");
		courseCode = courseChecker.next();
		if(AdminControl.checkValidCourseID(courseCode)==1) {
			checkSlots();
		}
		AdminControl.checkCourseSlots(courseCode);
		adminMenu();
	}
	
	
	/**	Interaction handler for viewing students registered to a particular index number. 
	 * 
	 * @throws IOException
	 */
	//5 
	public static void indexNoList() throws IOException {
		String courseIndex, courseID;
		Scanner courseChecker = new Scanner(System.in);
		System.out.println("Please enter the course ID you would like to check student list: ");
		courseID = courseChecker.next();
		if(AdminControl.checkValidCourseID(courseID)==1) {
			indexNoList();
		}
		System.out.println("Please enter the course index you would like to check student list: ");
		courseIndex = courseChecker.next();
		if(AdminControl.checkValidCourseIndex(courseIndex)==1) {
			indexNoList();
		}
		AdminControl.checkStudentsIndex(courseIndex);
		adminMenu();

	}
	
	/** Interaction handler for viewing students registered to a particular course.
	 * 
	 * @throws IOException
	 */
	//6
	public static void courseNoList() throws IOException {
		String courseCode;
		Scanner courseChecker = new Scanner(System.in);
		System.out.println("Please enter the course code you would like to check student list: ");
		courseCode = courseChecker.next();
		if(AdminControl.checkValidCourseID(courseCode)==1) {
			adminMenu();
		}
		AdminControl.checkStudentsCode(courseCode);
		adminMenu();
	}
	
	
	

}
