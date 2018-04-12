package com.ssowens.android.baking;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.ssowens.android.baking.activities.MainActivity;
import com.ssowens.android.baking.fragments.RecipeCardsFragment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Sheila Owens on 4/10/18.
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityBasicTest {

    @Rule
    public ActivityTestRule<MainActivity> activityMainActivityTestRule =
            new ActivityTestRule<MainActivity>(MainActivity.class);

    @Before
    public void init() {
        activityMainActivityTestRule.getActivity().getFragmentManager().beginTransaction();
    }

    @Test
    public void checkTextDisplayedInDynamicallyCreatedFragment() {
        RecipeCardsFragment fragment = new RecipeCardsFragment();
        activityMainActivityTestRule.getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, fragment).commit();

        onView(withId(R.id.recycle_view)).check(matches(isDisplayed()));
        // onView(withId(R.id.recycle_view)).check(matches(isDisplayed()));
    }

}
