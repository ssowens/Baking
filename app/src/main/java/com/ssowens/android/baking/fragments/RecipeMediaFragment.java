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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
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
import com.ssowens.android.baking.activities.RecipeMediaActivity;
import com.ssowens.android.baking.models.Recipe;
import com.ssowens.android.baking.models.Step;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.exoplayer2.Player.STATE_READY;
import static com.ssowens.android.baking.activities.RecipeMediaActivity.EXTRA_ID;
import static com.ssowens.android.baking.activities.RecipeMediaActivity.EXTRA_RECIPE_ID;
import static com.ssowens.android.baking.activities.RecipeMediaActivity.EXTRA_RECIPE_NAME;
import static com.ssowens.android.baking.activities.RecipeMediaActivity.EXTRA_VIDEO_URL;

/**
 * Created by Sheila Owens on 3/30/18.
 */
public class RecipeMediaFragment extends Fragment implements View.OnClickListener,
        PlayerControlView.VisibilityListener, RecipeMediaActivity.OnConfigurationRotate {

    private String videoUrl;
    private SimpleExoPlayer exoPlayer;
    public View view;
    private PlayerView playerView;
    private ViewPager viewPager;
    private TextView noVideoTxtView;
    private ProgressBar progressBar;
    private MediaSource videoSource;
    private boolean shouldAutoPlay;
    private DataSource.Factory dataSourceFactory;
    private int stepId;
    private int recipeId;
    private String recipeName;
    private List<Recipe> recipes;
    private ImageButton btnRightRecipe;
    private ImageButton btnLeftRecipe;
    private View buttonLayout;
    private ArrayList<Step> steps = new ArrayList<>();


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
        noVideoTxtView = view.findViewById(R.id.no_video);
        progressBar = view.findViewById(R.id.progress);

        viewPager = view.findViewById(R.id.media_view_pager);
        buttonLayout = view.findViewById(R.id.buttonLayout);
        recipes = RecipeCollection.get(getActivity()).getRecipes();
        steps.clear();

        for (Recipe recipe : recipes) {
            if (recipe.getId() == recipeId) {
                steps.addAll(recipe.getSteps());
                break;
            }
        }

        if (!steps.isEmpty()) {
            updateVideoUrl(stepId);
        }

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        viewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Step step = steps.get(position);
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
            Step step = steps.get(stepId);
            if (!TextUtils.isEmpty(step.getVideoURL())) {
                videoUrl = step.getVideoURL();
            }
        }
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // Stop the player and start the next video
                releasePlayer();
                updateVideoUrl(position);
                playVideo();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // initializePlayer();

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

    private void updateVideoUrl(int index) {
        videoUrl = "";
        if (index < steps.size()) {
            Step step = steps.get(index);
            if (!TextUtils.isEmpty(step.getVideoURL())) {
                videoUrl = step.getVideoURL();
            }
        }

    }

    private void playVideo() {
        if (videoUrl.isEmpty()) {
            noVideoTxtView.setVisibility(View.VISIBLE);
            playerView.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
        } else {
            noVideoTxtView.setVisibility(View.GONE);
            playerView.setVisibility(View.VISIBLE);
            initializePlayer();
        }
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
        exoPlayer.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {
            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (playbackState >= STATE_READY) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {

            }
        });

        playerView.setPlayer(exoPlayer);
    }

    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        if (exoPlayer != null) {
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onVisibilityChange(int visibility) {

    }

    /**
     * As our app can be visible but not active in split window mode, we need to initialize the
     * player in onStart.
     */
    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    /**
     * Before API Level 24 there is no guarantee of onStop being called. So we have to release the
     * player as early as possible in onPause.
     */
    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }


    /**
     * Starting with API Level 24 (which brought multi and split window mode) onStop is
     * guaranteed to be called and in the paused mode our activity is eventually still visible.
     * Hence we need to wait releasing until onStop.
     */
    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    /**
     * Before API level 24 we wait as long as possible until we grab resources, so we wait until
     * onResume before initializing the player.
     */
    @Override
    public void onResume() {
        super.onResume();
        //exoPlayer.prepare(videoSource);
        if ((Util.SDK_INT <= 23 || exoPlayer == null)) {
            playVideo();
        }
    }

    @Override
    public void onRotate(boolean landscape) {
        if (landscape) {
            viewPager.setVisibility(View.GONE);
            buttonLayout.setVisibility(View.GONE);
        } else {
            viewPager.setVisibility(View.VISIBLE);
            buttonLayout.setVisibility(View.VISIBLE);
        }
    }


}
