package com.example.zgames.types;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class User implements Parcelable {
    public int userId;
    public String username, displayName, password;

    public User(int userId, String username, String displayName, String password) {
        this.userId = userId;
        this.username = username;
        this.displayName = displayName;
        this.password = password;
    }

    public User(String username, String displayName, String password) {
        this.username = username;
        this.displayName = displayName;
        this.password = password;
    }

    protected User(Parcel in) {
        userId = in.readInt();
        username = in.readString();
        displayName = in.readString();
        password = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(userId);
        parcel.writeString(username);
        parcel.writeString(displayName);
        parcel.writeString(password);
    }
}
