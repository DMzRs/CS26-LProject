package icedcoffee.coldbrewco;

import ForEnkeepingLoginId.AdminId;
import Main.Admin;
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
        Admin admin = new Admin();
        greetLabel.setText(admin.showAdminName(AdminId.getAdminId()));
    }

    @FXML
    private void addEmployeeAccount() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppLogin.class.getResource("createNewEmpAcc.fxml"));
        Scene createAcc = new Scene(fxmlLoader.load(), 900, 700);

        Stage currentStage = (Stage) greetLabel.getScene().getWindow();
        currentStage.setScene(createAcc);
        currentStage.setTitle("Create New Employee Account");
        currentStage.setResizable(false);
        currentStage.centerOnScreen();
        currentStage.show();
    }

    @FXML
    private void checkEmployeeSalesButton() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppLogin.class.getResource("AdminTotalSalePage.fxml"));
        Scene salesPage = new Scene(fxmlLoader.load(), 900, 700);

        Stage currentStage = (Stage) greetLabel.getScene().getWindow();
        currentStage.setScene(salesPage);
        currentStage.setTitle("Admin Sales");
        currentStage.setResizable(false);
        currentStage.centerOnScreen();
        currentStage.show();
    }

    @FXML
    private void productPageButton() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppLogin.class.getResource("AdminProductStocks.fxml"));
        Scene productPage = new Scene(fxmlLoader.load(), 900, 700);
        Stage currentStage = (Stage) greetLabel.getScene().getWindow();
        currentStage.setScene(productPage);
        currentStage.setTitle("Coffee Stocks");
        currentStage.setResizable(false);
        currentStage.centerOnScreen();
        currentStage.show();
    }
    @FXML
    private void LogoutButton() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppLogin.class.getResource("adminLogin.fxml"));
        Scene adminLogin = new Scene(fxmlLoader.load(), 650, 400);

        Stage currentStage = (Stage) greetLabel.getScene().getWindow();
        currentStage.setScene(adminLogin);
        currentStage.setTitle("Admin Login");
        currentStage.setResizable(false);
        currentStage.centerOnScreen();
        currentStage.show();
    }

    @FXML
    private void ExitButton() throws IOException {
        System.exit(0);
    }
}
