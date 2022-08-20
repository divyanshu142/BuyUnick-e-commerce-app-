package com.example.buyunick.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.buyunick.databinding.ActivityPaymentBinding;
import com.example.buyunick.utility.Constants;

public class PaymentActivity extends AppCompatActivity {

    ActivityPaymentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String OrderCode = getIntent().getStringExtra("OrderCode");

         binding.webview.setMixedContentAllowed(true);
         binding.webview.loadUrl(Constants.PAYMENT_URL + OrderCode);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}