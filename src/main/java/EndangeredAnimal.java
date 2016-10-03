import org.sql2o.*;

public class EndangeredAnimal extends Animal {
  // Member Variables
  private String health = "";
  private String age = "";

  // Constructor
  public EndangeredAnimal(String _name, String _health, String _age) {
    super(_name);
    setHealth(_health);
    setAge(_age);
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
    update();
  }
  public void setAge(String _age) {
    age = _age;
    update();
  }

  // Overrides
  // @Override
  // public boolean equals(Object _other) {
  //   if(!(_other instanceof EndangeredAnimal))
  //     return false;
  //   EndangeredAnimal newEndangeredAnimal = (EndangeredAnimal) _other;
  //   return id == newEndangeredAnimal.getId();
  // }

  // CRUD
  @Override
  public void create(){
    try(Connection con = DB.sql2o.open()){
      id = (int) con.createQuery("INSERT INTO animals (name, health, age) VALUES (:name, :health, :age)", true)
        .addParameter("name",name)
        .addParameter("health",health)
        .addParameter("age",age)
        .executeUpdate()
        .getKey();
    }
  }
  @Override
  public void update(){
    try(Connection con = DB.sql2o.open()){
      con.createQuery("UPDATE animals SET name=:name,health=:health,age=:age WHERE id=:id", true)
        .addParameter("name",name)
        .addParameter("health",health)
        .addParameter("age",age)
        .addParameter("id",id)
        .executeUpdate();
    }
  }
}
