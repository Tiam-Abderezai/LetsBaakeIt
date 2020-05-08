package com.example.letsbakeit.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Recipe implements Parcelable {

    @SerializedName("id")
    private int mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("ingredients")
    private List<Ingredient> mIngredients = null;

    @SerializedName("steps")
    private List<Step> mSteps = null;

    @SerializedName("servings")
    private String mServings;

    @SerializedName("image")
    private String mImage;

    public Recipe(int recipeId, String name, List<Ingredient> ingredients, List<Step> steps,
                  String servings, String image) {
        mId = recipeId;
        mName = name;
        mIngredients = ingredients;
        mSteps = steps;
        mServings = servings;
        mImage = image;
    }

    protected Recipe(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mServings = in.readString();
        mImage = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public List<Ingredient> getmIngredients() {
        return mIngredients;
    }

    public void setmIngredients(List<Ingredient> mIngredients) {
        this.mIngredients = mIngredients;
    }

    public List<Step> getmSteps() {
        return mSteps;
    }

    public void setmSteps(List<Step> mSteps) {
        this.mSteps = mSteps;
    }

    public String getmServings() {
        return mServings;
    }

    public void setmServings(String mServings) {
        this.mServings = mServings;
    }

    public String getmImage() {
        return mImage;
    }

    public void setmImage(String mImage) {
        this.mImage = mImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mName);
        dest.writeString(mServings);
        dest.writeString(mImage);
    }
}
