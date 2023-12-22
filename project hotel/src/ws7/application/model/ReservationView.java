package ws7.application.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Duration;

public class ReservationView extends Reservation
{
	private String roomType;
	private String customerName;
	private int    noOfDay;
	
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public int getNoOfDay() {
		return noOfDay;
	}
	public void setNoOfDay(int noOfDay) {
		this.noOfDay = noOfDay;
	}
	
	public IntegerProperty bookingIdProperty()
	{
		return new SimpleIntegerProperty(this.getId());
	}
	
	public StringProperty customerNameProperty()
	{
		return new SimpleStringProperty(getCustomerName());
	}
	
	public StringProperty roomTypeProperty()
	{
		return new SimpleStringProperty(roomType);
	}
	
	public StringProperty roomIdsProperty()
	{
		return new SimpleStringProperty(getRoomIds());
	}
	
	
	public IntegerProperty noOfDayProperty()
	{
		return new SimpleIntegerProperty(noOfDay);
	}
	
	public ReservationView(Reservation rv)
	{
		super(rv.getId(), rv.getBookDate(), rv.getCheckIn(), rv.getCheckOut(), rv.getRoomIds(), rv.getNumberOfperson(), rv.getGuestId());
	}
	
	public ReservationView()
	{
		super();
	}
	
	public List<ReservationView> loadView()
	{
	    List<ReservationView> rv = null;
	    Guest guest = new Guest();
	    Room room = new Room();
	    
	    try
	    {
	    	List<Reservation> res = super.list();
	    	
	    	for(Reservation rs : res)
	    	{
	    		
	    	       Guest g = guest.load(rs.getGuestId());
	    	       
	    	       ReservationView rev = new ReservationView(rs);
	    	       
	    	       if(g != null)
	    	       {
	    	    	  rev.setCustomerName(g.getLastname() + ", " + g.getFirtsname());
	    	    	  
	    	    	  String[] rms = rs.getRoomIds().replace("[", "").split("]");
	    	    	  StringBuilder sb = new StringBuilder();
	    	    	  
	    	    	  for(String rid : rms)
	    	    	  {
	    	    		  if(rid != null && rid.length() > 0)
	    	    		  {
	    	    			  sb.append("["+room.load(Integer.parseInt(rid)).getType() + "]");
	    	    		  }
	    	    	  }
	    	    	  
	    	    	  rev.setRoomType(sb.toString());
	    	    	  
	    	    	  int diffInDays = (int)( (rs.getCheckOut().getTime() - rs.getCheckIn().getTime()) 
	    	                  / (1000 * 60 * 60 * 24) );
	    	    	  
	    	    	  rev.setNoOfDay(diffInDays);
	    	    	  
	    	    	  if(rv == null)
	    	    		  rv = new ArrayList<ReservationView>();
	    	    	  
	    	    	  rv.add(rev);
	    	       }
	    	       
	    	}
	    	
	    } catch(Exception ex)
	    {
	    	ex.printStackTrace();
	    	
	    	rv.clear();
	    }
	    
	    return rv;
	}
	
	

}
