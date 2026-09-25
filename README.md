# Recipe Gallery

>  **This app requires the [Recipe-Gallery-API](https://github.com/SezerEnisBirgili/Recipe-Gallery-API) backend to be running.** It's the frontend of the Recipe Gallery app. Without the API running, the app will not be functional.

An Android app for browsing, searching, and creating recipes, built with Java and backed by a Spring Boot REST API.

## Tech Stack

- Java
- Android SDK (Gradle Kotlin DSL build scripts)

## Features

- **Browse recipes:** in a 2-column grid with thumbnail images
- **Live search:** filters recipes as you type, matching by name or ingredients
- **Recipe details:** full ingredients, instructions, and image for a selected recipe
- **Create a recipe:** name, ingredients, and instructions (uses a default placeholder image)

## Getting Started

### 1. Start the backend first

Clone and run [Recipe-Gallery-API](https://github.com/SezerEnisBirgili/Recipe-Gallery-API) — follow its README to get it running on `http://localhost:8080`.

### 2. Open this project in Android Studio

```bash
git clone <this-repo-url>
```

Open the folder in Android Studio (File → Open) and let Gradle sync.

### 3. Run on an emulator

This app is currently hardcoded to call the backend at:

```
http://10.0.2.2:8080/recipegallery/...
```

`10.0.2.2` is the **Android Emulator's special alias for the host machine's `localhost`**. This setup works out of the box **only on the emulator**, as long as the backend is running on the same computer.

Select an emulator in Android Studio's device dropdown and hit Run.

## Project Structure

```
app/src/main/java/com/unitconverter/recipegallery/
├── MainActivity.java             # Entry screen — links to View/Create
├── ViewRecipeActivity.java       # Grid + live search
├── RecipeDetailsActivity.java    # Single recipe detail view
├── CreateRecipeActivity.java     # New recipe form
├── RecipeAdapter.java            # RecyclerView adapter, async image loading
├── RecipeRepo.java               # All networking (HTTP calls to the API)
├── RecipeGalleryApplication.java # App-wide ExecutorService for background work
└── Recipe.java                   # Data model
```

## Limitations

- No edit or delete functionality in the app yet
- Backend URL is hardcoded rather than configurable
