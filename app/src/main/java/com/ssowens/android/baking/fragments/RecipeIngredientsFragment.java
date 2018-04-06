package com.ssowens.android.baking.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ssowens.android.baking.R;
import com.ssowens.android.baking.RecipeCollection;
import com.ssowens.android.baking.adapters.RecipeIngredientsStepsAdapter;
import com.ssowens.android.baking.models.Ingredient;
import com.ssowens.android.baking.models.Step;

import java.util.ArrayList;
import java.util.List;

import static com.ssowens.android.baking.activities.RecipeIngredientsActivity.EXTRA_RECIPE_ID;
import static com.ssowens.android.baking.activities.RecipeIngredientsActivity.EXTRA_RECIPE_NAME;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeIngredientsFragment extends Fragment {

    private static final String TAG = RecipeIngredientsFragment.class.getSimpleName();

    RecyclerView recyclerView;
    RecipeIngredientsStepsAdapter recipeIngredientsAdapter;
    int recipeId;
    String recipeName;

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
            recipeName = args.getString(EXTRA_RECIPE_NAME, "Baking");
        }

        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(recipeName);
        }
    }

    public static RecipeIngredientsFragment newInstance(int id, String name) {
        Bundle args = new Bundle();
        args.putInt(EXTRA_RECIPE_ID, id);
        args.putString(EXTRA_RECIPE_NAME, name);

        RecipeIngredientsFragment fragment = new RecipeIngredientsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_ingredients, container,
                false);

        // ReclyclerView for Ingredients and Steps
        recyclerView = view.findViewById(R.id.ingredient_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();
        return view;
    }

    private void updateUI() {

        List<Ingredient> ingredients = RecipeCollection.get(getActivity()).getRecipe(recipeId)
                .getIngredients();
        List<Object> objects = new ArrayList<>();
        objects.add(getContext().getString(R.string.recipe_ingredients_title));
        objects.addAll(ingredients);

        objects.add(getContext().getString(R.string.recipe_steps));
        List<Step> steps = RecipeCollection.get(getActivity()).getRecipe(recipeId).getSteps();
        objects.addAll(steps);
        recipeIngredientsAdapter = new RecipeIngredientsStepsAdapter(objects, recipeId, recipeName);
        recipeIngredientsAdapter.setIngredientList(objects);
        recipeIngredientsAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(recipeIngredientsAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }
}
