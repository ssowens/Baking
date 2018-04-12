package com.ssowens.android.baking;


import android.content.ComponentName;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.ssowens.android.baking.activities.RecipeMediaActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;

@RunWith(AndroidJUnit4.class)
public class RecipeMediaActivityBasicTest {

    @Rule
    public ActivityTestRule<RecipeMediaActivity> activityMainActivityTestRule =
            new ActivityTestRule<RecipeMediaActivity>(RecipeMediaActivity.class);

    @Test
    public void checkLaunchedAcitivy() {
        intended(hasComponent(new ComponentName(InstrumentationRegistry.getTargetContext(),
                RecipeMediaActivity.class.getName())));
    }
}
