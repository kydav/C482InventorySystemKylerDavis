package Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/**
 * @author kylerdavis
 */
public abstract class Part {
    private final SimpleIntegerProperty partId;
    private final SimpleStringProperty name;
    private final SimpleDoubleProperty price;
    private final SimpleIntegerProperty inStock;
    private final SimpleIntegerProperty min;
    private final SimpleIntegerProperty max;
    private static int partIdCount = 0;
    private static String error;
    
    public Part(){
        partId = new SimpleIntegerProperty();
        name = new SimpleStringProperty();
        price = new SimpleDoubleProperty();
        inStock = new SimpleIntegerProperty();
        min = new SimpleIntegerProperty();
        max = new SimpleIntegerProperty();
        error = "";
    }
    /*public Part(){
        partId = new SimpleIntegerProperty();
        name = new SimpleStringProperty();
        price = new SimpleDoubleProperty();
        inStock = new SimpleIntegerProperty();
        min = new SimpleIntegerProperty();
        max = new SimpleIntegerProperty();
    }*/
    public IntegerProperty partIdProperty() {
        return partId;
    }
    public StringProperty partNameProperty() {
        return name;
    }
    public DoubleProperty partPriceProperty() {
        return price;
    }
    public IntegerProperty partInStockProperty() {
        return inStock;
    }
    public void setPartId(int partId){
        this.partId.set(partId);
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
    public int getPartId(){
        return partId.get();
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
    public static int getPartIdCount() {
        partIdCount++;
        return partIdCount;
    }  
    public static String partValidation(String name, double price, int inStock,  int max, int min){
        error = null;
        if(inStock > max || inStock < min){
            error = "Inventory value must be greater than the minimum and less than the maximum value for the Product.";
        }
        if(min > max){
            error = "Minimum value must be less than the maximum value.";
        }
        if(max < min){
            error = "Maximum value must be greater than the minimum value.";
        }
        if(name== null){
            error = "Name field cannot be empty";
        }
        return error;
    }
}
