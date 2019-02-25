package com.example.afp.myapplication.Recycler;

import android.support.annotation.NonNull;
import android.view.View;

import com.example.afp.myapplication.DataBusiness.Dish;
import com.example.afp.myapplication.DataBusiness.Product;
import com.example.afp.myapplication.R;
import com.xwray.groupie.Item;

public class CustomItem<S, T extends CustomViewHolder> extends Item<T> {
    private S s;
    private T t;

    public CustomItem(S s) {
        this.s = s;
    }


    @Override
    public void bind(@NonNull T viewHolder, int position) {
        viewHolder.binViewHolder(s);
        this.t = viewHolder;
    }

    @NonNull
    @Override
    public T createViewHolder(@NonNull View itemView) {
        if (s instanceof Dish) {
            return (T) new DishViewHolder(itemView);
        } else if (s instanceof Product) {
            return (T) new ProductViewHolder(itemView);
        }
        return null;
    }
    @Override
    public int getLayout() {
        if (s instanceof Dish){
            return R.layout.dishes_item;
        } else if (s instanceof Product) {
            return R.layout.products_item;
        }
        return 0;
    }
}
