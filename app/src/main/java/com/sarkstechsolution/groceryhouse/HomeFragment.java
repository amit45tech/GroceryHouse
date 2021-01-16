package com.sarkstechsolution.groceryhouse;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sarkstechsolution.groceryhouse.Adapter.BannerAdapter;
import com.sarkstechsolution.groceryhouse.Adapter.MainCategoryAdapter;
import com.sarkstechsolution.groceryhouse.Adapter.StoresAdapter;
import com.sarkstechsolution.groceryhouse.Models.BannerModel;
import com.sarkstechsolution.groceryhouse.Models.MainCategoryItem;
import com.sarkstechsolution.groceryhouse.Models.StoresModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    public HomeFragment() {
    }

    private View view;
    TextView currentLocation;


   //////////////Category slider start////////////
    private RecyclerView recyclerViewCategory;
    private MainCategoryAdapter mainCategoryAdapter;
    private List<MainCategoryItem> mainCategoryItemList;
   //////////////Category slider end////////////

   //////////////Banner slider Start////////////
    private ViewPager bannerPager;
    BannerAdapter bannerAdapter;
    TabLayout tabIndicator;
   //////////////Banner slider end////////////

   //////////////Stores slider start////////////
    private RecyclerView recyclerViewStore;
     StoresAdapter storesAdapter;
    private DatabaseReference storeRef;
   //////////////Stores slider end////////////
    Context context;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);


        currentLocation = view.findViewById(R.id.current_location);
//        currentLocation.setText(view.getIntent().getStringExtra("address"));
       // Bundle bundle = getArguments();


        String message = getArguments().getString("message");
        currentLocation.setText(message);


        recyclerViewStore = view.findViewById(R.id.stores_recycler);
        recyclerViewStore.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<StoresModel> options = new FirebaseRecyclerOptions.Builder<StoresModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("RegisteredStores"), StoresModel.class)
                .build();



        storesAdapter = new StoresAdapter(options, getContext());
        recyclerViewStore.setAdapter(storesAdapter);

        initCategory();
        initBanner();


        return  view;

    }



    @Override
    public void onStart() {
        super.onStart();
        storesAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        storesAdapter.stopListening();
    }

    private void initCategory()
    {
        recyclerViewCategory = view.findViewById(R.id.main_category);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerViewCategory.setLayoutManager(layoutManager);


        mainCategoryItemList = new ArrayList<>();
        mainCategoryItemList.add(new MainCategoryItem("Ration", R.drawable.ration));
        mainCategoryItemList.add(new MainCategoryItem("Dairy Products", R.drawable.ration));
        mainCategoryItemList.add(new MainCategoryItem("Fruits", R.drawable.fruits));
        mainCategoryItemList.add(new MainCategoryItem("Vegetables", R.drawable.vegies));
        mainCategoryItemList.add(new MainCategoryItem("Non Veg", R.drawable.nonveg));
        mainCategoryItemList.add(new MainCategoryItem("Street Foods", R.drawable.streetfood));


        mainCategoryAdapter = new MainCategoryAdapter(mainCategoryItemList, getContext());
        recyclerViewCategory.setAdapter(mainCategoryAdapter);
        mainCategoryAdapter.notifyDataSetChanged();
    }

    private void initBanner()
    {
        tabIndicator = view.findViewById(R.id.bannerTabLayout);
        tabIndicator.setSelectedTabIndicatorColor(Color.TRANSPARENT);

        List<BannerModel> bannerModelList = new ArrayList<>();
        bannerModelList.add(new BannerModel(R.drawable.banner1));
        bannerModelList.add(new BannerModel(R.drawable.banner2));
        bannerModelList.add(new BannerModel(R.drawable.banner3));
        bannerModelList.add(new BannerModel(R.drawable.banner2));
        bannerModelList.add(new BannerModel(R.drawable.banner1));

        // setup viewpager
        bannerPager =view.findViewById(R.id.bannerViewPager);
        bannerAdapter = new BannerAdapter(getContext(),bannerModelList);
        bannerPager.setAdapter(bannerAdapter);

        // setup tablayout with viewpager

        tabIndicator.setupWithViewPager(bannerPager);


    }

}
