package com.abdelmeged.ahmed.nourplayer.app;

import android.app.Application;
import android.content.Context;

import com.abdelmeged.ahmed.nourplayer.BuildConfig;
import com.abdelmeged.ahmed.nourplayer.utils.Constants;
import com.abdelmeged.ahmed.nourplayer.utils.StreamingUtils;
import com.danikula.videocache.HttpProxyCacheServer;

import net.gotev.speech.Logger;
import net.gotev.speech.Speech;

import java.util.Locale;

/**
 * Created by ahmed on 7/20/2017.
 */

public class App extends Application {
    private HttpProxyCacheServer proxy;

    @Override
    public void onCreate() {
        super.onCreate();
        Speech.init(this);
        if (BuildConfig.DEBUG) {
            Logger.setLogLevel(Logger.LogLevel.DEBUG);
        }
        //set the speech language to arabic
        Speech.getInstance().setLocale(new Locale(Constants.LOCALE_ARABIC));
    }


    public static HttpProxyCacheServer getProxy(Context context) {
        App app = (App) context.getApplicationContext();
        return app.proxy == null ? (app.proxy = app.newProxy()) : app.proxy;
    }

    private HttpProxyCacheServer newProxy() {
        return new HttpProxyCacheServer.Builder(this)
                .cacheDirectory(StreamingUtils.getAudioCacheDir(this))
                .build();
    }
}
