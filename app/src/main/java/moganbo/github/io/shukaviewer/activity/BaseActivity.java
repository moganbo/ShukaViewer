package moganbo.github.io.shukaviewer.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import moganbo.github.io.shukaviewer.utils.LogUtil;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // フラグメント追加処理
    protected void addFragment(int viewId, Fragment fragment, String tag, boolean isAddToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(viewId, fragment, tag);
        if (isAddToBackStack) {
            transaction.addToBackStack(tag);
        }
        transaction.commit();
    }

    // フラグメント置き換え処理
    public void replaceFragment(int viewId, Fragment fragment, String tag, boolean isAddToBackStack) {
        LogUtil.d("replaceFragment");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.replace(viewId, fragment, tag);
        if (isAddToBackStack) {
            transaction.addToBackStack(tag);
        }
        transaction.commit();
    }

    /**
     * 現在表示中のフラグメント取得
     *
     * @return 現在表示中のフラグメント(取得できなかった場合はNullを返却)
     */
    public Fragment getNowFragment() {
        FragmentManager manager = getSupportFragmentManager();
        if (manager == null || manager.getFragments().size() == 0) {
            return null;
        }
        for (int i = manager.getFragments().size() - 1; i >= 0; i--) {
            Fragment fragment = manager.getFragments().get(i);
            if (fragment.isVisible()) {
                return fragment;
            }
        }
        return manager.getFragments().get(0);
    }
}
