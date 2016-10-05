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
    post("/sightings",(request,response)->{
      Map<String,Object> model = new HashMap<>();
      String template = "templates/index.vtl";
      Boolean newRanger = request.queryParams("select_ranger").equals("new");
      Boolean newAnimal = request.queryParams("select_animal").equals("new");
      Boolean newLocation = request.queryParams("select_location").equals("new");
      if(newRanger||newAnimal||newLocation){
        template = "templates/newElements.vtl";
        model.put("newRanger",newRanger);
        model.put("newAnimal",newAnimal);
        model.put("newLocation",newLocation);
      }
      else{
        // ADD CODE TO ACTUALLY ADD TO DB
      }
      model.put("rangers", Sighting.readAllRangers());
      model.put("animals", Animal.readAllExclusive());
      model.put("endangeredAnimals", EndangeredAnimal.readAllEndangeredExclusive());
      model.put("locations", Sighting.readAllLocations());
      model.put("template", template);
      return new ModelAndView(model, layout);
    },new VelocityTemplateEngine());

    post("/",(request,response)->{
      Map<String,Object> model = new HashMap<>();
      // ADD CODE TO ACTUALLY ADD TO DB
      // if(request.queryParams("check_endangered").equals("endangered")){
      //   try()
      //   EndangeredAnimal newEndangeredAnimal = new EndangeredAnimal();
      // }

      model.put("rangers", Sighting.readAllRangers());
      model.put("animals", Animal.readAllExclusive());
      model.put("endangeredAnimals", EndangeredAnimal.readAllEndangeredExclusive());
      model.put("locations", Sighting.readAllLocations());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    },new VelocityTemplateEngine());
  }


}
