package ws7.application;
	
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Pair;
import ws7.application.controller.AdminPanelController;
import ws7.application.controller.AvailableRoomsController;
import ws7.application.controller.BillingController;
import ws7.application.controller.BookingListController;
import ws7.application.controller.BookingNewController;
import ws7.application.controller.Controller;
import ws7.application.controller.GuestController;
import ws7.application.controller.HomeController;
import ws7.application.controller.LoginController;
import ws7.application.database.JDao;
import ws7.application.helper.RoomHelper;
import ws7.application.model.Login;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	
	HomeController homeController;
	BookingNewController bookingNewController;
	LoginController loginController;
	GuestController guestController;
	AdminPanelController adminPanelController;
	AvailableRoomsController availableRoomsController;
	BookingListController bookingListController;
	BillingController billingController;
	
	Login admin = new Login();
	

	BorderPane homeView, bookingNewView, loginView, guestView, adminPanelView, availableRoomsView, bookingListView, billingView;
	Stage pstage;
	
	private void setupActions()
	{
		
		pstage.setOnCloseRequest(e ->{
			
			if(e.getEventType() == WindowEvent.WINDOW_CLOSE_REQUEST)
			{
				e.consume();
				
				if(pstage.getScene().getRoot() == bookingNewView)
				{
				 if(bookingNewView.getUserData() == null || bookingNewView.getUserData().equals("userMode"))
				   pstage.getScene().setRoot(homeView);
				 else
				   pstage.getScene().setRoot(adminPanelView);
				} else
					if(pstage.getScene().getRoot() == adminPanelView)
					{
						admin.setAdmin(false);
						pstage.getScene().setRoot(homeView);
						
					} else
					if(pstage.getScene().getRoot() == homeView)
					{
						System.exit(0);
					}
			}
			
		});
		
		homeController.getStartBookingButton().setOnAction(e -> 
		{
			bookingNewView.setUserData("userMode");
			pstage.getScene().setRoot(bookingNewView);
		});
		
		homeController.getAdminPanelButton().setOnAction(e -> {
			pstage.getScene().setRoot(loginView);
		});
		
		bookingNewController.getCheckAvailableButton().setOnAction(e -> {
			bookingNewController.update();
			
			try
			{
				Map<String,Integer> rooms = bookingNewController.getRoomsFor();
				
			  	StringBuilder sb = new StringBuilder();
			  	
			  	if(rooms != null && rooms.size() > 0)
			  	{
			  		for(String r : rooms.keySet())
			  		{
			  			sb.append(String.format("%d %s Room%s\n", rooms.get(r),r,rooms.get(r) > 1 ? "s" : ""));
			  		}
			  		
			  	} else
			  	{
			  		sb.append("No offer !");
			  	}
			  	
			  	bookingNewController.getRecommendationText().setText(sb.toString());
			  	
			} catch(Exception ex)
			{
			   ex.printStackTrace();
			   
			   showAlert("Invalid number of guest !");
			}
			
		});
		
		bookingNewController.getBookButton().setOnAction(e -> {
			
			try
			{
				Pair<Boolean, String> v = bookingNewController.validateModel(); 
				if(v.getKey())
				{
					pstage.getScene().setRoot(guestView);
				} else
				{
					showAlert(v.getValue());
				}
			} catch(Exception ex)
			{
				ex.printStackTrace();
				showAlert("Invalid inputs !");
			}
		});
		
		guestController.getReturnButton().setOnAction(e ->
		{
			pstage.getScene().setRoot(bookingNewView);
		});
		
		guestController.getBookNowButton().setOnAction(e -> 
		{
		   	Pair<Boolean,String> r = guestController.validateModel();
		   	
		   	if(r.getKey())
		   	{
		   	   int gid = guestController.registerGuest();
		   	   int bid = bookingNewController.bookRoom(gid);
		   	   
		   	   if(bid > 0)
		   	   {
		   		   showInfo("Book Id -> " + bid);
		   	   } else
		   	   {
		   		   showAlert("Booking failed !");
		   	   }
		   	   
		   	   pstage.getScene().setRoot(homeView);
		   	   
		   	} else
		   	{
		   		showAlert(r.getValue());
		   	}
		   	
		});
		
		loginController.getReturnButton().setOnAction(e -> 
		{
			 pstage.getScene().setRoot(homeView);
		});
		
		loginController.getLoginButton().setOnAction(e -> {
			 
			 try
			 {
				 admin = loginController.authenticate();
				 
				 if(admin != null && admin.getIsAdmin())
				 {
				    
					 pstage.getScene().setRoot(adminPanelView);
				    
				 } else
				 {
					 showAlert("Invalid admin user !");
					 pstage.getScene().setRoot(homeView);
				 }
				 
			 } catch(Exception ex)
			 {
				 ex.printStackTrace();
				 
				 showAlert("Invalid user / password !");
			 }
		});
		
		adminPanelController.getApplicationExitButton().setOnAction(e ->
		{
			System.exit(0);
		});
		
		adminPanelController.getStartBookingButton().setOnAction(e -> 
		{
			bookingNewView.setUserData("adminMode");
			pstage.getScene().setRoot(bookingNewView);
		});
		
		adminPanelController.getAvailableRoomsButton().setOnAction(e ->{
			availableRoomsController.update();
			pstage.getScene().setRoot(availableRoomsView);
		});
		
		adminPanelController.getBillServiceButton().setOnAction(e -> {
			pstage.getScene().setRoot(billingView);
		});
		
		adminPanelController.getCurrentBookingsButton().setOnAction(e -> {
			bookingListController.update();
			pstage.getScene().setRoot(bookingListView);
		});
		
		
		availableRoomsController.getReturnButton().setOnAction(e -> {
			
			pstage.getScene().setRoot(adminPanelView);
		});
		
		
		bookingListController.getReturnButton().setOnAction(e -> {
			
			pstage.getScene().setRoot(adminPanelView);
		});
		
		
		billingController.getReturnButton().setOnAction(e -> {
			pstage.getScene().setRoot(adminPanelView);
		});
		
		billingController.getSaveButton().setOnAction(e ->{
			// save bill & checkout
			
			pstage.getScene().setRoot(adminPanelView);
		});
		
	}
	
	protected void showAlert(String msg)
	{
		Alert alert = new Alert(AlertType.ERROR);
		
		alert.setContentText(msg);
		alert.showAndWait();
	}
	
	protected void showInfo(String msg)
	{
		Alert alert = new Alert(AlertType.INFORMATION);
		
		alert.setContentText(msg);
		alert.showAndWait();
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			//BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Home.fxml"));
			pstage = primaryStage;
			
			homeView = HomeController.setupScene(HomeController.class,this);
			homeController = HomeController.getController();
			
			bookingNewView = BookingNewController.setupScene(BookingNewController.class, this);
			bookingNewController = BookingNewController.getController();
			
			loginView = LoginController.setupScene(LoginController.class, this);
			loginController = LoginController.getController();
			
			guestView = GuestController.setupScene(GuestController.class, this);
			guestController = GuestController.getController();
			
	
			adminPanelView = AdminPanelController.setupScene(AdminPanelController.class, this);
			adminPanelController = AdminPanelController.getController();
			
			
			availableRoomsView = AvailableRoomsController.setupScene(AvailableRoomsController.class, this);
			availableRoomsController = AvailableRoomsController.getController();
			
			bookingListView = BookingListController.setupScene(BookingListController.class, this);
			bookingListController = BookingListController.getController();
			
			billingView = BillingController.setupScene(BillingController.class, this);
			billingController = BillingController.getController();
			
			setupActions();
			
			Scene scene = new Scene(homeView,600,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
