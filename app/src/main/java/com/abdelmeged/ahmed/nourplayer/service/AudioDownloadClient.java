package com.abdelmeged.ahmed.nourplayer.service;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

/**
 * Created by ahmed on 7/20/2017.
 */

public interface AudioDownloadClient {

    @GET("uc?export=download")
    @Streaming
    Call<ResponseBody> downloadAudio(@Query("id") String id);
}
