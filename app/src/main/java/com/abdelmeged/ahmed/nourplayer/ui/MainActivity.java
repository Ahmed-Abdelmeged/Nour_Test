package com.abdelmeged.ahmed.nourplayer.ui;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.abdelmeged.ahmed.nourplayer.R;
import com.abdelmeged.ahmed.nourplayer.app.App;
import com.abdelmeged.ahmed.nourplayer.model.Download;
import com.abdelmeged.ahmed.nourplayer.model.Sura;
import com.abdelmeged.ahmed.nourplayer.service.AudioDownloadService;
import com.abdelmeged.ahmed.nourplayer.ui.adapter.SuraClickCallbacks;
import com.abdelmeged.ahmed.nourplayer.ui.adapter.SuraDownloadClickCallbacks;
import com.abdelmeged.ahmed.nourplayer.ui.adapter.SuraRecyclerAdapter;
import com.abdelmeged.ahmed.nourplayer.utils.Constants;
import com.abdelmeged.ahmed.nourplayer.utils.DownloadUtils;
import com.abdelmeged.ahmed.nourplayer.utils.QuranIndex;
import com.abdelmeged.ahmed.nourplayer.utils.StreamingUtils;
import com.abdelmeged.ahmed.nourplayer.utils.Utilities;

import net.gotev.speech.GoogleVoiceTypingDisabledException;
import net.gotev.speech.Speech;
import net.gotev.speech.SpeechDelegate;
import net.gotev.speech.SpeechRecognitionNotAvailable;
import net.gotev.speech.ui.SpeechProgressView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.abdelmeged.ahmed.nourplayer.utils.Constants.MESSAGE_PROGRESS;

