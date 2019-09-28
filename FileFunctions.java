package net.sqlitedb;

import java.io.*;
import java.lang.*;
import java.sql.Connection; 
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import org.sqlite.util.StringUtils;

@SuppressWarnings("unused")
public class FileFunctions
{
	private static String JDBC_CONNECTION_URL = "jdbc:sqlite:csv.db";

	@SuppressWarnings("resource")
	public static void main( String [] args ) throws SecurityException, IOException
	{
		
		 	String csvFile = "/Users/MKarim/SQLiteDB/ms3Interview.csv";
	        BufferedReader br = null;
	        String line = "";
	        String csvSplitBy = ",";
	        boolean HeadRowExists = true;
	        int AcceptedNumberofColumns = 10;
	        int InvalidStats = 0;
	        String[] customer = null;
	      //Create List for holding objects
         List<Customer> custStats = new ArrayList<Customer>();
	        
	        try 
            {
				br = new BufferedReader(new FileReader(csvFile));
				
				           
	            if(HeadRowExists) 
	            {
	            	String HeadRow = br.readLine();
	         
	            	if(HeadRow==null || HeadRow.isEmpty()) 
	            	{
	            		throw new FileNotFoundException(
	        					"No columns defined in given CSV file." +
	        					"Please check the CSV file format.");
	            	}
	            }
	            
				 while ((line = br.readLine()) != null) 
				 {

		                // use comma as separator
		                customer = line.split(csvSplitBy);
		                
		                if(customer.length > 0 && customer.length == AcceptedNumberofColumns)
		                {
		                    //Save the details in an object
		                    Customer custom = new Customer(customer[0],
		                    		customer[1],customer[2],customer[3],
		                    		customer[4],customer[5],customer[6],
		                    		Boolean.parseBoolean(customer[7]),Boolean.parseBoolean(customer[8]),
		                    		customer[7]);
		                    custStats.add(custom);
		                }
		                else 
                        {
		                	
		                	InvalidStats++;
		                }   
				 }
			
			CSVtoDB(custStats);

		}
		
		// handle exceptions
		catch (FileNotFoundException fnfe)
		{
			System.out.println("file not found");
		}

		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}

		// stat results into log file
	    boolean append = true;
	    FileHandler handler = new FileHandler("results.log", append);
	    
	    Logger logger = Logger.getLogger("Following are the statistics :\n#"+
	            customer.length+" of records received.\n#"+
	            custStats.size()+" of records successful.\n#"+
	            InvalidStats+" of records failed.");

	}

	private static void CSVtoDB(List<Customer> custStats) 
	{
		// TODO Auto-generated method stub
		
		Connection connection = null;
		boolean tableExists = true;
		boolean truncateTable = true;
		
		try 
		{
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection(JDBC_CONNECTION_URL);
			
			if(tableExists != true) 
			{
				connection.createStatement().execute("create table customer(A, B, C, D, E, F, G, H, I, J)");
			}
			
			if(truncateTable == true) 
			{
				connection.createStatement().execute("delete from customer");
			}
				
			
			PreparedStatement stmt =
					connection.prepareStatement("insert into customer (A, B, C, D, E, F, G, H, I, J) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			
			for(Customer e : custStats)
            {
				
				stmt.setString(1, e.getA());
				stmt.setString(2, e.getB());
				stmt.setString(3, e.getC());
				stmt.setString(4, e.getD());
				stmt.setString(5, e.getE());
				stmt.setString(6, e.getF());
				stmt.setString(7, e.getG());
				stmt.setBoolean(8, e.getH());
				stmt.setBoolean(9, e.getI());
				stmt.setString(10, e.getJ());
				
				stmt.executeUpdate();
            }
			
			System.out.println("Result of SELECT\n");
			
			ResultSet rs = connection.createStatement().executeQuery("select * from customer");
			
			while(rs.next()) 
			{
				String A = rs.getString(1);
				String B = rs.getString(2);
				String C = rs.getString(3);
				String D = rs.getString(4);
				String E = rs.getString(5);
				String F = rs.getString(6);
				Float G = rs.getFloat(7);
				Boolean H = rs.getBoolean(8);
				Boolean I = rs.getBoolean(9);
				String J = rs.getString(10);
				
				System.out.println(A+"\t"+B+"\t"+C+"\t"+D+"\t"+E+"\t"
				+F+"\t"+G+"\t"+H+"\t"+I+"\t"+J);
			}
			
		} catch (SQLException | ClassNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}