package com.geili.activity;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.geili.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WeChatActivity extends BaseActivity {

    @Bind(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wechat);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        if (toolbar != null) {
            toolbar.setTitle(getString(R.string.wechat));
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

}
