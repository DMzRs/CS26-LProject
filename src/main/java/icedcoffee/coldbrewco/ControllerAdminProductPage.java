package icedcoffee.coldbrewco;
import Main.Admin;
import Main.Product;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;

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
    @FXML
    private ImageView add;
    @FXML
    private Button deleteButton;

    // Method to initialize the controller
    @FXML
    public void initialize() {
        Product product = new Product();
        int lastId = product.lastProductId();

        // Counter for valid products
        int validProductIndex = 0;

        for (int i = 0; i < lastId; i++) {
            String productName = product.showProductName(i + 1);

            if (productName == null || productName.isEmpty()) {
                continue; // Skip invalid products
            }

            // Pass validProductIndex instead of loop index (i)
            listProducts(productName, "Current Quantity: " + product.getProductQuantity(i + 1), getClass().getResource("/ProductImages/" + productName + ".jpg").toExternalForm(), validProductIndex);

            // Increment the valid product index only for valid products
            validProductIndex++;
        }
    }


    // Method to add a product dynamically
    private void listProducts(String name, String quantity, String imagePath, int index) {
        Product product = new Product();
        Admin admin = new Admin();

        // Clone the productContainer
        Pane newProductContainer = new Pane();
        newProductContainer.setPrefHeight(productContainer.getPrefHeight());
        newProductContainer.setPrefWidth(productContainer.getPrefWidth());
        newProductContainer.setStyle(productContainer.getStyle());

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

            int currentQuantity = product.getProductQuantity(product.showProductId(name));
            String addedQuantityStr = JOptionPane.showInputDialog(null, "Enter Quantity to Add:", "Add Quantity", JOptionPane.QUESTION_MESSAGE);
            if (addedQuantityStr != null) {
                try {
                    int addedQuantity = Integer.parseInt(addedQuantityStr);
                    admin.addProductQuantity(product.showProductId(name), currentQuantity + addedQuantity); // Update quantity in database
                    newQuantityLabel.setText("Current Quantity: " + (currentQuantity + addedQuantity)); // Update label
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Enter valid number!", "Error", JOptionPane.ERROR_MESSAGE);
                }
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

            int currentQuantity = product.getProductQuantity(product.showProductId(name));
            if (currentQuantity > 0) {
                String deductedQuantityStr = JOptionPane.showInputDialog(null, "Enter Quantity to Remove:", "Remove Quantity", JOptionPane.QUESTION_MESSAGE);
                if (deductedQuantityStr != null) {
                    try {
                        int deductedQuantity = Integer.parseInt(deductedQuantityStr);
                        if (deductedQuantity <= currentQuantity) {
                            admin.removeProductQuantity(product.showProductId(name), currentQuantity - deductedQuantity); // Update quantity in database
                            newQuantityLabel.setText("Current Quantity: " + (currentQuantity - deductedQuantity)); // Update label
                        } else {
                            JOptionPane.showMessageDialog(null, "You can't remove more than the current quantity!");
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Enter valid number!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        newProductContainer.getChildren().add(newRemoveButton);

        Button newDeleteButton = new Button("X");
        newDeleteButton.setFont(deleteButton.getFont());
        newDeleteButton.setLayoutX(deleteButton.getLayoutX());
        newDeleteButton.setLayoutY(deleteButton.getLayoutY());
        newDeleteButton.setUserData(index);
        newDeleteButton.setOnAction(event -> {

            String productName = (String) newProductContainer.getUserData();

            int productIndex = (int) newDeleteButton.getUserData(); // Retrieve the index of the product

            // Ask for confirmation before deletion
            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the product: " + productName + "?", "Delete Product", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    // Fetch product data (e.g., image path and database ID)
                    String productImagePath = getClass().getResource("/ProductImages/" + name + ".jpg").toExternalForm();  // You can modify this method to retrieve the image path

                    // 1. Delete the product from the database
                    admin.removeProduct(name);  // Method in Admin to delete the product from the database

                    // 2. Delete the image from the file system (target and resource folder)
                    File productImageFile = new File(productImagePath);
                    if (productImageFile.exists()) {
                        boolean imageDeleted = productImageFile.delete();
                        if (!imageDeleted) {
                            JOptionPane.showMessageDialog(null, "Failed to delete the image file.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Image file not found.");
                    }

                    // 3. Remove the product from the UI (from the scrollAnchor container)
                    scrollAnchor.getChildren().remove(newProductContainer);

                    // Optionally update the scrollAnchor's height if needed (similar to your existing code)

                    // Adjust the height of the empty container to match the new number of products
                    Pane emptyContainer = (Pane) scrollAnchor.lookup("#emptyContainer");
                    emptyContainer.setLayoutY((index) * (productContainer.getPrefHeight() + 20.0) + 30);  // Adjusted positioning for empty container
                    scrollAnchor.setPrefHeight(scrollAnchor.getPrefHeight() - productContainer.getPrefHeight() - 20.0);

                    JOptionPane.showMessageDialog(null, "Product " + productName + " deleted successfully.");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error occurred while deleting the product: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        newProductContainer.getChildren().add(newDeleteButton);

        // Calculate vertical placement: Each pane's Y-coordinate is the index multiplied by pane height + margin
        double spacing = 20.0;  // Increased spacing between product containers
        double containerHeight = productContainer.getPrefHeight();

        // Update the layout for the new product container
        newProductContainer.setLayoutX(productContainer.getLayoutX());
        newProductContainer.setLayoutY(productContainer.getLayoutY() + index * (containerHeight + spacing));

        // Add the new product container to the scrollAnchor
        scrollAnchor.getChildren().add(newProductContainer);

        // Increase the spacing for the empty container
        Pane emptyContainer = (Pane) scrollAnchor.lookup("#emptyContainer");
        emptyContainer.setLayoutY((index + 1) * (containerHeight + spacing) + 30);  // Added extra margin for empty container
        scrollAnchor.setPrefHeight(scrollAnchor.getPrefHeight() + containerHeight + spacing + 30);  // Adjusted the scrollAnchor height
    }


    //go back to admin main page
    @FXML
    private void backtoMainPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppLogin.class.getResource("AdminMainPage.fxml"));
        Scene adminPage = new Scene(fxmlLoader.load(), 900, 700);

        Stage currentStage = (Stage) backToAdminMain.getScene().getWindow();
        currentStage.setScene(adminPage);
        currentStage.setTitle("Admin Main Page");
        currentStage.setResizable(false);
        currentStage.centerOnScreen();
        currentStage.show();
    }

    //go to add product page
    @FXML
    private void onAddProductClicked() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppLogin.class.getResource("addProductPage.fxml"));
        Scene adminPage = new Scene(fxmlLoader.load(), 900, 700);

        Stage currentStage = (Stage) add.getScene().getWindow();
        currentStage.setScene(adminPage);
        currentStage.setTitle("Add Product");
        currentStage.setResizable(false);
        currentStage.centerOnScreen();
        currentStage.show();
    }


}

