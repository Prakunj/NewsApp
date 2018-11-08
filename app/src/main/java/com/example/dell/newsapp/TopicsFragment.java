package com.example.dell.newsapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class TopicsFragment extends Fragment {

    RecyclerView recyclerView;
    SectionAdapter adapter;
    LinearLayoutManager layoutManager;
    Context context;
    ArrayList<Section> sections;

    public TopicsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context  = getActivity();

        sections = new ArrayList<>();
        sections.add(new Section(Section.TOPIC_TYPE,"Politics"));
        sections.add(new Section(Section.TOPIC_TYPE,"Culture"));
        sections.add(new Section(Section.TOPIC_TYPE,"Travel"));
        sections.add(new Section(Section.TOPIC_TYPE,"Sports"));
        sections.add(new Section(Section.TOPIC_TYPE,"Music"));
        sections.add(new Section(Section.TOPIC_TYPE,"Society"));
        sections.add(new Section(Section.TOPIC_TYPE,"Books"));
        sections.add(new Section(Section.TOPIC_TYPE,"Education"));
        sections.add(new Section(Section.TOPIC_TYPE,"Film"));
        sections.add(new Section(Section.TOPIC_TYPE,"Lifestyle"));

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_topics, container, false);

        context = getActivity();

        recyclerView = (RecyclerView)rootView.findViewById(R.id.recyclerView3);
        adapter = new SectionAdapter(getContext(), sections);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        return rootView;
    }
}
