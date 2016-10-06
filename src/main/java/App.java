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

    get("/sightings/new",(request,response)->{
      Map<String,Object> model = new HashMap<>();
      // model.put("newRanger",newRanger);
      // model.put("newAnimal",newAnimal);
      // model.put("newLocation",newLocation);
      model.put("rangers", Sighting.readAllRangers());
      model.put("animals", Animal.readAllExclusive());
      model.put("endangeredAnimals", EndangeredAnimal.readAllEndangeredExclusive());
      model.put("locations", Sighting.readAllLocations());
      model.put("template", "templates/newElements.vtl");
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
        // template = "templates/newElements.vtl";
        model.put("newRanger",newRanger);
        model.put("newAnimal",newAnimal);
        model.put("newLocation",newLocation);
        // model.put("rangers", Sighting.readAllRangers());
        // model.put("animals", Animal.readAllExclusive());
        // model.put("endangeredAnimals", EndangeredAnimal.readAllEndangeredExclusive());
        // model.put("locations", Sighting.readAllLocations());
        // model.put("template", template);
        response.redirect("/sightings/new");
      }
      else{ // IF SUBMITTING DIRECTLY FROM INDEX
        Animal selectedAnimal = Animal.readByName(request.queryParams("select_animal"));
        String selectedLocation = request.queryParams("select_location");
        String selectedRanger = request.queryParams("select_ranger");
        String selectedHealth = request.queryParams("select_health");
        String selectedAge = request.queryParams("select_age");
        Sighting newSighting;
        if(selectedAnimal.getType().equals(Constants.ENDANGERED)){
          try{
            if(selectedHealth == null || selectedHealth.equals("") || selectedAge == null || selectedAge.equals("")){
              throw new IllegalArgumentException("Endangered Species REQUIRE NEW VALUES for HEALTH and AGE, specific to each sighting");
            }
            EndangeredAnimal newEndangeredAnimal = new EndangeredAnimal(selectedAnimal.getName(), selectedHealth, selectedAge);
            newEndangeredAnimal.create();
            newSighting = new Sighting(newEndangeredAnimal.getId(),selectedLocation,selectedRanger);
          }
          catch(IllegalArgumentException exception){
            String errorMessage = "ERROR: " + exception.getMessage();
            return errorMessage;
          }
        }
        else{
           newSighting = new Sighting(selectedAnimal.getId(),selectedLocation,selectedRanger);
        }
        newSighting.create();
        response.redirect("/");
      }
      return "Success!";
    });

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
          String errorMessage = "ERROR: " + exception.getMessage();
          return errorMessage;
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
