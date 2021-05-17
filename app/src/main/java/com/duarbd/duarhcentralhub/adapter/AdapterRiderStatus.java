package com.duarbd.duarhcentralhub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.duarbd.duarhcentralhub.R;
import com.duarbd.duarhcentralhub.interfaces.AdapterRiderStatusCallback;
import com.duarbd.duarhcentralhub.model.ModelRider;

import java.util.List;

public class AdapterRiderStatus extends RecyclerView.Adapter<AdapterRiderStatus.ViewHolderAdapterRiderStatus>{
     List<ModelRider> riderList;
     Context context;
    AdapterRiderStatusCallback callback;

    public AdapterRiderStatus(List<ModelRider> riderList, Context context, AdapterRiderStatusCallback callback) {
        this.riderList = riderList;
        this.context = context;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolderAdapterRiderStatus onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_rider_status,parent,false);
        ViewHolderAdapterRiderStatus viewHolderAdapterRiderStatus=new ViewHolderAdapterRiderStatus(view);
        return viewHolderAdapterRiderStatus;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAdapterRiderStatus holder, int position) {
        ModelRider rider=riderList.get(position);

        holder.tvrs_ridername.setText(rider.getRiderName());
        holder.tvrs_riderid.setText("ID: "+rider.getRiderid());
        holder.tvrs_riderVehicle.setText("Vehicle: "+rider.getVehicleType());

        holder.iv_callRider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onCallRiderClicked(rider);
            }
        });

        holder.switchOnDuty.setChecked(rider.getWorkingStatus()==1?true:false);

        holder.switchOnDuty.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                callback.onRiderSutyStatusSwitchClicked(rider,isChecked);
            }
        });

    }

    @Override
    public int getItemCount() {
        return riderList.size();
    }

    class ViewHolderAdapterRiderStatus extends RecyclerView.ViewHolder{
        TextView tvrs_ridername,tvrs_riderid,tvrs_riderVehicle;
        ImageView iv_callRider;
        Switch switchOnDuty;
        public ViewHolderAdapterRiderStatus(@NonNull View itemView) {
            super(itemView);
            tvrs_ridername=itemView.findViewById(R.id.tvrs_ridername);
            tvrs_riderid=itemView.findViewById(R.id.tvrs_riderid);
            tvrs_riderVehicle=itemView.findViewById(R.id.tvrs_riderVehicle);
            iv_callRider=itemView.findViewById(R.id.iv_callRider);
            switchOnDuty=itemView.findViewById(R.id.switchOnDuty);
        }
    }
}
