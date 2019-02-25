package com.example.afp.myapplication.DataBusiness;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.bignerdranch.expandablerecyclerview.model.Parent;
import com.example.afp.myapplication.db.ProductsListConverter;

import java.util.List;
@Entity
@TypeConverters(ProductsListConverter.class)
public class Dish {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int mId;
    private List<Product> mIngredients;
    private String mName;
    private boolean isExpanded;


    public Dish(String mName, List<Product> mIngredients) {
        this.mName = mName;
        this.mIngredients = mIngredients;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public void setId(@NonNull int mId) {
        this.mId = mId;
    }

    public List<Product> getIngredients() {
        return mIngredients;
    }

    public void setIngredients(List<Product> mIngredients) {
        this.mIngredients = mIngredients;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public int getId() {
        return mId;
    }
}
