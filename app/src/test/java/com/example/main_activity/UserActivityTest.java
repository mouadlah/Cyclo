package com.example.main_activity;

import android.content.Intent;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import org.robolectric.android.controller.ActivityController;
import org.robolectric.shadows.ShadowActivity;

import static org.junit.Assert.*;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class UserActivityTest {

    private UserActivity userActivity;
    private ActivityController<UserActivity> controller;

    @Before
    public void setUp() {
        Intent intent = new Intent();
        intent.putExtra("userName", "testUser");
        intent.putExtra("role", "userRole");
        controller = Robolectric.buildActivity(UserActivity.class, intent).create().start();
        userActivity = controller.get();
    }

    @Test
    public void onCreate_shouldSetUserNameAndRole() {
        TextView userNameView = userActivity.findViewById(R.id.userNameId);
        TextView roleView = userActivity.findViewById(R.id.roleId);

        assertEquals("UserName: testUser", userNameView.getText().toString());
        assertEquals("Role: userRole", roleView.getText().toString());
    }

    @Test
    public void onClickJoinEvent_shouldStartJoinEventActivity() {
        userActivity.findViewById(R.id.join).performClick();
        ShadowActivity shadowActivity = shadowOf(userActivity);
        Intent expectedIntent = new Intent(userActivity, JoinEvent.class);
        expectedIntent.putExtra("userName", "testUser");

        assertTrue(shadowActivity.getNextStartedActivity().filterEquals(expectedIntent));
    }
}
