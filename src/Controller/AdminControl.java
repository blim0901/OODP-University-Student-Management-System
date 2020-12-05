package Controller;

import Entity.Student;
import Entity.CourseIndex;
import Entity.CourseAdder;
import java.io.Console;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import java.util.regex.*; 

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;



/** Controller class for admin access functional logic.
*
*/
public class AdminControl extends Control{
		/** Logic method for adding access period for a student.
		 * 
		 * @param studentNo ID of student to be edited.
		 * @param dateStr New date of access to add.
		 * @throws IOException
		 */
		//change access time for students
		public static void addAccesstime(int studentNo, String dateStr) throws IOException {
			Student.updateAccessTime(studentNo,dateStr);	
		}
		
		/** Logic method for registering a new student to the STARS system.
		 * 
		 * @param addUser User name of new student to be added.
		 * @param addPasswordStr Password of new student to be added.
		 * @param name Real name of new student to be added.
		 * @param matricNo Matriculation number of new student to be added.
		 * @param gender Gender of new student to be added.
		 * @param nationality Nationality of new student to be added.
		 * @throws IOException
		 */
		//add students to the txt file "registers them"
		public static void addStudents(String addUser, String addPasswordStr, String name,String matricNo,String gender,String nationality) throws IOException {
			Student.addStudentList(addUser, addPasswordStr, name, matricNo, gender, nationality);	
		}
		
		/** Logic method for retrieving full list of students.
		 * 
		 * @throws IOException
		 */
		//get the list of students
		public static void getStudents() throws IOException {
			Student.getStudentList();
		}
		
		/** Logic method for retrieving list of students registered to a particular index number.
		 * 
		 * @param courseIndex Course index number to check.
		 * @throws IOException
		 */
		//get the list of student index
		public static void checkStudentsIndex(String courseIndex) throws IOException {
			Student.getStudentIndex(courseIndex);
		}
		
		
		/** Logic method for retrieving list of students registered to a particular course.
		 * 
		 * @param courseCode Course code to check.
		 * @throws IOException
		 */
		//get the list of student course code
		public static void checkStudentsCode(String courseCode) throws IOException {
			Student.getStudentsCode(courseCode);
		}
		
		/** Logic method for adding a new course.
		 * 
		 * @param courseCode Course code to add.
		 * @param school School to add.
		 * @param indexNo Index number to add.
		 * @param groupNo Group number to add.
		 * @param startTime Start time to add.
		 * @param endTime End time to add.
		 * @param location Location to add.
		 * @param lessonType Lesson type to add.
		 * @param day Lesson day to add.
		 * @param oddEven The weeks which lessons happen to add.
		 * @param vacancy Vacancy of course to add.
		 * @param size Size of course to add.
		 * @param acadUnits Academic unit of course to add.
		 * @throws IOException
		 */
		//add course to course list
		public static void addCourse(String courseCode, String school, String indexNo, String groupNo, String startTime,String endTime,String location, String lessonType, String day, String oddEven, String vacancy, String size, String acadUnits) throws IOException {
			CourseAdder.addCourseList(courseCode, school, indexNo, groupNo, startTime, endTime, location, lessonType, day, oddEven, vacancy, size, acadUnits);	
		}
		
		
		/** Logic method for retrieving full list of courses.
		 * 
		 * @throws IOException
		 */
		//get the course list
		public static void getCourse() throws IOException {
			CourseIndex.getCourseList();	
		}
		
		
		/** Logic method for updating a particular course code.
		 * 
		 * @param courseUpdate Course code for updating.
		 * @param courseCode Course code to update.
		 * @param school School to update
		 * @param indexNo Index number to update.
		 * @param vacancy Vacancy to update.
		 * @param size Course size to update.
		 * @throws IOException
		 */
		//update course list
		public static void updateCourse(int courseUpdate, String courseCode, String  school, String  indexNo, String  vacancy, String  size) throws IOException {
			CourseAdder.updateCourseList(courseUpdate, courseCode, school, indexNo, vacancy, size);
		}
		
		
		/**	Logic method for checking the amount of vacancies of a particular course.
		 * 
		 * @param courseCode Course code to check.
		 * @throws IOException
		 */
		//check course vacancy
		public static void checkCourseSlots(String courseCode) throws IOException {
			CourseIndex.getCourseVacancy(courseCode);
		}
		
		
		
