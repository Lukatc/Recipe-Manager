package com.example.recipemanager.work

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.recipemanager.R
import com.example.recipemanager.data.database.RecipeDatabase
import kotlinx.coroutines.flow.first

class MealPlanReminderWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    companion object {
        private const val TAG = "MealPlanWorker"
        private const val CHANNEL_ID = "meal_plan_reminder"
        private const val CHANNEL_NAME = "Meal Plan Reminders"
        private const val NOTIF_ID = 1001
    }

    override suspend fun doWork(): Result {
        Log.d(TAG, "doWork() invoked")

        // 1) Read from Room
        val dao = RecipeDatabase.getDatabase(applicationContext).mealPlanDao()
        val plans = dao.getAllMealPlans().first()
        Log.d(TAG, "Found ${plans.size} meal plans")

        if (plans.isEmpty()) {
            Log.d(TAG, "No plans → skipping notification")
            return Result.success()
        }

        // 2) Ensure channel exists (Oreo+)
        val nm = applicationContext
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d(TAG, "Creating notification channel")
            val chan = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            nm.createNotificationChannel(chan)
        }

        // 3) Build & post
        val summary = plans.joinToString { it.title }
        val notif = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)          // must be valid!
            .setContentTitle("Today's Meal Plan")
            .setStyle(NotificationCompat.BigTextStyle().bigText(summary))
            .setContentText(summary)
            .build()

        Log.d(TAG, "Issuing notification")
        nm.notify(NOTIF_ID, notif)
        Log.d(TAG, "Notification posted")

        return Result.success()
    }
}
