package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.FileNotFoundException;
import java.util.*;

public class BuyerController{
    @FXML
    private ComboBox<String> categoryBox;
    @FXML
    private ComboBox<String> conditionBox;

    @FXML
    private TableView bookTable = new TableView<BuyerPageDataModel>();

    @FXML
    private TableColumn bookTitle = new TableColumn<BuyerPageDataModel, String>("Title");

    @FXML
    private TableColumn bookAuthor = new TableColumn<BuyerPageDataModel,String>("Author");
    @FXML
    private TableColumn bookISBN = new TableColumn<BuyerPageDataModel, String>("ISBN");
    @FXML
    private TableColumn bookPrice = new TableColumn<BuyerPageDataModel,Double>("Price");//might need to switch to int or convert int to string
    @FXML
    private TableColumn bookQuantity = new TableColumn<BuyerPageDataModel,String>("Quantity");//might need to switch to int or convert int to string

    private Books booksObject;

    private ArrayList<Book> selectedCategoryAllBooks = new ArrayList<Book>();

    private ArrayList<Book> selectedConditionAllBooks = new ArrayList<Book>();

    static ArrayList<Book> purchasedBooks = new ArrayList<Book>();


    public void initialize() throws FileNotFoundException {
        categoryBox.getItems().addAll("Natural Science","Computer","Math","English Language","Other");

        bookTitle.setCellValueFactory(new PropertyValueFactory<BuyerPageDataModel,String>("title"));
        bookAuthor.setCellValueFactory(new PropertyValueFactory<BuyerPageDataModel,String>("author"));
        bookISBN.setCellValueFactory(new PropertyValueFactory<BuyerPageDataModel,String>("isbn"));
        bookPrice.setCellValueFactory(new PropertyValueFactory<BuyerPageDataModel,Double>("price"));
        bookQuantity.setCellValueFactory(new PropertyValueFactory<BuyerPageDataModel,String>("quantity"));

        Books books = new Books();
        books.uploadBooks();

        booksObject = books;

    }

    @FXML public void setBooksByCategory(){
        if(conditionBox.getItems().size() == 0){
            conditionBox.getItems().addAll("Used Like New","Moderately Used","Heavily Used");
        }

        String selectedCategory = categoryBox.getValue();
        ArrayList<Book> allBooks = booksObject.getBooks();
        selectedCategoryAllBooks = new ArrayList<Book>();

        ArrayList<String> uniqueISBNSelectedCategory = new ArrayList<String>();
        ArrayList<Book> uniqueISBNSelectedCategoryBooks = new ArrayList<Book>();
        ArrayList<Integer> uniqueISBNSelectedCategoryQuantity = new ArrayList<Integer>();



        for(int i = 0; i < booksObject.numBooks; i++){
            if(allBooks.get(i).getCategory().equals(selectedCategory)){
                selectedCategoryAllBooks.add(allBooks.get(i));
                if(!uniqueISBNSelectedCategory.contains(allBooks.get(i).isbn)){
                    uniqueISBNSelectedCategory.add(allBooks.get(i).isbn);
                    uniqueISBNSelectedCategoryBooks.add(allBooks.get(i));
                }
            }
        }

        for(int j = 0; j < uniqueISBNSelectedCategory.size(); j++){
            uniqueISBNSelectedCategoryQuantity.add((Integer)0);
        }

        for(int y = 0; y < selectedCategoryAllBooks.size(); y++){
            int quantityIndex = uniqueISBNSelectedCategory.indexOf(selectedCategoryAllBooks.get(y).isbn);
            uniqueISBNSelectedCategoryQuantity.set(quantityIndex, (Integer) uniqueISBNSelectedCategoryQuantity.get(quantityIndex) + 1);
        }


        ArrayList<BuyerPageDataModel> booksToDisplay = new ArrayList<BuyerPageDataModel>();
        for(int z = 0; z < uniqueISBNSelectedCategoryBooks.size(); z++){
            String title = uniqueISBNSelectedCategoryBooks.get(z).title;
            String author = uniqueISBNSelectedCategoryBooks.get(z).author;
            String isbn = uniqueISBNSelectedCategoryBooks.get(z).isbn;
            Double price = uniqueISBNSelectedCategoryBooks.get(z).generatedPrice;
            String quantity = uniqueISBNSelectedCategoryQuantity.get(z).toString();

            booksToDisplay.add(new BuyerPageDataModel(title, author, isbn, price, quantity, uniqueISBNSelectedCategoryBooks.get(z)));
        }

        System.out.println(conditionBox.getValue());
        if(conditionBox.getValue() == null){
            ObservableList<BuyerPageDataModel> bookOL = FXCollections.observableArrayList(booksToDisplay);
            bookTable.setItems(bookOL);
        }else{
            this.setBooksByCondition();
        }
    }

