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
      return new ModelAndView(createModel(), layout);
    },new VelocityTemplateEngine());

    // POST Requests
  }

  // Private Methods
  private static Map<String,Object> createModel() {
    Map<String,Object> model = new HashMap<>();
    model.put("template", "templates/index.vtl");
    return model;
  }
}
