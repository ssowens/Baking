package com.ssowens.android.baking.services;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.ssowens.android.baking.R;
import com.ssowens.android.baking.models.Ingredient;
import com.ssowens.android.baking.provider.RecipeWidgetProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.ssowens.android.baking.fragments.RecipeIngredientsFragment.JSON_INGREDIENTS_STRING;

/**
 * Created by Sheila Owens on 4/14/18.
 */
public class RecipeIngredientsService extends IntentService {


    private static final String TAG = RecipeIngredientsService.class.getSimpleName();
    public static final String ACTION_UPDATE_RECIPE_WIDGETS = "update_recipe_widgets";
    public static final String ACTION_GET_INGREDIENTS = "get_ingredient_list";
    public static final long RECIPE_ID = -1;
    public static final String JSON_INGREDIENTS_STRING_EMPTY = "ingredientsJsonStrEmpty";

    private Gson gson;
    private List<Ingredient> ingredients = new ArrayList<>();

    public RecipeIngredientsService() {
        super("RecipeIngredientsService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.i(TAG, "Sheila onHandleIntent");
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_GET_INGREDIENTS.equals(action)) {
                handleActionGetIngredients();
            } else if (ACTION_UPDATE_RECIPE_WIDGETS.equals(action)) {
                handleActionUpdateRecipeWidgets();
            }
        }
    }

    /**
     * Handle action GetIngredients in the provided background thread with the provided
     * parameters.
     */
    private void handleActionGetIngredients() {
        Log.i(TAG, "Sheila Get Ingredients");
        startActionGetIngredientList(this);
    }

    /**
     * Handle action UpdateRecipeWidgets in the provided background thread with the provided
     * parameters.
     */
    private void handleActionUpdateRecipeWidgets() {

        Ingredient[] ingredients;
        List<Ingredient> ingredientList;

        Log.i(TAG, "Sheila Update Ingredients List");
        // Extract the plant details
        int imgRes = R.drawable.baking_icon; // Default image in case our garden is empty

        String jsonString = PreferenceManager.getDefaultSharedPreferences
                (getApplicationContext())
                .getString(JSON_INGREDIENTS_STRING,
                        "emptyJsonString");
        Log.i(TAG, "Sheila *** jsonString " + jsonString);

        // Convert the JSON string to an Ingredient Object
        Gson gson = new Gson();

//        Type listType = new TypeToken<ArrayList<Ingredient>>(){}.getType();
//        List<Ingredient> ingredientList = new Gson().fromJson(JSON_INGREDIENTS_STRING, listType);

        try {
            ingredients = gson.fromJson(jsonString, Ingredient[].class);
            ingredientList = new ArrayList<>(Arrays.asList(ingredients));
        } catch (IllegalStateException | JsonSyntaxException exception) {
            Log.i(TAG, "JSON error: " + exception);
        }




        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this,
                RecipeWidgetProvider.class));

        boolean isRecipeAvail = false;  //TODO Fix this!!
        //Trigger data update to handle the GridView widgets and force a data refresh
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_grid_view);
        //Now update all widgets
        RecipeWidgetProvider.updateRecipeWidgets(this, appWidgetManager, imgRes,
                isRecipeAvail, appWidgetIds);
    }

    /**
     * Starts this service to perform GetIngredientList action with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionGetIngredientList(Context context) {
        Intent intent = new Intent(context, RecipeIngredientsService.class);
        intent.setAction(ACTION_GET_INGREDIENTS);
        context.startService(intent);

    }

    /**
     * Starts this service to perform UpdateRecipeWidgets action with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionUpdateRecipeWidgets(Context context) {
        Intent intent = new Intent(context, RecipeIngredientsService.class);
        intent.setAction(ACTION_UPDATE_RECIPE_WIDGETS);
        context.startService(intent);

    }

    //TODO CONVERT JSON TO OBJECT
    /**
     * Gson g = new Gson(); Player p = g.fromJson(jsonString, Player.class)

     */

    // TODO GET SHAREDPREFERENCES
    /*

   PreferenceManager.getDefaultSharedPreferences(context).getString("MYLABEL",
   "defaultStringIfNothingFound");
     */
}
