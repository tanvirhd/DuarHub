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
import com.duarbd.duarhcentralhub.interfaces.AdapterDeliveryRequestCallbacks;
import com.duarbd.duarhcentralhub.model.ModelDeliveryRequest;
import com.duarbd.duarhcentralhub.tools.Utils;

import java.util.List;

public class AdapterDeliveryRequest extends RecyclerView.Adapter<AdapterDeliveryRequest.ViewHolderAdapterDeliveryRequest>{

    List<ModelDeliveryRequest> deliveryRequestList;
    Context context;
    AdapterDeliveryRequestCallbacks callbacks;

    public AdapterDeliveryRequest(List<ModelDeliveryRequest> deliveryRequestList, Context context, AdapterDeliveryRequestCallbacks callbacks) {
        this.deliveryRequestList = deliveryRequestList;
        this.context = context;
        this.callbacks = callbacks;
    }

    @NonNull
    @Override
    public ViewHolderAdapterDeliveryRequest onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_requested_delivery,parent,false);
        ViewHolderAdapterDeliveryRequest viewHolderAdapterDeliveryRequest=new ViewHolderAdapterDeliveryRequest(view);
        return viewHolderAdapterDeliveryRequest;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAdapterDeliveryRequest holder, int position) {
        ModelDeliveryRequest deliveryRequest=deliveryRequestList.get(position);

        switch (deliveryRequest.getDeliveryStatus()){
            case 0:
                holder.tvRDStatus.setText("Status: "+"Pending");
                break;
            case 1:
                holder.tvRDStatus.setText("Status: "+"Accepted");
                break;
            case 2:
                holder.tvRDStatus.setText("Status: "+"Rider Assigned");
                break;
            case 3:
                holder.tvRDStatus.setText("Status: "+"On the way to pickup");
                break;
            case 4:
                holder.tvRDStatus.setText("Status: "+"Received");
                break;
            case 5:
                holder.tvRDStatus.setText("Status: "+"On the way to delivery");
                break;
            case 6:
                holder.tvRDStatus.setText("Status: "+"Delivered");
                break;
            case 7:
                holder.tvRDStatus.setText("Canceled");
                break;
        }

        holder.tvRdDeliveryRequestId.setText("Request ID: "+deliveryRequest.getDeliveryRequestId());
        holder.tvRdRequestedBy.setText("Requested By: "+deliveryRequest.getClientName());

        holder.tvPickupFrom.setText("PICKUP FROM:"+"\n"+deliveryRequest.getPickUpAddress());
        holder.tvDeliverTo.setText("DELIVER TO:"+"\n"+deliveryRequest.getDeliveryArea());

        holder.tvRiderName.setText(deliveryRequest.getRiderName().equals("")?"RIDER NAME"+"\n"+"Rider isn't assigned yet":
                "RIDER NAME"+"\n"+deliveryRequest.getRiderName());

        holder.tvPickupTime.setText("Pickup Time"+"\n"+
                Utils.addMinute(Utils.getTimeFromDeliveryRequestPlacedDate(deliveryRequest.getRequestPlacedTime()),
                        deliveryRequest.getPickupTime()));


        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callbacks.onRequestedDeliveryItemClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return deliveryRequestList.size();
    }

    class ViewHolderAdapterDeliveryRequest extends RecyclerView.ViewHolder{

        TextView tvRDStatus,tvPickupTime,tvRdDeliveryRequestId,tvRdRequestedBy,tvPickupFrom,tvDeliverTo,tvRiderName;
        CardView container;
        public ViewHolderAdapterDeliveryRequest(@NonNull View itemView) {
            super(itemView);
            tvRDStatus=itemView.findViewById(R.id.tvRDStatus);
            tvPickupTime=itemView.findViewById(R.id.tvPickupTime);
            tvRdDeliveryRequestId=itemView.findViewById(R.id.tvRdDeliveryRequestId);
            tvRdRequestedBy=itemView.findViewById(R.id.tvRdRequestedBy);
            tvPickupFrom=itemView.findViewById(R.id.tvPickupFrom);
            tvDeliverTo=itemView.findViewById(R.id.tvDeliverTo);
            container=itemView.findViewById(R.id.oc_cardview);
            tvRiderName=itemView.findViewById(R.id.tvRiderName);
        }
    }
}
