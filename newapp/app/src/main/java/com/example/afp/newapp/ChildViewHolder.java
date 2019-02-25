package com.example.afp.newapp;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.xwray.groupie.ViewHolder;

public class ChildViewHolder extends ViewHolder {
    private TextView textView;

    public ChildViewHolder(@NonNull View rootView) {
        super(rootView);
        textView = rootView.findViewById(R.id.text);
    }

    public void bind(ChildElement childElement) {
        this.textView.setText(childElement.getName());
    }
}
