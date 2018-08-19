package moganbo.github.io.shukaviewer.application;

import android.app.Application;
import android.content.Context;

import moganbo.github.io.shukaviewer.utils.LogUtil;

public class AppController extends Application {
    private static Context appContext;

    @Override
    public void onCreate() {
        LogUtil.d();
        appContext = getApplicationContext();
        super.onCreate();
    }

    /**
     * 自身のコンテキスト取得
     *
     * @return 自身のコンテキスト
     */
    public static Context getAppContext() {
        return appContext;
    }
}
