import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class AnimalTest {
  // Rules
  @Rule
  public DatabaseRule databaseRule = new DatabaseRule();

  // Tests
  //// Constructor
  @Test
  public void constructor_createsAnimal() {
    Animal testAnimal = new Animal("Mojojojo");
    assertEquals(true, testAnimal instanceof Animal);
  }

  //// Equals
  @Test
  public void equals_returnsFalseAppropriately_false() {
    Animal testAnimal1 = new Animal("Mojojojo");
    testAnimal1.create();
    Animal testAnimal2 = new Animal("Mojojojojojo");
    testAnimal2.create();
    assertEquals(false,testAnimal1.equals(testAnimal2));
  }
  @Test
  public void equals_returnsTrueAppropriately_true() {
    Animal testAnimal1 = new Animal("Mojojojo");
    testAnimal1.create();
    Animal testAnimal2 = testAnimal1;
    assertEquals(true,testAnimal1.equals(testAnimal2));
  }

  //// Read
  @Test
  public void readById_returnsCorrectAnimal_Animal() {
    Animal testAnimal = new Animal("Mojojojo");
    testAnimal.create();
    assertEquals(true,testAnimal.equals(Animal.readById(testAnimal.getId())));
  }
  @Test
  public void readByName_returnsCorrectAnimal_Animal() {
    Animal testAnimal = new Animal("Mojojojo");
    testAnimal.create();
    assertEquals(true,testAnimal.equals(Animal.readByName(testAnimal.getName())));
  }
  @Test
  public void readAll_returnsAllAnimals_ListAnimal() {
    List<Animal> expectedOutput = new ArrayList<>();
    Animal testAnimal1 = new Animal("Mojojojo");
    testAnimal1.create();
    Animal testAnimal2 = new Animal("Mojojojojojo");
    testAnimal2.create();
    expectedOutput.add(testAnimal1);
    expectedOutput.add(testAnimal2);
    assertEquals(expectedOutput,Animal.readAll());
  }
  @Test
  public void readAllExclusive_returnsAllAnimals_ListAnimal() {
    List<Animal> expectedOutput = new ArrayList<>();
    Animal testAnimal1 = new Animal("Mojojojo");
    testAnimal1.create();
    Animal testAnimal2 = new Animal("Mojojojojojo");
    testAnimal2.create();
    EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("MJ","test","test");
    testEndangeredAnimal.create();
    expectedOutput.add(testAnimal1);
    expectedOutput.add(testAnimal2);
    assertEquals(expectedOutput,Animal.readAllExclusive());
  }

  //// Update (via setName)
  @Test
  public void setName_updatesNameAppropriately_true() {
    String expectedOutput = "Mojojojojojo";
    Animal testAnimal = new Animal("Mojojojo");
    testAnimal.create();
    testAnimal.setName(expectedOutput);
    testAnimal.update();
    assertEquals(expectedOutput,Animal.readById(testAnimal.getId()).getName());
  }

  //// Delete
  @Test
  public void delete_removesFromDBAppropriately_true() {
    Animal testAnimal = new Animal("Mojojojo");
    testAnimal.create();
    testAnimal.delete();
    assertEquals(0,Animal.readAll().size());
  }
}
