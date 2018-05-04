package interfaceGraphic;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Controller implements Initializable {
	@FXML
	private Button _creation;
	@FXML
	private TextArea _textarea;
	@FXML
	private TextField _textf;
	@FXML
	private Button connexion;
	
	public void initialize(URL location, ResourceBundle resources) {

	       // TODO (don't really need to do anything here).

	   }
	
	@FXML
	public void showDateTime(ActionEvent event) {
	       System.out.println("Button Clicked!");

	       Date now= new Date();

	       DateFormat df = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");
	       String dateTimeString = df.format(now);
	        // Show in VIEW
	        _textf.setText(dateTimeString);

	   }
	
	public void showConnexion (ActionEvent event) {
		try {
			/*
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			*/
			 // Read file fxml and draw interface.
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/interfaceGraphic/Connexion.fxml"));
			Parent root = fxmlLoader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setOpacity(1);
			stage.setTitle("Connexion");
			stage.setScene(new Scene(root, 450, 450));
			stage.showAndWait();
            
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	@FXML
	public void showInscription (ActionEvent event) {
		try {
			
			 // Read file fxml and draw interface.
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/interfaceGraphic/Inscription.fxml"));
			Parent root = fxmlLoader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setOpacity(1);
			stage.setTitle("Iscription");
			stage.setScene(new Scene(root, 450, 450));
			stage.showAndWait();
            
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	@FXML
	public void showInfoProfil(ActionEvent event) {
		try {
			
			 // Read file fxml and draw interface.
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/interfaceGraphic/InfoProfil.fxml"));
			Parent root = fxmlLoader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setOpacity(1);
			stage.setTitle("Info Profil");
			stage.setScene(new Scene(root, 450, 450));
			stage.showAndWait();
           
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}


