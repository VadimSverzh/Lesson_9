public class WebsiteInfo {
private String url;
private String tag;
private String tagAttribute;

    WebsiteInfo (String url, String tag, String tagAttribute) {
        this.url = url;
        this.tag = tag;
        this.tagAttribute = tagAttribute;
    }

    public String getUrl() {
        return url;
    }

    public String getTag() {
        return tag;
    }

    public String getTagAttribute() {
        return tagAttribute;
    }

}
