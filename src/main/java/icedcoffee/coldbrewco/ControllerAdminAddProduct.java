package icedcoffee.coldbrewco;

import Main.Admin;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

public class ControllerAdminAddProduct {
    @FXML
    private ImageView back;
    @FXML
    private ImageView attachImage;
    @FXML
    private TextField newProductName;
    @FXML
    private TextField newQuantity;
    @FXML
    private TextField newPrice;
    @FXML
    private TextField newIngredients;
    @FXML
    private Label addProduct;

    private File selectedImageFile;

    @FXML
    private void backToProductStocks() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppLogin.class.getResource("AdminProductStocks.fxml"));
        Scene adminPage = new Scene(fxmlLoader.load(), 900, 700);
        Stage currentStage = (Stage) back.getScene().getWindow();
        currentStage.setScene(adminPage);
        currentStage.setTitle("Coffee Stocks");
        currentStage.setResizable(false);
        currentStage.centerOnScreen();
        currentStage.show();
    }

    //method to select Image to assign in new product
    @FXML
    private void setImageUpload() {
        // Open a file chooser to allow the user to select an image
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.gif", "*.jpeg"));
        Stage stage = (Stage) attachImage.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            attachImage.setImage(image);

            selectedImageFile = selectedFile;
        }
    }

    //to add the new product
    @FXML
    private void AddProduct() {
        Admin admin = new Admin();
        if (selectedImageFile != null) {
            try {
                // Retrieve user inputs
                String productName = newProductName.getText().trim();
                int quantity = Integer.parseInt(newQuantity.getText().trim());
                int price = Integer.parseInt(newPrice.getText().trim());
                String ingredients = newIngredients.getText().trim();

                // Validate product name
                if (productName.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Product name cannot be empty!",
                            "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    return;
                } else if (ingredients.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Ingredients cannot be empty!",
                            "Invalid Input", JOptionPane.ERROR_MESSAGE);
                } else {

                    if (admin.isProductNameExisting(productName)) {
                        JOptionPane.showMessageDialog(null, "Product name is already existing!");
                    } else {

                        // Ensure the destination directories exist
                        Path targetImageDir = Paths.get("target/classes/ProductImages");
                        Path resourcesImageDir = Paths.get("src/main/resources/ProductImages");
                        Files.createDirectories(targetImageDir); // Ensure `target/classes` directory exists
                        Files.createDirectories(resourcesImageDir); // Ensure `resources` directory exists

                        // Save the image in both locations
                        String newImageName = productName + ".jpg";

                        // Save to `target/classes`
                        Path targetImagePath = targetImageDir.resolve(newImageName);
                        saveImageToFile(selectedImageFile, targetImagePath);

                        // Save to `src/main/resources`
                        Path resourcesImagePath = resourcesImageDir.resolve(newImageName);
                        saveImageToFile(selectedImageFile, resourcesImagePath);

                        // Add the product to the system (assuming `admin.addProduct` does this)
                        admin.addProduct(productName, quantity, ingredients, price);

                        // Clear input fields and reset the ImageView
                        newProductName.clear();
                        newQuantity.clear();
                        newPrice.clear();
                        newIngredients.clear();

                        Path placeholderImagePath = Paths.get("src/main/resources/ProductImages/add.png");
                        attachImage.setImage(new Image(placeholderImagePath.toUri().toString()));

                        // Show success message
                        JOptionPane.showMessageDialog(null, "Product added and image saved successfully!",
                                "Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error while saving the image: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter valid numeric values for Quantity and Price.",
                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // If no image is selected
            JOptionPane.showMessageDialog(null, "You must select an image first!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Utility method to ensure the image is saved on the file destination
     *
     * @param sourceFile The source image file to be saved.
     * @param destinationPath The path where the image should be saved.
     * @throws IOException If an error occurs during file writing.
     */
    private void saveImageToFile(File sourceFile, Path destinationPath) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(sourceFile);
        File destinationFile = destinationPath.toFile();
        ImageIO.write(bufferedImage, "jpg", destinationFile);
    }
}