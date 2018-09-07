package com.pranata1adi.sqlcipher;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {
    private int id;
    private String name;
    private int priority;

    public Item(int id, String name, int priority) {
        this.id = id;
        this.name = name;
        this.priority = priority;
    }

    public Item(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeInt(this.priority);
    }

    protected Item(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.priority = in.readInt();
    }

    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel source) {
            return new Item(source);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
}
