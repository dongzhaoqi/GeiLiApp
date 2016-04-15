package com.geili.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.geili.R;
import com.geili.activity.RankGuideActivity;
import com.geili.view.CYRotateView;


/**
 * Created by Dong on 2016/3/25.
 */
public class HomePageFragment extends BaseFragment implements View.OnClickListener {

    private View mView;
    private Button btn_home_pic;
    private TextView txt_home_title,txt_home_info;
    private RelativeLayout layout_bg;
    private Bundle bundle = new Bundle();
    private String rankCategory,userName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_homepage,null);
        initView(mView);

        initEvent();
        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initView(View v) {

        CYRotateView rotate1 = (CYRotateView) v.findViewById(R.id.rotate1);
        CYRotateView rotate2 = (CYRotateView) v.findViewById(R.id.rotate2);
        CYRotateView rotate3 = (CYRotateView) v.findViewById(R.id.rotate3);
        CYRotateView rotate4 = (CYRotateView) v.findViewById(R.id.rotate4);
        CYRotateView rotate5 = (CYRotateView) v.findViewById(R.id.rotate5);
        CYRotateView rotate6 = (CYRotateView) v.findViewById(R.id.rotate6);

        View v11 = LayoutInflater.from(getActivity()).inflate(R.layout.view11, null);
        RelativeLayout layout11 = (RelativeLayout) v11.findViewById(R.id.layout11);
        layout11.setOnClickListener(this);
        Button button11 = (Button) v11.findViewById(R.id.button_11);
        button11.setOnClickListener(this);

        rotate1.addView(v11);
        rotate1.rorateToNext();
        rotate1.rorateToPre();
        rotate1.setIsNeedCirculate(false);

        View v21 = LayoutInflater.from(getActivity()).inflate(R.layout.view21, null);
        View v22 = LayoutInflater.from(getActivity()).inflate(R.layout.view22, null);
        RelativeLayout rank_360 = (RelativeLayout) v21.findViewById(R.id.view21);
        rank_360.setOnClickListener(this);
        Button button21 = (Button) v21.findViewById(R.id.button_21);
        button21.setOnClickListener(this);

        rotate2.addView(v21);
        rotate2.addView(v22);
        rotate2.rorateToNext();
        rotate2.rorateToPre();
        rotate2.setIsNeedCirculate(false);

        View v31 = LayoutInflater.from(getActivity()).inflate(R.layout.view31, null);

        rotate3.addView(v31);
        rotate3.rorateToNext();
        rotate3.rorateToPre();
        rotate3.setIsNeedCirculate(false);

        View v41 = LayoutInflater.from(getActivity()).inflate(R.layout.view41, null);
        View v42 = LayoutInflater.from(getActivity()).inflate(R.layout.view42, null);

        rotate4.addView(v41);
        rotate4.addView(v42);
        rotate4.rorateToNext();
        rotate4.rorateToPre();
        rotate4.setIsNeedCirculate(false);

        View v51 = LayoutInflater.from(getActivity()).inflate(R.layout.view51, null);

        rotate5.addView(v51);
        rotate5.rorateToNext();
        rotate5.rorateToPre();
        rotate5.setIsNeedCirculate(false);

        View v61 = LayoutInflater.from(getActivity()).inflate(R.layout.view61, null);
        View v62 = LayoutInflater.from(getActivity()).inflate(R.layout.view62, null);

        rotate6.addView(v61);
        rotate6.addView(v62);
        rotate6.rorateToNext();
        rotate6.rorateToPre();
        rotate6.setIsNeedCirculate(false);

    }

    private void initEvent(){

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view21:
            case R.id.button_21:
                rankCategory = "360";
                bundle.putString("rankCategory", rankCategory);
                Intent intent2 = new Intent(getActivity(),RankGuideActivity.class).putExtras(bundle);
                startActivity(intent2);

                break;
        }
    }
}
