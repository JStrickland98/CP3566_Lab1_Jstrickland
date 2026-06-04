May 29/26, 1:00pm
Generate code that will connect to H2 database using JDBC.
URL: jdbc:h2:./data/studentdb;MODE=MySQL;DATABASE_TO_LOWER=TRUE
Username: sa
Password: secret

May 31/26, 3:20pm
Please review my code and fix any syntax issues.

May 31/26, 3:50pm
Edit the code in ListStudents.java to print each student row using printf with specific column formatting.
id: width 3, right-aligned
name: width 16, left-aligned
program: width 4, left-aligned
gpa: width 5, 2 decimal places, right-aligned

June 02/26, 9:00pm
Please adjust lines 49 and 50 so that the output is formatted like this: Student #2 · Bob Mercer · program SE · GPA 3.45

June 02/26, 10:00pm
Update my code in AddStudent.java and add input validation for name, program, and gpa before database connection.
name: trimmed, 1-80 chars
program code: (regex ^[A-Z0-9]{2,12}$ )
gpa: 0.00-4.00

June 03/26, 12:30pm
go through ListStudents.java, FindStudent.java, AddStudent.java, and UpdateGpa.java and replace the unicode escape sequence \u00B7 with ·
