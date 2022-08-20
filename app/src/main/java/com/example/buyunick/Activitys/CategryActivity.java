package com.example.buyunick.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.buyunick.adopter.product_adoptor;
import com.example.buyunick.databinding.ActivityCategryBinding;
import com.example.buyunick.modals.product;
import com.example.buyunick.utility.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CategryActivity extends AppCompatActivity {


    ActivityCategryBinding binding;
    product_adoptor prodectadopter;
    ArrayList<product> prodects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityCategryBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());


        prodects = new ArrayList<>();
        prodectadopter = new product_adoptor(this, prodects);


        int catId = getIntent().getIntExtra("catId",1) ;
        String categryName = getIntent().getStringExtra("categryName") ;

        getSupportActionBar().setTitle(categryName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Getproduct(catId);



        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        binding.productList.setLayoutManager(layoutManager);
        binding.productList.setAdapter(prodectadopter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    void Getproduct(int catId){
        RequestQueue queue = Volley.newRequestQueue(this);

// Request a string response from the provided URL.   "?category_id=" + catId;
        String url = Constants.GET_PRODUCTS_URL+ "?category_id=" + catId;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        try {
                            JSONObject mainobject = new JSONObject(response);
                            if (mainobject.getString("status").equals("success")){
                                JSONArray productarry = mainobject.getJSONArray("products");
                                for(int i = 0; i < productarry.length(); i++){
                                    JSONObject object = productarry.getJSONObject(i);
                                    product PRODUCT = new product(
                                            object.getString("name"),
                                            Constants.PRODUCTS_IMAGE_URL+object.getString("image"),
                                            object.getString("status"),
                                            object.getDouble("price"),
                                            object.getDouble("price_discount"),
                                            object.getInt("stock"),
                                            object.getInt("id")
                                    );
                                    prodects.add(PRODUCT);
                                }
                                prodectadopter.notifyDataSetChanged();
                            }else{
                                // do nothing
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}