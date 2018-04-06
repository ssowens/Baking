package com.ssowens.android.baking.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.ssowens.android.baking.R;
import com.ssowens.android.baking.RecipeCollection;
import com.ssowens.android.baking.fragments.RecipeMediaFragment;
import com.ssowens.android.baking.models.Recipe;
import com.ssowens.android.baking.models.Step;

import java.util.List;

import static com.ssowens.android.baking.activities.RecipeMediaActivity.EXTRA_ID;
import static com.ssowens.android.baking.activities.RecipeMediaActivity.EXTRA_RECIPE_ID;
import static com.ssowens.android.baking.activities.RecipeMediaActivity.EXTRA_RECIPE_NAME;
import static com.ssowens.android.baking.activities.RecipeMediaActivity.EXTRA_VIDEO_URL;

/**
 * Created by Sheila Owens on 4/3/18.
 */
public class RecipeMediaPagerActivity extends FragmentActivity {


    private ViewPager viewPager;
    private List<Recipe> recipes;
    private List<Step> steps;
    private int recipeId;
    private int stepId;
    private String url;
    static final String URL = "https://d17h27t6h515a5.cloudfront" +
            ".net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4";

    public static Intent newIntent(Context context, int recipeId, int stepId, String url) {
        Intent intent = new Intent(context, RecipeMediaPagerActivity.class);
        intent.putExtra(EXTRA_RECIPE_ID, recipeId);
        intent.putExtra(EXTRA_VIDEO_URL, url);
        intent.putExtra(EXTRA_ID, stepId);

        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_recipe_media);

        final int recipeId = (int) getIntent().getSerializableExtra(EXTRA_RECIPE_ID);
        final int stepId = (int) getIntent().getSerializableExtra(EXTRA_ID);
        final String url = (String) getIntent().getSerializableExtra(EXTRA_VIDEO_URL);
        final String recipeName = (String) getIntent().getSerializableExtra(EXTRA_RECIPE_NAME);

        viewPager = findViewById(R.id.media_view_pager);

        recipes = RecipeCollection.get(this).getRecipes();
        steps = recipes.get(recipeId).getSteps();
        FragmentManager fragmentManager = getSupportFragmentManager();

        viewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Step step = steps.get(position);
                return RecipeMediaFragment.newInstance(url, stepId, recipeId, recipeName);
            }

            @Override
            public int getCount() {
                return steps.size();
            }
        });

        for (int i = 0; i < steps.size(); i++) {
            if (recipes.get(recipeId).getSteps().equals(stepId)) {
                viewPager.setCurrentItem(i);
                break;
            }
        }

        //FragmentRecipeMediaBinding fragmentRecipeMediaBinding =
    }
}
