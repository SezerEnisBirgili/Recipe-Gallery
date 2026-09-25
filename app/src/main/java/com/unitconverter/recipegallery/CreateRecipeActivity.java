package com.unitconverter.recipegallery;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.unitconverter.recipegallery.databinding.ActivityCreateRecipeBinding;
import com.unitconverter.recipegallery.databinding.ActivityMainBinding;

import java.util.concurrent.ExecutorService;

public class CreateRecipeActivity extends AppCompatActivity {

    ActivityCreateRecipeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCreateRecipeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnFinish.setOnClickListener(v->{

            Recipe createdRecipe = new Recipe(binding.CreateRecipeName.getText().toString(),
                                          binding.CreateRecipeIngredients.getText().toString(),
                                          binding.CreateRecipeInstructions.getText().toString(),
                                    "defaultImage",
                                 "http://10.0.2.2:8080/recipegallery/images/default.jpeg");

            ExecutorService srv = ((RecipeGalleryApplication)((Activity)this).getApplication()).srv;

            RecipeRepo repo = new RecipeRepo();

            repo.saveRecipe(srv, createdRecipe);

            finish();
        });

        binding.backButton2.setOnClickListener(v->{

            finish();
        });

    }
}