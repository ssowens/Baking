package com.ssowens.android.baking.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.ssowens.android.baking.R;
import com.ssowens.android.baking.fragments.RecipeIngredientsFragment;
import com.ssowens.android.baking.models.Ingredient;
import com.ssowens.android.baking.models.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sowens on 3/22/18.
 */
public class RecipeIngredientsActivity extends SingleFragmentActivity {

    private static final String TAG = RecipeIngredientsActivity.class.getSimpleName();
    private int recipeId;
    private List<Ingredient> ingredientList = new ArrayList<>();
    Recipe recipe;

    public RecipeIngredientsActivity() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int recipeId = extras.getInt("id");
//            ingredientList.add(ingredientList.get(recipeId).getQuantity(),
//                    ingredientList.get(recipeId).getMeasure(),
//                    ingredientList.get(recipeId).getIngredient();
        }

        Log.i(TAG, "This is the recipeid " + recipeId);
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
