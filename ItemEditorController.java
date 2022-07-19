package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ItemEditorController {

    @FXML
    private TextField ItemNumber;

    @FXML
    private TextField itemName;

    @FXML
    private TextField unitPrice;

    @FXML
    private TextField picture;

    @FXML
    private ComboBox<String> cbox_category;

    @FXML
    private ComboBox<String> cbox_type;

    @FXML
    private Button btn_newCategory;

    @FXML
    private Button btn_newItemType;

    @FXML
    private Button btn_create;

    @FXML
    private Button btn_close;

    @FXML
    private ImageView imageView;
    
    static Stage primaryStage;
    static Stage stageCat;
    static Stage stageType;
    static ObservableList<String> list1 = FXCollections.observableArrayList();
    static ObservableList<String> list2 = FXCollections.observableArrayList();
    @FXML
    void initialize(){
    	
    	try {
			Connection c = DBConnect.connect();
			 int r = getRandomNumber(100000, 999999);
			 String sql = "SELECT * FROM storeitem WHERE itemNumber = " + r ;
			ResultSet rs = c.createStatement().executeQuery(sql);
			
			while(rs.next()){
				r = getRandomNumber(100000, 999999);
				sql = "SELECT * FROM storeitem WHERE itemNumber = " + r ;
				rs = c.createStatement().executeQuery(sql);
			}
			ItemNumber.setText(String.valueOf(r));
			
			sql = "SELECT * FROM category";
			rs = c.createStatement().executeQuery(sql);
			
			if(rs.next()){
					list1 = setDataCat(rs);
					cbox_category.setItems(list1);
			}
					
				
				//setData(list1);
				
			
			
			
			sql = "SELECT * FROM type";
			rs = c.createStatement().executeQuery(sql);
			if(rs.next()){
				list2 = setDataType(rs);
				cbox_type.setItems(list2);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static ObservableList<String> setDataCat(ResultSet rs){
    	ObservableList<String> list = FXCollections.observableArrayList();
		try {
			while(rs.next()){
				list.add(rs.getString("name"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
    	
    }
    
    public static ObservableList<String> setDataType(ResultSet rs){
    	ObservableList<String> list = FXCollections.observableArrayList();
		try {
			while(rs.next()){
				list.add(rs.getString("type_name"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
    	
    }

    @FXML
    void handle_close(ActionEvent event) {
    	mainController.getStage().close();
    	Main.getStage().show();
    }

    @FXML
    void handle_create(ActionEvent event) {
    	if(unitPrice.getText().isEmpty()|| itemName.getText().isEmpty()||cbox_type.getValue()==null||picture.getText().isEmpty()){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setContentText("Please, Fill all field");
			alert.show();
		}else {
			String number = ItemNumber.getText();
			double price = Double.parseDouble(unitPrice.getText());
			String name = itemName.getText();
			String category = cbox_category.getValue().toString();
			String type = cbox_type.getValue().toString();
			String pic = picture.getText();
			
			try {
				Connection c = DBConnect.connect();
				String sql = "INSERT INTO storeitem (itemNumber, category, type, itemName, unitPrice, path)"
						+ " values ('"+ number +"', '" + category + "','" + type + "','" + name + "'," +price + ",'" + picture.getText() + "')";
				c.createStatement().execute(sql);
				
				unitPrice.setText("");
				itemName.setText("");
				picture.setText("");
				int r = getRandomNumber(100000, 999999);
				sql = "SELECT * FROM storeitem WHERE itemNumber = " + r ;
				ResultSet rs = c.createStatement().executeQuery(sql);
				
				while(rs.next()){
					r = getRandomNumber(100000, 999999);
					sql = "SELECT * FROM items WHERE itemNumber = " + r ;
					rs = c.createStatement().executeQuery(sql);
				}
				ItemNumber.setText(String.valueOf(r));
				
				Alert alert = new Alert(AlertType.WARNING);
				alert.setContentText("SuccessFully create");
				alert.show();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
    }

    @FXML
    void handle_newCategory(ActionEvent event) {
    	try{
    		FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("CategoryEditor.fxml"));
            GridPane personOverview = (GridPane) loader.load();
            stageCat  = new Stage();
            stageCat.setTitle("Add Category");
            stageCat.setScene(new Scene(personOverview));
            stageCat.show();
    	} catch(Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void handle_newItemType(ActionEvent event) {
    	try{
    		FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("TypeInterface.fxml"));
            GridPane personOverview = (GridPane) loader.load();
            stageType  = new Stage();
            stageType.setTitle("Type Editor");
            stageType.setScene(new Scene(personOverview));
            stageType.show();
    	} catch(Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void handle_picture(ActionEvent event) {
    	FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showOpenDialog(mainController.primaryStage.getScene().getWindow());
		if (file != null) {
			printLog(picture, file);
			openFile(file);
		}
    }
    
    private void printLog(TextField textArea, File files) {
		if (files == null) 
			return;
		else 
			textArea.setText(files.getAbsolutePath());
		
	}
    
    private void openFile(File file) {
			Image image = new Image(file.toURI().toString());
			imageView.setImage(image);
	}

    private static int getRandomNumber(int min, int max) {

    	Random r = new Random();
    	return r.nextInt((max - min) + 1) + min;
   }

    static Stage getStageCat(){
    	return stageCat;
    }
    static Stage getStageType(){
    	return stageType;
    }
    static Stage getStage(){
    	return primaryStage;
    }
}
