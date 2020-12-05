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


/** Entity class for handling course index number data read/write.
 * 
 */
public class CourseIndex {

	private String courseCode;
	private int indexCode;
	private String day;
	private String startTime;
	private String endTime;
	private String  lessonType;
	private String remarks;
	
	
	
	public CourseIndex (String courseCode, int indexCode, String day, String startTime, String endTime, String lessonType, String remarks) {
		this.courseCode = courseCode;
		this.indexCode = indexCode;
		this.day = day;
		this.startTime = startTime;
		this.endTime = endTime;
		this.lessonType = lessonType;
		this.remarks = remarks;
	}
	
	
	
	/** Data method for reading all course information.
	 * 
	 * @throws IOException
	 */
	public static void getCourseList() throws IOException {
  	    
		CSVReader reader = new CSVReader(new FileReader("database/course_index.txt"),',');
        List<String[]> csvBody = reader.readAll();
        for(int i=0; i<csvBody.size(); i++){
            String[] strArray = csvBody.get(i);
            System.out.println("Course "+(i+1)+ ": " );
            System.out.println("Course code: "+csvBody.get(i)[0]);
            System.out.println("School: "+csvBody.get(i)[1]);
            System.out.println("Index No.: "+csvBody.get(i)[2]);
            System.out.println("Lesson type: "+csvBody.get(i)[3]);
            System.out.println("Group No.: "+csvBody.get(i)[4]);
            System.out.println("Day: "+csvBody.get(i)[5]);
            System.out.println("Start time: "+csvBody.get(i)[6]);
            System.out.println("End time: "+csvBody.get(i)[7]);
            System.out.println("Location: "+csvBody.get(i)[8]);
            System.out.println("Week Type: "+csvBody.get(i)[9]);
            System.out.println("Vacancy: "+csvBody.get(i)[10]);
            System.out.println("Size: "+csvBody.get(i)[11]);
            System.out.println("AUs: "+csvBody.get(i)[12]);
            System.out.println("");
        }
        reader.close();
	}
	
	
	
	/** Data method for reading vacancies of a particular course.
	 *  
	 * @param courseCode Course code to check.
	 * @throws IOException
	 */
	public static void getCourseVacancy(String courseCode) throws IOException {
  	    
		ArrayList<String> indexList = new ArrayList<String>();
		indexList.add("");
		int vacancyCounter=0, temp;
		String courseVacancy="";
		CSVReader reader = new CSVReader(new FileReader("database/course_index.txt"),',');
        List<String[]> csvBody = reader.readAll();
        for(int i=0; i<csvBody.size(); i++){
        	if(csvBody.get(i)[0].equals(courseCode)) {
        		if(indexList.contains(csvBody.get(i)[2])) {
        			//continue
        		}
        		else {
        			indexList.add(csvBody.get(i)[2]);
        			temp = Integer.parseInt(csvBody.get(i)[10]);
        			vacancyCounter = vacancyCounter + temp;
        		}
        	}
        	
        }
        System.out.println("Vacancy for "+courseCode+" is:"+vacancyCounter);
        
	}
	
	
	
	/** Data method for reading vacancy of a particular index number.
	 * 
	 * @param courseId Index number to check.
	 * @throws IOException
	 */
	public static void getIndexVacancy(String courseId) throws IOException {
  	    
		CSVReader reader = new CSVReader(new FileReader("database/course_index.txt"),',');
    	List<String[]> csvBody = reader.readAll();
    	for(int i=0; i<csvBody.size(); i++){
            System.out.println("Here is the course index vacancy for " +  courseId + ": ");
            	if(csvBody.get(i)[0].equals(courseId)) {
            		System.out.println("-----------------------------------------------");
            		System.out.println("Index Number: " + csvBody.get(i)[2]);
            		System.out.println("Lesson: " + csvBody.get(i)[3]);
            		System.out.println("Vacancy: " + csvBody.get(i)[10] + " / " + csvBody.get(i)[11]);
            	}
        }
    	reader.close();
	}
	
	
	/** Data method for checking if a particular index number has any vacancy.
	 * 
	 * @param newIndex Index number to check.
	 * @throws IOException
	 */
	public static int isVacant(String newIndex) throws IOException {	
		CSVReader reader = new CSVReader(new FileReader("database/course_index.txt"),',');
    	List<String[]> csvBody = reader.readAll();
    	for(int i=0; i<csvBody.size(); i++){
            	if(csvBody.get(i)[2].equals(newIndex)) {
            		if(!csvBody.get(i)[10].equals("0")) {
            			return 1;
            		}

            	}
        }
    	reader.close();
    	return -1;
	}
	
	
	/*public void viewCourseIndex(String courseCode) {
		
		
	try(Scanner in = new Scanner(indexFile)){
			while(in.hasNextLine()){
				if ( ) { //Add code so that it will print only when it is the target courseCode
					String id = in.next();
					String start = in.next().trim();
					String end = in.next().trim();
					String sDay = in.next().trim();
					String eDay = in.next().trim();
					String tut = in.nextLine();
					
					System.out.println(id + " \t " + start + " \t " + end +" \t " + sDay + " \t " + eDay + " \t " + tut );
				}
				
			}
		} catch(FileNotFoundException e){
			System.out.println("File Not Found!");
		}
		
	}
*/
	
	
}