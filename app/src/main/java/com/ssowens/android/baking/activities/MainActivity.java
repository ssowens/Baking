package com.ssowens.android.baking.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.ssowens.android.baking.R;
import com.ssowens.android.baking.fragments.RecipeCardsFragment;

public class MainActivity extends SingleFragmentActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Fragment createFragment() {
        if (isOnline()) {
            return RecipeCardsFragment.newInstance();
        } else {
            Toast.makeText(this, getString(R.string.no_internet_service),
                    Toast.LENGTH_SHORT).show();
            return null;
        }
    }
}
