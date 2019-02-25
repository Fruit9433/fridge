package com.example.afp.newapp;

import android.support.annotation.NonNull;

import com.xwray.groupie.Item;

public class HeaderItem extends Item<MyViewHolder> {
    Element element;

    public HeaderItem(Element element) {
        this.element = element;
    }

    @Override
    public void bind(@NonNull MyViewHolder viewHolder, int position) {
        viewHolder.bind(element);

    }

    @Override
    public int getLayout() {
        return R.layout.recycleritem;
    }
}
