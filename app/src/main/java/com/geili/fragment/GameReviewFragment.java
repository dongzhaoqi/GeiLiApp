package com.geili.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geili.R;

/**
 * Created by Dong on 4/9/2016.
 */
public class GameReviewFragment extends Fragment {

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_game_review,container,false);

        initView(view);
        return view;
    }

    private void initView(View v){

    }
}
