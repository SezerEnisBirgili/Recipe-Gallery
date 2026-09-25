package com.unitconverter.recipegallery;

import static java.lang.Thread.sleep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;

import com.unitconverter.recipegallery.databinding.ActivityViewRecipeBinding;
import com.unitconverter.recipegallery.databinding.FragmentListRecipesBinding;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class ViewRecipeActivity extends AppCompatActivity {

    ActivityViewRecipeBinding binding;

    Context act = this;

    Handler dataHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {


            List<Recipe> data = (List<Recipe>)msg.obj;

            RecipeAdapter adp = new RecipeAdapter(act, data);

            if (binding.recView.getAdapter() != null) {
                binding.recView.swapAdapter(adp, true);
            }
            else {
                binding.recView.setAdapter(adp);
            }

            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewRecipeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ExecutorService srv=((RecipeGalleryApplication)this.getApplication()).srv;

        binding.recView.setLayoutManager(new GridLayoutManager(this, 2));

        RecipeRepo repo = new RecipeRepo();

        repo.getAllRecipes(srv,dataHandler);

        binding.searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                RecipeRepo repo = new RecipeRepo();
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                repo.getFilteredRecipes(srv, dataHandler, s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.backButton.setOnClickListener(v->{

            finish();
        });


    }
}