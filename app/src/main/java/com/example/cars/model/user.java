package com.example.cars.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class user {
    private String userEmailId;
    private int biddingAmount;



    public user()
    {

    }


    public user(String userId, int biddingAmount) {

        this.userEmailId = userId;
        this.biddingAmount = biddingAmount;
    }

    public String getUserEmailId() {
        return userEmailId;
    }

    public int getBiddingAmount() {
        return biddingAmount;
    }
}
