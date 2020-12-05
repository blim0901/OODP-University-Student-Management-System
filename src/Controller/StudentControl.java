package Controller;

import Entity.Student;
import Entity.CourseIndex;
import Entity.Course;
import Entity.CourseRegistration;
import Entity.StudentSchedule;

import java.io.Console;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;


/** Controller class for student access functional logic.
 * 
 */
public class StudentControl extends Control{
	
	/** Logic method for viewing the class schedules of a particular course.
	 * 
	 * @param courseId Course code to check.
	 * @throws IOException
	 */
	//print course information for 1 course
	public static void printCourseDetails(String courseId) throws IOException {
		Course.getCourseDetails(courseId);	
	}
	
	/** Logic method for viewing the vacancies of a particular course.
	 * 
	 * @param courseId Course code to check.
	 * @throws IOException
	 */
	//print vacancy of indexes in course
	public static void printVacancyList(String courseId) throws IOException {
		CourseIndex.getIndexVacancy(courseId);
	}
	
	/** Logic method for registering a course.
	 * 
	 * @param addUser Student registering for the course.
	 * @throws IOException
	 */
	//register course for student
	public static int addCourse(String addUser) throws IOException {
		int flag = CourseRegistration.registerCourse(addUser);
		return flag;
	}
	
	/** Logic method for dropping a course.
	 * 
	 * @param user Student dropping the course.
	 * @throws IOException
	 */
	//drop course for student
	public static int dropCourse(String user) throws IOException {
		int flag = CourseRegistration.deRegisterCourse(user);
		return flag;
	}
	
	/** Logic method for viewing all courses student has registered for.
	 * 
	 * @param addUser Student to check.
	 * @throws IOException
	 */
	//print student registered courses
	public static void printRegdCourse(String addUser) throws IOException {
		StudentSchedule.getRegdCourse(addUser);
	}
	
	/** Logic method for viewing all courses student is in waitlist for.
	 * 
	 * @param addUser Student to check.
	 * @throws IOException
	 */
	//print student courses indexes in waiting list
	public static void printWaitlist(String addUser) throws IOException {
		StudentSchedule.getIndexWaitList(addUser);
	}
	
	/** Logic method for changing the index number of a course student is registered for. 
	 * 
	 * @param username User name of student.
	 * @param courseId Course code.
	 * @param oldIndex Index number to change from.
	 * @param newIndex Index number to change to.
	 * @throws IOException
	 */
	//change the index for a student's registered course
	public static void changeRegdIndex(String username, String courseId, String oldIndex, String newIndex) throws IOException {
		CourseRegistration.updateRegdIndex(username, courseId, oldIndex, newIndex);
	}
	
	/** Logic method for swapping the index number of a course student is registered for, with another student.
	 * 
	 * @param user User name of student initiating the swap.
	 * @param peerUser User name of student willing to swap.
	 * @param courseId Course code.
	 * @param yourIndex Index number of user.
	 * @param peerIndex Index number of swapping peer.
	 * @throws IOException
	 */
	//change the index for a student's registered course
	public static void swopPeerIndex(String user, String peerUser, String courseId, String yourIndex, String peerIndex) throws IOException {
		CourseRegistration.updateSwopIndex(user, peerUser, courseId, yourIndex, peerIndex);
	}
	


}
