package com.ssowens.android.baking.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.ssowens.android.baking.R;
import com.ssowens.android.baking.RecipeCollection;
import com.ssowens.android.baking.models.Recipe;
import com.ssowens.android.baking.models.Step;

import java.util.List;

import static com.ssowens.android.baking.activities.RecipeMediaActivity.EXTRA_ID;
import static com.ssowens.android.baking.activities.RecipeMediaActivity.EXTRA_RECIPE_ID;
import static com.ssowens.android.baking.activities.RecipeMediaActivity.EXTRA_RECIPE_NAME;
import static com.ssowens.android.baking.activities.RecipeMediaActivity.EXTRA_VIDEO_URL;

/**
 * Created by Sheila Owens on 3/30/18.
 */
public class RecipeMediaFragment extends Fragment implements View.OnClickListener,
        PlayerControlView.VisibilityListener {

    private String videoUrl;
    private SimpleExoPlayer exoPlayer;
    public View view;
    private PlayerView playerView;
    private MediaSource videoSource;
    private boolean shouldAutoPlay;
    private DataSource.Factory dataSourceFactory;
    private int stepId;
    private int recipeId;
    private String recipeName;
    private List<Recipe> recipes;
    private ImageButton btnRightRecipe;
    private ImageButton btnLeftRecipe;

    static final String URL = "https://d17h27t6h515a5.cloudfront" +
            ".net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4";
    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();

    public RecipeMediaFragment() {
        // Required empty public constructor
    }

    public static RecipeMediaFragment newInstance(String url, int stepId, int recipeId,
                                                  String recipeName) {
        Bundle args = new Bundle();
        args.putString(EXTRA_VIDEO_URL, url);
        args.putInt(EXTRA_ID, stepId);
        args.putInt(EXTRA_RECIPE_ID, recipeId);
        args.putString(EXTRA_RECIPE_NAME, recipeName);
        RecipeMediaFragment fragment = new RecipeMediaFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            videoUrl = args.getString(EXTRA_VIDEO_URL, URL);
            stepId = args.getInt(EXTRA_ID);
            recipeId = args.getInt(EXTRA_RECIPE_ID);
            recipeName = args.getString(EXTRA_RECIPE_NAME);
        }

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(recipeName);
        }

        recipes = RecipeCollection.get(getActivity()).getRecipes();

        shouldAutoPlay = true;

        // Measures bandwidth during playback. Can be null if not required.
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();

        // Produces DataSource instances through which media data is loaded.
        dataSourceFactory = new DefaultDataSourceFactory(getContext(),
                Util.getUserAgent(getContext(), "Baking"),
                bandwidthMeter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recipe_media, container, false);
        playerView = view.findViewById(R.id.playerView);

        final ViewPager viewPager = view.findViewById(R.id.media_view_pager);

        recipes = RecipeCollection.get(getActivity()).getRecipes();
        final List<Step> steps = recipes.get(recipeId).getSteps();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        viewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Step step = steps.get(position);
                if (!TextUtils.isEmpty(step.getVideoURL())) {
                    videoUrl = step.getVideoURL();
                }
                playerView.hideController();
                return StepsFragment.newInstance(step.getDescription());
            }

            @Override
            public int getCount() {
                return steps.size();
            }
        });
        if (stepId < steps.size()) {
            viewPager.setCurrentItem(stepId);
        }
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // Stop the player and start the next video
                exoPlayer.stop(true);
                initializePlayer();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        initializePlayer();

        btnRightRecipe = view.findViewById(R.id.right_nav);
        btnRightRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.arrowScroll(View.FOCUS_RIGHT);
            }
        });

        btnLeftRecipe = view.findViewById(R.id.left_nav);
        btnLeftRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.arrowScroll(View.FOCUS_LEFT);
            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {

    }

    private void initializePlayer() {

        // Measures bandwidth during playback. Can be null if not required.
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();

        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

        // This is the MediaSource representing the media to be played.
        videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(videoUrl));

        exoPlayer =
                ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);

        // Prepare the player with the source.
        exoPlayer.prepare(videoSource);
        exoPlayer.setPlayWhenReady(shouldAutoPlay);
        playerView.setPlayer(exoPlayer);
    }

    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        exoPlayer.stop();
        exoPlayer.release();
        exoPlayer = null;
    }

    /**
     * Release the player when the activity is destroyed.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    @Override
    public void onVisibilityChange(int visibility) {

    }

    @Override
    public void onPause() {
        super.onPause();
        exoPlayer.stop();
    }

    @Override
    public void onResume() {
        super.onResume();
        exoPlayer.prepare(videoSource);
    }


}
