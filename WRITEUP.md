Question 1
The query was built by concatenating the user's input and injecting the statement directly into the SQL. The final query looked something like ‘SELECT id, name, program, gpa FROM student WHERE name LIKE ‘%%’ OR ‘1’=’1%’ ’. Since ‘1’=’1’ is always true, the WHERE clause matched every row and all rows were returned.

Question 2
If we ignore the returned value in task 4, then it is possible that our program would state that a student’s GPA was updated even if there were no matching student id. Since rows = 0 is not an error, without checking the returned value our program would wrongly print that the GPA was updated successfully and it would not appear faulty while testing manually.

Question 3
The id was assigned by the database when the INSERT statement was executed, and our Java code got the number back by requesting the generated keys and reading the values using getGeneratedKeys().

Question 4
I used very specific wording for all of my prompts when using Copilot, which limited the ambiguity in Copilot’s responses. However, there were still some issues. In my first prompt, it did generate usable code, however the output formatting was completely off. I had to give it precise instructions and examples of what I wanted, otherwise I would have lost marks. Additionally, it gave me suggestions with escaped unicode characters, which was completely unnecessary. I had to ask it to remove them throughout all of my files and replace it with the correct character. Several times it would try to finish my code, which was convenient, but it would often give me incorrect values despite writing the correct values several times myself beforehand, which would have cost me marks. (setQueryTimeout() for example.)
