package com.sarkstechsolution.groceryhouse.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sarkstechsolution.groceryhouse.Interfaces.CheckCart;
import com.sarkstechsolution.groceryhouse.LoginActivity;
import com.sarkstechsolution.groceryhouse.Models.Products;
import com.sarkstechsolution.groceryhouse.R;

import java.util.HashMap;

public class CartItemAdapter extends FirebaseRecyclerAdapter<Products, CartItemAdapter.CartItemViewHolder> {
    private CheckCart listener;
    private Context context;

    public CartItemAdapter(@NonNull FirebaseRecyclerOptions<Products> options, CheckCart listener, Context context) {
        super(options);
        this.listener = listener;
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull CartItemAdapter.CartItemViewHolder holder, int position, @NonNull Products model) {

        float cp = Float.parseFloat(model.getMrp().toString());
        float sp = Float.parseFloat(model.getSp().toString());

        int discount =Math.round(((sp-cp)/cp)*100);

        holder.Name.setText(model.getName());
//        holder.Mrp.setText("\u20B9" + model.getMrp());
//        holder.Mrp.setPaintFlags(holder.Mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.Sp.setText("\u20B9" + model.getSp());
        holder.Unit.setText(model.getUnit());
        if(discount > 0) {
            holder.perDiscount.setText(discount + "%off");
        }
        else if(discount <=0) {
            holder.perDiscount.setVisibility(View.GONE);
        }

        Glide.with(holder.Pimage.getContext())
                .load(model.getImage()) // image url
                .placeholder(R.drawable.chicken) // any placeholder to load at start
//                .error(R.drawable.ic_baseline_image_24)  // any image in case of error
//                .override(100, 100) // resizing
//                .optionalFitCenter()
                .into(holder.Pimage);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null)
        {
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("Cart").child(model.getPid());
            cartRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                       @Override
                                                       public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                           if (snapshot.exists()) {
                                                               int qty = Integer.parseInt(snapshot.child("quantity").getValue().toString());

                                                               holder.quant.setText(String.valueOf(qty));
                                                               holder.productQuantity = qty;

                                                               holder.addProductToCart.setVisibility(View.GONE);
                                                               holder.layout.setVisibility(View.VISIBLE);
                                                           }
                                                           else
                                                           {
                                                               int qty =0;
                                                               holder.quant.setText(String.valueOf(qty));
                                                               holder.productQuantity = qty;

                                                           }
                                                       }

                                                       @Override
                                                       public void onCancelled(@NonNull DatabaseError error) {

                                                       }
                                                   }
            );
        }else
        {
           return;
        }


//        holder.addProductToCart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                if(user == null)
//                {
//
//                    Intent intent = new Intent(context, LoginActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    context.startActivity(intent);
//                }
//                else
//                {
//                    holder.productQuantity++;
//
//                    HashMap<String, Object> cartItemMap = new HashMap<>();
//                    cartItemMap.put("name",model.getName());
//                    cartItemMap.put("mrp",model.getMrp());
//                    cartItemMap.put("sp",model.getSp());
//                    cartItemMap.put("pid",model.getPid());
//                    cartItemMap.put("sid",model.getSid());
//                    cartItemMap.put("unit",model.getUnit());
//                    cartItemMap.put("description",model.getDescription());
//                    cartItemMap.put("category",model.getCategory());
//                    cartItemMap.put("quantity",holder.productQuantity);
//                    cartItemMap.put("image",model.getImage());
//
//                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
//
//                    FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("Cart").child(model.getPid()).updateChildren(cartItemMap)
//                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//
//                                    if (task.isSuccessful())
//                                    {
//
//                                        holder.quant.setText(String.valueOf(holder.productQuantity));
//
//                                        holder.addProductToCart.setVisibility(View.GONE);
//                                        holder.layout.setVisibility(View.VISIBLE);
//
//                                        Toast.makeText(context, "added", Toast.LENGTH_SHORT).show();
//                                    }
//                                    else
//                                    {
//                                        String message = task.getException().toString();
//                                        Toast.makeText(context, "Error:"+message, Toast.LENGTH_SHORT).show();
//                                    }
//
//                                }
//                            });
//
//                }
//
//            }
//        });

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.productQuantity == 9)
                {
                    holder.plus.setEnabled(false);
                }else {


                    holder.productQuantity++;

                    HashMap<String, Object> cartItemMap = new HashMap<>();
                    cartItemMap.put("quantity", holder.productQuantity);

                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                    FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("Cart").child(model.getPid()).updateChildren(cartItemMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                        holder.quant.setText(String.valueOf(holder.productQuantity));


                                        Toast.makeText(context, "added", Toast.LENGTH_SHORT).show();
                                    } else {
                                        String message = task.getException().toString();
                                        Toast.makeText(context, "Error:" + message, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
            }
        });

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.productQuantity < 9)
                {
                    holder.plus.setEnabled(true);
                }
                if (holder.productQuantity == 1)
                {
                    listener.EmptyCart();
                    holder.productQuantity--;
                    holder.quant.setText(String.valueOf(holder.productQuantity));
                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                    FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("Cart").child(model.getPid()).removeValue();
                    holder.addProductToCart.setVisibility(View.VISIBLE);
                    holder.layout.setVisibility(View.INVISIBLE);
                }
                else {
                    holder.productQuantity--;

                    HashMap<String, Object> cartItemMap = new HashMap<>();
                    cartItemMap.put("quantity", holder.productQuantity);

                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                    FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("Cart").child(model.getPid()).updateChildren(cartItemMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                        holder.quant.setText(String.valueOf(holder.productQuantity));


                                        Toast.makeText(context, "added", Toast.LENGTH_SHORT).show();
                                    } else {
                                        String message = task.getException().toString();
                                        Toast.makeText(context, "Error:" + message, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

    }

    @NonNull
    @Override
    public CartItemAdapter.CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cartitem, parent, false);
        return new CartItemViewHolder(view);
    }

    public class CartItemViewHolder extends RecyclerView.ViewHolder {
        TextView Name, Mrp, Sp, perDiscount, Unit, quant;
        ImageView Pimage;
        Button addProductToCart, plus,minus;
        LinearLayout layout;
        int productQuantity=0;

        public CartItemViewHolder(@NonNull View itemView) {
            super(itemView);

            Name = itemView.findViewById(R.id.pName);
            Mrp = itemView.findViewById(R.id.mrp);
            Sp = itemView.findViewById(R.id.sp);
            perDiscount = itemView.findViewById(R.id.Off);
            Unit = itemView.findViewById(R.id.pUnit);
            addProductToCart = itemView.findViewById(R.id.addtoCartBtn);
            Pimage = itemView.findViewById(R.id.pImg);
            plus = itemView.findViewById(R.id.plusP);
            minus = itemView.findViewById(R.id.minusP);
            quant = itemView.findViewById(R.id.quantity);
            layout = itemView.findViewById(R.id.qControl);
        }
    }
}
