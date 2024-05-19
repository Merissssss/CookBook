package com.example.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.example.myapplication.Activity.DetailActivity;
import com.example.myapplication.databinding.RecipesBinding;
import com.example.myapplication.domain.AddRecipeModel;

import java.util.ArrayList;
import java.util.List;

public class Foods extends RecyclerView.Adapter<Foods.RecipesViewHolder> {

    private List<AddRecipeModel> recipes;
    private Context context;

    public Foods(List<AddRecipeModel> recipes) {
        this.recipes = recipes != null ? recipes : new ArrayList<>();
    }

    @NonNull
    @Override
    public RecipesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RecipesBinding binding = RecipesBinding.inflate(layoutInflater, parent, false);
        context = parent.getContext(); // Set context when creating the view holder
        return new RecipesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesViewHolder holder, int position) {
        AddRecipeModel item = recipes.get(position);

        Glide.with(context)
                .load(item.getImageAlpha()) // Assuming getImageAlpha() returns the URL of the image in Firestore
                .transform(new GranularRoundedCorners(30, 30, 0, 0))
                .into(holder.binding.imageView);

        holder.binding.textView.setText(item.getName());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("recipeId", item); // Pass the recipe object
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public void updateList(List<AddRecipeModel> newRecipes) {
        recipes = newRecipes != null ? newRecipes : new ArrayList<>();
        notifyDataSetChanged();
    }

    public static class RecipesViewHolder extends RecyclerView.ViewHolder {
        public RecipesBinding binding;

        public RecipesViewHolder(RecipesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
