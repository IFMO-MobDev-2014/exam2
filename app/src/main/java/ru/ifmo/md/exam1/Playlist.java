package ru.ifmo.md.exam1;

import android.os.Parcel;
import android.os.Parcelable;

// {"name":"Mapei – Don't Wait",
// "url":"https://cs1-46v4.vk-cdn.net/p23/53f922c0d2cec9.mp3",
// "duration":"3:35",
// "popularity":76,
// "genres":["Blues","Alternative","Soul"],
// "year":2009}

public class Playlist implements Parcelable {
    public String name = "New list";
    public String[] list = new String[0];

    public Playlist() {}

    public Playlist(String name, String[] list) {
        this.name = name;
        this.list = list;
    }

    private Playlist(Parcel in) {
        name = in.readString();
        int length = in.readInt();
        in.readStringArray(list = new String[length]);
    }

    public int describeContents() {
        return 0;
    }
    public void writeToParcel(Parcel out, int i) {
        out.writeString(name);
        out.writeInt(list.length);
        out.writeStringArray(list);
    }

    public static final Creator CREATOR = new Creator() {
        public Playlist createFromParcel(Parcel in) {
            return new Playlist(in);
        }
        public Playlist[] newArray(int length) {
            return new Playlist[length];
        }
    };
}