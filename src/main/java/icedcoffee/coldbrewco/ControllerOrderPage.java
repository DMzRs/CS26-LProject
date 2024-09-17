package icedcoffee.coldbrewco;

import Database.DatabaseShow;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.io.InputStream;
import javafx.scene.layout.StackPane;
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


    //to go back to caramel mach
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

    @FXML
    private void showProductDetails(int productId) throws IOException {
        DatabaseShow show = new DatabaseShow();
        String Name = show.showProductName(productId);
        String Description = show.showProductDescription(productId);
        int price = show.showProductPrice(productId);

        nameBox.setText(Name);
        descriptionBox.setText(Description);
        priceBox.setText(String.valueOf(price));

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

    @FXML
    protected void addQuantityButton() throws IOException {

    }
}
