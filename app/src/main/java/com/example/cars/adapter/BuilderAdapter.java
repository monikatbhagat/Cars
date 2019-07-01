package com.example.cars.adapter;

        import android.content.Context;
        import android.text.Editable;
        import android.text.TextUtils;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.LinearLayout;
        import android.widget.TextView;
        import android.widget.Toast;


        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;

        import com.example.cars.R;
        import com.example.cars.model.BiddingModel;
        import com.example.cars.model.Car;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.Query;
        import com.google.firebase.database.ValueEventListener;

        import java.util.ArrayList;

public class BuilderAdapter extends RecyclerView.Adapter<BuilderAdapter.BuilderCarView> {


    Context mContext;
    private static ArrayList<Car> carsList= new ArrayList<>();
    long biddingId=0;

//    Car car;
    BiddingModel bidding;
    private FirebaseAuth firebaseAuth;
    private int biddingAmount;
    private DatabaseReference mDatabase;
    String carID,userId,carId_userId,mailId,carBrand;


    public BuilderAdapter(Context context, ArrayList<Car> cars)
    {
        mContext=context;
        carsList=cars;
    }


    @NonNull
    @Override
    public BuilderCarView onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.carlist_forbuilder,parent,false);
        BuilderCarView carView=new BuilderCarView(view);
        return carView;
    }


    @Override
    public void onBindViewHolder(@NonNull final BuilderCarView carView, int position) {

        final Car car=carsList.get(position);
        carView.tvBrand.setText(car.getBrand());
        carView.tvModel.setText(car.getModel());
        carView.tvVariant.setText(car.getVariant());
        carView.tvRegYear.setText(car.getYear());
        carView.tvState.setText(car.getState());
        carView.tvKilometers.setText(car.getKilom());

        carView.btnSubmitCost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase = FirebaseDatabase.getInstance().getReference("bidding");
//                mDatabase.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        if(dataSnapshot.exists())
//                        {
//                            biddingId=(dataSnapshot.getChildrenCount());
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });


                firebaseAuth = FirebaseAuth.getInstance();

                if (carView.etBuilCost.getText().toString().equals("")) {
                    Toast.makeText(mContext, "Please enter bidding amount first..", Toast.LENGTH_LONG).show();
                } else{

                   carID=car.getId();
                   carBrand=car.getBrand();
                   userId=firebaseAuth.getCurrentUser().getUid();
                   mailId=firebaseAuth.getCurrentUser().getEmail();
                   carId_userId=carID+"_"+userId;
                   Log.d("carIDDD","cccc"+mailId);

                    Query caruserIdQuery=mDatabase.orderByChild("carId_userId").equalTo(carId_userId) ;

                    caruserIdQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.getChildrenCount()>0)
                            {
                                Toast.makeText(mContext, "Already bid this car..", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                biddingAmount=Integer.parseInt(carView.etBuilCost.getText().toString());
//                              String biddingId = mDatabase.push().getKey();
                                bidding=new BiddingModel(carID,carBrand,userId,mailId,biddingAmount,carId_userId);
                                mDatabase.child(carId_userId).setValue(bidding);
                                carView.ll_BiddingBox.setVisibility(View.GONE);
                                carView.ll_Locked.setVisibility(View.VISIBLE);
                                Toast.makeText(mContext, "Your bidding submitted..", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });



                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return carsList.size();
    }

    class BuilderCarView extends RecyclerView.ViewHolder{

        private TextView tvBrand,tvModel,tvRegYear,tvVariant,tvState,tvKilometers;
        private EditText etBuilCost;
        private Button btnSubmitCost;
        private LinearLayout ll_BiddingBox,ll_Locked;

        public BuilderCarView(@NonNull View itemView) {
            super(itemView);
            tvBrand=itemView.findViewById(R.id.tvBrand);
            tvModel=itemView.findViewById(R.id.tvModel);
            tvRegYear=itemView.findViewById(R.id.tvRegYear);
            tvVariant=itemView.findViewById(R.id.tvVariant);
            tvState=itemView.findViewById(R.id.tvState);
            tvKilometers=itemView.findViewById(R.id.tvKilometers);
            etBuilCost=itemView.findViewById(R.id.etBuilCost);
            btnSubmitCost=itemView.findViewById(R.id.btnSubmitCost);
            ll_BiddingBox=itemView.findViewById(R.id.ll_BiddingBox);
            ll_Locked=itemView.findViewById(R.id.ll_Locked);
        }
    }
}
