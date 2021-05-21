package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyProduct implements Initializable {

    Stage stage;
    Parent scene;

    private ObservableList<Part> partHolder = FXCollections.observableArrayList();

    @FXML
    private TableView<Part> PartTable1;

    @FXML
    private TableView<Part> PartTable2;

    @FXML
    private TextField ProductIDField;

    @FXML
    private TextField ProductNameField;

    @FXML
    private TextField ProductInventoryField;

    @FXML
    private TextField ProductPriceField;

    @FXML
    private TextField ProductMinField;

    @FXML
    private TextField ProductMaxField;

    @FXML
    private TextField PartSearchField;

    @FXML
    private TableColumn<?, ?> PartIDCol1;

    @FXML
    private TableColumn<?, ?> PartNameCol1;

    @FXML
    private TableColumn<?, ?> PartInv1;

    @FXML
    private TableColumn<?, ?> PartPriceCol1;

    @FXML
    private TableColumn<?, ?> PartPriceCol2;

    @FXML
    private TableColumn<?, ?> PartID2;

    @FXML
    private TableColumn<?, ?> PartNameCol2;

    @FXML
    private TableColumn<?, ?> PartInv2;

    @FXML
    private Button PartAdd;

    @FXML
    private Button PartRemove;

    @FXML
    private Button SaveProduct;

    @FXML
    private Button CancelProduct;

    /**
     * RUNTIME ERROR: error message pops up if no part is selected to add to associated parts list.
     * @param event
     */

    @FXML
    void OnActionAddPart(ActionEvent event) { Part part = PartTable1.getSelectionModel().getSelectedItem();
        if(part == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Selection Error");
            alert.setContentText("Nothing was selected.");
            alert.showAndWait();
            return;
        }
        for(Part p: partHolder)
            if(p.getId() == part.getId())
                return;                     //if there is a match in partID then ths function exits. else it adds part.
        partHolder.add(part);

    }

    public void receiveProduct(Product product){
        ProductIDField.setText(String.valueOf(product.getId()));
        ProductNameField.setText(product.getName());
        ProductInventoryField.setText((String.valueOf(product.getStock())));
        ProductPriceField.setText(String.valueOf(product.getPrice()));
        ProductMaxField.setText(String.valueOf(product.getMax()));
        ProductMinField.setText(String.valueOf(product.getMin()));
        product.getAllAssociatedParts();
        for(Part p : product.getAllAssociatedParts()){
            partHolder.add(p);
        }

    }

    @FXML
    void OnActionCancelProduct(ActionEvent event)throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/mainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**
     * RUNTIME ERROR: error message pops up if no part is selected to remove from associated parts list.
     * @param event
     */
    @FXML
    void OnActionRemovePart(ActionEvent event) {
        Part part = PartTable2.getSelectionModel().getSelectedItem();
        if (part == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Selection Error");
            alert.setContentText("Nothing was selected.");
            alert.showAndWait();
            return;
        }
        partHolder.remove(part);

    }

    /**
     * FUTURE ENHANCEMENT: Allow for user to search without case sensitivity.
     * @param event
     */
    @FXML
    void OnActionSearch(ActionEvent event) {
        String query = PartSearchField.getText();
        if (query.isEmpty())
            PartTable1.setItems(Inventory.getAllParts());
        else {
            if (Character.isDigit(query.charAt(0))) {
                ObservableList<Part> partNames = FXCollections.observableArrayList();
                partNames.add(Inventory.lookUpPart(Integer.parseInt(query)));
                PartTable1.setItems(partNames);
            } else
                PartTable1.setItems(Inventory.lookUpPart(query));

        }

    }
    /**
     * RUNTIME ERROR Multiple checks preformed before saving to prevent crashing due to incompatible data types.
     * Checks if Max is less than min.
     * Checks if inventory is between max and min.
     * Checks if total price of Product is greater than the cost of the associated parts.
     * Checks that all fields have data entered.
     * @param event
     */
    @FXML
    void OnActionSaveProduct(ActionEvent event) {
        try{

            int ID =  Integer.parseInt(ProductIDField.getText());
            String Name = ProductNameField.getText();
            int Inventory = Integer.parseInt(ProductInventoryField.getText());
            double Price = Double.parseDouble(ProductPriceField.getText());
            int Max = Integer.parseInt(ProductMaxField.getText());
            int Min = Integer.parseInt(ProductMinField.getText());
            if(Max <= Min) {
                errorWindow(4);
                return;
            }
            if(Inventory < Min || Inventory > Max) {
                errorWindow(1);
                return;
            }
            double total =0;
            for(Part checker: partHolder){
                total = total + checker.getPrice();
            }
            if(Price < total){
                errorWindow(2);
                return;
            }
            Product product = (new Product(ID, Name, Price, Inventory, Max, Min));
            for(Part part : partHolder){
                product.addAssociatedPart(part);
            }
            model.Inventory.updateProduct(product.getId(), product);

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/mainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();


        } catch (IOException | NumberFormatException e) {// declares a object for the error caused by the try statement- not valid entry.
            //the next three lines only print to the Terminal. only devs will see these printed lines.
            System.out.println("Enter a valid value");
            System.out.println("Exception:" + e); // e displays the message that would be in the list of errors.
            System.out.println(e.getMessage());

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please enter a word for a name or color and a number for the age.");
            alert.showAndWait();

        }

    }
    public ObservableList<Part> getPartHolder(){
        return partHolder;
    }
    private void errorWindow(int code){
        if(code == 1){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Inventory Error");
            alert.setContentText("Inventory count is not between the Maximum and Minimum values.");
            alert.showAndWait();
        }
        if(code == 2){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Price Error");
            alert.setContentText("Total price of included parts exceeds the price of the product.");
            alert.showAndWait();
        }
        if(code == 3){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Delete Selection Error");
            alert.setContentText("Nothing was selected to delete.");
            alert.showAndWait();
        }
        if(code == 4){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Max and Min Error");
            alert.setContentText("The inventory maximum cannot be less than the minimum.");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        ProductIDField.setText(String.valueOf(Inventory.createUniqueProductID()));

        PartTable1.setItems(Inventory.getAllParts());
        PartIDCol1.setCellValueFactory(new PropertyValueFactory<>("Id"));
        PartNameCol1.setCellValueFactory(new PropertyValueFactory<>("Name"));
        PartInv1.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        PartPriceCol1.setCellValueFactory(new PropertyValueFactory<>("Price"));


        PartTable2.setItems(getPartHolder());
        PartID2.setCellValueFactory(new PropertyValueFactory<>("Id"));
        PartNameCol2.setCellValueFactory(new PropertyValueFactory<>("Name"));
        PartInv2.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        PartPriceCol2.setCellValueFactory(new PropertyValueFactory<>("Price"));
    }
}
