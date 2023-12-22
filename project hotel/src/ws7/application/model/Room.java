package ws7.application.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import ws7.application.database.JDao;

public class Room extends BaseModel<Room,Integer> {
	
	
	
	public static final class RoomType
	{
		public final static String SINGLE = "Single";
		public final static String DOUBLE = "Double";
		public final static String DELUX = "Delux";
		public final static String PENT = "Pent";
	}
	
	private int Id;
	private String type;
	private double rate;
	
	public Room(int id, String type, double rate)
	{
		this.Id = id;
		this.type = type;
		this.rate = rate;
		
		this.dao = new JDao();
	}
	
	public Room()
	{
		this(0,"",0);
	}
	
	public IntegerProperty idProperty()
	{
		return new SimpleIntegerProperty(this.getId());
	}
	
	public StringProperty typeProperty()
	{
		return new SimpleStringProperty(this.getType());
	}
	
	public DoubleProperty rateProperty()
	{
		return new SimpleDoubleProperty(this.getRate());
	}
	
	
	
	public void setId(int id)
	{
		this.Id = id;
	}
	
	public void setType(String type)
	{
		this.type = type;
	}
	
	public void setRate(double rate)
	{
		this.rate = rate;
	}
	
	public int getId()
	{
		return this.Id;
	}
	
	public String getType()
	{
		return this.type;
	}
	
	public double getRate()
	{
		return this.rate;
	}
	
	public boolean isSingleRoom()
	{
		return this.type == RoomType.SINGLE;
	}
	
	public boolean isDoubleRoom()
	{
		return this.type == RoomType.DOUBLE;
	}
	
	public boolean isDeluxRoom()
	{
		return this.type == RoomType.DELUX;
	}
	
	public boolean isPentRoom()
	{
		return this.type == RoomType.PENT;
	}
	
	public double getRateFor(int numberOfRoom)
	{
		return this.rate * numberOfRoom;
	}

	@Override
	public void add(Room t) {
		try
		{
		  setException(null);
		  
		  PreparedStatement ps = dao.getConnection().prepareStatement("insert into Room(Id,Type,Rate) values (?,?,?)");
		  
		  ps.setInt(1, t.Id);
		  ps.setString(2, t.type);
		  ps.setDouble(3, t.rate);
		  
		  ps.executeUpdate();
		  
		  ps.close();
		  
		} catch(Exception ex)
		{
			setException(ex);
		}
		
	}

	@Override
	public void delete(Room t) {
		try
		{
		  setException(null);
		  
          PreparedStatement ps = dao.getConnection().prepareStatement("delete from  Room where Id = ?");
		  
		  ps.setInt(1, t.Id);
		  
		  ps.executeUpdate();
		  
		  ps.close();
		  
		} catch(Exception ex)
		{
			setException(ex);
		}
		
	}

	@Override
	public void update(Room t) {
		try
		{
		  setException(null);
          PreparedStatement ps = dao.getConnection().prepareStatement("update Room set Type = ?, Rate = ? where Id = ?");
		  
		  
		  ps.setString(1, t.type);
		  ps.setDouble(2, t.rate);
		  ps.setInt(3, t.Id);
		  
		  ps.executeUpdate();
		  
		  ps.close(); 
		  
		} catch(Exception ex)
		{
			setException(ex);
		}
		
	}

	@Override
	public Room load(Integer id) {
		Room rv = null;
		try
		{
		  setException(null);
		  
          PreparedStatement ps = dao.getConnection().prepareStatement("select Id,Type,Rate from Room where Id = ?");
		  ps.setInt(1, id);
		  
		  ResultSet rs = ps.executeQuery();
		  
		  if(rs.next())
		  {
			  rv = new Room(rs.getInt(1), rs.getString(2), rs.getDouble(3));
		  }
		  
		  ps.close(); 
		} catch(Exception ex)
		{
			setException(ex);
		}
		return rv;
	}

