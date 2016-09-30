import org.sql2o.*;

public class HelperMethods {

  public static int create(String _table, String _cols, String _newValues){
    int id = 0;
    try(Connection con = DB.sql2o.open()){
      String sql = "INSERT INTO :table (:columns) VALUES (:values)";
      id = (int) con.createQuery(sql, true)
        .addParameter("table", _table)
        .addParameter("columns", _cols)
        .addParameter("values", _newValues)
        .executeUpdate()
        .getKey();
    }
    return id;
  }

  public static void update(String _table, String _col, String _newValue, int _id){
    try(Connection con = DB.sql2o.open()){
      String sql = "UPDATE :table SET :column = :value WHERE id = :id";
      con.createQuery(sql, true)
        .addParameter("table",_table)
        .addParameter("column",_col)
        .addParameter("value",_newValue)
        .addParameter("id",_id)
        .executeUpdate();
    }
  }

  public void delete(String _table, int _id) {
   try(Connection con = DB.sql2o.open()) {
     String sql = "DELETE FROM :table WHERE id = :id";
     con.createQuery(sql)
       .addParameter("table",_table)
       .addParameter("id",_id)
       .executeUpdate();
   }
 }
}
