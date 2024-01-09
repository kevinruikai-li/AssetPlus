package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import java.sql.Date;
import java.util.List;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet2Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet3Controller;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFxmlView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;

/**
 * <h1>AssetTypePageController</h1>
 * This class is the controller for the 'AssetTypePage.fxml' view.
 * 
 * @author Jerome Desrosiers
 */
public class AssetTypePageController {
  @FXML private TextField createNewAssetTypeTextField;
  @FXML private TextField createExpectedLifespanTextField;
  @FXML private TextField updateOldAssetTypeTextField;
  @FXML private TextField updateNewAssetTypeTextField;
  @FXML private TextField updateExpectedLifespanTextField;
  @FXML private Button createButton;
  @FXML private Button updateButton;
  @FXML private Button deleteButton;

  @FXML
  // Event Listener on Button[#createButton].onAction
  /**
   * <h2>createButtonClicked</h2>
   * This method is called when a "create" button is clicked on the asset type page.
   * 
   * @param event This is the action of a "create" button being clicked.
   * @author Jerome Desrosiers
   */
  public void createButtonClicked(ActionEvent event) {
    String createNewAssetTypeString = createNewAssetTypeTextField.getText();
    String createExpectedLifespanString = createExpectedLifespanTextField.getText();

    if (createNewAssetTypeString == null || createNewAssetTypeString.trim().isEmpty()){
      ViewUtils.showError("The New Asset Type field cannot be empty");
    } else if (createExpectedLifespanString == null || createExpectedLifespanString.trim().isEmpty()){
      ViewUtils.showError("The Expected Lifespan field cannot be empty");
    } else {
      int expectedLifeSpanInDays = Integer.parseInt(createExpectedLifespanString);
      String errorMessage = AssetPlusFeatureSet2Controller.addAssetType(createNewAssetTypeString, expectedLifeSpanInDays);
      if (errorMessage.isEmpty()){
        createNewAssetTypeTextField.setText("");
        createExpectedLifespanTextField.setText("");
        AssetPlusFxmlView.getInstance().refresh();
      } else {
        ViewUtils.showError(errorMessage);
      }

    }
  }

  @FXML
  // Event Listener on Button[#updateButton].onAction
    /**
   * <h2>updateButtonClicked</h2>
   * This method is called when an "update" button is clicked on the asset type page.
   * 
   * @param event This is the action of an "update" button being clicked.
   * @author Jerome Desrosiers
   */
  public void updateButtonClicked(ActionEvent event) {
    String updateOldAssetTypeString = updateOldAssetTypeTextField.getText();
    String updateNewAssetTypeString = updateNewAssetTypeTextField.getText();
    String updateExpectedLifespanString = updateExpectedLifespanTextField.getText();

    if (updateOldAssetTypeString == null || updateOldAssetTypeString.trim().isEmpty()){
      ViewUtils.showError("The Old Asset Type field cannot be empty");
    } else if (updateNewAssetTypeString == null || updateNewAssetTypeString.trim().isEmpty()){
      ViewUtils.showError("The New Asset Type field cannot be empty");
    } else if (updateExpectedLifespanString == null || updateExpectedLifespanString.trim().isEmpty()){
      ViewUtils.showError("The Expected Lifespan field cannot be empty");
    } else {
      int expectedLifeSpanInDays = Integer.parseInt(updateExpectedLifespanString);
      String errorMessage = AssetPlusFeatureSet2Controller.updateAssetType(updateOldAssetTypeString, updateNewAssetTypeString, expectedLifeSpanInDays);
      if (errorMessage.isEmpty()){
        updateOldAssetTypeTextField.setText("");
        updateNewAssetTypeTextField.setText("");
        updateExpectedLifespanTextField.setText("");
        AssetPlusFxmlView.getInstance().refresh();
      } else {
        ViewUtils.showError(errorMessage);
      }
    }
  }

  @FXML
  // Event Listener on Button[#deleteButton].onAction
    /**
   * <h2>deleteButtonClicked</h2>
   * This method is called when a "delete" button is clicked on the asset type page.
   * 
   * @param event This is the action of a "delete" button being clicked.
   * @author Jerome Desrosiers
   */
  public void deleteButtonClicked(ActionEvent event) {
    String updateOldAssetTypeString = updateOldAssetTypeTextField.getText();

    if (updateOldAssetTypeString == null || updateOldAssetTypeString.trim().isEmpty()){
      ViewUtils.showError("The Old Asset Type field cannot be empty");
    } else {
      List<String> assetTypes = AssetPlusFeatureSet2Controller.getAssetTypesAsList();
      for (String assetType : assetTypes){
        if (assetType.equalsIgnoreCase(updateOldAssetTypeString)){
          AssetPlusFeatureSet2Controller.deleteAssetType(updateOldAssetTypeString);
          updateOldAssetTypeTextField.setText("");
          AssetPlusFxmlView.getInstance().refresh();
          return;
        }
      }
      ViewUtils.showError("The asset type you tried to delete does not exist");
    }
  }
}