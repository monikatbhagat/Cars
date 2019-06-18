package com.example.cars.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cars.R;
import com.example.cars.model.Car;

import java.util.ArrayList;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarView> {


    Context mContext;
    private static ArrayList<Car> carsList= new ArrayList<>();

    Car car;




    public CarAdapter(Context context, ArrayList<Car> cars)
    {
        mContext=context;
        carsList=cars;
    }


    @NonNull
    @Override
    public CarView onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.itemlist_car,parent,false);
        CarView carView=new CarView(view);
        return carView;
    }


    @Override
    public void onBindViewHolder(@NonNull CarView carView, int position) {

        car=carsList.get(position);
        carView.tvBrand.setText(car.getBrand());
        carView.tvModel.setText(car.getModel());
        carView.tvVariant.setText(car.getVariant());
        carView.tvRegYear.setText(car.getYear());
        carView.tvState.setText(car.getState());
        carView.tvKilometers.setText(car.getKilom());
    }

    @Override
    public int getItemCount() {
        return carsList.size();
    }

    class CarView extends RecyclerView.ViewHolder{

    private TextView tvBrand,tvModel,tvRegYear,tvVariant,tvState,tvKilometers;

        public CarView(@NonNull View itemView) {
            super(itemView);

            tvBrand=itemView.findViewById(R.id.tvBrand);
            tvModel=itemView.findViewById(R.id.tvModel);
            tvRegYear=itemView.findViewById(R.id.tvRegYear);
            tvVariant=itemView.findViewById(R.id.tvVariant);
            tvState=itemView.findViewById(R.id.tvState);
            tvKilometers=itemView.findViewById(R.id.tvKilometers);
        }



    }
}
