package com.example.afp.myapplication.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.afp.myapplication.DataBusiness.Product;
import com.example.afp.myapplication.R;
import com.example.afp.myapplication.Recycler.ProductViewHolder;
import com.example.afp.myapplication.Recycler.RecyclerItemTouchHelper;
import com.example.afp.myapplication.Recycler.RecyclerItemTouchHelperListener;
import com.example.afp.myapplication.Recycler.RecylerAdapter;
import com.example.afp.myapplication.thread.ThreadPoolManager;

import java.util.ArrayList;
import java.util.List;

public class ProductsFragment extends Fragment implements RecyclerItemTouchHelperListener {
    private List<Product> list = new ArrayList<>();
    private RecyclerView productsList;
    private ImageButton addButton;
    private ThreadPoolManager tp;

    public static Fragment newInstance() {
        return new ProductsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tp = ThreadPoolManager.getsInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.products_fragment, container, false);
        productsList = v.findViewById(R.id.products_list);
        addButton = v.findViewById(R.id.add_button);
        addButton.setOnClickListener(v1 -> showInputDialog());
        tp.getAllBoughtProducts();
        tp.getProductsLivedata().observe(this, this::showProducts);
        ItemTouchHelper.SimpleCallback it = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(it).attachToRecyclerView(productsList);

        return v;
    }
    private void showProducts(List<Product> products) {
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        productsList.setLayoutManager(lm);
        productsList.setAdapter(new RecylerAdapter<Product>(getContext(), products) {
            @Override
            public RecyclerView.ViewHolder setViewHolder(ViewGroup parent) {
                View v = LayoutInflater.from(getContext()).inflate(R.layout.products_item, parent, false);
                ProductViewHolder pvh = new ProductViewHolder(v);
                return pvh;
            }

            @Override
            public void onBindData(RecyclerView.ViewHolder holder, Product product) {
                ProductViewHolder pvh = (ProductViewHolder) holder;
                pvh.binViewHolder(product);

            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    protected void showInputDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View promptView = layoutInflater.inflate(R.layout.input_product, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(promptView);

        final EditText productName = (EditText) promptView.findViewById(R.id.edittext);
        final EditText productCount = (EditText) promptView.findViewById(R.id.edittext2);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton(R.string.alert_add, (dialog, id) -> tp.insertProduct(new Product(productName.getText().toString(),
                        Integer.valueOf(productCount.getText().toString()))))
                .setNegativeButton(R.string.alert_cancel,
                        (dialog, id) -> dialog.cancel());

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        System.out.println("sds");
        tp.deleteProduct(((RecylerAdapter<Product>)productsList.getAdapter()).getList().get(viewHolder.getAdapterPosition()));
        ((RecylerAdapter)productsList.getAdapter()).deleteItem(viewHolder.getAdapterPosition());
    }
}