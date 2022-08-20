package com.example.buyunick.adopter;

import android.content.Context;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.buyunick.Activitys.CategryActivity;
import com.example.buyunick.R;
import com.example.buyunick.databinding.CategryItemBinding;
import com.example.buyunick.modals.categry;

import java.util.ArrayList;


public class catagry_adopter extends RecyclerView.Adapter<catagry_adopter.catagryviewholder>{

    Context context;
    ArrayList<categry> categrys;
    public catagry_adopter (Context context, ArrayList<categry> categrys){
        this.context = context;
        this.categrys = categrys;
    }
    @NonNull
    @Override
    public catagryviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new catagryviewholder(LayoutInflater.from(context).inflate(R.layout.categry_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull catagryviewholder holder, int position) {
          categry categry = categrys.get(position);
          holder.binding.leble.setText(categry.getName());
          Glide.with(context)
                  .load(categry.getIcon())
                  .into(holder.binding.image);

          holder.binding.image.setBackgroundColor(Color.parseColor(categry.getColour()));

          holder.itemView.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent intent = new Intent(context, CategryActivity.class);
                  intent.putExtra("catId", categry.getId());
                  intent.putExtra("categryName",categry.getName());
                  context.startActivity(intent);

              }
          });
    }

    @Override
    public int getItemCount() {
        return categrys.size();
    }

    public class catagryviewholder extends RecyclerView.ViewHolder {
        CategryItemBinding binding;

        public catagryviewholder(@NonNull View itemView) {
            super(itemView);
            binding = CategryItemBinding.bind(itemView);
        }
    }
}
