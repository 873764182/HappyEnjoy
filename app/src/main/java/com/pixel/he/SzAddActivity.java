package com.pixel.he;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.pixel.he.bean.SzBean;
import com.pixel.he.utils.DateUtil;

import java.util.Calendar;
import java.util.Date;

import pixel.database.library.SqlTemplate;

public class SzAddActivity extends BaseActivity {

    private TextView mTextBack;
    private TextView mTextSave;
    private EditText mEditDate;
    private EditText mEditDescribe;
    private EditText mEditAmount;
    private EditText mEditNote;

    private String type = "收入";

    @Override
    protected int $(Bundle bundle) {
        return R.layout.activity_sz_add;
    }

    @Override
    protected void init(Bundle bundle) {

        mTextBack = (TextView) findViewById(R.id.textBack);
        mTextSave = (TextView) findViewById(R.id.textSave);
        mEditDate = (EditText) findViewById(R.id.editDate);
        mEditDescribe = (EditText) findViewById(R.id.editDescribe);
        mEditAmount = (EditText) findViewById(R.id.editAmount);
        mEditNote = (EditText) findViewById(R.id.editNote);

        type = getIntent().getStringExtra("TYPE");

        mTextBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(0);
                finish();
            }
        });

        mTextSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

        mEditDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //时间选择器
                TimePickerView pvTime = new TimePickerView.Builder(activity, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        mEditDate.setText(DateUtil.getStringByDate(date));
                    }
                }).build();
                pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                pvTime.show();
            }
        });

        mEditDescribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] menuItem = null;
                if ("收入".equalsIgnoreCase(type)) {
                    menuItem = new String[]{"工资", "补贴", "奖金", "其他"};
                } else {
                    menuItem = new String[]{"购物", "房租", "旅游", "其他"};
                }
                final String[] finalMenuItem = menuItem;
                new AlertDialog.Builder(activity).setItems(menuItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mEditDescribe.setText(finalMenuItem[which]);
                    }
                }).create().show();
            }
        });
    }

    protected void saveData() {
        String date = mEditDate.getText().toString();
        String amount = mEditAmount.getText().toString();
        String note = mEditNote.getText().toString();
        String describe = mEditDescribe.getText().toString();
        if (date.length() <= 0 || amount.length() <= 0 || note.length() <= 0 || describe.length() <= 0) {
            Toast.makeText(activity, "数据不完整", Toast.LENGTH_SHORT).show();
            return;
        }

        String[] dateArray = date.split("-");
        if (dateArray.length < 4) {
            return;
        }

        SzBean bean = new SzBean();
        bean.amount = amount;
        bean.dateTime = System.currentTimeMillis();
        bean.note = note;
        bean.type = type;
        bean.describe = describe;
        bean.year = dateArray[0];
        bean.month = dateArray[1];
        bean.day = dateArray[2];
        bean.hour = dateArray[3];
        bean.minutes = dateArray[4];
        SqlTemplate.insert(bean);

        Toast.makeText(activity, "完成", Toast.LENGTH_SHORT).show();

        if ("收入".equalsIgnoreCase(type)) {
            setResult(1);
        } else {
            setResult(-1);
        }
        finish();
    }
}
