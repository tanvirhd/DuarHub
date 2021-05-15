package com.duarbd.duarhcentralhub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.duarbd.duarhcentralhub.R;
import com.duarbd.duarhcentralhub.model.ModelDeliveryRequest;

import java.util.List;

public class AdapterRiderBillClearance extends RecyclerView.Adapter<AdapterRiderBillClearance.ViewHoldeAdapterRiderBillClearance>{

    List<ModelDeliveryRequest> deliverylist;
    Context context;

    public AdapterRiderBillClearance(List<ModelDeliveryRequest> deliverylist, Context context) {
        this.deliverylist = deliverylist;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHoldeAdapterRiderBillClearance onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_rider_bill_clearance,parent,false);
        ViewHoldeAdapterRiderBillClearance viewHoldeAdapterRiderBillClearance=new ViewHoldeAdapterRiderBillClearance(view);
        return  viewHoldeAdapterRiderBillClearance;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoldeAdapterRiderBillClearance holder, int position) {
        ModelDeliveryRequest delivery=deliverylist.get(position);

        holder.rbc_riderName.setText(delivery.getRiderName());
        holder.rbc_totalRide.setText("Total Delivery "+delivery.getResponse());
    }

    @Override
    public int getItemCount() {
        return deliverylist.size();
    }

    class ViewHoldeAdapterRiderBillClearance extends RecyclerView.ViewHolder{
        CardView container;
        TextView rbc_riderName,rbc_totalRide,rbc_viewDetails;
        public ViewHoldeAdapterRiderBillClearance(@NonNull View itemView) {
            super(itemView);
            rbc_riderName=itemView.findViewById(R.id.rbc_riderName);
            rbc_totalRide=itemView.findViewById(R.id.rbc_totalRide);
            rbc_viewDetails=itemView.findViewById(R.id.rbc_viewDetails);

        }
    }
}
