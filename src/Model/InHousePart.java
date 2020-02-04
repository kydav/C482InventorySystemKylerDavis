package Model;

import javafx.beans.property.SimpleIntegerProperty;
/**
 * @author kylerdavis
 */
public class InHousePart extends Part {
    private final SimpleIntegerProperty machineId;

    public InHousePart() {
        super();
        machineId = new SimpleIntegerProperty();
    }    
    public void setMachineId(int machineId){
        this.machineId.set(machineId);
    }
    public int getMachineId(){
        return this.machineId.get();
    }
}
