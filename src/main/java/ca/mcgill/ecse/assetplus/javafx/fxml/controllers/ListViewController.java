package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;
/**
 * Sample Skeleton for 'ListView.fxml' Controller Class
 */

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import ca.mcgill.ecse.assetplus.controller.*;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFxmlView;
import javafx.event.ActionEvent;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.beans.binding.Bindings;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;

/**
 * <h1>ListViewController</h1>
 * This class is the controller for the 'ListView.fxml' view.
 * 
 * @author Tessa Hason
 */
public class ListViewController {
    @FXML  private ResourceBundle resources;

    @FXML private URL location;

    @FXML private DatePicker searchByDate; 

    @FXML private Button searchByDateButton;

    @FXML private Button seeAllTicketsButton;

    @FXML private TableView<TOMaintenanceTicket> ticketTable;

    @FXML private Button searchButton;

    @FXML private Button searchByRaiserButton;
    
    @FXML private TextField searchRaiserTextField;

    @FXML private TextField searchTicketTextField;

    /**
     * <h2>initialize</h2>
     * Initializes the controller.
     * 
     * @author Tessa Hason
     */
    public void initialize() {
        //TICKET ID COLUMN (int)
        var idcolumn = createTableColumn("Ticket ID", "id");
        ticketTable.getColumns().add(idcolumn);


        //STATUS COLUMN (string)
        var statusColumn = createTableColumn("Status", "status");
        ticketTable.getColumns().add(statusColumn);
    
        //RAISED BY EMAIL COLUMN (customizable string)
        var raisedColumn = new TableColumn<TOMaintenanceTicket, String>("Ticket Raiser");
        raisedColumn.setCellValueFactory(data -> Bindings.createStringBinding(
            () -> data.getValue().getRaisedByEmail()));
        ticketTable.getColumns().add(raisedColumn);

        //DESCRIPTION COLUMN (customizable string)
        var descriptionColumn = new TableColumn<TOMaintenanceTicket, String>("Description");
        descriptionColumn.setCellValueFactory(data -> Bindings.createStringBinding(
            () -> data.getValue().getDescription()));
        ticketTable.getColumns().add(descriptionColumn);

        //FIXED BY EMAIL COLUMN (customizable string)
        var fixedempColumn = new TableColumn<TOMaintenanceTicket, String>("Assigned Employee");
        fixedempColumn.setCellValueFactory(data -> Bindings.createStringBinding(
            () -> data.getValue().getFixedByEmail()));
        ticketTable.getColumns().add(fixedempColumn);

        var timeResColumn = new TableColumn<TOMaintenanceTicket, String>("Time To Resolve");
        timeResColumn.setCellValueFactory(data -> Bindings.createStringBinding(
            () -> {
                TOMaintenanceTicket ticket = data.getValue();
                if (ticket != null && ticket.getTimeToResolve() != null) {
                    return ticket.getTimeToResolve();
                } else {
                    return ""; // or any default value or appropriate handling
                }
            }
            ));
        ticketTable.getColumns().add(timeResColumn);

        //PRIORITY COLUMN (string)
        ticketTable.getColumns().add(createTableColumn("Priority", "priority"));

        //APPROVAL REQUIRED (Boolean)
        ticketTable.getColumns().add(createTableColumn("Approval Required", "approvalRequired"));

        //ASSET NAME (customizable string)
        var assetNameColumn = new TableColumn<TOMaintenanceTicket, String>("Asset Name");
        assetNameColumn.setCellValueFactory(data -> Bindings.createStringBinding(
            () -> data.getValue().getAssetName()));
        ticketTable.getColumns().add(assetNameColumn);

        //LIFESPAN COLUMN (string)
        ticketTable.getColumns().add(createTableColumn("Lifespan", "expectLifeSpanInDays"));

        //raised on date (Date)
        var raisedOnDateColumn = new TableColumn<TOMaintenanceTicket, String>("Raised On");
        raisedOnDateColumn.setCellValueFactory(data -> Bindings.createStringBinding(
            () -> data.getValue().getRaisedOnDate().toString()));
        ticketTable.getColumns().add(raisedOnDateColumn);

        //Purchase date (Date)
        var purchaseDateColumn = new TableColumn<TOMaintenanceTicket, String>("Purchase Date");
        purchaseDateColumn.setCellValueFactory(data -> Bindings.createStringBinding(
            () -> data.getValue().getPurchaseDate().toString()));
        ticketTable.getColumns().add(purchaseDateColumn);

        //FloorNumber int
        ticketTable.getColumns().add(createTableColumn("Floor Number", "floorNumber"));

        //RoomNumber int
        ticketTable.getColumns().add(createTableColumn("Room Number", "roomNumber"));

        //Image URLS 
        var imageColumn = new TableColumn<TOMaintenanceTicket, String>("Images");
        imageColumn.setCellValueFactory(data -> Bindings.createStringBinding(
            () -> data.getValue().getImageURLs().toString()));
        ticketTable.getColumns().add(imageColumn);

        //Maintenance Notes 
        var notesColumn = new TableColumn<TOMaintenanceTicket, String>("Notes");
        notesColumn.setCellValueFactory(data -> Bindings.createStringBinding(
            () -> data.getValue().getNotes().toString()));
        ticketTable.getColumns().add(notesColumn);


        //Set colors 
        ticketTable.setRowFactory(tv -> new TableRow<TOMaintenanceTicket>() {
            @Override
            protected void updateItem(TOMaintenanceTicket item, boolean empty) {
                super.updateItem(item, empty);
    
                if (item == null || item.getStatus() == null) {
                    // If the status is null, set the default row color
                    setStyle("");
                } else {
                    // Set row color based on the value of the "Status" column
                    switch (item.getStatus()) {
                        case "Open":
                            setStyle("-fx-background-color: lightgreen;");
                            break;
                        case "InProgress":
                            setStyle("-fx-background-color: #FFFFE0;"); //Light yellow
                            break;
                        case "Assigned":
                            setStyle("-fx-background-color: #FFDAB9;");  //light orange
                            break;
                        case "Closed":
                            setStyle("-fx-background-color: #FF0000;"); //light red 
                            break;
                        case "Resolved":
                            setStyle("-fx-background-color: #E6E6FA;"); //light purple
                            break;
                        default:
                            // Set the default row color for other statuses
                            setStyle("");
                            break;
                    }
                }
            }
        });


        // configure data picker
        // set editable to false so that the user cannot choose from the calendar
        //searchByDate.setEditable(false);
        // set default value to today
        searchByDate.setValue(LocalDate.now());
    
        ticketTable.addEventHandler(AssetPlusFxmlView.REFRESH_EVENT, e -> ticketTable.setItems(getAllTickets()));
    
        // register refreshable nodes
        AssetPlusFxmlView.getInstance().registerRefreshEvent(ticketTable);

        assert ticketTable != null : "fx:id=\"ticketTable\" was not injected: check your FXML file 'ListView.fxml'.";
        assert searchByDate != null : "fx:id=\"searchByDate\" was not injected: check your FXML file 'ListView.fxml'.";
      }
      
