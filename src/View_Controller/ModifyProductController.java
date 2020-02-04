package View_Controller;

import Model.Inventory;
import static Model.Inventory.getAllParts;
import static Model.Inventory.getAllProducts;
import Model.Part;
import Model.Product;
import static Model.Product.getAssociatedParts;
import static View_Controller.MainScreenController.productToModify;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ModifyProductController {
    private ObservableList<Part> tempAssociatedParts = FXCollections.observableArrayList(getAssociatedParts());
    private ObservableList<Part> tempAllParts = FXCollections.observableArrayList(getAllParts());
    private double totalCostOfParts;
    @FXML
    private TextField modifyProductIdField;
    @FXML
    private TextField modifyProductNameField;
    @FXML
    private TextField modifyProductInvField;
    @FXML
    private TextField modifyProductPriceField;
    @FXML
    private TextField modifyProductMaxField;
    @FXML
    private TextField modifyProductMinField;
    @FXML
    private Button modifyProductSaveButton;
    @FXML
    private Button modifyProductCancelButton;
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
    void modifyProductAddButtonClicked(ActionEvent event) {
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
    void ModifyProductCancelButtonClicked(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("You are about to cancel editing a product");
        alert.setContentText("Are you sure?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            Stage stage = (Stage) modifyProductCancelButton.getScene().getWindow();
            Parent cancelModifyPart = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene scene = new Scene(cancelModifyPart);
            stage.setScene(scene);
            stage.show();
        }
    }
    @FXML
    void modifyProductDeleteButtonClicked(ActionEvent event) {
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
    void modifyProductSaveButtonClicked(ActionEvent event) throws IOException {
        int productId = Integer.parseInt(modifyProductIdField.getText());
        String name = modifyProductNameField.getText();
        String inv = modifyProductInvField.getText();
        String cost = modifyProductPriceField.getText();
        String max = modifyProductMaxField.getText();
        String min = modifyProductMinField.getText();
        int associatedParts = tempAssociatedParts.size();
        try{
            Integer parsedInv = Integer.parseInt(inv);
            Double parsedCost = Double.parseDouble(cost);
            Integer parsedMax = Integer.parseInt(max);
            Integer parsedMin = Integer.parseInt(min);
            
            String isProductValid = Product.productValidation(name, parsedCost, parsedInv, parsedMax, parsedMin, associatedParts, totalCostOfParts);
            if(isProductValid == null){
                Product product = new Product();
                product.setProductId(productId);
                product.setName(name);
                product.setInStock(parsedInv);
                product.setPrice(parsedCost);
                product.setMax(parsedMax);
                product.setMin(parsedMin);
                product.setAssociatedParts(tempAssociatedParts);
                Inventory.updateProduct(productId-1, product);
        
                Stage stage = (Stage) modifyProductSaveButton.getScene().getWindow();
                Parent saved = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene scene = new Scene(saved);
                stage.setScene(scene);
                stage.show();
        }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Part Error Dialog");
                alert.setHeaderText("Error Cannot add product");
                alert.setContentText(isProductValid);
                alert.showAndWait();
            }
        }
        catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
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
        associatedPartsTableView.setItems(tempAllParts);
        associatedPartsSearchField.setText("");
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
        tempAllParts.removeAll(tempAssociatedParts);
        Product newProductToModify = getAllProducts().get(productToModify.getProductId()-1);
        modifyProductIdField.setText(String.valueOf(newProductToModify.getProductId()));
        modifyProductNameField.setText(newProductToModify.getName());
        modifyProductInvField.setText(String.valueOf(newProductToModify.getInStock()));
        modifyProductPriceField.setText(String.valueOf(newProductToModify.getPrice()));
        modifyProductMaxField.setText(String.valueOf(newProductToModify.getMax()));
        modifyProductMinField.setText(String.valueOf(newProductToModify.getMin()));
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
        tempAssociatedParts.forEach((p) -> {
            totalCostOfParts += p.getPrice();
        });
        
    }
}
