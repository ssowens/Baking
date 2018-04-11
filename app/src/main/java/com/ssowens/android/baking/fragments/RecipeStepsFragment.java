package com.ssowens.android.baking.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ssowens.android.baking.R;
import com.ssowens.android.baking.RecipeCollection;
import com.ssowens.android.baking.adapters.RecipeStepsAdapter;
import com.ssowens.android.baking.models.Step;

import java.util.List;

import static com.ssowens.android.baking.activities.RecipeIngredientsActivity.EXTRA_RECIPE_ID;

/**
 * Created by Sheila Owens on 3/25/18.
 */

public class RecipeStepsFragment extends Fragment {

    private static final String TAG = RecipeStepsFragment.class.getSimpleName();
    int recipeId;
    RecyclerView recyclerView;
    RecipeStepsAdapter recipeStepsAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            recipeId = args.getInt(EXTRA_RECIPE_ID, 0);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_ingredients, container, false);
        recyclerView = view.findViewById(R.id.ingredient_recycle_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        updateUI();
        return view;
    }

    public static RecipeStepsFragment newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt(EXTRA_RECIPE_ID, id);

        RecipeStepsFragment fragment = new RecipeStepsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void updateUI() {
        List<Step> steps = RecipeCollection.get(getActivity()).getRecipe(recipeId).getSteps();
        recipeStepsAdapter = new RecipeStepsAdapter();
        recipeStepsAdapter.setStepList(steps);
        recyclerView.setAdapter(recipeStepsAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }
}
