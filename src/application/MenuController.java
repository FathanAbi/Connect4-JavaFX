package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MenuController extends Main{
	Stage menuStage;
    @FXML
    private Button menuExitButton;

    @FXML
    private Button menuPlayButton;
    
    @FXML
    private AnchorPane menuPane;

    @FXML
    void menuExitButtonOnPressed(ActionEvent event) {
    	System.out.println("Exited");
    	menuStage = (Stage) menuPane.getScene().getWindow();
    	menuStage.close();
    	
    	
    }

    @FXML
    void menuPlayButtonOnPressed(ActionEvent event) {
    	System.out.println("played");
    	
    }

}
