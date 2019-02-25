package com.example.afp.myapplication.DataBusiness;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.UUID;

@Entity
public class Product {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private Integer mId;
    private String mName;
    private int mCount;
    private String mImageUrl;
    private boolean mShouldBuy;
    private boolean mIsBought;

    public Product() {
    }

    public Product(String name, int count) {
        this.mName = name;
        this.mCount = count;
        this.mIsBought = false;
    }

    public Product(String name, int count, boolean shouldBuy) {
        this.mName = name;
        this.mCount = count;
        this.mShouldBuy = shouldBuy;
    }


    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        this.mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.mImageUrl = imageUrl;
    }

    public int getCount() {
        return mCount;
    }

    public void setCount(int mCount) {
        this.mCount = mCount;
    }

    public boolean isShouldBuy() {
        return mShouldBuy;
    }

    public void setShouldBuy(boolean mShouldBuy) {
        this.mShouldBuy = mShouldBuy;
    }

    public boolean isIsBought() {
        return mIsBought;
    }

    public void setIsBought(boolean mIsBought) {
        this.mIsBought = mIsBought;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (((Product)obj).getId() == this.getId()) {
            return true;
        }
        return false;
    }
}
