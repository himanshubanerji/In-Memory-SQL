package model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class Sql {
	Database db;
	public Sql(Database db){
		this.db = db;
	}

	// INSERT INTO EMPLOYEE values("");
	public void insert(String insertQuery) {
		String[] splitedQuery = insertQuery.toLowerCase().split("\\s");
		String[] values = parseValues(splitedQuery[3]);
		Table table = db.getTable(splitedQuery[2]);
		if (table == null) {
			System.out.print("Table not found");
			return;
		}
		Set<String> columns = table.getColumns().keySet();

		if (values.length != columns.size()) {
			System.out.println("Values length invalid " + columns.size() + " " + values.length);
      System.out.println(values[0]);
			return;
		}
		LinkedHashMap<String, Object> record = new LinkedHashMap<String, Object>();

		var wrapper = new Object() {
			int ordinal = 0;
		};
		columns.forEach(col -> insert(table, record, col, values[wrapper.ordinal++]));

		table.insert(record);
	}

	private void insert(Table table, Map<String, Object> record, String col, String value) {
		if (table.getColumns().get(col).type == Type.INTEGER)
			record.put(col, Integer.valueOf(value));
		else
			record.put(col, value);
	}

	private String[] parseValues(String splitedQuery) {
    return splitedQuery.replaceAll("values", "").replaceAll("\"", "").replaceAll("\\)", "").replaceAll("\"", "").replaceAll("\\(", "").replaceAll(";", "").split(",");
    //return splitedQuery.replaceFirst(".*values\\(", "").replaceAll("\\);", "").replaceAll("\"", "").trim().split("\\s*,\\s*");
    //return splitedQuery.replaceAll("values", "").replaceAll("\"", "").replaceAll("\\)", "").replaceAll("\"", "").replaceAll("\\(", "").replaceAll(";", "").split(",");
	}

}

