package com.example.afp.myapplication.Recycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.afp.myapplication.DataBusiness.Product;
import com.example.afp.myapplication.R;
import java.util.List;

public abstract class RecylerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<T> products;

    public abstract RecyclerView.ViewHolder setViewHolder(ViewGroup parent);

    public abstract void onBindData(RecyclerView.ViewHolder holder, T val);

    public RecylerAdapter(Context context, List<T> list) {
        this.context = context;
        products = list;
    }

    public List<T> getList() {
        return products;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder vh = setViewHolder(viewGroup);
        //View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.products_item, viewGroup, false);
        //return new ProductViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        //viewHolder.binViewHolder(products.get(viewHolder.getAdapterPosition()));
        onBindData(viewHolder, products.get(viewHolder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setList(List<T> list) {
        this.products = list;
    }

    public void deleteItem(int position) {
        products.remove(position);
        notifyItemRemoved(position);

    }

}
