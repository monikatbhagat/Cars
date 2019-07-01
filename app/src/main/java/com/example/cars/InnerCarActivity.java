package com.example.cars;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cars.adapter.BiddingUserAdapter;
import com.example.cars.model.BiddingModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class InnerCarActivity extends AppCompatActivity {

   Context mContext;
    private static String mCarId, mCarBrand, mCarModel, mCarVariant, mCarYear, mCarState, mCarKilometer;

    TextView tvCarBrand,tvCarModel;
    ArrayList<BiddingModel> biddingModels;
    BiddingUserAdapter biddingUserAdapter;
    RecyclerView recyclerView;

    public  static void open(Context context,String carID, String carBrand, String carModel, String carVariant, String carYear, String carState, String carKilometer)
    {
        mCarBrand=carBrand;
        mCarId=carID;
        mCarModel=carModel;
        mCarVariant=carVariant;
        mCarYear=carYear;
        mCarState=carState;
        mCarKilometer=carKilometer;
        context.startActivity(new Intent(context, InnerCarActivity.class));
        Log.d("detail "," carrrr  " +mCarModel+"  "+mCarId+" "+mCarBrand);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inner_car);
        bindViews();
        FirebaseDatabase.getInstance().getReference("bidding").orderByChild("carId").equalTo(mCarId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    BiddingModel bidding = postSnapshot.getValue(BiddingModel.class);
                    biddingModels.add(bidding);
                }

                Collections.sort(biddingModels);

                biddingUserAdapter= new BiddingUserAdapter(mContext,biddingModels);
                RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(mContext);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(biddingUserAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void bindViews()
    {
        mContext=InnerCarActivity.this;
        tvCarModel=findViewById(R.id.tvCarModel);
        tvCarBrand=findViewById(R.id.tvCarBrand);
        recyclerView=findViewById(R.id.recyclerView);
        tvCarBrand.setText(mCarBrand);
        tvCarModel.setText("Model No.- "+mCarModel +"\n"+"Car Variant- "+mCarVariant+"\n"
                           +"Registration Year- "+mCarYear+"\n"+"Resistration State- "+mCarState+"\n"+"Kilometers driven- "+mCarKilometer);
        biddingModels=new ArrayList<>();
    }


}
