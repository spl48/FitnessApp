package seng202.team6;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class SeanTest
        extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public SeanTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(SeanTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        Sean sean = new Sean();
        int int1 = 10;
        int int2 = 7;
        int result = sean.subtract(int1, int2);
        assertEquals(result, 3);
    }
}
