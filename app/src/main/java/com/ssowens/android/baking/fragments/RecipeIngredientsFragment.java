package com.ssowens.android.baking.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ssowens.android.baking.R;
import com.ssowens.android.baking.adapters.RecipeRecyclerAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeIngredientsFragment extends Fragment {

    private static final String TAG = RecipeIngredientsFragment.class.getSimpleName();

    RecyclerView recyclerView;
    RecipeRecyclerAdapter recyclerAdapter;

    public RecipeIngredientsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
    }

    public static RecipeCardsFragment newInstance() {
        RecipeCardsFragment fragment = new RecipeCardsFragment();
        fragment.setRetainInstance(true);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView()");
        View view = inflater.inflate(R.layout.fragment_recipe_ingredients, container, false);
        recyclerView = view.findViewById(R.id.ingredient_recycle_view);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));

        // TODO Populate the ingredients list
        recyclerView.setAdapter(recyclerAdapter);
        // Inflate the layout for this fragment
        return view;
    }

}
