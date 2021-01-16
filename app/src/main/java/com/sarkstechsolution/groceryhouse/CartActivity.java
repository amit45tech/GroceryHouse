package com.sarkstechsolution.groceryhouse;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sarkstechsolution.groceryhouse.Adapter.CartItemAdapter;
import com.sarkstechsolution.groceryhouse.Interfaces.CheckCart;
import com.sarkstechsolution.groceryhouse.Models.Products;

import java.util.HashMap;

public class CartActivity extends AppCompatActivity implements CheckCart {

    TextView TotalMrp,TotalItems, RemoveTip, TotalSaving, ServiceCharge, Tip, SubTotal,Total, Plus5, Plus10, Plus20, Plus30, Plus50, Plus100, EnterAmt;
    ImageView  back;
    String userId;
    private RecyclerView cartItemRecyclerView;
    private CartItemAdapter adapter;
    LinearLayout CheckoutLayout, tipLayout;
    DatabaseReference cartRef;
    int tTip = 0;
    EditText TipAmt;
    Button AddTip;
    NestedScrollView scrollView;
    LottieAnimationView lottieAnimationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        back = findViewById(R.id.backBtn);
        lottieAnimationView = findViewById(R.id.lottie2);
        TotalItems = findViewById(R.id.totalItems);
        scrollView = findViewById(R.id.nestedScrollView);
        CheckoutLayout = findViewById(R.id.checkout);
        tipLayout = findViewById(R.id.tipLayout);
        TotalMrp = findViewById(R.id.totalMrp);
        TotalSaving = findViewById(R.id.totalSaving);
        ServiceCharge = findViewById(R.id.serviceCharge);
        Tip = findViewById(R.id.tip);
        SubTotal = findViewById(R.id.totalAmt);
        Total = findViewById(R.id.total);
        Plus5 = findViewById(R.id.plus5);
        Plus10 = findViewById(R.id.plus10);
        Plus20 = findViewById(R.id.plus20);
        Plus30 = findViewById(R.id.plus30);
        Plus50 = findViewById(R.id.plus50);
        Plus100 = findViewById(R.id.plus100);
        EnterAmt = findViewById(R.id.enterAmt);
        TipAmt = findViewById(R.id.tipAmt);
        AddTip = findViewById(R.id.addTip);
        RemoveTip = findViewById(R.id.removeTip);
        cartItemRecyclerView = findViewById(R.id.cartItemRecycler);
        cartItemRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        AddTip.setVisibility(View.GONE);
        TipAmt.setVisibility(View.GONE);
        tipLayout.setVisibility(View.GONE);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            userId= FirebaseAuth.getInstance().getCurrentUser().getUid();
            cartRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
            TipCheck();

            CartTotal();
            // Firebase recycler---------------------------
            FirebaseRecyclerOptions options =
                    new FirebaseRecyclerOptions.Builder<Products>()
                            .setQuery(cartRef.child("Cart"), Products.class)
                            .build();

