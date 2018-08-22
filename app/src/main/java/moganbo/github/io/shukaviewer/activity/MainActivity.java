package moganbo.github.io.shukaviewer.activity;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import moganbo.github.io.shukaviewer.R;
import moganbo.github.io.shukaviewer.fragment.BaseFragment;
import moganbo.github.io.shukaviewer.fragment.DrawerFragment;
import moganbo.github.io.shukaviewer.fragment.MainWebViewFragment;
import moganbo.github.io.shukaviewer.ui.CommonHeader;
import moganbo.github.io.shukaviewer.utils.LogUtil;

@SuppressLint("Registered")
@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @ViewById(R.id.activity_main_drawer_layout)
    protected DrawerLayout drawerLayout;
    @ViewById(R.id.activity_main_common_header)
    protected CommonHeader commonHeader;
    @ViewById(R.id.activity_main_progress_view)
    protected View progressView;

    private ActionBarDrawerToggle drawerToggle;

    DrawerFragment drawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    protected void afterViews() {
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open ,R.string.close){
            @Override
            public void onDrawerClosed(View drawerView) {
            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        };
        drawerLayout.addDrawerListener(drawerToggle);
        drawerLayout.setFocusableInTouchMode(false);
        drawerFragment = DrawerFragment.newInstance();
        replaceFragment(R.id.activity_main_drawer_fragment_content, drawerFragment,
                DrawerFragment.TAG, false);
        replaceFragment(R.id.activity_main_container,
                MainWebViewFragment.newInstance(),
                MainWebViewFragment.TAG, true);
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getNowFragment();
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
        if (fragment != null && fragment instanceof BaseFragment) {
            ((BaseFragment) fragment).onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    public void showDrawer(){
        drawerLayout.openDrawer(GravityCompat.START);
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
