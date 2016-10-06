import java.util.List;
import org.sql2o.*;

public class EndangeredAnimal extends Animal {
  // Member Variables
  private String health;
  private String age;

  // Constructor
  public EndangeredAnimal(String _name, String _health, String _age) {
    super(_name);
    if(_name == null || _health == null || _age == null || _name.equals("") || _health.equals("") || _age.equals(""))
      throw new IllegalArgumentException("Endangered Species REQUIRE values for species name, healht, and age");
    setHealth(_health);
    setAge(_age);
    setType("endangered");
    update();
  }

  // Getters
  public String getHealth() {
    return health;
  }
  public String getAge() {
    return age;
  }

  // Setters
  public void setHealth(String _health) {
    health = _health;
    // update();
  }
  public void setAge(String _age) {
    age = _age;
    // update();
  }

  // Overrides
  @Override
  public boolean equals(Object _other) {
    if(!(_other instanceof EndangeredAnimal))
      return false;
    EndangeredAnimal newEndangeredAnimal = (EndangeredAnimal) _other;
    return id == newEndangeredAnimal.getId();
  }

  // CRUD
  //// Create
  @Override
  public void create(){
    try(Connection con = DB.sql2o.open()){
      id = (int) con.createQuery("INSERT INTO animals (name, type, health, age) VALUES (:name, :type, :health, :age)", true)
        .addParameter("name",name)
        .addParameter("type",type)
        .addParameter("health",health)
        .addParameter("age",age)
        .executeUpdate()
        .getKey();
    }
  }
  //// Read
  public static EndangeredAnimal readById(int _id){
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT * FROM animals WHERE id = :id")
        .addParameter("id",_id)
        .throwOnMappingFailure(false)
        .executeAndFetchFirst(EndangeredAnimal.class);
    }
  }
  public static EndangeredAnimal readByName(String _name){
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT * FROM animals WHERE name = :name")
        .addParameter("name",_name)
        .throwOnMappingFailure(false)
        .executeAndFetchFirst(EndangeredAnimal.class);
    }
  }
  public static List<EndangeredAnimal> readAllEndangeredExclusive(){
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT * FROM animals WHERE type = :type")
        .addParameter("type","endangered")
        .throwOnMappingFailure(false)
        .executeAndFetch(EndangeredAnimal.class);
    }
  }
  //// Update
  @Override
  public void update(){
    try(Connection con = DB.sql2o.open()){
      con.createQuery("UPDATE animals SET name=:name,type=:type,health=:health,age=:age WHERE id=:id", true)
        .addParameter("name",name)
        .addParameter("type",type)
        .addParameter("health",health)
        .addParameter("age",age)
        .addParameter("id",id)
        .executeUpdate();
    }
  }
}
