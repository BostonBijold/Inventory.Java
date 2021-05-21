package model;

import model.Part;

public class inHouse extends Part {

    private int machineID;


    public inHouse(int machineID, int partID, String partName, double partPrice, int partInStock, int min, int max) {
        super(partID, partName, partPrice, partInStock, min, max);
        this.machineID = machineID;
    }

    public int getMachineID() {
        return machineID;
    }

    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }
}
