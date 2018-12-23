package moganbo.github.io.shukaviewer.fragment;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import com.folioreader.Config;
import com.folioreader.FolioReader;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

import java.io.File;

import moganbo.github.io.shukaviewer.BuildConfig;
import moganbo.github.io.shukaviewer.R;
import moganbo.github.io.shukaviewer.activity.MainActivity;
import moganbo.github.io.shukaviewer.constants.PageUrl;
import moganbo.github.io.shukaviewer.utils.LogUtil;
import moganbo.github.io.shukaviewer.utils.OnFileSelectedListener;
import moganbo.github.io.shukaviewer.utils.StringUtil;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DrawerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@EFragment(R.layout.fragment_drawer)
public class DrawerFragment extends Fragment {

    public static final String TAG = DrawerFragment.class.getSimpleName();

    private static final boolean IS_SHOW_EPUB_INNER = false;


    public DrawerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DrawerFragment.
     */
    public static DrawerFragment newInstance() {
        DrawerFragment fragment = new DrawerFragment_();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @AfterViews
    protected void afterViews() {

    }

    @Click(R.id.fragment_drawer_land_text_view)
    protected void onClickLandTextView() {
        loadPage(PageUrl.SHUKA_LAND_TOP.getUrl());
        closeThisView();
    }

    @Click(R.id.fragment_drawer_blog_text_view)
    protected void onClickBlogTextView() {
        loadPage(PageUrl.SHUKA_BLOG.getUrl());
        closeThisView();
    }

    @Click(R.id.fragment_drawer_and_text_view)
    protected void onClickAndTextView() {
        loadPage(PageUrl.AND_TOP.getUrl());
        closeThisView();
    }

    @Click(R.id.fragment_drawer_epub_viewer_text_view)
    protected void onClickEpubViewerTextView() {
        if (getActivity() == null) {
            return;
        }
        String initialDirectory =
                Environment.getExternalStorageDirectory().getPath() + "/" +
                        Environment.DIRECTORY_DOWNLOADS;
        new FilePickerDialogFragment.Builder(getActivity())
                .rootDirectory(initialDirectory)
                .initialDirectory(initialDirectory)
                .previous("..")
                .cancelLabel("キャンセル")
                .listener(new OnFileSelectedListener() {
                    @Override
                    public void onFileSelected(final String path) {
                        LogUtil.d("extension:" + StringUtil.getFileNameExtension(path));
                        if (StringUtil.getFileNameExtension(path).equals("epub")) {
                            new CommonDialogFragment.Builder(getActivity())
                                    .message(path + "\n\n" +
                                            "外部アプリを使用して開きますか？")
                                    .type(CommonDialogFragment.CommonDialog.DialogType.DOUBLE_BUTTONS)
                                    .positive("外部アプリを使用して開く(推奨)", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            File file = new File(path);
                                            Uri uri = FileProvider.getUriForFile(
                                                    getActivity(),
                                                    BuildConfig.APPLICATION_ID + ".fileprovider",
                                                    file);
                                            Intent intent = new Intent(Intent.ACTION_VIEW);
                                            intent.setData(uri);
                                            intent.putExtra(Intent.EXTRA_TEXT, file);
                                            intent.addFlags(
                                                    Intent.FLAG_ACTIVITY_NEW_TASK |
                                                            Intent.FLAG_ACTIVITY_NO_HISTORY |
                                                            Intent.FLAG_GRANT_WRITE_URI_PERMISSION |
                                                            Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                            startActivity(intent);
                                        }
                                    })
                                    .negative("内部アプリを使用して開く", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            String fileUriStr = "file://" + path;
                                            LogUtil.d("fileUriStr:" + fileUriStr);
                                            Config config = new Config()
                                                    .setDirection(Config.Direction.HORIZONTAL);
                                            FolioReader.get()
                                                    .setConfig(config, true)
                                                    .openBook(path);
                                        }
                                    })
                                    .show();
                            /*
                            if (IS_SHOW_EPUB_INNER) {
                                String fileUriStr = "file://" + path;
                                LogUtil.d("fileUriStr:" + fileUriStr);
                                Config config = new Config()
                                        .setDirection(Config.Direction.HORIZONTAL);
                                FolioReader.get()
                                        .setConfig(config, true)
                                        .openBook(path);
                            } else {
                                File file = new File(path);
                                Uri uri = FileProvider.getUriForFile(
                                        getActivity(),
                                        BuildConfig.APPLICATION_ID + ".fileprovider",
                                        file);
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(uri);
                                intent.putExtra(Intent.EXTRA_TEXT, file);
                                intent.addFlags(
                                        Intent.FLAG_ACTIVITY_NEW_TASK |
                                                Intent.FLAG_ACTIVITY_NO_HISTORY |
                                                Intent.FLAG_GRANT_WRITE_URI_PERMISSION |
                                                Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                startActivity(intent);
                            }
                            /**/
                        }
                    }

                    @Override
                    public void onFileSelectCanceled() {
                        Toast.makeText(getActivity(), "キャンセル", Toast.LENGTH_LONG).show();
                    }
                })
                .extensionFilter("epub")
                .show();

    }

    private void loadPage(String url) {
        if (getMainActivity() != null && getMainActivity().getNowFragment() instanceof MainWebViewFragment) {
            ((MainWebViewFragment) getMainActivity().getNowFragment()).loadPage(url);
        }
    }

    private void closeThisView() {
        if (getMainActivity() != null) {
            getMainActivity().onBackPressed();
        }
    }

    private MainActivity getMainActivity() {
        Activity activity = getActivity();
        if (activity != null && activity instanceof MainActivity) {
            return (MainActivity) activity;
        }

        return null;
    }
}
