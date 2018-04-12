package com.ssowens.android.baking;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.ssowens.android.baking.activities.RecipeIngredientsActivity;
import com.ssowens.android.baking.activities.RecipeMediaActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Sheila Owens on 4/12/18.
 */

@RunWith(AndroidJUnit4.class)
public class RecipeIngredientsActivityBasicTest {

    @Rule
    public ActivityTestRule<RecipeIngredientsActivity> activityActivityTestRule
            = new ActivityTestRule<>(RecipeIngredientsActivity.class);

    @Test
    public void testCallingRecipeMediaActivity() {
        Context targetContext = InstrumentationRegistry.getInstrumentation()
                .getTargetContext();
        Intent intent = new Intent(targetContext, RecipeMediaActivity.class);
        intent.putExtra("videoUrl", "https://d17h27t6h515a5.cloudfront\" +\n" +
                "            \".net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4");
        intent.putExtra("id", "1");
        intent.putExtra("recipeId", "1");
        intent.putExtra("name", "Chocolate Cake");

        activityActivityTestRule.launchActivity(intent);

        /* Your activity is initialized and ready to go. */
    }



}
