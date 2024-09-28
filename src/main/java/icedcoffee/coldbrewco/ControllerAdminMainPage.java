package icedcoffee.coldbrewco;

import Database.DatabaseShow;
import ForEnkeepingLoginId.AdminId;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerAdminMainPage {
    @FXML
    private Label greetLabel;

    @FXML
    private void initialize() {
        DatabaseShow show = new DatabaseShow();
        greetLabel.setText(show.showAdminName(AdminId.getAdminId()));
    }

    @FXML
    private void checkEmployeeSalesButton() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppLogin.class.getResource("AdminTotalSalePage.fxml"));
        Scene salesPage = new Scene(fxmlLoader.load(), 900, 700);

        Stage currentStage = (Stage) greetLabel.getScene().getWindow();
        currentStage.setScene(salesPage);
        currentStage.setTitle("Admin Sales Page");
        currentStage.centerOnScreen();
        currentStage.show();
    }

    @FXML
    private void productPageButton() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppLogin.class.getResource("AdminProductPage.fxml"));
        Scene productPage = new Scene(fxmlLoader.load(), 900, 700);
        Stage currentStage = (Stage) greetLabel.getScene().getWindow();
        currentStage.setScene(productPage);
        currentStage.setTitle("Product Page");
        currentStage.centerOnScreen();
        currentStage.show();
    }
    @FXML
    private void LogoutButton() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppLogin.class.getResource("adminLogin.fxml"));
        Scene adminLogin = new Scene(fxmlLoader.load(), 650, 400);

        Stage currentStage = (Stage) greetLabel.getScene().getWindow();
        currentStage.setScene(adminLogin);
        currentStage.setTitle("Admin Login Page");
        currentStage.centerOnScreen();
        currentStage.show();
    }

    @FXML
    private void ExitButton() throws IOException {
        System.exit(0);
    }
}
