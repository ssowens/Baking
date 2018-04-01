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

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
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
    private PlayerView playerView;
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
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_media, container, false);

        // Initialize the player view.
        playerView = view.findViewById(R.id.playerView);

        // Initialize the player.
        initializePlayer(Uri.parse(videoUrl));

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * Initialize ExoPlayer.
     *
     * @param mediaUri The URI of the sample to play.
     */
    private void initializePlayer(Uri mediaUri) {
        Log.i(TAG, "Sheila mediaUrl" + mediaUri);
        if (exoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelection.Factory adaptiveTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);
            TrackSelector trackSelector = new DefaultTrackSelector(adaptiveTrackSelectionFactory);

            LoadControl loadControl = new DefaultLoadControl();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);

            playerView.setPlayer(exoPlayer);
            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getActivity(), "Baking");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);



//            // Create a default TrackSelector
//            Handler mainHandler = new Handler();
//            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
//            TrackSelection.Factory videoTrackSelectionFactory =
//                    new AdaptiveTrackSelection.Factory(bandwidthMeter);
//            TrackSelector trackSelector =
//                    new DefaultTrackSelector(videoTrackSelectionFactory);
//
//            // Create the player
//            SimpleExoPlayer player =
//                    ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
//            // Bind the player to the view.
//            playerView.setPlayer(player);
//
////            // Prepare the MediaSource.
////            // Measures bandwidth
////            // during playback. Can be null if not required.
////            DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
////            // Produces DataSource instances through which media data is loaded.
////            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
////                    Util.getUserAgent(context, "yourApplicationName"), bandwidthMeter);
////            // This is the MediaSource representing the media to be played.
////            MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
////                    .createMediaSource(mp4VideoUri);
////            // Prepare the player with the source.
////            player.prepare(videoSource);



        }
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
