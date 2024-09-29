package icedcoffee.coldbrewco;


import Database.OrderItem;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.print.PrinterJob;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.util.List;

public class ControllerReceiptPage {


    @FXML
    private Label receiptContactNumber;
    @FXML
    private TextArea receiptTextArea;


    // Method to set order items and display in the TextArea
    public void setOrderItems(List<OrderItem> orderItems, int moneyReceived) {
        StringBuilder receiptContent = new StringBuilder();
        int totalPrice = 0;

        // Add the current date
        receiptContent.append("\n\n");
        receiptContent.append(String.format("%50s%n", "COLD BREW CORP"));
        receiptContent.append("\n\n");
        receiptContent.append(String.format("%70s%n", "Contect Number: 0967215052"));
        receiptContent.append(String.format("%80s%n", "Date: " + java.time.LocalDate.now()));

        // Header separator
        receiptContent.append("__________________________________________________\n");

        // Header formatting with consistent widths
        receiptContent.append("|        COFFEE NAME         |        QUANTITY       |    PRICE   |\n");
        receiptContent.append("|________________________________________________|\n");

        // Loop through the order items and format each line
        for (OrderItem item : orderItems) {
            receiptContent.append(String.format("| %-35s  %-10d  %-10d |\n",
                    item.getName(),
                    item.getQuantity(),
                    item.getSubTotal()));
            totalPrice += item.getSubTotal();
        }

        // Footer separator
        receiptContent.append("|________________________________________________|\n");
        // Total, Money Received, and Change aligned to the right
        receiptContent.append(String.format("                                                             %s %d%n", "Total Price:", totalPrice));
        receiptContent.append(String.format("                                                             %s %d%n", "Money Received:", moneyReceived));
        receiptContent.append(String.format("                                                             %s %d%n", "Change:", moneyReceived - totalPrice));

        // Set the content in the TextArea
        receiptTextArea.setText(receiptContent.toString());
    }



    // Method to print the content of the TextArea
    @FXML
    public void onPrintButtonClick() {
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        if (printerJob != null && printerJob.showPrintDialog(receiptTextArea.getScene().getWindow())) {
            boolean success = printerJob.printPage(receiptTextArea);
            if (success) {
                printerJob.endJob();
                JOptionPane.showMessageDialog(null,"DONE PRINTING");
            }
        }
    }


    //Close Event to go Back to Main Page
    @FXML
    protected void onCloseButtonClicked() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppLogin.class.getResource("MainPage.fxml"));
        Scene mainPage = new Scene(fxmlLoader.load(), 900, 700);

        Stage currentStage = (Stage) receiptContactNumber.getScene().getWindow();
        currentStage.setScene(mainPage);
        currentStage.setTitle("Main Page");
        currentStage.centerOnScreen();
        currentStage.show();
    }

}
