package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFxmlView;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet5Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet4Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.sql.Date;
// makes sure order is good for null checks

/**
 * <h1>MaintenancePageController</h1>
 * This class is the controller for the 'MaintenancePage.fxml' view.
 * 
 * @author Kevin Li
 */
public class MaintenancePageController {
  @FXML private TextField createTicketIdTextField, createTicketRaiserEmailTextField, createTicketDateTextField, createTicketDescriptionTextField, createTicketAssetNumberTextField,
        updateTicketIdTextField, updateTicketRaiserEmailTextField, updateTicketDateTextField, updateTicketDescriptionTextField, updateTicketAssetNumberTextField,
        imageTicketIdTextField, addImageURLTextField, deleteImageURLTextField;

  @FXML private Button createButton, updateButton, deleteButton, addImageButton, deleteImageButton;


  // Event Listener on Button[#addImageButton].onAction
  @FXML
  /**
   * <h2>addImageClicked</h2>
   * This method is called whenever an "add image" button is clicked on the maintenance page.
   * 
   * @param event This is the action of the "add image" button being clicked.
   * 
   * @author Kevin Li
   */
  void addImageClicked(ActionEvent event) {
    String url = addImageURLTextField.getText().trim();
    String idString = imageTicketIdTextField.getText().trim();
    if (url == null || url.trim().isEmpty()) {
      ViewUtils.showError("The URL cannot be empty. ");
    } else if (idString == null || idString.trim().isEmpty()) {
      ViewUtils.showError("The ticket ID cannot be empty. ");
    } else {
      // reset the text field if success
      int id = Integer.parseInt(idString);
      String error = AssetPlusFeatureSet5Controller.addImageToMaintenanceTicket(url,id);
      if (error.isEmpty()) {
        addImageURLTextField.setText("");
        imageTicketIdTextField.setText("");
      }
      else {
        ViewUtils.showError(error);
      }
    }
  }

  // Event Listener on Button[#deleteImageButton].onAction
  @FXML
  /**
   * <h2>deleteImageClicked</h2>
   * This method is called whenever a "delete image" button is clicked on the maintenance page.
   * 
   * @param event This is the action of the "delete image" button being clicked.
   * 
   * @author Kevin Li
   */
  void deleteImageClicked(ActionEvent event) {
    String url = deleteImageURLTextField.getText().trim();
    String idString = imageTicketIdTextField.getText().trim();

    if (url == null || url.trim().isEmpty()) {
      ViewUtils.showError("The URL cannot be empty. ");
    } 
    else if (idString == null || idString.trim().isEmpty()) {
      ViewUtils.showError("The ticket ID cannot be empty. ");
    } 
    else {
      // reset the text field if success
      int id = Integer.parseInt(idString);
      String error = AssetPlusFeatureSet5Controller.deleteImageFromMaintenanceTicket(url,id);
      if (error.isEmpty()) {
        deleteImageURLTextField.setText("");
        imageTicketIdTextField.setText("");
      }
      else {
        ViewUtils.showError(error);
      }
    }
  }

