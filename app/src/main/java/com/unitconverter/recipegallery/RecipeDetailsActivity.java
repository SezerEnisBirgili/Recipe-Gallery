package com.unitconverter.recipegallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.unitconverter.recipegallery.databinding.ActivityMainBinding;
import com.unitconverter.recipegallery.databinding.ActivityRecipeDetailsBinding;

import java.util.concurrent.ExecutorService;

public class RecipeDetailsActivity extends AppCompatActivity {

    boolean imageDownloaded = false;
    Handler imageHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

            binding.recipeImage.setImageBitmap((Bitmap) msg.obj);
            imageDownloaded = true;

            return true;
        }
    });


    ActivityRecipeDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRecipeDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();

        Recipe recipe = (Recipe) intent.getSerializableExtra("recipe");

        String imagePath = recipe.getImagePath();

        binding.recipeDetailsName.setText(recipe.getName());
        binding.ingredientDescription.setText(recipe.getDescription());
        binding.instructionsDescription.setText(recipe.getInstructions());


        RecipeRepo repo = new RecipeRepo();

        ExecutorService srv=((RecipeGalleryApplication)this.getApplication()).srv;

        repo.downloadImage(srv, imageHandler, imagePath);

        binding.backButton3.setOnClickListener(v->{

            finish();
        });

    }
}