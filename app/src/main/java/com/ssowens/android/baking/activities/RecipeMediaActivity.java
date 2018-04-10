package com.ssowens.android.baking.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.ssowens.android.baking.R;
import com.ssowens.android.baking.fragments.RecipeMediaFragment;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

public class RecipeMediaActivity extends SingleFragmentActivity {

    private static final String TAG = RecipeMediaActivity.class.getSimpleName();

    public static final String EXTRA_VIDEO_URL = "videoUrl";
    public static final String EXTRA_ID = "id";
    public static final String EXTRA_RECIPE_ID = "recipeId";
    public static final String EXTRA_RECIPE_NAME = "name";

    private RecipeMediaFragment fragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Fragment createFragment() {
        if (isOnline()) {
            String url = (String) getIntent().getSerializableExtra(EXTRA_VIDEO_URL);
            int stepId = (int) getIntent().getSerializableExtra(EXTRA_ID);
            int recipeId = (int) getIntent().getSerializableExtra(EXTRA_RECIPE_ID);
            String recipeName = (String) getIntent().getSerializableExtra(EXTRA_RECIPE_NAME);
            fragment =  RecipeMediaFragment.newInstance(url, stepId, recipeId, recipeName);
            return fragment;
        } else {
            Toast.makeText(this, getString(R.string.no_internet_service),
                    Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        boolean isLandscape = newConfig.orientation == ORIENTATION_LANDSCAPE;
        if (fragment != null) {
            fragment.onRotate(isLandscape);
        }
        Log.e("Sheila", "test " + newConfig.orientation);
        if (isLandscape) {
            getSupportActionBar().hide();
            // TODO HIDE STATUS BAR
        } else {
            getSupportActionBar().show();
        }
    }

    public interface OnConfigurationRotate {
        void onRotate(boolean landscape);
    }

}

