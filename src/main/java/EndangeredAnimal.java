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
    HelperMethods.update("animals","health",health,id);
  }
  public void setAge(String _age) {
    age = _age;
    HelperMethods.update("animals","age",age,id);
  }

  // Overrides
  @Override
  public boolean equals(Object _other) {
    if(!(_other instanceof EndangeredAnimal))
      return false;
    EndangeredAnimal newEndangeredAnimal = (EndangeredAnimal) _other;
    return id == newEndangeredAnimal.getId();
  }
}
