package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.inHouse;
import model.Outsourced;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyPart implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private ToggleGroup PartProvider;

    @FXML
    private TextField ModifyIDField;

    @FXML
    private TextField ModifyNameField;

    @FXML
    private TextField ModifyInventoryField;

    @FXML
    private TextField ModifyCostField;

    @FXML
    private TextField ModifyMaxField;

    @FXML
    private TextField ModifyMachineField;

    @FXML
    private TextField ModifyMinField;

    @FXML
    private Label InOutLabel;

    @FXML
    private RadioButton inHouseRd;

    @FXML
    private RadioButton OutsourcedRd;

    @FXML
    private Button SavePartModify;

    @FXML
    private Button Cancel;

    /**
     * RUNTIME ERROR: multiple checks are preformed before saving to prevent crashing due to incompatible data types.
     * Checks that max is greater than the min.
     * Checks if inventory is between the maximum and the minimum.
     * Checks if In-House is selected, if so checks the entered value for integers only.
     * Checks that all parts of the form have data values.
     * @param event
     */

    public void SavePartModify(ActionEvent event) {
        try {

            int ID = Integer.parseInt(ModifyIDField.getText());
            String Name = ModifyNameField.getText();
            int Inventory = Integer.parseInt(ModifyInventoryField.getText());
            double Price = Double.parseDouble(ModifyCostField.getText());
            int Max = Integer.parseInt(ModifyMaxField.getText());
            int Min = Integer.parseInt(ModifyMinField.getText());
            if(Max <= Min) {
                errorWindow(2);
                return;
            }
            if(Inventory < Min || Inventory > Max){
                errorWindow(1);
                return;
            }
            if (inHouseRd.isSelected() ) {
                int MachineID = Integer.parseInt((ModifyMachineField.getText()));

                Part part = (new inHouse(MachineID, ID, Name, Price, Inventory, Min, Max));
                model.Inventory.updatePart(part.getId(), part);
            } else {
                String companyName = ModifyMachineField.getText();

                //check max min options before saving the part and Update the part.

                Part part = (new Outsourced(companyName, ID, Name, Price, Inventory, Min, Max));
                model.Inventory.updatePart(part.getId(), part);
            }

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
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

    public void CancelModifyPart(ActionEvent event)  throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/mainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void receivePart(Part part) {
        ModifyIDField.setText(String.valueOf(part.getId()));
        ModifyNameField.setText(part.getName());
        ModifyInventoryField.setText(String.valueOf(part.getStock()));
        ModifyCostField.setText(String.valueOf(part.getPrice()));
        ModifyMaxField.setText(String.valueOf(part.getMax()));
        ModifyMinField.setText(String.valueOf(part.getMin()));
        if (part instanceof inHouse) {
            InOutLabel.setText("Machine ID:");
            inHouseRd.setSelected(true);
            ModifyMachineField.setText(String.valueOf(((inHouse) part).getMachineID()));
        } else {
            InOutLabel.setText("Company Name:");
            OutsourcedRd.setSelected(true);
            ModifyMachineField.setText(((Outsourced) part).getCompanyName());
        }


    }

    public void InHouseRadio(ActionEvent event) {
        InOutLabel.setText("Machine ID:");
    }

    public void OutsourcedRadio(ActionEvent event) {
        InOutLabel.setText("Company Name:");
    }
    private void errorWindow(int code) {
        if (code == 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Inventory Error");
            alert.setContentText("Inventory count is not between the Maximum and Minimum values.");
            alert.showAndWait();
        }
        if(code == 2){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Max and Min Error");
            alert.setContentText("The inventory maximum cannot be less than the minimum.");
            alert.showAndWait();
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
