package com.ssowens.android.baking;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.ssowens.android.baking.activities.SingleFragmentActivity;

import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * Created by Sheila Owens on 4/9/18.
 */

@RunWith(AndroidJUnit4.class)
public class SingleFragmentActivityBasicTest {

    @Rule
    public ActivityTestRule<SingleFragmentActivity> activityTestRule
            = new ActivityTestRule<>(SingleFragmentActivity.class);

}
