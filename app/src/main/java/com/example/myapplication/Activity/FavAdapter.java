package com.example.myapplication.Activity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder> {
    private Context context;
    private List<FavItem> favItemList;
    private FavList favDB;

    public FavAdapter(Context context, List<FavItem> favItemList) {
        this.context = context;
        this.favItemList = favItemList;
        this.favDB = new FavList(context); // Make sure context is valid
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.favTextView.setText(favItemList.get(position).getItem_tittle());
        holder.favImageView.setImageResource(favItemList.get(position).getItem_image());
    }

    @Override
    public int getItemCount() {
        return favItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView favTextView;
        ImageView favBtn;
        ImageView favImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            favTextView = itemView.findViewById(R.id.favTextView);
            favBtn = itemView.findViewById(R.id.favBtn);
            favImageView = itemView.findViewById(R.id.favImageView);

            favBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final FavItem favItem = favItemList.get(position);
                    favDB.remove_fav(favItem.getKey_id());
                    removeItem(position);
                    Log.d("FavAdapter", "Item removed at position: " + position);
                }

                private void removeItem(int position) {
                    favItemList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemChanged(position, favItemList.size());
                    Log.d("FavAdapter", "Item list updated after removal");
                }
            });
        }
    }
}
