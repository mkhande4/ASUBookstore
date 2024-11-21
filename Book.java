//Book.java
//Class Object to handle book Information
package application;

public class Book {
    protected Integer ID;
    protected String isbn;
    protected String title;
    protected String author;
    protected String condition;
    protected String category;
    protected Double originalPrice;
    protected Double generatedPrice;
    protected String seller;
    protected String buyer;
    protected Boolean sold;
    protected Integer pubYear;
    public Book() {
    	
    }

    public Book(Integer ID, String isbn, String title, String author, String condition, String category,
            Double originalPrice, Double generatedPrice, String seller, String buyer, Boolean sold, Integer year) {
        this.ID = ID;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.condition = condition;
        this.category = category;
        this.originalPrice = originalPrice;
        this.generatedPrice = generatedPrice;
        this.seller = seller;
        this.buyer = buyer;
        this.sold = sold;
        this.pubYear = year;
    }

    public String getTitle() {
        return this.title;
    }

    
    // Setter functions to be able to change each attribute of a book
    public void setPubYear(Integer newYear) {
    	this.pubYear = newYear;
    }
    public void setID(Integer newID) {
        this.ID = newID;
    }

    public void setIsbn(String newISBN) {
        this.isbn = newISBN;
    }

    public void setTitle(String newTitle) {
        this.title = newTitle;
    }

    public void setAuthor(String newAuthor) {
        this.author = newAuthor;
    }

    public void setCondition(String newCondition) {
        this.condition = newCondition;
    }

    public void setCategory(String newCategory) {
        this.category = newCategory;
    }

    public void setOriginalPrice(Double newOriginalPrice) {
        this.originalPrice = newOriginalPrice;
    }

    public void setGeneratedPrice(Double newGeneratedPrice) {
        this.generatedPrice = newGeneratedPrice;
    }

    public void setSeller(String newSeller) {
        this.seller = newSeller;
    }

    public void setBuyer(String newBuyer) {
        this.buyer = newBuyer;
    }

    public void setSold(Boolean newSoldStatus) {
        this.sold = newSoldStatus;
    }

    // Getter functions for each attribute of a book
    public Integer getPubYear() {
    	return pubYear;
    }
    public Integer getId() {
        return ID;
    }

    public String getIsbn() {
        return isbn;
    }


    public String getAuthor() {
        return author;
    }

    public String getCondition() {
        return condition;
    }

    public String getCategory() {
        return category;
    }

    public Double getOriginalPrice() {
        return originalPrice;
    }

    public Double getGeneratedPrice() {
        return generatedPrice;
    }

    public String getSeller() {
        return seller;
    }

    public String getBuyer() {
        if (seller != null) {
            return buyer;
        } else {
            return null;
        }
    }

    public void displayBook() {
        System.out.println("ID:" + this.ID);
        System.out.println("ISBN: " + this.isbn);
        System.out.println("Title: " + this.title);
        System.out.println("Author: " + this.author);
        System.out.println("Condition: " + this.condition);
        System.out.println("Category: " + this.category);
        System.out.println("Original Price: " + this.originalPrice);
        System.out.println("Generated Price: " + this.generatedPrice);
        System.out.println("Seller: " + this.seller);
        System.out.println("Buyer: " + this.buyer);
        System.out.println("isSold: " + this.sold);
        System.out.println("Year Published" + this.pubYear);
    }

    public Boolean isSold() {
        return sold;
    }

}
