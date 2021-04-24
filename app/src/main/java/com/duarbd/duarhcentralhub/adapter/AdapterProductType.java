package com.duarbd.duarhcentralhub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.duarbd.duarhcentralhub.R;
import com.duarbd.duarhcentralhub.interfaces.AdapterProductTypeCallBacks;
import com.duarbd.duarhcentralhub.model.ModelProductType;

import java.util.List;


public class AdapterProductType extends RecyclerView.Adapter<AdapterProductType.ViewHolderAdapterProductType>{

    List<ModelProductType> modelProductTypeList;
    Context context;
    AdapterProductTypeCallBacks callBacks;

    public AdapterProductType(List<ModelProductType> productTypeList, Context context, AdapterProductTypeCallBacks callBacks) {
        this.modelProductTypeList = productTypeList;
        this.context = context;
        this.callBacks = callBacks;
    }

    @NonNull
    @Override
    public ViewHolderAdapterProductType onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.lauout_product_type,parent,false);
        ViewHolderAdapterProductType viewHolderAdapterProductType=new ViewHolderAdapterProductType(view);
        return viewHolderAdapterProductType;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAdapterProductType holder, int position) {
        ModelProductType type=modelProductTypeList.get(position);
        holder.tvProductType.setText(type.getProductTypeName());
        if(type.isSelected()){
            holder.container.setBackground(context.getResources().getDrawable(R.drawable.bg_setected,null));
        }else {
            holder.container.setBackground(context.getResources().getDrawable(R.drawable.bg_not_setected,null));
        }

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBacks.onProductTypeClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelProductTypeList.size();
    }

    class ViewHolderAdapterProductType extends RecyclerView.ViewHolder{
        TextView tvProductType;
        ConstraintLayout container;

        public ViewHolderAdapterProductType(@NonNull View itemView) {
            super(itemView);
            tvProductType=itemView.findViewById(R.id.tvProductType);
            container=itemView.findViewById(R.id.product_type_conatiner);
        }
    }
}
