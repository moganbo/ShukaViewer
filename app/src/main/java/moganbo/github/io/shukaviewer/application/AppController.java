package moganbo.github.io.shukaviewer.application;

import android.app.Application;
import android.content.Context;

import com.os.operando.garum.Garum;

import moganbo.github.io.shukaviewer.constants.AppFlags;
import moganbo.github.io.shukaviewer.utils.LogUtil;

public class AppController extends Application {
    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.d();
        appContext = getApplicationContext();
        Garum.initialize(appContext);
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
