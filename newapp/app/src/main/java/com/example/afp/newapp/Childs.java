package com.example.afp.newapp;

import android.support.annotation.NonNull;

import com.xwray.groupie.Group;
import com.xwray.groupie.GroupDataObserver;
import com.xwray.groupie.Item;

import java.util.ArrayList;
import java.util.List;

public class Childs implements Group {
    List<RecyclerItem> elements;

    public Childs(List<RecyclerItem> elements) {
        this.elements = elements;
    }

    @Override
    public int getItemCount() {
        return elements.size();
    }

    @NonNull
    @Override
    public Item getItem(int position) {
        return elements.get(position);
    }

    @Override
    public int getPosition(@NonNull Item item) {
        return elements.indexOf(item);
    }

    @Override
    public void registerGroupDataObserver(@NonNull GroupDataObserver groupDataObserver) {

    }

    @Override
    public void unregisterGroupDataObserver(@NonNull GroupDataObserver groupDataObserver) {

    }
}
