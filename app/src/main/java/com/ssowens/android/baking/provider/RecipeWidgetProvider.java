package com.ssowens.android.baking.provider;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;

import com.ssowens.android.baking.R;
import com.ssowens.android.baking.activities.MainActivity;
import com.ssowens.android.baking.activities.RecipeIngredientsActivity;
import com.ssowens.android.baking.services.ListWidgetService;
import com.ssowens.android.baking.services.RecipeIngredientsService;

import static com.ssowens.android.baking.fragments.RecipeIngredientsFragment.SHARED_PREF_RECIPE_ID;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    private static final String TAG = RecipeWidgetProvider.class.getSimpleName();
    private static int recipeId;
    int imgRes;
    String recipeName = "";

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int imgRes, boolean isRecipeAvail, int appWidgetId) {

        Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
        int width = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
        Log.i(TAG, "Sheila width = " + width);
        RemoteViews rv;

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences
                (context);
        if (sharedPreferences.contains("recipe")) {
            recipeId = sharedPreferences.getInt(SHARED_PREF_RECIPE_ID, -1);
            Log.i(TAG, "Sheila This is the recipe id" + recipeId);
        }

        if (width < 60 || recipeId == -1) {
            Log.i(TAG, "** Sheila No Ingredient List Avail **");
            rv = getHomeRemoteView(context, imgRes, isRecipeAvail);
        } else {
            rv = getIngredientListRemoteView(context);
        }
        appWidgetManager.updateAppWidget(appWidgetId, rv);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        //Start the intent service update widget action, the service takes care of updating the widgets UI
        RecipeIngredientsService.startActionUpdateRecipeWidgets(context);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // Perform any action when one or more AppWidget instances have been deleted
    }

    /**
     * Creates and returns the RemoteViews to be displayed in the GridView mode widget
     *
     * @param context The context
     * @return The RemoteViews for the GridView mode widget
     */
    private static RemoteViews getIngredientListRemoteView(Context context) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_list_view);

        // Set the ListWidgetService intent to act as the adapter for the ListView
        Intent intent = new Intent(context, ListWidgetService.class);
        views.setRemoteAdapter(R.id.widget_list_view, intent);

        // Set the Ingredients intent to launch when clicked
        // TODO I should the recipe ID this
        Intent appIntent = new Intent(context, RecipeIngredientsActivity.class);
        appIntent.getIntExtra(SHARED_PREF_RECIPE_ID, recipeId);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0,
                appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.widget_list_view, appPendingIntent);
        // Handle no ingredients
        views.setEmptyView(R.id.widget_list_view, R.id.empty_view);
        return views;
    }

    private static RemoteViews getHomeRemoteView(Context context, int imgRes, boolean
            isRecipeAvail) {

        // Set the click handler to open the MainActivity if there is no recipe
        // selected. Otherwise, open the Ingredient activity.
        Intent intent;
        if (recipeId == 0) {
            Log.i(TAG, "Sheila Recipe Id* = " + recipeId);
            intent = new Intent(context, MainActivity.class);
        } else {
            Log.i(TAG, "Sheila Recipe Id = " + recipeId);
            intent = new Intent(context, RecipeIngredientsActivity.class);
            intent.putExtra(RecipeIngredientsActivity.EXTRA_RECIPE_ID, recipeId);
            // TODO Need Recipe Name
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.home_list_widget_provider);
        // Update image
        views.setImageViewResource(R.id.widget_ingredients_image, imgRes);

        // Widgets allow click handlers to only launch pending intents
        views.setOnClickPendingIntent(R.id.widget_ingredients_image, pendingIntent);
        return views;
    }

    public static void updateRecipeWidgets(Context context, AppWidgetManager appWidgetManager,
                                           int imgRes, boolean isRecipeAvail, int[]
                                                   appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, imgRes, isRecipeAvail, appWidgetId);
        }
    }
}

