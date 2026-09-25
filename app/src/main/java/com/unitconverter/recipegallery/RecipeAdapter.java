package com.unitconverter.recipegallery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class RecipeAdapter  extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>{

    Context ctx;
    List<Recipe> data;

    public RecipeAdapter(Context ctx, List<Recipe> data) {
        this.ctx = ctx;
        this.data = data;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View root = LayoutInflater.from(ctx).inflate(R.layout.recipe_row_layout,parent,false);
        RecipeViewHolder holder = new RecipeViewHolder(root);

        holder.setIsRecyclable(false);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {

        holder.txtName.setText(data.get(position).getName());

        ExecutorService srv = ((RecipeGalleryApplication)((Activity)ctx).getApplication()).srv;

        holder.downloadImage(srv,data.get(position).getImagePath());

        holder.row.setOnClickListener(v->{

            Intent i = new Intent( ctx, RecipeDetailsActivity.class);

            Recipe selectedRecipe = data.get(position);

            i.putExtra("recipe", selectedRecipe);

            ctx.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout row;
        TextView txtName;
        ImageView imgRec;

        boolean imageDownloaded;


        Handler imageHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {

                imgRec.setImageBitmap((Bitmap) msg.obj);
                imageDownloaded = true;

                return true;
            }
        });


        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            row = itemView.findViewById(R.id.RecipeRow);
            txtName = itemView.findViewById(R.id.recipeName);
            imgRec = itemView.findViewById(R.id.recipeImg);
        }

        public void downloadImage(ExecutorService srv, String path){

            if(!imageDownloaded){
                RecipeRepo repo = new RecipeRepo();
                repo.downloadImage(srv,imageHandler,path);
            }


        }
    }


}