package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import java.sql.Date;
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
 * <h1>AssetPageController</h1>
 * This class is the controller for the 'AssetsPage.fxml' view.
 * 
 * @author Luis Jarquin
 */
public class AssetPageController {
  @FXML private TextField addAssetNumberTextField;
  @FXML private TextField addPurchaseDateTextField;
  @FXML private TextField addFloorNumberTextField;
  @FXML private TextField addRoomNumberTextField;
  @FXML private TextField updateAssetNumberTextField;
  @FXML private TextField updatePurchaseDateTextField;
  @FXML private TextField updateFloorNumberTextField;
  @FXML private TextField updateRoomNumberTextField;
  @FXML private Button addButton;
  @FXML private Button updateButton;
  @FXML private Button deleteButton;
  @FXML private Button refreshButton;
  @FXML private ChoiceBox<String> addAssetTypeChoiceBox;
  @FXML private ChoiceBox<String> updateAssetTypeChoiceBox;

   @FXML
  /**
  * <h2>initialize</h2> 
  * This method is called to update the dropdown menus in the asset page.
  *
  * @author Luis Jarquin
  */
  public void initialize() {
    // the driver and assignment choice boxes are refreshable
    addAssetTypeChoiceBox.addEventHandler(AssetPlusFxmlView.REFRESH_EVENT, e -> {
      addAssetTypeChoiceBox.setItems(AssetPlusFeatureSet2Controller.getAssetTypes());
      addAssetTypeChoiceBox.setValue(null);
    });

    updateAssetTypeChoiceBox.addEventHandler(AssetPlusFxmlView.REFRESH_EVENT, e -> {
      updateAssetTypeChoiceBox.setItems(AssetPlusFeatureSet2Controller.getAssetTypes());
      updateAssetTypeChoiceBox.setValue(null);
    });

    // register the refreshable nodes
    AssetPlusFxmlView.getInstance().registerRefreshEvent(addAssetTypeChoiceBox);
    AssetPlusFxmlView.getInstance().registerRefreshEvent(updateAssetTypeChoiceBox);
  }

  // @FXML
  // public void refreshButtonClicked(ActionEvent event) {
  //   AssetPlusFxmlView.getInstance().refresh();
  // }

  @FXML
  // Event Listener on Button[#addButton].onAction
  /**
   * <h2>addButtonClicked</h2>
   * This method is called whenever an "add" button is clicked in the asset page.
   * 
   * @param event This is the action of an "add" button being clicked.
   * @author Luis Jarquin
   */
  public void addButtonClicked(ActionEvent event) {
    String assetNumberString = addAssetNumberTextField.getText();
    String purchaseDateString = addPurchaseDateTextField.getText();
    String floorNumberString = addFloorNumberTextField.getText();
    String roomNumberString = addRoomNumberTextField.getText();

    if (assetNumberString == null || assetNumberString.trim().isEmpty()) {
      ViewUtils.showError("The asset number cannot be empty");
    }
    else if (purchaseDateString == null || purchaseDateString.trim().isEmpty()) {
      ViewUtils.showError("The purchase date cannot be empty");
    }
    else if (floorNumberString == null || floorNumberString.trim().isEmpty()) {
      ViewUtils.showError("The floor number cannot be empty");
    } else {
      String assetType = addAssetTypeChoiceBox.getValue();
      int assetNumber = Integer.parseInt(assetNumberString);
      int floorNumber = Integer.parseInt(floorNumberString);
      int roomNumber;
      if (roomNumberString == null || roomNumberString.trim().isEmpty()){
        roomNumber = -1;
      } else {
        roomNumber = Integer.parseInt(roomNumberString);
      }
      Date purchaseDate = Date.valueOf(purchaseDateString);
      String errorMessage = AssetPlusFeatureSet3Controller.addSpecificAsset(assetNumber, floorNumber, roomNumber, purchaseDate, assetType);
      if (errorMessage.isEmpty()) {
        addAssetNumberTextField.setText("");
        addPurchaseDateTextField.setText("");
        addFloorNumberTextField.setText("");
        addRoomNumberTextField.setText("");
      } else {
        ViewUtils.showError(errorMessage);
      }
    }
  }
  
  @FXML
  // Event Listener on Button[#updateButton].onAction
  /**
   * <h2>updateButtonClicked</h2>
   * This method is called when an "update" button is clicked on the asset page.
   * 
   * @param event This is the action of an "update" button being clicked.
   * @author Luis Jarquin
   */
  public void updateButtonClicked(ActionEvent event) {
    String assetNumberString = updateAssetNumberTextField.getText();
    String purchaseDateString = updatePurchaseDateTextField.getText();
    String floorNumberString = updateFloorNumberTextField.getText();
    String roomNumberString = updateRoomNumberTextField.getText();
    if (assetNumberString == null || assetNumberString.trim().isEmpty()) {
      ViewUtils.showError("The asset number cannot be empty");
    }
    else if (purchaseDateString == null || purchaseDateString.trim().isEmpty()) {
      ViewUtils.showError("The purchase date cannot be empty");
    }
    else if (floorNumberString == null || floorNumberString.trim().isEmpty()) {
      ViewUtils.showError("The floor number cannot be empty");
    } else {
      String assetType = updateAssetTypeChoiceBox.getValue();
      int assetNumber = Integer.parseInt(assetNumberString);
      int floorNumber = Integer.parseInt(floorNumberString);
      int roomNumber;
      if (roomNumberString == null || roomNumberString.trim().isEmpty()){
        roomNumber = -1;
      } else {
        roomNumber = Integer.parseInt(roomNumberString);
      }
      Date purchaseDate = Date.valueOf(purchaseDateString);
      String errorMessage = AssetPlusFeatureSet3Controller.updateSpecificAsset(assetNumber, floorNumber, roomNumber, purchaseDate, assetType);
      if (errorMessage.isEmpty()){
        updateAssetNumberTextField.setText("");
        updatePurchaseDateTextField.setText("");
        updateFloorNumberTextField.setText("");
        updateRoomNumberTextField.setText("");
      } else {
        ViewUtils.showError(errorMessage);
      }
    }

  }

  @FXML
  // Event Listener on Button[#deleteButton].onAction
  /**
   * <h2>deleteButtonClicked</h2>
   * This method is called whenever a "delete" button is clicked on the asset page.
   * 
   * @param event This is the action of a "delete" button being clicked.
   * @author Luis Jarquin
   */
  public void deleteButtonClicked(ActionEvent event) {
    String assetNumberString = updateAssetNumberTextField.getText();
    if (assetNumberString == null || assetNumberString.trim().isEmpty()) {
      ViewUtils.showError("The asset number cannot be empty");
    } else {
      int assetNumber = Integer.parseInt(assetNumberString);
      if (AssetPlusFeatureSet3Controller.hasAsset(assetNumber)){
        AssetPlusFeatureSet3Controller.deleteSpecificAsset(assetNumber);
        updateAssetNumberTextField.setText("");
        return;
      } else {
        ViewUtils.showError("Asset you tried to delete does not exist");
      }

    }
  }
}
