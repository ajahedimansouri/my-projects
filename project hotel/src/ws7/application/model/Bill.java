package ws7.application.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Bill extends BaseModel<Bill, Integer> {

	private int Id;
	private double amount;
	private int reservationId;
	private double discount;
	
	public Bill(int id, double amount, int reservationId, double discount)
	{
		this.setId(id);
		this.setAmount(amount);
		this.setReservationId(reservationId);
		this.setDiscount(discount);
	}
	
	public Bill()
	{
		this(0,0,0,0);
	}
	
	public void setId(int id)
	{
		this.Id = id;
	}
	
	public int getId()
	{
		return this.Id;
	}
	
	
	public void setAmount(double amount)
	{
		this.amount = amount;
	}
	
	public double getAmount()
	{
		return this.amount;
	}
	
	public void setReservationId(int rid)
	{
		this.reservationId = rid;
	}
	
	public int getReservationId()
	{
		return this.reservationId;
	}
	
	public void setDiscount(double dis)
	{
		this.discount = dis;
	}
	
	public double getDiscount()
	{
		return this.discount;
	}
	
	@Override
	public void add(Bill t) {
		try
		{
		  setException(null);
		  
		  PreparedStatement ps = dao.getConnection().prepareStatement("insert into Bill(amount,reservationId,discount) values (?,?,?)");
		  
		 
		  ps.setDouble(1, t.amount);
		  ps.setInt(2, t.reservationId);
		  ps.setDouble(3, t.discount);
		 
		  
		  ps.executeUpdate();
		  
		  ps.close();
		  
		} catch(Exception ex)
		{
			setException(ex);
		}
		
	}

	@Override
	public void delete(Bill t) {
		try
		{
		  setException(null);
		  
          PreparedStatement ps = dao.getConnection().prepareStatement("delete from  Bill where Id = ?");
		  
		  ps.setInt(1, t.Id);
		  
		  ps.executeUpdate();
		  
		  ps.close();
		  
		} catch(Exception ex)
		{
			setException(ex);
		}
		
	}

	@Override
	public void update(Bill t) {
		try
		{
		  setException(null);
          PreparedStatement ps = dao.getConnection().prepareStatement("update Bill set amount = ?, reservationId = ?, discount = ? where Id = ?");
		  
		  
		  ps.setDouble(1, t.amount);
		  ps.setInt(2, t.reservationId);
		  ps.setDouble(3, t.discount);
		  ps.setInt(4, t.Id);
		  
		  ps.executeUpdate();
		  
		  ps.close(); 
		  
		} catch(Exception ex)
		{
			setException(ex);
		}
		
	}

	@Override
	public Bill load(Integer id) {
		Bill rv = null;
		try
		{
		  setException(null);
		  
          PreparedStatement ps = dao.getConnection().prepareStatement("select Id,Amount,ReservationId,Discount from Bill where Id = ?");
		  ps.setInt(1, id);
		  
		  ResultSet rs = ps.executeQuery();
		  
		  if(rs.next())
		  {
			  rv = new Bill(rs.getInt(1),  rs.getDouble(2), rs.getInt(3), rs.getDouble(4));
		  }
		  
		  ps.close(); 
		} catch(Exception ex)
		{
			setException(ex);
		}
		return rv;
	}

	@Override
	public List<Bill> list() {
		List<Bill> rv = null;
		try
		{
		  setException(null);
		  
		  PreparedStatement ps = dao.getConnection().prepareStatement("select Id,Amount,ReservationId,Discount from Bill");
		  
		  
		  ResultSet rs = ps.executeQuery();
		  
		  while(rs.next())
		  {
			  Bill r = new Bill(rs.getInt(1),  rs.getDouble(2), rs.getInt(3), rs.getDouble(4));
			 
			 if(rv == null)
				 rv = new ArrayList<Bill>();
			 
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