	@Override
	public List<Room> list() {
		List<Room> rv = null;
		try
		{
		  setException(null);
		  
		  PreparedStatement ps = dao.getConnection().prepareStatement("select Id,Type,Rate from Room");
		  
		  
		  ResultSet rs = ps.executeQuery();
		  
		  while(rs.next())
		  {
			 Room r = new Room(rs.getInt(1), rs.getString(2), rs.getDouble(3));
			 
			 if(rv == null)
				 rv = new ArrayList<Room>();
			 
			 rv.add(r);
		  }
		  
		  ps.close(); 
		  
		} catch(Exception ex)
		{
			setException(ex);
		}
		return rv;
	}
	
	
	public List<Room> listOfFreeRoom(Date checkIn, Date checkOut) {
		List<Room> rv = new ArrayList<Room>();
		
		List<Room> tr = list();
		Reservation rs = new Reservation();
		List<Reservation> lrs = rs.list();
		
		for(Room o : tr)
		{
		  boolean rem = false;
		  
		  if(lrs != null)
		  {
			  for(Reservation r : lrs)
			  {
				  if(r.getRoomIds().contains("["+o.Id+"]") && 
						  (checkIn.before(r.getCheckIn()) || checkIn.after(r.getCheckOut()) ) &&
								  (checkOut.before(r.getCheckIn()) || checkOut.after(r.getCheckOut())) )
				  {
					  rem = true;
					  break;
				  }
			  }
		  }
		  
		  if(!rem)
			  rv.add(o);
		}
		
		return rv;
	}
	
	public List<Room> listOfFreeFormNow() {
		List<Room> rv = new ArrayList<Room>();
		
		List<Room> tr = list();
		Reservation rs = new Reservation();
		List<Reservation> lrs = rs.list();
		Date now = new Date(System.currentTimeMillis());
		
		for(Room o : tr)
		{
		  boolean rem = false;
		  
		  if(lrs != null)
		  {
			  for(Reservation r : lrs)
			  {
				  if(r.getRoomIds().contains("["+o.Id+"]") && 
						 now.after(r.getCheckIn()) && now.before(r.getCheckOut()) )
				  {
					  rem = true;
					  break;
				  }
			  }
		  }
		  
		  if(!rem)
			  rv.add(o);
		}
		
		return rv;
	}

	
	public boolean isRoomFree(Date from, Date to)
	{
		return isRoomFree(from, to, this.getId());
	}
	
	public boolean isRoomFree(Date from, Date to, int roomId)
	{
		boolean rv = false;
		
		try
		{
			
		    setException(exception);
		    
		    PreparedStatement ps = dao.getConnection().prepareStatement("select * from Reservation where roomIds like '%[?]%' and (checkIn between ? and ? or checkOut between ? and ?)");
		    
		    ps.setInt(1, roomId);
		    ps.setDate(2, (java.sql.Date) from);
		    ps.setDate(3, (java.sql.Date) to);
		    ps.setDate(4, (java.sql.Date) from);
		    ps.setDate(5, (java.sql.Date) to);
		    
		    ResultSet rs = ps.executeQuery();
		    
		    if(rs.next())
		    	rv = false;
		    else
		    	rv = true;
		  
		    ps.close();
			
		} catch(Exception ex)
		{
			setException(ex);
		}
		
		return rv;
	}
	
	public double getRateOfRooms(String roomIds)
	{
		double rv = 0;
		
		try
		{
		  String[] rms = roomIds.replace("[", "").split("]");
		  
		  for(String r : rms)
		  {
			  if(r != null && r.length() > 0)
			  {
				  int rid = Integer.parseInt(r);
				  
				  Room rm = load(rid);
				  
				  if(rm != null)
					  rv += rm.getRate();
			  }
		  }
		  
		} catch(Exception ex)
		{
			ex.printStackTrace();
			rv = 0;
		}
		
		
		return rv;
	}
	
	public String getTypeOfRooms(String roomIds)
	{
		String rv = "";
		
		try
		{
		  String[] rms = roomIds.replace("[", "").split("]");
		  
		  for(String r : rms)
		  {
			  if(r != null && r.length() > 0)
			  {
				  int rid = Integer.parseInt(r);
				  
				  Room rm = load(rid);
				  
				  if(rm != null)
					  rv += "["+ rm.getType()+"]";
			  }
		  }
		  
		} catch(Exception ex)
		{
			ex.printStackTrace();
			rv = "";
		}
		
		return rv;
	}
	
}
