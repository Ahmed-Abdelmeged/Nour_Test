package com.abdelmeged.ahmed.nourplayer.ui;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.AudioManager;
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
import android.widget.Button;
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

import java.io.IOException;
import java.util.ArrayList;

import static com.abdelmeged.ahmed.nourplayer.utils.Constants.MESSAGE_PROGRESS;

public class MainActivity extends AppCompatActivity implements SuraClickCallbacks, SuraDownloadClickCallbacks {

    /**
     * UI Element
     */
    private RecyclerView suraRecycler;
    private SuraRecyclerAdapter suraRecyclerAdapter;
    private Toast toast;

    /**
     * Tag for debugging
     */
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private static final int PERMISSION_REQUEST_CODE = 1;


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

        ArrayList<Sura> suras = new ArrayList<Sura>();
        suras.add(new Sura("Al-Baqara", "0Bz9EiHndgROYLUU5anNWWGJsX1k", QuranIndex.BQR));
        suras.add(new Sura("Al-Kawthar", "0Bz9EiHndgROYeHlFLTdiekFKREE", QuranIndex.KWA));
        suras.add(new Sura("Ar-Room", "0Bz9EiHndgROYTndObTRsWlBZMTg", QuranIndex.ROM));
        suras.add(new Sura("Al-Mulk", "0Bz9EiHndgROYZGVnMmFYaFd1WTg", QuranIndex.MLK));

        suraRecyclerAdapter.setSuras(suras);

        registerReceiver();
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
        showToast("sura download clicked");
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
                // mProgressBar.setProgress(download.getProgress());
                if (download.getProgress() == 100) {

                    //mProgressText.setText("File Download Complete");
                    suraRecyclerAdapter.notifyDataSetChanged();

                } else {
                    //mProgressText.setText(String.format("Downloaded (%d/%d) MB",download.getCurrentFileSize(),download.getTotalFileSize()));
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

                    //  startDownload();
                } else {

                    //Snackbar.make(findViewById(R.id.coordinatorLayout),"Permission Denied, Please allow to proceed !", Snackbar.LENGTH_LONG).show();

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
        if (toast != null) {
            toast.cancel();
        }
    }
}
