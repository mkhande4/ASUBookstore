package application;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.text.Text;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.shape.Line;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

public class PurchaseController extends BuyerController {

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
    @FXML
    private Label totalTextLabel;
    @FXML
    private Label totalNumberLabel;
    @FXML
    private Label taxTextLabel;
    @FXML
    private Label taxNumberLabel;
    @FXML
    private Line bottomLine;

    private ObservableList<String> cartItems; 
    private static ArrayList<Book> purchasedBooks = BuyerController.purchasedBooks;
    private double total, tax;
    private Books listOfBooks;

    public void initialize() throws FileNotFoundException {
        cartItems = FXCollections.observableArrayList();
        cartListView.setItems(cartItems);
        updateCartList();
        statusLabel.setVisible(false);
        listOfBooks = new Books();
        listOfBooks.uploadBooks();
        total = 0;
        tax = 0;
    }

    private void updateCartList() {
        cartItems.clear();
 
        if (purchasedBooks.isEmpty()) {
        	priceLabel.setVisible(false);
        	totalTextLabel.setVisible(false);
        	totalNumberLabel.setVisible(false);
        	taxTextLabel.setVisible(false);
        	taxNumberLabel.setVisible(false);
        	cartPane.setVisible(false);
        	emptyCartLabel.setVisible(true);
        	bottomLine.setVisible(false);
        }
        
        else {
            priceLabel.setVisible(true);
            totalTextLabel.setVisible(true);
            taxTextLabel.setVisible(true);
            emptyCartLabel.setVisible(false);
            for (Book book : purchasedBooks) {
            	String price = String.format("$%.2f", book.getGeneratedPrice());
            	String bookInfo = book.getTitle() +  "\n" +
            			"Author: " + book.getAuthor() + "\n" +
            			"Published Year: " + book.getPubYear() + 
            			"\t\t\t\t\t\t\t\t\t\t\t\t\t\t   " + price + "\n" +
            			"ISBN: " + book.getIsbn()  + "\n"  +
                        "Category: " + book.getCategory() + "\n" +
                        "Condition: " + book.getCondition() + "\n" + "\n";
            	cartItems.add(bookInfo);
            	total += book.getGeneratedPrice();
            }
            
            tax = total * .056;
            total += tax;
            
            totalNumberLabel.setText(String.format("$%.2f", total));
            totalNumberLabel.setVisible(true);
            taxNumberLabel.setText(String.format("$%.2f", tax));
        	taxNumberLabel.setVisible(true);
            
        }  
    }

    @FXML
    public void checkout(ActionEvent event) throws Exception {
        if (purchasedBooks.isEmpty()) {
            System.out.println("Your cart is empty. Cannot proceed with checkout.");
            return;
        }

        
        for (Book book : purchasedBooks) {
        	Book bookInCatalog = listOfBooks.searchBookById(book.getId());
            if (bookInCatalog != null) {
                bookInCatalog.setSold(true);
                bookInCatalog.setBuyer(Main.thisUser.getEmail());
                total += bookInCatalog.getGeneratedPrice();
            }
        }
        
    
        
        listOfBooks.updateBookFile();

        priceLabel.setVisible(false);
    	totalTextLabel.setVisible(false);
    	totalNumberLabel.setVisible(false);
    	taxTextLabel.setVisible(false);
    	taxNumberLabel.setVisible(false);
    	bottomLine.setVisible(false);
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