import org.sql2o.*;

public class Animal {
  // Member Variables
  protected int id = 0;
  protected String name = "";

  // Constructor
  public Animal(String _name) {
    name = _name;
    String table = "animals";
    String columns = "name";
    String values = name;
    id = HelperMethods.create(table,columns,values);
  }

  // Getters
  public int getId() {
    return id;
  }
  public String getName() {
    return name;
  }

  // Setters
  public void setName(String _name) {
    name = _name;
    HelperMethods.update("animals","name",name,id);
  }

  // Overrides
  @Override
  public boolean equals(Object _other) {
    if(!(_other instanceof Animal))
      return false;
    Animal newAnimal = (Animal) _other;
    return id == newAnimal.getId();
  }
}
