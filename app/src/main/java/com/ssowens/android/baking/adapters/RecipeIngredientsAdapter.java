package com.ssowens.android.baking.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ssowens.android.baking.databinding.ItemRecipeStepsBinding;
import com.ssowens.android.baking.databinding.RecipeIngredientItemBinding;
import com.ssowens.android.baking.models.Ingredient;
import com.ssowens.android.baking.models.Step;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sheila Owens on 3/18/18.
 */

public class RecipeIngredientsAdapter extends RecyclerView.Adapter<RecipeIngredientsAdapter
        .MyViewHolder> {

    private List<Object> items = new ArrayList<>();

    private static final String TAG = RecipeIngredientsAdapter.class.getSimpleName();

    public void setIngredientList(List<Object> ingredientsList) {
        this.items = ingredientsList;
    }

    public RecipeIngredientsAdapter(List<Object> ingredientsList) {
        this.items = ingredientsList;
    }

    private static final int INGREDIENTS = 1;
    private static final int STEPS = 2;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder");
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        if (viewType == INGREDIENTS) {
            RecipeIngredientItemBinding recipeIngredientItemBinding = RecipeIngredientItemBinding
                    .inflate(layoutInflater,
                            parent, false);
            return new MyViewHolder(recipeIngredientItemBinding);
        } else {
            ItemRecipeStepsBinding itemRecipeStepsBinding = ItemRecipeStepsBinding.inflate
                    (layoutInflater, parent, false);
            return new MyViewHolder(itemRecipeStepsBinding);
        }
    }


    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder");
        final int itemType = getItemViewType(position);

        if (itemType == INGREDIENTS) {
            Object ingredient = items.get(position);
            holder.bindIngredient(ingredient);
        } else {
            Object step = items.get(position);
            holder.bindStep(step);
        }
    }

    @Override
    public int getItemCount() {
        Log.i(TAG, "getItemCount " + items.size());
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private RecipeIngredientItemBinding binding1;
        private ItemRecipeStepsBinding binding2;

        public MyViewHolder(final RecipeIngredientItemBinding binding) {
            super(binding.getRoot());
            this.binding1 = binding;
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

        public MyViewHolder(final ItemRecipeStepsBinding binding) {
            super(binding.getRoot());
            this.binding2 = binding;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Clicked " +
                                    binding.getModel().getDescription() + " " + binding.getModel()
                                    .getShortDescription(),
                            Toast.LENGTH_LONG).show();
                }
            });
        }

        public void bindIngredient(Object ingredient) {
            binding1.setModel((Ingredient) ingredient);
            binding1.executePendingBindings();
        }

        public void bindStep(Object step) {
            binding2.setModel((Step) step);
            binding2.executePendingBindings();
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof Ingredient) {
            Log.i(TAG, "Sheila instance of Ingredients");
            return INGREDIENTS;
        } else if (items.get(position) instanceof Step) {
            Log.i(TAG, "Sheila instance of Ingredients");
            return STEPS;
        } else
            return super.getItemViewType(position);
    }


}