    // Event Listener on DatePicker[#searchByDate].onAction
    /**
     * <h2>selectedDateChanged</h2>
     * Event handler for changes in the selected date.
     *
     * @param event The ActionEvent triggered by the date change.
     * 
     * @author Tessa Hason
     */
    @FXML
    void selectedDateChanged(ActionEvent event) {
        AssetPlusFxmlView.getInstance().refresh();
    }

    /**
     * <h2>createTableColumn</h2>
     * Creates a TableColumn for the TableView.
     *
     * @param header The header text for the column.
     * @param propertyName The property name associated with the column.
     * @return TableColumn<TOMaintenanceTicket, String> - Returns a TableColumn instance.
     * 
     * @author Tessa Hason
     */
    public static TableColumn<TOMaintenanceTicket, String> createTableColumn(String header, String propertyName) {
        TableColumn<TOMaintenanceTicket, String> column = new TableColumn<>(header);
        column.setCellValueFactory(new PropertyValueFactory<>(propertyName));
        return column;
    }

    // Event Listener on Button[#seeAllTicketsButton].onAction
    /**
     * <h2>seeAllTicketsButtonClicked</h2>
     * Event handler for the "See All Tickets" button.
     *
     * @param event The ActionEvent triggered by the button click.
     * 
     * @author Tessa Hason
     */
    @FXML
    void seeAllTicketsButtonClicked(ActionEvent event) {
        ticketTable.setItems(FXCollections.observableList(getAllTickets()));
        AssetPlusFxmlView.getInstance().registerRefreshEvent(ticketTable);
        AssetPlusFxmlView.getInstance().refresh();
    }

