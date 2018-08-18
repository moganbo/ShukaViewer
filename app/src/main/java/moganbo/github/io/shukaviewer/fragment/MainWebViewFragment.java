package moganbo.github.io.shukaviewer.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import moganbo.github.io.shukaviewer.R;
import moganbo.github.io.shukaviewer.utils.LogUtil;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainWebViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@SuppressLint("SetJavaScriptEnabled")
@EFragment(R.layout.fragment_main_web_view)
public class MainWebViewFragment extends BaseFragment {

    public static final String TAG = MainWebViewFragment.class.getSimpleName();

    public MainWebViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MainWebViewFragment.
     */
    public static MainWebViewFragment newInstance() {
        MainWebViewFragment fragment = new MainWebViewFragment_();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @ViewById(R.id.fragment_main_web_view_web_view)
    protected WebView webView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    protected void afterViews() {
        LogUtil.d();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.loadUrl("https://shuka-land.jp/");
        //webView.loadUrl("https://www.google.co.jp/");
    }
}
