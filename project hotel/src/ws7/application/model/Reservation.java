package ws7.application.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Reservation extends BaseModel<Reservation, Integer> {

	private int Id;
	private Date bookDate;
	private Date checkIn;
	private Date checkOut;
	private String roomIds;
	private int    numberOfperson;
	private int    guestId;
	
	public Reservation(int id, Date bookDate, Date checkIn, Date checkout, String roomIds, int numberOfPerson, int guestId)
	{
		this.setId(id);
		this.setBookDate(bookDate);
		this.setCheckIn(checkIn);
		this.setCheckOut(checkout);
		this.setNumberOfperson(numberOfPerson);
		this.setGuestId(guestId);
		this.setRoomIds(roomIds);
	}
	
	public Reservation()
	{
		this(0,null,null,null,null,0,0);
	}
	
	
	
	@Override
	public void add(Reservation t) {
		try
		{
		  setException(null);
		  
		  PreparedStatement ps = dao.getConnection().prepareStatement("insert into Reservation(bookDate, checkIn, checkOut, roomIds, numberOfPerson, guestId) values (?,?,?,?,?,?)");
		 
		  ps.setDate(1, new java.sql.Date( t.getBookDate().getTime()));
		  ps.setDate(2, new java.sql.Date( t.getCheckIn().getTime()));
		  ps.setDate(3, new java.sql.Date( t.getCheckOut().getTime()));
		  ps.setString(4, t.getRoomIds());
		  ps.setInt(5, t.getNumberOfperson());
		  ps.setInt(6, t.getGuestId());
		  
		  ps.executeUpdate();
		  
		  ps.close();
		  
		} catch(Exception ex)
		{
			setException(ex);
		}
		
	}

	@Override
	public void delete(Reservation t) {
		try
		{
		  setException(null);
		  
          PreparedStatement ps = dao.getConnection().prepareStatement("delete from  Reservation where Id = ?");
		  
		  ps.setInt(1, t.getId());
		  
		  ps.executeUpdate();
		  
		  ps.close();
		  
		} catch(Exception ex)
		{
			setException(ex);
		}
		
	}

	@Override
	public void update(Reservation t) {
		try
		{
		  setException(null);
          PreparedStatement ps = dao.getConnection().prepareStatement("update Reservation set bookDate = ?, checkIn = ?, checkOut = ?, roomIds = ?, numberOfPerson = ?, guestId = ? where Id = ?");
		  
		  ps.setDate(1, (java.sql.Date) t.getBookDate());
		  ps.setDate(2, (java.sql.Date) t.getCheckIn());
		  ps.setDate(3, (java.sql.Date) t.getCheckOut());
		  ps.setString(4, t.getRoomIds());
		  ps.setInt(5, t.getNumberOfperson());
		  ps.setInt(6, t.getGuestId());
		  
		  ps.setInt(7, t.getId());
		  
		  ps.executeUpdate();
		  
		  ps.close(); 
		  
		} catch(Exception ex)
		{
			setException(ex);
		}
		
	}

	@Override
	public Reservation load(Integer id) {
		Reservation rv = null;
		try
		{
		  setException(null);
		  
          PreparedStatement ps = dao.getConnection().prepareStatement("select Id,bookDate, checkIn, checkOut, roomIds, numberOfPerson, guestId from Reservation where Id = ?");
		  ps.setInt(1, id);
		  
		  ResultSet rs = ps.executeQuery();
		  
		  if(rs.next())
		  {
			  rv = new Reservation(rs.getInt(1), rs.getDate(2), rs.getDate(3), rs.getDate(4), rs.getString(5), rs.getInt(6), rs.getInt(7));
		  }
		  
		  ps.close(); 
		} catch(Exception ex)
		{
			setException(ex);
		}
		return rv;
	}

	@Override
	public List<Reservation> list() {
		List<Reservation> rv = null;
		try
		{
		  setException(null);
		  
		  PreparedStatement ps = dao.getConnection().prepareStatement("select Id,bookDate, checkIn, checkOut, roomIds, numberOfPerson, guestId from Reservation");
		  
		  
		  ResultSet rs = ps.executeQuery();
		  
		  while(rs.next())
		  {
			  Reservation r = new Reservation(rs.getInt(1), rs.getDate(2), rs.getDate(3), rs.getDate(4), rs.getString(5), rs.getInt(6), rs.getInt(7));
			 
			 if(rv == null)
				 rv = new ArrayList<Reservation>();
			 
			 rv.add(r);
		  }
		  
		  ps.close(); 
		  
		} catch(Exception ex)
		{
			setException(ex);
		}
		return rv;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public Date getBookDate() {
		return bookDate;
	}

	public void setBookDate(Date bookDate) {
		this.bookDate = bookDate;
	}

	public Date getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(Date checkIn) {
		this.checkIn = checkIn;
	}

	public Date getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(Date checkOut) {
		this.checkOut = checkOut;
	}

	public String getRoomIds() {
		return roomIds;
	}

	public void setRoomIds(String roomIds) {
		this.roomIds = roomIds;
	}

	public int getNumberOfperson() {
		return numberOfperson;
	}

	public void setNumberOfperson(int numberOfperson) {
		this.numberOfperson = numberOfperson;
	}

	public int getGuestId() {
		return guestId;
	}

	public void setGuestId(int guestId) {
		this.guestId = guestId;
	}

}
