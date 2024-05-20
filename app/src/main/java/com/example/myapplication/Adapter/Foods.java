package com.example.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.example.myapplication.Activity.DetailActivity;
import com.example.myapplication.R;
import com.example.myapplication.databinding.RecipesBinding;
import com.example.myapplication.domain.AddRecipeModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.UUID;

public class Foods extends RecyclerView.Adapter<Foods.RecipesViewHolder> {

    private ArrayList<AddRecipeModel> items;
    private Context context;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    public Foods(ArrayList<AddRecipeModel> items) {
        this.items = items;
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public RecipesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RecipesBinding binding = RecipesBinding.inflate(layoutInflater, parent, false);
        context = parent.getContext();
        return new RecipesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesViewHolder holder, int position) {
        AddRecipeModel item = items.get(position);

        // Load image with Glide, handling null case
        if (item.getImageAlpha() != null) {
            Glide.with(context)
                    .load(item.getImageAlpha())
                    .transform(new GranularRoundedCorners(30, 30, 0, 0))
                    .into(holder.binding.imageView);
        } else {
            holder.binding.imageView.setImageResource(R.drawable.favbtn); // Use a placeholder image
        }

        // Set recipe name, handling null case
        holder.binding.textView.setText(item.getName() != null ? item.getName() : "No Name");

        // Set click listener for the item to open DetailActivity
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("recipe", item);
            context.startActivity(intent);
        });

        // Set like button state based on whether the item is liked
        holder.binding.like.setImageResource(item.isLiked() ? R.drawable.favbtn : R.drawable.notliked);

        // Set click listener for the like button to add/remove recipe from favorites
        holder.binding.like.setOnClickListener(v -> {
            if (item.isLiked()) {
                removeRecipeFromFavorites(item);
                item.setLiked(false);
            } else {
                addRecipeToFavorites(item);
                item.setLiked(true);
            }
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateList(ArrayList<AddRecipeModel> newItems) {
        this.items = newItems != null ? newItems : new ArrayList<>();
        notifyDataSetChanged();
    }

    private void addRecipeToFavorites(AddRecipeModel recipe) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String productId = recipe.getProductId();

        if (productId == null) {
            productId = UUID.randomUUID().toString();
            recipe.setProductId(productId);
        }

        DocumentReference favoriteRef = db.collection("users").document(userId)
                .collection("favorites").document(productId);

        recipe.setLiked(true);

        favoriteRef.set(recipe).addOnSuccessListener(aVoid -> {}).addOnFailureListener(e -> {
            Log.e("Foods", "Error adding recipe to favorites", e);
        });
    }

    private void removeRecipeFromFavorites(AddRecipeModel recipe) {
        String userId = mAuth.getCurrentUser().getUid();
        String productId = recipe.getProductId();

        if (productId == null) {
            return;
        }

        DocumentReference favoriteRef = db.collection("users").document(userId)
                .collection("favorites").document(productId);

        recipe.setLiked(false);

        favoriteRef.delete().addOnSuccessListener(aVoid -> {}).addOnFailureListener(e -> {
            Log.e("Foods", "Error removing recipe from favorites", e);
        });
    }

    public static class RecipesViewHolder extends RecyclerView.ViewHolder {
        public RecipesBinding binding;

        public RecipesViewHolder(RecipesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
