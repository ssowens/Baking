package com.ssowens.android.baking.services;

import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.ssowens.android.baking.models.Ingredient;

import java.util.ArrayList;
import java.util.List;

import static com.ssowens.android.baking.fragments.RecipeIngredientsFragment.JSON_INGREDIENTS_STRING;

/**
 * Created by Sheila Owens on 4/14/18.
 */
public class GridWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewsFactory(this.getApplicationContext());
    }

    public class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        Context context;

        public GridRemoteViewsFactory(Context context) {
            this.context = context;
        }

        @Override
        public void onCreate() {

        }


        // Called on start and when NotifyAppViewWidgetDataChanged is called
        @Override
        public void onDataSetChanged() {

            //TODO Get the recipe data here
            Gson gson = new Gson();
            List<Ingredient> ingredients = new ArrayList<>();
            String jsonString = PreferenceManager.getDefaultSharedPreferences(context).getString
                    (JSON_INGREDIENTS_STRING,
                    "defaultStringIfNothingFound");
            Ingredient ingredient = gson.fromJson(jsonString, Ingredient.class);

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public RemoteViews getViewAt(int position) {
            return null;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 0;
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
