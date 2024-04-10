package com.SAMLops.projetDevOps;
import java.util.List;
import java.util.ArrayList;

// Define a class to represent a DataFrame
class DataFrame {
    List<String> columns;
    List<List<Object>> data;
    List<Class<?>> columnTypes;

    public DataFrame(List<String> columns, List<List<Object>> data) {
        this.columns = columns;
        this.data = data;
        this.columnTypes = determineColumnTypes(data);
    }

    private List<Class<?>> determineColumnTypes(List<List<Object>> data) {
        List<Class<?>> columnTypes = new ArrayList<>();
        for (List<Object> row : data) {
            for (int i = 0; i < row.size(); i++) {
                Object value = row.get(i);
                Class<?> type = value != null ? value.getClass() : Object.class;
                if (columnTypes.size() <= i) {
                    columnTypes.add(type);
                } else {
                    Class<?> existingType = columnTypes.get(i);
                    if (existingType != type && existingType != Object.class) {
                        if (type == Double.class && existingType == Integer.class) {
                            columnTypes.set(i, Double.class); // Upgrade to Double
                        } else if (type == Integer.class && existingType == Double.class) {
                            // Existing type is already Double, no change needed
                        } else {
                            columnTypes.set(i, Object.class); // Incompatible types, downgrade to Object
                        }
                    }
                }
            }
        }
        return columnTypes;
    }


    // Method to display the entire dataframe
    public void display() {
        for (String col : columns) {
            System.out.print(col + "\t");
        }
        System.out.println();
        for (List<Object> row : data) {
            for (Object value : row) {
                System.out.print(value.toString() + "\t");
            }
            System.out.println();
        }
    }


}
