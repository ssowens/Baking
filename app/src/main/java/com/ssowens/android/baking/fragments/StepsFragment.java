package com.ssowens.android.baking.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class StepsFragment extends Fragment {

    public static StepsFragment newInstance(String step) {
        StepsFragment fragment = new StepsFragment();
        Bundle arg = new Bundle();
        arg.putString("content", step);
        fragment.setArguments(arg);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        String step = getArguments().getString("content");

        TextView text = new TextView(getActivity());
        text.setText(step);
        return text;
    }



}
