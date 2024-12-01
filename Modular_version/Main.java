import java.util.*;

import model.*;

public class Main {
  public static void main(String[] args) {
    Database database = Database.getInstance("city");
    Sql sql = new Sql(database);
    Table employee = database.createTable("people");

    // Creation of table
    Column id = employee.createColumn("id", Type.INTEGER);
    Column firstName = employee.createColumn("first_name", Type.STRING);
    Column lastName = employee.createColumn("last_name", Type.STRING);
    Column age = employee.createColumn("age", Type.INTEGER);
    Column city = employee.createColumn("city", Type.STRING);


    //Insert values
    sql.insert("INSERT INTO PEOPLE values(1,\"Himanshu\",\"Banerji\",22,\"Manipal\");");
    sql.insert("INSERT INTO PEOPLE values(2,\"Abhishek\",\"Patil\",26,\"Bengaluru\");");
    sql.insert("INSERT INTO PEOPLE values(3,\"Jason\",\"DMello\",20,\"Manipal\");");
    sql.insert("INSERT INTO PEOPLE values(4,\"Viraj\",\"Desai\",21,\"Manipal\");");

    // SELECT * FROM PEOPLE
    database.getTable("people").printAllRecords();
    System.out.println("--------------------");

    //Query

  }
}
