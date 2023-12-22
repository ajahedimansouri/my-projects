package ws7.application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import ws7.application.model.Bill;
import ws7.application.model.Guest;
import ws7.application.model.Reservation;
import ws7.application.model.ReservationView;
import ws7.application.model.Room;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;

public class BillingController extends Controller<BorderPane, BillingController, Bill> 
		implements Initializable
{

	@FXML TextField bookingIdText;
	@FXML Button returnButton;
	@FXML TextField geustNameText;
	@FXML TextField noOfRoomsText;
	@FXML TextField typeOfroomsText;
	@FXML TextField ratePerNightText;
	@FXML Slider discountSlider;
	@FXML Button saveButton;
	@FXML Button findButton;
	@FXML TextField totalAmount;
	
	double ratePerNight = 0;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		model = new Bill();
		
		getSaveButton().setDisable(true);
		
		findButton.setOnAction(e -> {
			try
			{
				int bkid = Integer.parseInt(bookingIdText.getText());
				
				Reservation rv = new Reservation();
				Room room = new Room();
				Guest guest = new Guest();
				Reservation r = rv.load(bkid);
				
				if(r != null)
				{
					 geustNameText.setText(guest.load(r.getGuestId()).getFullname());
					 noOfRoomsText.setText(r.getRoomIds().split("]").length+"");
					 typeOfroomsText.setText(room.getTypeOfRooms(r.getRoomIds()));
					 ratePerNight = room.getRateOfRooms(r.getRoomIds());
					 ratePerNightText.setText("$"+ratePerNight+"");
					 totalAmount.setText("$"+Math.round(ratePerNight * (100-discountSlider.getValue())/100)+"");
					 
					 saveButton.setDisable(false);
				} else 
					saveButton.setDisable(true);
				
				
			} catch(Exception ex)
			{
				ex.printStackTrace();
				saveButton.setDisable(true);
			}
		});
		
		discountSlider.valueProperty().addListener(e -> {
			totalAmount.setText("$"+Math.round(ratePerNight * (100-discountSlider.getValue())/100)+"");
		});
	}

	public Button getReturnButton() {
		return returnButton;
	}

	public Button getSaveButton() {
		return saveButton;
	}

}
