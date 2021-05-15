package com.duarbd.duarhcentralhub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.duarbd.duarhcentralhub.R;
import com.duarbd.duarhcentralhub.model.ModelRiderSalary;

import java.util.List;


public class AdapterSalaryCalculation extends RecyclerView.Adapter<AdapterSalaryCalculation.ViewHoldeAdapterSalaryCalculation>{
    List<ModelRiderSalary> salaryList;
    Context context;

    public AdapterSalaryCalculation(List<ModelRiderSalary> salaryList, Context context) {
        this.salaryList = salaryList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHoldeAdapterSalaryCalculation onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_rider_salary,parent,false);
        ViewHoldeAdapterSalaryCalculation viewholder=new ViewHoldeAdapterSalaryCalculation(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoldeAdapterSalaryCalculation holder, int position) {
        ModelRiderSalary riderSalary=salaryList.get(position);
        holder.rsc_riderName.setText(riderSalary.getRiderName());
        holder.rsc_RiderVehicel.setText(riderSalary.getRiderVehicle()+" Rider");
        holder.rsc_salary.setText(riderSalary.getRiderSalary()+" Tk");
    }

    @Override
    public int getItemCount() {
        return salaryList.size();
    }

    class  ViewHoldeAdapterSalaryCalculation extends RecyclerView.ViewHolder{
        TextView rsc_riderName,rsc_RiderVehicel,rsc_salary;
        public ViewHoldeAdapterSalaryCalculation(@NonNull View itemView) {
            super(itemView);
            rsc_riderName=itemView.findViewById(R.id.rsc_riderName);
            rsc_RiderVehicel=itemView.findViewById(R.id.rsc_RiderVehicel);
            rsc_salary=itemView.findViewById(R.id.rsc_salary);
        }
    }
}
