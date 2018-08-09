package seng202.team6;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class LucyTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public LucyTest(String testName) {
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
        Lucy lucy = new Lucy();
        int int1 = 20;
        int int2 = 10;
        int result = lucy.subtract(int1, int2);
        assertEquals(result, 10);
    }
}
