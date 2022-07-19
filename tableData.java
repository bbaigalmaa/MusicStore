package application;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class tableData {
	private SimpleStringProperty name;
	private SimpleStringProperty number;
	private SimpleDoubleProperty unitPrice;
	private SimpleStringProperty path;
	
	public String getPath() {
		return path.get();
	}
	public void setPath(String path) {
		this.path = new SimpleStringProperty(path);
	}
	public String getName() {
		return name.get();
	}
	public void setName(String name) {
		this.name = new SimpleStringProperty(name);
	}
	public String getNumber() {
		return number.get();
	}
	public void setNumber(String number) {
		this.number = new SimpleStringProperty(number);
	}
	public Double getUnitPrice() {
		return unitPrice.get();
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = new SimpleDoubleProperty(unitPrice);
	}
}
