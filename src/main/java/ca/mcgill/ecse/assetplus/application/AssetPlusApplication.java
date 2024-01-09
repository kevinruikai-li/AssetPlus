package ca.mcgill.ecse.assetplus.application;

import ca.mcgill.ecse.assetplus.model.AssetPlus;
import ca.mcgill.ecse.assetplus.persistence.AssetPlusPersistence;
import javafx.application.Application;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFxmlView;
import java.sql.Date;
import java.time.LocalDate;

public class AssetPlusApplication {

  private static AssetPlus assetPlus;
  public static final boolean DARK_MODE = true;
  private static Date currentDate;

  public static void main(String[] args) {
    Application.launch(AssetPlusFxmlView.class, args);
  }

  public static AssetPlus getAssetPlus() {
    if (assetPlus == null) {
      // these attributes are default, you should set them later with the setters
      assetPlus = AssetPlusPersistence.load();
    }
    return assetPlus;
  }

  public static Date getCurrentDate() {
    if (currentDate == null) {
      return Date.valueOf(LocalDate.now());
    }

    return currentDate;
  }

  public static void setCurrentDate(Date date) {
    currentDate = date;
  }
}