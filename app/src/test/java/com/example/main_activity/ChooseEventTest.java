package com.example.main_activity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.robolectric.RobolectricTestRunner;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import com.google.firebase.database.DatabaseReference;

import static org.mockito.Mockito.verify;




    @RunWith(RobolectricTestRunner.class)
    @Config(manifest = Config.NONE)
    public class ChooseEventTest {

        @Mock
        private DatabaseReference mockDatabase;

        private choose_event chooseEventUnderTest;

        @Before
        public void setUp() {
            MockitoAnnotations.initMocks(this);
            chooseEventUnderTest = Mockito.spy(new choose_event());
            chooseEventUnderTest.database = mockDatabase; // Set the mock database

            // Mock any other dependencies required by choose_event
        }

        @Test
        public void testLoadDataFromFirebase() {
            // Call the method that you want to test
            chooseEventUnderTest.loadDataFromFirebase();

            // Verify that database interaction occurs
            verify(mockDatabase).addValueEventListener(Mockito.any());
        }

        // Additional tests...
}



