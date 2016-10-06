import java.util.List;
import org.sql2o.*;

public class Animal{
  // Member Variables
  protected int id = 0;
  protected String name;
  protected String type = Constants.UNENDANGERED;

  // Constructor
  public Animal(String _name){
    name = _name;
  }

  // Getters
  public int getId(){
    return id;
  }
  public String getName(){
    return name;
  }
  public String getType(){
    return type;
  }

  // Setters
  public void setName(String _name){
    name = _name;
    // update();
  }
  public void setType(String _type){
    type = _type;
    // update();
  }

  // Overrides
  @Override
  public boolean equals(Object _other){
    if(!(_other instanceof Animal))
      return false;
    Animal newAnimal = (Animal) _other;
    return id == newAnimal.getId();
  }

  // CRUD
  public void create(){
    try(Connection con = DB.sql2o.open()){
      id = (int) con.createQuery("INSERT INTO animals (name, type) VALUES (:name, :type)", true)
        .addParameter("name",name)
        .addParameter("type",type)
        .executeUpdate()
        .getKey();
    }
  }
  public static Animal readById(int _id){
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT id, name, type FROM animals WHERE id = :id")
        .addParameter("id",_id)
        .throwOnMappingFailure(false)
        .executeAndFetchFirst(Animal.class);
    }
  }
  public static Animal readByName(String _name){
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT id, name, type FROM animals WHERE name = :name")
        .addParameter("name",_name)
        .executeAndFetchFirst(Animal.class);
    }
  }
  public static List<Animal> readAll(){
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT id, name, type FROM animals")
        .throwOnMappingFailure(false)
        .executeAndFetch(Animal.class);
    }
  }
  public static List<Animal> readAllExclusive(){
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT id, name, type FROM animals WHERE type = :type")
        .addParameter("type",Constants.UNENDANGERED)
        .throwOnMappingFailure(false)
        .executeAndFetch(Animal.class);
    }
  }
  public void update(){
    try(Connection con = DB.sql2o.open()){
      con.createQuery("UPDATE animals SET name = :name, type = :type WHERE id = :id", true)
        .addParameter("name",name)
        .addParameter("type",type)
        .addParameter("id",id)
        .executeUpdate();
    }
  }
  public void delete(){
    try(Connection con = DB.sql2o.open()) {
      con.createQuery("DELETE FROM animals WHERE id = :id")
        .addParameter("id",id)
        .executeUpdate();
    }
  }
}
