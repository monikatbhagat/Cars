package com.example.cars.model;


import androidx.annotation.NonNull;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class BiddingModel  implements Comparable<BiddingModel> {
    private String carId;
    private String userId;
    private int biddingAmount;
    private String carId_userId;
    private String mailId;
    private String carBrand;


    public BiddingModel()
    {

    }

    public BiddingModel(String carId, String carBrand,String userId,String mailId, int biddingAmount,String carId_userId)
    {
        super();
        this.carId=carId;
        this.carBrand=carBrand;
        this.userId=userId;
        this.mailId=mailId;
        this.biddingAmount=biddingAmount;
        this.carId_userId=carId_userId;
    }


//        public BiddingModel(String biddingId,String carId, String userId, int biddingAmount)
//            {
//                this.biddingId=biddingId;
//                this.carId=carId;
//                this.userId=userId;
//                this.biddingAmount=biddingAmount;
//            }

    public String getCarId_userId() {
        return carId_userId;
    }

    public String getCarId() {
        return carId;
    }

    public String getUserId() {
        return userId;
    }

    public int getBiddingAmount() {
        return biddingAmount;
    }

    public String getMailId() {
        return mailId;
    }

    public String getCarBrand() {
        return carBrand;
    }

    @Override
    public int compareTo(@NonNull BiddingModel o) {
        int amount = ((BiddingModel) o).getBiddingAmount();
        return amount - this.biddingAmount;

//        return this.biddingAmount - amount;
    }
}


