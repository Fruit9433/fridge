package com.example.afp.myapplication;

import android.support.v4.app.Fragment;

public interface Navigation {
    void navigateTo(Fragment fragment, boolean addToBackstack);
}
