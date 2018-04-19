package com.ssowens.android.baking.fragments;


import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import com.google.gson.Gson;
import com.ssowens.android.baking.R;
import com.ssowens.android.baking.RecipeCollection;
import com.ssowens.android.baking.adapters.RecipeIngredientsStepsAdapter;
import com.ssowens.android.baking.models.Ingredient;
import com.ssowens.android.baking.models.Step;

import java.util.ArrayList;
import java.util.List;

import static com.ssowens.android.baking.activities.RecipeIngredientsActivity.EXTRA_RECIPE_ID;
import static com.ssowens.android.baking.activities.RecipeIngredientsActivity.EXTRA_RECIPE_NAME;


public class RecipeIngredientsFragment extends Fragment {

    private static final String TAG = RecipeIngredientsFragment.class.getSimpleName();
    public Callbacks callbacks;
    public static final String JSON_INGREDIENTS_STRING = "ingredientsJsonStr";
    public static final String SHARED_PREF_RECIPE_ID = "recipeId";
    public static final String SHARED_PREF_RECIPE_NAME = "recipeName";

    RecyclerView recyclerView;
    RecipeIngredientsStepsAdapter recipeIngredientsAdapter;
    int recipeId;
    String recipeName;
    private Gson gson;

    public RecipeIngredientsFragment() {
        // Required empty public constructor
    }

    /**
     * Required interface for hosting activities
     */
    public interface Callbacks {
        void onStepSelected(String url, int stepId, int recipeId, String recipeName);
    }

    RecipeIngredientsStepsAdapter.ClickListener clickListener =
            new RecipeIngredientsStepsAdapter.ClickListener() {
                @Override
                public void onItemClicked(String url, int stepId, int recipeId, String recipeName) {
                    callbacks.onStepSelected(url, stepId, recipeId, recipeName);
                }
            };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callbacks = (Callbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callbacks = null;
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

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(recipeName);
        }
        setHasOptionsMenu(true);
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

        // Get the ingredients for the widget and save them into SharedPreferences
        gson = new Gson();
        String ingredientsJsonStr = gson.toJson(ingredients);

        //Save data in SharedPreferences
        PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString
                (JSON_INGREDIENTS_STRING, ingredientsJsonStr).apply();
        PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putInt
                (SHARED_PREF_RECIPE_ID, recipeId).apply();
        PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString
                (SHARED_PREF_RECIPE_NAME, recipeName).apply();

        objects.add(getContext().getString(R.string.recipe_steps));
        List<Step> steps = RecipeCollection.get(getActivity()).getRecipe(recipeId).getSteps();
        objects.addAll(steps);
        recipeIngredientsAdapter = new RecipeIngredientsStepsAdapter(objects, recipeId,
                recipeName, clickListener);
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
