package com.worldline.techforum.obfuscation.ui;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Francois Lolom on 06/05/2016.
 */
public class MainActivity extends AppCompatActivity {

    private BottomBar bottomBar;
    @Bind(R.id.main_coordinator) CoordinatorLayout coordinatorLayout;
    @Bind(R.id.main_container) ViewGroup mainContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        bottomBar = BottomBar.attachShy(coordinatorLayout, mainContainer, savedInstanceState);

//        bottomBar = BottomBar.attach(this, savedInstanceState);
        bottomBar.setItemsFromMenu(R.menu.bottombar_menu, new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.bottombar_events) {
                    // The user selected item number one.
                    Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);
                    if (fragment != null && fragment instanceof TalksFragment) {
                        TalksFragment talksFragment = (TalksFragment) fragment;
                        talksFragment.switchToAllTalks();
                    } else {
                        TalksFragment talksFragment = TalksFragment.newInstance(false);
                        replaceFragment(talksFragment);
                    }

                } else if (menuItemId == R.id.bottombar_favorites) {
                    // The user selected item number one.
                    Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);
                    if (fragment != null && fragment instanceof TalksFragment) {
                        TalksFragment talksFragment = (TalksFragment) fragment;
                        talksFragment.switchToFavoritesTalks();
                    } else {
                        TalksFragment talksFragment = TalksFragment.newInstance(true);
                        replaceFragment(talksFragment);
                    }
                } else if (menuItemId == R.id.bottombar_about) {
                    replaceFragment(AboutFragment.newInstance());
                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                BottomBarFragment reselectedFragment = (BottomBarFragment) getSupportFragmentManager().findFragmentById(R.id.main_container);

                if (reselectedFragment != null) {
                    reselectedFragment.scrollToTop();
                }
            }
        });

        // scroll trick to hide bottom bar on scroll

    }

    private void replaceFragment(BottomBarFragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, fragment)
                .commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Necessary to restore the BottomBar's state, otherwise we would
        // lose the current tab on orientation change.
        bottomBar.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ButterKnife.unbind(this);
    }
}
