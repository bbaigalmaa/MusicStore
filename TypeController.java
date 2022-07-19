package application;

import java.sql.Connection;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class TypeController {

    @FXML
    private TextField txtFieldName;

    @FXML
    private Button btnOk;

    @FXML
    private Button btnCancel;

    @FXML
    void clickCancel(ActionEvent event) {
    	ItemEditorController.getStageType().close();
    }

    @FXML
    void clickOk(ActionEvent event) {
    	if(txtFieldName.getText().isEmpty()){
    		Alert alert = new Alert(AlertType.INFORMATION);
    		alert.setContentText("Enter type name");
    		alert.show();
    	}else {
    		try {
				Connection c = DBConnect.connect();
				
				String sql = "INSERT INTO type (type_name) values('" + txtFieldName.getText() + "');";
				
				c.createStatement().execute(sql);
				
				ItemEditorController controller = mainController.loaderItemEditor.getController();
				controller.initialize();
				
				mainController control = Main.loader.getController();
				control.initialize();
				
				txtFieldName.setText("");
				Alert alert = new Alert(AlertType.INFORMATION);
	    		alert.setContentText("Type saved.");
	    		alert.show();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    	}
    }

}
