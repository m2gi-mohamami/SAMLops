package com.SAMLops.projetDevOps;

import java.io.BufferedReader; // Import the BufferedReader class
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

    public DataFrame(String filePath) throws IOException {
        this.columns = new ArrayList<>();
        this.data = new ArrayList<>();
        this.columnTypes = new ArrayList<>();

        parseCSV(filePath);
    }

    private void parseCSV(String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Lire les noms de colonnes de la première ligne
            this.columns = Arrays.asList(br.readLine().split(","));

            // Utiliser la deuxième ligne pour déduire les types de colonnes
            String row = br.readLine(); // Cette ligne pourrait être utilisée pour déduire les types
            String[] sampleValues = row.split(",");
            for (String value : sampleValues) {
                this.columnTypes.add(determineType(value));
            }

            // Lire et convertir les données des lignes suivantes

            while (row != null) {
                List<Object> rowData = new ArrayList<>();
                String[] values = row.split(",");
                for (int i = 0; i < values.length; i++) {
                    rowData.add(convertToType(values[i], this.columnTypes.get(i)));

                }
                this.data.add(rowData);
                row = br.readLine();

            }

        }

    }

    private Class<?> determineType(String value) {
        try {
            Integer.parseInt(value);
            return Integer.class;
        } catch (NumberFormatException e1) {
            try {
                Double.parseDouble(value);
                return Double.class;
            } catch (NumberFormatException e2) {
                if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
                    return Boolean.class;
                }
            }
        }
        return String.class; // Par défaut, utiliser String si aucun autre type ne correspond
    }

    private Object convertToType(String value, Class<?> type) {
        if (type.equals(Integer.class)) {
            return Integer.parseInt(value);
        } else if (type.equals(Double.class)) {
            return Double.parseDouble(value);
        } else if (type.equals(Boolean.class)) {
            return Boolean.parseBoolean(value);
        }
        return value; // Si le type est String, retourner la valeur sans conversion
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

    /*Statistique functions */
    //fontion qui calcule le moyennage
    public Double calculateMean(String columnName) throws IndexOutOfBoundsException {
        int columnIndex = columns.indexOf(columnName);
        if (columnIndex == -1) throw new IndexOutOfBoundsException("Column not found: " + columnName);
        if (!isValidNumericColumn(columnIndex)) {
            throw new IllegalArgumentException("Column must be of type Integer or Double");
        }
        double sum = 0;
        int count = 0;
        for (List<Object> row : data) {
            try {
                Object value = row.get(columnIndex);
                if (value instanceof Number) {
                    sum += ((Number) value).doubleValue();
                    count++;
                }
            } catch (IndexOutOfBoundsException e) {
                System.err.println(e.getMessage());
            }
        }
        return count > 0 ? sum / count : null;
    }
    
    //fonction qui calcule la minima de column selected
    public Object calculateMin(String columnName) throws IndexOutOfBoundsException {
        int columnIndex = columns.indexOf(columnName);
        if (columnIndex == -1) throw new IndexOutOfBoundsException("Column not found: " + columnName);
        if (!isValidNumericColumn(columnIndex)) {
            throw new IllegalArgumentException("Column must be of type Integer or Double");
        }
        Object min = null;
        for (List<Object> row : data) {
            try {
                Object value = row.get(columnIndex);
                if (value instanceof Comparable && (min == null || ((Comparable) value).compareTo(min) < 0)) {
                    min = value;
                }
            } catch (IndexOutOfBoundsException e) {
                System.err.println(e.getMessage());
            }
        }
        return min;
    }
    
    //fonction qui calcule la maxima de column selected
    public Object calculateMax(String columnName) throws IndexOutOfBoundsException {
        int columnIndex = columns.indexOf(columnName);
        if (columnIndex == -1) throw new IndexOutOfBoundsException("Column not found: " + columnName);
        if (!isValidNumericColumn(columnIndex)) {
            throw new IllegalArgumentException("Column must be of type Integer or Double");
        }
        Object max = null;
        for (List<Object> row : data) {
            try {
                Object value = row.get(columnIndex);
                if (value instanceof Comparable && (max == null || ((Comparable) value).compareTo(max) > 0)) {
                    max = value;
                }
            } catch (IndexOutOfBoundsException e) {
                System.err.println(e.getMessage());
            }
        }
        return max;
    }
    
    //fonction qui verifie si le colonne est numérique ou pas
    public boolean isValidNumericColumn(int columnIndex) {
        Class<?> columnType = columnTypes.get(columnIndex);
        return columnType.equals(Integer.class) || columnType.equals(Double.class);
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
