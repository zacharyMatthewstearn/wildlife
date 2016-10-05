import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Sighting {
  // Member Variables
  private int id = 0;
  private int animal_id = 0;
  private String location = "";
  private String ranger_name = "";

  // Constructor
  public Sighting(int _animal_id, String _location, String _ranger_name) {
    animal_id = _animal_id;
    location = _location;
    ranger_name = _ranger_name;
    create();
  }

  // Getters
  public int getId(){
    return id;
  }
  public int getAnimalId(){
    return animal_id;
  }
  public String getLocation(){
    return location;
  }
  public String getRangerName(){
    return ranger_name;
  }

  // Setters
  public void setLocation(String _location){
    location = _location;
    update();
  }
  public void setRangerName(String _rangerName){
    ranger_name = _rangerName;
    update();
  }

  // Overrides
  @Override
  public boolean equals(Object _other) {
    if(!(_other instanceof Sighting))
      return false;
    Sighting newSighting = (Sighting) _other;
    return id == newSighting.getId();
  }

  // CRUD
  public void create(){
    try(Connection con = DB.sql2o.open()){
      id = (int) con.createQuery("INSERT INTO sightings (animal_id, location, ranger_name) VALUES (:animal_id, :location, :ranger_name)", true)
        .addParameter("animal_id",animal_id)
        .addParameter("location",location)
        .addParameter("ranger_name",ranger_name)
        .executeUpdate()
        .getKey();
    }
  }
  public static Sighting readById(int _id){
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT * FROM sightings WHERE id = :id")
        .addParameter("id",_id)
        .executeAndFetchFirst(Sighting.class);
    }
  }
  public static List<Sighting> readAll(){
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT * FROM sightings")
        .executeAndFetch(Sighting.class);
    }
  }
  public static List<String> readAllRangers() {
    try(Connection con = DB.sql2o.open()){
      return con.createQuery("SELECT DISTINCT ranger_name FROM sightings ORDER BY ranger_name ASC")
      .executeAndFetch(String.class);
    }
  }
  public static List<String> readAllLocations() {
    try(Connection con = DB.sql2o.open()){
      return con.createQuery("SELECT DISTINCT location FROM sightings ORDER BY location ASC")
      .executeAndFetch(String.class);
    }
  }
  public static List<Sighting> readAllExclusive(){
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT * FROM animals WHERE health IS NULL AND age IS NULL")
        .executeAndFetch(Sighting.class);
    }
  }
  public void update(){
    try(Connection con = DB.sql2o.open()){
      con.createQuery("UPDATE sightings SET animal_id=:animal_id, location=:location, ranger_name=:ranger_name WHERE id = :id", true)
        .addParameter("animal_id",animal_id)
        .addParameter("location",location)
        .addParameter("ranger_name",ranger_name)
        .addParameter("id",id)
        .executeUpdate();
    }
  }
  public void delete(){
    try(Connection con = DB.sql2o.open()) {
      con.createQuery("DELETE FROM sightings WHERE id=:id")
        .addParameter("id",id)
        .executeUpdate();
    }
  }
}
