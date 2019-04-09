package com.example.zc.planegame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {


    private long time;//用于检测按两次 "再按一次退出游戏"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent=new Intent(GameActivity.this,MusicService.class);
        startService(intent);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getSupportActionBar().hide();//隐藏标题栏

//        setContentView(new hua(this));

        //setContentView()跟swing的add()差不多吧，不过这里只能添加一个控件，默认铺满屏幕
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) { //返回键
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            long t = System.currentTimeMillis();//获取系统时间
            if (t - time <= 2000) {
                exit(); //如果500毫秒内按下两次返回键则退出游戏
            } else {
                time = t;
                Toast.makeText(getApplicationContext(), "再按一次退出游戏", Toast.LENGTH_SHORT).show();
            }

            return true;
        }
        return false;

    }

    public void exit() {
        Intent intent=new Intent(GameActivity.this,MusicService.class);
        Intent intent1=new Intent(GameActivity.this,MainActivity.class);
        GameActivity.this.finish();
        GameActivity.this.stopService(intent);
        startActivity(intent1);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.exit(0);
            }
        }).start();
    }
}


