package com.example.afp.newapp;

import android.support.annotation.NonNull;
import android.view.View;

import com.xwray.groupie.Item;

public class ChildItem extends Item<ChildViewHolder> {
    ChildElement childElement;

    public ChildItem(ChildElement childElement) {
        this.childElement = childElement;
    }

    @Override
    public void bind(@NonNull ChildViewHolder viewHolder, int position) {
        viewHolder.bind(childElement);
    }

    @Override
    public int getLayout() {
        return R.layout.recycleritem;
    }


    @NonNull
    @Override
    public ChildViewHolder createViewHolder(@NonNull View itemView) {
        return new ChildViewHolder(itemView);
    }
}
