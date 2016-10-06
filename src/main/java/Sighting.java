import java.sql.Timestamp;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;
import java.text.SimpleDateFormat;

public class Sighting {
  // Member Variables
  private int id = 0;
  private int animal_id = 0;
  private String location = "";
  private String ranger_name = "";
  private Timestamp time_sighted;

  // Constructor
  public Sighting(int _animal_id, String _location, String _ranger_name) {
    animal_id = _animal_id;
    location = _location;
    ranger_name = _ranger_name;
    time_sighted = new Timestamp(new Date().getTime());
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
  public String getTimeSighted(){
    String pattern = "MM/dd/yyyy HH:mm:ss";
    SimpleDateFormat format = new SimpleDateFormat(pattern);
    System.out.println("Output: " + time_sighted);
    return format.format(time_sighted);
  }

  // Setters
  public void setLocation(String _location){
    location = _location;
    // update();
  }
  public void setRangerName(String _rangerName){
    ranger_name = _rangerName;
    // update();
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
      id = (int) con.createQuery("INSERT INTO sightings (animal_id, location, ranger_name, time_sighted) VALUES (:animal_id, :location, :ranger_name, :time_sighted)", true)
        .addParameter("animal_id",animal_id)
        .addParameter("location",location)
        .addParameter("ranger_name",ranger_name)
        .addParameter("time_sighted",time_sighted)
        .executeUpdate()
        .getKey();
    }
  }
  public static Sighting readById(int _id){
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT * FROM sightings WHERE id = :id")
        .addParameter("id",_id)
        .throwOnMappingFailure(false)
        .executeAndFetchFirst(Sighting.class);
    }
  }
  public Animal readAnimal(){
    try(Connection con = DB.sql2o.open()) {
      try{
        EndangeredAnimal newEndangeredAnimal = (EndangeredAnimal)
        con.createQuery("SELECT * FROM animals WHERE id = :id")
        .addParameter("id",animal_id)
        .throwOnMappingFailure(false)
        .executeAndFetchFirst(EndangeredAnimal.class);
        return newEndangeredAnimal;
      }
      catch(IllegalArgumentException exception){
        Animal newAnimal = (Animal)
        con.createQuery("SELECT * FROM animals WHERE id = :id")
        .addParameter("id",animal_id)
        .throwOnMappingFailure(false)
        .executeAndFetchFirst(Animal.class);
        return newAnimal;
      }
    }
  }
  public static List<Sighting> readAll(){
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT * FROM sightings")
        .throwOnMappingFailure(false)
        .executeAndFetch(Sighting.class);
    }
  }
  public static List<String> readAllRangers() {
    try(Connection con = DB.sql2o.open()){
      return con.createQuery("SELECT DISTINCT ranger_name FROM sightings ORDER BY ranger_name ASC")
      .throwOnMappingFailure(false)
      .executeAndFetch(String.class);
    }
  }
  public static List<String> readAllLocations() {
    try(Connection con = DB.sql2o.open()){
      return con.createQuery("SELECT DISTINCT location FROM sightings ORDER BY location ASC")
      .throwOnMappingFailure(false)
      .executeAndFetch(String.class);
    }
  }
  public static List<Sighting> readAllExclusive(){
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT * FROM animals WHERE health IS NULL AND age IS NULL")
        .throwOnMappingFailure(false)
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
