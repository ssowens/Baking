package com.ssowens.android.baking.provider;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.ssowens.android.baking.R;
import com.ssowens.android.baking.activities.MainActivity;
import com.ssowens.android.baking.services.GridWidgetService;
import com.ssowens.android.baking.services.RecipeIngredientsService;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    private static final String TAG = RecipeWidgetProvider.class.getSimpleName();
    int imgRes;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int imgRes, boolean isRecipeAvail, int appWidgetId) {

        Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
        int width = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
        RemoteViews rv;
        if (width < 300) {
            Log.i(TAG, "** Sheila No Ingredient List Avail **");
            rv = getHomeRemoteView(context, imgRes, isRecipeAvail);
        } else {
            rv = getIngredientGridRemoteView(context);
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
    private static RemoteViews getIngredientGridRemoteView(Context context) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_grid_view);

        // Set the GridWidgetService intent to act as the adapter for the GridView
        Intent intent = new Intent(context, GridWidgetService.class);
        views.setRemoteAdapter(R.id.widget_grid_view, intent);
        // TODO Need the Recipe ID
        // Set the PlantDetailActivity intent to launch when clicked
//        Intent appIntent = new Intent(context, PlantDetailActivity.class);
//        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        views.setPendingIntentTemplate(R.id.widget_grid_view, appPendingIntent);
        // Handle empty gardens
        views.setEmptyView(R.id.widget_grid_view, R.id.empty_view);
        return views;
    }

    private static RemoteViews getHomeRemoteView(Context context, int imgRes, boolean
            isRecipeAvail) {

        // Set the click handler to open the MainActivity is there is no recipe
        // selected. Otherwise, open the Ingredient activity.

        Intent recipeIntent = new Intent(context, MainActivity.class);
        PendingIntent recipePendingIntent = PendingIntent.getActivity(context, 0,
                recipeIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.home_widget_provider);
        // Update image
        views.setImageViewResource(R.id.widget_recipe_image, imgRes);
        //TODO might not want to make this invisibile
        if (!isRecipeAvail) {
            views.setViewVisibility(R.id.recipe_icon, View.INVISIBLE);
        }
        // Widgets allow click handlers to only launch pending intents
        views.setOnClickPendingIntent(R.id.widget_recipe_image, recipePendingIntent);
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

