package com.example.afp.myapplication.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.afp.myapplication.DataBusiness.Dish;
import com.example.afp.myapplication.DataBusiness.Product;

@Database(entities = {Product.class, Dish.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract ProductDao productDao();
}
