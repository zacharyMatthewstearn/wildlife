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
        Sighting newSighting = new Sighting(Animal.readByName(request.queryParams("select_animal")).getId(),request.queryParams("select_location"),request.queryParams("select_ranger"));
      }
      response.redirect("/");
      return "Success!";
    //   model.put("rangers", Sighting.readAllRangers());
    //   model.put("animals", Animal.readAllExclusive());
    //   model.put("endangeredAnimals", EndangeredAnimal.readAllEndangeredExclusive());
    //   model.put("locations", Sighting.readAllLocations());
    //   model.put("Animal", Animal.class);
    //   model.put("template", template);
    //   return new ModelAndView(model, layout);
    // },new VelocityTemplateEngine());
    });

    post("/",(request,response)->{
      String newSpeciesName = request.queryParams("input_animal");
      String newSpeciesHealth = request.queryParams("select_health");
      String newSpeciesAge = request.queryParams("select_age");
      if(request.queryParams("check_endangered") != null){
        try{
          EndangeredAnimal newEndangeredAnimal = new EndangeredAnimal(newSpeciesName,newSpeciesHealth,newSpeciesAge);
        }
        catch(IllegalArgumentException exception){
          return "When attempting to add an endangered species to the database, you MUST include information on the specimen's health and age at the time of sighting";
        }
      }
      try(Connection con = DB.sql2o.open()){
        String addSpecies = "INSERT INTO animals (name, type, health, age) VALUES (:name, :type, :health, :age)";
        String type = "unendangered";
        if(request.queryParams("check_endangered") != null)
          type = "endangered";
        int newAnimalId = (int) con.createQuery(addSpecies,true)
          .addParameter("name",newSpeciesName)
          .addParameter("type",type)
          .addParameter("health",newSpeciesHealth)
          .addParameter("age",newSpeciesAge)
          .executeUpdate()
          .getKey();
        Sighting newSighting = new Sighting(newAnimalId,request.queryParams("input_location"),request.queryParams("input_ranger"));
        // String addSighting = "INSERT INTO sightings (animal_id, location, ranger_name) VALUES (:animal_id, :location, :ranger_name)";
        // con.createQuery(addSighting,true)
        //   .addParameter("animal_id",newAnimalId)
        //   .addParameter("location",request.queryParams("input_location"))
        //   .addParameter("ranger_name",request.queryParams("input_ranger"))
        //   .executeUpdate();
      }
      response.redirect("/");
      return "Success!";
    });
  }



}
