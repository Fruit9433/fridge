package com.example.afp.myapplication.Recycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.ParentViewHolder;
import com.example.afp.myapplication.DataBusiness.Dish;
import com.example.afp.myapplication.DataBusiness.Product;
import com.example.afp.myapplication.R;
import com.example.afp.myapplication.thread.ThreadPoolManager;
import com.xwray.groupie.ViewHolder;


import de.hdodenhof.circleimageview.CircleImageView;

public class DishViewHolder extends CustomViewHolder<Dish> {

    private Context context;
    private TextView dishName;
    private LinearLayout linearLayout_childItems;
    private CircleImageView dishImage;
    private ImageButton dishRemove;
    private ImageButton dishAddIngredient;
    public LinearLayout lld;
    private Context mContext;


    public DishViewHolder(@NonNull View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        dishImage=itemView.findViewById(R.id.dish_image);
        dishRemove=itemView.findViewById(R.id.dish_remove);
        dishAddIngredient=itemView.findViewById(R.id.dish_add_ingredient);
        context = itemView.getContext();
        dishName = itemView.findViewById(R.id.dish_name);
        lld = itemView.findViewById(R.id.dish_foreground);
    }

    @Override
    public void binViewHolder(final Dish dish) {
        dishName.setText(dish.getName());
        dishRemove.setOnClickListener(v -> ThreadPoolManager.getsInstance().deleteDish(dish));
        dishAddIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog(mContext, dish);

            }
        });
    }

    private void showInputDialog(Context context, Dish dish) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View promptView = layoutInflater.inflate(R.layout.input_product, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(promptView);
        final EditText productName = promptView.findViewById(R.id.edittext);
        final EditText productCount = promptView.findViewById(R.id.edittext2);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton(R.string.alert_add, (dialog, id) -> {
                    dish.getIngredients().add(
                            new Product(productName.getText().toString(),
                                    Integer.valueOf(productCount.getText().toString()))
                    );
                    ThreadPoolManager.getsInstance().updateDish(dish);
                })
                .setNegativeButton(R.string.alert_cancel,
                        (dialog, id) -> dialog.cancel());
        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    @Override
    public ViewGroup getForegroundLayout() {
        return lld;
    }


}


