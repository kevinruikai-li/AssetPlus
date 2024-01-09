package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import static ca.mcgill.ecse.assetplus.javafx.fxml.controllers.ViewUtils.callController;
import java.sql.Date;
import ca.mcgill.ecse.assetplus.controller.AssetPlusAPI;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet1Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet6Controller;
import ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicket;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFxmlView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

/**
 * <h1>AssignmentPageController</h1>
 * This class is the controller for the 'AssignmentPage.fxml' view.
 * 
 * @author Kevin Li
 */
public class AssignmentPageController {

  @FXML private TextField ticketIdTextField, noteTextField, dateTextField;
  @FXML private ChoiceBox<String> employeeEmailChoiceBox, priorityChoiceBox, timeEstimateChoiceBox, approvalChoiceBox;
  @FXML private Button assignButton, startWorkButton, completeWorkButton, approveButton, disapproveButton;
  
  @FXML
    /**
   * <h2>initialize</h2>
   * This method is called to initialize the dropdown menus in the assignment page.
   * 
   * @author Kevin Li
   */
  public void initialize() {
    // non refreshable choice boxes (always the same list to choose from)
    ObservableList<String> priorities =
      FXCollections.observableArrayList("Low", "Normal", "Urgent");
    
    ObservableList<String> timeEstimate =
      FXCollections.observableArrayList("LessThanADay", "OneToThreeDays", "ThreeToSevenDays", "OneToThreeWeeks", "ThreeOrMoreWeeks");
    
    ObservableList<String> approvalRequired =
      FXCollections.observableArrayList("Yes", "No");

      approvalChoiceBox.setItems(approvalRequired);
      approvalChoiceBox.setValue("No");
      
      priorityChoiceBox.setItems(priorities);
      priorityChoiceBox.setValue(null);

      timeEstimateChoiceBox.setItems(timeEstimate);
      timeEstimateChoiceBox.setValue(null);

      

    // the combo boxes are refreshable
    employeeEmailChoiceBox.addEventHandler(AssetPlusFxmlView.REFRESH_EVENT, e -> {
      employeeEmailChoiceBox.setItems(AssetPlusFeatureSet1Controller.getEmployees());
      employeeEmailChoiceBox.setValue(null);
    });

    // register the refreshable nodes
    AssetPlusFxmlView.getInstance().registerRefreshEvent(employeeEmailChoiceBox);
  }

  // Event Listener on Button[#assignButton].onAction
  @FXML
  /**
   * <h2>assignButtonClicked</h2>
   * This method is called whenever the "assign" button is clicked on the assignment page.
   * 
   * @param event This is the action of the "assign" button being clicked.
   * @author Kevin Li
   */
  public void assignButtonClicked(ActionEvent event) {
    String ticketIdString = ticketIdTextField.getText();
    String employeeEmail = employeeEmailChoiceBox.getValue();
    String priorityString = priorityChoiceBox.getValue();
    String timeEstimateString = timeEstimateChoiceBox.getValue();
    String approvalString = approvalChoiceBox.getValue();

    if (ticketIdString == null || ticketIdString.trim().isEmpty()) {
      ViewUtils.showError("The ticket number cannot be empty");
    }
    else if (employeeEmail == null) {
      ViewUtils.showError("An employee must be specified");
    }
    else if (priorityString == null || priorityString.trim().isEmpty()) {
      ViewUtils.showError("Please select a priority");
    }
    else if (timeEstimateString == null || timeEstimateString.trim().isEmpty()) {
      ViewUtils.showError("Please select a time estimate");
    }
    else {
      boolean approval = ("Yes".compareToIgnoreCase(approvalString)==0);
      int ticketId = Integer.parseInt(ticketIdString);
      String errorMessage = AssetPlusAPI.assign(ticketId, employeeEmail, AssetPlusAPI.getTimeEstimateEnum(timeEstimateString), 
      AssetPlusAPI.getPriorityEnum(priorityString), approval);
      if (errorMessage.isEmpty()) {
        ticketIdTextField.setText("");
      } else {
        ViewUtils.showError(errorMessage);
      }
    }
  }

