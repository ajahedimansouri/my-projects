package ws7.application.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class Guest extends BaseModel<Guest, Integer> {

	private int Id;
	private String title;
	private String firstName;
	private String lastName;
	private String address;
	private String phone;
	private String email;
	
	public Guest(int id, String title, String firstname, String lastName, String address, String phone, String email)
	{
		this.Id = id;
		this.title = title;
		this.firstName = firstname;
		this.lastName = lastName;
		this.address = address;
		this.phone = phone;
		this.email = email;
	}
	
	public Guest()
	{
		this(0,"","","","","","");
	}
	
	public void setId(int id) 
	{
		this.Id = id;
	}
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public void setFirstname(String fn)
	{
		this.firstName = fn;
	}
	
	public void setLastname(String ln)
	{
		this.lastName = ln;
	}
	
	public void setAddress(String adr)
	{
		this.address = adr;
	}
	
	public void setPhone(String phno)
	{
		this.phone = phno;
	}
	
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	public int getId() 
	{
		return this.Id;
	}
	
	public String getTitle()
	{
		return this.title;
	}
	
	public String getFirtsname()
	{
		return this.firstName;
	}
	
	public String getLastname()
	{
		return this.lastName;
	}
	
	public String getFullname()
	{
		return this.lastName + ", " + this.firstName;
	}
	
	public String getAddress()
	{
		return this.address;
	}
	
	public String getPhone()
	{
		return this.phone;
	}
	
	public String getEmail()
	{
		return this.email;
	}
	
	
	@Override
	public void add(Guest t) {
		try
		{
		  setException(null);
		  
		  PreparedStatement ps = dao.getConnection().prepareStatement("insert into Guest(title,firstName,lastName,address,phone,email) values (?,?,?,?,?,?)");
		  
		  ps.setString(1, t.title);
		  ps.setString(2, t.firstName);
		  ps.setString(3, t.lastName);
		  ps.setString(4, t.address);
		  ps.setString(5, t.phone);
		  ps.setString(6, t.email);
		  
		  ps.executeUpdate();
		  
		  ps.close();
		  
		} catch(Exception ex)
		{
			setException(ex);
		}
		
	}

	@Override
	public void delete(Guest t) {
		try
		{
		  setException(null);
		  
          PreparedStatement ps = dao.getConnection().prepareStatement("delete from Guest where Id = ?");
		  
		  ps.setInt(1, t.Id);
		  
		  ps.executeUpdate();
		  
		  ps.close();
		  
		} catch(Exception ex)
		{
			setException(ex);
		}
		
	}

	@Override
	public void update(Guest t) {
		try
		{
		  setException(null);
          PreparedStatement ps = dao.getConnection().prepareStatement("update Guest set title = ?, firstName = ?, lastName = ?, address = ?, phone = ?, email = ? where Id = ?");
		  
          ps.setString(1, t.title);
		  ps.setString(2, t.firstName);
		  ps.setString(3, t.lastName);
		  ps.setString(4, t.address);
		  ps.setString(5, t.phone);
		  ps.setString(6, t.email);
		  ps.setInt(7, t.Id);
		  
		  ps.executeUpdate();
		  
		  ps.close(); 
		  
		} catch(Exception ex)
		{
			setException(ex);
		}
		
	}

	@Override
	public Guest load(Integer id) {
		Guest rv = null;
		try
		{
		  setException(null);
		  
          PreparedStatement ps = dao.getConnection().prepareStatement("select Id,title,firstName,lastName,address,phone,email from Guest where Id = ?");
		  ps.setInt(1, id);
		  
		  ResultSet rs = ps.executeQuery();
		  
		  if(rs.next())
		  {
			  rv = new Guest(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));
		  }
		  
		  ps.close(); 
		} catch(Exception ex)
		{
			setException(ex);
		}
		return rv;
	}

	@Override
	public List<Guest> list() {
		List<Guest> rv = null;
		try
		{
		  setException(null);
		  
		  PreparedStatement ps = dao.getConnection().prepareStatement("select Id,title,firstName,lastName,address,phone,email from Guest");
		  
		  
		  ResultSet rs = ps.executeQuery();
		  
		  while(rs.next())
		  {
			 Guest r = new Guest(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));
			 if(rv == null)
				 rv = new ArrayList<Guest>();
			 
			 rv.add(r);
		  }
		  
		  ps.close(); 
		  
		} catch(Exception ex)
		{
			setException(ex);
		}
		return rv;
	}

}
