package moganbo.github.io.shukaviewer.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.view.View;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import moganbo.github.io.shukaviewer.R;
import moganbo.github.io.shukaviewer.constants.AppConstants;
import moganbo.github.io.shukaviewer.fragment.BaseFragment;
import moganbo.github.io.shukaviewer.fragment.CommonDialogFragment;
import moganbo.github.io.shukaviewer.fragment.DrawerFragment;
import moganbo.github.io.shukaviewer.fragment.MainWebViewFragment;
import moganbo.github.io.shukaviewer.ui.CommonHeader;
import moganbo.github.io.shukaviewer.utils.LogUtil;
import moganbo.github.io.shukaviewer.utils.StringUtil;

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
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close) {
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
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
        if (fragment != null && fragment instanceof BaseFragment) {
            ((BaseFragment) fragment).onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    public void showDrawer() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void openOptionDialog() {
        final Fragment fragment = getNowFragment();
        if (fragment == null || !(fragment instanceof MainWebViewFragment)) {
            return;
        }
        new CommonDialogFragment.Builder(this)
                .message("ブラウザで開きますか？")
                .type(CommonDialogFragment.CommonDialog.DialogType.DOUBLE_BUTTONS)
                .positive("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        openBrowser(((MainWebViewFragment) fragment).getNowUrl());
                    }
                })
                .negative("NO", null)
                .show();
    }

    private void openBrowser(String url) {
        LogUtil.d("url:" + url);
        if (!StringUtil.isNullOrEmpty(url)) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    public void setHeaderText(String string) {
        if (commonHeader != null) {
            commonHeader.setText(string);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length == 0){
            return;
        }
        switch (requestCode){
            case AppConstants.PermissonRequestCode.FROM_MUSEUM:
                break;

            case AppConstants.PermissonRequestCode.FROM_BOOK_STAND:
                break;
        }
    }
}
