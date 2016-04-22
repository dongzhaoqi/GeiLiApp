/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Raphaël Bussa
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.geili.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.geili.R;
import com.geili.fragment.AccountFragment;
import com.geili.fragment.FindFragment;
import com.geili.fragment.HomePageFragment;
import com.geili.fragment.ManageFragment;
import com.geili.view.CustomApplication;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import rebus.header.view.HeaderInterface;
import rebus.header.view.HeaderView;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.nav_view) NavigationView navigationView;
    @Bind(R.id.drawer_layout) DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private BottomBar mBottomBar;
    private int pos;
    private static long firstTime;

    private Fragment[] fragments;
    private AccountFragment accountFragment;
    private ManageFragment manageFragment;
    private HomePageFragment homePageFragment;
    private FindFragment findFragment;
    private FragmentManager fragmentManager = getFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
        mBottomBar = BottomBar.attach(this, savedInstanceState);
        initFragment();
        initBottom();
    }


    /**
     * 初始化View
     */
    private void initView() {

        if (toolbar != null) {
            toolbar.setTitle(getString(R.string.app_name));
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        navigationView.addHeaderView(headerView());
        navigationView.setNavigationItemSelectedListener(this);
        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.post(new Runnable() {
            @Override
            public void run() {
                actionBarDrawerToggle.syncState();
            }
        });
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
    }


    /**
     * 初始化fragment并显示第一个
     */
    private void initFragment() {
        accountFragment = new AccountFragment();
        manageFragment = new ManageFragment();
        homePageFragment = new HomePageFragment();
        findFragment = new FindFragment();

        fragments = new Fragment[]{accountFragment, manageFragment, homePageFragment, findFragment};
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.fade_in, R.animator.fade_out)
                .replace(R.id.fragment_container, accountFragment)
                .commit();
    }

    /**
     * 初始化侧边栏
     *
     * @return
     */
    private HeaderView headerView() {
        HeaderView headerView = new HeaderView(MainActivity.this, true);
        headerView.background().setBackgroundColor(getResources().getColor(R.color.primary_dark));
        /*Picasso.with(MainActivity.this)
                .load(getString(R.string.url_img_header))
                .into(headerView.background());*/
        Picasso.with(MainActivity.this)
                .load(getString(R.string.url_img_profile))
                .into(headerView.avatar());
        String userName = ((CustomApplication) getApplication()).getmUser().getUserName();
        if (userName != null) {
            headerView.username(userName);
        }

        //点击头像
        headerView.setOnAvatarClickListener(new HeaderInterface.OnAvatarClickListener() {
            @Override
            public void onClick(View view) {
                showToast(getString(R.string.avatar_click));
            }
        });

        return headerView;
    }


    /**
     * 初始化底部导航栏
     */
    private void initBottom() {

        mBottomBar.noNavBarGoodness();
        mBottomBar.noTopOffset();
        mBottomBar.useFixedMode();
        mBottomBar.setItemsFromMenu(R.menu.menu_bottom, new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.menu_account) {
                    pos = 0;
                } else if (menuItemId == R.id.menu_manage) {
                    pos = 1;
                } else if (menuItemId == R.id.menu_homepage) {
                    pos = 2;
                } else if (menuItemId == R.id.menu_find) {
                    pos = 3;
                }
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.animator.fade_in, R.animator.fade_out)
                        .replace(R.id.fragment_container, fragments[pos])
                        .commit();
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {

            }
        });

        mBottomBar.mapColorForTab(0, "#FFFCFF");
        mBottomBar.mapColorForTab(1, "#FFFCFF");
        mBottomBar.mapColorForTab(2, "#FFFCFF");
        mBottomBar.mapColorForTab(3, "#FFFCFF");

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Necessary to restore the BottomBar's state, otherwise we would
        // lose the current tab on orientation change.
        mBottomBar.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }

        if (System.currentTimeMillis() - firstTime < 2000) {
            super.onBackPressed();
        } else {
            Toast.makeText(this, "再次点击退出", Toast.LENGTH_SHORT).show();
        }
        firstTime = System.currentTimeMillis();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_scan:
                startAnimActivity(ScanActivity.class);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void closeDrawer() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
            }
        }, 400);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        closeDrawer();
        if (item.isChecked()) return false;
        switch (item.getItemId()) {
            case R.id.item_1:
                openUSB();
                break;
            case R.id.item_2:
                Toast.makeText(MainActivity.this, "item2", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_3:
                Toast.makeText(MainActivity.this, "item3", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_4:
                startAnimActivity(SettingActivity.class);
                break;
            case R.id.item_5:
                startAnimActivity(FeedBackActivity.class);
                break;
            case R.id.item_6:
                startAnimActivity(LoginActivity.class);
                break;
        }
        return false;
    }

    private void openUSB() {
        Intent mIntent = new Intent();
        ComponentName comp = new ComponentName("com.android.settings", "com.android.settings.DevelopmentSettings");
        mIntent.setComponent(comp);
        mIntent.setAction("android.intent.action.VIEW");
        startActivity(mIntent);
    }
}