package com.example.afp.myapplication.Recycler;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.afp.myapplication.DataBusiness.Product;
import com.example.afp.myapplication.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProductViewHolder extends CustomViewHolder<Product> implements View.OnLongClickListener {
    private CircleImageView productImage;
    private TextView productName;
    private TextView productCount;
    public LinearLayout ll;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setOnLongClickListener(this);
        productImage=itemView.findViewById(R.id.product_image);
        productName=itemView.findViewById(R.id.product_name);
        productCount=itemView.findViewById(R.id.product_count);
        ll=itemView.findViewById(R.id.product_foreground);
    }

    @Override
    public void binViewHolder(Product product) {
        productName.setText(product.getName());
        productCount.setText(String.valueOf(product.getCount()));

    }

    @Override
    public ViewGroup getForegroundLayout() {
        return ll;
    }


    @Override
    public boolean onLongClick(View v) {
        productCount.setText("fdf");
        return true;
    }

}
