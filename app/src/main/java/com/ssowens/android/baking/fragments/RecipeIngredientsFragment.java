package com.ssowens.android.baking.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ssowens.android.baking.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeIngredientsFragment extends Fragment {


    public RecipeIngredientsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_ingredients, container, false);
    }

}
