package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {
    // private
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int Id;
    private String Name;
    private double Price;
    private int Stock;
    private int Max;
    private int Min;

    public Product(int id, String name, double price, int stock, int max, int min) {
        Id = id;
        Name = name;
        Price = price;
        Stock = stock;
        Max = max;
        Min = min;
    }


    // public
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public int getStock() {
        return Stock;
    }

    public void setStock(int stock) {
        Stock = stock;
    }

    public int getMax() {
        return Max;
    }

    public void setMax(int max) {
        Max = max;
    }

    public int getMin() {
        return Min;
    }

    public void setMin(int min) {
        Min = min;
    }

    //Product specific actions

    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }

    public boolean deleteAssociatedPart(Part part){
        associatedParts.remove(part);
        return false;
    }

    public ObservableList<Part> getAllAssociatedParts(){

        return associatedParts;
    }

    // Add ObserList Add Delete and Get.


}
