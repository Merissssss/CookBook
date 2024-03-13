package com.example.myapplication.Activity;

///Coffee Adapter

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    private ArrayList<FoodItem> foodItems;
    private Context context;
    private FavList favList;

    public FoodAdapter(ArrayList<FoodItem> foodItems, Context context) {
        this.foodItems = foodItems;
        this.context = context;
    }

    @NonNull
    @Override
    public FoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        favList = new FavList(context);

        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);
        if (firstStart) {
            createTableOnFirstStart();
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipes, parent, false);

        return new ViewHolder(view);
    }

    private void createTableOnFirstStart() {
        favList.insertEmpty();
        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }

    @Override
    public void onBindViewHolder(@NonNull FoodAdapter.ViewHolder holder, int position) {
        final FoodItem foodItem = foodItems.get(position);
        readCursorData(foodItem, holder);
        holder.imageView.setImageResource(foodItem.getImageRecourse());
        holder.tittleTextView.setText(foodItem.getTittle());
    }

    private void readCursorData(FoodItem foodItem, ViewHolder viewHolder) {
        Cursor cursor = favList.read_all_data(foodItem.getKey_id());
        SQLiteDatabase db = favList.getReadableDatabase();
        try {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String item_fav_status = cursor.getString(cursor.getColumnIndex(FavList.FAVORITE_STATUS));

                if (item_fav_status != null && item_fav_status.equals("1")) {
                    viewHolder.favBtn.setBackgroundResource(R.drawable.liked);
                } else if (item_fav_status != null && item_fav_status.equals("0")) {
                    viewHolder.favBtn.setBackgroundResource(R.drawable.notliked);
                }
            }
        } finally {
            if (cursor != null && cursor.isClosed())
                cursor.close();
            db.close();
        }
    }

    @Override
    public int getItemCount() {
        return foodItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView tittleTextView;
        ImageView favBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.favImageView);
            tittleTextView = itemView.findViewById(R.id.tittleText);
            favBtn = itemView.findViewById(R.id.imageView2);

            favBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    FoodItem foodItem = foodItems.get(position);

                    if (foodItem.getFavStatus().equals("0")) {
                        foodItem.setFavStatus("1");
                        favList.insertIntoTheDatabase(foodItem.getTittle(), foodItem.getImageRecourse(),
                                foodItem.getKey_id(), foodItem.getFavStatus());
                        favBtn.setImageResource(R.drawable.liked);
                    } else {
                        foodItem.setFavStatus("0");
                        favList.remove_fav(foodItem.getKey_id());
                        favBtn.setBackgroundResource(R.drawable.notliked);
                    }
                }
            });

        }
    }
}
