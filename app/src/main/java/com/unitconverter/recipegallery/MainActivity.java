package com.unitconverter.recipegallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Intent;
import android.os.Bundle;

import com.unitconverter.recipegallery.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.viewRecipe.setOnClickListener(v->{

            Intent i = new Intent(MainActivity.this,
                    ViewRecipeActivity.class);

            startActivity(i);
        });

        binding.createRecipe.setOnClickListener(v->{

            Intent i = new Intent(MainActivity.this,
                    CreateRecipeActivity.class);

            startActivity(i);
        });
    }
}