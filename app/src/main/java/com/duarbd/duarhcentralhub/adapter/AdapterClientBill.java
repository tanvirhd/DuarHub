package com.duarbd.duarhcentralhub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.duarbd.duarhcentralhub.R;
import com.duarbd.duarhcentralhub.interfaces.AdapterClientBillCallBack;
import com.duarbd.duarhcentralhub.model.ModelClientBill;

import java.util.List;

public class AdapterClientBill extends RecyclerView.Adapter<AdapterClientBill.ViewHolderAdapterClientBill> {
    Context context;
    List<ModelClientBill> billList;
    AdapterClientBillCallBack clientBillCallBack;

    public AdapterClientBill(List<ModelClientBill> billList,Context context,  AdapterClientBillCallBack clientBillCallBack) {
        this.context = context;
        this.billList = billList;
        this.clientBillCallBack = clientBillCallBack;
    }

    @NonNull
    @Override
    public ViewHolderAdapterClientBill onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_client_bill,parent,false);
        ViewHolderAdapterClientBill viewHolderAdapterClientBill=new ViewHolderAdapterClientBill(view);
        return viewHolderAdapterClientBill;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAdapterClientBill holder, final int position) {
        holder.client_name.setText(""+billList.get(position).getClient_name());
        holder.total_order.setText("Total Order: "+billList.get(position).getTotal_order());
        holder.total_bill.setText("Total Bill: "+billList.get(position).getTotal_bill()+" Tk");
        holder.total_deliveryCharge.setText("Total Delivery Charge: "+billList.get(position).getTotal_deliveryCharge()+" Tk");

        holder.view_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clientBillCallBack.onViewDetailsClicked(billList.get(position).getClient_name());
            }
        });
    }

    @Override
    public int getItemCount() {
        return billList.size();
    }

    class ViewHolderAdapterClientBill extends RecyclerView.ViewHolder{
        TextView client_name,total_bill,total_deliveryCharge,total_order,view_details;
        public ViewHolderAdapterClientBill(@NonNull View itemView) {
            super(itemView);
            client_name=itemView.findViewById(R.id.cbl_clientname);
            total_bill=itemView.findViewById(R.id.cbl_totalbill);
            total_deliveryCharge=itemView.findViewById(R.id.cbl_totaldeliverycharge);
            total_order=itemView.findViewById(R.id.cbl_totalorder);
            view_details=itemView.findViewById(R.id.cbl_vieworderdetails);
        }
    }
}
