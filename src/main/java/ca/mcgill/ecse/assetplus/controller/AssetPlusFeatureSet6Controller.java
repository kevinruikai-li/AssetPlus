package ca.mcgill.ecse.assetplus.controller;
import java.util.List;
import java.util.ArrayList;
import java.sql.Date;
import ca.mcgill.ecse.assetplus.model.*;
import ca.mcgill.ecse.assetplus.persistence.AssetPlusPersistence;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;

/**
 * <h1>AssetPlusFeatureSet6Controller</h1>
 * @author Tessa Hason
 * @version 1.0
 * @since 2023-10-18
 */
public class AssetPlusFeatureSet6Controller {

  /**
   * <h2>deleteEmployeeOrGuest</h2>
   * This method is used to delete an Employee or a Guest within the AssetPlus application.
   * 
   * @param email The employee or guest with the given email in this string that must be deleted.
   * 
   * @author Tessa Hason
   */
  public static void deleteEmployeeOrGuest(String email) {

    User userToDelete = User.getWithEmail(email);
    if(email.equals("manager@ap.com")){
    } else if (userToDelete instanceof Employee || userToDelete instanceof Guest){
      try {
      userToDelete.delete();
      AssetPlusPersistence.save();
    }
    catch(Exception e) {
      String errorMessage = "Unknown exception";
     }
    }
  }

  /**
   * <h2>getTickets</h2>
   * The method is used to view the status of Maintenance Tickets for an employee/manager.
   * 
   * @return List<TOMaintenanceTicket> - Returns a list of TOMaintenanceTicket objects representing all MaintenanceTickets
   * in the AssetPlus System.
   * 
   * @author Tessa Hason
   */
  public static List<TOMaintenanceTicket> getTickets() {
    AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();
    if (assetPlus.hasMaintenanceTickets()){
      List<MaintenanceTicket> maintenanceTickets = assetPlus.getMaintenanceTickets();
      List<TOMaintenanceTicket> TOMaintenanceTickets = new ArrayList<TOMaintenanceTicket>();
        
      for (MaintenanceTicket ticket : maintenanceTickets) {
        List<String> urls = new ArrayList<String>();
        List<TicketImage> images = ticket.getTicketImages();
        for (TicketImage TicketImage : images) {
          urls.add(TicketImage.getImageURL());
        }

        TOMaintenanceNote[] notes = new TOMaintenanceNote[ticket.numberOfTicketNotes()];
        int i = 0;
        for (MaintenanceNote note : ticket.getTicketNotes()) {
          Date noteDate = note.getDate();
          String noteDescription = note.getDescription();
          String noteTakerEmail = note.getNoteTaker().getEmail();
          TOMaintenanceNote TOMaintenanceNote = new TOMaintenanceNote(noteDate, noteDescription, noteTakerEmail);
          notes[i] = TOMaintenanceNote;
          i++;
        }

        String assetName;
        int lifespan = -1;
        Date purchaseDate;
        int floorNumber = -1;
        int roomNumber = -1;

        if (ticket.hasAsset()) {
          assetName = ticket.getAsset().getAssetType().getName();
          lifespan = ticket.getAsset().getAssetType().getExpectedLifeSpan();
          purchaseDate = ticket.getAsset().getPurchaseDate();
          floorNumber = ticket.getAsset().getFloorNumber();
          roomNumber = ticket.getAsset().getRoomNumber();
        } else {
          assetName = null;
          purchaseDate = null;
        }
        //ticketFixercheck

        String fixer = null;
        if (ticket.hasTicketFixer()) {
          fixer = ticket.getTicketFixer().getEmail();
        }


        //timetoresolve check

        String timetores = null;
        if (ticket.getTimeToResolve() !=null){
          timetores = ticket.getTimeToResolve().toString();
        }

        //priority check

        String prioritycheck = null;
        if (ticket.getPriority()!=null){
          prioritycheck = ticket.getPriority().toString();
        }


        TOMaintenanceTicket TOticket = new TOMaintenanceTicket(ticket.getId(), ticket.getRaisedOnDate(), ticket.getDescription(), ticket.getTicketRaiser().getEmail(), ticket.getStatusFullName(), fixer, timetores, prioritycheck, ticket.hasFixApprover(), assetName, lifespan, purchaseDate, floorNumber, roomNumber, urls, notes);
        TOMaintenanceTickets.add(TOticket);

      }
      AssetPlusPersistence.save();
      return TOMaintenanceTickets;

    }
    return null;
  }

  /**
   * <h2>hasUser</h2>
   * This methods is used to verify if a user with email exists.
   * 
   * @param email String containing the email we want to verify corresponds to an existing user in the system.
   * @return boolean - Returns a boolean coresponding to whether or not a user with the given email exists in the assetPlus system.
   * 
   * @author Yuri Sorice
   */
  public static boolean hasUser(String email){
    return User.hasWithEmail(email);
  }

}
