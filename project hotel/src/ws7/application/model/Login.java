package ws7.application.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Login extends BaseModel<Login, Integer> {

	private int Id;
	private String password;
	private boolean isAdmin;
	
	public Login(int id, String password, boolean admin)
	{
		this.Id = id;
		this.password = password;
		this.isAdmin = admin;
	}
	
	
	public Login()
	{
		this(0,"",false);
	}
	
	
	public void setId(int id)
	{
		this.Id = id;
	}
	
	
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public void setAdmin(boolean admin)
	{
		this.isAdmin = admin;
	}
	
	public int getId()
	{
		return this.Id;
	}
	
	public String getPassword()
	{
		return this.password;
	}
	
	public boolean getIsAdmin()
	{
		return this.isAdmin;
	}
	
	@Override
	public void add(Login t) {
		try
		{
		  setException(null);
		  
		  PreparedStatement ps = dao.getConnection().prepareStatement("insert into Login(password,isadmin) values (?,?)");
		  
		 
		  ps.setString(1, t.password);
		  ps.setBoolean(2, t.isAdmin);
		  
		  ps.executeUpdate();
		  
		  ps.close();
		  
		} catch(Exception ex)
		{
			setException(ex);
		}
		
	}

	@Override
	public void delete(Login t) {
		try
		{
		  setException(null);
		  
          PreparedStatement ps = dao.getConnection().prepareStatement("delete from  Login where Id = ?");
		  
		  ps.setInt(1, t.Id);
		  
		  ps.executeUpdate();
		  
		  ps.close();
		  
		} catch(Exception ex)
		{
			setException(ex);
		}
		
	}

	@Override
	public void update(Login t) {
		try
		{
		  setException(null);
          PreparedStatement ps = dao.getConnection().prepareStatement("update Login set password = ?, isadmin = ? where Id = ?");
		  
		  
		  ps.setString(1, t.password);
		  ps.setBoolean(2, t.isAdmin);
		  ps.setInt(3, t.Id);
		  
		  ps.executeUpdate();
		  
		  ps.close(); 
		  
		} catch(Exception ex)
		{
			setException(ex);
		}
		
	}

	@Override
	public Login load(Integer id) {
		Login rv = null;
		try
		{
		  setException(null);
		  
          PreparedStatement ps = dao.getConnection().prepareStatement("select Id,Password,IsAdmin from Login where Id = ?");
		  ps.setInt(1, id);
		  
		  ResultSet rs = ps.executeQuery();
		  
		  if(rs.next())
		  {
			  rv = new Login(rs.getInt(1), rs.getString(2), rs.getBoolean(3));
		  }
		  
		  ps.close(); 
		} catch(Exception ex)
		{
			setException(ex);
		}
		return rv;
	}

	@Override
	public List<Login> list() {
		List<Login> rv = null;
		try
		{
		  setException(null);
		  
		  PreparedStatement ps = dao.getConnection().prepareStatement("select Id,Password,IsAdmin from Login");
		  
		  
		  ResultSet rs = ps.executeQuery();
		  
		  while(rs.next())
		  {
			  Login r = new Login(rs.getInt(1), rs.getString(2), rs.getBoolean(3));
			 
			 if(rv == null)
				 rv = new ArrayList<Login>();
			 
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
