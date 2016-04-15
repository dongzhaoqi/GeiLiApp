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
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.geili.R;
import com.geili.fragment.AccountFragment;
import com.geili.fragment.FindFragment;
import com.geili.fragment.HomePageFragment;
import com.geili.fragment.ManageFragment;
import com.geili.view.CustomApplication;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import rebus.header.view.HeaderInterface;
import rebus.header.view.HeaderView;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private AHBottomNavigation bottomNavigation;
    private ArrayList<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();
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

        initView();

        initFragment();

        initBottom();
    }


    /**
     * 初始化View
     */
    private void initView() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle(getString(R.string.app_name));
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
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
    private void initFragment(){
        accountFragment = new AccountFragment();
        manageFragment = new ManageFragment();
        homePageFragment = new HomePageFragment();
        findFragment = new FindFragment();

        fragments = new Fragment[]{accountFragment,manageFragment,homePageFragment,findFragment};
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.fade_in, R.animator.fade_out)
                .replace(R.id.fragment_container, accountFragment)
                .commit();
    }

    /**
     * 初始化侧边栏
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
        String userName = ((CustomApplication)getApplication()).getmUser().getUserName();
        if(userName != null){
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
        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);

        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab_1, R.drawable.tab_account_selector, R.color.color_tab_1);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab_2, R.drawable.tab_manage_selector, R.color.color_tab_2);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.tab_3, R.drawable.tab_homepage_selector, R.color.color_tab_3);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.tab_4, R.drawable.tab_find_selector, R.color.color_tab_4);

        bottomNavigationItems.add(item1);
        bottomNavigationItems.add(item2);
        bottomNavigationItems.add(item3);
        bottomNavigationItems.add(item4);

        bottomNavigation.addItems(bottomNavigationItems);
        bottomNavigation.setAccentColor(Color.parseColor("#F63D2B"));
        bottomNavigation.setInactiveColor(Color.parseColor("#747474"));
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, boolean wasSelected) {
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.animator.fade_in, R.animator.fade_out)
                        .replace(R.id.fragment_container, fragments[position])
                        .commit();
            }
        });
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
        switch (item.getItemId()){
            case R.id.item_1:
                openUSB();
                break;
            case R.id.item_2:
                Toast.makeText(MainActivity.this,"item2",Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_3:
                Toast.makeText(MainActivity.this,"item3",Toast.LENGTH_SHORT).show();
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

    private void openUSB(){
        Intent mIntent = new Intent();
        ComponentName comp = new ComponentName("com.android.settings", "com.android.settings.DevelopmentSettings");
        mIntent.setComponent(comp);
        mIntent.setAction("android.intent.action.VIEW");
        startActivity(mIntent);
    }
}