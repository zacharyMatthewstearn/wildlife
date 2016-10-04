import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class EndangeredAnimalTest {
  // Rules
  @Rule
  public DatabaseRule databaseRule = new DatabaseRule();

  // Tests
  //// Constructor
  @Test
  public void constructor_createsEndangeredAnimal() {
    EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("Mojojojo","test","test");
    assertEquals(true, testEndangeredAnimal instanceof EndangeredAnimal);
  }

  //// Equals
  @Test
  public void equals_returnsFalseAppropriately_false() {
    EndangeredAnimal testEndangeredAnimal1 = new EndangeredAnimal("Mojojojo","test","test");
    EndangeredAnimal testEndangeredAnimal2 = new EndangeredAnimal("Mojojojojojo","test","test");
    assertEquals(false,testEndangeredAnimal1.equals(testEndangeredAnimal2));
  }
  @Test
  public void equals_returnsTrueAppropriately_true() {
    EndangeredAnimal testEndangeredAnimal1 = new EndangeredAnimal("Mojojojo","test","test");
    EndangeredAnimal testEndangeredAnimal2 = testEndangeredAnimal1;
    assertEquals(true,testEndangeredAnimal1.equals(testEndangeredAnimal2));
  }

  //// Read
  @Test
  public void readById_returnsCorrectEndangeredAnimal_EndangeredAnimal() {
    EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("Mojojojo","test","test");
    assertEquals(true,testEndangeredAnimal.equals(EndangeredAnimal.readById(testEndangeredAnimal.getId())));
  }
  @Test
  public void readByName_returnsCorrectEndangeredAnimal_EndangeredAnimal() {
    EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("Mojojojo","test","test");
    assertEquals(true,testEndangeredAnimal.equals(EndangeredAnimal.readByName(testEndangeredAnimal.getName())));
  }
  @Test
  public void readAllEndangered_returnsAllEndangeredAnimals_ListEndangeredAnimal() {
    List<EndangeredAnimal> expectedOutput = new ArrayList<>();
    EndangeredAnimal testEndangeredAnimal1 = new EndangeredAnimal("Mojojojo","test","test");
    EndangeredAnimal testEndangeredAnimal2 = new EndangeredAnimal("Mojojojojojo","test","test");
    expectedOutput.add(testEndangeredAnimal1);
    expectedOutput.add(testEndangeredAnimal2);
    assertEquals(expectedOutput,EndangeredAnimal.readAllEndangered());
  }
  @Test
  public void readAllEndangeredExclusive_returnsAllEndangeredAnimals_ListEndangeredAnimal() {
    List<EndangeredAnimal> expectedOutput = new ArrayList<>();
    EndangeredAnimal testEndangeredAnimal1 = new EndangeredAnimal("Mojojojo","test","test");
    EndangeredAnimal testEndangeredAnimal2 = new EndangeredAnimal("Mojojojojojo","test","test");
    Animal testAnimal = new Animal("MJ");
    expectedOutput.add(testEndangeredAnimal1);
    expectedOutput.add(testEndangeredAnimal2);
    assertEquals(expectedOutput,EndangeredAnimal.readAllEndangeredExclusive());
  }

  //// Update (via setName)
  @Test
  public void setName_updatesNameAppropriately_true() {
    String expectedOutput = "Mojojojojojo";
    EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("Mojojojo","test","test");
    testEndangeredAnimal.setName(expectedOutput);
    assertEquals(expectedOutput,EndangeredAnimal.readById(testEndangeredAnimal.getId()).getName());
  }

  //// Delete
  @Test
  public void delete_removesFromDBAppropriately_true() {
    EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("Mojojojo","test","test");
    testEndangeredAnimal.delete();
    assertEquals(0,EndangeredAnimal.readAll().size());
  }
}
