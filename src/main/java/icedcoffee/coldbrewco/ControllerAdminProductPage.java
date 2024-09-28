package icedcoffee.coldbrewco;
import Database.DatabaseShow;
import Database.DatabaseUpdate;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

public class ControllerAdminProductPage {
    @FXML
    private ImageView backToAdminMain;
    @FXML
    private AnchorPane scrollAnchor;

    @FXML
    private Pane productContainer;

    @FXML
    private Label nameContainer;

    @FXML
    private Label quantityContainer;

    @FXML
    private Button addButton;

    @FXML
    private Button removeButton;

    @FXML
    private ImageView imageContainer;
    // Method to initialize the controller (called after FXML file is loaded)
    @FXML
    public void initialize() {
        DatabaseShow show = new DatabaseShow();
        // Sample data for products
        String[][] products = {
                {"Caramel Macchiato", "Current Quantity: " + show.getProductQuantity(1), getClass().getResource("/images/CaramelMacchiato.jpg").toExternalForm()},
                {"Spanish Latte", "Current Quantity: " + show.getProductQuantity(2), getClass().getResource("/images/SpanishLatte.jpg").toExternalForm()},
                {"Vanilla Latte", "Current Quantity: " + show.getProductQuantity(3), getClass().getResource("/images/VanillaLatte.jpg").toExternalForm()},
                {"Iced Americano", "Current Quantity: " + show.getProductQuantity(4), getClass().getResource("/images/IcedAmericano.jpg").toExternalForm()},
                {"Matcha Latte", "Current Quantity: " + show.getProductQuantity(5), getClass().getResource("/images/MatchaLatte.jpg").toExternalForm()},
                {"Strawberry Matcha Latte", "Current Quantity: " + show.getProductQuantity(6), getClass().getResource("/images/StrawberryMatchaLatte.jpg").toExternalForm()}

        };

        // Add product containers dynamically
        for (int i = 0; i < products.length; i++) {
            addProduct(products[i][0], products[i][1], products[i][2], i);
        }
    }

