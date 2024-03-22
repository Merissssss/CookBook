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

import com.example.myapplication.Adapter.FavAdapter;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentFavBinding;
import com.example.myapplication.databinding.FragmentHomeBinding;
import com.example.myapplication.domain.FavItem;

import java.util.ArrayList;
import java.util.List;
 public class FavFragment extends Fragment {

     private static final String TAG_FAVORITE = "Favorite: ";
     FragmentFavBinding binding;
     FavAdapter favAdapter;
     List<FavItem> favItemList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fav, container, false);

        binding = FragmentFavBinding.inflate(inflater, container, false);

        initGlobalFields();
        fetchUserFavorites();

        return view;
    }

     private void fetchUserFavorites() {
     }

     private void initGlobalFields() {

     }

 }

