package com.geili.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.geili.R;
import com.geili.bean.Game;
import com.geili.fragment.GameGiftFragment;
import com.geili.fragment.GameIntroFragment;
import com.geili.fragment.GameReviewFragment;
import com.geili.fragment.GameStrategyFragment;
import com.squareup.picasso.Picasso;

import net.yanzm.mth.MaterialTabHost;

import java.util.Locale;

/**
 * Created by Dong on 2016/4/8.
 */
public class GameInfoActivity extends BaseActivity {

    private ImageView img_game;
    private TextView tv_game_title;
    private TextView tv_game_emulator;
    private TextView tv_game_size;
    private TextView tv_game_download;
    private int pos = 0;
    private String id;
    private GameIntroFragment mGameIntroFragment;
    private GameReviewFragment mGameReviewFragment;
    private GameStrategyFragment mGameStrategyFragment;
    private GameGiftFragment mGameGiftFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_info);

        initView();
    }

    private void initView(){
        img_game = (ImageView) findViewById(R.id.img_game);
        tv_game_title = (TextView) findViewById(R.id.tv_game_title);
        tv_game_emulator = (TextView) findViewById(R.id.tv_game_emulator);
        tv_game_size = (TextView) findViewById(R.id.tv_game_size);
        tv_game_download = (TextView) findViewById(R.id.tv_game_download);
        MaterialTabHost tabHost = (MaterialTabHost) findViewById(R.id.tabhost);

        Game game = (Game) getIntent().getExtras().getSerializable("game");
        Picasso.with(GameInfoActivity.this)
                .load(game.getImage())
                .into(img_game);
        id = game.getId();
        tv_game_title.setText(game.getTitle());
        tv_game_size.setText(game.getSize());
        tv_game_download.setText("下载量: " + game.getDownloadNumber());


        tabHost.setType(MaterialTabHost.Type.FullScreenWidth);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tabHost.setElevation(0);
        }

        mGameIntroFragment = new GameIntroFragment();
        mGameReviewFragment = new GameReviewFragment();
        mGameStrategyFragment = new GameStrategyFragment();
        mGameGiftFragment = new GameGiftFragment();

        SectionsPagerAdapter pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
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
            }
        });

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putString("id", id);
            if(position == 0){
                mGameIntroFragment.setArguments(bundle);
                return mGameIntroFragment;
            }else if(position == 1){
                return mGameReviewFragment;
            }else if(position == 2){
                return mGameStrategyFragment;
            }else {
                return mGameGiftFragment;
            }
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.str_intro).toUpperCase(l);
                case 1:
                    return getString(R.string.str_review).toUpperCase(l);
                case 2:
                    return getString(R.string.str_strategy).toUpperCase(l);
                case 3:
                    return getString(R.string.str_gift).toUpperCase(l);
            }
            return null;
        }
    }
}
