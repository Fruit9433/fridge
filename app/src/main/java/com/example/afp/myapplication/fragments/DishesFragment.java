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

import com.example.afp.myapplication.DataBusiness.Dish;
import com.example.afp.myapplication.DataBusiness.Product;
import com.example.afp.myapplication.R;
import com.example.afp.myapplication.Recycler.ExpandableCustomItem;
import com.example.afp.myapplication.Recycler.CustomItem;
import com.example.afp.myapplication.Recycler.ProductViewHolder;
import com.example.afp.myapplication.Recycler.RecyclerItemTouchHelper;
import com.example.afp.myapplication.Recycler.RecyclerItemTouchHelperListener;
import com.example.afp.myapplication.Recycler.RecylerAdapter;
import com.example.afp.myapplication.thread.ThreadPoolManager;
import com.xwray.groupie.ExpandableGroup;
import com.xwray.groupie.GroupAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DishesFragment extends Fragment implements RecyclerItemTouchHelperListener {
    private List<Dish> list = new ArrayList<>();
    private RecyclerView dishesList;
    private ImageButton addButton;
    private ThreadPoolManager tp;
    private boolean isExpanded;

    public static Fragment newInstance() {
        return new DishesFragment();
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
        dishesList = v.findViewById(R.id.products_list);
        tp.getAllDishes();
        tp.getDishesLivedata().observe(this,this::showDishes);
        addButton = v.findViewById(R.id.add_button);
        Dish dish = new Dish("bijol",Arrays.asList(new Product("gout",11),new Product("go",3),new Product("gut",11)));
        addButton.setOnClickListener(v1 -> showInputDialog());
        ItemTouchHelper.SimpleCallback it = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(it).attachToRecyclerView(dishesList);
        return v;
    }

    private void showDishes(List<Dish> dish) {
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        dishesList.setLayoutManager(lm);
        list = dish;
        GroupAdapter groupAdapter = new GroupAdapter();
        ExpandableGroup expandableGroup;
        for (Dish d: dish) {
            expandableGroup = new ExpandableGroup(new ExpandableCustomItem(d),false);
            for (Product p: d.getIngredients()) {
                expandableGroup.add(new CustomItem<Product, ProductViewHolder>(p));
            }
            groupAdapter.add(expandableGroup);
        }

        dishesList.setAdapter(groupAdapter);
    }

    protected void showInputDialog() {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View promptView = layoutInflater.inflate(R.layout.input_dish, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(promptView);

        final EditText dishName = promptView.findViewById(R.id.input_dish_name);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton(R.string.alert_add, (dialog, id) -> {
                    if(dishName.getText().toString().equals("")) {
                        Toast.makeText(getContext(), R.string.alert_warning_name_not_input, Toast.LENGTH_SHORT).show();
                    } else {
                        tp.insertDish(new Dish(dishName.getText().toString(), new ArrayList<>()));
                    }
                })
                .setNegativeButton(R.string.alert_cancel,
                        (dialog, id) -> dialog.cancel());

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("exp", isExpanded);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        System.out.println("sds");
        tp.deleteDish(list.get(viewHolder.getAdapterPosition()));
    }
}
