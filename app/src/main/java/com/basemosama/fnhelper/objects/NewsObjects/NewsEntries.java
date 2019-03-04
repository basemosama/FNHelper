package com.basemosama.fnhelper.objects.NewsObjects;

import android.os.Parcel;
import android.os.Parcelable;

public class NewsEntries implements Parcelable {
    private String title;
    private String body;
    private String image;
    private String time;

    public NewsEntries(String title, String body, String image, String time) {
        this.title = title;
        this.body = body;
        this.image = image;
        this.time = time;
    }


    protected NewsEntries(Parcel in) {
        title = in.readString();
        body = in.readString();
        image = in.readString();
        time = in.readString();
    }
    public static final Creator<NewsEntries> CREATOR = new Creator<NewsEntries>() {
        @Override
        public NewsEntries createFromParcel(Parcel in) {
            return new NewsEntries(in);
        }

        @Override
        public NewsEntries[] newArray(int size) {
            return new NewsEntries[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getImage() {
        return image;
    }

    public String getTime() {
        return time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(body);
        parcel.writeString(image);
        parcel.writeString(time);
    }
}
