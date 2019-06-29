//package com.example.cars.adapter;
//
//import android.app.DownloadManager;
//import android.content.Context;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.example.cars.R;
//import com.example.cars.model.BiddingModel;
//import com.example.cars.model.Car;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.Query;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//
//public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarView> {
//
//
//    Context mContext;
//    private static ArrayList<Car> carsList= new ArrayList<>();
//    private static ArrayList<BiddingModel> biddingModelArrayList= new ArrayList<>();
//
//    Car car;
//    private DatabaseReference mDatabase;
//
//
//
//
//    public CarAdapter(Context context, ArrayList<Car> cars)
//    {
//        mContext=context;
//        carsList=cars;
//    }
//
//
//    @NonNull
//    @Override
//    public CarView onCreateViewHolder(@NonNull ViewGroup parent, int i) {
//        View view=LayoutInflater.from(mContext).inflate(R.layout.itemlist_car,parent,false);
//        CarView carView=new CarView(view);
//        return carView;
//    }
//
//
//    @Override
//    public void onBindViewHolder(@NonNull final CarView carView, int position) {
//
//        car=carsList.get(position);
//        carView.tvBrand.setText(car.getBrand());
//        carView.tvModel.setText(car.getModel());
//        carView.tvVariant.setText(car.getVariant());
//        carView.tvRegYear.setText(car.getYear());
//        carView.tvState.setText(car.getState());
//        carView.tvKilometers.setText(car.getKilom());
//
//        mDatabase = FirebaseDatabase.getInstance().getReference("bidding");
//
//
//        mDatabase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                biddingModelArrayList.clear();
//
//                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    //getting biddingList
//                    BiddingModel bidding=postSnapshot.getValue(BiddingModel.class);
//                    biddingModelArrayList.add(bidding);
//                    String carId=car.getId();
//                    Query queryForCarBidding=mDatabase.orderByChild("carId").equalTo(carId);
//                    queryForCarBidding.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            BiddingUserAdapter biddingUserAdapter= new BiddingUserAdapter(mContext,biddingModelArrayList);
//                            RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(mContext);
//                            carView.rvBiddingList.setLayoutManager(layoutManager);
//                            carView.rvBiddingList.setAdapter(biddingUserAdapter);
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return carsList.size();
//    }
//
//
//
//
//    class CarView extends RecyclerView.ViewHolder{
//
//    private TextView tvBrand,tvModel,tvRegYear,tvVariant,tvState,tvKilometers;
//    private RecyclerView rvBiddingList;
//
//        public CarView(@NonNull View itemView) {
//            super(itemView);
//
//            tvBrand=itemView.findViewById(R.id.tvBrand);
//            tvModel=itemView.findViewById(R.id.tvModel);
//            tvRegYear=itemView.findViewById(R.id.tvRegYear);
//            tvVariant=itemView.findViewById(R.id.tvVariant);
//            tvState=itemView.findViewById(R.id.tvState);
//            tvKilometers=itemView.findViewById(R.id.tvKilometers);
//            rvBiddingList=itemView.findViewById(R.id.rvBiddingList);
//
//        }
//
//
//
//    }
//}
//
//
//
//
//




package com.example.cars.adapter;

import android.app.DownloadManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cars.InnerCarActivity;
import com.example.cars.R;
import com.example.cars.model.BiddingModel;
import com.example.cars.model.Car;
import com.example.cars.model.user;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarView> {
    Context mContext;
    private static ArrayList<Car> carsList= new ArrayList<>();
    private  ArrayList<BiddingModel> biddingModelArrayList= new ArrayList<>();
    private static ArrayList<user> userList= new ArrayList<>();
    BiddingUserAdapter biddingUserAdapter;


    public CarAdapter(Context context, ArrayList<Car> cars)
    {
        this.mContext=context;
        carsList=cars;
//        biddingModelArrayList=biddingModels;
    }


    @NonNull
    @Override
    public CarView onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.itemlist_car,parent,false);
        CarView carView=new CarView(view);
        return carView;
    }

    @Override
    public void onBindViewHolder(@NonNull final CarView carView, int position) {

        final Car car =carsList.get(position);
        carView.tvBrand.setText(car.getBrand());
        carView.tvModel.setText(car.getModel());
        carView.tvVariant.setText(car.getVariant());
        carView.tvRegYear.setText(car.getYear());
        carView.tvState.setText(car.getState());
        carView.tvKilometers.setText(car.getKilom());


        carView.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InnerCarActivity.open(mContext,car.getId(),car.getBrand(),car.getModel(),car.getVariant(),car.getYear(),car.getState(),car.getKilom());

            }
        });


    }

    @Override
    public int getItemCount() {
        return carsList.size();
    }

    class CarView extends RecyclerView.ViewHolder{

        private TextView tvBrand,tvModel,tvRegYear,tvVariant,tvState,tvKilometers;
//        private RecyclerView rvBiddingList;

        public CarView(@NonNull View itemView) {
            super(itemView);

            tvBrand=itemView.findViewById(R.id.tvBrand);
            tvModel=itemView.findViewById(R.id.tvModel);
            tvRegYear=itemView.findViewById(R.id.tvRegYear);
            tvVariant=itemView.findViewById(R.id.tvVariant);
            tvState=itemView.findViewById(R.id.tvState);
            tvKilometers=itemView.findViewById(R.id.tvKilometers);
//            rvBiddingList=itemView.findViewById(R.id.rvBiddingList);

        }



    }
}

