package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import java.util.List;
import ca.mcgill.ecse.assetplus.controller.*;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFxmlView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * <h1>ViewUtils</h1>
 * Utility class for handling views and displaying messages.
 * 
 * @author Tessa Hason, Yuri Sorice, Jerome Desrosiers, Mathieu Allaire, Luis Jarquin, Kevin Liu
 */
public class ViewUtils {

  /**
   * <h2>callController</h2>
   * Calls the controller and shows an error, if applicable.
   * 
   * @param result The result from the controller as a string.
   * @return boolean - Returns a boolean "True" if the call to the controller is successful; otherwise, returns "False".
   * 
   * @author Tessa Hason
   */
  public static boolean callController(String result) {
    if (result.isEmpty()) {
      AssetPlusFxmlView.getInstance().refresh();
      return true;
    }
    showError(result);
    return false;
  }

  /**
   * <h2>successful</h2>
   * Calls the controller and returns true on success. This method is included for readability.
   * 
   * @param controllerResult The result from the controller as a string.
   * @return boolean - Returns the boolean "True" if the call to the controller is successful; otherwise, returns "False".
   * 
   * @author Jerome Desrosiers
   */
  public static boolean successful(String controllerResult) {
    return callController(controllerResult);
  }

  /**
   * <h2>makePopupWindow</h2>
   * Creates a popup window.
   *
   * @param title Title of the popup window as a string.
   * @param message Message to display as a string.
   * 
   * @author Luis Jarquin
   */
  public static void makePopupWindow(String title, String message) {
    Stage dialog = new Stage();
    dialog.initModality(Modality.APPLICATION_MODAL);
    VBox dialogPane = new VBox();

    // create UI elements
    Text text = new Text(message);
    Button okButton = new Button("OK");
    okButton.setOnAction(a -> dialog.close());

    // display the popup window
    int innerPadding = 10; // inner padding/spacing
    int outerPadding = 100; // outer padding
    dialogPane.setSpacing(innerPadding);
    dialogPane.setAlignment(Pos.CENTER);
    dialogPane.setPadding(new Insets(innerPadding, innerPadding, innerPadding, innerPadding));
    dialogPane.getChildren().addAll(text, okButton);
    Scene dialogScene = new Scene(dialogPane, outerPadding + 5 * message.length(), outerPadding);
    dialog.setScene(dialogScene);
    dialog.setTitle(title);
    dialog.show();
  }
  /**
   * <h2>showError</h2>
   * Displays an error message in a popup window.
   *
   * @param message The error message to display as a string.
   * 
   * @author Yuri Sorice
   */
  public static void showError(String message) {
    makePopupWindow("Error", message);
  }

  /**
   * <h2>getTickets</h2>
   * Gets the maintenance tickets.
   *
   * @return ObservableList<TOMaintenanceTicket> - Returns an ObservableList containing all maintenance tickets as TOMaintenanceTicket objects.
   * 
   * @author Mathieu Allaire, Kevin Liu
   */
  public static ObservableList<TOMaintenanceTicket> getTickets() {
    List<TOMaintenanceTicket> TOMaintenanceTickets = AssetPlusFeatureSet6Controller.getTickets();
    // as javafx works with observable list, we need to convert the java.util.List to
    // javafx.collections.observableList
    return FXCollections.observableList(TOMaintenanceTickets);
  }

}