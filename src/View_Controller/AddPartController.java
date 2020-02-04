package View_Controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Model.InHousePart;
import Model.OutSourcedPart;
import Model.Inventory;
import Model.Part;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class AddPartController {
    private boolean isInHousePart = true;
    @FXML
    private TextField addPartNameField;
    @FXML
    private TextField addPartInvField;
    @FXML
    private TextField addPartPriceField;
    @FXML
    private TextField addPartMaxField;
    @FXML
    private TextField addPartMinField;
    @FXML
    private Text addPartMachineIdCompanyNameText;
    @FXML
    private TextField addPartMachineIdCompanyNameField;
    @FXML
    private Button addPartSaveButton;
    @FXML
    private Button addPartCancelButton;
    @FXML
    void addPartCancelButtonClicked(ActionEvent event) throws IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("You are about to cancel creating a new part");
        alert.setContentText("Are you sure?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            Stage stage = (Stage) addPartCancelButton.getScene().getWindow();
            Parent addPart = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene scene = new Scene(addPart);
            stage.setScene(scene);
            stage.show();
        }
    }
    @FXML
    void addPartSaveButtonClicked(ActionEvent event) throws IOException {
        int machineId;
        String companyName;
        
        String name = addPartNameField.getText();
        String inv = addPartInvField.getText();
        String cost = addPartPriceField.getText();
        String max = addPartMaxField.getText();
        String min = addPartMinField.getText();
        try{
            Integer parsedInv = Integer.parseInt(inv);
            Double parsedCost = Double.parseDouble(cost);
            Integer parsedMax = Integer.parseInt(max);
            Integer parsedMin = Integer.parseInt(min);
            
            String isPartValid = Part.partValidation(name,parsedCost,parsedInv,parsedMax,parsedMin);
            if(isPartValid == null){
                if(isInHousePart == true){
                    machineId = Integer.parseInt(addPartMachineIdCompanyNameField.getText());
                    InHousePart inHouse = new InHousePart();
                    int partId = Part.getPartIdCount();
                    inHouse.setPartId(partId);
                    inHouse.setName(name);
                    inHouse.setInStock(parsedInv);
                    inHouse.setPrice(parsedCost);
                    inHouse.setMax(parsedMax);
                    inHouse.setMin(parsedMin);
                    inHouse.setMachineId(machineId);
                    Inventory.addPart(inHouse);
                }else{
                    companyName = addPartMachineIdCompanyNameField.getText();
                    OutSourcedPart outSourced = new OutSourcedPart();
                    int partId = Part.getPartIdCount();
                    outSourced.setPartId(partId);
                    outSourced.setName(name);
                    outSourced.setInStock(parsedInv);
                    outSourced.setPrice(parsedCost);
                    outSourced.setMax(parsedMax);
                    outSourced.setMin(parsedMin);
                    outSourced.setCompanyName(companyName);
                    Inventory.addPart(outSourced);
                    }
                    Stage stage = (Stage) addPartSaveButton.getScene().getWindow();
                    Parent saved = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                    Scene scene = new Scene(saved);
                    stage.setScene(scene);
                    stage.show();   
                }
        else{
            Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Part Error Dialog");
                alert.setHeaderText("Error Cannot add part");
                alert.setContentText(isPartValid);
                alert.showAndWait();
        }
        }
        catch(NumberFormatException e){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Part Error Dialog");
            alert.setHeaderText("Error Cannot add part");
            alert.setContentText("Please fill in any empty fields");
            alert.showAndWait();     
        } 
    }
    @FXML
    void addPartToggleHandler(ActionEvent event) throws IOException {
        String eventSource = event.getSource().toString();
        if(eventSource.contains("In-House")){
            isInHousePart = true;
            addPartMachineIdCompanyNameField.setPromptText("Machine ID");
            addPartMachineIdCompanyNameText.setText("Machine ID        ");
        }
        else if(eventSource.contains("Outsourced")){
            isInHousePart = false;
            addPartMachineIdCompanyNameField.setPromptText("Company Name");
            addPartMachineIdCompanyNameText.setText("Company Name     ");
        }
    }
}
