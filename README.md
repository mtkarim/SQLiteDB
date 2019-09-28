# SQLiteDB

Java Application that consumes the data, parses the data, and inserts the records into SQLite Database. 

Purpose (MS3 Programming Challenge):

With given data (a sample CSV file is included), you are informed to quickly create a Java Application that consumes the given CSV file, parses its data, inserts the data into your SQLite database. 

1. The table in the given CSV file has 10 columns: A, B, C, D, E, F, G, H, I, J (column headers in CSV file). 
2. Records need to be verified to contain the right number of data elements to match the columns. 
  a. Records that don't match must be written to a separate csv file.
3. At the end, write your stats results into a log file: a. # of records received.  b. # of records successful. c. # of records failed.
4. Application should be re-runnable, so it should give the same input everytime it runs. 

Guidelines:
1. Use of online resources is allowed.
2. Use of existing tools like Maven and open source libraries is encouraged.


Steps to run:
1. Download the SQLite-JDBC jar file (link: https://bitbucket.org/xerial/sqlite-jdbc/downloads/) and append it to your classpath.
To append: 
  a. Right click on your project. 
  b. Go to 'Build Path'. 
  c. Go to 'Configure Build Path'. 
  d. Click the 'Libraries' tab. 
  e. Click 'Add external JARs'. 
  f. Finally select the downloaded JAR file. 
2. From here, you create your files in order to create the application (I used eclipse, and yes free to use my resources).

Overview of my approach: 
1. My FileFunctions.java created the database, created the path to the given CSV file, read the CSV file, created the lists, distinguished the invalid stats, loaded the values to the database, and at the end created a log file with all the results. 
2. I also had the function to insert all the csv data to the database in this file. This is where if there was no table, it would create one based on the requirements of having 10 columns. If there is a truncated table, then it will be deleted. Prepared Statements were used here to be more efficient by parametizing the data that needs to be accessed. Prepared statment queries run much faster than normal queries.
3. The Customer.java file is a simple class that defines the data in the given csv file. This way when we are reading it, parsing it, and about to insert it, the Customer file will have the definitions that is needed by the FileFunctions file to insert data into the database. 
