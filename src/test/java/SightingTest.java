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
  @Test
  public void constructor_createsSighting() {
    Sighting testSighting = new Sighting(1,"here","Blossom");
    assertEquals(true, testSighting instanceof Sighting);
  }

  // @Test
  // public void getAllRangers_returnsAllRangerNames_ListString() {
  //   List<String> expectedOutput = new ArrayList<>();
  //   Sighting sighting1 = new Sighting(1,"here","Blossom");
  //   Sighting sighting2 = new Sighting(1,"here","Buttercup");
  //   Sighting sighting3 = new Sighting(1,"here","Bubbles");
  //   expectedOutput.add("Blossom");
  //   expectedOutput.add("Buttercup");
  //   expectedOutput.add("Bubbles");
  //   assertEquals(expectedOutput, Sighting.getAllRangers());
  // }
}
