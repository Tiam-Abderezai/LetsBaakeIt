package com.example.letsbakeit.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Step implements Parcelable {


    @SerializedName("id")
    public int mId;

    @SerializedName("shortDescription")
    public String mShortDescription;

    @SerializedName("description")
    public String mDescription;

    @SerializedName("videoURL")
    public String mVideoUrl;

    @SerializedName("thumbnailURL")
    public String mThumbnailUrl;

    public Step(int id, String shortDescription, String description, String videoUrl, String thumbnailUrl) {
        mId = id;
        mShortDescription = shortDescription;
        mDescription = description;
        mVideoUrl = videoUrl;
        mThumbnailUrl = thumbnailUrl;
    }

    public void setStepId(int id) {
        mId = id;
    }

    public int getStepId() {
        return mId;
    }

    public void setShortDescription(String shortDescription) {
        mShortDescription = shortDescription;
    }

    public String getShortDescription() {
        return mShortDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setVideoUrl(String videoUrl) {
        mVideoUrl = videoUrl;
    }

    public String getVideoUrl() {
        return mVideoUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        mThumbnailUrl = thumbnailUrl;
    }

    public String getThumbnailUrl() {
        return mThumbnailUrl;
    }


    protected Step(Parcel in) {
        mId = in.readInt();
        mShortDescription = in.readString();
        mDescription = in.readString();
        mVideoUrl = in.readString();
    }

    public static final Creator<Step> CREATOR = new Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mShortDescription);
        dest.writeString(mDescription);
        dest.writeString(mVideoUrl);
    }
}
