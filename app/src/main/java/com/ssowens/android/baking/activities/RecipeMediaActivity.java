package com.ssowens.android.baking.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.ssowens.android.baking.R;
import com.ssowens.android.baking.fragments.RecipeMediaFragment;

public class RecipeMediaActivity extends SingleFragmentActivity {

    private static final String TAG = RecipeMediaActivity.class.getSimpleName();

    public static final String EXTRA_VIDEO_URL = "videoUrl";
    public static final String EXTRA_ID = "id";
    public static final String EXTRA_RECIPE_ID = "recipeId";

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
            return RecipeMediaFragment.newInstance(url, stepId, recipeId);
        } else {
            Toast.makeText(this, getString(R.string.no_internet_service),
                    Toast.LENGTH_SHORT).show();
        }
        return null;
    }




}

