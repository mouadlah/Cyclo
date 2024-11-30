package com.example.main_activity;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ClubProfileTest {

    private ClubProfile clubProfileUnderTest;

    @Before
    public void setUp() {
        clubProfileUnderTest = new ClubProfile("John Doe", "123456789", "https://instagram.com/club", "01/01/2000", "Cycling Club");
    }

    @Test
    public void getters_ReturnCorrectValues() {
        assertEquals("John Doe", clubProfileUnderTest.getContactName());
        assertEquals("123456789", clubProfileUnderTest.getNumber());
        assertEquals("https://instagram.com/club", clubProfileUnderTest.getInstagramLink());
        assertEquals("01/01/2000", clubProfileUnderTest.getFoundationDate());
        assertEquals("Cycling Club", clubProfileUnderTest.getClubName());
    }

    @Test
    public void setters_UpdateValuesCorrectly() {
        clubProfileUnderTest.setContactName("Jane Doe");
        clubProfileUnderTest.setNumber("987654321");
        clubProfileUnderTest.setInstagramLink("https://instagram.com/newclub");
        clubProfileUnderTest.setFoundationDate("02/02/2002");
        clubProfileUnderTest.setClubName("Running Club");

        assertEquals("Jane Doe", clubProfileUnderTest.getContactName());
        assertEquals("987654321", clubProfileUnderTest.getNumber());
        assertEquals("https://instagram.com/newclub", clubProfileUnderTest.getInstagramLink());
        assertEquals("02/02/2002", clubProfileUnderTest.getFoundationDate());
        assertEquals("Running Club", clubProfileUnderTest.getClubName());
    }
}
