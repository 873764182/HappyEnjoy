package com.pixel.he;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.view.Window;

/**
 * Created by pixel on 2017/9/26.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected AppCompatActivity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 允许使用transitions
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        // 设置一个exit transition
        // getWindow().setExitTransition(new Explode());//new Slide()  new Fade()

        activity = this;

        setContentView($(savedInstanceState));

        init(savedInstanceState);
    }

    protected abstract int $(Bundle bundle);

    protected abstract void init(Bundle bundle);

}
