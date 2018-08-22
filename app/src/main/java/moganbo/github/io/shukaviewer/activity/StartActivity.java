package moganbo.github.io.shukaviewer.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import moganbo.github.io.shukaviewer.R;

@SuppressLint("Registered")
@EActivity(R.layout.activity_start)
public class StartActivity extends AppCompatActivity {

    private static final long SPLASH_SHOW_TIME = 2000L;

    private boolean isStartPause = false;
    private boolean isWaitTransition = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        isStartPause = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isStartPause = false;
        if (isWaitTransition) {
            toMainActivity();
        }
    }

    @AfterViews
    protected void afterViews() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                toMainActivity();
            }
        }, SPLASH_SHOW_TIME);
    }

    private void toMainActivity() {
        if (isStartPause) {
            isWaitTransition = true;
            return;
        }
        MainActivity_.intent(this)
                .flags(Intent.FLAG_ACTIVITY_CLEAR_TOP).start();
    }
}
