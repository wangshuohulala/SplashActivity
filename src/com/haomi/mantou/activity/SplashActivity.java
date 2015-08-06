package com.haomi.mantou.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.haomi.mantou.R;
import com.haomi.mantou.utils.sharepreferences.UserInfoPreUtil;

public class SplashActivity extends Activity {

    private final int SPLASH_DISPLAY_LENGHT = 1000;//延迟三秒

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                if (UserInfoPreUtil.getInstance(SplashActivity.this).isLogin()) {
                    Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                } else {
                    Intent intent = new Intent(SplashActivity.this, UserLoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

        }, SPLASH_DISPLAY_LENGHT);
        createShortcut();

    }

    /**
     * 创建桌面快捷方式
     */
    private void createShortcut() {
        SharedPreferences setting = getSharedPreferences("silent.preferences", 0);
// 判断是否第一次启动应用程序（默认为true）
        boolean firstStart = setting.getBoolean("FIRST_START", true);
// 第一次启动时创建桌面快捷方式
        if (firstStart) {
            Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
// 快捷方式的名称
            shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(R.string.app_name));
// 不允许重复创建
            shortcut.putExtra("duplicate", false);
// 指定快捷方式的启动对象
            shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(this, this.getClass()).setAction(Intent.ACTION_MAIN));
// 快捷方式的图标
            Intent.ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(this, R.drawable.ic_launcher);
            shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);
// 发出广播
            sendBroadcast(shortcut);
// 将第一次启动的标识设置为false
            SharedPreferences.Editor editor = setting.edit();
            editor.putBoolean("FIRST_START", false);
// 提交设置
            editor.commit();
        }
    }
}