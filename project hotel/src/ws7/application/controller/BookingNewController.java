package ws7.application.controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.util.Pair;
import ws7.application.helper.RoomHelper;
import ws7.application.model.Guest;
import ws7.application.model.Reservation;
import ws7.application.model.Room;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TableColumn;


public class BookingNewController extends Controller<BorderPane, BookingNewController, Room> implements Initializable 
{

	Reservation reservation;
	Guest       guest;
	
	@FXML
	private TableView<Room> roomTableView;
	@FXML
	private TextField noOfguestText;
	@FXML
	private DatePicker checkInDate;
	@FXML
	private DatePicker checkOutDate;
	@FXML
	private Button checkAvailableButton;
	@FXML
	private Button bookButton;
	@FXML
	private TextArea recommendationText;
	@FXML TextField totalRateText;
	
	
	double rate = 0;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		model = new Room();
		reservation = new Reservation();
		guest = new Guest();
		
		totalRateText.setText("$0");
		
		roomTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		// roomTableView.getSelectionModel().selectedIndexProperty()
		roomTableView.setOnMousePressed(e -> {
			if((roomTableView.getSelectionModel().getSelectedItems().size()) <= 0)
			{
				bookButton.setDisable(true);
				totalRateText.setText("$0.00");
			} else
			{
				bookButton.setDisable(false);
				
				rate = 0;
				ObservableList<Room> rs = getSelectedRooms();
				getSelectedRooms().forEach((t) -> { rate += t.getRate();
				 
				});
				
				 totalRateText.setText(String.format("$%.2f", rate));
			}
			
		});
		
		noOfguestText.textProperty().addListener(e -> {
			try
			{
				reservation.setNumberOfperson(Integer.parseInt(noOfguestText.getText()));
			} catch(Exception ex)
			{
				ex.printStackTrace();
			}
		});
		
		checkInDate.valueProperty().addListener((e) -> {
			try
			{
				reservation.setCheckIn(Date.from(checkInDate.getValue().atStartOfDay().toInstant(ZoneOffset.UTC)));
			} catch(Exception ex)
			{
				ex.printStackTrace();
			}
		});
		
		checkOutDate.valueProperty().addListener((e) -> {
			try
			{
				reservation.setCheckOut(Date.from(checkOutDate.getValue().atStartOfDay().toInstant(ZoneOffset.UTC)));
			} catch(Exception ex)
			{
				ex.printStackTrace();
			}
		});
		
	    ((TableColumn<Room,Integer>)roomTableView.getColumns().get(0)).setCellValueFactory(new PropertyValueFactory<Room, Integer>("id"));
	    ((TableColumn<Room,String>)roomTableView.getColumns().get(1)).setCellValueFactory(new PropertyValueFactory<Room, String>("type"));
	    ((TableColumn<Room,Double>)roomTableView.getColumns().get(2)).setCellValueFactory(new PropertyValueFactory<Room, Double>("rate"));
		
		

	}
	
	public ObservableList<Room> getSelectedRooms()
	{
		return roomTableView.getSelectionModel().getSelectedItems();
	}
	
	public Map<String,Integer> getRoomsFor()
	{
		Map<String,Integer> rv = new HashMap<String, Integer>();
		
		try
		{
		   int nog = Integer.parseInt(noOfguestText.getText());
		   
		   List<String> rooms =	RoomHelper.getNumberOfRoom(nog);
		   
		   if(rooms != null)
		   {
			   for(String r : rooms)
			   {
				   if(rv.containsKey(r))
					   rv.replace(r, rv.get(r) + 1);
				   else
					   rv.put(r,1);
			   }
		   }
		   
		} catch(Exception ex)
		{
			ex.printStackTrace();
			rv.clear();
		}
		
		return rv;
	}
	
	public void update()
	{
		try
		{
			Date checkin = Date.from(checkInDate.getValue().atStartOfDay().toInstant(ZoneOffset.UTC));
			Date checkout = Date.from(checkOutDate.getValue().atStartOfDay().toInstant(ZoneOffset.UTC)); 
			
		    List<Room> r = model.listOfFreeRoom(checkin, checkout);
		    
		    roomTableView.setItems(FXCollections.observableArrayList(r));
		    roomTableView.refresh();
		    
		    String nog = noOfguestText.getText();
		    
		} catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public TableView<Room> getRoomTableView() {
		return roomTableView;
	}


	public TextField getNoOfguestText() {
		return noOfguestText;
	}

	public DatePicker getCheckInDate() {
		return checkInDate;
	}


	public DatePicker getCheckOutDate() {
		return checkOutDate;
	}


	public Button getCheckAvailableButton() {
		return checkAvailableButton;
	}


	public Button getBookButton() {
		return bookButton;
	}


	public TextArea getRecommendationText() {
		return recommendationText;
	}
	
	public Pair<Boolean, String> validateModel()
	{
		String rv = "";
		
		try
		{
		   if(reservation.getCheckIn() == null)
			   rv = "Invalid checkin date !\n";
		   
		   if(reservation.getCheckOut() == null)
			   rv += "Invalid checkout date !\n";
		   
		   if(reservation.getNumberOfperson() <= 0)
			   rv += "Invalid number of guest !\n";
		   
		   if(!RoomHelper.validateRooms(getSelectedRooms(), reservation.getNumberOfperson()))
			   rv += "Invalid number of rooms is selected !\n";

		} catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		return new Pair<Boolean, String>(rv.length() == 0, rv);
	}
	
	public int bookRoom(int guestId)
	{
		int rv = -1;
		
		if(validateModel().getKey())
		{
			try
			{
			   List<Room> rooms = getSelectedRooms();
			   StringBuilder rs = new StringBuilder();
			   for(Room r : rooms)
				   rs.append("["+ r.getId() +"]");
			   Date bd = new Date(System.currentTimeMillis());
			   reservation.setBookDate(bd);
			   reservation.setGuestId(guestId);
			   reservation.setRoomIds(rs.toString());
			   
			   reservation.add(reservation);
			   
			   if(reservation.getException() == null)
			   {
				   Optional<Reservation> ress = reservation.list().stream().filter(r -> r.getBookDate().compareTo(bd) == 0 && 
						                                                                r.getRoomIds().equals(rs.toString()) && 
						                                                                r.getGuestId() == guestId).findFirst();
				   
				   if(!ress.isEmpty())
					   rv = ress.get().getId();
			   }
			   
			   
			} catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		
		return rv;
	}

}
