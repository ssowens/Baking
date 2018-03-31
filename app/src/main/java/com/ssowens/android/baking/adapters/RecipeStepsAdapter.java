package com.ssowens.android.baking.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ssowens.android.baking.databinding.ItemRecipeStepsBinding;
import com.ssowens.android.baking.models.Step;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sheila Owens on 3/25/18.
 */

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.MyViewHolder> {

    private static final String TAG = RecipeStepsAdapter.class.getSimpleName();
    private List<Step> stepsList = new ArrayList<>();

    public void setStepList(List<Step> stepsList) {
        this.stepsList = stepsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder");

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemRecipeStepsBinding recipeStepsItemBinding = ItemRecipeStepsBinding.inflate
                (layoutInflater,
                        parent, false);
        return new RecipeStepsAdapter.MyViewHolder(recipeStepsItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Step step = stepsList.get(position);
        holder.bind(step);
    }


    @Override
    public int getItemCount() {
        return stepsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final ItemRecipeStepsBinding binding;

        public MyViewHolder(final ItemRecipeStepsBinding binding) {
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
