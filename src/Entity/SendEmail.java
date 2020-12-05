package Entity;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


/** Class for sending e-mail to a student who has successfully registered for a course from the waitlist.
 * 
 */
public class SendEmail {
 public static void main(String[] args) {
 // Add recipient

}
 
 /** Method for sending the notification email to the lucky student.
  * 
  * @param user Student to notify.
  */
 public static void sendMail(String user) {
	 String to = user+"@e.ntu.edu.sg";

	// Add sender
	 String from = "starstesterAdm@gmail.com";
	 final String username = "starstesterAdm@gmail.com";//your Gmail username 
	 final String password = "hunter12#";//your Gmail password

	String host = "smtp.gmail.com";

	Properties props = new Properties();
	 props.put("mail.smtp.auth", "true");
	 props.put("mail.smtp.starttls.enable", "true"); 
	 props.put("mail.smtp.host", host);
	 props.put("mail.smtp.port", "587");

	// Get the Session object
	 Session session = Session.getInstance(props,
	 new javax.mail.Authenticator() {
	 protected PasswordAuthentication getPasswordAuthentication() {
	 return new PasswordAuthentication(username, password);
	 }
	 });

	try {
	 // Create a default MimeMessage object
	 Message message = new MimeMessage(session);
	 
	 message.setFrom(new InternetAddress(from));
	 
	 message.setRecipients(Message.RecipientType.TO,
	 InternetAddress.parse(to));
	 
	 // Set Subject
	 message.setSubject("Course successfully registered");
	 
	 // Put the content of your message
	 message.setText("You have successfully registered for the course");

	// Send message
	 Transport.send(message);

	System.out.println("Sent message successfully....");

	} catch (MessagingException e) {
	 throw new RuntimeException(e);
	 }
	 }
}
