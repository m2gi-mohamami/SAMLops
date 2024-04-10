package com.SAMLops.projetDevOps;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

public class DataFrameTest extends TestCase {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    public DataFrameTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(DataFrameTest.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        System.setOut(new PrintStream(outContent));
    }

    @Override
    protected void tearDown() throws Exception {
        System.setOut(originalOut);
        super.tearDown();
    }

    public void testDetermineColumnTypes() {
        List<String> columns = Arrays.asList("Name", "Age", "Salary", "PartTime");
        List<List<Object>> data = Arrays.asList(
            Arrays.asList("Alice", 30, 2500.0, true),
            Arrays.asList("Bob", 29, 3200, false), // Mixed integer and double for Salary
            Arrays.asList("Charlie", 25, 2800.0, false)
        );

        DataFrame df = new DataFrame(columns, data);

        // Assuming getColumnTypes is a public method you've added for testing
        List<Class<?>> columnTypes = df.getColumnTypes();

        assertEquals(String.class, columnTypes.get(0)); // Name column type
        assertEquals(Integer.class, columnTypes.get(1)); // Age column type
        assertEquals(Double.class, columnTypes.get(2)); // Salary column type, expecting Double because of mix
        assertEquals(Boolean.class, columnTypes.get(3)); // PartTime column type
    }
    
    public void testDisplay() {
        List<String> columns = Arrays.asList("Name", "Age", "Travail", "Salaire");
        List<List<Object>> data = Arrays.asList(
            Arrays.asList("Alice", 30, "caissiere", 1800),
            Arrays.asList("Bob", 29, "comptable", 2500),
            Arrays.asList("Charlie", 25, "developpeur", 3000)
        );

        DataFrame df = new DataFrame(columns, data);
        df.display();

        String expectedOutput = "Name\tAge\tTravail\tSalaire\t\nAlice\t30\tcaissiere\t1800\t\nBob\t29\tcomptable\t2500\t\nCharlie\t25\tdeveloppeur\t3000\t\n";
        assertEquals(expectedOutput.trim(), outContent.toString().trim());
    }
}
