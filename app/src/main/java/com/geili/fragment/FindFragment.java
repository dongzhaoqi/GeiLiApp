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

/**
 * Created by Dong on 2016/3/25.
 */
public class FindFragment extends BaseFragment implements View.OnClickListener{

    private View mView;
    private RelativeLayout layout_find_game,layout_find_tool,layout_find_gift,
            layout_find_strategy,layout_find_special,
            layout_find_week,layout_find_forum,layout_find_sign;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_find,container,false);
        initView(mView);
        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initEvent();
    }

    private void initView(View v) {
        layout_find_game = (RelativeLayout) v.findViewById(R.id.layout_find_game);
        layout_find_tool = (RelativeLayout) v.findViewById(R.id.layout_find_tool);
        layout_find_gift = (RelativeLayout) v.findViewById(R.id.layout_find_gift);

        layout_find_special = (RelativeLayout) v.findViewById(R.id.layout_find_special);
        layout_find_strategy = (RelativeLayout) v.findViewById(R.id.layout_find_strategy);

        layout_find_week = (RelativeLayout) v.findViewById(R.id.layout_find_week);
        layout_find_forum = (RelativeLayout) v.findViewById(R.id.layout_find_forum);
        layout_find_sign = (RelativeLayout) v.findViewById(R.id.layout_find_sign);

    }

    private void initEvent(){
        layout_find_game.setOnClickListener(this);
        layout_find_tool.setOnClickListener(this);
        layout_find_gift.setOnClickListener(this);

        layout_find_strategy.setOnClickListener(this);
        layout_find_special.setOnClickListener(this);

        layout_find_week.setOnClickListener(this);
        layout_find_forum.setOnClickListener(this);
        layout_find_sign.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();

        int id = v.getId();
        switch (id){
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
