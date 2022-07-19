package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;

public class CategoryController {

    @FXML
    private TextField txtFieldName;

    @FXML
    private Button btnOk;

    @FXML
    private Button btnCancel;

    @FXML
    void clickCancel(ActionEvent event) {
		ItemEditorController.getStageCat().close();
		
    }

    @FXML
    void clickOk(ActionEvent event) {
    	if(txtFieldName.getText().isEmpty()){
    		Alert alert = new Alert(AlertType.INFORMATION);
    		alert.setContentText("Enter category name");
    		alert.show();
    	}else {
    		try {
				Connection c = DBConnect.connect();
				String sql = "SELECT * FROM category WHERE name='" + txtFieldName.getText() +"'";
				ResultSet rs = c.createStatement().executeQuery(sql);
				
				if(rs.next()){
					Alert alert = new Alert(AlertType.INFORMATION);
		    		alert.setContentText("Duplicate item name.");
		    		alert.show();
				}else{
					sql = "INSERT INTO category (name) values('" + txtFieldName.getText() + "');";
					
					c.createStatement().execute(sql);
					
//					sql = "SELECT * FROM category";
//					ResultSet rs = c.createStatement().executeQuery(sql);
//					
//					ObservableList<String> list = ItemEditorController.setDataCat(rs);
//					
					ItemEditorController controller = mainController.loaderItemEditor.getController();
					controller.initialize();
					
					mainController control = Main.loader.getController();
					control.initialize();
					
					txtFieldName.setText("");
					Alert alert = new Alert(AlertType.INFORMATION);
		    		alert.setContentText("Category name saved.");
		    		alert.show();
				}
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    	}
    }

}
