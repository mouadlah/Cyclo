package com.example.main_activity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowToast;

import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.widget.EditText;
import android.widget.RadioGroup;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class FeedbackUnitTest {

    private Feedback feedbackUnderTest;

    @Mock
    private RadioGroup mockRadioGroup;
    @Mock
    private EditText mockEditTextFeedback;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        feedbackUnderTest = new Feedback();
        feedbackUnderTest.radioGroup = mockRadioGroup;
        feedbackUnderTest.editTextFeedback = mockEditTextFeedback;
    }

    @Test
    public void testGetSelectedRating() {
        when(mockRadioGroup.getCheckedRadioButtonId()).thenReturn(R.id.radioBtn3); // Assuming radioBtn3 is a valid ID

        int rating = feedbackUnderTest.getSelectedRating();

        assertEquals(3, rating);
    }


}
