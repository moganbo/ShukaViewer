package moganbo.github.io.shukaviewer.activity;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import moganbo.github.io.shukaviewer.R;
import moganbo.github.io.shukaviewer.fragment.BaseFragment;
import moganbo.github.io.shukaviewer.fragment.MainWebViewFragment;
import moganbo.github.io.shukaviewer.utils.LogUtil;

@SuppressLint("Registered")
@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @ViewById(R.id.activity_main_progress_view)
    protected View progressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    protected void afterViews() {
        replaceFragment(R.id.activity_main_container,
                MainWebViewFragment.newInstance(),
                MainWebViewFragment.TAG, true);
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getNowFragment();
        if (fragment != null && fragment instanceof BaseFragment) {
            ((BaseFragment) fragment).onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    // プログレスの表示回数をカウントし、適切に表示非表示ができるように制御
    private int progressShowCount = 0;

    /**
     * プログレス表示
     */
    public void showProgress() {
        LogUtil.d("progressShowCount:" + progressShowCount);
        if (progressView != null) {
            progressShowCount++;
            progressView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * プログレス非表示
     */
    public void hideProgress() {
        LogUtil.d("progressShowCount:" + progressShowCount);
        if (progressView != null) {
            progressShowCount--;
            if (progressShowCount > 0) {
                return;
            }
            progressShowCount = 0;
            progressView.setVisibility(View.GONE);
        }
    }

    /**
     * プログレス非表示(強制)
     */
    public void hideProgressForces() {
        LogUtil.d("progressShowCount:" + progressShowCount);
        if (progressView != null) {
            progressShowCount = 0;
            progressView.setVisibility(View.GONE);
        }
    }
}