		/**	Logic method for checking the validity of student number
		 * 
		 * @param studentID Student ID to check.
		 * @throws IOException
		 */
		//check the validity of student number
		public static int checkValidStudentID(String studentID) throws IOException {
			CSVReader reader = new CSVReader(new FileReader("database/students.txt"),',');
	        List<String[]> csvBody = reader.readAll();
	        for(int i=0; i<csvBody.size(); i++){
				if(csvBody.get(i)[2].equals(studentID)) {
					return -1;
				}
			}
			reader.close();
			System.out.println("Invalid Student ID. Please try again.");
			return 1;
	
		}
		
		
		/**	Logic method for checking the validity of student username
		 * 
		 * @param addUser Student username to check.
		 * @throws IOException
		 */
		//check the validity of student username
		public static int checkValidUsername(String addUser) throws IOException {
			CSVReader reader = new CSVReader(new FileReader("database/login.txt"),',');
	        List<String[]> csvBody = reader.readAll();
	        for(int i=0; i<csvBody.size(); i++){
				if(csvBody.get(i)[0].equals(addUser)) {
					return -1;
				}
			}
			reader.close();
			System.out.println("Invalid Student Username. Please try again.");
			return 1;
	
		}
	
		
		/**	Logic method for checking the validity of date entered
		 * 
		 * @param strDate Date string to check.
		 * @throws IOException
		 */
		//check the validity of date entered
		public static boolean validateDate(String strDate) throws IOException {
			/* Check if date is 'null' */
			if (strDate.trim().equals(""))
			{
			    return false;
			}
			/* Date is not 'null' */
			else
			{
			    /*
			     * Set preferred date format,
			     * For example MM-dd-yyyy, MM.dd.yyyy,dd.MM.yyyy etc.*/
			    SimpleDateFormat sdfrmt = new SimpleDateFormat("dd-MM-yyyy");
			    sdfrmt.setLenient(false);
			    /* Create Date object
			     * parse the string into date 
		             */
			    try
			    {
			        Date javaDate = sdfrmt.parse(strDate); 
			        System.out.println(strDate+" is valid date format");
			    }
			    /* Date format is invalid */
			    catch (ParseException e)
			    {
			        System.out.println(strDate+" is Invalid Date format");
			        return false;
			    }
			    /* Return true if date format is valid */
			    return true;
			}
		   }
			
		
		/**	Logic method for checking the validity of nationality
		 * 
		 * @param nationality Nationality to check.
		 * @throws IOException
		 */
		//check the validity of nationality
		public static int checkValidCountry(String nationality) throws IOException {
	  		
			if(!nationality.equals("SG") && !nationality.equals("MY") && !nationality.equals("CN") && !nationality.equals("IN") && !nationality.equals("JP") && !nationality.equals("HK")) {
				System.out.println("Please enter a valid nationality, in caps");
				return 1;
			}
			
			return 0;
		}
		
		
		/**	Logic method for checking the validity of lessons
		 * 
		 * @param lesson Lessons to check.
		 * @throws IOException
		 */
		//check the validity of lessons
		public static int checkValidLesson(String lesson) throws IOException {
	  		
			if(!lesson.equals("Lecture") && !lesson.equals("Tutorial") && !lesson.equals("Seminar")) {
				System.out.println("Please enter a valid lesson. First letter is capitalised.");
				return 1;
			}
			
			return 0;
		}
		
		/**	Logic method for checking the validity of day
		 * 
		 * @param day Day to check.
		 * @throws IOException
		 */
		//check the validity of day
		public static int checkValidDay(String day) throws IOException {
	  		
			if(!day.equals("Monday") && !day.equals("Tuesday") && !day.equals("Wednesday") && !day.equals("Thursday") && !day.equals("Friday")) {
				System.out.println("Please enter a valid day, first letter is capitalised.");
				return 1;
			}
			
			return 0;
		}
		
		/**	Logic method for checking the validity of week
		 * 
		 * @param oddeven Week to check if it is odd, even or all.
		 * @throws IOException
		 */
		//check the validity of week
		public static int checkValidWeek(String oddeven) throws IOException {
	  		
			if(!oddeven.equals("ODD") && !oddeven.equals("EVEN") && !oddeven.equals("ALL")) {
				System.out.println("Please enter a valid week, all letters are capitalised.");
				return 1;
			}
			
			return 0;
		}
		
		/**	Logic method to validate the time in 24-hour format 
		 * 
		 * @param time Time format to check.
		 * @throws IOException
		 */
		// Function to validate the time in 24-hour format 
	    public static boolean isValidTime(String time) 
	    { 
	  
	        // Regex to check valid time in 24-hour format. 
	        String regex = "([01]?[0-9]|2[0-3]):[0-5][0-9]"; 
	  
	        // Compile the ReGex 
	        Pattern p = Pattern.compile(regex); 
	  
	        // If the time is empty 
	        // return false 
	        if (time == null) { 
	            return false; 
	        } 
	  
	        // Pattern class contains matcher() method 
	        // to find matching between given time 
	        // and regular expression. 
	        Matcher m = p.matcher(time); 
	  
	        // Return if the time 
	        // matched the ReGex 
	        return m.matches(); 
	    }
	    
	    
	    /**	Logic method to check for existing username
		 * 
		 * @param addUser Exising username to check.
		 * @throws IOException
		 */
	    //check for existing username
		public static int existingUsername(String addUser) throws IOException {
			CSVReader reader = new CSVReader(new FileReader("database/login.txt"),',');
	        List<String[]> csvBody = reader.readAll();
	        for(int i=0; i<csvBody.size(); i++){
				if(csvBody.get(i)[0].equals(addUser)) {
					System.out.println("This username already exists. Please try another username.");
					return 1;
				}
			}
			reader.close();
			return -1;
	
		}
		
		/**	Logic method to check for existing matriculation number
		 * 
		 * @param matricNo Exising matriculation number to check.
		 * @throws IOException
		 */
		//check for existing matriculation number
		public static int existingMatricNumber(String matricNo) throws IOException {
			CSVReader reader = new CSVReader(new FileReader("database/students.txt"),',');
	        List<String[]> csvBody = reader.readAll();
	        for(int i=0; i<csvBody.size(); i++){
				if(csvBody.get(i)[2].equals(matricNo)) {
					System.out.println("This matriculation number already exists. Please try again.");
					return 1;
				}
			}
			reader.close();
			return -1;
	
		}
		
		



}
