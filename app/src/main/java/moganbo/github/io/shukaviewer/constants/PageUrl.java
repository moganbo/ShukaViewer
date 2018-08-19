package moganbo.github.io.shukaviewer.constants;

public enum PageUrl {
    GOOGLE("https://www.google.co.jp/"),
    SHUKA_LAND_TOP("https://shuka-land.jp/"),
    SHUKA_LAND_SIGN_UP("https://shuka-land.jp/signup"),
    SHULA_LAND_MY_ROOM("https://shuka-land.jp/myroom"),
    SHUKA_LAND_NEWS("https://shuka-land.jp/news"),
    SHUKA_LAND_SCHEDULES("https://shuka-land.jp/schedules"),
    SHUKA_LAND_MUSEUM("https://shuka-land.jp/museum"),
    SHUKA_LAND_BOOK_STAND("https://shuka-land.jp/bookstand"),
    SHUKA_LAND_THEATERS("https://shuka-land.jp/theaters"),
    SHUKA_LAND_LETTERS("https://shuka-land.jp/letters"),
    SHUKA_LAND_COIN_CENTER("https://shuka-land.jp/coincenter"),
    SHUKA_LAND_BIOGRAPHY("https://shuka-land.jp/biography"),
    SHUKA_LAND_HOW_TO("https://shuka-land.jp/howto");

    String url;

    PageUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
