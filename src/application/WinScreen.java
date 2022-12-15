package application;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

public class WinScreen {
	// win stage, scene, parent root
	Stage winStage;
	Parent root;
	Scene winScene;
	
	// menyimpan winner
	static String winner;
	
	// konstruktor
	public WinScreen() throws IOException {
		winStage = new Stage();
		root = FXMLLoader.load(getClass().getResource("WinScreen.fxml"));
		winScene = new Scene(root);
		
		
	}

	// launch win screen
	public void createWinScreen() {
		
		winStage.setScene(winScene);
		winStage.setTitle("Connect 4");
		winStage.show();
	}
	
	// setter dan getteruntuk winner
	public static void setWinner(String winner) {
		WinScreen.winner = winner;
	}
	
	public static String getWinner() {
		return winner;
	}
	
	
   

}
