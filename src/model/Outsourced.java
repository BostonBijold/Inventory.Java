package model;

public class Outsourced extends Part {

    private String companyName;


    public Outsourced(String companyName, int partID, String partName, double partPrice, int partInStock, int min, int max) {
        super(partID, partName, partPrice, partInStock, min, max);
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
