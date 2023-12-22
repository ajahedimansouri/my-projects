package ws7.application.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import ws7.application.model.Guest;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.util.Pair;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;

public class GuestController extends Controller<Integer, GuestController, Guest> implements Initializable 
{
    
	@FXML TextField titleText;
	@FXML TextField firstNameText;
	@FXML TextField lastNameText;
	@FXML TextField emailText;
	@FXML TextField phoneText;
	@FXML TextArea addressText;
	@FXML Button returnButton;
	@FXML Button bookNowButton;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		model = new Guest();
		
		titleText.textProperty().addListener(e ->{
			model.setTitle(titleText.getText());
		});
		
		firstNameText.textProperty().addListener(e -> {
			model.setFirstname(firstNameText.getText());
		});
		
		lastNameText.textProperty().addListener(e -> {
			model.setLastname(lastNameText.getText());
		});
		
		emailText.textProperty().addListener(e -> 
		{
			String email = emailText.getText();
			
			if(!email.isBlank())
			{
				model.setEmail(email);
			} else
			{
				model.setEmail(null);
			}
		});
		
		addressText.textProperty().addListener(e -> {
			model.setAddress(addressText.getText());
		});
		
		phoneText.textProperty().addListener(e -> {
			model.setPhone(phoneText.getText());
		});
		
	}
	
	public Pair<Boolean, String> validateModel()
	{
		String rv = "";
		
		try
		{
		   if(model.getAddress() == null || model.getAddress().length() <= 0)
			   rv += "Invalid address !\n";
		   
		   if(model.getEmail() == null || !model.getEmail().matches("^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$"))
			   rv += "Invalid email address !\n";
		   
		   if(model.getFirtsname() == null || model.getFirtsname().length() <= 0)
		       rv += "Invalid firstname !\n";
		   
		   if(model.getLastname() == null || model.getLastname().length() <= 0)
			   rv += "Invalid lastname !\n";
		   
		   if(model.getPhone() == null || model.getPhone().length() <= 0)
		       rv += "Invalid phone no !\n";
		   
		   if(model.getTitle() == null || model.getTitle().length() <= 0)
		      rv += "Invalid title !\n";
		   
		   
		} catch(Exception ex)
		{
		   ex.printStackTrace();
		}
		
		return new Pair<Boolean, String>(rv.length() == 0, rv);
	}
	
	
	public int registerGuest()
	{
		int rv = -1;
		
		if(validateModel().getKey())
		{
			model.add(model);
			Optional<Guest> g = model.list().stream().filter(r -> r.getEmail().equals( model.getEmail()) && r.getPhone().equals(model.getPhone())).findFirst();
			if(!g.isEmpty())
				rv = g.get().getId();
		}
		
		return rv;
	}

	public TextField getTitleText() {
		return titleText;
	}

	public TextField getFirstNameText() {
		return firstNameText;
	}

	public TextField getLastNameText() {
		return lastNameText;
	}

	public TextField getEmailText() {
		return emailText;
	}

	public TextField getPhoneText() {
		return phoneText;
	}

	public TextArea getAddressText() {
		return addressText;
	}

	public Button getReturnButton() {
		return returnButton;
	}

	public Button getBookNowButton() {
		return bookNowButton;
	}

}
