package com.example.cars.adapter;

import android.content.Context;
//import android.support.annotation.NonNull;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
//import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cars.R;
import com.example.cars.model.BiddingModel;

import java.util.ArrayList;

public class BiddingUserAdapter extends RecyclerView.Adapter<BiddingUserAdapter.BUViewHolder> {

    Context mContext;
    private ArrayList<BiddingModel> carsBiddingList;

    public BiddingUserAdapter(Context context, ArrayList<BiddingModel> biddingList)
    {
        mContext=context;
        carsBiddingList=biddingList;
    }

    @NonNull
    @Override
    public BUViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.bidding_user_list,parent,false);
        BUViewHolder buViewHolder=new BUViewHolder(view);
        return buViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final BUViewHolder viewHolder, int position) {

        final BiddingModel biddingModel=carsBiddingList.get(position);
        if(position==0)
        {
            viewHolder.tvUserName.setTextColor(ContextCompat.getColor(mContext,R.color.colorAccent));
            viewHolder.tvCarName.setTextColor(ContextCompat.getColor(mContext,R.color.colorAccent));
            viewHolder.tvBidding.setTextColor(ContextCompat.getColor(mContext,R.color.colorAccent));
        }

        viewHolder.tvCarName.setText(biddingModel.getCarBrand());
        viewHolder.tvUserName.setText(biddingModel.getMailId());
        viewHolder.tvBidding.setText(String.valueOf(biddingModel.getBiddingAmount()));
    }

    @Override
    public int getItemCount() {
        return carsBiddingList.size();
    }

    class BUViewHolder extends RecyclerView.ViewHolder{

        TextView tvUserName,tvBidding,tvCarName;

        public BUViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBidding=itemView.findViewById(R.id.tvBidding);
            tvUserName=itemView.findViewById(R.id.tvUserName);
            tvCarName=itemView.findViewById(R.id.tvCarName);
        }
    }
}
