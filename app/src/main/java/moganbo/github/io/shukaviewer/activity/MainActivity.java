package moganbo.github.io.shukaviewer.activity;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import moganbo.github.io.shukaviewer.R;
import moganbo.github.io.shukaviewer.fragment.MainWebViewFragment;

@SuppressLint("Registered")
@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {

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
}
