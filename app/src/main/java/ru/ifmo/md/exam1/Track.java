package ru.ifmo.md.exam1;

import android.os.Parcel;
import android.os.Parcelable;

// {"name":"Mapei – Don't Wait",
// "url":"https://cs1-46v4.vk-cdn.net/p23/53f922c0d2cec9.mp3",
// "duration":"3:35",
// "popularity":76,
// "genres":["Blues","Alternative","Soul"],
// "year":2009}

public class Track implements Parcelable {
    public String name = null, url = null, duration = null;
    private int genLength = 0;
    public String[] genres = new String[0];
    public int popularity = 0, year = 0;

    public Track() {}

    public Track(String name, String url, String duration, String[] genres, int popularity, int year) {
        this.name = name;
        this.url = url;
        this.duration = duration;
        this.genres = genres;
        this.popularity = popularity;
        this.year = year;
        this.genLength = genres.length;
    }

    private Track(Parcel in) {
        this.name = in.readString();
        this.url  = in.readString();
        this.duration = in.readString();
        this.genLength = in.readInt();
        in.readStringArray(this.genres = new String[genLength]);
        this.popularity = in.readInt();
        this.year = in.readInt();
    }

    public int describeContents() {
        return 0;
    }
    public void writeToParcel(Parcel out, int i) {
        out.writeString(name);
        out.writeString(url);
        out.writeString(duration);
        out.writeInt(genres.length);
        out.writeStringArray(genres);
        out.writeInt(popularity);
        out.writeInt(year);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Track createFromParcel(Parcel in) {
            return new Track(in);
        }
        public Track[] newArray(int length) {
            return new Track[length];
        }
    };

    public String toString() {
        return name + " (" + duration + ")";
    }
}