package com.example.afp.myapplication.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.afp.myapplication.DataBusiness.Dish;
import com.example.afp.myapplication.DataBusiness.Product;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface ProductDao{
    @Query("SELECT * FROM product WHERE mShouldBuy= 0 ORDER BY mId DESC")
    Flowable<List<Product>> getBoughtProducts();

    @Query("SELECT * FROM product WHERE mShouldBuy= 1 ORDER BY mId DESC")
    Flowable<List<Product>> getShouldBuyProducts();

    @Query("SELECT * FROM dish")
    Flowable<List<Dish>> getAllDishes();

    @Query("SELECT * FROM product WHERE mId = :id")
    Product getById(long id);

    @Insert
    void insert(Product product);

    @Insert
    void insert(Dish dish);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int update(Product product);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int update(Dish dish);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int update(List<Product> products);

    @Delete
    void delete(Product product);

    @Delete
    void delete(Dish dish);
}
