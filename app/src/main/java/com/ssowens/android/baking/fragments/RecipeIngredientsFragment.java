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
import com.ssowens.android.baking.RecipeCollection;
import com.ssowens.android.baking.adapters.RecipeIngredientsAdapter;
import com.ssowens.android.baking.models.Ingredient;
import com.ssowens.android.baking.models.Recipe;

import java.util.List;

import static com.ssowens.android.baking.activities.RecipeIngredientsActivity.EXTRA_RECIPE_ID;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeIngredientsFragment extends Fragment {

    private static final String TAG = RecipeIngredientsFragment.class.getSimpleName();

    RecyclerView recyclerView;
    RecipeIngredientsAdapter recipeIngredientsAdapter;
    int recipeId;
    Recipe recipe;

    public RecipeIngredientsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            recipeId = args.getInt(EXTRA_RECIPE_ID, 0);
        }
    }

    public static RecipeIngredientsFragment newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt(EXTRA_RECIPE_ID, id);
        Log.i(TAG, "This is the recipe*& id => " + id);

        RecipeIngredientsFragment fragment = new RecipeIngredientsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView()");

        View view = inflater.inflate(R.layout.fragment_recipe_ingredients, container, false);
        recyclerView = view.findViewById(R.id.ingredient_recycle_view);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));

        updateUI();
        return view;
    }

    private void updateUI() {

        List<Ingredient> ingredients = RecipeCollection.get(getActivity()).getRecipe(recipeId)
                 .getIngredients();

        recipeIngredientsAdapter = new RecipeIngredientsAdapter();
        recipeIngredientsAdapter.setIngredientList(ingredients);
        recipeIngredientsAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(recipeIngredientsAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }
}
