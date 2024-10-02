package icedcoffee.coldbrewco;

import Database.DatabaseShow;
import Database.EmployeeSales;
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
    private TableView <EmployeeSales> SalesTable;
    @FXML
    private TableColumn <EmployeeSales, String> empNameCol;
    @FXML
    private TableColumn <EmployeeSales, String> dateCol;
    @FXML
    private TableColumn <EmployeeSales, Integer> coffeeSoldCol;
    @FXML
    private TableColumn<EmployeeSales, Integer> salesPerEmpCol;
    @FXML
    private Label totalSold;

    @FXML
    private void initialize(){
        DatabaseShow show = new DatabaseShow();
    empNameCol.setCellValueFactory(new PropertyValueFactory<>("EmpName"));
    dateCol.setCellValueFactory(new PropertyValueFactory<>("Date"));
    coffeeSoldCol.setCellValueFactory(new PropertyValueFactory<>("TotalCoffeeSold"));
    salesPerEmpCol.setCellValueFactory(new PropertyValueFactory<>("SalesPerEmployee"));

    totalSold.setText(" "+show.showOverallSales());
    SalesTable.setItems(show.getEmployeeSales());
    }

    @FXML
    private void switchToAdminMainButton() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppLogin.class.getResource("AdminMainPage.fxml"));
        Scene adminPage = new Scene(fxmlLoader.load(), 900, 700);

        Stage currentStage = (Stage) backToAdminMain.getScene().getWindow();
        currentStage.setScene(adminPage);
        currentStage.setTitle("Admin Main Page");
        currentStage.centerOnScreen();
        currentStage.setResizable(false);
        currentStage.show();
    }
}
