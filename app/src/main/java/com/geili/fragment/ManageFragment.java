package com.geili.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geili.R;
import com.geili.adapter.AppListAdapter;
import com.geili.util.CommonUtils;


/**
 * Created by Dong on 2016/3/25.
 */
public class ManageFragment extends BaseFragment {

    private View mView;
    private RecyclerView rv_apps;

    /**
     * 此处要设置layoutManager,否则会出现 No layout manager attached; skipping layout错误
     */
    private RecyclerView.LayoutManager layoutManager;
    private AppListAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_manage,null);

        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(mView);
    }

    private void initView(View view) {
        rv_apps = (RecyclerView) view.findViewById(R.id.rv_apps);
        layoutManager = new LinearLayoutManager(getActivity());
        rv_apps.setLayoutManager(layoutManager);
        //rv_apps.setLayoutManager(new GridLayoutManager(getActivity(), 2));//这里用线性宫格显示 类似于grid view
        //rv_apps.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));//这里用线性宫格显示 类似于瀑布流

        mAdapter = new AppListAdapter(getActivity(), CommonUtils.getInstalledApp(getActivity()), getActivity().getPackageManager());
        rv_apps.setAdapter(mAdapter);
    }


}
