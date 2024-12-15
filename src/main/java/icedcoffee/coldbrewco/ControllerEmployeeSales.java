package icedcoffee.coldbrewco;

import ObservableTableOrganizers.PreviousTransactionsOfSpecificEmployee;
import Main.Employee;
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

public class ControllerEmployeeSales extends ControllerLogin {
    @FXML
    private ImageView backToMain;
    @FXML
    private Label totalSold;
    @FXML
    private TableView<PreviousTransactionsOfSpecificEmployee> previousOrderTable;
    @FXML
    private TableColumn<PreviousTransactionsOfSpecificEmployee, String> previousProductNameColumn;
    @FXML
    private TableColumn<PreviousTransactionsOfSpecificEmployee, Integer> previousPriceColumn;
    @FXML
    private TableColumn<PreviousTransactionsOfSpecificEmployee, Integer> previousQuantityColumn;
    @FXML
    private TableColumn<PreviousTransactionsOfSpecificEmployee, String> previousDateColumn;
    @FXML
    private TableColumn<PreviousTransactionsOfSpecificEmployee, Integer> previousSubTotalColumn;

    @FXML
    private void initialize(){
        Employee employee = new Employee();
        previousProductNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        previousPriceColumn.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
        previousQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("productQuantity"));
        previousDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        previousSubTotalColumn.setCellValueFactory(new PropertyValueFactory<>("subTotal"));

        totalSold.setText(" "+employee.Sales(employee.getEmployeeId()));
        previousOrderTable.setItems(employee.showPreviousOrdersEmployee(employee.getEmployeeId()));
    }


    @FXML
    protected void switchtoMain() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppLogin.class.getResource("MainPage.fxml"));
        Scene mainPage = new Scene(fxmlLoader.load(), 900, 700);
        previousOrderTable.getItems().clear();

        Stage currentStage = (Stage) backToMain.getScene().getWindow();
        currentStage.setScene(mainPage);
        currentStage.setTitle("Main Page");
        currentStage.setResizable(false);
        currentStage.centerOnScreen();
        currentStage.show();
    }
}
