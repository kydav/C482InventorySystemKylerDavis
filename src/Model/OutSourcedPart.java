package Model;

import javafx.beans.property.SimpleStringProperty;
/**
 * @author kylerdavis
 */
public class OutSourcedPart extends Part {
    private final SimpleStringProperty companyName;
    
    public OutSourcedPart(){
        super();
        companyName = new SimpleStringProperty();
    }
    public void setCompanyName(String companyName){
        this.companyName.set(companyName);
    }
    public String getCompanyName(){
        return this.companyName.get();
    }
}
