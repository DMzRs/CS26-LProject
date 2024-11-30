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
                }

                // Ensure the destination directory exists in `target/classes`
                Path imageDir = Paths.get("target/classes/ProductImages");
                Files.createDirectories(imageDir);

                // Save the image in target/classes
                String newImageName = productName + ".jpg";
                Path targetImagePath = imageDir.resolve(newImageName);
                BufferedImage bufferedImage = ImageIO.read(selectedImageFile);
                File targetOutputFile = targetImagePath.toFile();
                ImageIO.write(bufferedImage, "jpg", targetOutputFile);
                System.out.println("Image saved successfully at: " + targetImagePath.toAbsolutePath());

                // Save the image in the resources/ProductImages directory
                Path resourcesImageDir = Paths.get("src/main/resources/ProductImages");
                Files.createDirectories(resourcesImageDir);
                Path resourcesImagePath = resourcesImageDir.resolve(newImageName);
                File resourcesOutputFile = resourcesImagePath.toFile();
                ImageIO.write(bufferedImage, "jpg", resourcesOutputFile);
                System.out.println("Image saved successfully at resources path: " + resourcesImagePath.toAbsolutePath());

                // Add the product (assuming `admin.addProduct` handles adding the product to your data source)
                admin.addProduct(productName, quantity, ingredients, price);

                // Clear the input fields and reset the ImageView
                newProductName.clear();
                newQuantity.clear();
                newPrice.clear();
                newIngredients.clear();
                attachImage.setImage(null);

                // Show success message
                JOptionPane.showMessageDialog(null, "Product added successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);

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
}
