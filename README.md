# Recipe Manager

An Android single-Activity app for securely managing your personal recipes, ingredients and daily meal plans entirely on your device. No external database sync—everything is stored locally using Room—and you get a gentle daily reminder of what's on your meal plan.

## Screenshots

### Recipe List Screen

<img src="Screenshot 2025-06-23 204340.png" alt="Recipe List" width="300">

### Add Recipe Screen

<img src="Screenshot 2025-06-23 204410.png" alt="Add Recipe" width="300">

### Meal Plan Screen

<img src="Screenshot 2025-06-23 204355.png" alt="Meal Plan" width="300">

## Features

* **Email/Password Authentication**  
  Secure login & registration powered by Firebase Auth; users stay signed in until they log out.  
* **Recipe Management**  
  • Add, edit and delete recipes with title, category, difficulty, servings and ingredient list  
  • Full-screen detail view showing all ingredients and instructions  
  • Mark recipes as "favorites" for quick access  
* **Meal Planning**  
  • Build a daily meal plan by selecting from your saved recipes  
  • View & remove planned meals in a dedicated fragment  
* **Daily Notification**  
  Receive a "Today's Meal Plan" notification every morning at 8 AM for any meals you've scheduled  
* **Offline-First Storage**  
  All data (recipes, ingredients, meal plans) lives in a local Room database—no network needed to browse or edit  
* **Smooth Navigation & Animations**  
  • Single-Activity architecture with Navigation Component + BottomNavigationView  
  • Slide-in/slide-out fragment transitions defined in `res/anim/`  
  • Type-safe SafeArgs for passing `recipeId` between fragments  

## Tech Stack

* **Kotlin**  
* **AndroidX Navigation** + BottomNavigationView  
* **Firebase Auth** (`firebase-auth-ktx`) for authentication only  
* **Room** (`androidx.room`) for local relational data storage  
* **WorkManager** (`androidx.work`) for daily background reminders  
* **Material Components** for UI  
* **RecyclerView** for efficient lists  


---

## 📋 What You Need

* **Android Studio 2022.3+**
* **Android SDK**: API Level 24+ (minSdkVersion 24), compileSdk 34
* **An Android device or emulator** running Android 6.0 ("Marshmallow") or higher
* **Firebase Project**: 
  1. Create a Firebase project in the console 
  2. Enable **Email/Password** sign-in under Authentication 
  3. Download and drop your `google-services.json` into `app/`
* **Internet connection** for initial authentication (after login, all data is local)
* **(Android 13+) Notification permission**: 
  The app will prompt you to grant `POST_NOTIFICATIONS` so you can receive your daily meal reminders.

## How to Run

1. **Clone** the repository  
   ```bash
   git clone https://github.com/Lukatc/Recipe-Manager.git
   ```

2. **Import** in Android Studio
3. **Add** your Firebase config: copy `google-services.json` into the `app/` folder
4. **Sync** Gradle and build
5. **Run** on a device or emulator (Android 6.0+)
6. **Grant** notification permission when prompted (Android 13+) so daily reminders can appear

## Terms of Use

* All recipe and meal-plan data is stored **locally** on your device.
* Firebase is used **only** for user authentication; no recipe data is uploaded or shared.

## How the App Works

### Main Flow

1. **Launch** → `MainActivity` checks FirebaseAuth:

   * If user is signed in → show **Recipes**
   * Otherwise → navigate to **Login**
2. **BottomNavigationView** switches between:

   * **Recipes** (list, search, favorites)
   * **Meal Plan** (add/remove planned meals)
   * **Profile** (view email, log out)
3. **Add/Edit** uses a form fragment to collect title, ingredients, category, difficulty and servings
4. **Detail** screens load recipe + ingredients from Room by `recipeId` via SafeArgs
5. **MealPlanReminderWorker** runs daily at 8 AM (WorkManager) and posts a notification if any meals are scheduled

### Key Components

| Component                  | Responsibility                                               |
| -------------------------- | ------------------------------------------------------------ |
| **MainActivity.kt**        | Hosts NavHostFragment, BottomNav, schedules WorkManager jobs |
| **Login/Register**         | Email/password screens using FirebaseAuth                    |
| **RecipesFragment**        | Displays list, handles add/edit/delete/favorite              |
| **RecipeDetailFragment**   | Shows full recipe details                                    |
| **AddRecipeFragment**      | Form to create or update a recipe                            |
| **MealPlanFragment**       | Manage today's meal plan                                     |
| **ProfileFragment**        | Display user email & logout                                  |
| **MealPlanReminderWorker** | Background worker that posts notifications                   |
| **RecipeViewModel**        | Exposes LiveData flows from Room DAOs                        |
| **MealPlanViewModel**      | Exposes meal plan data & handles add/delete operations       |

## Project Structure

```
app/src/main/
├── java/com/example/recipemanager/
│   ├── MainActivity.kt
│   ├── work/
│   │   └── MealPlanReminderWorker.kt
│   ├── utils/
│   │   └── TimeFormatter.kt
│   ├── data/
│   │   ├── database/
│   │   │   ├── RecipeDatabase.kt
│   │   │   ├── entities/
│   │   │   │   ├── Recipe.kt
│   │   │   │   ├── Ingredient.kt
│   │   │   │   └── MealPlan.kt
│   │   │   └── dao/
│   │   │       ├── RecipeDao.kt
│   │   │       ├── IngredientDao.kt
│   │   │       └── MealPlanDao.kt
│   │   └── repository/
│   │       ├── RecipeRepository.kt
│   │       └── MealPlanRepository.kt
│   ├── ui/
│   │   ├── auth/
│   │   │   ├── LoginFragment.kt
│   │   │   └── RegisterFragment.kt
│   │   ├── recipes/
│   │   │   ├── RecipesFragment.kt
│   │   │   ├── RecipeDetailFragment.kt
│   │   │   └── AddRecipeFragment.kt
│   │   ├── mealplan/
│   │   │   ├── MealPlanFragment.kt
│   │   │   └── MealPlanAdapter.kt
│   │   └── profile/
│   │       └── ProfileFragment.kt
│   └── viewmodel/
│       ├── RecipeViewModel.kt
│       └── MealPlanViewModel.kt
└── res/
    ├── layout/          # XML layouts for activities/fragments/items
    ├── navigation/      # nav_graph.xml
    ├── anim/            # slide_in_*.xml, slide_out_*.xml
    ├── drawable/        # icons & placeholders
    └── values/          # strings.xml, colors.xml, themes.xml
```

Enjoy cooking and planning—your recipes are now just a tap away!
