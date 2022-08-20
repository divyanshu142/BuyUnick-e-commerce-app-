package com.example.buyunick.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.TaskStackBuilder;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.buyunick.R;
import com.example.buyunick.adopter.Cart_Adapter;
import com.example.buyunick.databinding.ActivityCardBinding;
import com.example.buyunick.modals.product;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.model.Item;
import com.hishd.tinycart.util.TinyCartHelper;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;

public class CardActivity extends AppCompatActivity {

    ActivityCardBinding binding;
    Cart_Adapter Adapter;
    ArrayList<product> Product;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Product = new ArrayList<>();

        Cart cart = TinyCartHelper.getCart();

        for(Map.Entry<Item, Integer> item : cart.getAllItemsWithQty().entrySet()){
            product product = (product) item.getKey();
            int Quentity = item.getValue();
            product.setGetQuantity(Quentity);

            Product.add(product);

        }
        Adapter = new Cart_Adapter(this, Product, new Cart_Adapter.CartLishnar() {
            @Override
            public void OnQuantityChange() {
                binding.Prices.setText(String.format("₹ %.2f", cart.getTotalPrice()));
            }
        });



        LinearLayoutManager leyoutmaneger = new LinearLayoutManager(this);
        DividerItemDecoration decoretion = new DividerItemDecoration(this, leyoutmaneger.getOrientation());
        binding.CartList.setLayoutManager(leyoutmaneger);
        binding.CartList.addItemDecoration(decoretion);
        binding.CartList.setAdapter(Adapter);

        binding.Prices.setText(String.format("₹ %.2f", cart.getTotalPrice()));

        binding.continu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CardActivity.this, CheckoutActivity.class));
            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

}
