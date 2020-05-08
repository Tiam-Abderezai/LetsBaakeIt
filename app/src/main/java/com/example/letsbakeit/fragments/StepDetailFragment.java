package com.example.letsbakeit.fragments;


import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.letsbakeit.R;
import com.example.letsbakeit.databinding.FragmentStepBinding;
import com.example.letsbakeit.databinding.FragmentDetailStepBinding;
import com.example.letsbakeit.databinding.ItemStepBinding;
import com.example.letsbakeit.model.Recipe;
import com.example.letsbakeit.model.Step;
import com.example.letsbakeit.utils.Client;
import com.example.letsbakeit.utils.Service;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StepDetailFragment extends Fragment {

    // Exoplayer member variables
    private PlaybackStateListener playbackStateListener;
    private static final String TAG = StepDetailFragment.class.getName();
    private PlayerView playerView;
    private SimpleExoPlayer player;
    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;

    private static final String INTENT_RECIPE_KEY = "recipeObject";


    private FragmentDetailStepBinding binding;

    public StepDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_step, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedViewModel viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        viewModel.getSelected().observe(getViewLifecycleOwner(), step -> {
            binding.tvStepDetailNumber.setText("STEP " + Integer.toString(step.getStepId()));
            binding.tvStepDetailDesc.setText(step.getDescription());
            binding.videoView.setPlayer(player);
            playbackStateListener = new PlaybackStateListener();


        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    private void initializePlayer() {
        if (player == null) {
            DefaultTrackSelector trackSelector = new DefaultTrackSelector();
            trackSelector.setParameters(
                    trackSelector.buildUponParameters().setMaxVideoSizeSd());
            player = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);
        }

        // Pull the STEPs from the server for the selected RECIPE
        final Service service = Client.getClient().create(Service.class);
        Call<List<Recipe>> callRecipes = service.getRecipes();
        callRecipes.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if (!response.isSuccessful()) {
                    System.out.println(response.code());
                    return;
                }
                final Recipe recipe = getActivity().getIntent().getExtras().getParcelable(INTENT_RECIPE_KEY);

                List<Recipe> recipes = response.body();

                // Pull the selected STEP video and display it
                SharedViewModel viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
                viewModel.getSelected().observe(getViewLifecycleOwner(), step -> {

                    String vidLink = recipes.get(recipe.getmId() - 1).getmSteps()
                            .get(step.getStepId()).getVideoUrl();
                    Uri uri = Uri.parse(vidLink);
                    MediaSource mediaSource = buildMediaSource(uri);

                    player.setPlayWhenReady(playWhenReady);
                    player.seekTo(currentWindow, playbackPosition);
                    player.addListener(playbackStateListener);
                    player.prepare(mediaSource, false, false);


                });

            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });


    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.removeListener(playbackStateListener);
            player.release();
            player = null;
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(getActivity(), "exoplayer-codelab");
        return new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri);
    }


    private class PlaybackStateListener implements Player.EventListener {

        @Override
        public void onPlayerStateChanged(boolean playWhenReady,
                                         int playbackState) {
            String stateString;
            switch (playbackState) {
                case ExoPlayer.STATE_IDLE:
                    stateString = "ExoPlayer.STATE_IDLE      -";
                    break;
                case ExoPlayer.STATE_BUFFERING:
                    stateString = "ExoPlayer.STATE_BUFFERING -";
                    break;
                case ExoPlayer.STATE_READY:
                    stateString = "ExoPlayer.STATE_READY     -";
                    break;
                case ExoPlayer.STATE_ENDED:
                    stateString = "ExoPlayer.STATE_ENDED     -";
                    break;
                default:
                    stateString = "UNKNOWN_STATE             -";
                    break;
            }
            Log.d(TAG, "changed state to " + stateString
                    + " playWhenReady: " + playWhenReady);
        }
    }


}
