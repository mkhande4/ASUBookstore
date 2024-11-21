package application;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.text.Text;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import java.util.ArrayList;

public class PurchaseController {

    @FXML
    private Button returnButton;
    @FXML
    private Button checkoutButton;
    @FXML
    private Button logoutButton;
    @FXML
    private ListView<String> cartListView;
    @FXML
    private ScrollPane cartPane; 
    @FXML
    private Label emptyCartLabel; 
    @FXML
    private Label priceLabel; 
    @FXML
    private Label statusLabel; 

    private ObservableList<String> cartItems; 
    private static ArrayList<Book> purchasedBooks = BuyerPageController.purchasedBooks; 

    public void initialize() {
        cartItems = FXCollections.observableArrayList();
        cartListView.setItems(cartItems);
        updateCartList();
        statusLabel.setVisible(false);
    }

    private void updateCartList() {
        cartItems.clear();
        for (Book book : purchasedBooks) {
        	String bookInfo = book.getTitle() +  "\n" +
                    "Category: " + book.getCategory() + "\n" +
                    "Condition: " + book.getCondition() + "\n" +
                    "Author: " + book.getAuthor() + "\n" +
                    "Published Year: " + book.getPubYear() + "\n" +
                    "ISBN: " + book.getIsbn()  + "\n";
        	
        	cartItems.add(bookInfo);
        }
        
        if (purchasedBooks.isEmpty()) {
        	priceLabel.setVisible(false);
        	cartPane.setVisible(false);
        	emptyCartLabel.setVisible(true);
        }
        
        else {
            priceLabel.setVisible(true);
            emptyCartLabel.setVisible(false);
        }
    }

    @FXML
    public void checkout(ActionEvent event) throws Exception {
        if (purchasedBooks.isEmpty()) {
            System.out.println("Your cart is empty. Cannot proceed with checkout.");
            return;
        }

        
        for (Book book : purchasedBooks) {
            book.setSold(true); 

        }

        priceLabel.setVisible(false);
        cartPane.setVisible(false); 
        statusLabel.setVisible(true); 

       
        cartItems.clear();
        purchasedBooks.clear();
    }

    @FXML
    private void logoutTime(ActionEvent event) throws Exception {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Main main = new Main();
        User noUser = new User();
        main.thisUser = noUser;
        main.start(stage);
    }

    @FXML
    public void goBackToPreviousPage(ActionEvent event) throws Exception {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Main main = new Main();
        main.thisUser.setAccountType("buyer");
        main.start(stage);
    }
}
