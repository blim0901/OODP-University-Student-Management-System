package Entity;
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

/** Entity class for handling course data read/write.
 * 
 */
public class Course {

	private String id;  //Course unique id
	private String name;  //Course name
	private int numberAU;  //number of AU for the course
	private String school;  //the school that this course belongs to, eg SCSE, MAE, EEE
	private String  type;  //Course type can be CORE, UE, GERPE
	private String gradingType;  //Course grading type
	private String description;  //a short paragraph to describe the course
	
	
	
	public Course(String id, String name, int numberAU, String school, String type, String gradingType, String description) {
		
		this.id = id;
		this.name = name;
		this.numberAU = numberAU;
		this.school = school;
		this.type = type;
		this.gradingType = gradingType;
		this.description = description;
		
	}
	
	/** Data method for reading course schedule details.
	 * 
	 * @param courseId Course code to check.
	 * @throws IOException
	 */
	public static void getCourseDetails(String courseId) throws IOException {
  	    
		CSVReader reader = new CSVReader(new FileReader("database/course_index.txt"),',');
    	List<String[]> csvBody = reader.readAll();
    	System.out.println("Here is the class schedule for course " +  courseId);
    	for(int i=0; i<csvBody.size(); i++){
        	if(csvBody.get(i)[0].equals(courseId)) {
        		System.out.println("-----------------------------------------------");
        		System.out.println("Index Number: " + csvBody.get(i)[2]);
        		System.out.println("Lesson Type: " + csvBody.get(i)[3]);
        		System.out.println("Group No.: " + csvBody.get(i)[4]);
        		System.out.println("Day: " + csvBody.get(i)[5]);
        		System.out.println("Time : " + csvBody.get(i)[6] + " - " + csvBody.get(i)[7]);
        		System.out.println("Venue: " + csvBody.get(i)[8]);
        		System.out.println("Week Type: "+csvBody.get(i)[9]);
        		System.out.println("Vacancy: "+csvBody.get(i)[10]);
                System.out.println("Size: "+csvBody.get(i)[11]);
                System.out.println("AUs: "+csvBody.get(i)[12]);
        	}
            
        }
    	reader.close();
	}
	
	
	/*
	public String getID() {
		return this.id;
	}
	
	public void setId(String id) {
        this.id = id;
    }
	
	public String getName() {
        return this.name;
    }
	
	public void setName(String name){
        this.name = name;
    }
	
	public int getNumberAU() {
        return this.numberAU;
    }
	
	public void setNumberAU(int numberAU) {
        this.numberAU = numberAU;
    }
	
	public School getSchool() {
        return this.school;
    }
	
	public void setSchool(School school) {
        this.school = school;
    }
	
	
	public CourseType getCourseType() {
        return this.type;
    }
	
	public void setCourseType(CourseType type) {
        this.type = type;
    }
	
	public String getGradingType() {
		return this.gradingType;
	}
	
	public void setGradingType(String gradingType) {
        this.gradingType = gradingType;
    }
	
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String description) {
        this.description = description;
    }
	
	
	public ArrayList<Integer> getIndex() {
		return this.index;
	}
	
	public void setIndex(ArrayList<Integer> index) {
        this.index = index;
    }
	
	
	public String getOverallVacancy() {
		//Insert codes to link to CourseIndex to sum up all of the index vacancies
	}
	
	*/
	
}
