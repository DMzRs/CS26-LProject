package icedcoffee.coldbrewco;

import ObservableTableOrganizers.OrderItem;
import ObservableTableOrganizers.OrderItemStorage;
import Main.Admin;
import Main.Product;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

public class ControllerOrderPage {
    @FXML
    private ImageView backButton;
    @FXML
    private ImageView backButton1;
    @FXML
    private TilePane productTilePane;
    @FXML
    private Pane specificOrderPane;
    @FXML
    private Label addOrderButton;
    @FXML
    private Label quantLabel;
    @FXML
    private ImageView minusButton;
    @FXML
    private ImageView plusButton;
    @FXML
    private Label AvailableStocksLabel;
    @FXML
    private ImageView specificImageBox;
    @FXML
    private Label nameBox;
    @FXML
    private Label priceBox;
    @FXML
    private Label descriptionBox;
    @FXML
    private Label orderQuantityLabel;
    @FXML
    private Label availableQuantity;

    // Initialize the page with products dynamically
    @FXML
    public void initialize() {
        specificOrderPane.setVisible(false);
        if (productTilePane == null) {
            System.out.println("productTilePane is null!");
            return;
        }

        Product product = new Product();
        for (int productId = 1; productId <= product.lastProductId(); productId++) {
            String prodName = product.showProductName(productId);
            if (prodName == null || prodName.isEmpty()) continue;


            String imagePath = "/ProductImages/" + prodName + ".jpg";

            // tile for each product
            Pane tile = new Pane();
            tile.setPrefWidth(150);
            tile.setPrefHeight(200);
            tile.setStyle("-fx-background-color: #F5F5F5; -fx-border-color: #CCCCCC; -fx-border-width: 1;");

            // Create the product image
            ImageView productImage = new ImageView();
            productImage.setFitWidth(98);
            productImage.setFitHeight(119);
            productImage.setLayoutX(25);
            productImage.setLayoutY(20);
            InputStream imageStream = getClass().getResourceAsStream(imagePath);
            if (imageStream != null) {
                productImage.setImage(new Image(imageStream));
            } else {
                productImage.setImage(new Image(getClass().getResourceAsStream("/images/default.jpg"))); // Fallback image
            }

            // add product name
            Label productNameLabel = new Label(prodName);
            productNameLabel.setLayoutX(10);
            productNameLabel.setLayoutY(150);
            productNameLabel.setPrefWidth(130);
            productNameLabel.setWrapText(true);
            productNameLabel.setTextAlignment(TextAlignment.CENTER);
            productNameLabel.setStyle("-fx-font-family: 'Franklin Gothic Medium'; -fx-font-size: 14; -fx-text-fill: #333333;");
            productNameLabel.setAlignment(javafx.geometry.Pos.CENTER);


            tile.getChildren().addAll(productImage, productNameLabel);


            productTilePane.getChildren().add(tile);


            tile.setOnMouseClicked(event -> {
                try {
                    onCoffeeClick(product.showProductId(prodName));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    // Go back to the main order page
    @FXML
    protected void onBackButton1Click(){
        BacktoOrderPage();
    }

    // Go back to main page and clear the table on order details before going back
    @FXML
    protected void onBackButtonClick() throws IOException {
        Admin admin = new Admin();
        Product product = new Product();

        ObservableList<OrderItem> selectedItems = OrderItemStorage.getInstance().getSelectedItems();

        for (OrderItem item : selectedItems) {
            String itemName = item.getName();
            int itemQuantity = item.getQuantity();

            admin.productAddedBackFromTemporaryRemovedItems(product.showProductId(itemName), itemQuantity);
        }

        // Clear the order items before going back
        OrderItemStorage.getInstance().clearItems();

        FXMLLoader fxmlLoader = new FXMLLoader(AppLogin.class.getResource("MainPage.fxml"));
        Scene mainAccount = new Scene(fxmlLoader.load(), 900, 700);

        Stage currentStage = (Stage) backButton.getScene().getWindow();
        currentStage.setScene(mainAccount);
        currentStage.setTitle("Main Page");
        currentStage.setResizable(false);
        currentStage.centerOnScreen();
        currentStage.show();
    }

    @FXML
    private void BacktoOrderPage(){
        specificOrderPane.setVisible(false);
    }

    // Handle product selection to show details
    @FXML
    private void onCoffeeClick(int productId) throws IOException {
        specificOrderPane.setVisible(true);
        showProductDetails(productId);
    }



    // Show product details when clicked in order page
    @FXML
    private void showProductDetails(int productId) throws IOException {
        Product product = new Product();
        orderQuantityLabel.setText("1");
        AvailableStocksLabel.setText("Available Stocks:");

        String Name = product.showProductName(productId);
        String Description = product.showProductDescription(productId);
        int price = product.showProductPrice(productId);
        int availableQuantityInt = product.getProductQuantity(productId);

        nameBox.setText(Name);
        descriptionBox.setText(Description);
        priceBox.setText(String.valueOf(price));
        if (availableQuantityInt != 0) {
            availableQuantity.setVisible(true);
            availableQuantity.setText(String.valueOf(availableQuantityInt));
            addOrderButton.setVisible(true);
            plusButton.setVisible(true);
            minusButton.setVisible(true);
            quantLabel.setVisible(true);
            orderQuantityLabel.setVisible(true);
        } else {
            availableQuantity.setVisible(false);
            addOrderButton.setVisible(false);
            plusButton.setVisible(false);
            minusButton.setVisible(false);
            quantLabel.setVisible(false);
            orderQuantityLabel.setVisible(false);
            AvailableStocksLabel.setText("Out of Stock");
        }

        String imagePath = "/ProductImages/" + product.showProductName(productId) + ".jpg"; // Initialize image path

        // Load the image
        InputStream imageStream = getClass().getResourceAsStream(imagePath);
        if (imageStream != null) {
            specificImageBox.setImage(new Image(imageStream));
            specificImageBox.setFitWidth(98);
            specificImageBox.setFitHeight(119);
        } else {
            System.out.println("Image resource not found: " + imagePath);
        }
    }

    // Increase number of orders
    @FXML
    protected void addQuantityButton() {
        String quantityString = orderQuantityLabel.getText();
        int availableQuantityInt = Integer.parseInt(availableQuantity.getText());
        int quantity = Integer.parseInt(quantityString);
        if (quantity < availableQuantityInt) {
            quantity = quantity + 1;
            orderQuantityLabel.setText(String.valueOf(quantity));
        }
    }

    // Decrease number of orders
    @FXML
    protected void reduceQuantityButton() {
        int quantity = Integer.parseInt(orderQuantityLabel.getText());
        if (quantity > 1) {
            quantity = quantity - 1;
            orderQuantityLabel.setText(String.valueOf(quantity));
        }
    }

    // Add order to Order Details
    @FXML
    protected void addOrderButton() throws IOException {
        Admin admin = new Admin();
        Product product = new Product();

        String coffeeName = nameBox.getText();
        int coffeePriceInt = Integer.parseInt(priceBox.getText());
        int orderQuantity = Integer.parseInt(orderQuantityLabel.getText());
        admin.productTemporaryDeductionQuantity(product.showProductId(coffeeName), orderQuantity);

        // Create the new OrderItem
        OrderItem newOrder = new OrderItem(coffeeName, coffeePriceInt, orderQuantity);

        // Get the current selected items from the storage
        ObservableList<OrderItem> currentItems = OrderItemStorage.getInstance().getSelectedItems();

        // Check if the item already exists in the storage
        boolean itemExists = false;
        for (OrderItem existingItem : currentItems) {
            if (existingItem.getName().equals(coffeeName)) {
                existingItem.setQuantity(existingItem.getQuantity() + orderQuantity);
                itemExists = true; // Mark that the item exists
                break; // Exit the loop once found
            }
        }

        // If the item does not exist, add the new order to the storage
        if (!itemExists) {
            OrderItemStorage.getInstance().addItem(newOrder);
        }

        // Always return to the order page
        BacktoOrderPage();
    }

    // Switch to Order Details Page
    @FXML
    protected void proceedToOrderDetailsButton() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppLogin.class.getResource("OrderDetailsPage.fxml"));
        Scene orderDetails = new Scene(fxmlLoader.load(), 900, 700);

        // Get the controller instance of OrderDetailsPage
        ControllerOrderDetailsPage detailsController = fxmlLoader.getController();

        ObservableList<OrderItem> selectedItems = OrderItemStorage.getInstance().getSelectedItems();
        detailsController.setOrderItems(selectedItems);

        // Set the scene for the new stage
        Stage currentStage = (Stage) backButton.getScene().getWindow();
        currentStage.setScene(orderDetails);
        currentStage.setTitle("Order Details");
        currentStage.centerOnScreen();
        currentStage.setResizable(false);
        currentStage.show();
    }
}
