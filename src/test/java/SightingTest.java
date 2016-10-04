import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class SightingTest {
  // Rules
  @Rule
  public DatabaseRule databaseRule = new DatabaseRule();

  // Tests
  //// Constructor
  @Test
  public void constructor_createsSighting() {
    Sighting testSighting = new Sighting(1,"here","Mojojojo");
    assertEquals(true, testSighting instanceof Sighting);
  }

  //// Equals
  @Test
  public void equals_returnsFalseAppropriately_false() {
    Sighting testSighting1 = new Sighting(1,"here","Mojojojo");
    Sighting testSighting2 = new Sighting(1,"here","Mojojojojojo");
    assertEquals(false,testSighting1.equals(testSighting2));
  }
  @Test
  public void equals_returnsTrueAppropriately_true() {
    Sighting testSighting1 = new Sighting(1,"here","Mojojojo");
    Sighting testSighting2 = testSighting1;
    assertEquals(true,testSighting1.equals(testSighting2));
  }

  //// Read
  @Test
  public void readById_returnsCorrectSighting_Sighting() {
    Sighting testSighting = new Sighting(1,"here","Mojojojo");
    assertEquals(true,testSighting.equals(Sighting.readById(testSighting.getId())));
  }
  @Test
  public void readAll_returnsAllSightings_ListSighting() {
    List<Sighting> expectedOutput = new ArrayList<>();
    Sighting testSighting1 = new Sighting(1,"here","Mojojojo");
    Sighting testSighting2 = new Sighting(1,"here","Mojojojojojo");
    expectedOutput.add(testSighting1);
    expectedOutput.add(testSighting2);
    assertEquals(expectedOutput,Sighting.readAll());
  }

  //// Update (via setName)
  @Test
  public void setName_updatesNameAppropriately_true() {
    String expectedOutput = "Mojojojojojo";
    Sighting testSighting = new Sighting(1,"here","Mojojojo");
    testSighting.setRangerName(expectedOutput);
    assertEquals(expectedOutput,Sighting.readById(testSighting.getId()).getRangerName());
  }

  //// Delete
  @Test
  public void delete_removesFromDBAppropriately_true() {
    Sighting testSighting = new Sighting(1,"here","Mojojojo");
    testSighting.delete();
    assertEquals(0,Sighting.readAll().size());
  }
}
