module ws7 {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
	requires javafx.graphics;
	requires javafx.base;
	
	opens ws7.application to javafx.graphics, javafx.fxml, javafx.base;
	opens ws7.application.controller to javafx.graphics, javafx.fxml, javafx.base;
	opens ws7.application.model to javafx.graphics, javafx.fxml, javafx.base;

}
