package com.geili.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.geili.R;
import com.geili.activity.GameGiftActivity;
import com.geili.activity.GameStrategyActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dong on 2016/3/25.
 */
public class FindFragment extends BaseFragment implements View.OnClickListener {

    @Bind(R.id.layout_find_game) RelativeLayout layout_find_game;
    @Bind(R.id.layout_find_tool) RelativeLayout layout_find_tool;
    @Bind(R.id.layout_find_gift) RelativeLayout layout_find_gift;
    @Bind(R.id.layout_find_strategy) RelativeLayout layout_find_strategy;
    @Bind(R.id.layout_find_special) RelativeLayout layout_find_special;
    @Bind(R.id.layout_find_week) RelativeLayout layout_find_week;
    @Bind(R.id.layout_find_forum) RelativeLayout layout_find_forum;
    @Bind(R.id.layout_find_sign) RelativeLayout layout_find_sign;
    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_find, container, false);
        ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.layout_find_game, R.id.layout_find_tool, R.id.layout_find_gift, R.id.layout_find_strategy,
            R.id.layout_find_special, R.id.layout_find_week, R.id.layout_find_forum, R.id.layout_find_sign})
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.layout_find_game:
                break;
            case R.id.layout_find_tool:
                break;
            case R.id.layout_find_gift:
                startAnimActivity(GameGiftActivity.class);
                break;
            case R.id.layout_find_strategy:
                startAnimActivity(GameStrategyActivity.class);
                break;
            case R.id.layout_find_special:
                break;
            case R.id.layout_find_week:
                bundle.putString("category", "week");
                break;
            case R.id.layout_find_forum:
                Intent intentWebView = new Intent();
                intentWebView.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("http://wsq.discuz.qq.com/?c=index&a=forumlist&f=wx&siteid=263807124");
                intentWebView.setData(content_url);
                startActivity(intentWebView);
                break;
            case R.id.layout_find_sign:
                showToast("已签到");
                break;
        }
    }
}
