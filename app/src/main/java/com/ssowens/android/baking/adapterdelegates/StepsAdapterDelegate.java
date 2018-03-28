package com.ssowens.android.baking.adapterdelegates;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;
import com.ssowens.android.baking.databinding.ItemRecipeStepsBinding;
import com.ssowens.android.baking.models.DisplayableItem;
import com.ssowens.android.baking.models.Step;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sheila Owens on 3/26/18.
 */

public class StepsAdapterDelegate extends AdapterDelegate<List<DisplayableItem>> {

    private List<Step> stepsList = new ArrayList<>();
    LayoutInflater inflater;
    private List<DisplayableItem> items;
    private int position;
    private StepViewHolder holder;
    private List<Object> payloads;

    public StepsAdapterDelegate(Activity activity) {
        inflater = activity.getLayoutInflater();
    }

    @Override
    public boolean isForViewType(@NonNull List<DisplayableItem> items, int position) {
        return items.get(position) instanceof Step;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        Log.d("Scroll", "StepsAdapterDelegate createViewHolder ");
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemRecipeStepsBinding itemRecipeStepsBinding = ItemRecipeStepsBinding.inflate
                (layoutInflater, parent, false);
        return new StepViewHolder(itemRecipeStepsBinding);
    }

    @Override
    protected void onBindViewHolder(@NonNull List<DisplayableItem> items, int position,
                                    @NonNull RecyclerView.ViewHolder holder,
                                    @NonNull List<Object> payloads) {

        this.items = items;
        this.position = position;
        this.holder = (StepViewHolder) holder;
        this.payloads = payloads;
        Step step = (Step) items.get(position);
        ((StepViewHolder) holder).bind(step);

        Log.d("Scroll", "StepsAdapterDelegate bind  " + position);
    }

    public static class StepViewHolder extends RecyclerView.ViewHolder {
        private final ItemRecipeStepsBinding binding;

        public StepViewHolder(final ItemRecipeStepsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Clicked " +
                                    binding.getModel().getShortDescription() + " " + binding.getModel()
                                    .getDescription(),
                            Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(v.getContext(), RecipeIngredientsActivity.class);
//                    intent.putExtra("id", binding.getRecipe().getId());
//                    v.getContext().startActivity(intent);
                }
            });
        }

        public void bind(Step step) {
            binding.setModel(step);
            binding.executePendingBindings();
        }
    }

}
