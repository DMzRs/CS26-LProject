package icedcoffee.coldbrewco;

import Database.DatabaseShow;
import ForEnkeepingLoginId.LoginId;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerOrderList {
    @FXML
    private ImageView backToMain;

    @FXML
    protected void showOrderListButtonClick(){
        DatabaseShow show = new DatabaseShow();
        int userId = LoginId.getLoginId();
        while(show.showOrderId(userId)>0) {
            System.out.println(show.showOrderId(userId));
        }
    }
    @FXML
    protected void onSwitchToMainButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppLogin.class.getResource("MainPage.fxml"));
        Scene mainPage = new Scene(fxmlLoader.load(), 900, 700);

        Stage currentStage = (Stage) backToMain.getScene().getWindow();
        currentStage.setScene(mainPage);
        currentStage.setTitle("Main Page");
        currentStage.centerOnScreen();
        currentStage.show();
    }
}
