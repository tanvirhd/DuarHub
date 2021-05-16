package com.duarbd.duarhcentralhub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.duarbd.duarhcentralhub.R;
import com.duarbd.duarhcentralhub.interfaces.AdapterRiderRideHistoryCallback;
import com.duarbd.duarhcentralhub.model.ModelDeliveryRequest;

import java.util.List;

public class AdapterRiderRideHistory extends RecyclerView.Adapter<AdapterRiderRideHistory.ViewHolderAdapterRiderRideHistory>{

    List<ModelDeliveryRequest> rideList;
    Context context;
    AdapterRiderRideHistoryCallback callback;

    public AdapterRiderRideHistory(List<ModelDeliveryRequest> rideList, Context context, AdapterRiderRideHistoryCallback callback) {
        this.rideList = rideList;
        this.context = context;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolderAdapterRiderRideHistory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_rider_ride_history,parent,false);
        ViewHolderAdapterRiderRideHistory viewHolderAdapterRiderRideHistory=new ViewHolderAdapterRiderRideHistory(view);
        return viewHolderAdapterRiderRideHistory;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAdapterRiderRideHistory holder, int position) {
        ModelDeliveryRequest ride=rideList.get(position);
        holder.tvrrh_deliveryid.setText("Delivery id: "+ride.getDeliveryRequestId());
        holder.tvrrh_price.setText("Bill: "+ride.getProductPrice()+" Tk");
        holder.tvrrh_deliverycharge.setText("Delivery Charge: "+ride.getDeliveryCharge()+" Tk");
        holder.tvrrh_pickupCharge.setText("Pickup Charge: "+ride.getPickupCharge()+" Tk");

        if(ride.getRiderClearance().equals("due")){
            holder.tvrrh_PaymentStatus.setText("due");
            holder.tvrrh_PaymentStatus.setBackground(context.getResources().getDrawable(R.drawable.bg_cr4_fill_red));
            holder.tvrrh_giveClearance.setVisibility(View.VISIBLE);
        }else {
            holder.tvrrh_PaymentStatus.setText("received");
            holder.tvrrh_PaymentStatus.setBackground(context.getResources().getDrawable(R.drawable.bg_cr4_fill_green));
            holder.tvrrh_giveClearance.setVisibility(View.GONE);
        }

        holder.tvrrh_vieworderdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onMoreClicked(ride);
            }
        });

        holder.tvrrh_giveClearance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onGiveClearanceClicked(ride);
            }
        });
    }

    @Override
    public int getItemCount() {
        return rideList.size();
    }

    class ViewHolderAdapterRiderRideHistory extends RecyclerView.ViewHolder{
        TextView tvrrh_deliveryid,tvrrh_PaymentStatus,tvrrh_price,tvrrh_deliverycharge,tvrrh_pickupCharge,
                tvrrh_vieworderdetails, tvrrh_giveClearance;
        public ViewHolderAdapterRiderRideHistory(@NonNull View itemView) {
            super(itemView);
            tvrrh_deliveryid=itemView.findViewById(R.id.tvrrh_deliveryid);
            tvrrh_PaymentStatus=itemView.findViewById(R.id.tvrrh_PaymentStatus);
            tvrrh_price=itemView.findViewById(R.id.tvrrh_price);
            tvrrh_deliverycharge=itemView.findViewById(R.id.tvrrh_deliverycharge);
            tvrrh_pickupCharge=itemView.findViewById(R.id.tvrrh_pickupCharge);
            tvrrh_vieworderdetails=itemView.findViewById(R.id.tvrrh_vieworderdetails);
            tvrrh_giveClearance=itemView.findViewById(R.id.tvrrh_giveClearance);
        }
    }
}
