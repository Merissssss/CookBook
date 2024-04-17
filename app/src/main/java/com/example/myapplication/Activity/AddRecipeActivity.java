package com.example.myapplication.Activity;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.Adapter.ColorSizeAdapter;
import com.example.myapplication.R;
import com.example.myapplication.Category.Constants;
import com.example.myapplication.domain.Domain;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.example.myapplication.databinding.ActivityAddRecipeBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddRecipeActivity extends AppCompatActivity {

    private ActivityAddRecipeBinding binding;
    private final int PICK_IMAGE_REQUEST = 1;
    private static final String[] categories = Constants.categories;
    private Domain productModel;
    private FirebaseFirestore db;
    private Uri imagePath;
    private StorageReference storageReference;
    private int selectedCategory = -1;
    private ColorSizeAdapter colorAdapter;
    private List<EditText> editTextList = new ArrayList<>();
    private double latitude = -91;
    private double longitude = 181;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddRecipeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initGlobalFields();
        initListeners();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imagePath = data.getData();
            getIntent().putExtra("imagePath", imagePath.toString());
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                binding.productPhoto.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void initGlobalFields() {
        db = FirebaseFirestore.getInstance();
        imagePath = null;
        productModel = new Domain();
        productModel.setRecipe(db.collection("UnconfirmedProducts").document().getId());
        List<EditText> colorItems = new ArrayList<>();
        binding.colorRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        colorAdapter = new ColorSizeAdapter(colorItems);
        binding.colorRecyclerView.setAdapter(colorAdapter);
    }

    private void initListeners() {
        binding.CategoryView.setOnClickListener(v -> selectCategory());
        binding.productPhoto.setOnClickListener(v -> chooseImage());
        binding.btnDone.setOnClickListener(v -> submitProduct());
        binding.moreColors.setOnClickListener(v -> {
            if (binding.moreColorDetails.getVisibility() == View.VISIBLE) {
                binding.moreColorDetails.setVisibility(View.GONE);
            } else {
                binding.moreColorDetails.setVisibility(View.VISIBLE);
            }
        });
        binding.addItem.setOnClickListener(v -> {
            EditText newEditText = new EditText(this);
            colorAdapter.addItem(newEditText);
            editTextList.add(newEditText);
        });
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select product image"), PICK_IMAGE_REQUEST);
    }

    private void selectCategory() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select category");
        builder.setSingleChoiceItems(categories, selectedCategory, (dialogInterface, i) -> {
            binding.CategoryView.setText(categories[i]);
            selectedCategory = i;
        });

        builder.setNegativeButton("OK", null);
        builder.show();
    }

    private void submitProduct() {
        String productName = binding.productName.getText().toString();
        String category = binding.CategoryView.getText().toString();
        String details = binding.productDetails.getText().toString();

//
//        dialogHud = new DialogHud(requireContext())
//                .setMode(DialogHud.Mode.LOADING)
//                .setLabel(R.string.uploading)
//                .setLabelDetail(R.string.please_wait)
//                .setCancelable(false);


        if (productName.isEmpty()) {
            showToast(getString(R.string.product_name_can_t_be_empty));
            return;
        }

        if (category.isEmpty()) {
            showToast(getString(R.string.you_must_select_product_category));
            selectCategory();
            return;
        }


        if (details.isEmpty()) {
            showToast(getString(R.string.you_must_enter_product_details));
            return;
        }

        String imagePathStr = getIntent().getStringExtra("imagePath");
        if (imagePathStr != null) {
            imagePath = Uri.parse(imagePathStr);
            if (imagePath == null) {
                showToast(getString(R.string.you_must_select_product_photo));
                return;
            }
        }

        productModel.setTitle(productName);
        productModel.setCategory(category);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            productModel.setSeller(currentUser.getDisplayName());
            productModel.setSellerId(currentUser.getUid());
        }

        storageReference = FirebaseStorage.getInstance().getReference();

        uploadToStorage();

        Toast.makeText(getApplicationContext(), "Product submitted successfully", Toast.LENGTH_SHORT).show();

        finish();
    }
    private void uploadToStorage() {
        storageReference.child("products/" + productModel.getProductId()).putFile(imagePath)
                .addOnSuccessListener(taskSnapshot -> storageReference.child("products/" + productModel.getProductId()).getDownloadUrl()
                        .addOnSuccessListener(uri -> {
                            productModel.setPhoto(uri.toString());
                            uploadToFirestore();
                        }));
    }

    private void uploadToFirestore() {
        db.collection("UnconfirmedProducts").document(productModel.getProductId()).set(productModel)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getApplicationContext(), "Product uploaded successfully", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getApplicationContext(), "Failed to upload product: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }


    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
