package com.example.afp.myapplication.Recycler;

import android.support.annotation.NonNull;
import android.view.View;
import com.example.afp.myapplication.DataBusiness.Dish;
import com.xwray.groupie.ExpandableGroup;
import com.xwray.groupie.ExpandableItem;

public class ExpandableCustomItem extends CustomItem implements ExpandableItem {
    private ExpandableGroup expandableGroup;
    private Dish dish;



    public ExpandableCustomItem(Dish dish) {
        super(dish);
        this.dish = dish;
    }

    @Override
    public void bind(@NonNull CustomViewHolder viewHolder, int position) {
        viewHolder.binViewHolder(dish);
        viewHolder.itemView.setOnClickListener(v -> {
            expandableGroup.onToggleExpanded();
            dish.setExpanded(expandableGroup.isExpanded());

        });
    }

    @NonNull
    @Override
    public DishViewHolder createViewHolder(@NonNull View itemView) {
        return new DishViewHolder(itemView);
    }

    @Override
    public void setExpandableGroup(@NonNull ExpandableGroup onToggleListener) {
        expandableGroup = onToggleListener;
    }
}
