package com.unitconverter.recipegallery;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class RecipeRepo {

    public void getAllRecipes(ExecutorService srv, Handler uiHandler){


        srv.submit(()->{
            try {

                List<Recipe> data = new ArrayList<>();

                URL url =
                        new URL("http://10.0.2.2:8080/recipegallery/recipes");


                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                BufferedReader reader
                        = new BufferedReader(
                        new InputStreamReader(
                                conn.getInputStream()));
                StringBuilder buffer = new StringBuilder();
                String line ="";
                while((line=reader.readLine())!=null){
                    buffer.append(line);
                }

                JSONArray arr = new JSONArray(buffer.toString());

                for (int i = 0; i <arr.length() ; i++) {

                    JSONObject current = arr.getJSONObject(i);

                    Recipe recipe = new Recipe(current.getString("id"),
                            current.getString("name"),
                            current.getString("description"),
                            current.getString("instructions"),
                            current.getString("image"),
                            current.getString("imagePath"));

                    data.add(recipe);
                }

                Message msg = new Message();
                msg.obj = data;
                uiHandler.sendMessage(msg);



            } catch (MalformedURLException e) {
                Log.e("DEV",e.getMessage());
            } catch (IOException e) {
                Log.e("DEV",e.getMessage());
            } catch (JSONException e) {
                Log.e("DEV",e.getMessage());
            }


        });

    }

    public void getFilteredRecipes(ExecutorService srv, Handler uiHandler, String query)
    {


        srv.submit(()->{
            try {

                List<Recipe> data = new ArrayList<>();

                String urlPath = "http://10.0.2.2:8080/recipegallery/recipes/search?search=" + query;

                URL url = new URL(urlPath);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                BufferedReader reader
                        = new BufferedReader(
                        new InputStreamReader(
                                conn.getInputStream()));
                StringBuilder buffer = new StringBuilder();
                String line ="";
                while((line=reader.readLine())!=null){
                    buffer.append(line);
                }

                JSONArray arr = new JSONArray(buffer.toString());

                for (int i = 0; i <arr.length() ; i++) {

                    JSONObject current = arr.getJSONObject(i);

                    Recipe recipe = new Recipe(current.getString("id"),
                            current.getString("name"),
                            current.getString("description"),
                            current.getString("instructions"),
                            current.getString("image"),
                            current.getString("imagePath"));

                    data.add(recipe);
                }

                Message msg = new Message();
                msg.obj = data;
                uiHandler.sendMessage(msg);



            } catch (MalformedURLException e) {
                Log.e("DEV",e.getMessage());
            } catch (IOException e) {
                Log.e("DEV",e.getMessage());
            } catch (JSONException e) {
                Log.e("DEV",e.getMessage());
            }


        });
    }


    public void downloadImage(ExecutorService srv, Handler uiHandler, String path){

        srv.submit( () ->{
            try {
                URL url =
                        new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                Bitmap bmp = BitmapFactory.decodeStream(conn.getInputStream());

                Message msg = new Message();
                msg.obj = bmp;
                uiHandler.sendMessage(msg);

            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
    }

    public void saveRecipe(ExecutorService srv, Recipe recipe){
        srv.execute(()->{

            try {
                URL url = new URL("http://10.0.2.2:8080/recipegallery/recipes/save");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setDoInput(true);
                conn.setDoOutput(true);

                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type","application/JSON");


                JSONObject outputData  = new JSONObject();

                outputData.put("name",recipe.getName());
                outputData.put("description",recipe.getDescription());
                outputData.put("instructions",recipe.getInstructions());
                outputData.put("image",recipe.getImage());
                outputData.put("imagePath", recipe.getImagePath());

                BufferedOutputStream writer =
                        new BufferedOutputStream(conn.getOutputStream());


                writer.write(outputData.toString().getBytes(StandardCharsets.UTF_8));
                writer.flush();

                BufferedReader reader
                        = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder buffer = new StringBuilder();

                String line ="";

                while((line=reader.readLine())!=null){

                    buffer.append(line);

                }

                JSONObject retVal = new JSONObject(buffer.toString());
                conn.disconnect();


                String retValStr = retVal.getString("result");

                //Update Listener or handlers, it is for you to complete!


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        });
    }
}
