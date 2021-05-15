package com.duarbd.duarhcentralhub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.duarbd.duarhcentralhub.R;
import com.duarbd.duarhcentralhub.interfaces.AdapterDeliveryHistoryCallback;
import com.duarbd.duarhcentralhub.model.ModelDeliveryRequest;

import java.util.List;

public class AdapterDeliveryHistory extends RecyclerView.Adapter<AdapterDeliveryHistory.ViewHolderAdapterDeliveryHistory>{

    List<ModelDeliveryRequest> deliveryHistory;
    Context context;
    AdapterDeliveryHistoryCallback callback;

    public AdapterDeliveryHistory(List<ModelDeliveryRequest> deliveryHistory, Context context, AdapterDeliveryHistoryCallback callback) {
        this.deliveryHistory = deliveryHistory;
        this.context = context;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolderAdapterDeliveryHistory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_delivery_history,parent,false);
        ViewHolderAdapterDeliveryHistory viewHolderAdapterDeliveryHistory=new ViewHolderAdapterDeliveryHistory(view);
        return viewHolderAdapterDeliveryHistory;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAdapterDeliveryHistory holder, int position) {
        ModelDeliveryRequest delivery=deliveryHistory.get(position);

        holder.tvdh_deliveryid.setText("Delivery ID: "+delivery.getDeliveryRequestId());
        holder.tvdh_client.setText("Delivery of: "+delivery.getClientName());
        holder.tvdh_price.setText("Bill: "+delivery.getProductPrice()+" Tk");
        holder.tvdh_deliverycharge.setText("Delivery Charge: "+delivery.getDeliveryCharge()+" Tk");

        if(delivery.getRiderClearance().equals("due")){
           holder.tvoc_clearance_rider.setText("Clearance(R): due");
           holder.tvoc_clearance_rider.setBackground(context.getResources().getDrawable(R.drawable.bg_cr4_fill_red));
        }else {
            holder.tvoc_clearance_rider.setText("Clearance(R): received");
            holder.tvoc_clearance_rider.setBackground(context.getResources().getDrawable(R.drawable.bg_cr4_fill_green));
        }

        if(delivery.getClientPaymentStatus().equals("due")){
            holder.tvoc_clearance_client.setText("Clearance(C): due");
            holder.tvoc_clearance_client.setBackground(context.getResources().getDrawable(R.drawable.bg_cr4_fill_red));
        }else {
            holder.tvoc_clearance_client.setText("Clearance(C): received");
            holder.tvoc_clearance_client.setBackground(context.getResources().getDrawable(R.drawable.bg_cr4_fill_green));
        }

        holder.tvdh_vieworderdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onMoreClicked(delivery);
            }
        });
    }

    @Override
    public int getItemCount() {
        return deliveryHistory.size();
    }

    public  class  ViewHolderAdapterDeliveryHistory extends RecyclerView.ViewHolder{
        TextView tvdh_deliveryid,tvdh_client,tvdh_price,tvdh_deliverycharge,
                tvoc_clearance_client,tvoc_clearance_rider,tvdh_vieworderdetails;
        public ViewHolderAdapterDeliveryHistory(@NonNull View itemView) {
            super(itemView);
            tvdh_deliveryid=itemView.findViewById(R.id.tvdh_deliveryid);
            tvdh_client=itemView.findViewById(R.id.tvdh_client);
            tvdh_price=itemView.findViewById(R.id.tvdh_price);
            tvdh_deliverycharge=itemView.findViewById(R.id.tvdh_deliverycharge);
            tvoc_clearance_client=itemView.findViewById(R.id.tvoc_clearance_client);
            tvoc_clearance_rider=itemView.findViewById(R.id.tvoc_clearance_rider);
            tvdh_vieworderdetails=itemView.findViewById(R.id.tvdh_vieworderdetails);
        }
    }
}
