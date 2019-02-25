package com.example.afp.myapplication;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.afp.myapplication.Recycler.RecylerAdapter;
import com.example.afp.myapplication.db.App;
import com.example.afp.myapplication.db.AppDataBase;
import com.example.afp.myapplication.fragments.DishesFragment;
import com.example.afp.myapplication.fragments.ProductsFragment;
import com.example.afp.myapplication.fragments.ShouldBuysFragment;
import com.example.afp.myapplication.thread.ThreadPoolManager;

import java.util.List;
import java.util.Observable;
import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements Navigation {

    private FragmentManager fragmentManager;
    private int transactionId;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    navigateTo(ProductsFragment.newInstance(), true);
                    return true;
                case R.id.navigation_dashboard:
                    navigateTo(DishesFragment.newInstance(), true);
                    return true;
                case R.id.navigation_notifications:
                    navigateTo(ShouldBuysFragment.newInstance(), true);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);

        if (fragment == null){
            fragment = ProductsFragment.newInstance();
            transactionId = fragmentManager.beginTransaction()
                    .add(R.id.fragment_container,fragment, "products")
                    .commit();
        }


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void navigateTo(Fragment fragment, boolean addToBackstack) {
        FragmentTransaction transaction =
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragment);

        transactionId = transaction.commit();
    }
}
