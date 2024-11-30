package com.example.main_activity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class JoinEventTest {
    private JoinEvent joinEventUnderTest;

    @Mock
    private EventAdapter mockEventAdapter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        joinEventUnderTest = new JoinEvent();
        joinEventUnderTest.eventAdapter = mockEventAdapter;

        // Prepare some mock data
        ArrayList<Event> mockEvents = new ArrayList<>();
        Event event1 = new Event(); // Assuming Event class has setters or public fields
        event1.setName("Event1");
        event1.setTypeName("Type1");
        event1.setClubName("Club1");
        mockEvents.add(event1);

        Event event2 = new Event();
        event2.setName("Event2");
        event2.setTypeName("Type2");
        event2.setClubName("Club2");
        mockEvents.add(event2);



        joinEventUnderTest.eventList = mockEvents; // Directly set the eventList
    }

    @Test
    public void testFilter_withTextMatching() {
        joinEventUnderTest.filter("Event1");
        verify(mockEventAdapter).filterList(any());
    }

    @Test
    public void testFilter_withNoText() {
        joinEventUnderTest.filter("");
        verify(mockEventAdapter).filterList(any());
    }

    // Additional tests...
}
