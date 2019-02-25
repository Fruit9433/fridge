package com.example.afp.newapp;

import android.support.annotation.NonNull;
import android.view.View;
import com.xwray.groupie.ExpandableGroup;
import com.xwray.groupie.ExpandableItem;

public class RecyclerItem extends HeaderItem implements ExpandableItem  {
    Element element;
    ExpandableGroup  expandableGroup;

    public RecyclerItem(Element element) {
        super(element);
        this.element = element;
    }

    @Override
    public void bind(@NonNull MyViewHolder viewHolder, int position) {
        viewHolder.bind(element);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    expandableGroup.onToggleExpanded();

            }
        });
    }

    @NonNull
    @Override
    public MyViewHolder createViewHolder(@NonNull View itemView) {
        return new MyViewHolder(itemView);
    }

    @Override
    public void setExpandableGroup(@NonNull ExpandableGroup onToggleListener) {
        expandableGroup = onToggleListener;
    }
}
