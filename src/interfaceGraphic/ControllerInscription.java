package interfaceGraphic;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ControllerInscription implements Initializable{
	@FXML
	private TextField login;
	@FXML
	private TextField password1;
	@FXML
	private TextField password2;
	@FXML
	private TextField mail;
	@FXML
	private Button inscriOk;
	public ControllerInscription()  {
		// TODO Auto-generated constructor stub
	}
	
	private void initialize () {
		
	}
	@FXML
	public String getLogin() {
		return login.getText();
	}
	
	public String getPassword1() {
		return password1.getText();
	}
	
	public String getPassword2() {
		return password2.getText();
	}
	public String getMail() {
		return mail.getText();
	}
	@FXML
	public void inscriOk(ActionEvent event) {
		String ll;
		//ll = getLogin();
		System.out.println(login.getText());
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
}
