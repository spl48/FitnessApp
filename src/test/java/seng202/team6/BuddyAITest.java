package seng202.team6;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class BuddyAITest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public BuddyAITest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( BuddyAITest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        String userName1 = "TestUser";
        BuddyAI bud1 = new BuddyAI();
        String userGreeting = bud1.getGreeting(userName1);
        assertEquals(userGreeting, "Hello TestUser");
        int int1 = 5;
        int int2 = 6;
        int result = bud1.multiply(int1, int2);
        assertEquals(result, 30);
    }
}
