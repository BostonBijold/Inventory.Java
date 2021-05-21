package Controller;

//imports are used to bring in classes and prebuilt functions to be used in your program;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import model.Inventory;
import model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Product;


import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    // this section declares all of the fields on the screen to be used in showing and gathering data.

    Stage stage;
    Parent scene;

    private ObservableList<Part> partHolder = FXCollections.observableArrayList();

    @FXML
    private TableView<Part> PartTable;

    @FXML
    private TableColumn<Part, Integer> PartIdCol;

    @FXML
    private TableColumn<Part, String> PartNameCol;

    @FXML
    private TableColumn<Part, Integer> PartInvenCol;

    @FXML
    private TableColumn<Part, Double> PartPriceCol;

    @FXML
    private TextField PartSearchField;

    @FXML
    private TableView<Product> ProductTable;

    @FXML
    private TableColumn<Product, Integer> ProductIDCol;

    @FXML
    private TableColumn<Product, String> ProductNameCol;

    @FXML
    private TableColumn<Product, Integer> ProductInventoryCol;

    @FXML
    private TableColumn<Product, Double> ProductPriceCol;

    @FXML
    private Button AddProduct;

    @FXML
    private Button ModifyProduct;

    @FXML
    private Button DeleteProduct;// unused FXids will remain in grey.

    @FXML
    private TextField ProductSearchField;

    @FXML
    private Button ProductSearch;



    @FXML
    void onActionClose(ActionEvent event) { System.exit(0);}
    //System.exit(0) uses the false state of 0 to exit the program.

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Populates the Parts Table with the data created in the main class.

        PartTable.setItems(Inventory.getAllParts());
        PartIdCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        PartNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        PartInvenCol.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        PartPriceCol.setCellValueFactory(new PropertyValueFactory<>("Price"));

        //Populates the Product Table with the data created in the main class.

        ProductTable.setItems(Inventory.getAllProducts());
        ProductIDCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        ProductNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        ProductInventoryCol.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        ProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
    }

    public void onActionAddPart(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/AddPart.fxml"));// uses the FXMLLoader to load the screen AddPart.
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * RUNTIME ERROR: Used to catch an error of no selected part to modify. Returns an error message asking the user to select a part.
     * @param event
     * @throws IOException
     */
    public void onActionModifyPart(ActionEvent event)  throws IOException { // this will transfer a object from one screen to the next screen.

        try {// a try block will try the specified code and return an error if "caught" instead of crashing the program;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyPart.fxml"));//Starts the loading process of ModifyPart.
            loader.load();

            ModifyPart modifyPart = loader.getController();  //allows this controller to use another controller. like extending a class.
            //ModifyPart is the controller being Loaded.
            modifyPart.receivePart(PartTable.getSelectionModel().getSelectedItem()); //uses the selected row to go to display page.

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            //scene = FXMLLoader.load(getClass().getResource("details.fxml")); //already used above.
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (IOException| NullPointerException e) { //// RUNTIME ERROR not taught in provided text or videos but catches if nothing(null) is passed between screens.
            errorWindow(2); // launches error window 2 if nothing is selected.
        }
    }

    /**
     * RUNTIME ERROR: Returns an error if nothing is selected to delete.
     * @param event
     */
    public void onActionDeletePart(ActionEvent event) {

            Part part = PartTable.getSelectionModel().getSelectedItem();// receives selected part from the Table.
            if (part == null) { //If nothing is selected the errorWindow(3) is displayed and program returns to the main menu.
                errorWindow(3);
                return;
            }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will permanently remove the selected product, do you want to continue?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Inventory.deletePart(part);//uses observable list function to remove selected part from the table.
        }
    }
    /**
     * FUTURE ENHANCEMENT: Allow for searching without case sensitivity.
     * @param event
     */
    public void onActionPartSearch(ActionEvent event) {// RUNTIME ERROR all ActionEvents events are buttons being selected on the screen.

        String query = PartSearchField.getText(); // receives the text from the search bar.
        if (query.isEmpty())// checks if the search is empty.
            PartTable.setItems(Inventory.getAllParts());// if the search is empty the table returns all of the parts into the table.
        else {// if the search has 1 or more characters this will execute.
            if (Character.isDigit(query.charAt(0))) {//checks if the first character is a number. if so this executes.
                ObservableList<Part> partNames = FXCollections.observableArrayList();// creates a new Observable list to hold the searched data.
                partNames.add(Inventory.lookUpPart(Integer.parseInt(query)));//uses lookUpPart function to search the current list based on the query as an integer.
                PartTable.setItems(partNames);// adds found parts to the new searched observable list.
            } else
                PartTable.setItems(Inventory.lookUpPart(query));// uses look up part function as a string and adds found parts to the new observable list.

        }
    }

    /**
     * FUTURE ENHANCEMENT: Allow for searching without case sensitivity.
     * @param event
     */
    public void onActionProductSearch(ActionEvent event) {
        String query = ProductSearchField.getText();// receives the text from the search bar.
        if (query.isEmpty())// checks if the search is empty.
            ProductTable.setItems(Inventory.getAllProducts());// if the search is empty the table returns all of the products into the table.
        else {
            if (Character.isDigit(query.charAt(0))) {//checks if the first character is a number. if so this executes.
                ObservableList<Product> productNames = FXCollections.observableArrayList();// creates a new Observable list to hold the searched data.
                productNames.add(Inventory.lookUpProduct(Integer.parseInt(query)));//uses lookUpPart function to search the current list based on the query as an integer.
                ProductTable.setItems(productNames);// adds found products to the new searched observable list.
            } else
                ProductTable.setItems(Inventory.lookUpProduct(query));// uses look up part function as a string and adds found products to the new observable list.

        }
    }




    public void OnActionAddProduct(ActionEvent event)  throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/AddProduct.fxml"));// loads the addProduct screen
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * RUNTIME ERROR: Used to catch an error of no selected product to modify. Returns an error message asking the user to select a product to modify.
     * @param event
     * @throws IOException
     */
    public void OnActionModifyProduct(ActionEvent event)  throws IOException {// RUNTIME ERROR checks for null selected Product to modify.


        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyProduct.fxml"));// tries to load the ModifyProduct screen and throws an error if no Product is selected.
            loader.load();

            ModifyProduct modifyProduct = loader.getController();  //allows this controller to use another controller. like extending a class.
            //ModifyPart is the controller being Loaded.
            modifyProduct.receiveProduct(ProductTable.getSelectionModel().getSelectedItem()); //uses the selected row to go to display page.

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            //scene = FXMLLoader.load(getClass().getResource("details.fxml")); //already used above.
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (IOException| NullPointerException e) { // not taught in class or videos but catches if nothing is passed between screens.
            errorWindow(2);
        }
    }
    /**
     * RUNTIME ERROR: Returns an error if nothing is selected to delete. Checks if product has associated parts and does not allow removal.
     * @param event
     */
    public void OnActionDeleteProduct(ActionEvent event) {

            Product product = ProductTable.getSelectionModel().getSelectedItem();// pulls selected data from Table to delete.
            if (product == null) {// RUNTIME ERROR checks if data is null and displays window if nothing is selected.
                errorWindow(3);
                return;
            }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will permanently remove the selected product, do you want to continue?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            partHolder = product.getAllAssociatedParts();
            if (partHolder.isEmpty()) {
                Inventory.deleteProduct(product);// uses deleteProduct function from Inventory.
                partHolder.clear();
            } else {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Product has associated parts");
                alert.setContentText("A product with associated parts cannot be deleted.");
                alert.showAndWait();
            }
        }
    }

    /**
     * FUTURE ENHANCEMENT: More customized error messages to better inform the user about what action is incorrectly prformed.
     * @param code
     */
    private void errorWindow(int code){//listed error window for above code to use.
        if(code == 1){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Empty Inventory");
            alert.setContentText("Nothing was selected.");
            alert.showAndWait();
        }
        if(code == 2){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Modify Selection Error");
            alert.setContentText("Nothing was selected.");
            alert.showAndWait();
        }
        if(code == 3){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Delete Selection Error");
            alert.setContentText("Nothing was selected to delete.");
            alert.showAndWait();
        }
    }
}
