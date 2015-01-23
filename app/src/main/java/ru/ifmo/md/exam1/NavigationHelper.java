package ru.ifmo.md.exam1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by german on 22.01.15.
 */
public class NavigationHelper {

    private final FragmentManager manager;

    public NavigationHelper(FragmentManager manager) {
        this.manager = manager;
    }

    public void openFragment(Class<? extends Fragment> clazz, Bundle args, boolean addToBackStack) {
        Fragment fragment = createFragment(clazz, args);
        FragmentTransaction ft = manager.beginTransaction().replace(R.id.container, fragment);
        if (addToBackStack) {
            ft.addToBackStack(null);
        }
        ft.commit();
    }

    private Fragment createFragment(Class<? extends Fragment> clazz, Bundle args) {
        try {
            Fragment fragment = clazz.getConstructor().newInstance();
            fragment.setArguments(args);
            return fragment;
        } catch (Exception e) {
            throw new RuntimeException("Cannot create fragment", e);
        }
    }

}
