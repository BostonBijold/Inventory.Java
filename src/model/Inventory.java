package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Part;

public class Inventory {

    //Private
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();


    // Public
    // Parts
    public static void addPart(Part part){
        allParts.add(part);
    }

    public static void addProduct(Product product) {allProducts.add(product);}

    public static Part lookUpPart(int Id) {
        for(Part part : allParts)
            if(part.getId() == Id) {
                return part;}
        return null;
    }

    public static Product lookUpProduct(int Id){
        for(Product product : allProducts)
            if(product.getId() == Id) {
                return product;}
        return null;

    }

    public static ObservableList<Part> lookUpPart(String Name){
        ObservableList<Part> partSearch = FXCollections.observableArrayList();
        partSearch.clear();
        for(Part parts: allParts) {
            if (parts.getName().contains(Name)) {
                partSearch.add(parts);
            }
        }

        return partSearch;
    }

    public static ObservableList<Product> lookUpProduct(String Name){
        ObservableList<Product> productSearch = FXCollections.observableArrayList();
        productSearch.clear();
        for(Product product: allProducts) {
            if (product.getName().contains(Name)) {
                productSearch.add(product);
            }
        }

        return productSearch;
    }

    public static int createUniquePartID() {
        int uniqueID= -1;
            for(Part part: allParts){
                if(uniqueID < part.getId())
                    uniqueID = part.getId();
                uniqueID += 2;
            }
        return uniqueID;
    }

    public static int createUniqueProductID() {
        int uniqueID= -1;
        for(Product product: allProducts){
            if(uniqueID < product.getId())
                uniqueID = product.getId();
            uniqueID += 2;
        }
        return uniqueID;
    }

    public static void updatePart (int Index, Part part){

         int i = -1;
         for(Part orgPart: allParts){
             i++; // gets the index of the part;
             if(orgPart.getId() == Index) { // tests part ID vs the ID brought in by function call.
                 Inventory.allParts.set(i, part); // Replaces part with new part.
             }
         }
    }

    public static void updateProduct(int index, Product product){
        int i = -1;
        for(Product origProduct : allProducts){
            i++; // gets the index of the Product;
            if(origProduct.getId() == index){
                Inventory.allProducts.set(i, product);
            }
        }

    }

    public static void deletePart(Part part){
        allParts.remove(part);
    }

    public static void deleteProduct(Product product){
        allProducts.remove(product);
    }

    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }

}
