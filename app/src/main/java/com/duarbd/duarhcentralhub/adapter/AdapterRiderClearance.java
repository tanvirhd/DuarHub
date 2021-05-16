package com.duarbd.duarhcentralhub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.duarbd.duarhcentralhub.R;
import com.duarbd.duarhcentralhub.interfaces.AdapterRiderClearanceCallback;
import com.duarbd.duarhcentralhub.model.ModelRiderClearance;

import java.util.List;

public class AdapterRiderClearance extends RecyclerView.Adapter<AdapterRiderClearance.ViewHolderAdapterRiderClearance>{

    List<ModelRiderClearance> riderClearanceList;
    Context context;
    AdapterRiderClearanceCallback callback;

    public AdapterRiderClearance(List<ModelRiderClearance> riderClearanceList, Context context, AdapterRiderClearanceCallback callback) {
        this.riderClearanceList = riderClearanceList;
        this.context = context;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolderAdapterRiderClearance onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_rider_bill_clearance,parent,false);
        ViewHolderAdapterRiderClearance viewHolderAdapterRiderClearance=new ViewHolderAdapterRiderClearance(view);
        return viewHolderAdapterRiderClearance;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAdapterRiderClearance holder, int position) {
        holder.rbc_riderName.setText(riderClearanceList.get(position).getRiderName());
        holder.rbc_totalRide.setText("Total delivery: "+riderClearanceList.get(position).getTotalRide());

        holder.rbc_viewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onViewDetailsClicked(riderClearanceList.get(position).getRiderName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return riderClearanceList.size();
    }

    class ViewHolderAdapterRiderClearance extends RecyclerView.ViewHolder{
        TextView rbc_riderName,rbc_totalRide,rbc_viewDetails;
        public ViewHolderAdapterRiderClearance(@NonNull View itemView) {
            super(itemView);
            rbc_riderName=itemView.findViewById(R.id.rbc_riderName);
            rbc_totalRide=itemView.findViewById(R.id.rbc_totalRide);
            rbc_viewDetails=itemView.findViewById(R.id.rbc_viewDetails);
        }
    }
}
