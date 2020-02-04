package View_Controller;

import C482InventorySystemKylerDavis.MainApp;
import Model.Inventory;
import static Model.Inventory.getAllParts;
import static Model.Inventory.getAllProducts;
import Model.Part;
import Model.Product;
import java.io.IOException;
import java.util.Optional;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MainScreenController {
    private MainApp mainApp;
    public static Part partToModify;
    public static Product productToModify;
    @FXML
    private TableView<Part> partTableView;
    @FXML
    private TableColumn<Part, Integer> partTableViewPartId;
    @FXML
    private TableColumn<Part, String> PartTableViewName;
    @FXML
    private TableColumn<Part, Integer> partTableViewInv;
    @FXML
    private TableColumn<Part, Double> partTableViewPrice;
    @FXML
    private TableView<Product> productTableView;
    @FXML
    private TableColumn<Product, Integer> productTableViewProductId;
    @FXML
    private TableColumn<Product, String> productTableViewName;
    @FXML
    private TableColumn<Product, Integer> productTableViewInv;
    @FXML
    private TableColumn<Product, Double> productTableViewPrice;
    @FXML
    private TextField mainScreenSearchPartField;
    @FXML
    private TextField mainScreenSearchProductField;
    @FXML
    private Button mainScreenAddPartButton;
    @FXML
    private Button mainScreenModifyPartButton;
    @FXML
    private Button mainScreenAddProductButton;
    @FXML
    private Button mainScreenModifyProductButton;
    @FXML
    void mainScreenAddPartButtonClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) mainScreenAddPartButton.getScene().getWindow();
        Parent addPart = FXMLLoader.load(getClass().getResource("AddPart.fxml"));
        Scene scene = new Scene(addPart);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void mainScreenDeletePartButtonClicked(ActionEvent event) {
        int partToDeleteId = partTableView.getSelectionModel().getSelectedItem().getPartId();
        if(partToDeleteId != 0){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("You are about to delete a part");
            alert.setContentText("Are you sure?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK){
                getAllParts().remove(partToDeleteId-1);
                partTableView.setItems(Inventory.getAllParts());
            }
        }
    }
    @FXML
    void mainScreenModifyPartButtonClicked(ActionEvent event) throws IOException {
        partToModify = partTableView.getSelectionModel().getSelectedItem();
        if(partToModify != null){
            Stage stage = (Stage) mainScreenModifyPartButton.getScene().getWindow();
            Parent modifyPart = FXMLLoader.load(getClass().getResource("ModifyPart.fxml"));
            Scene scene = new Scene(modifyPart);
            stage.setScene(scene);
            stage.show();
        }
    }
    @FXML
    void mainScreenSearchPartButtonClicked(ActionEvent event) {
        ObservableList<Part> searchInventory = FXCollections.observableArrayList();
        String searchTerm = mainScreenSearchPartField.getText();
        boolean found = false;
        try{
        for(Part p: getAllParts()){
                int searchPartId = Integer.parseInt(searchTerm);
                if(p.getPartId() == searchPartId){
                    found = true;
                    searchInventory.add(p);
                    partTableView.setItems(searchInventory);
                }
            }
            if(found == false){
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Not Found Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Part ID is not found");
                alert.showAndWait();
            }
        }
            catch(NumberFormatException e){
                for(Part p: getAllParts()){
                    if(p.getName().equals(searchTerm)){
                        found = true;
                        searchInventory.add(p);
                        partTableView.setItems(searchInventory);
                    }
                }
                if(found == false){
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Not Found Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Part Name is not found");
                alert.showAndWait();
                }
            }
        }
    @FXML
    void mainScreenClearPartSearch(ActionEvent event) {
        partTableView.setItems(Inventory.getAllParts());
        mainScreenSearchPartField.setText("");
    }
    @FXML
    void mainScreenAddProductButtonClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) mainScreenAddProductButton.getScene().getWindow();
        Parent addProduct = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
        Scene scene = new Scene(addProduct);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void mainScreenDeleteProductButtonClicked(ActionEvent event) {
        int productToDeleteId = productTableView.getSelectionModel().getSelectedItem().getProductId();
        int productToDeletePartCount = productTableView.getSelectionModel().getSelectedItem().getAssociatedParts().size();
        if(productToDeleteId != 0){
            if(productToDeletePartCount > 0){
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Product Error Dialog");
                alert.setHeaderText("Error Cannot delete product");
                alert.setContentText("You are not able to delete Products with an associated part");
                alert.showAndWait();
            }
            else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("You are about to delete a part");
            alert.setContentText("Are you sure?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK){
                getAllProducts().remove(productToDeleteId-1);
                productTableView.setItems(Inventory.getAllProducts());
            }
            }
        }
    }
    @FXML
    void mainScreenModifyProductButtonClicked(ActionEvent event) throws IOException {
        productToModify = productTableView.getSelectionModel().getSelectedItem();
        if(productToModify != null){
            Stage stage = (Stage) mainScreenModifyProductButton.getScene().getWindow();
            Parent modifyProduct = FXMLLoader.load(getClass().getResource("ModifyProduct.fxml"));
            Scene scene = new Scene(modifyProduct);
            stage.setScene(scene);
            stage.show();
        }
    }
    @FXML
    void mainScreenSearchProductButtonClicked(ActionEvent event) {
        ObservableList<Product> searchInventory = FXCollections.observableArrayList();
        String searchTerm = mainScreenSearchProductField.getText();
        boolean found = false;
        try{
        for(Product p: getAllProducts()){
                int searchProductId = Integer.parseInt(searchTerm);
                if(p.getProductId() == searchProductId){
                    found = true;
                    searchInventory.add(p);
                    productTableView.setItems(searchInventory);
                }
            }
            if(found == false){
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Not Found Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Product ID is not found");
                alert.showAndWait();
            }
        }
            catch(NumberFormatException e){
                for(Product p: getAllProducts()){
                    if(p.getName().equals(searchTerm)){
                        found = true;
                        searchInventory.add(p);
                        productTableView.setItems(searchInventory);
                    }
                }
                if(found == false){
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Not Found Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Product Name is not found");
                alert.showAndWait();
                }
            }
    }
    @FXML
    void mainScreenClearProductSearch(ActionEvent event) {
        productTableView.setItems(Inventory.getAllProducts());
        mainScreenSearchProductField.setText("");
    }
    @FXML
    void mainScreenExitButtonClicked(ActionEvent event) {
        Platform.exit();
    }
    public void initialize(){
        partTableViewPartId.setCellValueFactory(cellData -> cellData.getValue().partIdProperty().asObject());
        PartTableViewName.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        partTableViewPrice.setCellValueFactory(cellData -> cellData.getValue().partPriceProperty().asObject());
        partTableViewInv.setCellValueFactory(cellData -> cellData.getValue().partInStockProperty().asObject());
        partTableView.setItems(Inventory.getAllParts());
        productTableViewProductId.setCellValueFactory(cellData -> cellData.getValue().productIdProperty().asObject());
        productTableViewName.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
        productTableViewPrice.setCellValueFactory(cellData -> cellData.getValue().productPriceProperty().asObject());
        productTableViewInv.setCellValueFactory(cellData -> cellData.getValue().productInStockProperty().asObject());
        productTableView.setItems(Inventory.getAllProducts());
    }
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp; 
    }
}
