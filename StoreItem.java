package application;

public class StoreItem {
	private String nbr;
    private String cat;
    private String tp;
    private String nm;
    private double prc;

    public void setItemNumber(String itemNumber){
        nbr = itemNumber; 
    }
    
    public String getItemNumber(){
        return nbr; 
    }

    public String getCategory() {
        return cat; 
    }

    public void setCategory(String cat) {
        this.cat = cat; 
    }
    public String getType() {
    	return tp;
    }
    
    public void setType(String val) {
    	tp = val;
    }

    public String getItemName(){
    	return nm;
    }

    public void getItemName(String val){
    	nm = val; 
    }
    
    public double getUnitPrice(){
    	return prc;
    }

    public void setUnitPrice(double val){
    	prc = val;
    }
    
    public boolean Equals(StoreItem same){
        if( (nbr == same.nbr) &&
            (cat == same.cat) &&
            (tp  == same.tp) &&
            (nm  == same.nm) &&
            (prc == same.prc))
            return true;
        else
            return false;
    }

    public StoreItem()
    {
        nbr = "000000";
        cat = "Accessories";
        tp  = "Accessories";
        nm  = "Unknown";
        prc = 0.00;
    }

    public StoreItem(String itmNumber)
    {
        nbr = itmNumber;
        cat = "Accessories";
        tp  = "Accessories";
        nm  = "Unknown";
        prc = 0.00;
    }

    public StoreItem(String itmNumber, String category, String type, String name, double price){
        nbr = itmNumber;
        cat = category;
        tp  = type;
        nm  = name;
        prc = price;
    }
}