  @FXML
  /**
   * <h2>startWorkButtonClicked</h2>
   * This method is called whenever the "start work" button is clicked on the assignment page.
   * 
   * @param event This is the action of the "start work" button being clicked.
   * 
   * @author Kevin Li
   */
  public void startWorkButtonClicked(ActionEvent event) {
    String ticketIdString = ticketIdTextField.getText();
    if (ticketIdString == null || ticketIdString.trim().isEmpty()) {
      ViewUtils.showError("The ticket number cannot be empty");
    }
    else {
      int ticketId = Integer.parseInt(ticketIdString);
      String errorMessage = AssetPlusAPI.startTicketWork(AssetPlusAPI.getTicketwithId(ticketId));
      if (errorMessage.isEmpty()) {
        ticketIdTextField.setText("");
      } else {
        ViewUtils.showError(errorMessage);
      }
    }
  }

  @FXML
  /**
   * <h2>completeWorkButtonClicked</h2>
   * This method is called whenever the "complete work" button is clicked on the assignment page.
   * 
   * @param event This is the action of the "complete work" button being clicked.
   * 
   * @author Kevin Li
   */
  public void completeWorkButtonClicked(ActionEvent event) {
    String ticketIdString = ticketIdTextField.getText();
    if (ticketIdString == null || ticketIdString.trim().isEmpty()) {
      ViewUtils.showError("The ticket number cannot be empty");
    }
    else {
      int ticketId = Integer.parseInt(ticketIdString);
      String errorMessage = AssetPlusAPI.completeTicketWork(AssetPlusAPI.getTicketwithId(ticketId));
      if (errorMessage.isEmpty()) {
        ticketIdTextField.setText("");
      } else {
        ViewUtils.showError(errorMessage);
      }
    }
  }

  @FXML
    /**
   * <h2>approveButtonClicked</h2>
   * This method is called whenever the "approve" button is clicked on the assignment page.
   * 
   * @param event This is the action of the "approve" button being clicked.
   * @author 
   */
  public void approveButtonClicked(ActionEvent event) {
    String ticketIdString = ticketIdTextField.getText();
    if (ticketIdString == null || ticketIdString.trim().isEmpty()) {
      ViewUtils.showError("The ticket number cannot be empty");
    }
    else {
      int ticketId = Integer.parseInt(ticketIdString);
      String errorMessage = AssetPlusAPI.approveTicketWork(AssetPlusAPI.getTicketwithId(ticketId));
      if (errorMessage.isEmpty()) {
        ticketIdTextField.setText("");
      } else {
        ViewUtils.showError(errorMessage);
      }
    }
  }

  @FXML
  /**
   * <h2>disapproveButtonClicked</h2>
   * This method is called whenever the "disapprove" button is clicked on the assignment page.
   * 
   * @param event This is the action of the "disapprove" button being clicked.
   * @author 
   */
  public void disapproveButtonClicked(ActionEvent event) {
    String ticketIdString = ticketIdTextField.getText();
    String note = noteTextField.getText();
    String dateString = dateTextField.getText();

    if (ticketIdString == null || ticketIdString.trim().isEmpty()) {
      ViewUtils.showError("The ticket number cannot be empty");
      return;
    }
    if(note == null || note.trim().isEmpty()){
      ViewUtils.showError("The ticket note cannot be empty");  
      return;    
    }
    if(dateString == null || dateString.trim().isEmpty()){
      ViewUtils.showError("The date cannot be empty");
      return;
    }
    else {
      int ticketId = Integer.parseInt(ticketIdString);
      Date date = Date.valueOf(dateString);
      String errorMessage = AssetPlusAPI.disapproveTicketWork(date, note, AssetPlusAPI.getTicketwithId(ticketId));
      if (errorMessage.isEmpty()) {
        ticketIdTextField.setText("");
        noteTextField.setText("");
        dateTextField.setText("");
      } else {
        ViewUtils.showError(errorMessage);
      }
    }
  }
}
