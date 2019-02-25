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

import com.example.afp.myapplication.DataBusiness.Product;
import com.example.afp.myapplication.R;
import com.example.afp.myapplication.Recycler.RecyclerItemTouchHelper;
import com.example.afp.myapplication.Recycler.RecyclerItemTouchHelperListener;
import com.example.afp.myapplication.Recycler.RecylerAdapter;
import com.example.afp.myapplication.Recycler.ShouldBuysViewHolder;
import com.example.afp.myapplication.thread.ThreadPoolManager;

import java.util.ArrayList;
import java.util.List;

public class ShouldBuysFragment extends Fragment implements RecyclerItemTouchHelperListener {
    private RecyclerView productsList;
    private ImageButton addButton;
    private ImageButton acceptButton;
    private List<Product> list = new ArrayList<>();
    private ThreadPoolManager tp = ThreadPoolManager.getsInstance();

    public static Fragment newInstance() {
        return new ShouldBuysFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.should_buy_products_fragment, container, false);
        productsList = v.findViewById(R.id.products_list);
        addButton = v.findViewById(R.id.add_button);
        acceptButton =v.findViewById(R.id.accept_button);
        acceptButton.setOnClickListener(v1-> tp.acceptChanges());
        addButton.setOnClickListener(v1 -> showInputDialog());
        tp.getAllShouldBuyProducts();
        tp.getShouldBuyProductsLivedata().observe(this, this::showProducts);
        ItemTouchHelper.SimpleCallback it = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(it).attachToRecyclerView(productsList);
        return v;
    }

    protected void showInputDialog() {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View promptView = layoutInflater.inflate(R.layout.input_product, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(promptView);

        final EditText productName = (EditText) promptView.findViewById(R.id.edittext);
        final EditText productCount = (EditText) promptView.findViewById(R.id.edittext2);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton(R.string.alert_add, (dialog, id) -> tp.insertProduct(new Product(productName.getText().toString(),
                        Integer.valueOf(productCount.getText().toString()),true)))
                .setNegativeButton(R.string.alert_cancel,
                        (dialog, id) -> dialog.cancel());

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    private void showProducts(List<Product> products) {
        list = products;
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        productsList.setLayoutManager(lm);
        lm.setStackFromEnd(true);
        lm.setReverseLayout(true);
        productsList.setAdapter(new RecylerAdapter<Product>(getContext(), products) {
            @Override
            public RecyclerView.ViewHolder setViewHolder(ViewGroup parent) {
                View v = LayoutInflater.from(getContext()).inflate(R.layout.shouldbuys_item, parent, false);
                ShouldBuysViewHolder pvh = new ShouldBuysViewHolder(v, getActivity());
                return pvh;
            }

            @Override
            public void onBindData(RecyclerView.ViewHolder holder, Product product) {
                ShouldBuysViewHolder pvh = (ShouldBuysViewHolder) holder;
                pvh.binViewHolder(product);

            }
        });
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        tp.deleteProduct(list.get(viewHolder.getAdapterPosition()));
    }
}
