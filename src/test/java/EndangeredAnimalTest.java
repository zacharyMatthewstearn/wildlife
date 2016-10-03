import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class EndangeredAnimalTest {
  // Rules
  @Rule
  public DatabaseRule databaseRule = new DatabaseRule();

  // Tests
  // @Test
  // public void constructor_createsEndangeredAnimal() {
  //   EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("Mojojojo!",Constants.HEALTHY,Constants.ADULT);
  //   assertEquals(true, testEndangeredAnimal instanceof EndangeredAnimal);
  // }
}
