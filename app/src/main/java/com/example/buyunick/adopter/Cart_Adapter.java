package com.example.buyunick.adopter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.buyunick.R;
import com.example.buyunick.databinding.CountityDailogeBinding;
import com.example.buyunick.databinding.ItemCartBinding;
import com.example.buyunick.modals.product;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.util.TinyCartHelper;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class Cart_Adapter extends RecyclerView.Adapter<Cart_Adapter.CardViewHolder>{

    Context context;
    ArrayList<product> products;
    CartLishnar cardlishnar;
    Cart cart;
    public interface CartLishnar{
        public void OnQuantityChange();
    }

    public Cart_Adapter(Context context, ArrayList<product> products, CartLishnar cartLishnar){
        this.context = context;
        this.products = products;
        this.cardlishnar = cartLishnar;
        cart = TinyCartHelper.getCart();
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
        holder.binding.couantity.setText(product.getGetQuantity() + " item(s)");


        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                CountityDailogeBinding Qountity = CountityDailogeBinding.inflate(LayoutInflater.from(context));
                AlertDialog alertdialog = new AlertDialog.Builder(context)
                        .setView(Qountity.getRoot())
                        .create();

                alertdialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));

                Qountity.prodctnamess.setText(product.getName());
                Qountity.stokss.setText("stock: "+ product.getStoke());
                Qountity.quanity.setText(product.getGetQuantity() + "");

                int stok = product.getStoke();


                Qountity.plasbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int quantity = product.getGetQuantity();
                           quantity++;
                           if (quantity > product.getStoke()){
                               Toast.makeText(context, "Unavailable Stock", Toast.LENGTH_SHORT).show();
                               return;
                           }
                           product.setGetQuantity(quantity);
                           Qountity.quanity.setText("" + quantity);
                        notifyDataSetChanged();
                        cart.updateItem(product, product.getGetQuantity());
                        cardlishnar.OnQuantityChange();

                    }
                });

                Qountity.minasbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int quantity = product.getGetQuantity();
                        if(quantity > 1)
                            quantity--;
                        product.setGetQuantity(quantity);
                        Qountity.quanity.setText("" + quantity);
                        notifyDataSetChanged();
                        cart.updateItem(product, product.getGetQuantity());
                        cardlishnar.OnQuantityChange();

                    }
                });



                Qountity.savess.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertdialog.dismiss();

                    }
                });
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
