package seng202.team6;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class RyanTest
        extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public RyanTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(RyanTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        Ryan ryan = new Ryan();
        int int1 = 5;
        int int2 = 6;
        int result = ryan.math_add(int1, int2);
        assertEquals(result, 11);
    }
}
