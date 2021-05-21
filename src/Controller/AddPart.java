package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class AddPart implements Initializable {

    Stage stage;
    Parent scene;
        @FXML
        private ToggleGroup PartProvider;


        @FXML
        private RadioButton OutSourced;

        @FXML
        private RadioButton InHouse;

        @FXML
        private Label InOutLabel;

        @FXML
        private TextField PartIDField;

        @FXML
        private TextField PartNameField;

        @FXML
        private TextField PartInventoryField;

        @FXML
        private TextField PartCostField;

        @FXML
        private TextField PartMaxField;

        @FXML
        private TextField PartMachineCompanyField;

        @FXML
        private TextField PartMInField;

    /**
     * RUNTIME ERROR: multiple checks are preformed before saving to prevent crashing due to incompatible data types.
     * Checks that max is grater than min.
     * Checks if inventory is between the maximum and the minimum.
     * Checks if In-House is selected, if so checks the entered value for integers only.
     * Checks that all parts of the form have data values.
     * @param event
     */
    public void SavePart(ActionEvent event) {
        try{// saves the new part if all of the below checks pass.
// pulls data from the text fields on the screen.
            int ID =  Inventory.createUniquePartID();
            String Name = PartNameField.getText();
            int Inventory = Integer.parseInt(PartInventoryField.getText());
            double Price = Double.parseDouble(PartCostField.getText());
            int Max = Integer.parseInt(PartMaxField.getText());
            int Min = Integer.parseInt(PartMInField.getText());
            if(Max <= Min) {
                errorWindow(2);
                return;
            }
            if(Inventory < Min || Inventory > Max){// RUNTIME ERROR checks to see if the inventory is above the Min and below the Max. otherwise it won't save.
                errorWindow(1);//displayed if the inventory is beyond the max or below the min.
                return;
            }
            if(InHouse.isSelected()){// if the part is a inhouse the last field's data is added as an integer.
                int MachineID = Integer.parseInt((PartMachineCompanyField.getText()));
                model.Inventory.addPart(new inHouse(MachineID, ID, Name, Price, Inventory, Min, Max));
                // creates a new part and adds it to the static part table.
            }
            else{
                String companyName = PartMachineCompanyField.getText();// pulls the last field as a string for outsourced parts.
                model.Inventory.addPart(new Outsourced(companyName, ID, Name, Price, Inventory, Min, Max));
            }// Creates a new part will all of the above data and the company name as an Outsourced data.

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/mainMenu.fxml"));// returns to the main menu after saving the new part.
            stage.setScene(new Scene(scene));
            stage.show();


    } catch (IOException | NumberFormatException e) {// declares a object for the error caused by the try statement- not valid entry.
            //the next three lines only print to the Terminal. only devs will see these printed lines.
            System.out.println("Enter a valid value");
            System.out.println("Exception:" + e); // e displays the message that would be in the list of errors.
            System.out.println(e.getMessage());

            Alert alert = new Alert(Alert.AlertType.ERROR); //display an error about the added part and does not save or return to main menu.
            alert.setTitle("Error Dialog");
            alert.setContentText("Please enter a word for a name or color and a number for Price, Inventory, Max, and Min.");
            alert.showAndWait();

        }
    }

    public void CancelAddPart(ActionEvent event)  throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all values, do you want to continue?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/mainMenu.fxml")); // canceling does not save and returns to main menu.
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * FUTURE ENHANCEMENT: Machine ID field will only allow for integers to be entered.
     * @param event
     */
    public void InHouseRadio(ActionEvent event) {
        InOutLabel.setText("Machine ID:");// changes the text based on the type of part being added.
    }

    public void OutsourcedRadio(ActionEvent event) {
        InOutLabel.setText("Company Name:");// changes the text based on the type of part being added.
    }


    private void errorWindow(int code) {// RUNTIME ERROR
        if (code == 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Inventory Error");
            alert.setContentText("Inventory count is not between the Maximum and Minimum values.");
            alert.showAndWait();// Displays if the inventory count is not between the Max and Min.
        }
        if(code == 2){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Max and Min Error");
            alert.setContentText("The inventory maximum cannot be less than the minimum.");
            alert.showAndWait();
        }    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        PartIDField.setText(String.valueOf(Inventory.createUniquePartID()));
        // uses the createUniqueID function to crete a new ID in sync with the current part count.
    }

}
