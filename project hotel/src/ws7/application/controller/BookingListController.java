package ws7.application.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import ws7.application.model.Reservation;
import ws7.application.model.ReservationView;
import ws7.application.model.Room;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;

public class BookingListController extends Controller<BorderPane, BookingListController, ReservationView> 
	implements Initializable
{

	@FXML TextField currentBookingsNoText;
	@FXML TableView<ReservationView> currentBookingsTableView;
	@FXML Button returnButton;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		model = new ReservationView();
		
	    ((TableColumn<ReservationView,Integer>)getCurrentBookingsTableView().getColumns().get(0)).setCellValueFactory(new PropertyValueFactory<ReservationView, Integer>("bookingId"));
        ((TableColumn<ReservationView,String>)getCurrentBookingsTableView().getColumns().get(1)).setCellValueFactory(new PropertyValueFactory<ReservationView, String>("customerName"));
	    ((TableColumn<ReservationView,String>)getCurrentBookingsTableView().getColumns().get(2)).setCellValueFactory(new PropertyValueFactory<ReservationView, String>("roomType"));
	    ((TableColumn<ReservationView,String>)getCurrentBookingsTableView().getColumns().get(3)).setCellValueFactory(new PropertyValueFactory<ReservationView, String>("roomIds"));
	    ((TableColumn<ReservationView,Integer>)getCurrentBookingsTableView().getColumns().get(4)).setCellValueFactory(new PropertyValueFactory<ReservationView, Integer>("noOfDay"));
		    
	    this.update();
	}

		
	public void update()
	{
		
		List<ReservationView> rv = model.loadView();
	    
	    if(rv != null)
	    {
	    	getCurrentBookingsTableView().setItems(FXCollections.observableList(rv));
	    	
	    	
	    	getCurrentBookingsNoText().setText(rv.size()+"");
	    } else
	    {
	    	getCurrentBookingsNoText().setText("0");
	    }
	    
	    getCurrentBookingsTableView().refresh();
	}


	public TextField getCurrentBookingsNoText() {
		return currentBookingsNoText;
	}


	public TableView<ReservationView> getCurrentBookingsTableView() {
		return currentBookingsTableView;
	}


	public Button getReturnButton() {
		return returnButton;
	}
	

}
