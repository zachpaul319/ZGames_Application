package com.example.zgames.types;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class WordlePlayer implements Parcelable {
    public int wordleId, userId, currentLevel;

    protected WordlePlayer(Parcel in) {
        wordleId = in.readInt();
        userId = in.readInt();
        currentLevel = in.readInt();
    }

    public static final Creator<WordlePlayer> CREATOR = new Creator<WordlePlayer>() {
        @Override
        public WordlePlayer createFromParcel(Parcel in) {
            return new WordlePlayer(in);
        }

        @Override
        public WordlePlayer[] newArray(int size) {
            return new WordlePlayer[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(wordleId);
        parcel.writeInt(userId);
        parcel.writeInt(currentLevel);
    }
}
