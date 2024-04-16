package com.SAMLops.projetDevOps;

import java.util.ArrayList;
import java.util.List;

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

    // Method to display the first n rows of the dataframe
    public void displayFirstRows(int n) {
        for (String col : columns) {
            System.out.print(col + "\t");
        }
        System.out.println();
        for (int i = 0; i < Math.min(n, data.size()); i++) {
            List<Object> row = data.get(i);
            for (Object value : row) {
                System.out.print(value.toString() + "\t");
            }
            System.out.println();
        }
    }

    public void displayLastRows(int n) {
        for (String col : columns) {
            System.out.print(col + "\t");
        }
        System.out.println();
        for (int i = Math.max(0, data.size() - n); i < data.size(); i++) {
            List<Object> row = data.get(i);
            for (Object value : row) {
                System.out.print(value.toString() + "\t");
            }
            System.out.println();
        }
    }


    /******************SELECTION*********************/

    //ROWS 
    public DataFrame selectRowsByIndices(List<Integer> indices) {
        List<List<Object>> newData = new ArrayList<>();
        for (int index : indices) {
            if (index >= 0 && index < data.size()) {
                newData.add(new ArrayList<>(data.get(index)));
            }
        }
        return new DataFrame(new ArrayList<>(columns), newData);
    }


    public DataFrame selectColumnsByLabels(List<String> selectedColumns) {
        List<Integer> indices = new ArrayList<>();
        for (String col : selectedColumns) {
            int index = columns.indexOf(col);
            if (index != -1) {
                indices.add(index);
            }
        }
    
        List<List<Object>> newData = new ArrayList<>();
        for (List<Object> row : data) {
            List<Object> newRow = new ArrayList<>();
            for (int index : indices) {
                newRow.add(row.get(index));
            }
            newData.add(newRow);
        }
    
        return new DataFrame(selectedColumns, newData);
    }


    public DataFrame advancedSelectionWithColumnValue(String columnName, Object value) {
        int columnIndex = columns.indexOf(columnName);
        if (columnIndex == -1) {
            return null; // Column not found
        }
    
        List<List<Object>> newData = new ArrayList<>();
        for (List<Object> row : data) {
            if (row.get(columnIndex).equals(value)) {
                newData.add(new ArrayList<>(row));
            }
        }
    
        return new DataFrame(new ArrayList<>(columns), newData);
    }
     

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    public List<List<Object>> getData() {
        return data;
    }

    public void setData(List<List<Object>> data) {
        this.data = data;
    }

    public List<Class<?>> getColumnTypes() {
        return columnTypes;
    }

    public void setColumnTypes(List<Class<?>> columnTypes) {
        this.columnTypes = columnTypes;
    }

}
