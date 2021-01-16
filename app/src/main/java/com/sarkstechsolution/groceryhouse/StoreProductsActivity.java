package com.sarkstechsolution.groceryhouse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.sarkstechsolution.groceryhouse.Adapter.ProductsAdapter;
import com.sarkstechsolution.groceryhouse.Models.Products;

public class StoreProductsActivity extends AppCompatActivity {

    private RecyclerView productRecyclerView;
    private ProductsAdapter  adapter;

    private String StoreID,StoreName;
    TextView sName;
    ImageView cart, back;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_products);
        productRecyclerView = findViewById(R.id.productRecyclerView);
        sName = findViewById(R.id.yourCart);
        cart = findViewById(R.id.cartBtn);
        back = findViewById(R.id.backBtn);


        productRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Bundle bundle = getIntent().getExtras();
        StoreID = bundle.getString("storeId");
        StoreName = bundle.getString("storeName");
        sName.setText(StoreName);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(StoreProductsActivity.this, CartActivity.class);

                startActivity(intent);

            }
        });





// Firebase recycler---------------------------
        FirebaseRecyclerOptions options =
                new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Products").child(StoreID), Products.class)
                .build();

        adapter = new ProductsAdapter(options, getApplicationContext());
        productRecyclerView.setAdapter(adapter);
        adapter.startListening();
// Firebase recycler end---------------------------

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }
}