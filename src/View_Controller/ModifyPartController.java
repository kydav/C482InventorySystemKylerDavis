package View_Controller;

import Model.Inventory;
import Model.InHousePart;
import static Model.Inventory.getAllParts;
import Model.OutSourcedPart;
import Model.Part;
import static View_Controller.MainScreenController.partToModify;
import java.io.IOException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ModifyPartController {
    private boolean isInHousePart;
    @FXML
    private TextField modifyPartIdField;
    @FXML
    private TextField modifyPartNameField;
    @FXML
    private TextField modifyPartInvField;
    @FXML
    private TextField modifyPartPriceField;
    @FXML
    private TextField modifyPartMaxField;
    @FXML
    private TextField modifyPartMinField;
    @FXML
    private Button modifyPartSaveButton;
    @FXML
    private Button modifyPartCancelButton;
    @FXML
    private RadioButton modifyPartInHouse;
    @FXML
    private TextField modifyPartMachineIdField;
    @FXML
    private Text modifyPartMachineIdCompanyNameText;
    @FXML
    private RadioButton modifyPartOutsourced;
    @FXML
    void modfyPartCancelButtonClicked(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("You are about to cancel editing a part");
        alert.setContentText("Are you sure?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            Stage stage = (Stage) modifyPartCancelButton.getScene().getWindow();
            Parent cancelModifyProduct = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene scene = new Scene(cancelModifyProduct);
            stage.setScene(scene);
            stage.show();
        }
    }
    @FXML
    void modifyPartSaveButtonClicked(ActionEvent event) throws IOException {
        int partId = Integer.parseInt(modifyPartIdField.getText());
        int machineId;
        String companyName;
        String name = modifyPartNameField.getText();
        String inv = modifyPartInvField.getText();
        String cost = modifyPartPriceField.getText();
        String max = modifyPartMaxField.getText();
        String min = modifyPartMinField.getText();
        try{
            Integer parsedInv = Integer.parseInt(inv);
            Double parsedCost = Double.parseDouble(cost);
            Integer parsedMax = Integer.parseInt(max);
            Integer parsedMin = Integer.parseInt(min);
            
            String isPartValid = Part.partValidation(name,parsedCost,parsedInv,parsedMax,parsedMin);
            if(isPartValid == null){
                if(isInHousePart == true){
                    machineId = Integer.parseInt(modifyPartMachineIdField.getText());
                    InHousePart inHouse = new InHousePart();
                    inHouse.setPartId(partId);
                    inHouse.setName(name);
                    inHouse.setInStock(parsedInv);
                    inHouse.setPrice(parsedCost);
                    inHouse.setMax(parsedMax);
                    inHouse.setMin(parsedMin);
                    inHouse.setMachineId(machineId);
                    Inventory.updatePart(partId-1, inHouse);
                }else{
                    companyName = modifyPartMachineIdField.getText();
                    OutSourcedPart outSourced = new OutSourcedPart();
                    outSourced.setPartId(partId);
                    outSourced.setName(name);
                    outSourced.setInStock(parsedInv);
                    outSourced.setPrice(parsedCost);
                    outSourced.setMax(parsedMax);
                    outSourced.setMin(parsedMin);
                    outSourced.setCompanyName(companyName);
                    Inventory.updatePart(partId-1, outSourced);
                    }
                    Stage stage = (Stage) modifyPartSaveButton.getScene().getWindow();
                    Parent saved = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                    Scene scene = new Scene(saved);
                    stage.setScene(scene);
                    stage.show();   
                }
        else{
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Part Error Dialog");
            alert.setHeaderText("Error Cannot modify part");
            alert.setContentText(isPartValid);
            alert.showAndWait();
        }
        }
        catch(NumberFormatException e){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Part Error Dialog");
            alert.setHeaderText("Error Cannot modify part");
            alert.setContentText("Please fill in any empty fields");
            alert.showAndWait();  
        }
    }

    @FXML
    void modifyPartToggleHandler(ActionEvent event){
        String eventSource = event.getSource().toString();
        if(eventSource.contains("In-House")){
            isInHousePart = true;
            modifyPartMachineIdField.setPromptText("Machine ID");
            modifyPartMachineIdCompanyNameText.setText("Machine ID        ");
        }
        else if(eventSource.contains("Outsourced")){
            isInHousePart = false;
            modifyPartMachineIdField.setPromptText("Company Name");
            modifyPartMachineIdCompanyNameText.setText("Company Name     ");
        }
    }
    public void initialize(){
        Part newPartToModify = getAllParts().get(partToModify.getPartId()-1);
        modifyPartIdField.setText(String.valueOf(newPartToModify.getPartId()));
        modifyPartNameField.setText(newPartToModify.getName());
        modifyPartInvField.setText(String.valueOf(newPartToModify.getInStock()));
        modifyPartPriceField.setText(String.valueOf(newPartToModify.getPrice()));
        modifyPartMaxField.setText(String.valueOf(newPartToModify.getMax()));
        modifyPartMinField.setText(String.valueOf(newPartToModify.getMin()));
        if(newPartToModify instanceof InHousePart){
            modifyPartMachineIdField.setText(String.valueOf(((InHousePart) newPartToModify).getMachineId())); 
            modifyPartInHouse.setSelected(true);
            modifyPartMachineIdCompanyNameText.setText("Machine ID        ");
            isInHousePart = true;
        }else{
            modifyPartMachineIdField.setText(String.valueOf(((OutSourcedPart) newPartToModify).getCompanyName()));
            modifyPartOutsourced.setSelected(true);
            modifyPartMachineIdCompanyNameText.setText("Company Name     ");
            isInHousePart = false;
        }
    }
}
