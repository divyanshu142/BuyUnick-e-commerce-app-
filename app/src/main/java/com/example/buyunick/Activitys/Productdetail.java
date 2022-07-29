package com.example.buyunick.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.buyunick.R;
import com.example.buyunick.databinding.ActivityProductdetailBinding;
import com.example.buyunick.utility.Constants;

import org.json.JSONException;
import org.json.JSONObject;

public class Productdetail extends AppCompatActivity {

    ActivityProductdetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductdetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String name  = getIntent().getStringExtra("name");
        String image  = getIntent().getStringExtra("image");
        int id  = getIntent().getIntExtra("id", 0);
        Double  price  = getIntent().getDoubleExtra("price",0);


        Glide.with(this)
                .load(image)
                .into(binding.productimage);

        Getproductdatail(id);
        getSupportActionBar().setTitle(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.card, menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.cart) {
            startActivity(new Intent(this, CardActivity.class));
           // return true;
        }
        return super.onOptionsItemSelected(item);
    }


    void Getproductdatail(int id){

// ...

// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

// Request a string response from the provided URL.
        String url = Constants.GET_PRODUCT_DETAILS_URL+id;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        try {
                            JSONObject object = new JSONObject(response);
                            if(object.getString("status").equals("success")){
                                JSONObject product = object.getJSONObject("product");
                                String descreption = product.getString("description");
                                binding.productdiscription.setText(
                                        Html.fromHtml(descreption)
                                );
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

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

}