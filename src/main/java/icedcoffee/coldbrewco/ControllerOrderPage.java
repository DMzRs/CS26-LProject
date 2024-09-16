package icedcoffee.coldbrewco;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerOrderPage {
    @FXML
    private ImageView backButton;
    @FXML
    private ImageView backButton1;
    @FXML
    private ImageView logoMainOrder;

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
        onCoffeeClick(backButton);
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
        Scene Orderselect = new Scene(fxmlLoader.load(), 900, 700);

        Stage currentStage = (Stage) backButton1.getScene().getWindow();
        currentStage.setScene(Orderselect);
        currentStage.setTitle("Order Page");
        currentStage.centerOnScreen();
        currentStage.show();
    }

    //to select coffee
    @FXML
    private void onCoffeeClick(ImageView imageView) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppLogin.class.getResource("OrderPageSpecificProduct.fxml"));
        Scene selectCoffee = new Scene(fxmlLoader.load(), 900, 700);

        Stage currentStage = (Stage) imageView.getScene().getWindow();
        currentStage.setScene(selectCoffee);
        currentStage.setTitle("Order Page");
        currentStage.centerOnScreen();
        currentStage.show();
    }
}
