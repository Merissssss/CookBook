package com.example.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.example.myapplication.Activity.DetailActivity;
import com.example.myapplication.databinding.RecipesBinding;
import com.example.myapplication.domain.Domain;

import java.util.ArrayList;

public class Foods extends RecyclerView.Adapter<Foods.Recipes> {

    ArrayList<Domain> items;
    Context context;
    RecipesBinding binding;

    public Foods(ArrayList<Domain> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public Foods.Recipes onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding=RecipesBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        context = parent.getContext();
        return new Recipes(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Foods.Recipes holder, int position) {
        binding.textView.setText(items.get(position).getTitle());

        int drawableRecourse = holder.itemView.getResources()
                .getIdentifier(String.valueOf(items.get(position).getPicUrl()),
                "drawable", holder.itemView.getContext().getPackageName());

        Glide.with(context)
                .load(drawableRecourse)
                .transform(new GranularRoundedCorners(30, 30, 0, 0))
                .into(binding.imageView);

        holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("object", items.get(position));
                context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Recipes extends RecyclerView.ViewHolder {
        public Recipes(RecipesBinding binding) {
            super(binding.getRoot());
        }
    }
}
