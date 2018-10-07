package seng202.team6.utilities;

import junit.framework.TestCase;

public class ActivityValidationTest extends TestCase {

    public void testValidateDescription() {
        String validDescription = "this is 30 characters long aaa";
        String invalidDescription = "this is 31 characters long aaaa";
        assertTrue(ActivityValidation.validateDescription(validDescription));
        assertFalse(ActivityValidation.validateDescription(invalidDescription));
    }

    public void testValidateNotes() {
        String validNotes = "this is 110 characters long aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        String invalidNotes = "this is 111 characters long aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        assertTrue(ActivityValidation.validateNotes(validNotes));
        assertFalse(ActivityValidation.validateNotes(invalidNotes));
    }

    public void testValidateDistance() {
        double validDistance = 1000;
        double invalidDistance = 1000.0000000000001;
        assertTrue(ActivityValidation.validateDistance(validDistance));
        assertFalse(ActivityValidation.validateDistance(invalidDistance));
    }
}