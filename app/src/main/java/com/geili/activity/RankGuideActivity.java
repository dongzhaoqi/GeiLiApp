package com.geili.activity;

import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.geili.R;
import com.geili.fragment.NetworkFragment;
import com.geili.fragment.SingleFragment;
import com.geili.fragment.ToolFragment;

import net.yanzm.mth.MaterialTabHost;

import java.util.Locale;

/**
 * Created by Dong on 4/3/2016.
 */
public class RankGuideActivity extends BaseActivity {

    private String rankCategory;
    private Toolbar toolbar;
    private String title = "";
    private int pos = 0;
    private NetworkFragment networkFragment;
    private SingleFragment singleFragment;
    private ToolFragment toolFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_guide);
        Bundle bundle = getIntent().getExtras();
        rankCategory = bundle.getString("rankCategory");
        initTitle();
        initView();
    }

    private void initTitle(){
        if(rankCategory.equals("360")){
            title = "360排行";
        }else if(rankCategory.equals("baidu")){
            title = "百度排行";
        }

        MaterialTabHost tabHost = (MaterialTabHost) findViewById(R.id.tabhost);
        tabHost.setType(MaterialTabHost.Type.FullScreenWidth);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tabHost.setElevation(0);
        }

        final SectionsPagerAdapter pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        for (int i = 0; i < pagerAdapter.getCount(); i++) {
            tabHost.addTab(pagerAdapter.getPageTitle(i));
        }

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(tabHost);
        viewPager.setCurrentItem(pos);
        tabHost.setOnTabChangeListener(new MaterialTabHost.OnTabChangeListener() {
            @Override
            public void onTabSelected(int position) {
                viewPager.setCurrentItem(position);
                Log.d("guide", pagerAdapter.getPageTitle(position).toString());
            }
        });
    }
    private void initView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle(title);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        networkFragment = new NetworkFragment();
        singleFragment = new SingleFragment();
        toolFragment = new ToolFragment();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        FragmentManager fm;
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fm = fm;
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putString("rankCategory", rankCategory);
            if(position == 0){
                networkFragment.setArguments(bundle);
                return networkFragment;
            }else if(position == 1){
                singleFragment.setArguments(bundle);
                return singleFragment;
            }else if(position == 2){
                toolFragment.setArguments(bundle);
                return toolFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.str_network);
                case 1:
                    return getString(R.string.str_single);
                case 2:
                    return getString(R.string.str_tool);
            }
            return null;
        }
    }
}
