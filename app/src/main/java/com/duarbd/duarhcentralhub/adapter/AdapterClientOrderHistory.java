package com.duarbd.duarhcentralhub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.duarbd.duarhcentralhub.R;
import com.duarbd.duarhcentralhub.interfaces.AdapterClientOrderHistoryCallback;
import com.duarbd.duarhcentralhub.model.ModelDeliveryRequest;

import java.util.List;

public class AdapterClientOrderHistory extends RecyclerView.Adapter<AdapterClientOrderHistory.ViewHolderAdapterClientOrderHistory>{

    List<ModelDeliveryRequest> deliveryList;
    Context context;
    AdapterClientOrderHistoryCallback callback;

    public AdapterClientOrderHistory(List<ModelDeliveryRequest> deliveryList, Context context, AdapterClientOrderHistoryCallback callback) {
        this.deliveryList = deliveryList;
        this.context = context;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolderAdapterClientOrderHistory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_client_order_history,parent,false);
        ViewHolderAdapterClientOrderHistory viewHolderAdapterClientOrderHistory=new ViewHolderAdapterClientOrderHistory(view);
        return viewHolderAdapterClientOrderHistory;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAdapterClientOrderHistory holder, int position) {
        ModelDeliveryRequest delivery=deliveryList.get(position);
        holder.tvcbh_deliveryid.setText("Delivery id: "+delivery.getDeliveryRequestId());
        holder.tvcbh_price.setText("Bill: "+delivery.getProductPrice()+" tk");
        holder.tvcbh_deliverycharge.setText(("Delivery Charge: "+delivery.getDeliveryCharge()+" tk"));
        holder.tvcbh_pickupCharge.setText("Pickup Charge: "+delivery.getPickupCharge()+" tk");

        if(delivery.getClientPaymentStatus().equals("due")){
            holder.tvcbh_PaymentStatus.setText("Due");
            holder.tvcbh_PaymentStatus.setBackground(context.getResources().getDrawable(R.drawable.bg_cr4_fill_red));
        }else {
            holder.tvcbh_PaymentStatus.setText("Paid");
            holder.tvcbh_PaymentStatus.setBackground(context.getResources().getDrawable(R.drawable.bg_cr4_fill_green));
        }


        holder.tvcbh_vieworderdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onMoreClicked(delivery);
            }
        });
    }

    @Override
    public int getItemCount() {
        return deliveryList.size();
    }

    class ViewHolderAdapterClientOrderHistory extends RecyclerView.ViewHolder{
        TextView tvcbh_deliveryid,tvcbh_PaymentStatus,tvcbh_price,tvcbh_deliverycharge,tvcbh_pickupCharge,tvcbh_vieworderdetails;
        public ViewHolderAdapterClientOrderHistory(@NonNull View itemView) {
            super(itemView);
            tvcbh_deliveryid=itemView.findViewById(R.id.tvcbh_deliveryid);
            tvcbh_PaymentStatus=itemView.findViewById(R.id.tvcbh_PaymentStatus);
            tvcbh_price=itemView.findViewById(R.id.tvcbh_price);
            tvcbh_deliverycharge=itemView.findViewById(R.id.tvcbh_deliverycharge);
            tvcbh_pickupCharge=itemView.findViewById(R.id.tvcbh_pickupCharge);
            tvcbh_vieworderdetails=itemView.findViewById(R.id.tvcbh_vieworderdetails);
        }
    }
}
