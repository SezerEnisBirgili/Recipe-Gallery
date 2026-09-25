package com.unitconverter.recipegallery;

import android.app.Application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RecipeGalleryApplication extends Application {

    ExecutorService srv = Executors.newCachedThreadPool();
}
