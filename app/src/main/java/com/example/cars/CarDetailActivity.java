package com.example.cars;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class CarDetailActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {

    Spinner spRegYear, spVariant,spState,spKilometers;

    String[] year = { "2009", "2008", "2007", "2006", "2005"};
    String[] variant = { "Petrol", "Diesel"};
    String[] state = { "Maharashtra", "Rajasthan"};
    String[] kilom = { "10000-20000", "20000-30000"};
    Context context;
    EditText etBrand, etModel;
    List<Car> cars;
    private DatabaseReference mDatabase;
    Button btnSubmitDetail;
    long carId=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_detail);
        context=CarDetailActivity.this;

        spKilometers=findViewById(R.id.spKilometers);
        spVariant=findViewById(R.id.spVariant);
        spRegYear=findViewById(R.id.spRegYear);
        spState=findViewById(R.id.spState);
        etBrand=findViewById(R.id.etBrand);
        etModel=findViewById(R.id.etModel);
        btnSubmitDetail=findViewById(R.id.btnSubmitDetail);

        spKilometers.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,kilom);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spKilometers.setAdapter(aa);

        spVariant.setOnItemSelectedListener(this);
        ArrayAdapter aaVariant = new ArrayAdapter(this,android.R.layout.simple_spinner_item,variant);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spVariant.setAdapter(aaVariant);

        spRegYear.setOnItemSelectedListener(this);
        ArrayAdapter aaRegYear = new ArrayAdapter(this,android.R.layout.simple_spinner_item,year);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spRegYear.setAdapter(aaRegYear);

        spState.setOnItemSelectedListener(this);
        ArrayAdapter aaState = new ArrayAdapter(this,android.R.layout.simple_spinner_item,state);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spState.setAdapter(aaState);


//        databaseCars = FirebaseDatabase.getInstance().getReference();

        //declare the database reference object. This is what we use to access the database.
        //NOTE: Unless you are signed in, this will not be useable.
//        mAuth = FirebaseAuth.getInstance();
//        mFirebaseDatabase = FirebaseDatabase.getInstance();
//        myRef = mFirebaseDatabase.getReference();




        mDatabase = FirebaseDatabase.getInstance().getReference("car");

        cars=new ArrayList<>();


        btnSubmitDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCar();
            }
        });


    }


    private void addCar() {
        //getting the values to save
        String model = etModel.getText().toString().trim();
        String brand = etBrand.getText().toString().trim();
        String year = spRegYear.getSelectedItem().toString();
        String variant = spVariant.getSelectedItem().toString();
        String kilo = spKilometers.getSelectedItem().toString();
        String state = spState.getSelectedItem().toString();

        //checking if the value is provided
        if (!TextUtils.isEmpty(model)) {

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our car
            String id = mDatabase.push().getKey();

//            mDatabase.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    if(dataSnapshot.exists())
//                    {
//                        carId=(dataSnapshot.getChildrenCount());
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });

//            String ccc=String.valueOf(carId+1);
            Car carNew = new Car(id,brand,model, year, variant,state,kilo);
            mDatabase.child(id).setValue(carNew);
            finish();
            Toast.makeText(this, "New car added", Toast.LENGTH_LONG).show();
        } else {
            //if the value is not given displaying a toast
            Toast.makeText(this, "Please enter model", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
