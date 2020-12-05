package Controller;

import Entity.Student;
import Entity.CourseIndex;
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

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

/** Main controller class for admin and student controllers to extend
*
*/
public class Control {

	/**	Logic method for checking the validity of course index number
	 * 
	 * @param indexNo Index number to check.
	 * @throws IOException
	 */
	//check the validity of a course index number
	public static int checkValidCourseIndex(String indexNo) throws IOException {
		CSVReader reader = new CSVReader(new FileReader("database/course_index.txt"),',');
        List<String[]> csvBody = reader.readAll();
        for(int i=0; i<csvBody.size(); i++){
			if(csvBody.get(i)[2].equals(indexNo)) {
				return -1;
			}
		}
		reader.close();
		System.out.println("Invalid Course Index Number. Please try again.");
		return 1;
	
	}
	
	/**	Logic method for checking the validity of course ID
	 * 
	 * @param courseId Course ID to check.
	 * @throws IOException
	 */
	//check the validity of a course ID
	public static int checkValidCourseID(String courseId) throws IOException {
		CSVReader reader = new CSVReader(new FileReader("database/course_index.txt"),',');
        List<String[]> csvBody = reader.readAll();
        for(int i=0; i<csvBody.size(); i++){
			if(csvBody.get(i)[0].equals(courseId)) {
				return -1;
			}
		}
		reader.close();
		System.out.println("Invalid CourseID. Please try again.");
		return 1;

	}
	
	
}



