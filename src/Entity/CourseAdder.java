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


/** Entity class for handling course creation functions.
 * 
 */
public class CourseAdder {

	private String courseNumberCode; 
	private String indexNumber; 
	
	/** Data method for writing new course information to data file.
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
	public static void addCourseList(String courseCode, String school, String indexNo, String groupNo, String startTime,String endTime,String location, String lessonType, String day, String oddEven, String vacancy, String size, String acadUnits) throws IOException {
  	    
		FileWriter writer = new FileWriter("database/course_index.txt",true); 
		writer.append('"'+courseCode+'"'+',');   
		writer.append('"'+school+'"'+','); 
		writer.append('"'+indexNo+'"'+',');
		writer.append('"'+lessonType+'"'+',');
		writer.append('"'+groupNo+'"'+',');
		writer.append('"'+day+'"'+',');
		writer.append('"'+oddEven+'"'+',');
		writer.append('"'+startTime+'"'+',');
		writer.append('"'+endTime+'"'+',');
		writer.append('"'+location+'"'+',');
		writer.append('"'+vacancy+'"'+',');
		writer.append('"'+size+'"'+',');
		writer.append('"'+acadUnits+'"'+",\n");
		writer.close();
	}


    /** Data method for overwriting course information of a particular course.
	 * 
	 * @param courseUpdate Course code for updating.
	 * @param courseCode Course code to update.
	 * @param school School to update
	 * @param indexNo Index number to update.
	 * @param vacancy Vacancy to update.
	 * @param size Course size to update.
	 * @throws IOException
	 */
	public static void updateCourseList(int courseUpdate, String courseCode, String  school, String  indexNo, String  vacancy, String  size) throws IOException {
	    CSVReader reader = new CSVReader(new FileReader("database/course_index.txt"),',');
	        List<String[]> csvBody = reader.readAll();
	        for(int i=0; i<csvBody.size(); i++){
	            String[] strArray = csvBody.get(i);
	            for(int j=0; j<strArray.length; j++){
	            	if((i+1)==courseUpdate) {
	            		csvBody.get(i)[0] = courseCode;
	            		csvBody.get(i)[1] = school;
	            		csvBody.get(i)[2] = indexNo;
	            		csvBody.get(i)[10] = vacancy;
	            		csvBody.get(i)[11] = size;
	            	}
	            }
	     }
	     reader.close();
	     CSVWriter writer = new CSVWriter(new FileWriter("database//course_index.txt"), ',');
	     writer.writeAll(csvBody);
	     writer.flush();
	     writer.close();
	     CourseIndex.getCourseList();
	     System.out.println("-------------------------------------------------------");
	     System.out.println("Course information updated!!");
	     System.out.println("-------------------------------------------------------");
	     System.out.println("");
	}



	
}