    // Event Listener on Button[#searchRaiserButton].onAction
    /**
     * <h2>searchRaiserButtonClicked</h2>
     * Event handler for the "Search Raiser" button.
     *
     * @param event The ActionEvent triggered by the button click.
     * 
     * @author Tessa Hason
     */
    @FXML
    void searchRaiserButtonClicked(ActionEvent event) {
        String selectedRaiser = searchRaiserTextField.getText().trim();
        if (selectedRaiser == null) {
            ViewUtils.showError("Please input a valid user email");
        }else{
            ObservableList<TOMaintenanceTicket> filteredTickets =  getAllTickets().filtered(ticket -> ticket.getRaisedByEmail().equalsIgnoreCase(selectedRaiser));

            if (filteredTickets.isEmpty()) {
                ViewUtils.showError("No tickets found with the specified Raiser");
            } else {
                ticketTable.setItems(FXCollections.observableList(filteredTickets));
                AssetPlusFxmlView.getInstance().registerRefreshEvent(ticketTable);
            }
        }

    }

    // Event Listener on Button[#searchTicketButton].onAction
    /**
     * <h2>searchTicketButtonClicked</h2>
     * Event handler for the "Search" button (for Ticket ID).
     *
     * @param event The ActionEvent triggered by the button click.
     * 
     * @author Tessa Hason
     */
    @FXML 
    void searchTicketButtonClicked(ActionEvent event) { //FOR TICKET ID
        String stringid = searchTicketTextField.getText().trim();
        if (stringid.isEmpty()) {
            ViewUtils.showError("Please input a valid ticket ID");
        } else {
            int id = Integer.parseInt(stringid);
            // Search tickets by ID and update the table
            ObservableList<TOMaintenanceTicket> filteredTickets = getAllTickets().filtered(ticket -> ticket.getId() ==id);

            if (filteredTickets.isEmpty()) {
                ViewUtils.showError("No tickets found with the specified ID");
            } else {
                ticketTable.setItems(filteredTickets);
                AssetPlusFxmlView.getInstance().registerRefreshEvent(ticketTable);
            }
        }
    }

    /**
     * <h2>getAllTickets</h2>
     * Retrieves all maintenance tickets.
     *
     * @return ObservableList<TOMaintenanceTicket> - An ObservableList containing all maintenance tickets.
     * 
     * @author Tessa Hason
     */
    public ObservableList<TOMaintenanceTicket> getAllTickets() {
        if (AssetPlusFeatureSet6Controller.getTickets() == null) {
            return FXCollections.emptyObservableList();
        }
        return FXCollections.observableList(AssetPlusFeatureSet6Controller.getTickets());
    }

    // Event Listener on Button[#searchByDateButton].onAction
     /**
     * <h2>searchByDateButtonClicked</h2>
     * Event handler for the "Search By Date" button.
     *
     * @param event The ActionEvent triggered by the button click.
     * 
     * @author Tessa Hason
     */
    @FXML
    void searchByDateButtonClicked(ActionEvent event) { //FOR SEARCHING BY A SPECIFIC DAY 
        LocalDate selectedDate = searchByDate.getValue();
        if (selectedDate == null) {
            ViewUtils.showError("Please select a valid date");
        }else{
            var date = Date.valueOf(selectedDate);
            ObservableList<TOMaintenanceTicket> filteredTickets = getAllTickets().filtered(ticket -> 0==ticket.getRaisedOnDate().compareTo(date));

            if (filteredTickets.isEmpty()) {
                ViewUtils.showError("No tickets found with the specified date");
            } else {
                ticketTable.setItems(filteredTickets);
                AssetPlusFxmlView.getInstance().registerRefreshEvent(ticketTable);
            }
        }

    }


}
