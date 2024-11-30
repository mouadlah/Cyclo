package com.example.main_activity;

public class Event {
    private String name;
    private String typeName;
    private String clubName;
    // getters and setters...

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getClubName() {
        return clubName;
    }

    // Setter for club name
    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    // Other getters and setters...
}
