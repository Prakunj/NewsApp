package com.example.dell.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;

public class SectionLoader extends AsyncTaskLoader<ArrayList<Section>> {

    String mUrl;

    public SectionLoader(@NonNull Context context, String mUrl) {
        super(context);
        this.mUrl = mUrl;
    }

    protected void onStartLoading(){
        forceLoad();
    }

    @Nullable
    @Override
    public ArrayList<Section> loadInBackground() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(mUrl == null){
            return null;
        }
        ArrayList<Section> sections = QueryUtils.fetchSectionData(mUrl);
        return sections;

    }
}
