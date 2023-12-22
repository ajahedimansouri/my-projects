package ws7.application.helper;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import ws7.application.model.Room;
import ws7.application.model.Room.RoomType;

public class RoomHelper {

	public static List<String> getNumberOfRoom(int numberOfAdults)
	{
		List<String> rv = new ArrayList<String>();
		int remain = numberOfAdults;
		
	    while(remain > 0)
	    {
	    	if(remain <= 2)
	    	{
	    		rv.add(Room.RoomType.SINGLE);
	    		remain = 0;
	    	} else
	    	if(remain <= 4)
	    	{
	    		rv.add(Room.RoomType.DOUBLE);
	    		remain = 0;
	    	} else
	    	if(remain > 4)
	    	{
	    		rv.add(Room.RoomType.DOUBLE);
	    		remain -= 4;
	    	} else
	    	if(remain > 2)
	    	{
	    		rv.add(RoomType.SINGLE);
	    		remain -= 2;
	    	}
	    }
	    
	    return rv;
	}
	
	
	public static boolean validateRooms(ObservableList<Room> rooms, int guest)
	{
		boolean rv = false;
		
		try
		{
			int c = guest;
			
			for(Room rm : rooms)
			{
				if(rm.getType().equals(RoomType.SINGLE))
					c -= 2;
				
				if(rm.getType().equals(RoomType.DOUBLE))
					c -= 4;
				
				if(rm.getType().equals(RoomType.DELUX))
					c -= 8;
				
				if(rm.getType().equals(RoomType.PENT))
					c -= 16;
				
				if(c <= 0) break;
			}
			
			rv = c <= 0;
		} catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		return rv;
	}
	
}
