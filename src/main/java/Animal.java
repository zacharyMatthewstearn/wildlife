import java.util.List;
import org.sql2o.*;

public class Animal{
  // Member Variables
  protected int id = 0;
  protected String name = "";

  // Constructor
  public Animal(String _name){
    name = _name;
    this.create();
  }

  // Getters
  public int getId(){
    return id;
  }
  public String getName(){
    return name;
  }

  // Setters
  public void setName(String _name){
    name = _name;
    this.update();
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
      id = (int) con.createQuery("INSERT INTO animals (name) VALUES (:name)", true)
        .addParameter("name",name)
        .executeUpdate()
        .getKey();
    }
  }
  public static Animal readById(int _id){
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT id, name FROM animals WHERE id = :id")
        .addParameter("id",_id)
        .executeAndFetchFirst(Animal.class);
    }
  }
  public static Animal readByName(String _name){
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT id, name FROM animals WHERE name = :name")
        .addParameter("name",_name)
        .executeAndFetchFirst(Animal.class);
    }
  }
  public static List<Animal> readAll(){
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT id, name FROM animals")
        .executeAndFetch(Animal.class);
    }
  }
  public static List<Animal> readAllExclusive(){
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT id, name FROM animals WHERE health IS NULL AND age IS NULL")
        .executeAndFetch(Animal.class);
    }
  }
  public void update(){
    try(Connection con = DB.sql2o.open()){
      con.createQuery("UPDATE animals SET name = :name WHERE id = :id", true)
        .addParameter("name",name)
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
