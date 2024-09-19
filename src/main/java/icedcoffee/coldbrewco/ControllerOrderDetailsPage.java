package icedcoffee.coldbrewco;

import javafx.fxml.FXML;

import javax.swing.table.TableColumn;
import javax.swing.text.TableView;
import java.beans.PropertyChangeListener;

public class ControllerOrderDetailsPage {
    @FXML
    private TableView orderTable;
    @FXML
    private TableColumn nameColumn;
    @FXML
    private TableColumn quantityColumn;
    @FXML
    private TableColumn priceColumn;
    @FXML
    private TableColumn totalPriceColumn;

    //set coffee name column
    @FXML
    public void setOrderNameColumn(String orderName) {
        System.out.println("setOrderNameColumn: " + orderName);
    }

    //set coffee quantity column
    @FXML
    public void setOrderQuantityColumn(int orderQuantity) {
        System.out.println("setOrderQuantityColumn: " + orderQuantity);
    }

    //set coffee quantity column
    @FXML
    public void setOrderPriceColumn(int orderPrice) {
        System.out.println("setOrderPriceColumn: " + orderPrice);
    }

    @FXML
    public void setOrderTotalPriceColumn(int orderTotalPrice) {
        System.out.println("setOrderTotalPriceColumn: " + orderTotalPrice);
    }

}
