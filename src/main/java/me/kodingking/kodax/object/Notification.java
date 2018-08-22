package me.kodingking.kodax.object;

public class Notification {

    private String title;
    private String content;
    private Status currentStatus;
    private int xOffset;
    private long staticLength;

    public Notification(String title, String content) {
        this.title = title;
        this.content = content;

        this.currentStatus = Status.NOT_SHOWING;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Status getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(Status currentStatus) {
        this.currentStatus = currentStatus;
    }

    public int getXOffset() {
        return xOffset;
    }

    public void setXOffset(int xOffset) {
        this.xOffset = xOffset;
    }

    public long getStaticLength() {
        return staticLength;
    }

    public void setStaticLength(long staticLength) {
        this.staticLength = staticLength;
    }

    public enum Status {
        NOT_SHOWING,
        PULLING_OUT,
        STATIC,
        PULLING_IN
    }
}
