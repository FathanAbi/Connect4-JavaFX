package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


// class controller untuk menu screen
public class MenuController extends Main{
	Stage menuStage;
    @FXML
    private Button menuExitButton;

    @FXML
    private Button menuPlayButton;
    
    @FXML
    private AnchorPane menuPane;

    
    // exit button, exit menu screen
    @FXML
    void menuExitButtonOnPressed(ActionEvent event) {
    	menuStage = (Stage) menuPane.getScene().getWindow();
    	menuStage.close();
    }
    
    
    // play button, masuk ke game screen
    @FXML
    void menuPlayButtonOnPressed(ActionEvent event) {
    	// close menu screen
    	menuStage = (Stage) menuPane.getScene().getWindow();
    	menuStage.close();
    	
    	// buka dan launch game
    	Game game = new Game();
    	game.createGame();
    	
    }

}
