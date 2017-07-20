package com.abdelmeged.ahmed.nourplayer.utils;

import android.content.Context;

import java.io.File;
import java.io.IOException;


/**
 * Created by ahmed on 7/20/2017.
 */

public class StreamingUtils {
    public static File getAudioCacheDir(Context context) {
        return new File(context.getExternalCacheDir(), "audio-cache");
    }

    public static void cleanAudioCacheDir(Context context) throws IOException {
        File videoCacheDir = getAudioCacheDir(context);
        cleanDirectory(videoCacheDir);
    }

    private static void cleanDirectory(File file) throws IOException {
        if (!file.exists()) {
            return;
        }
        File[] contentFiles = file.listFiles();
        if (contentFiles != null) {
            for (File contentFile : contentFiles) {
                delete(contentFile);
            }
        }
    }

    private static void delete(File file) throws IOException {
        if (file.isFile() && file.exists()) {
            deleteOrThrow(file);
        } else {
            cleanDirectory(file);
            deleteOrThrow(file);
        }
    }

    private static void deleteOrThrow(File file) throws IOException {
        if (file.exists()) {
            boolean isDeleted = file.delete();
            if (!isDeleted) {
                throw new IOException(String.format("File %s can't be deleted", file.getAbsolutePath()));
            }
        }
    }
}
