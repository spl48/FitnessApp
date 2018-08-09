package seng202.team6;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AngelicaTest
        extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AngelicaTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(AngelicaTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        Angelica sub = new Angelica();
        int int1 = 5;
        int int2 = 6;
        int result = sub.subtract(int1, int2);
        assertEquals(result,-1);
    }
}
