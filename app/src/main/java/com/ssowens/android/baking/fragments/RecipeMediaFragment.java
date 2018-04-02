package com.ssowens.android.baking.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import static com.ssowens.android.baking.activities.RecipeMediaActivity.EXTRA_VIDEO_URL;

/**
 * Created by Sheila Owens on 3/30/18.
 */
public class RecipeMediaFragment extends Fragment implements View.OnClickListener, PlayerControlView.VisibilityListener {

    private static final String TAG = RecipeMediaFragment.class.getSimpleName();

    String videoUrl;
    private SimpleExoPlayer exoPlayer;
    public View view;
    private PlayerView playerView;
    private MediaSource videoSource;
    private boolean shouldAutoPlay;
    private DataSource.Factory dataSourceFactory;

    static final String URL = "https://d17h27t6h515a5.cloudfront" +
            ".net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4";
    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();

    public RecipeMediaFragment() {
        // Required empty public constructor
    }

    public static RecipeMediaFragment newInstance(String url) {
        Bundle args = new Bundle();
        Log.i(TAG, "Sheila This is the URL => " + url);
        args.putString(EXTRA_VIDEO_URL, url);
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
        }

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

        // Initialize the player.
        initializePlayer();

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
}