public class MainActivity extends AppCompatActivity implements SuraClickCallbacks, SuraDownloadClickCallbacks,
        SpeechDelegate {

    /**
     * UI Element
     */
    private RecyclerView suraRecycler;
    private SuraRecyclerAdapter suraRecyclerAdapter;

    @BindView(R.id.voice_recognition_fab)
    FloatingActionButton voiceFab;

    @BindView(R.id.speech_progress_view)
    SpeechProgressView speechProgressView;

    @BindView(R.id.speech_progress_container)
    LinearLayout speechProgressContainer;


    private Toast toast;

    /**
     * Tag for debugging
     */
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int REQUEST_MICROPHONE = 2;

    ArrayList<Sura> suras = new ArrayList<Sura>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        suraRecycler = (RecyclerView) findViewById(R.id.sura_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        suraRecycler.setLayoutManager(layoutManager);
        suraRecycler.setHasFixedSize(true);

        suraRecyclerAdapter = new SuraRecyclerAdapter(this, this, this);
        suraRecycler.setAdapter(suraRecyclerAdapter);

        suras.add(new Sura("Al-Baqara", "0Bz9EiHndgROYLUU5anNWWGJsX1k", QuranIndex.BQR));
        suras.add(new Sura("Al-Kawthar", "0Bz9EiHndgROYeHlFLTdiekFKREE", QuranIndex.KWA));
        suras.add(new Sura("Ar-Room", "0Bz9EiHndgROYTndObTRsWlBZMTg", QuranIndex.ROM));
        suras.add(new Sura("Al-Mulk", "0Bz9EiHndgROYZGVnMmFYaFd1WTg", QuranIndex.MLK));

        suraRecyclerAdapter.setSuras(suras);

        ButterKnife.bind(this);

        //Set the speech progress color
        int[] colors = {
                ContextCompat.getColor(this, android.R.color.black),
                ContextCompat.getColor(this, android.R.color.darker_gray),
                ContextCompat.getColor(this, android.R.color.black),
                ContextCompat.getColor(this, android.R.color.holo_orange_dark),
                ContextCompat.getColor(this, android.R.color.holo_red_dark)
        };
        speechProgressView.setColors(colors);


        registerReceiver();
    }

    @OnClick(R.id.voice_recognition_fab)
    public void onVoiceButtonClicked() {
        if (Speech.getInstance().isListening()) {
            Speech.getInstance().stopListening();
        } else {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        REQUEST_MICROPHONE);
            } else {
                onRecordAudioPermissionGranted();
            }
        }
    }

    @Override
    public void onSuraClick(Sura sura) {
        Intent playSuraIntent = new Intent(MainActivity.this, SuraPlayerActivity.class);
        if (DownloadUtils.isSuraDownloaded(sura.getQuranIndex(), this)) {
            sura.setDownloadUrl(DownloadUtils.getSuraUri(sura.getQuranIndex(), this));
            playSuraIntent.putExtra(Constants.EXTRA_SURA, sura);
        } else {
            playSuraIntent.putExtra(Constants.EXTRA_SURA, sura);
        }
        startActivity(playSuraIntent);
    }

    @Override
    public void onSuraDownloadClick(Sura sura) {
        if (checkPermission()) {
            startDownload(sura);
        } else {
            requestPermission();
        }
    }

    private void startDownload(Sura sura) {
        Intent intent = new Intent(this, AudioDownloadService.class);
        intent.putExtra(Constants.EXTRA_SURA_DOWNLOAD, sura);
        startService(intent);
    }

    private void registerReceiver() {
        LocalBroadcastManager bManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MESSAGE_PROGRESS);
        bManager.registerReceiver(broadcastReceiver, intentFilter);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(MESSAGE_PROGRESS)) {

                Download download = intent.getParcelableExtra("download");
                if (download.getProgress() == 100) {
                    suraRecyclerAdapter.notifyDataSetChanged();
                } else {
                }
            }
        }
    };

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }


    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                }
                break;
            case REQUEST_MICROPHONE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    showToast(getString(R.string.permission_required));
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_clear_cache:
                try {
                    StreamingUtils.cleanAudioCacheDir(this);
                } catch (IOException e) {
                    showToast("Error cleaning cache");
                }
                break;
            case R.id.item_clear_data:
                try {
                    DownloadUtils.cleanAudioDownloadedDir(this);
                } catch (IOException e) {
                    showToast("Error cleaning data");
                }
                suraRecyclerAdapter.notifyDataSetChanged();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Fast way to call Toast
     */
    private void showToast(String message) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Speech.getInstance().shutdown();
        if (toast != null) {
            toast.cancel();
        }
    }

    private void onRecordAudioPermissionGranted() {
        voiceFab.setVisibility(View.GONE);
        speechProgressContainer.setVisibility(View.VISIBLE);

        try {
            Speech.getInstance().stopTextToSpeech();
            Speech.getInstance().startListening(speechProgressView, MainActivity.this);

        } catch (SpeechRecognitionNotAvailable exc) {
            Utilities.showSpeechNotSupportedDialog(this);

        } catch (GoogleVoiceTypingDisabledException exc) {
            Utilities.showEnableGoogleVoiceTyping(this);
        }
    }

    @Override
    public void onStartOfSpeech() {
        //Log.e(LOG_TAG, "speech recognition is now active");
    }

    @Override
    public void onSpeechRmsChanged(float value) {
        //Log.e(LOG_TAG, "Speech recognition rms is now " + value + "dB");
    }

    @Override
    public void onSpeechPartialResults(List<String> results) {
        for (String partial : results) {
            //Log.e(LOG_TAG,partial);
        }
    }

    @Override
    public void onSpeechResult(String result) {
        voiceFab.setVisibility(View.VISIBLE);
        speechProgressContainer.setVisibility(View.GONE);

        if (result.isEmpty()) {
            Speech.getInstance().say(getString(R.string.repeat));
        } else {
            // Speech.getInstance().say(result);
        }
        QuranIndex currentSura = getCurrentQuery(result);

        if (currentSura != null) {
            //TODO a query should happen that just a test
            Sura sura;
            switch (currentSura) {
                case BQR:
                    sura = suras.get(0);
                    break;
                case KWA:
                    sura = suras.get(1);
                    break;
                case ROM:
                    sura = suras.get(2);
                    break;
                case MLK:
                    sura = suras.get(3);
                    break;
                default:
                    sura = null;
            }
            Log.e(LOG_TAG, getCurrentQuery(result).toString());
            if (sura != null) {
                Intent playSuraIntent = new Intent(MainActivity.this, SuraPlayerActivity.class);
                if (DownloadUtils.isSuraDownloaded(sura.getQuranIndex(), this)) {
                    sura.setDownloadUrl(DownloadUtils.getSuraUri(sura.getQuranIndex(), this));
                    playSuraIntent.putExtra(Constants.EXTRA_SURA, sura);
                } else {
                    playSuraIntent.putExtra(Constants.EXTRA_SURA, sura);
                }
                startActivity(playSuraIntent);
            } else {
                showToast("This sura not found in the database");
            }
        } else {
            showToast("No sura found");
        }
    }

    private QuranIndex getCurrentQuery(String result) {
        List<String> p = Arrays.asList(result.split(" "));
        QuranIndex suarh = null;
        String part = "";
        String aya = "";
        String hzeb = "";
        String page = "";
        String quarter = "";

        //find if is a part
        for (int i = 0; i < p.size(); i++) {
            if (Constants.PART.contains(p.get(i))) {
                try {
                    part = p.get(i + 1);
                } catch (IndexOutOfBoundsException e) {
                    Log.e(LOG_TAG, "index out of range");
                }
            }

            if (Constants.AYA.contains(p.get(i))) {
                try {
                    aya = p.get(i + 1);
                } catch (IndexOutOfBoundsException e) {
                    Log.e(LOG_TAG, "index out of range");
                }
            }

            if (Constants.PAGE.contains(p.get(i))) {
                try {
                    page = p.get(i + 1);
                } catch (IndexOutOfBoundsException e) {
                    Log.e(LOG_TAG, "index out of range");
                }
            }

            if (Constants.QUARTER.contains(p.get(i))) {
                try {
                    quarter = p.get(i + 1);
                } catch (IndexOutOfBoundsException e) {
                    Log.e(LOG_TAG, "index out of range");
                }
            }

            if (Constants.HZEB.contains(p.get(i))) {
                try {
                    hzeb = p.get(i + 1);
                } catch (IndexOutOfBoundsException e) {
                    Log.e(LOG_TAG, "index out of range");
                }
            }
        }
        //find thw surah form text
        surahLoop:
        for (Map.Entry<QuranIndex, List<String>> entry : Constants.Surahs.entrySet()) {
            List<String> s = entry.getValue();
            for (int i = 0; i < p.size(); i++) {
                if (s.contains(p.get(i))) {
                    suarh = entry.getKey();
                    break surahLoop;
                }
            }
        }
        return suarh;
    }


}