package com.pixel.he;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.pixel.he.bean.SzBean;

import pixel.database.library.OnDbUpdateCallback;
import pixel.database.library.SqlTemplate;

/**
 * Created by pixel on 2017/9/26.
 */

public class HeApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SqlTemplate.initDataBase(this, "HeApp.db", 1, (OnDbUpdateCallback) null, SzBean.class);
    }
}
