package com.example.cars;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.cars.adapter.BuilderAdapter;
import com.example.cars.adapter.CarAdapter;
import com.example.cars.model.Car;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BuilderWelcomeActivity extends AppCompatActivity {

    ArrayList<Car> carsList;
    DatabaseReference carDatabaseReference;
    Context mContext;
    RecyclerView rvCarsForBuilder;
    BuilderAdapter carAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_builder);
        mContext=BuilderWelcomeActivity.this;
        carDatabaseReference= FirebaseDatabase.getInstance().getReference("car");
        rvCarsForBuilder=findViewById(R.id.rvCarsForBuilder);
        carsList=new ArrayList<>();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        carDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                carsList.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting car
                    Car car = postSnapshot.getValue(Car.class);
                    carsList.add(car);
                }
                carAdapter=new BuilderAdapter(mContext,carsList);
                RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(mContext);
                rvCarsForBuilder.setLayoutManager(layoutManager);
                rvCarsForBuilder.setAdapter(carAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
