package com.example.main_activity;

import android.content.Context;
import com.google.firebase.database.DatabaseReference;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import static org.mockito.Mockito.when;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

@RunWith(RobolectricTestRunner.class)
public class ActivityLoginPageTest {

    @Mock
    private Context mockContext;

    @Mock
    private DatabaseReference mockDatabaseReference;

    private activity_login_page activityUnderTest;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        activityUnderTest = new activity_login_page();


        activityUnderTest.mDatabase = mockDatabaseReference;


    }

    @Test
    public void validate_SuccessfulLogin() {
        boolean result = activityUnderTest.validate("admin", "admin");
        assertTrue(result);
    }

    @Test
    public void validate_UnsuccessfulLogin() {
        boolean result = activityUnderTest.validate("user", "password");
        assertFalse(result);
    }

    @Test
    public void validateEmail_ValidEmail() {
        boolean result = activityUnderTest.validateEmail("admin@example.com");
        assertTrue(result);
    }

    @Test
    public void validateEmail_InvalidEmail() {
        boolean result = activityUnderTest.validateEmail("admin@");
        assertFalse(result);
    }

}
