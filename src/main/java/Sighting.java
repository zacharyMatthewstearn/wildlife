import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Sighting {
  // Member Variables
  private int id = 0;
  private int animalId = 0;
  private String location = "";
  private String rangerName = "";

  // Constructor
  public Sighting(int _animalId, String _location, String _rangerName) {
    String table = "sightings";
    String columns = "animal_id, location, ranger_name";
    Integer tempAnimalId = (Integer) _animalId;
    String values = tempAnimalId.toString() + ", " + _location + ", " + _rangerName;
    // id = HelperMethods.create(table,columns,values);
  }

  // Getters
  public int getId(){
    return id;
  }
  public int getAnimalId(){
    return animalId;
  }
  public String getLocation(){
    return location;
  }
  public String getRangerName(){
    return rangerName;
  }

  // Setters
  public void setLocation(String _location){
    location = _location;
    // HelperMethods.update("sightings","location",location,id);
  }
  public void setRangerName(String _rangerName){
    rangerName = _rangerName;
    // HelperMethods.update("sightings","ranger_name",rangerName,id);
  }

  // Overrides
  @Override
  public boolean equals(Object _other) {
    if(!(_other instanceof Sighting))
      return false;
    Sighting newSighting = (Sighting) _other;
    return id == newSighting.getId();
  }

  // Static Methods
  public static List<String> getAllRangers() {
    List<String> temp = new ArrayList<>();
    try(Connection con = DB.sql2o.open()){
      temp = con.createQuery("SELECT DISTINCT ranger_name FROM sightings ORDER BY ranger_name ASC")
      .executeAndFetch(String.class);
    }
    return temp;
  }
}
