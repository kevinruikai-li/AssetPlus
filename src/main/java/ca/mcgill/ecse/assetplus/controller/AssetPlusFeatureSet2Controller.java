package ca.mcgill.ecse.assetplus.controller;
import java.util.ArrayList;
import java.util.List;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.AssetType;
import ca.mcgill.ecse.assetplus.model.AssetPlus;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import ca.mcgill.ecse.assetplus.persistence.AssetPlusPersistence;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * <h1>AssetPlusFeatureSet2Controller</h1>
 * 
 * The AssetPlusFeatureSet2Controller program is used
 * to control every action that has to do with the
 * creation, update or deletion of an asset type in the
 * AssetPlus program.
 * 
 * @author Jérôme Desrosiers
 * @version 1.0
 * @since 2023-10-18
 */
public class AssetPlusFeatureSet2Controller {

  private static AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();

  /**
   * <h2>addAssetType</h2>
   * This is the method called when a manager attempts to add a new 
   * asset type to the AssetPlus system. 
   * 
   * @param name This is the name the manager wants to give the new asset type. Cannot be empty.
   * @param expectedLifeSpanInDays This is the expected life span (in days) the manager wants to give to the new asset type. Cannot be less than or equal to zero.
   * @return String - This returns a string containing an error message gathered during execution, if this returned string is empty, the creation of a new asset type was successful.
   * 
   * @author Jérôme Desrosiers
   */
  public static String addAssetType(String name, int expectedLifeSpanInDays) {

    /* Check if the expected lifespan of the asset type is invalid and return the corresponding error message. */
    if (expectedLifeSpanInDays <= 0) {
      return "The expected life span must be greater than 0 days";
    }

    /* Check if the length of the asset type name is invalid and return the corresponding error message. */
    if (name.isEmpty()) {
      return "The name must not be empty";
    }

    /* Check if an asset type already exists with the name the managers wants to give to a new asset type. */
    if (AssetType.hasWithName(name)) {
      return "The asset type already exists";
    }

    /* Try creating a new asset type is no error has been encontered yet. */
    try {
      AssetType newAssetType = new AssetType(name, expectedLifeSpanInDays, AssetPlusApplication.getAssetPlus());
      AssetPlusPersistence.save();
    }
 
     /* Catch any exceptions thrown by the creation of a new asset type. */
    catch(Exception e) {      
      /* This is here just in case I made a mistake so that it won't stop the application, maybe? */
        return "I have no idea what happened, this error is not even supposed to be able to happen." + e;
    }

    /* Return an empty string if the creation of the new asset type was successful. */
    return "";
  }

  /**
   * <h2>updateAssetType</h2>
   * This is the method called whenever a manager attempts 
   * to update the name and expected life span of a 
   * specific asset type in the AssetPlus system.
   * 
   * @param oldName This is the name of the asset type to be updated as a string. An asset type with this name must exist in the system.
   * @param newName This is the new name the user wants to give to the asset type being updated as a string. No asset type can already exist with this name.
   * @param newExpectedLifeSpanInDays This is the new expected life span (in days) the user wants to give to the asset type being updated. Cannot be less than or equal to zero.
   * 
   * @return String - This returns a string containing an error message gathered during execution, if this returned string is empty, the update of the specified asset type was successful.
   * 
   * @author Jérôme Desrosiers
   */
  public static String updateAssetType(String oldName, String newName, int newExpectedLifeSpanInDays) {
    
    /* Check if the new expected lifespan of the asset type is invalid and return the corresponding error message. */
    if (newExpectedLifeSpanInDays <= 0) {
      return "The expected life span must be greater than 0 days";
    }

    /* Check if the length of the asset type name is invalid and return the corresponding error message. */
    if (newName.isEmpty()) {
      return "The name must not be empty";
    }

    /* Check if the asset type the manager wants to update exist, if it doesn't, return coressponding error message. */
    if(!AssetType.hasWithName(oldName)) {
      return "The asset type does not exist";
    }

    /* Check if the new name differs from the old name and there already exists an asset type with the new name, return coressponding error message. */
    if (AssetType.hasWithName(newName) && !(newName.equals(oldName))) {
      return "The asset type already exists";
    }

    try{
      AssetType someAssetType = AssetType.getWithName(oldName);
      someAssetType.setName(newName);
      someAssetType.setExpectedLifeSpan(newExpectedLifeSpanInDays);
      AssetPlusPersistence.save();
    }
    // This is here just in case there is an error I have not thought about yet.
    catch(Exception e) {
      return "I have no idea what happened, this error is not even supposed to be able to happen." + e;
    }

    /* If the uptade of the asset type was successful, return an empty string. */
    return "";
  }

  
  /**
   * <h2>deleteAssetType</h2>
   * This is the method called whenever a manager
   * attempts to delete a specified asset type
   * in the AssetPlus program.
   * 
   * @param name This is the name of the asset type the user wishes to delete as a string. An asset type with this name must already exist in the system.
   * 
   * @author Jérôme Desrosiers
   */
  public static void deleteAssetType(String name) {

    // Delete the asset type that has the specified asset type name if it exists.
    try {
      if (AssetType.hasWithName(name)) {
        // might have to move this to the pagecontroller instead
        if (assetPlus.hasMaintenanceTickets()) {
          List<MaintenanceTicket> tickets = assetPlus.getMaintenanceTickets();
          List<Integer> toDeleteIds = new ArrayList<Integer>();
          for (MaintenanceTicket ticket : tickets) {
            if (ticket.hasAsset() && ticket.getAsset().getAssetType().getName().equalsIgnoreCase(name)) {
                toDeleteIds.add(ticket.getId());
            }
          }
          for (Integer id : toDeleteIds) {
            MaintenanceTicket.getWithId(id).delete();
          }
        }
        AssetType.getWithName(name).delete();
        AssetPlusPersistence.save();
      }
    }
    catch (RuntimeException e){}
  }


  /**
   * <h3>getAssetTypes</h3>
   * This helper method returns the asset types as an observable list for JavaFX to be able to use it.
   * 
   * @return ObservableList<String> - Returns an ObservableList object containing all the asset types existing in the system.
   * 
   * @author Kevin Li
   */
  public static ObservableList<String> getAssetTypes() {
    List<String> assetTypeStrings = assetPlus.getAssetTypes().stream().map(AssetType::getName).toList();
    return FXCollections.observableList(assetTypeStrings);
  }


  /**
   * <h3>getAssetTypeAsList</h3>
   * This helper method method, just like the last one, returns a list of asset types, however, it
   * returns a List<String> because this is used in our page controllers.
   * 
   * @return List<String> - Returns a List object containing all the asset types that exist in the system stored as strings.
   * 
   * @author Luis Felipe Jarquin Romero
   */
  public static List<String> getAssetTypesAsList() {
    List<String> assetTypeStrings = assetPlus.getAssetTypes().stream().map(AssetType::getName).toList();
    return assetTypeStrings;
  }
}
