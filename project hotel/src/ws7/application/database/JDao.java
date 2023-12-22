package ws7.application.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDao 
{
	private String url;

	protected String getDatabaseUrl()
	{
		return String.format("jdbc:sqlite:%s%s%s",System.getProperty("user.dir"),File.separator,"hotelabc.db");
	}

	public JDao()
	{
		url = getDatabaseUrl();
		createDatabase();
	}

	public Connection getConnection() throws SQLException 
	{
		return DriverManager.getConnection(getDatabaseUrl());
	}
	
	public void createDatabase()
	{
		try (Connection conn = DriverManager.getConnection(url)) {
	        if (conn != null) {
	            DatabaseMetaData meta = conn.getMetaData();
	            meta.getDriverName();
	            
	            
	            Statement st = conn.createStatement();

	            String sql = "CREATE TABLE IF NOT EXISTS Room (id INTEGER PRIMARY KEY AUTOINCREMENT, type TEXT, rate DOUBLE)";
	            st.execute(sql);
	            
	            sql = "CREATE TABLE IF NOT EXISTS Login (id INTEGER PRIMARY KEY AUTOINCREMENT, password TEXT, isadmin BOOLEAN)";
	            st.execute(sql);
	            
	            sql = "CREATE TABLE IF NOT EXISTS Guest (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, firstname TEXT, lastname TEXT, address TEXT, phone TEXT, email TEXT)";
	            st.execute(sql);

	            sql = "CREATE TABLE IF NOT EXISTS Bill (id INTEGER PRIMARY KEY AUTOINCREMENT, amount DOUBLE, reservationId INTEGER, discount DOUBLE)";
	            st.execute(sql);

	            sql = "CREATE TABLE IF NOT EXISTS Reservation (id INTEGER PRIMARY KEY AUTOINCREMENT, bookDate DATE, checkIn DATE, checkOut DATE, roomIds TEXT, numberOfPerson INTEGER, guestId INTEGER)";
	            st.execute(sql);
	            
	            sql = "SELECT * FROM Login WHERE Id = 1";
	            if(!st.executeQuery(sql).next())
	            {
	            	sql = "INSERT INTO Login(Id,Password,IsAdmin) values (1,'admin-1',1) ";
	            	st.execute(sql);
	            }
	            
	            sql = "SELECT * FROM Login WHERE Id = 2";
	            if(!st.executeQuery(sql).next())
	            {
	            	sql = "INSERT INTO Login(Id,Password,IsAdmin) values (2,'admin-2',1) ";
	            	st.execute(sql);
	            }

	            
	            conn.close();
	        }
	        
	    } catch (SQLException e) 
		{
	    }
	}

	
}
