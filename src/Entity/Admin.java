package Entity;
import java.security.NoSuchAlgorithmException;


public class Admin extends User {
	

	private String username; 
	private String password;  
	public Admin(String username, String password) throws NoSuchAlgorithmException {
		super(username, password, ADMIN);
	}
}
	
