package com.example.main_activity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.shadows.ShadowToast;

import android.widget.EditText;
import android.widget.Spinner;
import android.widget.RadioGroup;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;

import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class EventFormTest {

    private EventForm eventFormUnderTest;

    @Mock
    private DatabaseReference mockDatabaseReference;
    @Mock
    private EditText mockEditTextAge;
    @Mock
    private EditText mockEditTextCity;
    @Mock
    private EditText mockEditTextPhoneNumber;
    @Mock
    private Spinner mockSpinnerTShirtSize;
    @Mock
    private RadioGroup mockRadioGroupParticipation;
    @Mock
    private Button mockButtonSubmit;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        eventFormUnderTest = new EventForm();

        // Assign mock objects to the activity
        eventFormUnderTest.editTextAge = mockEditTextAge;
        eventFormUnderTest.editTextCity = mockEditTextCity;
        eventFormUnderTest.editTextPhoneNumber = mockEditTextPhoneNumber;
        eventFormUnderTest.spinnerTShirtSize = mockSpinnerTShirtSize;
        eventFormUnderTest.radioGroupParticipation = mockRadioGroupParticipation;
        eventFormUnderTest.buttonSubmit = mockButtonSubmit;

        // Mock behavior for EditTexts
        when(mockEditTextAge.getText().toString()).thenReturn("25");
        when(mockEditTextCity.getText().toString()).thenReturn("New York");
        when(mockEditTextPhoneNumber.getText().toString()).thenReturn("1234567890");
    }

    @Test
    public void submitForm_ValidInput_SubmitsSuccessfully() {
        // Simulate button click
        eventFormUnderTest.buttonSubmit.performClick();

        // Assertions for database interactions
        // (Note: Add your database interaction assertions here)

        // Check for success Toast message
        assertEquals("Information submitted successfully!", ShadowToast.getTextOfLatestToast());
    }

    @Test
    public void submitForm_InvalidInput_ShowsError() {
        // Set invalid input
        when(mockEditTextAge.getText().toString()).thenReturn("");
        when(mockEditTextPhoneNumber.getText().toString()).thenReturn("");

        // Simulate button click
        eventFormUnderTest.buttonSubmit.performClick();

        // Assertions for error handling
        // (Note: Add your error handling assertions here)
    }
}
