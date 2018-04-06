package com.ssowens.android.baking.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.ssowens.android.baking.R;
import com.ssowens.android.baking.fragments.RecipeIngredientsFragment;

/**
 * Created by sowens on 3/22/18.
 */
public class RecipeIngredientsActivity extends SingleFragmentActivity {

    private static final String TAG = RecipeIngredientsActivity.class.getSimpleName();
    public static final String EXTRA_RECIPE_ID = "id";
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
        if (isOnline()) {
            int recipeId = (int) getIntent().getSerializableExtra(EXTRA_RECIPE_ID);
            String name = (String) getIntent().getSerializableExtra(EXTRA_RECIPE_NAME);
            return RecipeIngredientsFragment.newInstance(recipeId, name);
        } else {
            Toast.makeText(this, getString(R.string.no_internet_service),
                    Toast.LENGTH_SHORT).show();
            return null;
        }
    }
}
