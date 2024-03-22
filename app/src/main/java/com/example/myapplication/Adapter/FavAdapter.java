package com.example.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.myapplication.Activity.DetailActivity;
import com.example.myapplication.R;
import com.example.myapplication.domain.FavItem;

import java.util.List;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.FavViewHolder> {

    Context context;
    List<FavItem> favItemList;
    int layoutResId;

    public FavAdapter(Context context, List<FavItem> favItemList, int layoutResId) {
        this.context = context;
        this.favItemList = favItemList;
        this.layoutResId = layoutResId;
    }

    @NonNull
    @Override
    public FavAdapter.FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull FavAdapter.FavViewHolder holder, int position) {
        FavItem favItem = favItemList.get(position);

        Glide.with(context)
                .load(favItem.getPicUrl())
                .transform(new RoundedCorners(30))
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        holder.imageView.setBackground(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });

        holder.title.setText(favItem.getTitle());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
            intent.putExtra("object", favItem);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class FavViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView title;

        public FavViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.favImageView);
            title = itemView.findViewById(R.id.tittleText);
        }
    }
}
