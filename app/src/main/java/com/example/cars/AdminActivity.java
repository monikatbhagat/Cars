package com.example.cars;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.example.cars.adapter.CarAdapter;
import com.example.cars.model.BiddingModel;
import com.example.cars.model.Car;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity  implements View.OnClickListener {
    private FirebaseAuth firebaseAuth;
    private TextView textViewUserEmail, tvList;
    private Button buttonLogout;
    Context mContext;
    ArrayList<Car> carsList;
//    ArrayList<BiddingModel> biddingModels;
    DatabaseReference carDatabaseReference, biddingDbReference;

    RecyclerView rvCars;
    CarAdapter carAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mContext=AdminActivity.this;

        firebaseAuth = FirebaseAuth.getInstance();
        carDatabaseReference=FirebaseDatabase.getInstance().getReference("car");
        biddingDbReference=FirebaseDatabase.getInstance().getReference("bidding");

        if(firebaseAuth.getCurrentUser() == null){
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, LoginActivity.class));
        }

        //getting current user
        FirebaseUser user = firebaseAuth.getCurrentUser();

        //initializing views
        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
        tvList = (TextView) findViewById(R.id.tvList);
        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        rvCars=findViewById(R.id.rvCars);

        //displaying logged in user name
        textViewUserEmail.setText("Welcome "+user.getEmail());

        //adding listener to button
        buttonLogout.setOnClickListener(this);

        carsList=new ArrayList<>();
//        biddingModels=new ArrayList<>();


        tvList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), activity_new_bidding.class);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, CarDetailActivity.class));
            }
        });
    }



    @Override
    protected void onStart()
    {
        super.onStart();


       /* biddingDbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // biddingModels.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting car
                    BiddingModel biddingModel = postSnapshot.getValue(BiddingModel.class);
                    biddingModels.add(biddingModel);
                }
               *//* carAdapter=new CarAdapter(mContext,carsList,biddingModels);
                RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(mContext);
                rvCars.setLayoutManager(layoutManager);
                rvCars.setAdapter(carAdapter);*//*
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/



       /* FirebaseDatabase.getInstance().getReference("car").orderByChild("brand").equalTo("newCar").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                carsList.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting car
                    Car car = postSnapshot.getValue(Car.class);
                    carsList.add(car);
                }


                carAdapter=new CarAdapter(mContext,carsList,biddingModels);
                RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(mContext);
                rvCars.setLayoutManager(layoutManager);
                rvCars.setAdapter(carAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


*/

 carDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                carsList.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting car
                    Car car = postSnapshot.getValue(Car.class);
                    carsList.add(car);
                }
                carAdapter=new CarAdapter(mContext,carsList);
//                carAdapter=new CarAdapter(mContext,carsList,biddingModels);
                RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(mContext);
                rvCars.setLayoutManager(layoutManager);
                rvCars.setAdapter(carAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

        if(view == buttonLogout){

            firebaseAuth.signOut();
            //closing activity
            finish();
            //starting login activity
            startActivity(new Intent(this, LoginActivity.class));
        }

    }
}
