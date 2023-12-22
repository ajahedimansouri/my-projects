package ws7.application.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import ws7.application.model.Login;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;

public class LoginController extends Controller<BorderPane, LoginController, Login> implements Initializable  
{
	

	@FXML TextField userIdText;
	@FXML PasswordField passwordText;
	@FXML Button returnButton;
	@FXML Button loginButton;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		model = new Login();
	}

	public TextField getUserIdText() {
		return userIdText;
	}

	public PasswordField getPasswordText() {
		return passwordText;
	}

	public Button getReturnButton() {
		return returnButton;
	}

	public Button getLoginButton() {
		return loginButton;
	}
	
	public Login authenticate()
	{
		Login rv = null;
		
		try
		{
			int userId = Integer.parseInt(userIdText.getText());
			String password = passwordText.getText();
			
			Login l = model.load(userId);
			
			if(l == null || 
					 l.getPassword() == null || 
					!l.getPassword().equals(password) 
			  )
			{
				rv = null;
			} else
			{
				rv = l;
			}
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		return rv;
	}

}
