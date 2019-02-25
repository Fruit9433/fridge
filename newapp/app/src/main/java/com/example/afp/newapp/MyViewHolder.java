package com.example.afp.newapp;

import android.view.View;
import android.widget.TextView;

import com.xwray.groupie.ViewHolder;


public class MyViewHolder extends com.xwray.groupie.ViewHolder {
    private TextView textView;

    public MyViewHolder(View rootView) {
        super(rootView);
        textView = rootView.findViewById(R.id.text);
    }

    public void bind(Element element) {
        this.textView.setText(element.getName());
    }
}
