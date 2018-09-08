package moganbo.github.io.shukaviewer.constants;

import android.net.Uri;

import moganbo.github.io.shukaviewer.R;

public enum TransitionPage {
    // TODO: 2018/09/08 足りない画像
    TOP(0, 0, PageUrl.SHUKA_LAND_TOP),
    NEWS(1, R.drawable.notification, PageUrl.SHUKA_LAND_NEWS),
    SCHEDULES(2, R.drawable.schedule, PageUrl.SHUKA_LAND_SCHEDULES),
    MUSEUM(3, R.drawable.photo, PageUrl.SHUKA_LAND_MUSEUM),
    BOOKSTAND(4, R.drawable.bookstand, PageUrl.SHUKA_LAND_BOOK_STAND),
    THEATERS(5, R.drawable.movie, PageUrl.SHUKA_LAND_THEATERS),
    LETTERS(6, 0, PageUrl.SHUKA_LAND_LETTERS),
    COIN_CENTER(7, 0, PageUrl.SHUKA_LAND_COIN_CENTER),
    BIOGRAPHY(8, 0, PageUrl.SHUKA_LAND_BIOGRAPHY),
    HOW_TO(9, 0, PageUrl.SHUKA_LAND_HOW_TO),
    MY_ROOM(10, 0, PageUrl.SHULA_LAND_MY_ROOM),
    SETTING(11, 0, PageUrl.SHULA_LAND_SETTING),

    BLOG(50, 0, PageUrl.SHUKA_BLOG),;

    private int pageId;
    private int resourceId;
    private PageUrl pageUrl;

    TransitionPage(int pageId, int resourceId, PageUrl pageUrl) {
        this.pageId = pageId;
        this.resourceId = resourceId;
        this.pageUrl = pageUrl;
    }

    public int getPageId() {
        return pageId;
    }

    public int getResourceId() {
        return resourceId;
    }

    public PageUrl getPageUrl() {
        return pageUrl;
    }
}
