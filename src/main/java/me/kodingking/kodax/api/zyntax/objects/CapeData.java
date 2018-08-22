package me.kodingking.kodax.api.zyntax.objects;

public class CapeData {
    private String url;
    private boolean hasCape;

    public CapeData(String url, boolean hasCape) {
        this.url = url;
        this.hasCape = hasCape;
    }

    public String getUrl() {
        return url;
    }

    public boolean hasCape() {
        return hasCape;
    }
}
