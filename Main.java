package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;


public class Main extends Application {
	static Stage stage;
	static FXMLLoader loader;
	@Override
	public void start(Stage primaryStage) {
		try {
			 loader = new FXMLLoader();
	         loader.setLocation(Main.class.getResource("interface.fxml"));
	         BorderPane personOverview = (BorderPane) loader.load();
	         stage  = new Stage();
	         stage.setTitle("ABC");
	         stage.setScene(new Scene(personOverview));
	         stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public static Stage getStage(){
		return stage;
	}
}
