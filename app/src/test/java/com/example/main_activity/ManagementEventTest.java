package com.example.main_activity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import android.os.Build;
import android.text.SpannableStringBuilder;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1})
public class ManagementEventTest {

    private ManagementEvent managementEvent;
    private RadioGroup radioGroup;
    private EditText codeNumber, ageEditText, levelEditText, paceEditText;

    @Before
    public void setUp() {
        // Initialize the activity
        managementEvent = Robolectric.buildActivity(ManagementEvent.class)
                .create()
                .resume()
                .get();

        // Get the UI components from the activity
        radioGroup = managementEvent.findViewById(R.id.radioGroup);
        codeNumber = managementEvent.findViewById(R.id.editTextText4);
        ageEditText = managementEvent.findViewById(R.id.editTextText5);
        levelEditText = managementEvent.findViewById(R.id.editTextText6);
        paceEditText = managementEvent.findViewById(R.id.editTextText3);

    }

    @Test
    public void createEventTypeFromInput_ValidInput_ReturnsEventType() {
        // Set the inputs
        radioGroup.check(R.id.radioGroup);
        codeNumber.setText("123");
        ageEditText.setText("20");
        levelEditText.setText("Beginner");
        paceEditText.setText("5");

        // Call the method
        EventType result = managementEvent.createEventTypeFromInput();

        // Assertions
        assertNotNull(result);
        assertEquals("ExpectedTypeName", result.typeName); // Replace with expected type name
        assertEquals("123", result.code);
        assertEquals("20", result.age);
        assertEquals("Beginner", result.level);
        assertEquals("5", result.pace);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createEventTypeFromInput_InvalidInput_ThrowsException() {
        // Set incomplete or incorrect inputs
        // ...

        // Call the method which should throw an exception
        managementEvent.createEventTypeFromInput();
    }

    // Additional test cases...
}
