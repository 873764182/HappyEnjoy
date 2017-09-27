package com.pixel.he;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.pixel.he.bean.SzBean;
import com.pixel.he.utils.ConfigUtil;

import java.util.Random;

import pixel.database.library.SqlTemplate;

public class MainActivity extends BaseActivity {

    private TextView mText1;
    private TextView mText2;

    @Override
    protected int $(Bundle bundle) {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle bundle) {

        mText1 = (TextView) findViewById(R.id.text_1);
        mText2 = (TextView) findViewById(R.id.text_2);

        initData();

        mText1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SZActivity.class),
                        ActivityOptions.makeSceneTransitionAnimation(activity, mText1, "shareName").toBundle());
            }
        });

        mText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ZCActivity.class),
                        ActivityOptions.makeSceneTransitionAnimation(activity, mText2, "shareName").toBundle());
            }
        });
    }

    protected void initData() {

        if (ConfigUtil.getBoolean(activity, "IS_INIT_DATA")) {
            return;
        }

        for (int i = 0; i < 50; i++) {
            SzBean bean = new SzBean();
            bean.amount = getNum(10, 100) + "";
            bean.dateTime = System.currentTimeMillis();
            bean.note = "测试数据，测试插入的。";
            bean.type = getType();
            bean.describe = "工资／购物";
            bean.year = getDate(getNum(0, 6));
            bean.month = 10 + "";
            bean.day = 22 + "";
            bean.hour = 12 + "";
            bean.minutes = 59 + "";
            SqlTemplate.insert(bean);
        }

        ConfigUtil.saveBoolean(activity, "IS_INIT_DATA", true);

    }

    protected int getNum(int min, int max) {
        return new Random().nextInt(max - min) + min;
    }

    protected String getType() {
        int num = getNum(1, 10);
        if (num % 2 == 0) {
            return "收入";
        } else {
            return "支出";
        }
    }

    protected String getDate(int flag) {
        if (flag == 1) {
            return "2017";
        } else if (flag == 2) {
            return "2016";
        } else if (flag == 3) {
            return "2015";
        } else if (flag == 4) {
            return "2014";
        } else if (flag == 5) {
            return "2013";
        } else {
            return "2012";
        }
    }

}
