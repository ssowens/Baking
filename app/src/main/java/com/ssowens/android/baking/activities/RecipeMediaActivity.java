package com.ssowens.android.baking.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
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
        String url;
        int stepId;
        int recipeId;
        String recipeName;
        if (isOnline()) {
            if (getIntent() != null) {
                if (getIntent().getSerializableExtra(EXTRA_ID) != null ||
                        getIntent().getSerializableExtra(EXTRA_RECIPE_ID) != null ||
                        getIntent().getSerializableExtra(EXTRA_VIDEO_URL) != null ||
                        getIntent().getSerializableExtra(EXTRA_RECIPE_NAME) != null) {

                    stepId = (int) getIntent().getSerializableExtra(EXTRA_ID);
                    recipeId = (int) getIntent().getSerializableExtra(EXTRA_RECIPE_ID);
                    url = (String) getIntent().getSerializableExtra(EXTRA_VIDEO_URL);
                    recipeName = (String) getIntent().getSerializableExtra(EXTRA_RECIPE_NAME);
                    fragment = RecipeMediaFragment.newInstance(url, stepId, recipeId, recipeName);
                    return fragment;
                }
            }
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
        if (isLandscape) {
            getSupportActionBar().hide();
        } else {
            getSupportActionBar().show();
        }
    }

    public interface OnConfigurationRotate {
        void onRotate(boolean landscape);
    }

}

