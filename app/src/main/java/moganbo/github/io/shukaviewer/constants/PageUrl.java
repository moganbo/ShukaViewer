package moganbo.github.io.shukaviewer.constants;

public enum PageUrl {
    GOOGLE("https://www.google.co.jp/"),
    SHUKA_LAND_TOP("https://shuka-land.jp/"),
    SHUKA_LAND_SIGN_UP("https://shuka-land.jp/signup"),;

    String url;

    PageUrl() {
        url = "";
    }

    PageUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
