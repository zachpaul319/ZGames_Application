package com.example.zgames.types;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class RPSPlayer implements Parcelable {
    public int rpsId, userId, longestStreak;

    public RPSPlayer(int rpsId, int userId, int longestStreak) {
        this.rpsId = rpsId;
        this.userId = userId;
        this.longestStreak = longestStreak;
    }

    protected RPSPlayer(Parcel in) {
        rpsId = in.readInt();
        userId = in.readInt();
        longestStreak = in.readInt();
    }

    public static final Creator<RPSPlayer> CREATOR = new Creator<RPSPlayer>() {
        @Override
        public RPSPlayer createFromParcel(Parcel in) {
            return new RPSPlayer(in);
        }

        @Override
        public RPSPlayer[] newArray(int size) {
            return new RPSPlayer[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(rpsId);
        parcel.writeInt(userId);
        parcel.writeInt(longestStreak);
    }
}
