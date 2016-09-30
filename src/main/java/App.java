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
      model.put("rangers", Sighting.getAllRangers());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    },new VelocityTemplateEngine());

    // POST Requests
    post("/sightings/new",(request,response)->{
      Map<String,Object> model = new HashMap<>();
      model.put("modal", request.params("select_ranger")=="new"||request.params("select_animal")=="new"||request.params("select_location")=="new"?true:false);
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