    @FXML public void setBooksByCondition(){

        String selectedCondition = conditionBox.getValue();
        selectedConditionAllBooks = new ArrayList<Book>();
        ArrayList<String> uniqueISBNSelectedCondition = new ArrayList<String>();
        ArrayList<Book> uniqueISBNSelectedConditionBooks = new ArrayList<Book>();
        ArrayList<Integer> uniqueISBNSelectedConditionQuantity = new ArrayList<Integer>();

        //ArrayList<ArrayList<Book>> allCategoryBooksPerISBN = new ArrayList<ArrayList<Book>>();

        for(int i = 0; i < selectedCategoryAllBooks.size(); i++){
            if(selectedCategoryAllBooks.get(i).getCondition().equals(selectedCondition)){
                selectedConditionAllBooks.add(selectedCategoryAllBooks.get(i));
                if(!uniqueISBNSelectedCondition.contains(selectedCategoryAllBooks.get(i).isbn)){
                    uniqueISBNSelectedCondition.add(selectedCategoryAllBooks.get(i).isbn);
                    uniqueISBNSelectedConditionBooks.add(selectedCategoryAllBooks.get(i));
                }
            }
        }

        for(int j = 0; j < uniqueISBNSelectedCondition.size(); j++){
            uniqueISBNSelectedConditionQuantity.add((Integer)0);
        }

        for(int y = 0; y < selectedConditionAllBooks.size(); y++){
            int quantityIndex = uniqueISBNSelectedCondition.indexOf(selectedConditionAllBooks.get(y).isbn);
            uniqueISBNSelectedConditionQuantity.set(quantityIndex, (Integer) uniqueISBNSelectedConditionQuantity.get(quantityIndex) + 1);
        }



        ArrayList<BuyerPageDataModel> booksToDisplay = new ArrayList<BuyerPageDataModel>();
        for(int z = 0; z < uniqueISBNSelectedConditionBooks.size(); z++){
            String title = uniqueISBNSelectedConditionBooks.get(z).title;
            String author = uniqueISBNSelectedConditionBooks.get(z).author;
            String isbn = uniqueISBNSelectedConditionBooks.get(z).isbn;
            Double price = uniqueISBNSelectedConditionBooks.get(z).generatedPrice;
            String quantity = uniqueISBNSelectedConditionQuantity.get(z).toString();

            booksToDisplay.add(new BuyerPageDataModel(title, author, isbn, price, quantity, uniqueISBNSelectedConditionBooks.get(z)));
        }

        ObservableList<BuyerPageDataModel> bookOL = FXCollections.observableArrayList(booksToDisplay);
        bookTable.setItems(bookOL);
    }
    @FXML
    public void goToCheckout(ActionEvent event) throws Exception {
        //InputEvent event =
        Node node = (Node)event.getSource();
        Scene scene = (Scene)node.getScene();
        Stage stage = (Stage)node.getScene().getWindow();
        Main main = new Main();
        main.thisUser.setAccountType("purchase");
        main.start(stage);
    }
    @FXML
    public void addToCart(){


        BuyerPageDataModel selectedBook = (BuyerPageDataModel) bookTable.getSelectionModel().getSelectedItem();
        if(selectedBook != null){
            purchasedBooks.add(selectedBook.getBook());
        }



    }
    @FXML
    private void logoutTime(ActionEvent event) throws Exception {

        Node node = (Node)event.getSource();
        Stage stage = (Stage)node.getScene().getWindow();
        Main main = new Main();
        User noUser = new User();
        main.thisUser.setAccountType("");
        main.start(stage);

    }

}


