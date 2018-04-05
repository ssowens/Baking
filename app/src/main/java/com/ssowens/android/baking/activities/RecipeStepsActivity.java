package com.ssowens.android.baking.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.ssowens.android.baking.R;
import com.ssowens.android.baking.fragments.RecipeStepsFragment;

/**
 * Created by Sheila Owens on 3/25/18.
 */

public class RecipeStepsActivity extends SingleFragmentActivity {

    public static final String EXTRA_RECIPE_ID = "id";

    public RecipeStepsActivity() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Fragment createFragment() {
        if (isOnline()) {
            int recipeId = (int) getIntent().getSerializableExtra(EXTRA_RECIPE_ID);
            return RecipeStepsFragment.newInstance(recipeId);
        } else {
            Toast.makeText(this, getString(R.string.no_internet_service),
                    Toast.LENGTH_SHORT).show();
        }
        return null;
    }
}
