package com.example.dell.newsapp;

import android.content.Context;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public ArrayList<Section> sectionArrayList;
    private Context context;
    int total_types;

    public SectionAdapter(Context context, ArrayList<Section> ArrayList) {
        this.sectionArrayList = ArrayList;
        this.context = context;
        total_types = sectionArrayList.size();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view;
        switch (viewType) {
            case Section.TOPIC_TYPE:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
                return new TopicViewHolder(view);
            case Section.RESULT_TYPE:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item2, viewGroup, false);
                return new ResultViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        final Section result = sectionArrayList.get(position);
        if (result != null){
            switch (result.type){
                case Section.TOPIC_TYPE:
                    ((TopicViewHolder) viewHolder).sectionName.setText(result.getTopic());
                    ((TopicViewHolder) viewHolder).listLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, Main2Activity.class);
                            intent.putExtra("result", result);
                            context.startActivity(intent);

                        }
                    });
                    break;
                    case Section.RESULT_TYPE:
                        ((ResultViewHolder) viewHolder).sectionName2.setText(result.getSectionName());
                        ((ResultViewHolder) viewHolder).webTitle.setText(result.getWebTitle());
                        ((ResultViewHolder) viewHolder).authorView.setText(result.getAuthor());
                        ((ResultViewHolder) viewHolder).layout2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Uri uri = Uri.parse(result.getUrl());
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                context.startActivity(intent);

                            }
                        });

                        String date = formatDate(result.getTime());
                        ((ResultViewHolder) viewHolder).dateView.setText(date);

                        String time = formatTime(result.getTime());
                        ((ResultViewHolder) viewHolder).timeView.setText(time);
            }
        }
    }
    private String formatDate(String resultDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date date;
        try {
            date = sdf.parse(resultDate);
            SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
            return dateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    private String formatTime(String resultDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date time;
        try {
            time = sdf.parse(resultDate);
            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
            return timeFormat.format(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {

        switch (sectionArrayList.get(position).type) {
            case 0:
                return Section.TOPIC_TYPE;
            case 1:
                return Section.RESULT_TYPE;
            default:
                return -1;
        }
    }

    public static class TopicViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.sectionName)
        TextView sectionName;
        @BindView(R.id.listLayout)
        LinearLayout listLayout;
        public TopicViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class ResultViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.layout)
        LinearLayout layout2;
        @BindView(R.id.webTitle)
        TextView webTitle;
        @BindView(R.id.sectionName2)
        TextView sectionName2;
        @BindView(R.id.date)
        TextView dateView;
        @BindView(R.id.time)
        TextView timeView;
        @BindView(R.id.author) TextView authorView;

        public ResultViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public int getItemCount() {
        return sectionArrayList.size();
    }
}