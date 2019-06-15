package com.example.cars;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cars.model.Car;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    DatabaseReference databaseCars;
    List<Car> cars;



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


        databaseCars = FirebaseDatabase.getInstance().getReference("Cardetail");
        cars=new ArrayList<>();


        addArtist();


    }


    private void addArtist() {
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
            //it will create a unique id and we will use it as the Primary Key for our Artist
            String id = databaseCars.push().getKey();

            //creating an Artist Object
            Car carNew = new Car(id, model, brand, year, variant, kilo, state);

            //Saving the Artist
            databaseCars.child(id).setValue(carNew);

            //setting edittext to blank again
//            editTextName.setText("");

            //displaying a success toast
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