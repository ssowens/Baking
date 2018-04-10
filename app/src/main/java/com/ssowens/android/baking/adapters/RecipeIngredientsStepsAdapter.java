package com.ssowens.android.baking.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ssowens.android.baking.R;
import com.ssowens.android.baking.databinding.ItemRecipeIngredientBinding;
import com.ssowens.android.baking.databinding.ItemRecipeStepsBinding;
import com.ssowens.android.baking.models.Ingredient;
import com.ssowens.android.baking.models.Step;

import java.util.List;

import static com.ssowens.android.baking.fragments.RecipeIngredientsFragment.Callbacks;

/**
 * Created by Sheila Owens on 3/18/18.
 */

public class RecipeIngredientsStepsAdapter extends RecyclerView.Adapter<RecipeIngredientsStepsAdapter
        .MyViewHolder> implements Callbacks {

    private List<Object> items;
    private int recipeId;
    private String recipeName;
    private ClickListener clickListener;

    private static final String TAG = RecipeIngredientsStepsAdapter.class.getSimpleName();

    public void setIngredientList(List<Object> ingredientsList) {
        this.items = ingredientsList;
    }

    public RecipeIngredientsStepsAdapter(List<Object> ingredientsList, int recipeId, String name,
                                         ClickListener clickListener) {
        this.items = ingredientsList;
        this.recipeId = recipeId;
        this.recipeName = name;
        this.clickListener = clickListener;
    }

    private static final int INGREDIENTS = 1;
    private static final int STEPS = 2;
    private static final int HEADER = 3;


    public interface ClickListener {
        void onItemClicked(String url,
                           int stepId,
                           int recipeId,
                           String recipeName);
    }

    @Override
    public void onStepSelected(String url, int stepId, int recipeId, String recipeName) {
        // TODO
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder");
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        if (viewType == INGREDIENTS) {
            ItemRecipeIngredientBinding recipeIngredientItemBinding = ItemRecipeIngredientBinding
                    .inflate(layoutInflater,
                            parent, false);
            return new MyViewHolder(recipeIngredientItemBinding);
        } else if (viewType == STEPS) {
            ItemRecipeStepsBinding itemRecipeStepsBinding = ItemRecipeStepsBinding.inflate
                    (layoutInflater, parent, false);
            return new MyViewHolder(itemRecipeStepsBinding);
        } else {
            TextView header = new TextView(parent.getContext());
            return new MyViewHolder(header);
        }
    }

    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder");
        final int itemType = getItemViewType(position);
        Object data = items.get(position);
        if (itemType == INGREDIENTS) {
            holder.bindIngredient(data);
        } else if (itemType == STEPS) {
            holder.bindStep(data);
        } else {
            holder.header.setText((String) data);
        }
    }

    @Override
    public int getItemCount() {
        Log.i(TAG, "getItemCount " + items.size());
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ItemRecipeIngredientBinding binding1;
        private ItemRecipeStepsBinding binding2;
        private TextView header;

        public MyViewHolder(TextView textView) {
            super(textView);
            this.header = textView;
        }

        public MyViewHolder(final ItemRecipeIngredientBinding binding) {
            super(binding.getRoot());
            this.binding1 = binding;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Clicked " +
                                    binding.getModel().getIngredient() + " " + binding.getModel()
                                    .getIngredient(),
                            Toast.LENGTH_LONG).show();
                }
            });
        }

        private MyViewHolder(final ItemRecipeStepsBinding binding) {
            super(binding.getRoot());
            this.binding2 = binding;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TextUtils.isEmpty(binding.getModel().getVideoURL())) {
                        Toast.makeText(v.getContext(), v.getContext().getString(R.string
                                        .no_video_avail) +
                                        binding.getModel().getShortDescription(),
                                Toast.LENGTH_LONG).show();
                    } else {
                        clickListener.onItemClicked(binding.getModel().getVideoURL(),
                                binding.getModel().getId(),
                                recipeId,
                                recipeName);
                    }
                }
            });
        }

        private void bindIngredient(Object ingredient) {
            binding1.setModel((Ingredient) ingredient);
            binding1.executePendingBindings();
        }

        private void bindStep(Object step) {
            binding2.setModel((Step) step);
            binding2.executePendingBindings();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof Ingredient) {
            return INGREDIENTS;
        } else if (items.get(position) instanceof Step) {
            return STEPS;
        } else
            return HEADER;
    }


}
