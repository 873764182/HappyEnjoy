package com.pixel.he;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.don.pieviewlibrary.LinePieView;
import com.pixel.he.bean.SzBean;
import com.pixel.he.utils.ListUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pixel.database.library.SqlTemplate;

public class SzTjActivity extends BaseActivity {

    private TextView mTextBack;
    private LinePieView mPieView;

    @Override
    protected int $(Bundle bundle) {
        return R.layout.activity_sz_tj;
    }

    @Override
    protected void init(Bundle bundle) {

        mTextBack = (TextView) findViewById(R.id.textBack);
        mPieView = (LinePieView) findViewById(R.id.pieView);

        mTextBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAfterTransition();
            }
        });

        String type = getIntent().getStringExtra("TYPE");
        List<SzBean> list = new ArrayList<>();
        if ("收入".equalsIgnoreCase(type)) {
            list = SqlTemplate.query(SzBean.class, "收入", "type");
        } else {
            list = SqlTemplate.query(SzBean.class, "支出", "type");
        }

        if (list == null) return;

        Map<Object, List<SzBean>> map = ListUtil.doGroup(list, true);

        int[] data = new int[map.size()];
        String[] name = new String[map.size()];
        int flag = 0;
        for (Map.Entry<Object, List<SzBean>> entry : map.entrySet()) {
            name[flag] = entry.getKey().toString();
            data[flag] = sum(entry.getValue());
            flag += 1;
        }

        mPieView.setData(data, name);
    }

    protected int sum(List<SzBean> szBeanList) {
        int sum = 0;
        for (SzBean bean : szBeanList) {
            try {
                sum += Integer.parseInt(bean.amount);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sum;
    }
}
