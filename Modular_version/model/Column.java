package model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Column {
	Type type;
	private String name;
	public Column(String name, Type type) {
		this.name = name;
		this.type = type;
	}

	public Type getType() {
		return type;
	}

	public String getName() {
		return name;
	}

}

