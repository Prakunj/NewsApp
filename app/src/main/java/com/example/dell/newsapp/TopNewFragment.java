package com.example.dell.newsapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class TopNewFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Section>> {

    String REQUEST_URL = "https://content.guardianapis.com/search?show-tags=contributor&api-key=61a69ce8-908b-43b3-b010-de70b6aeba81";

    RecyclerView recyclerView;
    SectionAdapter adapter;
    View loadingIndicator;


    private static final int SECTION_LOADER_ID = 1;
    private TextView mEmptyStateTextView;


    public TopNewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.activity_main2, container, false);
        recyclerView = rootView.findViewById(R.id.recyclerView2);
        loadingIndicator = rootView.findViewById(R.id.loading_indicator);
        mEmptyStateTextView = rootView.findViewById(R.id.empty_view);

        ConnectivityManager connectivityManager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            LoaderManager.getInstance(this).initLoader(SECTION_LOADER_ID, null, this);
        }else{

            loadingIndicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_internet);
        }

        return  rootView;

    }

    @NonNull
    @Override
    public Loader onCreateLoader(int i, @Nullable Bundle bundle) {
        return new SectionLoader(getActivity(), REQUEST_URL);

    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Section>> loader, ArrayList<Section> sections) {

        loadingIndicator.setVisibility(View.GONE);

        adapter = new SectionAdapter(getActivity(), sections);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {

    }
}
