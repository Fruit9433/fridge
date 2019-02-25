package com.example.afp.myapplication.db;

import android.arch.persistence.room.TypeConverter;

import com.example.afp.myapplication.DataBusiness.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ProductsListConverter {
    @TypeConverter
    public static List<Product> stringToMeasurements(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Product>>() {}.getType();
        List<Product> measurements = gson.fromJson(json, type);
        return measurements;
    }

    @TypeConverter
    public static String measurementsToString(List<Product> list) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Product>>() {}.getType();
        String json = gson.toJson(list, type);
        return json;
    }
}
