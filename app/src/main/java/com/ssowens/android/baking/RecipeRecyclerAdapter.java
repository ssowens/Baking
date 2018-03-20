package com.ssowens.android.baking;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ssowens.android.baking.databinding.CardViewItemBinding;
import com.ssowens.android.baking.models.Recipe;

import java.util.List;

/**
 * Created by Sheila Owens on 3/18/18.
 */

public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecipeRecyclerAdapter.MyViewHolder> {

    private static final String TAG = RecipeRecyclerAdapter.class.getSimpleName();
    List<Recipe> recipeList;

    public RecipeRecyclerAdapter(List<Recipe> list) {
        this.recipeList = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CardViewItemBinding cardViewItemBinding = CardViewItemBinding.inflate(layoutInflater,
                parent, false);
        return new MyViewHolder(cardViewItemBinding);
    }


    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);
        holder.bind(recipe);
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private final CardViewItemBinding binding;

        public MyViewHolder(CardViewItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Recipe recipe) {
            binding.setRecipe(recipe);
            binding.executePendingBindings();
        }
    }
}
