package com.ssowens.android.baking.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ssowens.android.baking.databinding.RecipeIngredientItemBinding;
import com.ssowens.android.baking.models.Ingredient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sheila Owens on 3/18/18.
 */

public class RecipeIngredientsAdapter extends RecyclerView.Adapter<RecipeIngredientsAdapter
        .MyViewHolder> {

    private List<Object> items;

    private static final String TAG = RecipeIngredientsAdapter.class.getSimpleName();
    private List<Ingredient> ingredientsList = new ArrayList<>();

    public void setIngredientList(List<Ingredient> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    public RecipeIngredientsAdapter(List<Ingredient> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder");

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RecipeIngredientItemBinding recipeIngredientItemBinding = RecipeIngredientItemBinding.inflate(layoutInflater,
                parent, false);
        return new MyViewHolder(recipeIngredientItemBinding);
    }


    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder");
        Object ingredient = ingredientsList.get(position);
        holder.bind(ingredient);
    }

    @Override
    public int getItemCount() {
        Log.i(TAG, "getItemCount " + ingredientsList.size());
        return ingredientsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final RecipeIngredientItemBinding binding;

        public MyViewHolder(final RecipeIngredientItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Clicked " +
                                    binding.getModel().getIngredient() + " " + binding.getModel()
                                    .getIngredient(),
                            Toast.LENGTH_LONG).show();
                    //       Intent intent = new Intent(v.getContext(), RecipeStepsActivity.class);
                    //       intent.putExtra("id", binding.getModel().getId());
                    //       v.getContext().startActivity(intent);
                }
            });
        }

        public void bind(Object ingredient) {
            binding.setModel((Ingredient) ingredient);
            binding.executePendingBindings();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (ingredientsList.get(position) instanceof Ingredient)
            return VIEW_TYPES.Ingredient;
        return super.getItemViewType(position);
    }

    private class VIEW_TYPES {
        public static final int Ingredient = 1;
        public static final int Step = 2;
    }
}
