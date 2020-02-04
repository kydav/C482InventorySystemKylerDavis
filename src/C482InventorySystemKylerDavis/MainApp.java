package C482InventorySystemKylerDavis;

import View_Controller.MainScreenController;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
/**
 * @author kylerdavis
 */
public class MainApp extends Application {

    private Stage primaryStage;
    private AnchorPane MainScreenApp;
    
    public void showMainScreenApp(){
        try{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("/View_Controller/MainScreen.fxml"));
        AnchorPane MainScreenApp = (AnchorPane) loader.load();
        
        Scene scene = new Scene(MainScreenApp);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        MainScreenController controller = loader.getController();
        controller.setMainApp(this);
    } catch (IOException e) {
        e.printStackTrace();
}
    }
    
    @Override
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Inventory System");
        showMainScreenApp();
    }
        
public Stage getPrimaryStage() {
    return primaryStage;
}
   
    public static void main(String[] args) {
        launch(args);
    }
}
