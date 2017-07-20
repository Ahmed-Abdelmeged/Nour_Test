package com.abdelmeged.ahmed.nourplayer.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.abdelmeged.ahmed.nourplayer.model.Sura;

import java.io.File;
import java.io.IOException;

import static android.R.attr.name;


/**
 * Created by ahmed on 7/20/2017.
 */

public class DownloadUtils {

    public static boolean isSuraDownloaded(QuranIndex quranIndex, Context context) {
        File audioDownloadDir = new File(getAudioDownloadDirectory(context), quranIndex.toString() + ".mp3");
        return audioDownloadDir.canRead();
    }

    public static String getSuraUri(QuranIndex quranIndex, Context context) {
        File audioDownloadDir = new File(getAudioDownloadDirectory(context), quranIndex.toString() + ".mp3");
        return audioDownloadDir.getAbsolutePath();
    }


    public static File getAudioDownloadDirectory(Context context) {
        File file = new File(context.getFilesDir(), "audio-download");
        if (!file.exists()) {
            boolean isCreated = file.mkdir();
        }
        return new File(context.getFilesDir(), "audio-download");
    }

    public static void cleanAudioDownloadedDir(Context context) throws IOException {
        File audioDownloadDir = getAudioDownloadDirectory(context);
        cleanDirectory(audioDownloadDir);
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
