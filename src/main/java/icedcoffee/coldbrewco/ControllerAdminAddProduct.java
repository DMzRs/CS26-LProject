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

    private File selectedImageFile;  // To store the selected image file

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
            // Display the selected image in the ImageView
            Image image = new Image(selectedFile.toURI().toString());
            attachImage.setImage(image);

            // Store the selected file for later use
            selectedImageFile = selectedFile;
        }
    }

    @FXML
    private void AddProduct() {
        Admin admin = new Admin();
        if (selectedImageFile != null) {
            try {
                String ProductName = newProductName.getText();
                int Quantity = Integer.parseInt(newQuantity.getText());
                int Price = Integer.parseInt(newPrice.getText());
                String Ingredients = newIngredients.getText();

                // Create the destination path and ensure the image is saved as a .jpg file
                String newImageName = newProductName.getText()+ ".jpg"; // Use the product name for the image filename
                Path destinationPath = Paths.get("src/main/resources/ProductImages/" + ProductName + ".jpg");
                BufferedImage bufferedImage = ImageIO.read(selectedImageFile);
                File outputFile = new File(destinationPath.toString());
                ImageIO.write(bufferedImage, "jpg", outputFile);
                System.out.println("Product added successfully with image: " + newImageName);


                admin.addProduct(ProductName, Quantity, Ingredients, Price);

                // Clear the fields after successful addition
                newProductName.clear();
                newQuantity.clear();
                newPrice.clear();
                newIngredients.clear();
                attachImage.setImage(null); // Clears the ImageView by setting the image to null


                JOptionPane.showMessageDialog(null, "Product added successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error while saving the image: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter valid numeric values for Quantity and Price.",
                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // If no image is selected
            JOptionPane.showMessageDialog(null, "You must select an Image First!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}

