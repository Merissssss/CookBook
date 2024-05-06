package com.example.myapplication.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityAddRecipeBinding;
import com.example.myapplication.domain.AddRecipeModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

public class AddRecipeActivity extends AppCompatActivity {
    ActivityAddRecipeBinding binding;
    private static final String TAG = "YourFragment";
    private static final int PICK_IMAGE_REQUEST = 1;
    private FirebaseFirestore db;
    private TextView name;
    private TextView desc;
    private ImageButton addBtn;
    private String[] categories = {"Breakfast", "Lunch", "Dinner", "Desert"};

    private int selectedCategory = 0;

    private TextView category;
    private ImageView productPhotoImageView;
    private StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddRecipeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference();

        name = findViewById(R.id.product_name);
        desc = findViewById(R.id.product_details);
        category = findViewById(R.id.category_view);
        addBtn = findViewById(R.id.btn_done);
        addBtn.setOnClickListener(v -> {
            saveDataToFirestore();
        });

        productPhotoImageView = findViewById(R.id.product_photo);
        productPhotoImageView.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });
        category.setOnClickListener(v -> {
            selectCategory();
        });
    }

    private void saveDataToFirestore() {
        if (productPhotoImageView.getDrawable() == null) {
            Toast.makeText(AddRecipeActivity.this, "Please select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ((BitmapDrawable) productPhotoImageView.getDrawable()).getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageData = baos.toByteArray();

        String imageName = UUID.randomUUID().toString() + ".jpg";
        String imagePath = "images/" + imageName;

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference imageRef = storage.getReference().child(imagePath);


        imageRef.putBytes(imageData)
                .addOnSuccessListener(taskSnapshot -> {
                    imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        String imageUrl = uri.toString();
                        Log.d(TAG, "Image uploaded. URL: " + imageUrl);

                        Toast.makeText(AddRecipeActivity.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();

                        saveDataToFirestore(imageUrl);
                    });
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error uploading image", e);
                    Toast.makeText(AddRecipeActivity.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                });
    }

    private void saveDataToFirestore(String imageUrl) {
        AddRecipeModel product = new AddRecipeModel(
                name.getText().toString(),
                category.getText().toString(),
                desc.getText().toString(),
                imageUrl
        );

        db.collection("products")
                .add(product)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                        Toast.makeText(AddRecipeActivity.this, "Product added successfully!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        Toast.makeText(AddRecipeActivity.this, "Failed to add product", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);

                productPhotoImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void selectCategory() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this); // Use 'this' instead of requireContext()
        builder.setTitle("Select category");
        builder.setSingleChoiceItems(categories, selectedCategory, (dialogInterface, i) -> {
            category.setText(categories[i]); // Set the selected category text to your TextView
            selectedCategory = i; // Update the selected category index
        });

        builder.setNegativeButton("OK", null);
        builder.show();
    }
}
