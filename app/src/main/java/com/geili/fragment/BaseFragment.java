package com.geili.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.geili.dialog.CustomProgressDialog;

/**
 * Created by Dong on 2016/3/25.
 */
public class BaseFragment extends Fragment {

    private Toast mToast;
    public CustomProgressDialog customProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customProgressDialog = new CustomProgressDialog(getActivity(), "loading..");
        customProgressDialog.setCancelable(true);
        customProgressDialog.setCanceledOnTouchOutside(true);
    }

    public void showToast(final String str) {
        if (!TextUtils.isEmpty(str)) {
            getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (mToast == null) {
                        mToast = Toast.makeText(getActivity(), str,
                                Toast.LENGTH_LONG);
                    } else {
                        mToast.setText(str);
                    }
                    mToast.show();
                }
            });
        }
    }

    public void showToast(int resId) {
        final String str = getResources().getString(resId);
        if (!TextUtils.isEmpty(str)) {
            getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (mToast == null) {
                        mToast = Toast.makeText(getActivity(), str,
                                Toast.LENGTH_LONG);
                    } else {
                        mToast.setText(str);
                    }
                    mToast.show();
                }
            });
        }
    }

    public void startAnimActivity(Class<?> cla) {
        this.startActivity(new Intent(getActivity(), cla));
    }

    public void startAnimActivity(Intent intent) {
        this.startActivity(intent);
    }

    public void showProcessDialog() {

        if (customProgressDialog == null){
            customProgressDialog = new CustomProgressDialog(getActivity(), "loading...");
            customProgressDialog.show();
            customProgressDialog.setCanceledOnTouchOutside(true);
        }
        if (customProgressDialog.isShowing() == false)
            customProgressDialog.show();

    }

    public void dismissProcessDialog() {

        if (customProgressDialog != null)
            customProgressDialog.dismiss();

    }

}
