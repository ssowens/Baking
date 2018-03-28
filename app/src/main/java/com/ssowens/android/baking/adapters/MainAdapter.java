package com.ssowens.android.baking.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegatesManager;
import com.ssowens.android.baking.adapterdelegates.StepsAdapterDelegate;
import com.ssowens.android.baking.models.DisplayableItem;
import java.util.List;

/**
 * Created by Sheila Owens on 3/26/18.
 */

public class MainAdapter extends RecyclerView.Adapter {

    private AdapterDelegatesManager<List<DisplayableItem>> delegatesManager;
    private List<DisplayableItem> items;

    public MainAdapter(Activity activity, List<DisplayableItem> items) {
        this.items = items;

        // Delegates
        delegatesManager = new AdapterDelegatesManager<>();
        delegatesManager.addDelegate(new StepsAdapterDelegate(activity));
    }

    @Override
    public int getItemViewType(int position) {
        return delegatesManager.getItemViewType(items, position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return delegatesManager.onCreateViewHolder(parent,viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        delegatesManager.onBindViewHolder(items, position, holder);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
