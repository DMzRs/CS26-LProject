package icedcoffee.coldbrewco;

import Database.DatabaseShow;
import Database.OrderItemStorage;
import Database.PreviousOrders;
import ForEnkeepingLoginId.LoginId;
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

public class ControllerEmployeeSales {
    @FXML
    private ImageView backToMain;
    @FXML
    private Label totalSold;
    @FXML
    private TableView<PreviousOrders> previousOrderTable;
    @FXML
    private TableColumn<PreviousOrders, String> previousProductNameColumn;
    @FXML
    private TableColumn<PreviousOrders, Integer> previousPriceColumn;
    @FXML
    private TableColumn<PreviousOrders, Integer> previousQuantityColumn;
    @FXML
    private TableColumn<PreviousOrders, String> previousDateColumn;
    @FXML
    private TableColumn<PreviousOrders, Integer> previousSubTotalColumn;

    @FXML
    private void initialize() {
        DatabaseShow show = new DatabaseShow();
        previousProductNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        previousPriceColumn.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
        previousQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("productQuantity"));
        previousDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        previousSubTotalColumn.setCellValueFactory(new PropertyValueFactory<>("subTotal"));

        totalSold.setText(" "+show.totalSaleOnSpecificEmployee(LoginId.getLoginId()));
        previousOrderTable.setItems(show.getPreviousOrdersEmployee(LoginId.getLoginId()));
    }


    @FXML
    protected void onSwitchToMainButtonClick() throws IOException {
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
