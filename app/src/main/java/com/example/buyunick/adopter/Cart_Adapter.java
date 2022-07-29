package com.example.buyunick.adopter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.buyunick.Activitys.CardActivity;
import com.example.buyunick.R;
import com.example.buyunick.databinding.CountityDailogeBinding;
import com.example.buyunick.databinding.ItemCartBinding;
import com.example.buyunick.modals.product;

import java.util.ArrayList;

public class Cart_Adapter extends RecyclerView.Adapter<Cart_Adapter.CardViewHolder>{

    Context context;
    ArrayList<product> products;

    public Cart_Adapter(Context context, ArrayList<product> products){
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CardViewHolder(LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
    product product = products.get(position);
        Glide.with(context)
                .load(product.getMages())
                .into(holder.binding.imges);
        holder.binding.name.setText(product.getName());
        holder.binding.prices.setText("₹ " + product.getPrice());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CountityDailogeBinding Countity = CountityDailogeBinding.inflate(LayoutInflater.from(context));
                AlertDialog alertdialog = new AlertDialog.Builder(context)
                        .setView(Countity.getRoot())
                        .create();

                        alertdialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {

        return products.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder{

        ItemCartBinding binding;
        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemCartBinding.bind(itemView);
        }
    }
}
