package com.example.main_activity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.robolectric.RobolectricTestRunner;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class EditEventTest {

    @Mock
    private DatabaseReference mockDatabaseReference;

    private EditEvent editEventUnderTest;
    private ArrayList<event_data> mockList;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        editEventUnderTest = new EditEvent();
        editEventUnderTest.database = mockDatabaseReference;
        mockList = new ArrayList<>();

        // Setting up a mock list of events
        mockList.add(new event_data(/* parameters if needed */));
        editEventUnderTest.list = mockList;
    }

    @Test
    public void deleteEvent_ValidPosition_RemovesEvent() {
        int positionToRemove = 0;

        // Assume Firebase deletion is successful
        doAnswer(invocation -> {
            mockList.remove(positionToRemove);
            return null;
        }).when(mockDatabaseReference).removeValue();

        editEventUnderTest.deleteEvent(positionToRemove);

        assertTrue("Event should be removed", mockList.isEmpty());
    }

    @Test
    public void deleteEvent_InvalidPosition_NoChange() {
        int invalidPosition = -1;

        editEventUnderTest.deleteEvent(invalidPosition);

        assertFalse("List should not be modified", mockList.isEmpty());
    }

    // Additional tests as needed...
}
