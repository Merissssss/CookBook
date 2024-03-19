package com.example.myapplication.Activity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;
 public class FavFragment extends Fragment {

    private RecyclerView recyclerView;
    private FavList favDB;
    private List<FavItem> favItemList = new ArrayList<>();
    private FavAdapter adapter;
    FragmentHomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fav, container, false);

        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Initialize the adapter only once
        adapter = new FavAdapter(getActivity(), favItemList);
        recyclerView.setAdapter(adapter);

        // Initialize the FavList instance
        favDB = new FavList(getActivity());

        // Load liked recipes from the database
        loadLikedRecipes();

        return view;
    }

     private void loadLikedRecipes() {
         if (favItemList != null) {
             favItemList.clear();
         }
         Cursor cursor = favDB.select_all_favorite_list();
         try {
             if (cursor != null) {
                 int titleIndex = cursor.getColumnIndex(FavList.ITEM_TITTLE);
                 int idIndex = cursor.getColumnIndex(FavList.KEY_ID);
                 int imageIndex = cursor.getColumnIndex(FavList.ITEM_IMAGE);

                 while (cursor.moveToNext()) {
                     // Check if the column indices are valid
                     if (titleIndex != -1 && idIndex != -1 && imageIndex != -1) {
                         String title = cursor.getString(titleIndex);
                         String id = cursor.getString(idIndex);
                         int image = cursor.getInt(imageIndex);

                         FavItem favItem = new FavItem(title, id, image);
                         favItemList.add(favItem);
                     } else {
                         // Handle the case when the column indices are invalid
                         // For example, log an error message or take appropriate action
                     }
                 }
             }
         } finally {
             if (cursor != null && !cursor.isClosed()) {
                 cursor.close();
             }
         }
         // Notify the adapter that the data set has changed
         adapter.notifyDataSetChanged();
     }

 }

