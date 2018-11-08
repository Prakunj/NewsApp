package com.example.dell.newsapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Section implements Parcelable {

    public static final int TOPIC_TYPE = 0;
    public static final int RESULT_TYPE = 1;

    private String SectionName;
    private String webTitle;
    private String url;
    private String Topic;
    public int type;
    private String time;
    private String author;

    public String getAuthor() {
        return author;
    }

    public String getTopic() {
        return Topic;
    }

    public String getUrl() {
        return url;
    }

    public String getTime() {
        return time;
    }

    public String getWebTitle() {
        return webTitle;
    }


    public String getSectionName() {
        return SectionName;
    }

    public Section(int type, String topic) {
        this.Topic = topic;
        this.type = type;
    }

    public Section(int type, String sectionName, String webTitle, String url, String time, String author){
        this.type = type;
        SectionName = sectionName;
        this.webTitle = webTitle;
        this.url = url;
        this.time = time;
        this.author = author;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.SectionName);
        dest.writeString(this.webTitle);
        dest.writeString(this.url);
        dest.writeString(this.Topic);
        dest.writeInt(this.type);
        dest.writeString(this.time);
        dest.writeString(this.author);
    }

    protected Section(Parcel in) {
        this.SectionName = in.readString();
        this.webTitle = in.readString();
        this.url = in.readString();
        this.Topic = in.readString();
        this.type = in.readInt();
        this.time = in.readString();
        this.author = in.readString();
    }

    public static final Creator<Section> CREATOR = new Creator<Section>() {
        @Override
        public Section createFromParcel(Parcel source) {
            return new Section(source);
        }

        @Override
        public Section[] newArray(int size) {
            return new Section[size];
        }
    };
}
