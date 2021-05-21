package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Inventory;
import model.Outsourced;
import model.Product;
import model.inHouse;
//************************************************ Javadoc folder is located in the top level of the project folder labeled Javadoc.***********
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{// launches the main screen through the loader.
        Parent root = FXMLLoader.load(getClass().getResource("/View/mainMenu.fxml"));
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root, 900, 350));
        primaryStage.show();
    }



    public static void main(String[] args) {

        // pre-populated Parts data for the program.
        inHouse bolt = new inHouse(1, 1 ,"Bolt", 1.00, 25,0,50);
        inHouse nut = new inHouse(1,3,"Nut", .50, 25,0, 50);
        Outsourced Frame = new Outsourced("Bike Frames R Us", 5, "Frame", 50.00, 4, 2,10);
        Outsourced Wheel = new Outsourced("Hotwheels", 7, "Wheel", 25.00, 8, 2,10);
        Outsourced Red = new Outsourced("Painters", 9, "Red Paint", 5.00, 8, 0,10);
        Outsourced Blue = new Outsourced("Painters", 11, "BLue Paint", 5.00, 8, 0,10);
        Outsourced Pink = new Outsourced("Painters", 13, "Pink Paint", 5.00, 8, 0,10);
        Outsourced Black = new Outsourced("Painters", 15, "Black Paint", 5.00, 8, 0,10);
        Inventory.addPart(bolt);
        Inventory.addPart(nut);
        Inventory.addPart(Frame);
        Inventory.addPart(Wheel);
        Inventory.addPart(Red);
        Inventory.addPart(Blue);
        Inventory.addPart(Pink);
        Inventory.addPart(Black);


        //Pre-populated product data.
        Product Bike = new Product(2,"HotRod Bike", 500.00, 2,4,0);
        Product Bike2 = new Product(4,"Red Bike", 755.00, 1,4,0);
        Product Bike3 = new Product(6,"Blue Bike", 300.00, 2,4,0);
        Product Bike4 = new Product(8,"Pink Bike", 400.00, 2,4,0);
        Bike.addAssociatedPart(Frame);
        Bike.addAssociatedPart(Wheel);
        Bike.addAssociatedPart(Red);
        Bike.addAssociatedPart(Black);
        Bike2.addAssociatedPart(Frame);
        Bike2.addAssociatedPart(Wheel);
        Bike2.addAssociatedPart(Red);
        Bike3.addAssociatedPart(Frame);
        Bike3.addAssociatedPart(Wheel);
        Bike3.addAssociatedPart(Blue);
        Inventory.addProduct(Bike);
        Inventory.addProduct(Bike2);
        Inventory.addProduct(Bike3);
        Inventory.addProduct(Bike4);



        launch(args);
    }
}
