package com.example.buyunick.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.provider.DocumentsContract;
import android.util.Log;
import android.widget.GridLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.buyunick.R;
import com.example.buyunick.adopter.catagry_adopter;
import com.example.buyunick.adopter.product_adoptor;
import com.example.buyunick.databinding.ActivityMainBinding;
import com.example.buyunick.modals.categry;
import com.example.buyunick.modals.product;
import com.example.buyunick.utility.Constants;

import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    catagry_adopter categryadopter;
    ArrayList<categry> categrys;

    product_adoptor prodectadopter;
    ArrayList<product> prodects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initcategory();
        Log.d("initcategory","successfully esuty");
        initproduct();
        Log.d("initproduct","successfully esuty");
        addcarasuldata();
        Log.d("addcarasuldata","successfully esuty");
    }

    private void addcarasuldata() {
        Getresentoffer();
    }

    void initcategory(){
        categrys = new ArrayList<>();
        categryadopter =  new catagry_adopter(this, categrys);

        Getcategory();

        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        binding.catagryitem.setLayoutManager(layoutManager);
        binding.catagryitem.setAdapter(categryadopter);
    }

    void Getcategory(){
        RequestQueue queue = Volley.newRequestQueue(this);
       // String url = "https://www.google.com";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.GET_CATEGORIES_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        try {
                            JSONObject mainobject = new JSONObject(response);
                            if (mainobject.getString("status").equals("success")){
                                JSONArray categouryarry = mainobject.getJSONArray("categories");
                                for(int i = 0; i < categouryarry.length(); i++){
                                    JSONObject object = categouryarry.getJSONObject(i);
                                    categry CATEGRY = new categry(
                                            object.getString("name"),
                                            Constants.CATEGORIES_IMAGE_URL+object.getString("icon"),
                                            object.getString("color"),
                                            object.getString("brief"),
                                            object.getInt("id")
                                    );
                                    categrys.add(CATEGRY);
                                }
                                  categryadopter.notifyDataSetChanged();
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

    void Getresentproduct(){
        RequestQueue queue = Volley.newRequestQueue(this);

// Request a string response from the provided URL.
        String url = Constants.GET_PRODUCTS_URL+"?count=8";
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

    void Getresentoffer(){
        RequestQueue queue = Volley.newRequestQueue(this);
        // String url = "https://www.google.com";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.GET_OFFERS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        try {
                            JSONObject mainobject = new JSONObject(response);
                            if (mainobject.getString("status").equals("success")){
                                JSONArray offerarry = mainobject.getJSONArray("news_infos");
                                for(int i = 0; i < offerarry.length(); i++){
                                    JSONObject object = offerarry.getJSONObject(i);
                                     binding.carousel.addData(

                                     new CarouselItem(

                                            Constants.NEWS_IMAGE_URL+object.getString("image"),
                                            object.getString("title")
                                    )

                                    );

                                }

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

    void initproduct(){
        prodects = new ArrayList<>();
        prodectadopter = new product_adoptor(this, prodects);

        Getresentproduct();

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        binding.pridectitem.setLayoutManager(layoutManager);
        binding.pridectitem.setAdapter(prodectadopter);
    }
}