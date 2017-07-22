package com.abdelmeged.ahmed.nourplayer.ui;

import android.app.ProgressDialog;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.abdelmeged.ahmed.nourplayer.R;
import com.abdelmeged.ahmed.nourplayer.app.App;
import com.abdelmeged.ahmed.nourplayer.model.Sura;
import com.abdelmeged.ahmed.nourplayer.utils.Constants;
import com.abdelmeged.ahmed.nourplayer.utils.Utilities;
import com.danikula.videocache.CacheListener;
import com.danikula.videocache.HttpProxyCacheServer;


import java.io.File;

public class SuraPlayerActivity extends LifecycleActivity implements View.OnClickListener, CacheListener {

    /**
     * UI Element
     */
    private Toast toast;
    private TextView suraNmaeTextView;
    private Button playButton, pauseButton;
    private SeekBar suraProgress;
    private ProgressDialog progressDialog;


    /**
     * Tag for debugging
     */
    private static final String LOG_TAG = SuraPlayerActivity.class.getSimpleName();

    private Sura currentSura;
    private int resumePosition = 0;

    /**
     * Audio
     */
    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;

    /**
     * This listener gets triggered whenever the audio focus changes
     * (i.e., we gain or lose audio focus because of another app or device).
     */
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.

                // Pause playback and reset player to the start of the file. That way, we can
                // play the word from the beginning when we resume playback.
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sura_player);

        initializeScreen();
        if (getIntent().getSerializableExtra(Constants.EXTRA_SURA) != null) {
            currentSura = (Sura) getIntent().getSerializableExtra(Constants.EXTRA_SURA);
        } else {
            showToast("Can't play this sura");
            finish();
        }

        // Create and setup the {@link AudioManager} to request audio focus
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        releaseMediaPlayer();

        if (currentSura != null) {
            suraNmaeTextView.setText(currentSura.getName());
        }

        //Add seek bar observer
        getLifecycle().addObserver(new AudioProgressUpdater());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play_button:
                resumeMedia();
                break;
            case R.id.pause_button:
                pauseMedia();
                break;
        }
    }

    private void startSura() {
        HttpProxyCacheServer proxy = App.getProxy(this);
        proxy.registerCacheListener(this, currentSura.getDownloadUrl());
        String proxyUrl = proxy.getProxyUrl(Constants.BASE_URL + "uc?export=download&id=" + currentSura.getDownloadUrl());

        int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            if (currentSura.getDownloadUrl().startsWith("/data")) {
                mMediaPlayer = MediaPlayer.create(SuraPlayerActivity.this, Uri.parse(currentSura.getDownloadUrl()));
                mMediaPlayer.start();
                suraProgress.setVisibility(View.VISIBLE);
            } else {
                suraProgress.setVisibility(View.INVISIBLE);
                if (proxyUrl != null) {
                    if (Utilities.isAvailableInternetConnection(this)) {
                        new createPLayer().execute(proxyUrl);
                    } else {
                        if (proxyUrl.startsWith("file://")) {
                            new createPLayer().execute(proxyUrl);
                        } else {
                            new createPLayer().execute(proxyUrl);
                            showToast("File not saved check internet connection");
                        }
                    }
                } else {
                    showToast("Unknown error");
                }
            }

        }

    }

    private void checkCachedState() {
        HttpProxyCacheServer proxy = App.getProxy(this);
        boolean fullyCached = proxy.isCached(currentSura.getDownloadUrl());
        if (fullyCached) {
            suraProgress.setSecondaryProgress(100);
        }
    }

    /**
     * Fast way to call Toast
     */
    private void showToast(String message) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(SuraPlayerActivity.this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (toast != null) {
            toast.cancel();
        }
        App.getProxy(this).unregisterCacheListener(this);
    }

    /**
     * Link the layout element from XML to Java
     */
    private void initializeScreen() {
        suraNmaeTextView = (TextView) findViewById(R.id.sura_name_textView);
        playButton = (Button) findViewById(R.id.play_button);
        pauseButton = (Button) findViewById(R.id.pause_button);
        suraProgress = (SeekBar) findViewById(R.id.sura_progress_seek_bar);

        playButton.setOnClickListener(this);
        pauseButton.setOnClickListener(this);
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;

            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (currentSura != null) {
            checkCachedState();
            startSura();

            suraProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    int audioPosition = mMediaPlayer.getDuration() * suraProgress.getProgress() / 100;
                    mMediaPlayer.seekTo(audioPosition);
                }
            });
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    @Override
    public void onCacheAvailable(File cacheFile, String url, int percentsAvailable) {
        suraProgress.setSecondaryProgress(percentsAvailable);
    }


    private void updateAudioProgress() {
        if (mMediaPlayer != null) {
            if (mMediaPlayer.getDuration() > 0) {
                int videoProgress = mMediaPlayer.getCurrentPosition() * 100 / mMediaPlayer.getDuration();
                suraProgress.setProgress(videoProgress);
            }
        }
    }

    private final class AudioProgressUpdater extends Handler implements LifecycleObserver {

        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        void start() {
            sendEmptyMessage(0);
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        void stop() {
            removeMessages(0);
        }

        @Override
        public void handleMessage(Message msg) {
            updateAudioProgress();
            sendEmptyMessageDelayed(0, 500);
        }
    }

    private class createPLayer extends AsyncTask<String, Void, MediaPlayer> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(SuraPlayerActivity.this, "", "Buffring");
        }

        @Override
        protected MediaPlayer doInBackground(String... params) {
            return MediaPlayer.create(SuraPlayerActivity.this, Uri.parse(params[0]));

        }


        @Override
        protected void onPostExecute(MediaPlayer aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            mMediaPlayer = aVoid;
            if (mMediaPlayer != null) {
                mMediaPlayer.seekTo(0);
                mMediaPlayer.start();
            }
        }
    }

    private void pauseMedia() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            resumePosition = mMediaPlayer.getCurrentPosition();
        }
    }

    private void resumeMedia() {
        if (!mMediaPlayer.isPlaying()) {
            mMediaPlayer.seekTo(resumePosition);
            mMediaPlayer.start();
        }
    }
}
