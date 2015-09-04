# Open_Issue-Count

Question-Create a repository on GitHub and write a program in any programming language that will do the following 

Input : User can input a link to any public GitHub repository 

Output : Your UI should display a table with the following information-
- Total number of open issues 
- Number of open issues that were opened in the last 24 hours 
- Number of open issues that were opened more than 24 hours ago but less than 7 days ago 
- Number of open issues that were opened more than 7 days ago 

Project Description-

Implemented by using gituhb, org.kohsuke.git and other deoendent API. 

On user input of any github repository issue URL in JTextField and on click of JButton-
Program checks is it correct github URL?

If yes, then we parse URL to get particular repositroy name and get connected to Github anonymously 
and fetch all open issue details using Github API. 
 
Further by picking created date of open issue & current date, we are counting issues occured during particular span of time 
and prints the same in Jtable. 

Processing time of open issue & counting is showing through cursor moving n UI (frame). 

Thanks for reading. 
Please share your thoughts on the same. 
