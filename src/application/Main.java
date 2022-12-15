package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


// Main class
public class Main extends Application {
	@Override
	public void start(Stage Stage) {
		try {
			// buat dan launch menu screen
			
			// scene dan root node
			Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
			Scene menuScene = new Scene(root);  
			
			// set window
			Stage.setScene(menuScene);
			Stage.setTitle("Connect 4");
			Stage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
