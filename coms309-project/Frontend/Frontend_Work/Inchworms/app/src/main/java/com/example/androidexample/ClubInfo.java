package com.example.androidexample;

public class ClubInfo {
    private String clubName;
    private int clubId;

    public ClubInfo(int clubId, String clubName) {
        this.clubName = clubName;
        this.clubId = clubId;
    }

    public String getClubName() {
        return clubName;
    }

    public int getClubId() {
        return clubId;
    }
}
