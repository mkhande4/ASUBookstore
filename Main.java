package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Main extends Application {
	public static User thisUser = new User();
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		AnchorPane root = new AnchorPane();
		Scene scene = new Scene(root);
		SceneController sceneController = new SceneController(scene);
		sceneController.addScene("login", FXMLLoader.load(getClass().getClassLoader().getResource("Login.fxml")));
		sceneController.addScene("seller", FXMLLoader.load(getClass().getClassLoader().getResource("Seller.fxml")));
		sceneController.addScene("buyer", FXMLLoader.load(getClass().getClassLoader().getResource("Buyer.fxml")));
		sceneController.addScene("purchase", FXMLLoader.load(getClass().getClassLoader().getResource("Purchase.fxml")));
		sceneController.addScene("admin", FXMLLoader.load(getClass().getClassLoader().getResource("Admin2.fxml")));

		if(thisUser.accountType == "") {
			LoginController controller = new LoginController();
			loader.setController(controller);
			sceneController.activate("login");

			primaryStage.setScene(scene);
			primaryStage.show();
		} else if (thisUser.accountType.toLowerCase().contains("seller")) {
			sceneController.activate("seller");
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} else if (thisUser.accountType.toLowerCase().contains("buyer")) {
			sceneController.activate("buyer");
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} else if (thisUser.accountType.toLowerCase().contains("purchase")) {
			sceneController.activate("purchase");
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} else if (thisUser.accountType.toLowerCase().contains("admin")) {
			AdminController2 adminController2 = new AdminController2();
			loader.setController(adminController2);
			sceneController.activate("admin");
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} else {
			Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setHeaderText("Something has gone wrong.");
			errorAlert.setContentText("Please contact administration.");
			errorAlert.show();
		}
			
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
}