    // Method to add a product dynamically
    private void addProduct(String name, String quantity, String imagePath, int index) {
        DatabaseUpdate update = new DatabaseUpdate();
        DatabaseShow show = new DatabaseShow();
        // Clone the productContainer
        Pane newProductContainer = new Pane();
        newProductContainer.setPrefHeight(productContainer.getPrefHeight());
        newProductContainer.setPrefWidth(productContainer.getPrefWidth());
        newProductContainer.setStyle(productContainer.getStyle());

        // Create and set ImageView for the product
        ImageView newImageView = new ImageView(imagePath);
        newImageView.setFitHeight(imageContainer.getFitHeight());
        newImageView.setFitWidth(imageContainer.getFitWidth());
        newImageView.setLayoutX(imageContainer.getLayoutX());
        newImageView.setLayoutY(imageContainer.getLayoutY());
        newProductContainer.getChildren().add(newImageView);

        // Create and set name label
        Label newNameLabel = new Label(name);
        newNameLabel.setFont(nameContainer.getFont());
        newNameLabel.setLayoutX(nameContainer.getLayoutX());
        newNameLabel.setLayoutY(nameContainer.getLayoutY());
        newProductContainer.getChildren().add(newNameLabel);

        // Create and set quantity label
        Label newQuantityLabel = new Label(quantity);
        newQuantityLabel.setFont(quantityContainer.getFont());
        newQuantityLabel.setLayoutX(quantityContainer.getLayoutX());
        newQuantityLabel.setLayoutY(quantityContainer.getLayoutY());
        newProductContainer.getChildren().add(newQuantityLabel);

        // Add button for adding quantity
        Button newAddButton = new Button("Add Quantity");
        newAddButton.setFont(addButton.getFont());
        newAddButton.setLayoutX(addButton.getLayoutX());
        newAddButton.setLayoutY(addButton.getLayoutY());
        newAddButton.setUserData(index); // Store the product index
        newAddButton.setOnAction(event -> {
            int productIndex = (int) newAddButton.getUserData(); // Retrieve the index
            int currentQuantity = show.getProductQuantity(productIndex + 1); // Fetch current quantity (adjust for index)
            String addedQuantityStr = JOptionPane.showInputDialog(null,"Enter Quantity to Add:","Add Quantity",JOptionPane.QUESTION_MESSAGE);
            if (addedQuantityStr != null) {
                try {
                    int addedQuantity = Integer.parseInt(addedQuantityStr);
                    update.addProductQuantity(productIndex + 1, currentQuantity + addedQuantity); // Update quantity in database
                    newQuantityLabel.setText("Current Quantity: " + (currentQuantity + addedQuantity)); // Update label
                    System.out.println("Add button for product " + productIndex + " clicked.");
                }catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null,"Enter valid number!","Error",JOptionPane.ERROR_MESSAGE);
                }
            } else {
                System.out.println("Cancelled");
            }
        });
        newProductContainer.getChildren().add(newAddButton);

        // Add button for removing quantity
        Button newRemoveButton = new Button("Remove Quantity");
        newRemoveButton.setFont(removeButton.getFont());
        newRemoveButton.setLayoutX(removeButton.getLayoutX());
        newRemoveButton.setLayoutY(removeButton.getLayoutY());
        newRemoveButton.setUserData(index); // Store the product index
        newRemoveButton.setOnAction(event -> {
            int productIndex = (int) newRemoveButton.getUserData(); // Retrieve the index
            int currentQuantity = show.getProductQuantity(productIndex + 1); // Fetch current quantity (adjust for index)
            if (currentQuantity > 0) {
                String deductedQuantityStr = JOptionPane.showInputDialog(null,"Enter Quantity to Remove:","Remove Quantity",JOptionPane.QUESTION_MESSAGE);
                if(deductedQuantityStr != null) {
                    try {
                        int deductedQuantity = Integer.parseInt(deductedQuantityStr);
                        if (deductedQuantity <= currentQuantity) {
                            update.deductProductQuantity(productIndex + 1, currentQuantity - deductedQuantity); // Update quantity in database
                            newQuantityLabel.setText("Current Quantity: " + (currentQuantity - deductedQuantity)); // Update label
                        } else {
                            JOptionPane.showMessageDialog(null,"You can't remove more than the current quantity!");
                        }
                    }catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null,"Enter valid number!","Error",JOptionPane.ERROR_MESSAGE);
                    }
                } else{
                System.out.println("Cancelled");
                }
            } else {
                System.out.println("Cannot remove quantity. Current quantity is 0.");
            }
            System.out.println("Remove button for product " + productIndex + " clicked.");
        });
        newProductContainer.getChildren().add(newRemoveButton);

        // Calculate vertical placement: Each pane's Y-coordinate is the index multiplied by pane height + margin
        double spacing = 10.0;  // Space between each product container
        double containerHeight = productContainer.getPrefHeight();
        newProductContainer.setLayoutX(productContainer.getLayoutX());
        newProductContainer.setLayoutY(productContainer.getLayoutY() + index * (containerHeight + spacing));

        // Add the new product container to the scrollAnchor
        scrollAnchor.getChildren().add(newProductContainer);
        scrollAnchor.setPrefHeight(scrollAnchor.getPrefHeight() + containerHeight + spacing);
    }

    @FXML
    private void backtoMainPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppLogin.class.getResource("AdminMainPage.fxml"));
        Scene adminPage = new Scene(fxmlLoader.load(), 900, 700);

        Stage currentStage = (Stage) backToAdminMain.getScene().getWindow();
        currentStage.setScene(adminPage);
        currentStage.setTitle("Admin Main Page");
        currentStage.centerOnScreen();
        currentStage.show();
    }

    @FXML
    private void AddQuantityButton(){

    }
}
