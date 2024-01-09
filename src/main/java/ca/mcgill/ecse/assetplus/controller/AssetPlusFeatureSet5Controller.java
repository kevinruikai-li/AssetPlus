package ca.mcgill.ecse.assetplus.controller;

import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.SpecificAsset;
import ca.mcgill.ecse.assetplus.model.TicketImage;
import ca.mcgill.ecse.assetplus.persistence.AssetPlusPersistence;

public class AssetPlusFeatureSet5Controller {

  /**
   * <h1>addImageToMaintenanceTicket</h1>
   * This method adds an image to a maintenance ticket.
   * 
   * @param imageURL This corresponds to the URL of the image to be added as a string.
   * @param ticketID This corresponds to the ticket ID of the ticket in which the image is to be added as an integer.
   * @return String - This method returns an empty string if the image was added successfully. If the image was not
   * added successfully, then a string containing the corresponding error messages is returned.
   * 
   * @author Kevin Li
   */
  public static String addImageToMaintenanceTicket(String imageURL, int ticketID) {
    String errorMessage = "";

    if (!imageURL.startsWith("http://") && !imageURL.startsWith("https://")) {
      errorMessage+="Image URL must start with http:// or https://. ";
      return errorMessage;
    }

    if (imageURL.isEmpty()) {
      errorMessage+="Image URL cannot be empty. ";
      return errorMessage;
    }

    if (!MaintenanceTicket.hasWithId(ticketID)) {
      errorMessage+="Ticket does not exist. ";
      return errorMessage;
    }

    MaintenanceTicket maintenanceTicket = MaintenanceTicket.getWithId(ticketID);

    if (maintenanceTicket != null) {
      for (TicketImage ticketImage: maintenanceTicket.getTicketImages()) {
        if (ticketImage.getImageURL().equals(imageURL)) {
          errorMessage+="Image already exists for the ticket. ";
          return errorMessage;
        }
      }
    }

    if (!errorMessage.isEmpty()) {
      return errorMessage;
    }

    try {
      maintenanceTicket.addTicketImage(imageURL);
      AssetPlusPersistence.save();
    } catch (RuntimeException e) {
      errorMessage+=e.getMessage();
    }

    return errorMessage;
  }

  /**
   * <h1>deleteImageFromMaintenanceTicket</h1>
   * This method deletes an image from a maintenance ticket.
   * 
   * @param imageURL This corresponds to the URL of the image to be deleted as a string.
   * @param ticketID This corresponds to the ticket ID of the ticket from which the image is to be deleted as an integer.
   * @return String - This method returns an empty string if the image was added successfully. If the image was not
   * added successfully, then a string containing the corresponding error messages is returned.
   * 
   * @author Kevin Li
   */
  public static String deleteImageFromMaintenanceTicket(String imageURL, int ticketID) {
    String errorMessage = "";
    MaintenanceTicket maintenanceTicket = MaintenanceTicket.getWithId(ticketID);
    
    if (maintenanceTicket == null) {
      errorMessage+="Invalid ticket ID";
    }
    if (!imageURL.startsWith("http://") && !imageURL.startsWith("https://")) {
      errorMessage+="Image URL must start with http:// or https://. ";
    }
    else {
      for (TicketImage ticketImage: maintenanceTicket.getTicketImages()) {
        if (ticketImage.getImageURL().equals(imageURL)) {
          ticketImage.delete();
          break;
        }
      }
      AssetPlusPersistence.save();
    }
    return errorMessage;
  }

}