  // Event Listener on Button[#createButton].onAction
  @FXML
  /**
   * <h2>createButtonClicked</h2>
   * This method is called whenever a "create" button is clicked on the maintenance page.
   * 
   * @param event This is the action of the "create" button being clicked.
   * 
   * @author Yuri Sorice
   */
  void createButtonClicked(ActionEvent event) {
    String idString = createTicketIdTextField.getText().trim();
    String dateString = createTicketDateTextField.getText().trim();
    String description = createTicketDescriptionTextField.getText().trim();
    String assetString = createTicketAssetNumberTextField.getText().trim();
    String emailString = createTicketRaiserEmailTextField.getText().trim();

    // Checking for null or empty strings
    if (idString == null || idString.trim().isEmpty()) {
      ViewUtils.showError("Please input a valid ticket id. ");
    }
    else if (dateString == null || dateString.trim().isEmpty()) {
      ViewUtils.showError("Please input a valid date. ");
    }
    else if (description == null || description.trim().isEmpty()) {
      ViewUtils.showError("Please input a valid description. ");
    }
    else if (assetString == null || assetString.trim().isEmpty()) {
      ViewUtils.showError("Please input a valid asset number. ");
    }
    else if (emailString == null || emailString.trim().isEmpty()) {
      ViewUtils.showError("Please input a valid user email. ");
    }
    else {
      int id = Integer.parseInt(idString);
      Date date = Date.valueOf(dateString);
      int assetNumber = Integer.parseInt(assetString);
      // TODO user email may not always be the manager
      String errorMessage = AssetPlusFeatureSet4Controller.addMaintenanceTicket(id, date, description, emailString, assetNumber);
        // clear textfields
      if (errorMessage.isEmpty()) {
        createTicketIdTextField.setText(null);
        createTicketDateTextField.setText(null);
        createTicketDescriptionTextField.setText(null);
        createTicketRaiserEmailTextField.setText(null);
        createTicketAssetNumberTextField.setText(null);

      } else {
        ViewUtils.showError(errorMessage);
      }


    }
  }
  // Event Listener on Button[#updateButton].onAction
  @FXML
  /**
   * <h2>updateButtonClicked</h2>
   * This method is called whenever an "update" button is clicked on the maintenance page.
   * 
   * @param event This is the action of the "update" button being clicked.
   * 
   * @author Yuri Sorice
   */
  void updateButtonClicked(ActionEvent event) {
    String idString = updateTicketIdTextField.getText();
    String dateString = updateTicketDateTextField.getText();
    String description = updateTicketDescriptionTextField.getText();
    String email = updateTicketRaiserEmailTextField.getText();
    String assetString = updateTicketAssetNumberTextField.getText();

    // Checking for null or empty strings
    if (idString == null || idString.trim().isEmpty()) {
      ViewUtils.showError("Please input a valid ticket id. ");
    }
    else if (dateString == null || dateString.trim().isEmpty()) {
      ViewUtils.showError("Please input a valid date. ");
    }
    else if (description == null || description.trim().isEmpty()) {
      ViewUtils.showError("Please input a valid description. ");
    }
    else if (assetString == null || assetString.trim().isEmpty()) {
      ViewUtils.showError("Please input a valid asset number. ");
    }
    else if (email == null || email.trim().isEmpty()) {
      ViewUtils.showError("Please input a valid user email. ");
    }
    else {
      int id = Integer.parseInt(idString);
      Date date = Date.valueOf(dateString);
      int assetNumber = Integer.parseInt(assetString);
      String errorMessage = AssetPlusFeatureSet4Controller.updateMaintenanceTicket(id, date, description, email, assetNumber);
        // clear textfields
      if (errorMessage.isEmpty()) {
        updateTicketIdTextField.setText(null);
        updateTicketDateTextField.setText(null);
        updateTicketDescriptionTextField.setText(null);
        updateTicketRaiserEmailTextField.setText(null);
        updateTicketAssetNumberTextField.setText(null);

      } else {
        ViewUtils.showError(errorMessage);
      }


    }
   }
  // Event Listener on Button[#deleteButton].onAction
  @FXML
  /**
   * <h2>deleteButtonClicked</h2>
   * This method is called whenever a "delete" button is clicked on the maintenance page.
   * 
   * @param event This is the action of the "delete" button being clicked.
   * 
   * @author Yuri Sorice
   */
  void deleteButtonClicked(ActionEvent event) {
    String idString = updateTicketIdTextField.getText();
    if (idString == null || idString.trim().isEmpty()) {
      ViewUtils.showError("Please input a ticket id. ");
    }
    else {
      int id = Integer.parseInt(idString);
      String errorMessage = AssetPlusFeatureSet4Controller.deleteMaintenanceTicket(id);
      
      if (errorMessage.isEmpty()) {
        updateTicketIdTextField.setText(null);
      }
      else {
        ViewUtils.showError(errorMessage);
      }
    }

  }
}

