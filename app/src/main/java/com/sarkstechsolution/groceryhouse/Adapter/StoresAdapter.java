package com.sarkstechsolution.groceryhouse.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.sarkstechsolution.groceryhouse.Models.StoresModel;
import com.sarkstechsolution.groceryhouse.R;
import com.sarkstechsolution.groceryhouse.StoreProductsActivity;

public class StoresAdapter extends FirebaseRecyclerAdapter<StoresModel, StoresAdapter.StoreViewHolder> {

    private Context context;

    public StoresAdapter(@NonNull FirebaseRecyclerOptions<StoresModel> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull StoreViewHolder holder, int position, @NonNull StoresModel model) {

        holder.storeName.setText(model.getStorename());
        holder.storeType.setText(model.getStoretype());
        holder.deliveryTime.setText("30 min");

        Glide.with(holder.storeLogoImg.getContext())
                .load(model.getLogo()) // image url
//                .placeholder(R.drawable.ic_baseline_image_24) // any placeholder to load at start
//                .error(R.drawable.ic_baseline_image_24)  // any image in case of error
//                .override(100, 100) // resizing
//                .optionalFitCenter()
                .into(holder.storeLogoImg);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("storeId", model.getStoreid());
                bundle.putString("storeName", model.getStorename());

                Intent intent = new Intent(context, StoreProductsActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

    }

    @NonNull
    @Override
    public StoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_store, parent, false);
        return new StoreViewHolder(view);
    }




    public class StoreViewHolder extends RecyclerView.ViewHolder {

        TextView storeName, storeType, deliveryTime;
        ImageView storeLogoImg;

        public StoreViewHolder(@NonNull View itemView) {
            super(itemView);

            storeName = itemView.findViewById(R.id.sto_name);
            storeType = itemView.findViewById(R.id.sto_type);
            deliveryTime = itemView.findViewById(R.id.del_time);
            storeLogoImg = itemView.findViewById(R.id.store_logo);
        }

    }

}
