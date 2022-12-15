package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

// kelas win screen kontriller
public class WinScreenController implements Initializable{
	Stage menuStage = new Stage();
	Stage winStage = new Stage();

    @FXML
    private Button backToMenuButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button restartButton;

    @FXML
    private AnchorPane winPane;
    

    @FXML
    private Text winnerText;

    
    
    
    
   
    // tombol kembali ke menu
    @FXML
    void backToMenuButtonPRessed(ActionEvent event) {
    	winStage = (Stage) winPane.getScene().getWindow();
    	winStage.close();
    	
    	Main main = new Main();
        main.start(menuStage);
    }
    
    
    // tombol exit game
    @FXML
    void exitButtonPressed(ActionEvent event) {
    	winStage = (Stage) winPane.getScene().getWindow();
    	winStage.close();
    }

    // tombol restart game
    @FXML
    void restartButtonPressed(ActionEvent event) {
    	winStage = (Stage) winPane.getScene().getWindow();
    	winStage.close();
    	
    	Game game = new Game();
    	game.createGame();
    }

    // initialisasi
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// tampilkan pemenang
		winnerText.setText("Winner " + WinScreen.getWinner());
	}

}
