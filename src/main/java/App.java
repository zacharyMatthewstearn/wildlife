import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.sql2o.*;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    //////////////////////////////////////
    // TEST
    try(Connection con = DB.sql2o.open()){
      con.createQuery("DELETE FROM sightings *").executeUpdate();
      con.createQuery("DELETE FROM animals *").executeUpdate();
    }
    Animal testAnimal = new Animal("testAnimal");
    EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("testEndangeredAnimal","testHealth","testAge");
    Sighting testSighting1 = new Sighting(testAnimal.getId(),"testLocation1","testRangerName1");
    Sighting testSighting2 = new Sighting(testEndangeredAnimal.getId(),"testLocation2","testRangerName2");
    //////////////////////////////////////

    // GET Requests
    get("/",(request,response)->{
      Map<String,Object> model = new HashMap<>();
      model.put("rangers", Sighting.readAllRangers());
      model.put("animals", Animal.readAllExclusive());
      model.put("endangeredAnimals", EndangeredAnimal.readAllEndangeredExclusive());
      model.put("locations", Sighting.readAllLocations());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    },new VelocityTemplateEngine());

    // POST Requests
    post("/sightings/new",(request,response)->{
      Map<String,Object> model = new HashMap<>();
      model.put("modal", request.params("select_ranger")=="new"||request.params("select_animal")=="new"||request.params("select_location")=="new"?true:false);
      model.put("rangers", Sighting.readAllRangers());
      model.put("animals", Animal.readAllExclusive());
      model.put("endangeredAnimals", EndangeredAnimal.readAllEndangeredExclusive());
      model.put("locations", Sighting.readAllLocations());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    },new VelocityTemplateEngine());
  }

  // Private Methods
  // private static Map<String,Object> createModel() {
  //   Map<String,Object> model = new HashMap<>();
  //   model.put("template", "templates/index.vtl");
  //   return model;
  // }
}
