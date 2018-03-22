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
    private int recipeId;

    public RecipeIngredientsActivity() {
    }

    public RecipeIngredientsActivity(int recipeId) {
        Log.i(TAG, "Recipe id = " + recipeId);
        this.recipeId = recipeId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps);
    }

    @Override
    protected Fragment createFragment() {
        if (isOnline()) {
            return RecipeIngredientsFragment.newInstance();
        } else {
            Toast.makeText(this, getString(R.string.no_internet_service),
                    Toast.LENGTH_SHORT).show();
            return null;
        }
    }
}
