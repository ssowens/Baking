package com.ssowens.android.baking.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.ssowens.android.baking.R;
import com.ssowens.android.baking.fragments.RecipeIngredientsFragment;
import com.ssowens.android.baking.fragments.RecipeMediaFragment;

import static com.ssowens.android.baking.activities.RecipeMediaActivity.EXTRA_ID;
import static com.ssowens.android.baking.activities.RecipeMediaActivity.EXTRA_VIDEO_URL;

/**
 * Created by sowens on 3/22/18.
 */
public class RecipeIngredientsActivity extends SingleFragmentActivity
        implements RecipeIngredientsFragment.Callbacks {

    private static final String TAG = RecipeIngredientsActivity.class.getSimpleName();
    public static final String EXTRA_RECIPE_ID = "recipeId";
    public static final String EXTRA_RECIPE_NAME = "name";

    public RecipeIngredientsActivity() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Fragment createFragment() {
        int recipeId;
        String name;
        if (isOnline()) {
            if (getIntent().getSerializableExtra(EXTRA_RECIPE_ID) != null ||
                    getIntent().getSerializableExtra(EXTRA_RECIPE_NAME) != null) {

                recipeId = (int) getIntent().getSerializableExtra(EXTRA_RECIPE_ID);
                name = (String) getIntent().getSerializableExtra(EXTRA_RECIPE_NAME);
                return RecipeIngredientsFragment.newInstance(recipeId, name);
            }
        } else {
            Toast.makeText(this, getString(R.string.no_internet_service),
                    Toast.LENGTH_SHORT).show();

        }
        return null;
    }

    @Override
    public void onStepSelected(String url, int stepId, int recipeId, String recipeName) {
        if (findViewById(R.id.detail_fragment_container) == null) {
            Intent intent = new Intent(this, RecipeMediaActivity.class);
            intent.putExtra(EXTRA_VIDEO_URL, url);
            intent.putExtra(EXTRA_ID, stepId);
            intent.putExtra(EXTRA_RECIPE_ID, recipeId);
            intent.putExtra(EXTRA_RECIPE_NAME, recipeName);
            startActivity(intent);
        } else {
            Fragment newDetail = RecipeMediaFragment.newInstance(url, stepId, recipeId, recipeName);
            getSupportFragmentManager().beginTransaction().replace(R.id.detail_fragment_container,
                    newDetail).commit();

        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }

}
