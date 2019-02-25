package com.example.afp.myapplication.Recycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.TimingLogger;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.afp.myapplication.DataBusiness.Product;
import com.example.afp.myapplication.R;
import com.example.afp.myapplication.thread.ThreadPoolManager;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShouldBuysViewHolder extends CustomViewHolder<Product> {

    private CircleImageView productImage;
    private TextView productName;
    private TextView productCount;
    private Switch bought;
    TimingLogger timings;
    private Context mContext;
    private LinearLayout ll;

    public ShouldBuysViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        productImage=itemView.findViewById(R.id.product_image);
        productName=itemView.findViewById(R.id.product_name);
        productCount=itemView.findViewById(R.id.product_count);
        bought = itemView.findViewById(R.id.bought);
        ll = itemView.findViewById(R.id.shouldbuys_foreground);
        mContext = context;
    }

    public void binViewHolder(final Product product) {
        productName.setText(product.getName());
        productCount.setText(String.valueOf(product.getCount()));
        bought.setChecked(product.isIsBought());

        bought.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                product.setIsBought(true);
                product.setShouldBuy(false);
                ThreadPoolManager.getsInstance().addChanges(product);
            } if (!isChecked) {
                ThreadPoolManager.getsInstance().deleteChanges(product);
            }
        });
    }

    @Override
    public ViewGroup getForegroundLayout() {
        return ll;
    }
}
