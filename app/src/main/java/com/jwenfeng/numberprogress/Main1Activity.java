package com.jwenfeng.numberprogress;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * 当前类注释:
 * 项目名：NumberProgress
 * 包名：com.jwenfeng.numberprogress
 * 作者：jinwenfeng on 16/3/28 20:40
 * 邮箱：823546371@qq.com
 * QQ： 823546371
 * 公司：南京穆尊信息科技有限公司
 * © 2016 jinwenfeng
 * ©版权所有，未经允许不得传播
 */
public class Main1Activity extends AppCompatActivity {

    private CircleNumberProgress circleNumberProgress;
    private int current = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        circleNumberProgress = (CircleNumberProgress) findViewById(R.id.number_circle);
        final Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                current++;
                if (current > 100) {
                    current = 0;
                }
                circleNumberProgress.setCurrentProgress(current);
                handler1.postDelayed(this, 100);
            }
        }, 100);

    }
}
