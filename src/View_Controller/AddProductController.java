package View_Controller;

import Model.Inventory;
import static Model.Inventory.getAllParts;
import Model.Part;
import Model.Product;
import java.io.IOException;
import java.util.Optional;
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

public class AddProductController {
    private final ObservableList<Part> tempAssociatedParts = FXCollections.observableArrayList();
    private final ObservableList<Part> tempAllParts = FXCollections.observableArrayList(getAllParts());
    private double totalCostOfParts = 0;
    @FXML
    private TextField addProductNameField;
    @FXML
    private TextField addProductInvField;
    @FXML
    private TextField addProductPriceField;
    @FXML
    private TextField addProductMaxField;
    @FXML
    private TextField addProductMinField;
    @FXML
    private Button addProductSaveButton;
    @FXML
    private Button addProductCancelButton;
    @FXML
    private TableView<Part> allPartsTableView;
    @FXML
    private TableColumn<Part, Integer> allPartsPartId;
    @FXML
    private TableColumn<Part, String> allPartsPartName;
    @FXML
    private TableColumn<Part, Integer> allPartsInv;
    @FXML
    private TableColumn<Part, Double> allPartsPrice;
    @FXML
    private TableView<Part> associatedPartsTableView;
    @FXML
    private TableColumn<Part, Integer> associatedPartsPartId;
    @FXML
    private TableColumn<Part, String> associatedPartsName;
    @FXML
    private TableColumn<Part, Integer> associatedPartsInv;
    @FXML
    private TableColumn<Part, Double> associatedPartsPrice;
    @FXML
    private TextField allPartsSearchField;
    @FXML
    private TextField associatedPartsSearchField;
    @FXML
    void addProductAddButtonClicked(ActionEvent event) {
        Part partToAdd = allPartsTableView.getSelectionModel().getSelectedItem();
        if(partToAdd != null){
            tempAssociatedParts.add(partToAdd);
            associatedPartsTableView.setItems(tempAssociatedParts);
            tempAllParts.remove(partToAdd);
            allPartsTableView.setItems(tempAllParts);
            totalCostOfParts += partToAdd.getPrice();
        }
    }
    @FXML
    void addProductCancelButtonClicked(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("You are about to cancel creating a new part");
        alert.setContentText("Are you sure?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            Stage stage = (Stage) addProductCancelButton.getScene().getWindow();
            Parent cancelButton = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene scene = new Scene(cancelButton);
            stage.setScene(scene);
            stage.show();
        }
    }
    @FXML
    void addProductDeleteButtonClicked(ActionEvent event) {
        Part partToDelete = associatedPartsTableView.getSelectionModel().getSelectedItem();
        if(partToDelete != null){
            tempAllParts.add(partToDelete);
            allPartsTableView.setItems(tempAllParts);
            tempAssociatedParts.remove(partToDelete);
            associatedPartsTableView.setItems(tempAssociatedParts);
            totalCostOfParts -= partToDelete.getPrice();
        }
    }
    @FXML
    void addProductSaveButtonClicked(ActionEvent event) throws IOException {
        
        String name = addProductNameField.getText();
        String inv = addProductInvField.getText();
        String cost = addProductPriceField.getText();
        String max = addProductMaxField.getText();
        String min = addProductMinField.getText();
        int associatedParts = tempAssociatedParts.size();
        try{
            Integer parsedInv = Integer.parseInt(inv);
            Double parsedCost = Double.parseDouble(cost);
            Integer parsedMax = Integer.parseInt(max);
            Integer parsedMin = Integer.parseInt(min);
            
            String isProductValid = Product.productValidation(name, parsedCost, parsedInv, parsedMax, parsedMin, associatedParts, totalCostOfParts);
            if(isProductValid == null){
                Product product = new Product();
                int productId = Product.getProductIdCount();
                product.setProductId(productId);
                product.setName(name);
                product.setInStock(parsedInv);
                product.setPrice(parsedCost);
                product.setMax(parsedMax);
                product.setMin(parsedMin);
                product.setAssociatedParts(tempAssociatedParts);
                Inventory.addProduct(product);

                Stage stage = (Stage) addProductSaveButton.getScene().getWindow();
                Parent saved = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene scene = new Scene(saved);
                stage.setScene(scene);
                stage.show();
        }
            else{
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Part Error Dialog");
                alert.setHeaderText("Error Cannot add product");
                alert.setContentText(isProductValid);
                alert.showAndWait();
            }
        }
        catch(NumberFormatException e){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Product Error Dialog");
            alert.setHeaderText("Error Cannot add product");
            alert.setContentText("Please fill in any empty fields");
            alert.showAndWait();
        }
    }
    @FXML
    void allPartsSearchButtonClicked(ActionEvent event) {
        ObservableList<Part> searchInventory = FXCollections.observableArrayList();
        String searchTerm = allPartsSearchField.getText();
        boolean found = false;
        try{
        for(Part p: tempAllParts){
                int searchPartId = Integer.parseInt(searchTerm);
                if(p.getPartId() == searchPartId){
                    found = true;
                    searchInventory.add(p);
                    allPartsTableView.setItems(searchInventory);
                }
            }
            if(found == false){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Not Found Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Part ID is not found");
                alert.showAndWait();
            }
        }
            catch(NumberFormatException e){
                for(Part p: tempAllParts){
                    if(p.getName().equals(searchTerm)){
                        found = true;
                        searchInventory.add(p);
                        allPartsTableView.setItems(searchInventory);
                    }
                }
                if(found == false){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Not Found Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Part Name is not found");
                alert.showAndWait();
                }
            }
    }
    @FXML
    void allPartsClearButtonClicked(ActionEvent event) {
        allPartsTableView.setItems(tempAllParts);
        allPartsSearchField.setText("");
    }
    @FXML
    void associatedPartsSearchButtonClicked(ActionEvent event) {
        ObservableList<Part> searchInventory = FXCollections.observableArrayList();
        String searchTerm = associatedPartsSearchField.getText();
        boolean found = false;
        try{
        for(Part p: tempAssociatedParts){
                int searchPartId = Integer.parseInt(searchTerm);
                if(p.getPartId() == searchPartId){
                    found = true;
                    searchInventory.add(p);
                    associatedPartsTableView.setItems(searchInventory);
                }
            }
            if(found == false){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Not Found Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Part ID is not found");
                alert.showAndWait();
            }
        }
            catch(NumberFormatException e){
                for(Part p: tempAssociatedParts){
                    if(p.getName().equals(searchTerm)){
                        found = true;
                        searchInventory.add(p);
                        associatedPartsTableView.setItems(searchInventory);
                    }
                }
                if(found == false){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Not Found Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Part Name is not found");
                alert.showAndWait();
                }
            }
    }
    @FXML
    void associatedPartsClearButtonClicked(ActionEvent event) {
        associatedPartsTableView.setItems(tempAssociatedParts);
        associatedPartsSearchField.setText("");
    }
    public void initialize(){
        allPartsPartId.setCellValueFactory(cellData -> cellData.getValue().partIdProperty().asObject());
        allPartsPartName.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        allPartsInv.setCellValueFactory(cellData -> cellData.getValue().partInStockProperty().asObject());
        allPartsPrice.setCellValueFactory(cellData -> cellData.getValue().partPriceProperty().asObject());
        allPartsTableView.setItems(tempAllParts);
        
        associatedPartsPartId.setCellValueFactory(cellData -> cellData.getValue().partIdProperty().asObject());
        associatedPartsName.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        associatedPartsInv.setCellValueFactory(cellData -> cellData.getValue().partInStockProperty().asObject());
        associatedPartsPrice.setCellValueFactory(cellData -> cellData.getValue().partPriceProperty().asObject());
        associatedPartsTableView.setItems(tempAssociatedParts);
        
    }
}
