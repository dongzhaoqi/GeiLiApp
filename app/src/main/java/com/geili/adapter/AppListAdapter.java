package com.geili.adapter;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.geili.R;
import com.geili.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Dong on 2016/3/25.
 */
public class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.ViewHolder> {

    private List mDataset = new ArrayList<>();
    private PackageManager mPackageManager;
    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        private ImageView mImageView;
        private Button mUnloadButton;
        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.txt_app_name);
            mImageView = (ImageView) v.findViewById(R.id.image_app);
            mUnloadButton = (Button) v.findViewById(R.id.btn_unload);
        }
    }

    public AppListAdapter(Context context,List dataset, PackageManager mPackageManager) {
        mDataset.clear();
        mDataset.addAll(dataset);
        this.mContext = context;
        this.mPackageManager = mPackageManager;
    }

    @Override
    public AppListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_app, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final ApplicationInfo appInfo = (ApplicationInfo) mDataset.get(position);

        holder.mImageView.setImageDrawable(appInfo.loadIcon(mPackageManager));
        holder.mTextView.setText(appInfo.loadLabel(mPackageManager));
        holder.mUnloadButton.setTag(appInfo.packageName);
        holder.mUnloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtils.uninstallAPK(mContext,appInfo.packageName);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}