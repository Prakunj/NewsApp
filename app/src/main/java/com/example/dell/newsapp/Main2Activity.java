package com.example.dell.newsapp;

import android.support.v4.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Section>> {

    String api_key = "";

    String REQUEST_URL = "https://content.guardianapis.com/search?show-tags=contributor&api-key="+api_key;
    RecyclerView recyclerView;
    SectionAdapter adapter;
    Context context;

    private static final int SECTION_LOADER_ID = 1;
    private TextView mEmptyStateTextView;
    View loadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        context = getApplicationContext();

        Intent intent = getIntent();
        Section result = intent.getParcelableExtra("result");
        String topic = result.getTopic();
        getSupportActionBar().setTitle(topic);

        recyclerView = findViewById(R.id.recyclerView2);
        mEmptyStateTextView = findViewById(R.id.empty_view);

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = LoaderManager.getInstance(this);
            loaderManager.initLoader(SECTION_LOADER_ID, null,  this);
        }else{
            loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            mEmptyStateTextView.setText(R.string.no_internet);
        }
    }

    @Override
    public Loader<ArrayList<Section>> onCreateLoader(int id, Bundle args) {

            Intent intent = getIntent();
            Section result = intent.getParcelableExtra("result");
            String topic = result.getTopic();

            Uri uri = Uri.parse(REQUEST_URL);
            Uri.Builder builder = uri.buildUpon();
            builder.appendQueryParameter("q", topic);
            return new SectionLoader(context, builder.toString());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Section>> loader, ArrayList<Section> data) {

        loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        adapter = new SectionAdapter(this, data);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void onLoaderReset(Loader<ArrayList<Section>> loader) {

    }
}
