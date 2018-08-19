package moganbo.github.io.shukaviewer.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import moganbo.github.io.shukaviewer.R;
import moganbo.github.io.shukaviewer.constants.PageUrl;
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

    private boolean isChromeAfterIndex = false;

    public boolean isChromeAfterIndex() {
        return isChromeAfterIndex;
    }

    public void setChromeAfterIndex(boolean chromeAfterIndex) {
        isChromeAfterIndex = chromeAfterIndex;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    protected void afterViews() {
        LogUtil.d();
        webView.setWebViewClient(new MyWebViewClient(this, webView));
        webView.setWebChromeClient(new MyWebChromeClient(this, webView));
        webView.addJavascriptInterface(new MyWebAppInterface(getActivity()), "Android");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
        webView.getSettings().setSupportMultipleWindows(false);
        webView.getSettings().setSupportZoom(false);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.evaluateJavascript("(function() {return window.location.href;})", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String url) {
                LogUtil.d("url:" + url);
                //do your scheme with variable "url"
            }
        });
        webView.loadUrl("https://shuka-land.jp/");
        //webView.loadUrl("https://www.google.co.jp/");
    }

    @Override
    public void onBackPressed() {
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
        } else {
            if (getActivity() != null) {
                new CommonDialogFragment.Builder(getActivity())
                        .message("アプリを終了しますか？")
                        .type(CommonDialogFragment.CommonDialog.DialogType.DOUBLE_BUTTONS)
                        .positive("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getActivity().moveTaskToBack(true);
                            }
                        })
                        .negative("NO", null)
                        .show();
            }
        }
    }
}

class MyWebViewClient extends WebViewClient {
    private static final String JS_SETVALUE =
            "javascript:document.querySelectorAll('%s').value='%s';void 0;";
    private static final String JS_CLICKANCHOR =
            "javascript:document.querySelector('%s').click();";

    private MainWebViewFragment fragment;
    private WebView webView;

    private String prevUrl = "";
    private String currentUrl = "";

    private Handler handler;

    private Runnable runnable;

    private boolean isLoading;
    private boolean isLoadFinish;

    MyWebViewClient(MainWebViewFragment fragment, WebView webView) {
        this.fragment = fragment;
        this.webView = webView;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        LogUtil.d("url:" + url);
        currentUrl = url;
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        LogUtil.d("url:" + url);
        super.onPageFinished(view, url);
        /*
        if (url.startsWith(PageUrl.SHUKA_LAND_SIGN_UP.getUrl())){
            view.loadUrl(String.format(JS_SETVALUE, "input.Textinput__input", "test@a.a"));
        }
        /**/
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        //LogUtil.d("url:" + url);
        //LogUtil.d("WebView url:" + webView.getUrl());
        if (!prevUrl.equals(webView.getUrl())) {
            prevUrl = webView.getUrl();
            onLoadStarted(webView.getUrl());
            isLoading = true;
        }
        if (isLoading) {
            if (handler == null) {
                handler = new Handler();
            } else if (runnable != null) {
                handler.removeCallbacks(runnable);
            }
            runnable = new Runnable() {
                @Override
                public void run() {
                    if (fragment.isChromeAfterIndex()) {
                        isLoading = false;
                        onLoadFinished(webView.getUrl());
                    } else {
                        handler.postDelayed(this, 1000);
                    }
                }
            };
            handler.postDelayed(runnable, 1000);
        }
        super.onLoadResource(view, url);
    }

    @Override
    public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
        LogUtil.d();
        super.onReceivedHttpError(view, request, errorResponse);
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        LogUtil.d("url:" + failingUrl);
        super.onReceivedError(view, errorCode, description, failingUrl);
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        LogUtil.d();
        super.onReceivedError(view, request, error);
    }

    @Override
    public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
        LogUtil.d("url:" + url + " isReload:" + isReload);
        if (!currentUrl.equals(url) && !isReload) {
            webView.loadUrl(url);
        }
        super.doUpdateVisitedHistory(view, url, isReload);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        LogUtil.d("url:" + url);
        return false;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        LogUtil.d("url:" + request.getUrl().toString());
        return super.shouldOverrideUrlLoading(view, request);
    }

    @Nullable
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        // LogUtil.d("url:" + request.getUrl().toString());
        return super.shouldInterceptRequest(view, request);
    }

    private void onLoadStarted(String url) {
        LogUtil.w("url:" + url);
        if (fragment != null && fragment.getMainActivity() != null) {
            fragment.getMainActivity().showProgress();
        }
    }

    private void onLoadFinished(String url) {
        LogUtil.w("url:" + url);
        if (fragment != null && fragment.getMainActivity() != null) {
            fragment.getMainActivity().hideProgressForces();
        }
    }
}

class MyWebChromeClient extends WebChromeClient {

    private static final String BEFORE_INDEX = "before index";
    private static final String AFTER_INDEX = "after index";

    private MainWebViewFragment fragment;
    private WebView webView;

    public MyWebChromeClient(MainWebViewFragment fragment, WebView webView) {
        this.fragment = fragment;
        this.webView = webView;
    }

    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        LogUtil.d("level:" + consoleMessage.messageLevel().name() +
                " line:" + consoleMessage.lineNumber() +
                " message:" + consoleMessage.message() +
                " sourceId" + consoleMessage.sourceId());
        if (consoleMessage.message().equals(BEFORE_INDEX)) {
            fragment.setChromeAfterIndex(false);
        } else if (consoleMessage.message().equals(AFTER_INDEX)) {
            fragment.setChromeAfterIndex(true);
        }
        return super.onConsoleMessage(consoleMessage);
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        LogUtil.d("url:" + view.getUrl() + " title:" + title);
    }
}

class MyWebAppInterface {
    Activity mContext;

    public MyWebAppInterface(Activity c) {
        mContext = c;
    }

    @JavascriptInterface
    public void getURL(final String url) {
        mContext.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LogUtil.d("getURL url:" + url);
            }
        });

    }

}