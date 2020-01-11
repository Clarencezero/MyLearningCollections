package basicjava.beans;

public class VideoBean {
    private long duration;
    private String durationFormat;
    private long size;
    private String format;
    private int height;
    private int width;

    public VideoBean() {
    }

    public VideoBean(long duration, String durationFormat, long size, String format, int height, int width) {
        this.duration = duration;
        this.durationFormat = durationFormat;
        this.size = size;
        this.format = format;
        this.height = height;
        this.width = width;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getDurationFormat() {
        return durationFormat;
    }

    public void setDurationFormat(String durationFormat) {
        this.durationFormat = durationFormat;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public String toString() {
        return "VideoBean{" +
                "duration=" + duration +
                ", durationFormat='" + durationFormat + '\'' +
                ", size=" + size +
                ", format='" + format + '\'' +
                ", height=" + height +
                ", width=" + width +
                '}';
    }
}
