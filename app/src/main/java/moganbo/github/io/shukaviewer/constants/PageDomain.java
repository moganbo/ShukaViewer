package moganbo.github.io.shukaviewer.constants;

public enum PageDomain {
    SHUKA_LAND("shuka-land.jp") ,
    ;

    String domain;

    PageDomain(String domain) {
        this.domain = domain;
    }

    public String getUrl() {
        return domain;
    }
}
