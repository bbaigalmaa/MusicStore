package application;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Random;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class mainController {

	@FXML
	private ImageView imgView;

	@FXML
	private TableView<tableData> tableView;

	@FXML
	private TableColumn<?, ?> tcItemIndex;

	@FXML
	private TableColumn<?, ?> tcItemName;

	@FXML
	private TableColumn<?, ?> tcItemUnitPrice;

	@FXML
	private ComboBox<String> cboxItemCategory;

	@FXML
	private ComboBox<String> cboxItemType;

	@FXML
	private Button btnNewStoreItem;

	@FXML
	private TextField tboxItemTotal;

	@FXML
	private TextField tboxTaxRate;

	@FXML
	private TextField tboxTaxAmount;

	@FXML
	private TextField tboxOrderTotal;

	@FXML
	private TextField tboxReceipt;

	@FXML
	private Button btnSave;

	@FXML
	private Button btnOpen;

	@FXML
	private Button btnNewOrder;

	@FXML
	private TextField tboxItem1;

	@FXML
	private TextField tboxDesc1;

	@FXML
	private TextField tboxUnitPrice1;

	@FXML
	private TextField tboxSubTotal1;

	@FXML
	private TextField tboxQty1;

	@FXML
	private TextField tboxItem2;

	@FXML
	private TextField tboxItem3;

	@FXML
	private TextField tboxItem4;

	@FXML
	private TextField tboxItem5;

	@FXML
	private TextField tboxItem6;

	@FXML
	private TextField tboxDesc2;

	@FXML
	private TextField tboxDesc3;

	@FXML
	private TextField tboxDesc4;

	@FXML
	private TextField tboxDesc5;

	@FXML
	private TextField tboxDesc6;

	@FXML
	private TextField tboxUnitPrice2;

	@FXML
	private TextField tboxUnitPrice3;

	@FXML
	private TextField tboxUnitPrice4;

	@FXML
	private TextField tboxUnitPrice5;

	@FXML
	private TextField tboxUnitPrice6;

	@FXML
	private TextField tboxQty2;

	@FXML
	private TextField tboxQty3;

	@FXML
	private TextField tboxQty4;

	@FXML
	private TextField tboxQty5;

	@FXML
	private TextField tboxQty6;

	@FXML
	private TextField tboxSubTotal2;

	@FXML
	private TextField tboxSubTotal3;

	@FXML
	private TextField tboxSubTotal4;

	@FXML
	private TextField tboxSubTotal5;

	@FXML
	private Button btnRemove1;

	@FXML
	private Button btnRemove2;

	@FXML
	private Button btnRemove3;

	@FXML
	private Button btnRemove4;

	@FXML
	private Button btnRemove5;

	@FXML
	private Button btnRemove6;

	@FXML
	private TextField tboxSubTotal6;
	static Stage primaryStage;
	static FXMLLoader loaderItemEditor;
	static ObservableList<tableData> listRent;

	private static int getRandomNumber(int min, int max) {

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

	@FXML
	void initialize(){
		try {

			tboxTaxRate.setText("7.75");
			tboxReceipt.setText(String.valueOf(getRandomNumber(1000, 9999)));
			Connection c =DBConnect.connect();

			String sql = "SELECT * FROM category";
			ResultSet rs = c.createStatement().executeQuery(sql);
			ObservableList<String> list = FXCollections.observableArrayList();
			ObservableList<String> list1 = FXCollections.observableArrayList();
			while(rs.next()){
				list.add(rs.getString("name"));
			}
			cboxItemCategory.setItems(list);

			sql = "SELECT * FROM type";
			rs = c.createStatement().executeQuery(sql);

			while(rs.next()){
				list1.add(rs.getString("type_name"));
			}
			cboxItemType.setItems(list1);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	void changed_category(ActionEvent event) {

		cboxItemCategory.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				String name;
				Connection c;
				// TODO Auto-generated method stub
				if(cboxItemType.getValue()!=null){
					try {
						c = DBConnect.connect();
						name = cboxItemCategory.getValue();
						String name2 = cboxItemType.getValue();
						ObservableList<tableData> list = FXCollections.observableArrayList();
						String sql = "SELECT * FROM storeitem WHERE category='" + name + "' AND type='" + name2 + "'";
						ResultSet rs = c.createStatement().executeQuery(sql);

						while(rs.next()){
							tableData data = new tableData();
							data.setName(rs.getString("itemName"));
							data.setNumber(rs.getString("itemNumber"));
							data.setUnitPrice(rs.getDouble("unitPrice"));
							data.setPath(rs.getString("path"));

							list.add(data);
							tcItemIndex.setCellValueFactory(new PropertyValueFactory<>("number"));
							tcItemName.setCellValueFactory(new PropertyValueFactory<>("name"));
							tcItemUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

							//							File file = new File(rs.getString("path"));
							//							Image image = new Image(file.toURI().toString());
							//							imgView.setImage(image);
						}
						tableView.setItems(list);

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});	
	}

	@FXML
	void changed_type(ActionEvent event) {
		cboxItemType.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				String name;
				Connection c;
				// TODO Auto-generated method stub
				if(!cboxItemCategory.getValue().isEmpty()){
					try {
						c = DBConnect.connect();
						name = cboxItemType.getValue();
						String name2 = cboxItemCategory.getValue();
						ObservableList<tableData> list = FXCollections.observableArrayList();
						String sql = "SELECT * FROM storeitem WHERE type='" + name + "' AND category='" + name2 + "'";
						ResultSet rs = c.createStatement().executeQuery(sql);

						while(rs.next()){
							tableData data = new tableData();
							data.setName(rs.getString("itemName"));
							data.setNumber(rs.getString("itemNumber"));
							data.setUnitPrice(rs.getDouble("unitPrice"));
							data.setPath(rs.getString("path"));


							list.add(data);
							tcItemIndex.setCellValueFactory(new PropertyValueFactory<>("number"));
							tcItemName.setCellValueFactory(new PropertyValueFactory<>("name"));
							tcItemUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

						}
						tableView.setItems(list);

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});	
	}

	@FXML
	void handle_Close(ActionEvent event) {
		System.exit(0);
	}

	@FXML
	void handle_NewOrder(ActionEvent event) {
		
	}

	@FXML
	void handle_NewStoreItem(ActionEvent event) {
		try {
			loaderItemEditor = new FXMLLoader();
			loaderItemEditor.setLocation(Main.class.getResource("ItemEditor.fxml"));
			GridPane personOverview = (GridPane) loaderItemEditor.load();
			primaryStage  = new Stage();
			primaryStage.setTitle("ABC");
			primaryStage.setScene(new Scene(personOverview));
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void handle_Remove1(ActionEvent event) {
		int number = Integer.valueOf(tboxQty1.getText());

		if(number > 1){
			number--;
			tboxSubTotal1.setText(String.format("%.2f",Double.parseDouble(tboxSubTotal1.getText())-Double.parseDouble(tboxUnitPrice1.getText())));
			tboxQty1.setText(String.valueOf(number));
		}else if(!tboxItem2.getText().isEmpty()){
			tboxItem1.setText(tboxItem2.getText());
			tboxDesc1.setText(tboxDesc2.getText());
			tboxUnitPrice1.setText(tboxUnitPrice2.getText());
			tboxQty1.setText(tboxQty2.getText());
			tboxSubTotal1.setText(tboxSubTotal2.getText());
			if(!tboxItem3.getText().isEmpty()){
				tboxItem2.setText(tboxItem3.getText());
				tboxDesc2.setText(tboxDesc3.getText());
				tboxUnitPrice2.setText(tboxUnitPrice3.getText());
				tboxQty2.setText(tboxQty3.getText());
				tboxSubTotal2.setText(tboxSubTotal3.getText());
				if(!tboxItem4.getText().isEmpty()){
					tboxItem3.setText(tboxItem4.getText());
					tboxDesc3.setText(tboxDesc4.getText());
					tboxUnitPrice3.setText(tboxUnitPrice4.getText());
					tboxQty3.setText(tboxQty4.getText());
					tboxSubTotal3.setText(tboxSubTotal4.getText());
					if(!tboxItem5.getText().isEmpty()){
						tboxItem4.setText(tboxItem5.getText());
						tboxDesc4.setText(tboxDesc5.getText());
						tboxUnitPrice4.setText(tboxUnitPrice5.getText());
						tboxQty4.setText(tboxQty5.getText());
						tboxSubTotal4.setText(tboxSubTotal5.getText());
						if(!tboxItem6.getText().isEmpty()){
							tboxItem5.setText(tboxItem6.getText());
							tboxDesc5.setText(tboxDesc6.getText());
							tboxUnitPrice5.setText(tboxUnitPrice6.getText());
							tboxQty5.setText(tboxQty6.getText());
							tboxSubTotal5.setText(tboxSubTotal6.getText());
							tboxItem6.setText("");
							tboxDesc6.setText("");
							tboxUnitPrice6.setText("");
							tboxQty6.setText("");
							tboxSubTotal6.setText("");
						}else {
							tboxItem5.setText("");
							tboxDesc5.setText("");
							tboxUnitPrice5.setText("");
							tboxQty5.setText("");
							tboxSubTotal5.setText("");
						}
					}else {
						tboxItem4.setText("");
						tboxDesc4.setText("");
						tboxUnitPrice4.setText("");
						tboxQty4.setText("");
						tboxSubTotal4.setText("");
					}
				}else {
					tboxItem3.setText("");
					tboxDesc3.setText("");
					tboxUnitPrice3.setText("");
					tboxQty3.setText("");
					tboxSubTotal3.setText("");
				}

			}else {
				tboxItem2.setText("");
				tboxDesc2.setText("");
				tboxUnitPrice2.setText("");
				tboxQty2.setText("");
				tboxSubTotal2.setText("");
			}
		}else {
			tboxItem1.setText("");
			tboxDesc1.setText("");
			tboxUnitPrice1.setText("");
			tboxQty1.setText("");
			tboxSubTotal1.setText("");
		}

		double total = 0;
		if(!tboxSubTotal1.getText().isEmpty())
			total+=Double.parseDouble(tboxSubTotal1.getText());
		if(!tboxSubTotal2.getText().isEmpty())
			total+=Double.parseDouble(tboxSubTotal2.getText());
		if(!tboxSubTotal3.getText().isEmpty())
			total+=Double.parseDouble(tboxSubTotal3.getText());
		if(!tboxSubTotal4.getText().isEmpty())
			total+=Double.parseDouble(tboxSubTotal4.getText());
		if(!tboxSubTotal5.getText().isEmpty())
			total+=Double.parseDouble(tboxSubTotal5.getText());
		if(!tboxSubTotal6.getText().isEmpty())
			total+=Double.parseDouble(tboxSubTotal6.getText());
		tboxItemTotal.setText(String.valueOf(total));

		tboxTaxAmount.setText(String.format("%.2f",(total*7.75)/100));

		tboxOrderTotal.setText(String.format("%.2f",Double.parseDouble(tboxItemTotal.getText()) + Double.parseDouble(tboxTaxAmount.getText())));

	}

	@FXML
	void handle_Remove2(ActionEvent event) {
		int number = Integer.valueOf(tboxQty2.getText());
		if(number > 1){
			number--;
			tboxSubTotal2.setText(String.format("%.2f",Double.parseDouble(tboxSubTotal2.getText())-Double.parseDouble(tboxUnitPrice2.getText())));
			tboxQty2.setText(String.valueOf(number));
		}else if(!tboxItem3.getText().isEmpty()){
			tboxItem2.setText(tboxItem3.getText());
			tboxDesc2.setText(tboxDesc3.getText());
			tboxUnitPrice2.setText(tboxUnitPrice3.getText());
			tboxQty2.setText(tboxQty3.getText());
			tboxSubTotal2.setText(tboxSubTotal3.getText());
			if(!tboxItem4.getText().isEmpty()){
				tboxItem3.setText(tboxItem4.getText());
				tboxDesc3.setText(tboxDesc4.getText());
				tboxUnitPrice3.setText(tboxUnitPrice4.getText());
				tboxQty3.setText(tboxQty4.getText());
				tboxSubTotal3.setText(tboxSubTotal4.getText());
				if(!tboxItem5.getText().isEmpty()){
					tboxItem4.setText(tboxItem5.getText());
					tboxDesc4.setText(tboxDesc5.getText());
					tboxUnitPrice4.setText(tboxUnitPrice5.getText());
					tboxQty3.setText(tboxQty5.getText());
					tboxSubTotal4.setText(tboxSubTotal5.getText());
					if(!tboxItem6.getText().isEmpty()){
						tboxItem5.setText(tboxItem6.getText());
						tboxDesc5.setText(tboxDesc6.getText());
						tboxUnitPrice5.setText(tboxUnitPrice6.getText());
						tboxQty5.setText(tboxQty6.getText());
						tboxSubTotal5.setText(tboxSubTotal6.getText());
						tboxItem6.setText("");
						tboxDesc6.setText("");
						tboxUnitPrice6.setText("");
						tboxQty6.setText("");
						tboxSubTotal6.setText("");
					}else {
						tboxItem5.setText("");
						tboxDesc5.setText("");
						tboxUnitPrice5.setText("");
						tboxQty5.setText("");
						tboxSubTotal5.setText("");
					}

				}else {
					tboxItem4.setText("");
					tboxDesc4.setText("");
					tboxUnitPrice4.setText("");
					tboxQty4.setText("");
					tboxSubTotal4.setText("");
				}

			}else {
				tboxItem3.setText("");
				tboxDesc3.setText("");
				tboxUnitPrice3.setText("");
				tboxQty3.setText("");
				tboxSubTotal3.setText("");
			}
		}else {
			tboxItem2.setText("");
			tboxDesc2.setText("");
			tboxUnitPrice2.setText("");
			tboxQty2.setText("");
			tboxSubTotal2.setText("");
		}

		double total = 0;
		if(!tboxSubTotal1.getText().isEmpty())
			total+=Double.parseDouble(tboxSubTotal1.getText());
		if(!tboxSubTotal2.getText().isEmpty())
			total+=Double.parseDouble(tboxSubTotal2.getText());
		if(!tboxSubTotal3.getText().isEmpty())
			total+=Double.parseDouble(tboxSubTotal3.getText());
		if(!tboxSubTotal4.getText().isEmpty())
			total+=Double.parseDouble(tboxSubTotal4.getText());
		if(!tboxSubTotal5.getText().isEmpty())
			total+=Double.parseDouble(tboxSubTotal5.getText());
		if(!tboxSubTotal6.getText().isEmpty())
			total+=Double.parseDouble(tboxSubTotal6.getText());
		tboxItemTotal.setText(String.valueOf(total));

		tboxTaxAmount.setText(String.format("%.2f",(total*7.75)/100));

		tboxOrderTotal.setText(String.format("%.2f",Double.parseDouble(tboxItemTotal.getText()) + Double.parseDouble(tboxTaxAmount.getText())));

	}

	@FXML
	void handle_Remove3(ActionEvent event) {
		int number = Integer.valueOf(tboxQty3.getText());
		if(number > 1){
			number--;
			tboxSubTotal3.setText(String.format("%.2f",Double.parseDouble(tboxSubTotal3.getText())-Double.parseDouble(tboxUnitPrice3.getText())));
			tboxQty3.setText(String.valueOf(number));
		}else if(!tboxItem4.getText().isEmpty()){
			tboxItem3.setText(tboxItem4.getText());
			tboxDesc3.setText(tboxDesc4.getText());
			tboxUnitPrice3.setText(tboxUnitPrice4.getText());
			tboxQty3.setText(tboxQty4.getText());
			tboxSubTotal3.setText(tboxSubTotal4.getText());
			if(!tboxItem5.getText().isEmpty()){
				tboxItem4.setText(tboxItem5.getText());
				tboxDesc4.setText(tboxDesc5.getText());
				tboxUnitPrice4.setText(tboxUnitPrice5.getText());
				tboxQty4.setText(tboxQty5.getText());
				tboxSubTotal4.setText(tboxSubTotal5.getText());
				if(!tboxItem6.getText().isEmpty()){
					tboxItem5.setText(tboxItem6.getText());
					tboxDesc5.setText(tboxDesc6.getText());
					tboxUnitPrice5.setText(tboxUnitPrice6.getText());
					tboxQty5.setText(tboxQty6.getText());
					tboxSubTotal5.setText(tboxSubTotal6.getText());
					tboxItem6.setText("");
					tboxDesc6.setText("");
					tboxUnitPrice6.setText("");
					tboxQty6.setText("");
					tboxSubTotal6.setText("");
				}else {
					tboxItem5.setText("");
					tboxDesc5.setText("");
					tboxUnitPrice5.setText("");
					tboxQty5.setText("");
					tboxSubTotal5.setText("");
				}

			}else {
				tboxItem4.setText("");
				tboxDesc4.setText("");
				tboxUnitPrice4.setText("");
				tboxQty4.setText("");
				tboxSubTotal3.setText("");
			}
		}else {
			tboxItem3.setText("");
			tboxDesc3.setText("");
			tboxUnitPrice3.setText("");
			tboxQty3.setText("");
			tboxSubTotal3.setText("");
		}

		double total = 0;
		if(!tboxSubTotal1.getText().isEmpty())
			total+=Double.parseDouble(tboxSubTotal1.getText());
		if(!tboxSubTotal2.getText().isEmpty())
			total+=Double.parseDouble(tboxSubTotal2.getText());
		if(!tboxSubTotal3.getText().isEmpty())
			total+=Double.parseDouble(tboxSubTotal3.getText());
		if(!tboxSubTotal4.getText().isEmpty())
			total+=Double.parseDouble(tboxSubTotal4.getText());
		if(!tboxSubTotal5.getText().isEmpty())
			total+=Double.parseDouble(tboxSubTotal5.getText());
		if(!tboxSubTotal6.getText().isEmpty())
			total+=Double.parseDouble(tboxSubTotal6.getText());
		tboxItemTotal.setText(String.valueOf(total));

		tboxTaxAmount.setText(String.format("%.2f",(total*7.75)/100));

		tboxOrderTotal.setText(String.format("%.2f",Double.parseDouble(tboxItemTotal.getText()) + Double.parseDouble(tboxTaxAmount.getText())));

	}

	@FXML
	void handle_Remove4(ActionEvent event) {
		int number = Integer.valueOf(tboxQty4.getText());
		if(number > 1){
			number--;
			tboxSubTotal4.setText(String.format("%.2f",Double.parseDouble(tboxSubTotal4.getText())-Double.parseDouble(tboxUnitPrice4.getText())));
			tboxQty4.setText(String.valueOf(number));
		}else if(!tboxItem5.getText().isEmpty()){
			tboxItem4.setText(tboxItem5.getText());
			tboxDesc4.setText(tboxDesc5.getText());
			tboxUnitPrice4.setText(tboxUnitPrice5.getText());
			tboxQty4.setText(tboxQty5.getText());
			tboxSubTotal4.setText(tboxSubTotal5.getText());
			if(!tboxItem6.getText().isEmpty()){
				tboxItem5.setText(tboxItem6.getText());
				tboxDesc5.setText(tboxDesc6.getText());
				tboxUnitPrice5.setText(tboxUnitPrice6.getText());
				tboxQty5.setText(tboxQty6.getText());
				tboxSubTotal5.setText(tboxSubTotal6.getText());
				tboxItem6.setText("");
				tboxDesc6.setText("");
				tboxUnitPrice6.setText("");
				tboxQty6.setText("");
				tboxSubTotal6.setText("");
			}else {
				tboxItem5.setText("");
				tboxDesc5.setText("");
				tboxUnitPrice5.setText("");
				tboxQty5.setText("");
				tboxSubTotal5.setText("");
			}
		}else {
			tboxItem4.setText("");
			tboxDesc4.setText("");
			tboxUnitPrice4.setText("");
			tboxQty4.setText("");
			tboxSubTotal4.setText("");
		}

		double total = 0;
		if(!tboxSubTotal1.getText().isEmpty())
			total+=Double.parseDouble(tboxSubTotal1.getText());
		if(!tboxSubTotal2.getText().isEmpty())
			total+=Double.parseDouble(tboxSubTotal2.getText());
		if(!tboxSubTotal3.getText().isEmpty())
			total+=Double.parseDouble(tboxSubTotal3.getText());
		if(!tboxSubTotal4.getText().isEmpty())
			total+=Double.parseDouble(tboxSubTotal4.getText());
		if(!tboxSubTotal5.getText().isEmpty())
			total+=Double.parseDouble(tboxSubTotal5.getText());
		if(!tboxSubTotal6.getText().isEmpty())
			total+=Double.parseDouble(tboxSubTotal6.getText());
		tboxItemTotal.setText(String.valueOf(total));

		tboxTaxAmount.setText(String.format("%.2f",(total*7.75)/100));

		tboxOrderTotal.setText(String.format("%.2f",Double.parseDouble(tboxItemTotal.getText()) + Double.parseDouble(tboxTaxAmount.getText())));

	}

	@FXML
	void handle_Remove5(ActionEvent event) {
		int number = Integer.valueOf(tboxQty5.getText());
		if(number > 1){
			number--;
			tboxSubTotal5.setText(String.format("%.2f",Double.parseDouble(tboxSubTotal5.getText())-Double.parseDouble(tboxUnitPrice5.getText())));
			tboxQty5.setText(String.valueOf(number));
		}else if(!tboxItem6.getText().isEmpty()){
			tboxItem5.setText(tboxItem6.getText());
			tboxDesc5.setText(tboxDesc6.getText());
			tboxUnitPrice5.setText(tboxUnitPrice6.getText());
			tboxQty5.setText(tboxQty6.getText());
			tboxSubTotal5.setText(tboxSubTotal6.getText());
			tboxItem6.setText("");
			tboxDesc6.setText("");
			tboxUnitPrice6.setText("");
			tboxQty6.setText("");
			tboxSubTotal6.setText("");
		}else {
			tboxItem5.setText("");
			tboxDesc5.setText("");
			tboxUnitPrice5.setText("");
			tboxQty5.setText("");
			tboxSubTotal5.setText("");
		}


		double total = 0;
		if(!tboxSubTotal1.getText().isEmpty())
			total+=Double.parseDouble(tboxSubTotal1.getText());
		if(!tboxSubTotal2.getText().isEmpty())
			total+=Double.parseDouble(tboxSubTotal2.getText());
		if(!tboxSubTotal3.getText().isEmpty())
			total+=Double.parseDouble(tboxSubTotal3.getText());
		if(!tboxSubTotal4.getText().isEmpty())
			total+=Double.parseDouble(tboxSubTotal4.getText());
		if(!tboxSubTotal5.getText().isEmpty())
			total+=Double.parseDouble(tboxSubTotal5.getText());
		if(!tboxSubTotal6.getText().isEmpty())
			total+=Double.parseDouble(tboxSubTotal6.getText());
		tboxItemTotal.setText(String.valueOf(total));

		tboxTaxAmount.setText(String.format("%.2f",(total*7.75)/100));

		tboxOrderTotal.setText(String.format("%.2f",Double.parseDouble(tboxItemTotal.getText()) + Double.parseDouble(tboxTaxAmount.getText())));

	}

	@FXML
	void handle_Remove6(ActionEvent event) {
		int number = Integer.valueOf(tboxQty6.getText());
		if(number > 1){
			number--;
			tboxSubTotal6.setText(String.format("%.2f",Double.parseDouble(tboxSubTotal6.getText())-Double.parseDouble(tboxUnitPrice6.getText())));
			tboxQty6.setText(String.valueOf(number));
		}else {
			tboxItem6.setText("");
			tboxDesc6.setText("");
			tboxUnitPrice6.setText("");
			tboxQty6.setText("");
			tboxSubTotal6.setText("");
		}


		double total = 0;
		if(!tboxSubTotal1.getText().isEmpty())
			total+=Double.parseDouble(tboxSubTotal1.getText());
		if(!tboxSubTotal2.getText().isEmpty())
			total+=Double.parseDouble(tboxSubTotal2.getText());
		if(!tboxSubTotal3.getText().isEmpty())
			total+=Double.parseDouble(tboxSubTotal3.getText());
		if(!tboxSubTotal4.getText().isEmpty())
			total+=Double.parseDouble(tboxSubTotal4.getText());
		if(!tboxSubTotal5.getText().isEmpty())
			total+=Double.parseDouble(tboxSubTotal5.getText());
		if(!tboxSubTotal6.getText().isEmpty())
			total+=Double.parseDouble(tboxSubTotal6.getText());
		tboxItemTotal.setText(String.valueOf(total));

		tboxTaxAmount.setText(String.format("%.2f",(total*7.75)/100));

		tboxOrderTotal.setText(String.format("%.2f",Double.parseDouble(tboxItemTotal.getText()) + Double.parseDouble(tboxTaxAmount.getText())));

	}

	@FXML
	void handle_Save(ActionEvent event) {

	}

	@FXML
	void handle_Open(ActionEvent event) {

	}

	@FXML
	void handle_tableView(MouseEvent event) {
		tableData selData = tableView.getSelectionModel().getSelectedItem(); 
		File file = new File(selData.getPath());
		Image image = new Image(file.toURI().toString());
		imgView.setImage(image);
		int quantity;
		double subTotal;
		if(tboxItem1.getText().equals(selData.getNumber())){
			quantity = Integer.parseInt(tboxQty1.getText());
			quantity++;
			tboxQty1.setText(String.valueOf(quantity));
			subTotal = Double.parseDouble(tboxSubTotal1.getText());
			tboxSubTotal1.setText(String.valueOf(subTotal + selData.getUnitPrice()));
		}else if(tboxItem2.getText().equals(selData.getNumber())){
			quantity = Integer.parseInt(tboxQty2.getText());
			quantity++;
			tboxQty2.setText(String.valueOf(quantity));
			subTotal = Double.parseDouble(tboxSubTotal2.getText());
			tboxSubTotal2.setText(String.valueOf(subTotal + selData.getUnitPrice()));
		}else if(tboxItem3.getText().equals(selData.getNumber())){
			quantity = Integer.parseInt(tboxQty3.getText());
			quantity++;
			tboxQty3.setText(String.valueOf(quantity));
			subTotal = Double.parseDouble(tboxSubTotal3.getText());
			tboxSubTotal3.setText(String.valueOf(subTotal + selData.getUnitPrice()));
		}else if(tboxItem4.getText().equals(selData.getNumber())){
			quantity = Integer.parseInt(tboxQty4.getText());
			quantity++;
			tboxQty4.setText(String.valueOf(quantity));
			subTotal = Double.parseDouble(tboxSubTotal4.getText());
			tboxSubTotal4.setText(String.valueOf(subTotal + selData.getUnitPrice()));
		}else if(tboxItem5.getText().equals(selData.getNumber())){
			quantity = Integer.parseInt(tboxQty5.getText());
			quantity++;
			tboxQty5.setText(String.valueOf(quantity));
			subTotal = Double.parseDouble(tboxSubTotal5.getText());
			tboxSubTotal5.setText(String.valueOf(subTotal + selData.getUnitPrice()));
		}else if(tboxItem6.getText().equals(selData.getNumber())){
			quantity = Integer.parseInt(tboxQty6.getText());
			quantity++;
			tboxQty6.setText(String.valueOf(quantity));
			subTotal = Double.parseDouble(tboxSubTotal6.getText());
			tboxSubTotal6.setText(String.valueOf(subTotal + selData.getUnitPrice()));
		}else {
			if(tboxItem1.getText().isEmpty()){
				tboxItem1.setText(selData.getNumber());
				tboxDesc1.setText(selData.getName());
				tboxUnitPrice1.setText(String.format("%.2f", selData.getUnitPrice()));
				quantity = 1;
				tboxQty1.setText(String.valueOf(quantity));
				tboxSubTotal1.setText(String.valueOf(quantity*selData.getUnitPrice()));
			}else if(tboxItem2.getText().isEmpty()){
				tboxItem2.setText(selData.getNumber());
				tboxDesc2.setText(selData.getName());
				tboxUnitPrice2.setText(String.format("%.2f", selData.getUnitPrice()));
				quantity = 1;
				tboxQty2.setText(String.valueOf(quantity));
				tboxSubTotal2.setText(String.valueOf(quantity*selData.getUnitPrice()));
			}else if(tboxItem3.getText().isEmpty()){
				tboxItem3.setText(selData.getNumber());
				tboxDesc3.setText(selData.getName());
				tboxUnitPrice3.setText(String.format("%.2f", selData.getUnitPrice()));
				quantity = 1;
				tboxQty3.setText(String.valueOf(quantity));
				tboxSubTotal3.setText(String.valueOf(quantity*selData.getUnitPrice()));
			}else if(tboxItem4.getText().isEmpty()){
				tboxItem4.setText(selData.getNumber());
				tboxDesc4.setText(selData.getName());
				tboxUnitPrice4.setText(String.format("%.2f", selData.getUnitPrice()));
				quantity = 1;
				tboxQty4.setText(String.valueOf(quantity));
				tboxSubTotal4.setText(String.valueOf(quantity*selData.getUnitPrice()));
			}else if(tboxItem5.getText().isEmpty()){
				tboxItem5.setText(selData.getNumber());
				tboxDesc5.setText(selData.getName());
				tboxUnitPrice5.setText(String.format("%.2f", selData.getUnitPrice()));
				quantity = 1;
				tboxQty5.setText(String.valueOf(quantity));
				tboxSubTotal5.setText(String.valueOf(quantity*selData.getUnitPrice()));
			}else if(tboxItem6.getText().isEmpty()){
				tboxItem6.setText(selData.getNumber());
				tboxDesc6.setText(selData.getName());
				tboxUnitPrice6.setText(String.format("%.2f", selData.getUnitPrice()));
				quantity = 1;
				tboxQty6.setText(String.valueOf(quantity));
				tboxSubTotal6.setText(String.valueOf(quantity*selData.getUnitPrice()));
			}
		}
		double total = 0;
		if(!tboxSubTotal1.getText().isEmpty())
			total+=Double.parseDouble(tboxSubTotal1.getText());
		if(!tboxSubTotal2.getText().isEmpty())
			total+=Double.parseDouble(tboxSubTotal2.getText());
		if(!tboxSubTotal3.getText().isEmpty())
			total+=Double.parseDouble(tboxSubTotal3.getText());
		if(!tboxSubTotal4.getText().isEmpty())
			total+=Double.parseDouble(tboxSubTotal4.getText());
		if(!tboxSubTotal5.getText().isEmpty())
			total+=Double.parseDouble(tboxSubTotal5.getText());
		if(!tboxSubTotal6.getText().isEmpty())
			total+=Double.parseDouble(tboxSubTotal6.getText());
		tboxItemTotal.setText(String.valueOf(total));

		tboxTaxAmount.setText(String.format("%.2f",(total*7.75)/100));

		tboxOrderTotal.setText(String.format("%.2f",Double.parseDouble(tboxItemTotal.getText()) + Double.parseDouble(tboxTaxAmount.getText())));
	}

	public static Stage getStage(){
		return primaryStage;
	}

}

