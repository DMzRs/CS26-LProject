package icedcoffee.coldbrewco;

import Database.DatabaseShow;
import Database.DatabaseUpdate;
import Database.OrderItem;
import Database.OrderItemStorage;
import icedcoffee.coldbrewco.ControllerOrderDetailsPage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.io.InputStream;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerOrderPage {
    @FXML
    private ImageView backButton;
    @FXML
    private ImageView backButton1;
    @FXML
    private ImageView CaraMacImage;
    @FXML
    private ImageView SpanishLatImage;
    @FXML
    private ImageView VanillaLatImage;
    @FXML
    private ImageView IcedAmericanoImage;
    @FXML
    private ImageView MatchaLatteImage;
    @FXML
    private ImageView StrawberryLatteImage;
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
    @FXML
    private Label NotAvailable;


    //to go back to main Page
    @FXML
    protected void onBackButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppLogin.class.getResource("MainPage.fxml"));
        Scene mainAccount = new Scene(fxmlLoader.load(), 900, 700);

        Stage currentStage = (Stage) backButton.getScene().getWindow();
        currentStage.setScene(mainAccount);
        currentStage.setTitle("Main Page");
        currentStage.centerOnScreen();
        currentStage.show();
    }

    //to order page caramel macchiato
    @FXML
    protected void onCaramelMachClick() throws IOException {
        onCoffeeClick(1);
    }
    @FXML
    protected void onSpanishLatteClick() throws IOException {
        onCoffeeClick(2);
    }
    @FXML
    protected void onVanillaLatteClick() throws IOException {
        onCoffeeClick(3);
    }
    @FXML
    protected void onIcedAmeClick() throws IOException {
        onCoffeeClick(4);
    }
    @FXML
    protected void onMatchaLatteClick() throws IOException {
        onCoffeeClick(5);
    }
    @FXML
    protected void onStrawberryClick() throws IOException {
        onCoffeeClick(6);
    }

    //go back to main order page
    @FXML
    protected void onBackButton1Click() throws IOException {
        BacktoOrderPage();
    }



    @FXML
    private void BacktoOrderPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppLogin.class.getResource("Order Page.fxml"));
        Scene gobackOrderselect = new Scene(fxmlLoader.load(), 900, 700);

        Stage currentStage = (Stage) backButton1.getScene().getWindow();
        currentStage.setScene(gobackOrderselect);
        currentStage.setTitle("Order Page");
        currentStage.centerOnScreen();
        currentStage.show();
    }


    //to switch to select coffee window
    @FXML
    private void onCoffeeClick(int productId) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppLogin.class.getResource("OrderPageSpecificProduct.fxml"));
        Pane orderPagePane = fxmlLoader.load();
        ControllerOrderPage controller = fxmlLoader.getController();
        controller.showProductDetails(productId);
        Stage currentStage = (Stage) backButton.getScene().getWindow();
        Pane rootPane = (Pane) currentStage.getScene().getRoot();
        rootPane.getChildren().add(orderPagePane);
        orderPagePane.setLayoutX(rootPane.getWidth() - orderPagePane.getPrefWidth());
    }


    //to show product details when clicked in order page
    @FXML
    private void showProductDetails(int productId) throws IOException {
        DatabaseShow show = new DatabaseShow();
        String Name = show.showProductName(productId);
        String Description = show.showProductDescription(productId);
        int price = show.showProductPrice(productId);
        int availableQuantityInt = show.getProductQuantity(productId);

        nameBox.setText(Name);
        descriptionBox.setText(Description);
        priceBox.setText(String.valueOf(price));
        availableQuantity.setText(String.valueOf(availableQuantityInt));
        NotAvailable.setVisible(availableQuantityInt <= 0);

        String imagePath = ""; // Initialize image path

        // Determine the correct image path based on productId
        switch (productId) {
            case 1:
                imagePath = "/images/CaramelMacchiato.jpg";
                break;
            case 2:
                imagePath = "/images/SpanishLatte.jpg";
                break;
            case 3:
                imagePath = "/images/VanillaLatte.jpg";
                break;
            case 4:
                imagePath = "/images/IcedAmericano.jpg";
                break;
            case 5:
                imagePath = "/images/MatchaLatte.jpg";
                break;
            case 6:
                imagePath = "/images/StrawberryMatchaLatte.jpg";
                break;
            default:
                System.out.println("Invalid product ID");
                return; // Exit if product ID is invalid
        }

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

    //increase number of orders
    @FXML
    protected void addQuantityButton() {
        int availableQuantityInt = Integer.parseInt(availableQuantity.getText());
        String quantityString = orderQuantityLabel.getText();
        int quantity = Integer.parseInt(quantityString);
        if(quantity<availableQuantityInt) {
            quantity = quantity + 1;
            orderQuantityLabel.setText(String.valueOf(quantity));
        }
    }

    //decrease number of orders
    @FXML
    protected void reduceQuantityButton(){
        int quantity = Integer.parseInt(orderQuantityLabel.getText());
        if(quantity>1) {
            quantity = quantity - 1;
            orderQuantityLabel.setText(String.valueOf(quantity));
        }
    }

    //to add Order to Order Details
    @FXML
    protected void addOrderButton() throws IOException {
        String coffeeName = nameBox.getText();
        int coffeePriceInt = Integer.parseInt(priceBox.getText());
        int orderQuantity = Integer.parseInt(orderQuantityLabel.getText());

        // Create the new OrderItem
        OrderItem newOrder = new OrderItem(coffeeName, coffeePriceInt, orderQuantity);

        // Get the current selected items from the storage
        ObservableList<OrderItem> currentItems = OrderItemStorage.getInstance().getSelectedItems();

        // Check if the item already exists in the storage
        for (OrderItem existingItem : currentItems) {
            if (existingItem.getName().equals(coffeeName)) {
                existingItem.setQuantity(existingItem.getQuantity() + orderQuantity);
                return;
            }
        }

        // If the item does not exist, add the new order to the storage
        OrderItemStorage.getInstance().addItem(newOrder);
        BacktoOrderPage();
    }


    //to switch to Order Details Page
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
        currentStage.setTitle("Order Details Page");
        currentStage.centerOnScreen();
        currentStage.show();
    }
}
