package icedcoffee.coldbrewco;

import Database.DatabaseShow;
import ForEnkeepingLoginId.AdminId;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ControllerAdminMainPage {
    @FXML
    private Label greetLabel;

    @FXML
    private void initialize() {
        DatabaseShow show = new DatabaseShow();
        greetLabel.setText(show.showAdminName(AdminId.getAdminId()));
    }
    @FXML
    private void checkEmployeeSalesButton(){

    }
}
