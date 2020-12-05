Hello, welcome to our MyStars university management system. To get started,
open up command prompt on your windows computer and navigate to through the
directories as follows:
oodp_mystars_grp3 > MyStars > src

Once you are in the src folder, run the following commands in sequence
to run our program:
------------------------------------------------------------------
1) javac -cp ".;./opencsv-2.4.jar;javax.mail.jar;activation-1.1.1.jar" Entity/*.java


2) javac -cp ".;./opencsv-2.4.jar;javax.mail.jar;activation-1.1.1.jar" Boundary/*.java


3) javac -cp ".;./opencsv-2.4.jar;javax.mail.jar;activation-1.1.1.jar" Controller/*.java


4) java -cp ".;./opencsv-2.4.jar;javax.mail.jar;activation-1.1.1.jar" Boundary/Login.java

------------------------------------------------------------------
When you are in the program, the system will prompt you to enter the username
and password. 

To log in to the admin account, use the following credentials:
username: admin
password: hunter2

To log in to the student account, you will first need to enter the admin
account and add the access date for student3 (this is the username) to 
your current date. After which, quite the program and run the above
java command lines again to get to the log in page. Once at the log in
page, use the following credentials to log in to the student account:
username: student3
password: hunter2


If you are interested to test out our email function, you will need to 
add a new student from the admin account and using your official NTU
username. For example, if your NTU email account is "BLIM059@e.ntu.edu.sg",
you will need to create a student with username of "BLIM059". After which,
the system will be able to send an email to your NTU email address.