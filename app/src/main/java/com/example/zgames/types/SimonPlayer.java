package com.example.zgames.types;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class SimonPlayer implements Parcelable {
    public int simonId, userId, highScore;

    public SimonPlayer(int simonId, int userId, int highScore) {
        this.simonId = simonId;
        this.userId = userId;
        this.highScore = highScore;
    }

    protected SimonPlayer(Parcel in) {
        simonId = in.readInt();
        userId = in.readInt();
        highScore = in.readInt();
    }

    public static final Parcelable.Creator<SimonPlayer> CREATOR = new Parcelable.Creator<SimonPlayer>() {
        @Override
        public SimonPlayer createFromParcel(Parcel in) {
            return new SimonPlayer(in);
        }

        @Override
        public SimonPlayer[] newArray(int size) {
            return new SimonPlayer[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(simonId);
        parcel.writeInt(userId);
        parcel.writeInt(highScore);
    }
}
