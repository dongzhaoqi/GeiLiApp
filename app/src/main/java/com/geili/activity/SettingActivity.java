package com.geili.activity;

import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.geili.R;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Dong on 3/27/2016.
 */
public class SettingActivity extends BaseActivity {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.current_value) TextView currentValue;
    @Bind(R.id.layout_show_pic) RelativeLayout layout_show_pic;
    @Bind(R.id.layout_wallpaper) RelativeLayout layout_wallpaper;
    @Bind(R.id.layout_install_hint) RelativeLayout layout_install_hint;
    @Bind(R.id.switch_show_pic) SwitchCompat switch_show_pic;
    @Bind(R.id.switch_wallpaper) SwitchCompat switch_wallpaper;
    @Bind(R.id.switch_install_hint) SwitchCompat switch_install_hint;
    @Bind(R.id.seekbar) DiscreteSeekBar seekbar;

    @OnClick(R.id.layout_show_pic) void showPic(){
        switch_show_pic.toggle();
    }

    @OnClick(R.id.layout_wallpaper) void setWallpaper(){
        switch_wallpaper.toggle();
    }

    @OnClick(R.id.layout_install_hint) void hintInstall(){
        switch_install_hint.toggle();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        initView();
        initEvent();
    }

    private void initView(){
        if(toolbar != null){
            toolbar.setTitle(getString(R.string.nav_set));
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

    private void initEvent(){

        seekbar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                currentValue.setText("当前值:" + seekBar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

            }
        });

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
}
