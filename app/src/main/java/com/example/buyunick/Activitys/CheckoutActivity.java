package com.example.buyunick.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.buyunick.adopter.Cart_Adapter;
import com.example.buyunick.databinding.ActivityCheckoutBinding;
import com.example.buyunick.modals.product;
import com.example.buyunick.utility.Constants;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.model.Item;
import com.hishd.tinycart.util.TinyCartHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CheckoutActivity extends AppCompatActivity {


    ActivityCheckoutBinding binding;
    Cart_Adapter Adapter;
    ArrayList<product> Product;
    double totalprice = 0;
    final int tax = 5;
    ProgressDialog progressDilogs;
    Cart cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDilogs = new ProgressDialog(this);
        progressDilogs.setCancelable(false);
        progressDilogs.setMessage("Processing...");


        Product = new ArrayList<>();

        cart = TinyCartHelper.getCart();

        for (Map.Entry<Item, Integer> item : cart.getAllItemsWithQty().entrySet()) {
            product product = (product) item.getKey();
            int Quentity = item.getValue();
            product.setGetQuantity(Quentity);

            Product.add(product);

        }
        Adapter = new Cart_Adapter(this, Product, new Cart_Adapter.CartLishnar() {
            @Override
            public void OnQuantityChange() {
                binding.subtotalrups.setText(String.format("₹ %.2f", cart.getTotalPrice()));
            }
        });


        LinearLayoutManager leyoutmaneger = new LinearLayoutManager(this);
        DividerItemDecoration decoretion = new DividerItemDecoration(this, leyoutmaneger.getOrientation());
        binding.CartList.setLayoutManager(leyoutmaneger);
        binding.CartList.addItemDecoration(decoretion);
        binding.CartList.setAdapter(Adapter);

        binding.subtotalrups.setText(String.format("₹ %.2f", cart.getTotalPrice()));

        totalprice = (cart.getTotalPrice().doubleValue() * tax / 100) + cart.getTotalPrice().doubleValue();
        binding.totalrupes.setText("₹ " + totalprice);

        binding.ProcessChackout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProcessOrder();
            }

        });

       getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }



    void ProcessOrder() {
        progressDilogs.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject productOder = new JSONObject();
        JSONObject dataobject = new JSONObject();
        try {
            productOder.put("address", binding.adressbox.getText().toString());
            productOder.put("buyer", binding.namebox.getText().toString());
            productOder.put("comment", binding.commentbox.getText().toString());
            productOder.put("created_at", Calendar.getInstance().getTimeInMillis());
            productOder.put("last_update", Calendar.getInstance().getTimeInMillis());
            productOder.put("date_ship", Calendar.getInstance().getTimeInMillis());
            productOder.put("email", binding.emailbox.getText().toString());
            productOder.put("phone", binding.phonebox.getText().toString());
            productOder.put("serial", "cab8c1a4e4421a3b");
            productOder.put("shipping", "");
            productOder.put("shipping_rate", "0.0");
            productOder.put("shipping_location", "");
            productOder.put("status", "WAITING");
            productOder.put("tax", tax);
            productOder.put("total_fees", totalprice);

           // Log.e("ppp", dataobject.toString());

            JSONArray product_order_detail = new JSONArray();

            for (Map.Entry<Item, Integer> item : cart.getAllItemsWithQty().entrySet()) {
                product product = (product) item.getKey();
                int Quentity = item.getValue();
                product.setGetQuantity(Quentity);

                JSONObject productobj = new JSONObject();
                productobj.put("amount", Quentity);
                productobj.put("price_item", product.getPrice());
                productobj.put("product_id", product.getId());
                productobj.put("product_name", product.getName());
                product_order_detail.put(productobj);
            }

            dataobject.put("product_order", productOder);
            dataobject.put("product_order_detail", product_order_detail);

            Log.e("err", dataobject.toString());

        } catch (JSONException e) {
        }
        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.POST, Constants.POST_ORDER_URL, dataobject, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                     try {

                     if(response.getString("status").equals("success")) {
                         Toast.makeText(CheckoutActivity.this, "Success Order", Toast.LENGTH_SHORT).show();
                         String Oredernumber = response.getJSONObject("data").getString("code");
                         new AlertDialog.Builder(CheckoutActivity.this)
                                        .setTitle("Order Success")
                                        .setCancelable(false)
                                        .setMessage("Your Order Number Is : " + Oredernumber)
                                        .setPositiveButton("Pay Now", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Intent intent = new Intent(CheckoutActivity.this, PaymentActivity.class);
                                                intent.putExtra("OrderCode", Oredernumber);
                                                startActivity(intent);
                                            }
                                        }).show();
                         Log.e("res", response.toString());
                     }else {
                         new AlertDialog.Builder(CheckoutActivity.this)
                                 .setTitle("Order Faild")
                                 .setCancelable(false)

                                 .setMessage("Something went wrong plz try again")
                                 .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                                     @Override
                                     public void onClick(DialogInterface dialog, int which) {

                                     }
                                 }).show();
                         Toast.makeText(CheckoutActivity.this, "Faild Order", Toast.LENGTH_SHORT).show();

                     }
                         progressDilogs.dismiss();
                         Log.e("res", response.toString());
                     }catch (Exception e){}
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> Headers = new HashMap<>();
                Headers.put("Security","secure_code");
                return Headers;
            }
        };

// Access the RequestQueue through your singleton class.
        queue.add(request);

    }
        @Override
        public boolean onSupportNavigateUp () {
            finish();
            return super.onSupportNavigateUp();
        }
    }
