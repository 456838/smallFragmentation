package com.salton123.appxmly.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * User: newSalton@outlook.com
 * Date: 2017/9/19 10:41
 * ModifyTime: 10:41
 * Description:
 */
public class HotVoiceItem implements Parcelable {
    public  String name;
    public String keyword;

    protected HotVoiceItem(Parcel in) {
        name = in.readString();
        keyword = in.readString();
    }

    public HotVoiceItem() {
    }

    public HotVoiceItem(String name, String keyword) {
        this.name = name;
        this.keyword = keyword;
    }

    public static final Creator<HotVoiceItem> CREATOR = new Creator<HotVoiceItem>() {
        @Override
        public HotVoiceItem createFromParcel(Parcel in) {
            return new HotVoiceItem(in);
        }

        @Override
        public HotVoiceItem[] newArray(int size) {
            return new HotVoiceItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(keyword);
    }
}
