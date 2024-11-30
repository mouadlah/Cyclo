package com.example.main_activity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.shadows.ShadowToast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class ActivityListTest {

    private ActivityList activityList;

    @Mock
    private DatabaseReference mockDatabaseReference;
    @Mock
    private DataSnapshot mockDataSnapshot;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        activityList = new ActivityList();
        activityList.setTestDatabaseReference(mockDatabaseReference); // Make sure this is set correctly
    }

    @Test
    public void deleteUser_ActivityExists_DeletesSuccessfully() {
        String activityCode = "activity1";

        // Mock FirebaseDatabase response
        when(mockDataSnapshot.exists()).thenReturn(true);
        when(mockDatabaseReference.child("EventTypes").child(activityCode)).thenReturn(mockDatabaseReference); // Check this mocking
        doAnswer(invocation -> {
            ValueEventListener listener = invocation.getArgument(0);
            listener.onDataChange(mockDataSnapshot);
            return null;
        }).when(mockDatabaseReference).addListenerForSingleValueEvent(any(ValueEventListener.class));

        activityList.deleteUser(activityCode);

        // Verify the delete call was made
        verify(mockDatabaseReference).removeValue();

        // Verify Toast message
        assertEquals("Activity deleted successfully", ShadowToast.getTextOfLatestToast());
    }
}
