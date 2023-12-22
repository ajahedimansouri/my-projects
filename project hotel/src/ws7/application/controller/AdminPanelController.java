package ws7.application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AdminPanelController extends Controller<BorderPane, AdminPanelController, Void> implements Initializable 
{

	@FXML Button startBookingButton;
	@FXML Button billServiceButton;
	@FXML Button currentBookingsButton;
	@FXML Button availableRoomsButton;
	@FXML Button applicationExitButton;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

	public Button getStartBookingButton() {
		return startBookingButton;
	}

	public Button getBillServiceButton() {
		return billServiceButton;
	}

	public Button getCurrentBookingsButton() {
		return currentBookingsButton;
	}

	public Button getAvailableRoomsButton() {
		return availableRoomsButton;
	}

	public Button getApplicationExitButton() {
		return applicationExitButton;
	}

}
