import java.util.*;
import java.util.function.Predicate;

class Table {
    private String name;
    private List<String> columns;
    private List<Map<String, Object>> rows;

    public Table(String name, List<String> columns) {
        this.name = name;
        this.columns = columns;
        this.rows = new ArrayList<>();
    }

    // Insert
    public void insert(Map<String, Object> row) {
        for (String col : columns) {
            if (!row.containsKey(col)) {
                throw new IllegalArgumentException("Missing column: " + col);
            }
        }
        rows.add(row);
    }

    // Retrieve all rows
    public List<Map<String, Object>> searchAll() {
        return new ArrayList<>(rows);
    }

    // Search by column
    public List<Map<String, Object>> searchByColumns(List<String> selectedColumns) {
        List<Map<String, Object>> result = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            Map<String, Object> filteredRow = new HashMap<>();
            for (String col : selectedColumns) {
                if (!columns.contains(col)) {
                    throw new IllegalArgumentException("Column " + col + " does not exist.");
                }
                filteredRow.put(col, row.get(col));
            }
            result.add(filteredRow);
        }
        return result;
    }

    // Conditional search using predicates
    public List<Map<String, Object>> conditionalSearch(Predicate<Map<String, Object>> condition) {
        List<Map<String, Object>> result = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            if (condition.test(row)) {
                result.add(row);
            }
        }
        return result;
    }
}

// Database
class InMemoryDatabase {
    private Map<String, Table> tables;

    public InMemoryDatabase() {
        this.tables = new HashMap<>();
    }

    // Create
    public void createTable(String tableName, List<String> columns) {
        if (tables.containsKey(tableName)) {
            throw new IllegalArgumentException("Table " + tableName + " already exists.");
        }
        tables.put(tableName, new Table(tableName, columns));
    }

    public Table getTable(String tableName) {
        if (!tables.containsKey(tableName)) {
            throw new IllegalArgumentException("Table " + tableName + " does not exist.");
        }
        return tables.get(tableName);
    }
}

public class Main {
    public static void main(String[] args) {
        InMemoryDatabase database = new InMemoryDatabase();

        // Create table
        database.createTable("people", Arrays.asList("id", "first_name", "last_name", "age", "city"));

        // Insert rows
        Table peopleTable = database.getTable("people");
        peopleTable.insert(Map.of("id", 1, "first_name", "Himanshu", "last_name", "Banerji", "age", 22, "city", "Manipal"));
        peopleTable.insert(Map.of("id", 2, "first_name", "Abhishek", "last_name", "Patil", "age", 26, "city", "Bangalore"));
        peopleTable.insert(Map.of("id", 3, "first_name", "Heman", "last_name", "Heman", "age", 22, "city", "Manipal"));
        peopleTable.insert(Map.of("id", 4, "first_name", "Patrick", "last_name", "King", "age", 40, "city", "NewYork"));

        // 1. Print the whole table
        System.out.println("Whole table:");
        List<Map<String, Object>> allRows = peopleTable.searchAll();
        allRows.forEach(System.out::println);

        // 2. Insert an element into the table
        System.out.println("\nInserting new row:");
        peopleTable.insert(Map.of("id", 5, "first_name", "Shreya", "last_name", "Singh", "age", 22, "city", "Manipal"));
        allRows = peopleTable.searchAll();
        allRows.forEach(System.out::println);

        // 3. Return results where first_name == last_name
        System.out.println("\nPeople where first_name == last_name:");
        List<Map<String, Object>> nameMatchResults = peopleTable.conditionalSearch(
            row -> row.get("first_name").equals(row.get("last_name"))
        );
        nameMatchResults.forEach(System.out::println);

        // 4. Return all names of people who belong to "Manipal"
        System.out.println("\nNames of people who belong to Manipal:");
        List<Map<String, Object>> manipalResults = peopleTable.conditionalSearch(
            row -> row.get("city").equals("Manipal")
        );
        manipalResults.forEach(row -> System.out.println(row.get("first_name") + " " + row.get("last_name")));

        // 5. Return all names of people who belong to "Manipal" and have first_name == last_name
        System.out.println("\nNames of people who belong to Manipal and have first_name == last_name:");
        List<Map<String, Object>> manipalMatches = peopleTable.conditionalSearch(
            row -> row.get("city").equals("Manipal") && row.get("first_name").equals(row.get("last_name"))
        );
        manipalMatches.forEach(row -> System.out.println(row.get("first_name") + " " + row.get("last_name")));
    }
}

