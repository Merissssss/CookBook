package com.example.myapplication.Activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentFavBinding;

 public class FavFragment extends Fragment {

     private static final String TAG_FAVORITE = "Favorite: ";
     FragmentFavBinding binding;
     private RecyclerView recyclerView;


     @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_fav, container, false);

         return view;
     }
 }

