package com.abdelmeged.ahmed.nourplayer.app;

import android.app.Application;
import android.content.Context;

import com.abdelmeged.ahmed.nourplayer.utils.StreamingUtils;
import com.danikula.videocache.HttpProxyCacheServer;

/**
 * Created by ahmed on 7/20/2017.
 */

public class App extends Application {
    private HttpProxyCacheServer proxy;


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
