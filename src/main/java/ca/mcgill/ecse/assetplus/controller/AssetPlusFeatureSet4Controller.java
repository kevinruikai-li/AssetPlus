package ca.mcgill.ecse.assetplus.controller;
import ca.mcgill.ecse.assetplus.model.*;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.persistence.AssetPlusPersistence;
import java.sql.Date;

/** <h1>AssetPlusFeature4Controller</h1>
 * @version 1.0
 * @author Yuri Sorice
 * @since 2023-10-18
 */
public class AssetPlusFeatureSet4Controller {
  /**
   * <h1>addMaintenanceTicket</h1>
   * This method is used to add a maintenance ticket to assetPlus.
   * 
   * @param id The id number of the maintenance ticket as an integer.
   * @param raisedOnDate The date the ticket was made as a Date object.
   * @param description A description of what is in need of maintenance as a string.
   * @param email The ticker raiser's email as a string.
   * @param assetNumber The number of the asset associated with the ticket as an integer.
   * @return String - Returns a string that is empty if addMaintenanceTicket was successful and
   * no errors were raised. Returns a string with an error message otherwise.
   * 
   * @author Yuri Sorice
   */
  public static String addMaintenanceTicket(int id, Date raisedOnDate, String description,
      String email, int assetNumber) {
        String errorMessage = "";
        if (MaintenanceTicket.hasWithId(id)) {
          errorMessage += "Ticket id already exists. ";
        }
        if (!SpecificAsset.hasWithAssetNumber(assetNumber) && assetNumber != -1) {
          errorMessage += "The asset does not exist. ";
        }
        if (!User.hasWithEmail(email)) {
          errorMessage += "The ticket raiser does not exist. ";
        }
        if (description == null || description.isEmpty()) {
          errorMessage += "Ticket description cannot be empty. ";
        }
        if (!errorMessage.isEmpty()) {
          return errorMessage;
        }
      try {
          MaintenanceTicket newTicket = new MaintenanceTicket(id, raisedOnDate, description, AssetPlusApplication.getAssetPlus(), User.getWithEmail(email));
          if (assetNumber == -1) {
            newTicket.setAsset(null);
          }
          else if (assetNumber > 0){
            newTicket.setAsset(SpecificAsset.getWithAssetNumber(assetNumber));
        }
        AssetPlusPersistence.save();
      
      }
      catch (Exception e){
        if (e.getMessage().contains("duplicate")){
          errorMessage += "Cannot create due to duplicate id. ";
        }
        if (e.getMessage().contains("assetPlus")){
          errorMessage += "Unable to create maintenanceTicket due to assetPlus. ";
        }
        if (e.getMessage().contains("raisedTicket")){
          errorMessage += "Unable to create raisedTicket due to ticketRaiser. ";
        }
      }

      return (errorMessage);
  }

  /**
   * <h1>updateMaintenanceTicket</h1>
   * This method updates an existing maintenance ticket.
   * 
   * @param id The ticket id of the existing ticket as an integer.
   * @param newRaisedOnDate The date of the update made to the ticket as a Date object.
   * @param newDescription The new description of what kind of maintenance is required as a string.
   * @param newEmail The email of the ticket updater as a string.
   * @param newAssetNumber The new number of the object as an integer (if the number is -1 the ticket stays assigned
   * to the same asset).
   * @return String - Similar to addMaintenanceTicket, it returns an empty string if the method is successful,
   * otherwise it returns the error message in the form of a string.
   * 
   * @author Yuri Sorice
   */
  public static String updateMaintenanceTicket(int id, Date newRaisedOnDate, String newDescription,
      String newEmail, int newAssetNumber) {
        String errorMessage = "";
        if (!HotelStaff.hasWithEmail(newEmail)) {
          errorMessage += "The ticket raiser does not exist. ";
        }
        if (newDescription == null || newDescription.isEmpty()) {
          errorMessage += "Ticket description cannot be empty. ";
        }
        if (!SpecificAsset.hasWithAssetNumber(newAssetNumber) && newAssetNumber != -1) {
          errorMessage += "The asset does not exist. ";
        }
        if (!errorMessage.isEmpty()) {
          return errorMessage;
        }
        try {
          MaintenanceTicket ticketToEdit = MaintenanceTicket.getWithId(id);
          ticketToEdit.setRaisedOnDate(newRaisedOnDate);
          ticketToEdit.setDescription(newDescription);
          ticketToEdit.setTicketRaiser(User.getWithEmail(newEmail));
          if (newAssetNumber > 0){
          ticketToEdit.setAsset(SpecificAsset.getWithAssetNumber(newAssetNumber));
          }
          else if (newAssetNumber == -1) {
            ticketToEdit.setAsset(null);
          }
          AssetPlusPersistence.save();
        }
        catch (RuntimeException e) {
          return e.getMessage();
        }

        return errorMessage;
    }
        
  /**
   * <h1>deleteMaintenanceTicket</h1>
   * This method deletes a maintenance ticket with the provided ticket id from the assetPlus system.
   * If the id given does not have a corresponding ticket, then nothing happens.
   * 
   * @param id The number of the ticket that is to be deleted as an integer.
   * @return String - Returns an empty string if the method is successful,
   * otherwise it returns the error message in the form of a string.
   * 
   * @author Yuri Sorice
   */
  public static String deleteMaintenanceTicket(int id) {
    String errorMessage = "";
    try {
      if (MaintenanceTicket.hasWithId(id)) {
          MaintenanceTicket.getWithId(id).delete(); 
      }
      else errorMessage += "Ticket does not exist. ";
      AssetPlusPersistence.save();
    }
    catch (RuntimeException e) {}
    return errorMessage;
  }
}
