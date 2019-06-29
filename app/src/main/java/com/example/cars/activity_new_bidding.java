package com.example.cars;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.cars.adapter.BiddingUserAdapter;
import com.example.cars.adapter.CarAdapter;
import com.example.cars.model.BiddingModel;
import com.example.cars.model.Car;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class activity_new_bidding extends AppCompatActivity {
  RecyclerView rvNew;
    ArrayList<BiddingModel> biddingModels;
    ArrayList<Car> carArrayList;
    BiddingUserAdapter biddingUserAdapter;
//    RecyclerView.LayoutManager layoutManager;
    Context mContext;
    DatabaseReference carDatabaseReference, biddingDbReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_bidding);
        mContext=activity_new_bidding.this;
        rvNew=findViewById(R.id.rvNew);
        carDatabaseReference=FirebaseDatabase.getInstance().getReference("car");
        biddingDbReference=FirebaseDatabase.getInstance().getReference("bidding");
        biddingModels=new ArrayList<>();
        carArrayList=new ArrayList<>();

        carDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

//                carsList.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting car
                    Car car = postSnapshot.getValue(Car.class);
                    carArrayList.add(car);
                }

                for(Car c:carArrayList)
                {
                    biddingDbReference.orderByChild("carId").equalTo(c.getId()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                             biddingModels.clear();
                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                BiddingModel bidding = postSnapshot.getValue(BiddingModel.class);
                                biddingModels.add(bidding);
                            }
                            biddingUserAdapter= new BiddingUserAdapter(mContext,biddingModels);
                            RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(mContext);
                            rvNew.setLayoutManager(layoutManager);
                            rvNew.setAdapter(biddingUserAdapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



/*
                for(Car c:carArrayList)
                {
                    biddingDbReference.orderByChild("carId").equalTo(c.getId()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                               biddingModels.clear();

                                        for(DataSnapshot postSnapshot : dataSnapshot.getChildren())
                                        {
                                            BiddingModel bidding = postSnapshot.getValue(BiddingModel.class);
                                            biddingModels.add(bidding);
                                        }
                                        Collections.reverse(biddingModels);
                                        biddingUserAdapter= new BiddingUserAdapter(mContext,biddingModels);
                                        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(mContext);
                                        rvNew.setLayoutManager(layoutManager);
                                        rvNew.setAdapter(biddingUserAdapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }*/

/*

                for(Car c:carArrayList)
                {
                    biddingDbReference.orderByChild("carId").equalTo(c.getId()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    biddingModels.clear();

                                biddingDbReference.orderByChild("biddingAmount").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        biddingModels.clear();
                                        for(DataSnapshot postSnapshot : dataSnapshot.getChildren())
                                        {
                                            BiddingModel bidding = postSnapshot.getValue(BiddingModel.class);
                                            Log.d("hhhh","hhh"+bidding.getCarId());
                                            biddingModels.add(bidding);
                                        }

                                        Collections.reverse(biddingModels);
                                        biddingUserAdapter= new BiddingUserAdapter(mContext,biddingModels);
                                        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(mContext);
                                        rvNew.setLayoutManager(layoutManager);
                                        rvNew.setAdapter(biddingUserAdapter);

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
*/



    }

    @Override
    protected void onStart() {
        super.onStart();


    }
}
