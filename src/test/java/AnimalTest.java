import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class AnimalTest {
  // Rules
  @Rule
  public DatabaseRule databaseRule = new DatabaseRule();

  // Tests
  @Test
  public void constructor_createsAnimal() {
    Animal testAnimal = new Animal("Mojojojo");
    assertEquals(true, testAnimal instanceof Animal);
  }
}
