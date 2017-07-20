package com.abdelmeged.ahmed.nourplayer.model;

import com.abdelmeged.ahmed.nourplayer.utils.QuranIndex;

import java.io.Serializable;

/**
 * Created by ahmed on 7/20/2017.
 */

public class Sura implements Serializable {
    private String name;
    private String downloadUrl;
    private QuranIndex quranIndex;

    public Sura(String name, String downloadUrl,QuranIndex quranIndex) {
        this.name = name;
        this.downloadUrl = downloadUrl;
        this.quranIndex=quranIndex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public QuranIndex getQuranIndex() {
        return quranIndex;
    }

    public void setQuranIndex(QuranIndex quranIndex) {
        this.quranIndex = quranIndex;
    }

    @Override
    public String toString() {
        return "Sura{" +
                "name='" + name + '\n' +
                ", downloadUrl='" + downloadUrl + '\'' +
                '}';
    }
}
