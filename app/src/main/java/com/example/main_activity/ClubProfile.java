package com.example.main_activity;

public class ClubProfile {
    private String contactName;
    private String clubName;

    private String number;

    private String instagramLink;
    private String foundationDate;

    public String getContactName() {
        return contactName;
    }

    public String getNumber() {
        return number;
    }

    public String getInstagramLink() {
        return instagramLink;
    }
    public String getClubName() {
        return clubName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setInstagramLink(String instagramLink) {
        this.instagramLink = instagramLink;
    }

    public void setFoundationDate(String foundationDate) {
        this.foundationDate = foundationDate;
    }

    public String getFoundationDate() {
        return foundationDate;
    }

    public ClubProfile(String contactName, String number, String instagramLink, String foundationDate, String clubName) {
        this.contactName = contactName;
        this.number = number;
        this.instagramLink = instagramLink;
        this.foundationDate = foundationDate;
        this.clubName= clubName;
    }
}
