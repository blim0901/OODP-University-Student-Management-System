package Controller;
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
//SINCE WE USE EXTERNAL JAR WE NEED TO INCLUDE IT WHILE RUNNING x

/** Controller class for login logic.
 * 
 */
public class LoginControl extends Control {
	/** Logic method for detecting empty (unfilled) fields in student registration.
	 * 
	 * @param addUser User name of student to add.
	 * @param addPasswordStr Password of student to add.
	 * @param name Real name of student to add.
	 * @param matricNo Matriculation number of student to add.
	 * @param nationality Nationality of student to add.
	 * @throws IOException
	 */
	public static int checkEmptyField(String addUser, String addPasswordStr, String name, String matricNo, String nationality) throws IOException {
		  		
		if(addUser.equals("")||addPasswordStr.equals("")||name.equals("")||matricNo.equals("")||nationality.equals("")) {
			System.out.println("Error, please fill out username or password");
			return 1;
		}
		
		return 0;
	}
	
	/** Logic method for detecting invalid gender input in student registration.
	 * 
	 * @param gender Gender of student to add.
	 * @throws IOException
	 */
	public static int checkValidGender(String gender) throws IOException {
  		
		if(!gender.equals("M") && !gender.equals("m") && !gender.equals("f") && !gender.equals("F")) {
			System.out.println("Please enter a valid gender, M or F");
			return 1;
		}
		
		return 0;
	}
	
	/** Logic method for hashing input passwords with SHA-1 algorithm.
	 * 
	 * @param input Unhashed password.
	 * @return String: Hashed password 
	 */
	public static String hashPassword(String input) {
		StringBuilder hash = new StringBuilder();

		try {
			MessageDigest sha = MessageDigest.getInstance("SHA-1");
			byte[] hashedBytes = sha.digest(input.getBytes());
			char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
					'a', 'b', 'c', 'd', 'e', 'f' };
			for (int idx = 0; idx < hashedBytes.length;idx++) {
				byte b = hashedBytes[idx];
				hash.append(digits[(b & 0xf0) >> 4]);
				hash.append(digits[b & 0x0f]);
			}
		} catch (NoSuchAlgorithmException e) {
			// handle error here.
		}

		return hash.toString();

		
	}

		
}



