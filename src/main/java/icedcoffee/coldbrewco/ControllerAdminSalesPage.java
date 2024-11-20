package icedcoffee.coldbrewco;

import ObservableTableOrganizers.EmployeeTransactions;
import Main.Admin;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


import java.io.IOException;

public class ControllerAdminSalesPage {
    @FXML
    private ImageView backToAdminMain;
    @FXML
    private TableView <EmployeeTransactions> SalesTable;
    @FXML
    private TableColumn <EmployeeTransactions, String> empNameCol;
    @FXML
    private TableColumn <EmployeeTransactions, String> coffeeNameCol;
    @FXML
    private TableColumn <EmployeeTransactions, String> dateCol;
    @FXML
    private TableColumn <EmployeeTransactions, Integer> coffeeSoldCol;
    @FXML
    private TableColumn<EmployeeTransactions, Integer> salesPerEmpCol;
    @FXML
    private Label totalSold;

    @FXML
    private void initialize(){
        Admin admin = new Admin();

    empNameCol.setCellValueFactory(new PropertyValueFactory<>("EmpName"));
    coffeeNameCol.setCellValueFactory(new PropertyValueFactory<>("CoffeeName"));
    dateCol.setCellValueFactory(new PropertyValueFactory<>("Date"));
    coffeeSoldCol.setCellValueFactory(new PropertyValueFactory<>("SoldQuantity"));
    salesPerEmpCol.setCellValueFactory(new PropertyValueFactory<>("SalesPerEmployee"));

    totalSold.setText(" "+admin.totalSales());
    SalesTable.setItems(admin.showEmployeeTransactions());
    }

    @FXML
    private void switchToAdminMainButton() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppLogin.class.getResource("AdminMainPage.fxml"));
        Scene adminPage = new Scene(fxmlLoader.load(), 900, 700);
        SalesTable.getItems().clear();

        Stage currentStage = (Stage) backToAdminMain.getScene().getWindow();
        currentStage.setScene(adminPage);
        currentStage.setTitle("Admin Main Page");
        currentStage.centerOnScreen();
        currentStage.setResizable(false);
        currentStage.show();
    }
}
