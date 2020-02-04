package Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * @author kylerdavis
 */
public class Product {
    private static ObservableList <Part> associatedParts = FXCollections.observableArrayList();
    private final SimpleIntegerProperty productId;
    private final SimpleStringProperty name;
    private final SimpleDoubleProperty price;
    private final SimpleIntegerProperty inStock;
    private final SimpleIntegerProperty min;
    private final SimpleIntegerProperty max;
    private static int productIdCount = 0;
    private static String error;
    
    public Product(){
        productId = new SimpleIntegerProperty();
        name = new SimpleStringProperty();
        price = new SimpleDoubleProperty();
        inStock = new SimpleIntegerProperty();
        min = new SimpleIntegerProperty();
        max = new SimpleIntegerProperty();
        error = "";
    }
    public IntegerProperty productIdProperty() {
        return productId;
    }
    public StringProperty productNameProperty() {
        return name;
    }
    public DoubleProperty productPriceProperty() {
        return price;
    }
    public IntegerProperty productInStockProperty() {
        return inStock;
    }
    public void setProductId(int productId){
        this.productId.set(productId);
    }
    public void setName(String name){
        this.name.set(name);
    }
    public void setPrice(double price){
        this.price.set(price);
    }
    public void setInStock(int inStock){
        this.inStock.set(inStock);
    }
    public void setMin(int min){
        this.min.set(min);
    }
    public void setMax(int max){
        this.max.set(max);
    }
    public void setAssociatedParts(ObservableList<Part> associatedParts){
        this.associatedParts = associatedParts;
    }
    public int getProductId(){
        return productId.get();
    }
    public String getName(){
        return name.get();
    }
    public double getPrice(){
        return price.get();
    }
    public int getInStock(){
        return inStock.get();
    }
    public int getMin(){
        return min.get();
    }
    public int getMax(){
        return max.get();
    }
    public static ObservableList getAssociatedParts() {
        return associatedParts;
    }
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }
    public boolean removeAssociatedPart(int partId){
        associatedParts.remove(partId);
        return true;
    }
    public Part lookupAssociatedPart(int partId){
        return associatedParts.get(partId);
    }
    public static int getProductIdCount() {
        productIdCount++;
        return productIdCount;
    }  
    public static String productValidation(String name, double price, int inStock,  int max, int min, int partCount, double partCost){
        error = null;
        if(inStock > max || inStock < min){
            error = "Inventory value must be greater than the minimum and less than the maximum value for the Part.";
        }
        if(min > max){
            error = "Minimum value must be less than the maximum value.";
        }
        if(max < min){
            error = "Maximum value must be greater than the minimum value.";
        }
        if(name == null){
            error = "Name field cannot be empty";
        }
        if(partCount == 0){
            error = "Must be associated to at least one part";
        }
        if(partCost > price){
            error = "The total cost of the parts cannot exceed the cost of the product";
        }
        return error;
    }
}