            adapter = new CartItemAdapter(options,this,  getApplicationContext());
            cartItemRecyclerView.setAdapter(adapter);
            adapter.startListening();
            // Firebase recycler end---------------------------
        }
        else{
            CheckoutLayout.setVisibility(View.GONE);
            scrollView.setVisibility(View.GONE);
//------------------------------------Put Ads here---------------------------------------------------------------
//------------------------------------Put Ads here---------------------------------------------------------------
//------------------------------------Put Ads here---------------------------------------------------------------
//------------------------------------Put Ads here---------------------------------------------------------------
        }




        HashMap<String, Object> addTip = new HashMap<>();

        Plus5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTip.put("deliveryTip", 5);

                cartRef.child("Tip").updateChildren(addTip);
                TipCheck();
                CartTotal();
            }
        });
        Plus10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTip.put("deliveryTip", "10");

                cartRef.child("Tip").updateChildren(addTip);
                TipCheck();
                CartTotal();
            }
        });
        Plus20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTip.put("deliveryTip", "20");

                cartRef.child("Tip").updateChildren(addTip);
                TipCheck();
                CartTotal();
            }
        });
        Plus30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTip.put("deliveryTip", "30");

                cartRef.child("Tip").updateChildren(addTip);
                TipCheck();
                CartTotal();
            }
        });
        Plus50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTip.put("deliveryTip", "50");

                cartRef.child("Tip").updateChildren(addTip);
                TipCheck();
                CartTotal();
            }
        });
        Plus100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTip.put("deliveryTip", "100");

                cartRef.child("Tip").updateChildren(addTip);
                TipCheck();
                CartTotal();
            }
        });
        EnterAmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               AddTip.setVisibility(View.VISIBLE);
               TipAmt.setVisibility(View.VISIBLE);
               Plus5.setVisibility(View.GONE);
               Plus10.setVisibility(View.GONE);
               Plus20.setVisibility(View.GONE);
               Plus30.setVisibility(View.GONE);
               Plus50.setVisibility(View.GONE);
               Plus100.setVisibility(View.GONE);
               EnterAmt.setVisibility(View.GONE);

            }
        });
        AddTip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amt =  TipAmt.getText().toString();

                if (amt.isEmpty()) {
                    TipAmt.setError("Enter Amount");
                    TipAmt.requestFocus();
                    return;
                }
                else{
                    addTip.put("deliveryTip", amt);

                    cartRef.child("Tip").updateChildren(addTip);
                    AddTip.setVisibility(View.GONE);
                    TipAmt.setVisibility(View.GONE);
                    Plus5.setVisibility(View.VISIBLE);
                    Plus10.setVisibility(View.VISIBLE);
                    Plus20.setVisibility(View.VISIBLE);
                    Plus30.setVisibility(View.VISIBLE);
                    Plus50.setVisibility(View.VISIBLE);
                    Plus100.setVisibility(View.VISIBLE);
                    EnterAmt.setVisibility(View.VISIBLE);
                    TipCheck();
                    CartTotal();
                }
            }
        });
        RemoveTip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartRef.child("Tip").removeValue();
                tipLayout.setVisibility(View.GONE);
                Plus5.setBackgroundResource(R.drawable.tip_bg);
                Plus10.setBackgroundResource(R.drawable.tip_bg);
                Plus20.setBackgroundResource(R.drawable.tip_bg);
                Plus30.setBackgroundResource(R.drawable.tip_bg);
                Plus50.setBackgroundResource(R.drawable.tip_bg);
                Plus100.setBackgroundResource(R.drawable.tip_bg);
                EnterAmt.setBackgroundResource(R.drawable.tip_bg);
                EnterAmt.setText("Enter Amt");
                CartTotal();
            }
        });

        CheckoutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
                startActivity(intent);
            }
        });


    }

    private void CartTotal() {
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
        cartRef.child("Cart").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int items = 0;
                int tMrp = 0;
                int tSp = 0;
                int tTotal = 0;
                int sCharge = 15;

                for (DataSnapshot childDataSnapshot : snapshot.getChildren()) {
                    int qty =Integer.parseInt(String.valueOf(childDataSnapshot.child("quantity").getValue()));
                    items = items + qty;
                    tMrp = tMrp + Integer.parseInt(String.valueOf(childDataSnapshot.child("mrp").getValue())) * qty;
                    tSp = tSp + Integer.parseInt(String.valueOf(childDataSnapshot.child("sp").getValue())) * qty;
                }

                tTotal = tSp + sCharge + tTip;
                TotalMrp.setText("+" + "\u20B9" + String.valueOf(tMrp));
                Tip.setText("+" + "\u20B9" + String.valueOf(tTip));
                TotalSaving.setText("-" + "\u20B9" + String.valueOf(tMrp - tSp));
                ServiceCharge.setText("+" + "\u20B9" + String.valueOf(sCharge));
                SubTotal.setText("\u20B9" + String.valueOf(tTotal));
                Total.setText("\u20B9" + String.valueOf(tTotal));
                TotalItems.setText("\u20B9" + String.valueOf(items + "items"));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        }
    }

    private void TipCheck() {
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            cartRef.child("Tip").addListenerForSingleValueEvent(new ValueEventListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        int dTip = Integer.parseInt(snapshot.child("deliveryTip").getValue().toString());
                        tipLayout.setVisibility(View.VISIBLE);

                        if (dTip == 5) {
                            Plus5.setBackgroundResource(R.drawable.tip_solid_bg);
                            Plus10.setBackgroundResource(R.drawable.tip_bg);
                            Plus20.setBackgroundResource(R.drawable.tip_bg);
                            Plus30.setBackgroundResource(R.drawable.tip_bg);
                            Plus50.setBackgroundResource(R.drawable.tip_bg);
                            Plus100.setBackgroundResource(R.drawable.tip_bg);
                            EnterAmt.setBackgroundResource(R.drawable.tip_bg);
                            tTip = 5;
                        } else if (dTip == 10) {
                            Plus5.setBackgroundResource(R.drawable.tip_bg);
                            Plus10.setBackgroundResource(R.drawable.tip_solid_bg);
                            Plus20.setBackgroundResource(R.drawable.tip_bg);
                            Plus30.setBackgroundResource(R.drawable.tip_bg);
                            Plus50.setBackgroundResource(R.drawable.tip_bg);
                            Plus100.setBackgroundResource(R.drawable.tip_bg);
                            EnterAmt.setBackgroundResource(R.drawable.tip_bg);
                            tTip = 10;
                        } else if (dTip == 20) {
                            Plus5.setBackgroundResource(R.drawable.tip_bg);
                            Plus10.setBackgroundResource(R.drawable.tip_bg);
                            Plus20.setBackgroundResource(R.drawable.tip_solid_bg);
                            Plus30.setBackgroundResource(R.drawable.tip_bg);
                            Plus50.setBackgroundResource(R.drawable.tip_bg);
                            Plus100.setBackgroundResource(R.drawable.tip_bg);
                            EnterAmt.setBackgroundResource(R.drawable.tip_bg);
                            tTip = 20;
                        } else if (dTip == 30) {
                            Plus5.setBackgroundResource(R.drawable.tip_bg);
                            Plus10.setBackgroundResource(R.drawable.tip_bg);
                            Plus20.setBackgroundResource(R.drawable.tip_bg);
                            Plus30.setBackgroundResource(R.drawable.tip_solid_bg);
                            Plus50.setBackgroundResource(R.drawable.tip_bg);
                            Plus100.setBackgroundResource(R.drawable.tip_bg);
                            EnterAmt.setBackgroundResource(R.drawable.tip_bg);
                            tTip = 30;
                        } else if (dTip == 50) {
                            Plus5.setBackgroundResource(R.drawable.tip_bg);
                            Plus10.setBackgroundResource(R.drawable.tip_bg);
                            Plus20.setBackgroundResource(R.drawable.tip_bg);
                            Plus30.setBackgroundResource(R.drawable.tip_bg);
                            Plus50.setBackgroundResource(R.drawable.tip_solid_bg);
                            Plus100.setBackgroundResource(R.drawable.tip_bg);
                            EnterAmt.setBackgroundResource(R.drawable.tip_bg);
                            tTip = 50;
                        } else if (dTip == 100) {
                            Plus5.setBackgroundResource(R.drawable.tip_bg);
                            Plus10.setBackgroundResource(R.drawable.tip_bg);
                            Plus20.setBackgroundResource(R.drawable.tip_bg);
                            Plus30.setBackgroundResource(R.drawable.tip_bg);
                            Plus50.setBackgroundResource(R.drawable.tip_bg);
                            Plus100.setBackgroundResource(R.drawable.tip_solid_bg);
                            EnterAmt.setBackgroundResource(R.drawable.tip_bg);
                            tTip = 100;
                        } else {
                            EnterAmt.setVisibility(View.VISIBLE);
                            Plus5.setBackgroundResource(R.drawable.tip_bg);
                            Plus10.setBackgroundResource(R.drawable.tip_bg);
                            Plus20.setBackgroundResource(R.drawable.tip_bg);
                            Plus30.setBackgroundResource(R.drawable.tip_bg);
                            Plus50.setBackgroundResource(R.drawable.tip_bg);
                            Plus100.setBackgroundResource(R.drawable.tip_bg);
                            EnterAmt.setBackgroundResource(R.drawable.tip_solid_bg);

                            EnterAmt.setText(String.valueOf(dTip));
                            tTip = dTip;
                        }
                    }
                    else{
                        tipLayout.setVisibility(View.GONE);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }

    public void EmptyCart() {
        CheckoutLayout.setVisibility(View.GONE);
        scrollView.setVisibility(View.GONE);
//------------------------------------Put Ads here---------------------------------------------------------------
//------------------------------------Put Ads here---------------------------------------------------------------
//------------------------------------Put Ads here---------------------------------------------------------------
//------------------------------------Put Ads here-----------
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