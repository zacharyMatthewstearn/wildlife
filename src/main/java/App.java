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
    // try(Connection con = DB.sql2o.open()){
    //   con.createQuery("DELETE FROM sightings *").executeUpdate();
    //   con.createQuery("DELETE FROM animals *").executeUpdate();
    // }
    // Animal testAnimal = new Animal("testAnimal");
    // EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("testEndangeredAnimal","testHealth","testAge");
    // Sighting testSighting1 = new Sighting(testAnimal.getId(),"testLocation1","testRangerName1");
    // Sighting testSighting2 = new Sighting(testEndangeredAnimal.getId(),"testLocation2","testRangerName2");
    //////////////////////////////////////

    // GET Requests
    get("/",(request,response)->{
      Map<String,Object> model = new HashMap<>();
      model.put("rangers", Sighting.readAllRangers());
      model.put("animals", Animal.readAllExclusive());
      model.put("endangeredAnimals", EndangeredAnimal.readAllEndangeredExclusive());
      model.put("locations", Sighting.readAllLocations());
      model.put("sightings", Sighting.readAll());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    },new VelocityTemplateEngine());

    // POST Requests
    post("/sightings/new",(request,response)->{ // On hitting SUBMIT button on Index
      Map<String,Object> model = new HashMap<>();
      String template = "templates/index.vtl";
      Boolean newRanger = request.queryParams("select_ranger").equals("new");
      Boolean newAnimal = request.queryParams("select_animal").equals("new");
      Boolean newLocation = request.queryParams("select_location").equals("new");

      if(newRanger||newAnimal||newLocation){ // IF GOING TO FORM PAGE
        template = "templates/newElements.vtl";
        model.put("newRanger",newRanger);
        model.put("newAnimal",newAnimal);
        model.put("newLocation",newLocation);
        model.put("rangers", Sighting.readAllRangers());
        model.put("animals", Animal.readAllExclusive());
        model.put("endangeredAnimals", EndangeredAnimal.readAllEndangeredExclusive());
        model.put("locations", Sighting.readAllLocations());
        model.put("Animal", Animal.class);
        model.put("template", template);
      }
      else{ // IF SUBMITTING DIRECTLY FROM INDEX
        Sighting newSighting = new Sighting(Animal.readByName(request.queryParams("select_animal")).getId(),request.queryParams("select_location"),request.queryParams("select_ranger"));
        newSighting.create();
        response.redirect("/");
      }

      return new ModelAndView(model, layout);
    },new VelocityTemplateEngine());

    post("/",(request,response)->{
      String newSpeciesName = request.queryParams("input_animal");
      String newSpeciesHealth = request.queryParams("select_health");
      String newSpeciesAge = request.queryParams("select_age");
      int newAnimalId = 0;
      if(request.queryParams("check_endangered") != null){
        try{
          EndangeredAnimal newEndangeredAnimal = new EndangeredAnimal(newSpeciesName,newSpeciesHealth,newSpeciesAge);
          newEndangeredAnimal.create();
          newAnimalId = newEndangeredAnimal.getId();
        }
        catch(IllegalArgumentException exception){
          return "When attempting to add an endangered species to the database, you MUST include information on the specimen's health and age at the time of sighting";
        }
      }
      else{
        Animal newAnimal = new Animal(newSpeciesName);
        newAnimal.create();
        newAnimalId = newAnimal.getId();
      }
      Sighting newSighting = new Sighting(newAnimalId,request.queryParams("input_location"),request.queryParams("input_ranger"));
      newSighting.create();
      response.redirect("/");
      return "Success!";
    });
  }



}
