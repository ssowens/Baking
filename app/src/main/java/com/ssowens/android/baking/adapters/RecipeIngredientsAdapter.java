package com.ssowens.android.baking.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ssowens.android.baking.databinding.CardViewItemBinding;
import com.ssowens.android.baking.models.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sheila Owens on 3/18/18.
 */

public class RecipeIngredientsAdapter extends RecyclerView.Adapter<RecipeIngredientsAdapter
        .MyViewHolder> {

    private static final String TAG = RecipeIngredientsAdapter.class.getSimpleName();
    List<Recipe> recipeList = new ArrayList<>();

    public void setRecipeList(List<Recipe> recipeList) {
        this.recipeList = recipeList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Log.i(TAG, "Sheila onCreateViewHolder");
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CardViewItemBinding cardViewItemBinding = CardViewItemBinding.inflate(layoutInflater,
                parent, false);
        return new MyViewHolder(cardViewItemBinding);
    }


    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.i(TAG, "Sheila onBindViewHolder");
        Recipe recipe = recipeList.get(position);
        holder.bind(recipe);
    }

    @Override
    public int getItemCount() {
        Log.i(TAG, "Sheila getItemCount " + recipeList.size());
        return recipeList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final CardViewItemBinding binding;

        public MyViewHolder(final CardViewItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Clicked " + binding.getRecipe().getName(), Toast.LENGTH_LONG).show();
                }
            });
        }

        public void bind(Recipe recipe) {
            binding.setRecipe(recipe);
            binding.executePendingBindings();
        }
    }
}
