package ws7.application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class HomeController extends Controller<BorderPane, HomeController, Void> implements Initializable
{
	@FXML
	Button startBookingButton;
	
	@FXML
	Button adminPanelButton;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
	}
	
	
	public Button getStartBookingButton()
	{
		return this.startBookingButton;
	}
	
	
	public Button getAdminPanelButton()
	{
		return this.adminPanelButton;
	}
	
}
