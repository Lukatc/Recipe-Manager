# Recipe Manager

An Android app that helps you manage your recipes and plan meals. You can add recipes with ingredients, view details, mark favorites, and create meal plans.

## Screenshots

### Recipe List Screen

<img src="Screenshot 2025-06-23 204340.png" alt="Recipe List" width="300">

### Add Recipe Screen

<img src="Screenshot 2025-06-23 204410.png" alt="Recipe Detail" width="300">

### Meal Plan Screen

<img src="Screenshot 2025-06-23 204355.png" alt="Meal Plan" width="300">

## Features

* **Recipe List**: Browse all your recipes in a simple list
* **Recipe Details**: Tap a recipe to see instructions and ingredients
* **Favorites**: Mark recipes as favorite for quick access
* **Meal Planning**: Add recipes to your meal plan
* **Offline Storage**: All data saved locally using Room database
* **User Accounts**: Supports user login with Firebase Authentication

## 🛠 Tech Stack

* **Kotlin** - Programming language
* **Room Database** - Local data storage
* **Firebase Authentication** - User management
* **RecyclerView** - Efficient list displays
* **Material Design Components** - UI elements and navigation

## 📂 How to Run

1. **Download the code**
2. **Open the project in Android Studio**
3. **Connect a device or start an emulator**
4. **Press the Run button to build and launch the app**

## 📄 Terms of Use

* This app stores data locally and uses Firebase Auth for user accounts.
* User data is private and stored only on your device and Firebase.

## How the App Works

### Main Parts

**MainActivity**

* The main entry point
* Hosts navigation and bottom navigation bar

**RecipeListFragment**

* Shows the list of recipes
* Allows adding, editing, and deleting recipes

**RecipeDetailFragment**

* Displays full recipe details including ingredients and instructions

**MealPlanFragment**

* Lets users add recipes to a meal plan
* View and manage planned meals

**Room Database**

* Manages storage of recipes, ingredients, and meal plans

**Firebase Authentication**

* Handles user login and registration

## File Organization

```
app/src/main/
├── java/com/example/recipemanager/
│   ├── ui/
│   │   ├── recipe/
│   │   ├── mealplan/
│   │   └── ...
│   ├── data/
│   │   ├── database/
│   │   │   ├── entities/
│   │   │   ├── dao/
│   │   │   └── RecipeDatabase.kt
│   │   └── repository/
│   ├── viewmodel/
│   └── MainActivity.kt
└── res/
    ├── layout/
    ├── drawable/
    └── values/
```

## Why the App is Fast

* **Room Database**: Efficient local data storage
* **RecyclerView with DiffUtil**: Smooth list performance
* **Lazy Loading**: Loads only needed data
* **Material Components**: Optimized UI elements

## What You Need

* **Android Phone or Emulator**: Running Android 6.0+ recommended
* **Internet**: Required for Firebase Authentication
