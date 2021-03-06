package moganbo.github.io.shukaviewer.constants;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

import moganbo.github.io.shukaviewer.fragment.BaseFragment;
import moganbo.github.io.shukaviewer.fragment.CommonDialogFragment;
import moganbo.github.io.shukaviewer.utils.RuntimePermissionUtil;
import moganbo.github.io.shukaviewer.utils.SharedPrefsUtils;

public enum PageUrl {
    NONE(""),
    GOOGLE("https://www.google.co.jp/"),
    SHUKA_LAND_TOP("https://shuka-land.jp/"),
    SHUKA_LAND_SIGN_UP("https://shuka-land.jp/signup"),
    SHULA_LAND_MY_ROOM("https://shuka-land.jp/myroom"),
    SHULA_LAND_SETTING("https://shuka-land.jp/setting"),
    SHUKA_LAND_NEWS("https://shuka-land.jp/news"),
    SHUKA_LAND_SCHEDULES("https://shuka-land.jp/schedules"),
    SHUKA_LAND_MUSEUM("https://shuka-land.jp/museum"),
    SHUKA_LAND_BOOK_STAND("https://shuka-land.jp/bookstand"),
    SHUKA_LAND_THEATERS("https://shuka-land.jp/theaters"),
    SHUKA_LAND_LETTERS("https://shuka-land.jp/letters"),
    SHUKA_LAND_COIN_CENTER("https://shuka-land.jp/coincenter"),
    SHUKA_LAND_BIOGRAPHY("https://shuka-land.jp/biography"),
    SHUKA_LAND_HOW_TO("https://shuka-land.jp/howto"),
    SHUKA_BLOG("https://ameblo.jp/shuka-saito/"),

    AND_TOP("https://kobayashiaika.jp/"),;

    String url;

    PageUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public static PageUrl getPageUrl(String url) {
        PageUrl[] pageUrls = values();
        for (PageUrl pageUrl : pageUrls) {
            if (url.equals(pageUrl.url)) {
                return pageUrl;
            }
        }
        return NONE;
    }
    public void onLoadStarted(BaseFragment fragment) {
        switch (this) {
            case GOOGLE:
                break;
            case SHUKA_LAND_TOP:
            case SHUKA_LAND_SIGN_UP:
            case SHULA_LAND_MY_ROOM:
            case SHUKA_LAND_NEWS:
            case SHUKA_LAND_SCHEDULES:
            case SHUKA_LAND_MUSEUM:
            case SHUKA_LAND_BOOK_STAND:
            case SHUKA_LAND_THEATERS:
            case SHUKA_LAND_LETTERS:
            case SHUKA_LAND_COIN_CENTER:
            case SHUKA_LAND_BIOGRAPHY:
            case SHUKA_LAND_HOW_TO:
            case SHULA_LAND_SETTING:
                onLoadStartedShukaLand(fragment);
                break;
            case SHUKA_BLOG:
                onLoadStartedShukaBlog(fragment);
                break;
            case AND_TOP:
                onLoadStartedAnd(fragment);
                break;
        }
    }

    public void onLoadFinished(BaseFragment fragment) {
        switch (this) {
            case GOOGLE:
                break;
            case SHUKA_LAND_TOP:
                break;
            case SHUKA_LAND_SIGN_UP:
                onLoadFinishedSignIn(fragment);
                break;
            case SHULA_LAND_MY_ROOM:
                break;
            case SHUKA_LAND_NEWS:
                break;
            case SHUKA_LAND_SCHEDULES:
                break;
            case SHUKA_LAND_MUSEUM:
                onLoadFinishedMuseum(fragment);
                break;
            case SHUKA_LAND_BOOK_STAND:
                onLoadFinishedBookStand(fragment);
                break;
            case SHUKA_LAND_THEATERS:
                break;
            case SHUKA_LAND_LETTERS:
                break;
            case SHUKA_LAND_COIN_CENTER:
                onLoadFinishCoinCenter(fragment);
                break;
            case SHUKA_LAND_BIOGRAPHY:
                break;
            case SHUKA_LAND_HOW_TO:
                break;
            case SHULA_LAND_SETTING:
                break;
            case SHUKA_BLOG:
                break;
            case AND_TOP:
                break;
        }
    }

    private void onLoadStartedShukaLand(final BaseFragment fragment){
        fragment.getMainActivity().getFooterMenu().updateForShukaLand();
    }

