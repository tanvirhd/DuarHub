package com.duarbd.duarhcentralhub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.duarbd.duarhcentralhub.R;
import com.duarbd.duarhcentralhub.interfaces.AdapterActiveRiderCallbacks;
import com.duarbd.duarhcentralhub.model.ModelActiveRider;

import java.util.List;

public class AdapterActiveRiders extends RecyclerView.Adapter<AdapterActiveRiders.ViewHolderAdapterActiveRiders>{
    List<ModelActiveRider> modelActiveRiderList;
    Context context;
    AdapterActiveRiderCallbacks callbacks;

    public AdapterActiveRiders(List<ModelActiveRider> modelActiveRiderList, Context context, AdapterActiveRiderCallbacks callbacks) {
        this.modelActiveRiderList = modelActiveRiderList;
        this.context = context;
        this.callbacks = callbacks;
    }

    @NonNull
    @Override
    public ViewHolderAdapterActiveRiders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_active_rider,parent,false);
        ViewHolderAdapterActiveRiders viewHolderAdapterActiveRiders=new ViewHolderAdapterActiveRiders(view);
        return  viewHolderAdapterActiveRiders;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAdapterActiveRiders holder, int position) {
        ModelActiveRider activeRider=modelActiveRiderList.get(position);
        holder.ridername.setText(activeRider.getRiderName());
        if(activeRider.getDelivery()==null)holder.ongoingRideNumber.setText("0");
        else holder.ongoingRideNumber.setText(activeRider.getDelivery().size()+"");

        if(activeRider.getDelivery().size()==0){
            holder.recycongoingRideList.setVisibility(View.INVISIBLE);
            holder.noOngoingRideText.setVisibility(View.VISIBLE);
        }else {
            holder.recycongoingRideList.setVisibility(View.VISIBLE);
            holder.noOngoingRideText.setVisibility(View.INVISIBLE);
        }

        holder.assignNewRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callbacks.onAssignNewRideClicked(position);
            }
        });


        holder.recycongoingRideList.setLayoutManager(new LinearLayoutManager(context));
        holder.recycongoingRideList.setAdapter(
                    new AdapterActiveRiderOngoingDelieveries(activeRider.getDelivery(),context)  );
    }

    @Override
    public int getItemCount() {
        return modelActiveRiderList.size();
    }

    class ViewHolderAdapterActiveRiders extends RecyclerView.ViewHolder{

        TextView ridername,ongoingRideNumber,noOngoingRideText,assignNewRide;
        RecyclerView recycongoingRideList;

        public ViewHolderAdapterActiveRiders(@NonNull View itemView) {
            super(itemView);
            ridername=itemView.findViewById(R.id.tvARD_RiderName);
            ongoingRideNumber=itemView.findViewById(R.id.tvARD_ongointRideNumber);
            noOngoingRideText=itemView.findViewById(R.id.tvARD_noOngoingRideText);
            assignNewRide=itemView.findViewById(R.id.tvARD_assignNewDelivery);

            recycongoingRideList=itemView.findViewById(R.id.recyc_ongoingRideList);
        }
    }
}

class AdapterActiveRiderOngoingDelieveries extends RecyclerView.Adapter<AdapterActiveRiderOngoingDelieveries.VH>{

    List<ModelActiveRider.ModelAssignedDelivery> assignedDeliveryList;
    private Context context;

    public AdapterActiveRiderOngoingDelieveries(List<ModelActiveRider.ModelAssignedDelivery> assignedDeliveryList, Context context) {
        this.assignedDeliveryList = assignedDeliveryList;
        this.context = context;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.layout_delivery_details,parent,false);
        VH vh=new VH(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        ModelActiveRider.ModelAssignedDelivery assignedDelivery=assignedDeliveryList.get(position);
        holder.deliveryStage.setText("Stage"+"\n"+"\n"+assignedDelivery.getDeliveryStatus());
        switch (assignedDelivery.getDeliveryStatus()){
            case "0":
                holder.deliveryStatus.setText("Pending");
                break;
            case "1":
                holder.deliveryStatus.setText("Accepted");
                break;
            case "2":
                holder.deliveryStatus.setText("On the way to pickup");
                break;
            case "3":
                holder.deliveryStatus.setText("Received");
                break;
            case "4":
                holder.deliveryStatus.setText("On the way to delivery");
                break;
            case "5":
                holder.deliveryStatus.setText("Delivered");
                break;
        }
        holder.pickupAddress.setText("Pickup from: "+assignedDelivery.getPickUpAddress());
        holder.deliveryAddress.setText("Deliver to:"+assignedDelivery.getDeliveryArea());

    }

    @Override
    public int getItemCount() {
        return assignedDeliveryList.size();
    }

    class VH extends RecyclerView.ViewHolder{
        TextView deliveryStage,pickupAddress,deliveryAddress,deliveryStatus;
        public VH(@NonNull View itemView) {
            super(itemView);
            deliveryStage=itemView.findViewById(R.id.tvARD_deliveryStage);
            pickupAddress=itemView.findViewById(R.id.tvARD_pickupAddress);
            deliveryAddress=itemView.findViewById(R.id.tvARD_deliveryAddress);
            deliveryStatus=itemView.findViewById(R.id.tvARD_deliveryStatus);
        }
    }
}
