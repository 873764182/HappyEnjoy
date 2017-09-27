package com.pixel.he;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.don.pieviewlibrary.AnimationPercentPieView;
import com.pixel.he.adapter.SzAdapter;
import com.pixel.he.bean.SzBean;

import java.util.ArrayList;
import java.util.List;

import pixel.database.library.SqlTemplate;

public class SZActivity extends BaseActivity {

    private TextView mBtnTitleLeft;
    private TextView mBtnTitleRight;
    private TextView mTextZC;
    private TextView mTextSR;
    private RecyclerView mRoomRecyclerView;
    private AnimationPercentPieView mAnimationPercentPieView;

    private List<SzBean> beanList = new ArrayList<>();
    private SzAdapter listAdapter;
    private int zsr = 0, zzc = 0;

    @Override
    protected int $(Bundle bundle) {

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE); // 沉浸式状态栏
        getWindow().setStatusBarColor(Color.TRANSPARENT);  // 状态栏颜色透明

        return R.layout.activity_sz;
    }

    @Override
    protected void init(Bundle bundle) {

        mBtnTitleLeft = (TextView) findViewById(R.id.btnTitleLeft);
        mBtnTitleRight = (TextView) findViewById(R.id.btnTitleRight);
        mTextZC = (TextView) findViewById(R.id.textZC);
        mTextSR = (TextView) findViewById(R.id.textSR);
        mRoomRecyclerView = (RecyclerView) findViewById(R.id.roomRecyclerView);
        mAnimationPercentPieView = (AnimationPercentPieView) findViewById(R.id.animationPercentPieView);

        mBtnTitleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                finish();
                finishAfterTransition();
            }
        });
        mBtnTitleRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu menu = new PopupMenu(activity, mBtnTitleRight);
                menu.getMenuInflater().inflate(R.menu.add_sz, menu.getMenu());
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.add_s) {
                            Intent intent = new Intent(activity, SzAddActivity.class);
                            intent.putExtra("TYPE", "收入");
                            startActivityForResult(intent, 100);
                        } else if (item.getItemId() == R.id.add_z) {
                            Intent intent = new Intent(activity, SzAddActivity.class);
                            intent.putExtra("TYPE", "支出");
                            startActivityForResult(intent, 100);
                        } else if (item.getItemId() == R.id.add_stj) {
                            Intent intent = new Intent(activity, SzTjActivity.class);
                            intent.putExtra("TYPE", "收入");
                            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity, mBtnTitleRight, "shareName").toBundle());
                        } else if (item.getItemId() == R.id.add_ztj) {
                            Intent intent = new Intent(activity, SzTjActivity.class);
                            intent.putExtra("TYPE", "支出");
                            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity, mBtnTitleRight, "shareName").toBundle());
                        }
                        return true;
                    }
                });
                menu.show();
            }
        });

        mTextSR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData("收入");
            }
        });

        mTextZC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData("支出");
            }
        });


        mRoomRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRoomRecyclerView.setAdapter(listAdapter = new SzAdapter(activity, beanList, "year"));
        mRoomRecyclerView.setNestedScrollingEnabled(false); // 解决滑动不流畅问题

        mTextSR.callOnClick();

        initView();
    }

    protected void initView() {

        List<SzBean> list_sr = SqlTemplate.query(SzBean.class, "收入", "type");
        zsr = 0;
        for (SzBean bean : list_sr) {
            try {
                zsr += Integer.parseInt(bean.amount);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        List<SzBean> list_zc = SqlTemplate.query(SzBean.class, "支出", "type");
        zzc = 0;
        for (SzBean bean : list_zc) {
            try {
                zzc += Integer.parseInt(bean.amount);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        int[] data = new int[]{zsr, zzc};
        String[] name = new String[]{"收入", "支出"};
        mAnimationPercentPieView.setData(data, name);

        mTextSR.setText("收入(" + zsr + ")");
        mTextZC.setText("支出(" + zzc + ")");
    }

    protected void initData(String type) {   // 1, -1
        List<SzBean> szBeanList = SqlTemplate.query(SzBean.class, type, "type");
        if (szBeanList != null) {
            beanList.clear();
            beanList.addAll(szBeanList);
        }

        listAdapter.notifyDataSetChanged();

//        if ("收入".equalsIgnoreCase(type)) {
//            mTextSR.setText("收入(" + beanList.size() + ")");
//        } else {
//            mTextZC.setText("支出(" + beanList.size() + ")");
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == 1) {
            mTextSR.callOnClick();
        } else if (requestCode == 100 && resultCode == -1) {
            mTextZC.callOnClick();
        }
    }
}
