package com.sarkstechsolution.groceryhouse.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sarkstechsolution.groceryhouse.CartFragment;
import com.sarkstechsolution.groceryhouse.MainActivity;
import com.sarkstechsolution.groceryhouse.Models.MainCategoryItem;
import com.sarkstechsolution.groceryhouse.R;
import com.sarkstechsolution.groceryhouse.SearchFragment;


import java.util.List;

public class MainCategoryAdapter extends RecyclerView.Adapter<MainCategoryAdapter.ViewHolder>{

    private List<MainCategoryItem> mainCategoryItemList;
    private Context context;

    public MainCategoryAdapter(List<MainCategoryItem> mainCategoryItemList, Context context)
    {
        this.mainCategoryItemList = mainCategoryItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.layout_main_category, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MainCategoryItem mainCategoryItem = mainCategoryItemList.get(position);


        holder.mainCategoryItem.setText(mainCategoryItem.getCategory());

        Glide.with(context).load(mainCategoryItem.getCimage()).placeholder(R.drawable.img1).into(holder.cat_img);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





            }
        });



    }


    @Override
    public int getItemCount() {
        return mainCategoryItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mainCategoryItem;
        private ImageView cat_img;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mainCategoryItem = (TextView) itemView.findViewById(R.id.categoryText);
            cat_img = itemView.findViewById(R.id.categoryImage);


        }
    }



}
