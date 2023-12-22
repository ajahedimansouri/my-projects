package ws7.application.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import ws7.application.model.Room;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Button;

public class AvailableRoomsController extends Controller<BorderPane, AvailableRoomsController, Room> 
							          implements Initializable 
{

	@FXML TextField availableRoomsNoText;
	@FXML TableView<Room> availableRoomsTableView;
	@FXML Button returnButton;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
	    ((TableColumn<Room,Integer>)availableRoomsTableView.getColumns().get(0)).setCellValueFactory(new PropertyValueFactory<Room, Integer>("id"));
	    ((TableColumn<Room,String>)availableRoomsTableView.getColumns().get(1)).setCellValueFactory(new PropertyValueFactory<Room, String>("type"));
	    ((TableColumn<Room,Double>)availableRoomsTableView.getColumns().get(2)).setCellValueFactory(new PropertyValueFactory<Room, Double>("rate"));
	    
	    this.update();
	}

	
	public void update()
	{
		model = new Room();
		
		List<Room> ar = model.listOfFreeFormNow();
	    
	    if(ar != null)
	    {
	    	
	    	availableRoomsTableView.setItems(FXCollections.observableList(ar));
	    	
	    	
	    	availableRoomsNoText.setText(ar.size()+"");
	    } else
	    {
	    	availableRoomsNoText.setText("0");
	    }
	    
	    availableRoomsTableView.refresh();
	}
	
	
	public Button getReturnButton() {
		return returnButton;
	}

	public TableView<Room> getAvailableRoomsTableView() {
		return availableRoomsTableView;
	}

	public TextField getAvailableRoomsNoText() {
		return availableRoomsNoText;
	}

}
