package com.example.afp.myapplication.Recycler;

import android.support.annotation.NonNull;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;

import com.xwray.groupie.ViewHolder;

public abstract class CustomViewHolder<T> extends ViewHolder {

    public CustomViewHolder(@NonNull View rootView) {
        super(rootView);
    }

    public abstract void binViewHolder(T t);

    public abstract ViewGroup getForegroundLayout();
}
