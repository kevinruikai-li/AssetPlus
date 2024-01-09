package ca.mcgill.ecse.assetplus.persistence;

import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.AssetPlus;

public class AssetPlusPersistence {

  private static String filename = "ap.data";
  private static JsonSerializer serializer = new JsonSerializer("ca.mcgill.ecse.assetplus");

  public static void setFilename(String filename) {
    AssetPlusPersistence.filename = filename;
  }

  public static void save() {
    save(AssetPlusApplication.getAssetPlus());
  }

  public static void save(AssetPlus assetplus) {
    serializer.serialize(assetplus, filename);
  }

  public static AssetPlus load() {
    var assetplus = (AssetPlus) serializer.deserialize(filename);
    // model cannot be loaded - create empty AssetPlus
    if (assetplus == null) {
      assetplus = new AssetPlus();
    } else {
      assetplus.reinitialize();
    }
    return assetplus;
  }
}