    private void onLoadStartedShukaBlog(final BaseFragment fragment){
        fragment.getMainActivity().getFooterMenu().updateForShukaBlog();
    }

    private void onLoadStartedAnd(final BaseFragment fragment){
        fragment.getMainActivity().getFooterMenu().updateForAnd();
    }

    private void onLoadFinishedSignIn(final BaseFragment fragment) {
        if (SharedPrefsUtils.getBooleanPreference(
                SharedPrefsConstants.Flag.IS_SHOWED_SIGN_IN_DIALOG, false)){
            return;
        }
        SharedPrefsUtils.setBooleanPreference(
                SharedPrefsConstants.Flag.IS_SHOWED_SIGN_IN_DIALOG, true);
        if (fragment != null && fragment.getActivity() != null) {
            new CommonDialogFragment.Builder(fragment.getActivity())
                    .message("入園手続きの方はブラウザにて手続きをお願いいたします。\n" +
                            "ブラウザを起動しますか？\n" +
                            "(再入場の方はNOをタップしてください。)")
                    .type(CommonDialogFragment.CommonDialog.DialogType.DOUBLE_BUTTONS)
                    .positive("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Uri uri = Uri.parse(PageUrl.SHUKA_LAND_SIGN_UP.getUrl());
                            Intent i = new Intent(Intent.ACTION_VIEW, uri);
                            fragment.getActivity().startActivity(i);
                        }
                    })
                    .negative("NO", null)
                    .show();
        }
    }

    private void onLoadFinishedMuseum(final BaseFragment fragment){
        if (SharedPrefsUtils.getBooleanPreference(
                SharedPrefsConstants.Flag.IS_SHOWED_MUSEUM_DIALOG, false)){
            return;
        }
        SharedPrefsUtils.setBooleanPreference(
                SharedPrefsConstants.Flag.IS_SHOWED_MUSEUM_DIALOG, true);
        checkWriteExternalStoragePermissions(fragment, this);
    }

    private void onLoadFinishedBookStand(final BaseFragment fragment){
        if (SharedPrefsUtils.getBooleanPreference(
                SharedPrefsConstants.Flag.IS_SHOWED_BOOK_STAND_DIALOG, false)){
            return;
        }
        SharedPrefsUtils.setBooleanPreference(
                SharedPrefsConstants.Flag.IS_SHOWED_BOOK_STAND_DIALOG, true);
        checkWriteExternalStoragePermissions(fragment, this);
    }

    private void onLoadFinishCoinCenter(final BaseFragment fragment) {
        if (fragment != null && fragment.getActivity() != null) {
            new CommonDialogFragment.Builder(fragment.getActivity())
                    .message("コインの購入はブラウザにてお願いいたします。")
                    .type(CommonDialogFragment.CommonDialog.DialogType.SINGLE_BUTTON)
                    .positive("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Uri uri = Uri.parse(PageUrl.SHUKA_LAND_COIN_CENTER.getUrl());
                            Intent i = new Intent(Intent.ACTION_VIEW, uri);
                            fragment.getActivity().startActivity(i);
                        }
                    }).show();
        }
    }

    private void checkWriteExternalStoragePermissions(final BaseFragment fragment, PageUrl pageUrl){
        if (fragment == null || fragment.getActivity() == null){
            return;
        }
        if (RuntimePermissionUtil.hasSelfPermission(fragment.getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            return;
        }
        int requestCode= 0;
        String contentName = "";
        if (pageUrl.equals(SHUKA_LAND_BOOK_STAND)){
            requestCode = AppConstants.PermissonRequestCode.FROM_BOOK_STAND;
            contentName = "しゅか日和";
        }else if (pageUrl.equals(SHUKA_LAND_MUSEUM)){
            requestCode = AppConstants.PermissonRequestCode.FROM_MUSEUM;
            contentName = "画像";
        }
        final int requestCodeFinal= requestCode;
        String contentNameFinal = contentName;
        new CommonDialogFragment.Builder(fragment.getActivity())
                .message(contentNameFinal + "のダウンロードをするために\n" +
                        "権限の許可をお願いします。")
                .type(CommonDialogFragment.CommonDialog.DialogType.SINGLE_BUTTON)
                .positive("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RuntimePermissionUtil.requestPermission(
                                fragment.getActivity(),
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                requestCodeFinal);
                    }
                }).show();
    }
}
