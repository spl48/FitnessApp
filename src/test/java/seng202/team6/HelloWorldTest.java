package seng202.team6;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class HelloWorldTest
        extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public HelloWorldTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(HelloWorldTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        HelloWorld hello = new HelloWorld();
        int int1 = 5;
        int int2 = 6;
        int result = hello.subtract(int1, int2);
        assertEquals(result, -1);
    }
}
