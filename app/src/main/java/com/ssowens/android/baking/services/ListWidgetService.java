package com.ssowens.android.baking.services;

import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.ssowens.android.baking.R;
import com.ssowens.android.baking.models.Ingredient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.ssowens.android.baking.fragments.RecipeIngredientsFragment.JSON_INGREDIENTS_STRING;

/**
 * Created by Sheila Owens on 4/14/18.
 */
public class ListWidgetService extends RemoteViewsService {

    private static final String TAG = ListWidgetService.class.getSimpleName();

    String jsonString;
    Ingredient[] ingredients;
    List<Ingredient> ingredientList = new ArrayList<>();
    boolean isRecipeAvail;
    int recipeId;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this.getApplicationContext());
        // TODO Get data from getDataFromSharePrefs
    }

    public class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        Context context;
        private List<Ingredient> ingredientList = new ArrayList<>();
        private int appWidgetId;

        public ListRemoteViewsFactory(Context context) {
            this.context = context;

            populateListItem();
        }

        @Override
        public void onCreate() {

        }

        private void populateListItem() {
            // SharedPreferences get them
        }


        // Called on start and when NotifyAppViewWidgetDataChanged is called
        @Override
        public void onDataSetChanged() {

            Gson gson = new Gson();
            jsonString = PreferenceManager.getDefaultSharedPreferences
                    (getApplicationContext())
                    .getString(JSON_INGREDIENTS_STRING,
                            "emptyJsonString");
            Log.i(TAG, "Sheila *** jsonString " + jsonString);

            // Convert the JSON string to an Ingredient Object
            try {
                ingredients = gson.fromJson(jsonString, Ingredient[].class);
                ingredientList = new ArrayList<>(Arrays.asList(ingredients));
                if (ingredientList.size() > 0) {
                    isRecipeAvail = true;
                }
            } catch (IllegalStateException | JsonSyntaxException exception) {
                Log.i(TAG, "JSON error: " + exception);
            }
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            Log.i(TAG, "Sheila Size for ingredientList.size()=" + ingredientList.size());
            return ingredientList.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout
                    .item_widget_list_view);

            String itemRow = ingredientList.get(position).getQuantity() + "  " +
                    ingredientList.get(position).getMeasure() + "  " +
                    ingredientList.get(position).getIngredient();
            views.setTextViewText(R.id.text_row, itemRow);


            // TODO  fill in Recipe Id
            Intent fillIntent = new Intent();
            fillIntent.putExtra("ItemRow", itemRow);
            views.setOnClickFillInIntent(R.id.widget_list_view, fillIntent);

            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}
