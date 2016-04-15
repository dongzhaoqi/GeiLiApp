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


/**
 * Created by Dong on 3/27/2016.
 */
public class SettingActivity extends BaseActivity  implements View.OnClickListener{

    private Toolbar toolbar;
    private TextView currentValue;
    private DiscreteSeekBar seekBar;
    private RelativeLayout layout_show_pic,layout_wallpaper,layout_install_hint;
    private SwitchCompat switch_show_pic,switch_wallpaper,switch_install_hint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();
        initEvent();
    }
    private void initView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null){
            toolbar.setTitle(getString(R.string.nav_set));
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        currentValue = (TextView) findViewById(R.id.current_value);
        layout_show_pic = (RelativeLayout) findViewById(R.id.layout_show_pic);
        layout_wallpaper = (RelativeLayout) findViewById(R.id.layout_wallpaper);
        layout_install_hint = (RelativeLayout) findViewById(R.id.layout_install_hint);

        switch_show_pic = (SwitchCompat) findViewById(R.id.switch_show_pic);
        switch_wallpaper = (SwitchCompat) findViewById(R.id.switch_wallpaper);
        switch_install_hint = (SwitchCompat) findViewById(R.id.switch_install_hint);

        seekBar = (DiscreteSeekBar) findViewById(R.id.seekbar);
    }

    private void initEvent(){

        layout_show_pic.setOnClickListener(this);
        layout_wallpaper.setOnClickListener(this);
        layout_install_hint.setOnClickListener(this);


        seekBar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar discreteSeekBar, int i, boolean b) {
                currentValue.setText("当前值:" + seekBar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar discreteSeekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar discreteSeekBar) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.layout_show_pic:
                switch_show_pic.toggle();
                break;
            case R.id.layout_wallpaper:
                switch_wallpaper.toggle();
                break;
            case R.id.layout_install_hint:
                switch_install_hint.toggle();
                break;
        }
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
