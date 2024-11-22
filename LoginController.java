package application;

import java.awt.Desktop;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class LoginController extends AnchorPane{

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    
    @FXML 
    private TextField emailField; 
    
    @FXML 
    private PasswordField passField;
    
    @FXML
    private Button loginButton;
    
    // Login/Logout functions

    @FXML
    private void loginTime(MouseEvent event) throws Exception {
    	
    	Node node = (Node)event.getSource();
    	Stage stage = (Stage)node.getScene().getWindow();

    	String email = emailField.getText();
    	String password = passField.getText();
    	
		Boolean isVerified = false;
		
		if(email.contains("@") == false || email.contains(".") == false) {
			Alert emailAlert = new Alert(AlertType.ERROR);
			emailAlert.setHeaderText("Invalid Email.");
			emailAlert.setContentText("Please verify that you have entered your email correctly.");
			emailAlert.show();
			return;
		}
		
		// load up current users
		Users allUsers = new Users();
		try {
			allUsers.uploadUsers();
		} catch (FileNotFoundException e) {
			Alert usersLoadError = new Alert(AlertType.ERROR);
			usersLoadError.setContentText("No Users Found Error: " + e.getMessage());
			usersLoadError.show();
			e.printStackTrace();
		}
		
		User checkUser = new User();
		
		// check if we have users currently
		if(allUsers.numUsers > 0) {
			checkUser = allUsers.searchUsersByEmail(email);
			
			// checks if that user exists
			if(checkUser.name.isEmpty()) {
				Alert errorAlert = new Alert(AlertType.ERROR);
				errorAlert.setHeaderText("No such email found.");
				errorAlert.setContentText("Please verify that you have entered your email correctly.");
				errorAlert.show();
				isVerified = false;
			} else {
				
				// checks if password matches
				Integer compareVal = checkUser.password.compareTo(password);
				if(compareVal != 0) {
					Alert errorAlert = new Alert(AlertType.ERROR);
					errorAlert.setHeaderText("Incorrect password.");
					errorAlert.setContentText("Please verify that you have entered your password correctly.");
					errorAlert.show();
					isVerified = false;
				} else {
					isVerified = true;
				}
			}
		}
		
		// if password and email match, switch scene
		if(isVerified == true) {
			Main main = new Main();
			Main.thisUser = checkUser;
			main.start(stage);
		}
    }
    
    @FXML
    private void logoutTime(MouseEvent event) throws Exception {
    	Node node = (Node)event.getSource();
    	Stage stage = (Stage)node.getScene().getWindow();
    	
    	Main main = new Main();
    	User noUser = new User();
    	Main.thisUser = noUser;
    	main.start(stage);
    	
    }
    
    @FXML
    private void resetPass(MouseEvent event) {
    	if(Desktop.isDesktopSupported())
	    {
	        try {
	            Desktop.getDesktop().browse(new URI("https://weblogin.asu.edu/password/lostpassword"));
	        } catch (IOException e1) {
				Alert ioError = new Alert(AlertType.ERROR);
				ioError.setContentText("IO Error: " + e1.getMessage());
				ioError.show();
				e1.printStackTrace();
	        } catch (URISyntaxException e2) {
				Alert uriError = new Alert(AlertType.ERROR);
				uriError.setContentText("URI Error: " + e2.getMessage());
				uriError.show();
				e2.printStackTrace();
	        }
	    }
    }
    
    @FXML
    private void newAccount(MouseEvent event) {
    	if(Desktop.isDesktopSupported())
	    {
	        try {
	            Desktop.getDesktop().browse(new URI("https://currentstudent.asuonline.asu.edu/user/register"));
	        } catch (IOException e1) {
				Alert ioError = new Alert(AlertType.ERROR);
				ioError.setContentText("IO Error: " + e1.getMessage());
				ioError.show();
				e1.printStackTrace();
	        } catch (URISyntaxException e2) {
				Alert uriError = new Alert(AlertType.ERROR);
				uriError.setContentText("URI Error: " + e2.getMessage());
				uriError.show();
				e2.printStackTrace();
	        }

	    }
   }
    
    

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	BooleanBinding emailValid = Bindings.createBooleanBinding(() -> {
    		if(emailField.getText().trim().isEmpty()) {
    			return false;
    		}
    		return true;
    	}, emailField.textProperty());

    	BooleanBinding passValid = Bindings.createBooleanBinding(() -> {
    		if(passField.getText().trim().isEmpty()) {
    			return false;
    		}
    		return true;
    	}, passField.textProperty());
    	
    	loginButton.disableProperty().bind(passValid.not().or(emailValid.not()));
    }

}
