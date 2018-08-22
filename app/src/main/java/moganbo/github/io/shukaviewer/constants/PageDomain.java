package moganbo.github.io.shukaviewer.constants;

import moganbo.github.io.shukaviewer.utils.LogUtil;

public enum PageDomain {
    NONE(""),
    SHUKA_LAND("shuka-land.jp", true),
    AMEBLO("ameblo.jp", true),;

    String domain;

    boolean isSPA;

    PageDomain(String domain) {
        this.domain = domain;
        this.isSPA = false;
    }

    PageDomain(String domain, boolean isSPA) {
        this.domain = domain;
        this.isSPA = isSPA;
    }

    public String getDomain() {
        return domain;
    }

    public boolean isSPA() {
        return isSPA;
    }

    public static PageDomain getPageDomain(String url) {
        // LogUtil.d("url:" + url);
        PageDomain[] values = PageDomain.values();
        for (PageDomain value : values) {
            if (!value.domain.equals("") && url.contains(value.domain)) {
                return value;
            }
        }
        return NONE;
    }
}
