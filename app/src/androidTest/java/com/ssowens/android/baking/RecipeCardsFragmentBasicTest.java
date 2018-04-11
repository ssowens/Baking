package com.ssowens.android.baking;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.ssowens.android.baking.activities.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Sheila Owens on 4/10/18.
 */

@RunWith(AndroidJUnit4.class)
public class RecipeCardsFragmentBasicTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule
            = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clickRecipeCard() {
        // Find the View
        // Perform Action on the View
        onView((withId(R.id.name))).perform(click());
    }
}
