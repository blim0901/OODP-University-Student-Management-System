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


/** Entity class for handling student data read/write.
 * 
 */
public class Student extends User {

	private String name;
	private int id;
	private String school;  //the school that this student belongs to, eg SCSE, MAE, EEE
	private String mobileNumber;
	private String homeAddress;
	private int totalRegdAU; //the sum of the total AU of the courses that the student had registered
	private String username;
	
	public Student(String username, String password) 
			throws NoSuchAlgorithmException {
		super(username, password, STUDENT);
	}
	
	public Student(String username, String password, String name, int id, String school, String mobileNumber, String homeAddress, int totalRegdAU) 
			throws NoSuchAlgorithmException {
		super(username, password, STUDENT);
		this.name = name;
		this.id = id;
		this.school = school;
		this.mobileNumber = mobileNumber;
		this.homeAddress = homeAddress;
		this.totalRegdAU = totalRegdAU;
	}
	
	
	/** Data method for overwriting access period of a particular student.
	 * 
	 * @param studentNo Student to update.
	 * @param dateStr New access date to write.
	 * @throws IOException
	 */
	public static void updateAccessTime(int studentNo, String dateStr) throws IOException {
		  	    
	      CSVReader reader = new CSVReader(new FileReader("database/students.txt"),',');
	        List<String[]> csvBody = reader.readAll();
	        for(int i=0; i<csvBody.size(); i++){
	            	if(studentNo==(i+1)) {
	            		 csvBody.get(i)[5] = dateStr;
	            	}

	        }
	        reader.close();
	        CSVWriter writer = new CSVWriter(new FileWriter("database/students.txt"), ',');
	        writer.writeAll(csvBody);
	        writer.flush();
	        writer.close();
	        System.out.println("User timing updated");
	}
	
	
	/** Data method for writing a new student to list of registered STARS student users.
	 * 
	 * @param addUser User name of student to add.
	 * @param addPasswordStr Password of student to add.
	 * @param name Real name of student to add.
	 * @param matricNo Matriculation number of student to add.
	 * @param gender Gender of student to add.
	 * @param nationality Nationality of student to add.
	 * @throws IOException
	 */
	public static void addStudentList(String addUser, String addPasswordStr, String name,String matricNo,String gender,String nationality) throws IOException {
  	    
		CSVReader reader = new CSVReader(new FileReader("database/login.txt"),',');
        List<String[]> csvBody = reader.readAll();
        for(int i=0; i<csvBody.size(); i++){
            String[] strArray = csvBody.get(i);
            for(int j=0; j<strArray.length; j++){
            	if(csvBody.get(i)[0].equals(addUser)) {
            		System.out.println("Error User already exists.");
                	return;
            	}
            }
        }
        reader.close();
	
		FileWriter writer = new FileWriter("database/login.txt",true); 
		writer.append('"'+addUser+'"'+',');   //username
		writer.append('"'+addPasswordStr+'"'+','); //password
		writer.append('"'+"Student"+'"'+",\n");
		writer.close();
		writer = new FileWriter("database/students.txt",true); 
		writer.append('"'+addUser+'"'+','); //username
		writer.append('"'+name+'"'+',');
		writer.append('"'+matricNo+'"'+',');
		writer.append('"'+gender+'"'+',');
		writer.append('"'+nationality+'"'+",-,\n");
		writer.close();
		System.out.println("User successfully added");
	}
	
	
	/** Data method for reading full list of students.
	 * 
	 * @throws IOException
	 */
	public static void getStudentList() throws IOException {
  	    
		CSVReader reader = new CSVReader(new FileReader("database/students.txt"),',');
	    int studentCounter = 1;
        List<String[]> csvBody = reader.readAll();
        for(int i=0; i<csvBody.size(); i++){
            String[] strArray = csvBody.get(i);
            System.out.println("Student"+studentCounter+ ": " );
            studentCounter++;
            for(int j=0; j<strArray.length; j++){
            		System.out.println(csvBody.get(i)[j]);
            }
            System.out.println("");
        }
        reader.close();
	}
	
	
	/** Data method for reading list of students registered to a particular index number.
	 * 
	 * @param courseIndex Index number to check.
	 * @throws IOException
	 */
	public static void getStudentIndex(String courseIndex) throws IOException {
  	    
		CSVReader reader = new CSVReader(new FileReader("database/registered_courses.txt"),',');
	    int flag=0;
        List<String[]> csvBody = reader.readAll();
        System.out.println("Student IDs");
        for(int i=0; i<csvBody.size(); i++){
        	if(csvBody.get(i)[2].equals(courseIndex)) {
        		System.out.println(csvBody.get(i)[0]);
        		flag=1;
        	}
        }
        if(flag==0) {
        	System.out.println("Invalid index number");
        }
	}
	
	
	/** Data method for reading list of students registered to a particular course code.
	 *  
	 * @param courseCode Course code to check.
	 * @throws IOException
	 */
	public static void getStudentsCode(String courseCode) throws IOException {
  	    
		int flag=0;
	    CSVReader reader = new CSVReader(new FileReader("database/registered_courses.txt"),',');
        List<String[]> csvBody = reader.readAll();
        System.out.println("Student IDs");
        for(int i=0; i<csvBody.size(); i++){
        	if(csvBody.get(i)[1].equals(courseCode)) {
        		System.out.println(csvBody.get(i)[0]);
        	}
        }
        /*if(flag==0) {
        	System.out.println("Invalid course number");

        }*/
	}


}
	
	/*
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String name) {
		this.username = username;
	}
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public School getSchool() {
		return school;
	}
	
	public void setSchool(School school) {
		this.school = school;
	}
	
	public String getMobileNumber() {
		return mobileNumber;
	}
	
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	
	public String getHomeAddress() {
		return homeAddress;
	}
	
	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}
	
	public int getTotalRegdAU() {
		return totalRegdAU;
	}
	
	public void setTotalRegdAU(int totalRegdAU) {
		this.totalRegdAU = totalRegdAU;
	}
	
	//Add a method to add course
	
	
	
	public String toString(){
		String toReturn = "";
		toReturn 	+= "Username: " + getName() + "\n"
					+ "E-mail: " + getEmail() + "\n"
					+ "Matriculation ID: " + getId() + "\n"
					+ "School: " + getSchool() + "\n"
					+ "Mobile number: " + getMobileNumber() + "\n"
					+ "Home Address: " + getHomeAddress() + "\n"
					+ "Total Registered AU: " + getTotalRegdAU();
		return toReturn; 
		
	*/
	
	
	
