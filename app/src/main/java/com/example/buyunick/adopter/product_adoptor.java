package com.example.buyunick.adopter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.buyunick.Activitys.Productdetail;
import com.example.buyunick.R;
import com.example.buyunick.databinding.ItemProductBinding;
import com.example.buyunick.modals.product;

import java.util.ArrayList;

public class product_adoptor extends RecyclerView.Adapter<product_adoptor.productviewholder>{

    Context contexts;
    ArrayList<product> products;

    public product_adoptor(Context context, ArrayList<product> product){
        this.contexts = context;
        this.products = product;
    }

    @NonNull
    @Override
    public productviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new productviewholder(LayoutInflater.from(contexts).inflate(R.layout.item_product, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull productviewholder holder, int position) {
        product product = products.get(position);
        Glide.with(contexts)
                .load(product.getMages())
                .into(holder.binding.imagess);
        holder.binding.leble.setText(product.getName());
        holder.binding.price.setText("₹ " + product.getPrice());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(contexts, Productdetail.class);
                intent.putExtra("name", product.getName());
                intent.putExtra("image", product.getMages());
                intent.putExtra("id", product.getId());
                intent.putExtra("price", product.getPrice());
                contexts.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class productviewholder extends RecyclerView.ViewHolder{

        ItemProductBinding binding;

        public productviewholder(@NonNull View itemView) {
            super(itemView);

            binding = ItemProductBinding.bind(itemView);
        }
    }
}
