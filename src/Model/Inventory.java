package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * @author kylerdavis
 */
public class Inventory {
    private static ObservableList <Product> products = FXCollections.observableArrayList();
    private static ObservableList <Part> allParts = FXCollections.observableArrayList();
    
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
    public static ObservableList<Product> getAllProducts() {
        return products;
    }
    public static void addProduct(Product product){
        products.add(product);
    }
    public boolean removeProduct(Product product){
        products.remove(product);
        return true;
    }
    public Product lookupProduct(int productId){
        return products.get(productId);
    }
    public static void updateProduct(int index, Product product){
        products.set(index, product);
    }
    public static void addPart(Part part){
        allParts.add(part);
    }
    public boolean deletePart(Part part){
        allParts.remove(part);
        return true;
    }
    public Part lookupPart(int partId){
        return allParts.get(partId);
    }
    public static void updatePart(int index, Part part){
        allParts.set(index, part);
    }
}
