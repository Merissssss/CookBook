package com.example.myapplication.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.Manifest;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
    private static final String TAG = "AddRecipeActivity";
    private static final int PICK_IMAGE_REQUEST = 1;
    private FirebaseFirestore db;
    private TextView name;
    private TextView desc;
    private ImageButton addBtn;
    Uri imagePath;
    private String[] categories = {"Breakfast", "Lunch", "Dinner", "Desert"};

    private int selectedCategory = 0;

    private TextView category;
    AddRecipeModel recipeModel;
    private ImageView productPhotoImageView;
    private StorageReference storageRef;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;

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
        // Check if the READ_EXTERNAL_STORAGE permission is not granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Request the permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        } else {
            // Permission is already granted, proceed with accessing the image
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        }

        recipeModel = new AddRecipeModel(name.getText().toString(), category.getText().toString(), desc.getText().toString(), TAG);
        recipeModel.setProductId(db.collection("UnconfirmedProducts").document().getId());

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {
            // Check if the permission has been granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with accessing the image
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            } else {
                // Permission denied, handle accordingly (e.g., show a message or disable functionality)
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
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

                        // Now that the image is uploaded, save the recipe data to Firestore
                        saveDataToFirestore(imageUrl);
                    });
                })
                .addOnFailureListener(e -> {
                    // Error occurred while uploading the image
                    Log.e(TAG, "Error uploading image", e);
                    Toast.makeText(AddRecipeActivity.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                });
    }

    private void saveDataToFirestore(String imageUrl) {
        if (imageUrl == null || imageUrl.isEmpty()) {
            Log.e(TAG, "Image URL is null or empty");
            Toast.makeText(AddRecipeActivity.this, "Image URL is null or empty", Toast.LENGTH_SHORT).show();
            return; // Exit the method if imageUrl is null or empty
        }

        if (recipeModel == null) {
            Log.e(TAG, "recipeModel is null");
            Toast.makeText(AddRecipeActivity.this, "Recipe data is null", Toast.LENGTH_SHORT).show();
            return;
        }

        recipeModel.setImageAlpha(imageUrl);

        db.collection("Recipes")
                .add(recipeModel)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                        Toast.makeText(AddRecipeActivity.this, "Product added successfully!", Toast.LENGTH_SHORT).show();
                        // Call uploadToStorage() after Firestore save is successful
                        uploadToStorage();
                        finish(); // Add finish() here to close the activity
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
            if (imageUri != null) {
                // Set the imagePath to the selected image URI
                imagePath = imageUri;
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    productPhotoImageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(this, "Failed to retrieve image URI", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void uploadToStorage() {
        if (imagePath != null) {
            storageRef.child("products/" + recipeModel.getProductId())
                    .putFile(imagePath)
                    .addOnSuccessListener(taskSnapshot -> storageRef.child("products/" + recipeModel.getProductId())
                            .getDownloadUrl()
                            .addOnSuccessListener(uri -> {
                                recipeModel.setImageAlpha(uri.toString());
                                saveDataToFirestore();
                            }))
                    .addOnFailureListener(e -> {
                        Toast.makeText(AddRecipeActivity.this, getString(R.string.failed_to_upload_photo) + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(AddRecipeActivity.this, "Image path is null", Toast.LENGTH_SHORT).show();
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
