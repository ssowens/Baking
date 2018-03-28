package com.ssowens.android.baking.adapterdelegates;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;
import com.ssowens.android.baking.databinding.RecipeIngredientItemBinding;
import com.ssowens.android.baking.models.DisplayableItem;
import com.ssowens.android.baking.models.Ingredient;
import java.util.List;

/**
 * Created by Sheila Owens on 3/27/18.
 */

public class IngredientsAdapterDelegate extends AdapterDelegate<List<DisplayableItem>> {

    private List<DisplayableItem> items;
    private int position;
    private IngredientViewHolder holder;
    private List<Object> payloads;

    @Override
    protected boolean isForViewType(@NonNull List<DisplayableItem> items, int position) {
        return items.get(position) instanceof Ingredient;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        Log.d("Scroll", "StepsAdapterDelegate createViewHolder ");
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RecipeIngredientItemBinding recipeIngredientItemBinding = RecipeIngredientItemBinding.inflate
                (layoutInflater, parent, false);
        return new IngredientViewHolder(recipeIngredientItemBinding);
    }

    @Override
    protected void onBindViewHolder(@NonNull List<DisplayableItem> items, int position,
                                    @NonNull RecyclerView.ViewHolder holder,
                                    @NonNull List<Object> payloads) {

        this.items = items;
        this.position = position;
        this.holder = (IngredientViewHolder) holder;
        this.payloads = payloads;
        Ingredient ingredient = (Ingredient) items.get(position);
        ((IngredientViewHolder) holder).bind(ingredient);
    }

    public static class IngredientViewHolder extends RecyclerView.ViewHolder {
        private final RecipeIngredientItemBinding binding;

        public IngredientViewHolder(final RecipeIngredientItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Clicked " +
                                    binding.getModel().getId() + " " + binding.getModel()
                                    .getMeasure(),
                            Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(v.getContext(), RecipeIngredientsActivity.class);
//                    intent.putExtra("id", binding.getRecipe().getId());
//                    v.getContext().startActivity(intent);
                }
            });
        }

        public void bind(Ingredient ingredient) {
            binding.setModel(ingredient);
            binding.executePendingBindings();
        }
    }
}
