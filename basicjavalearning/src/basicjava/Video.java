package basicjava;

import basicjava.beans.VideoBean;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
public class Video {
    public static final String VIDEO_FILE_PATH = "E:\\学习视频\\大数据 Flink大数据项目实战\\01.Flink依赖管理.mp4";
    public static final String VIDEO_PATH = "E:\\学习视频\\大数据 Flink大数据项目实战\\";

    public static void main(String[] args) {
        String videosTime = getVideosTime(VIDEO_PATH);
        System.out.println("GET TIME: " + videosTime);
    }


    public static String getVideosTime(String path) {
        File source = null;
        List<File> files = null;
        if (!(source = new File(path)).exists()) {
            throw new NullPointerException("File or directory is not exist!");
        }

        // 1. get files
        if (source.isDirectory()) {
            files = Arrays.stream(source.listFiles()).filter(x -> {
                return !x.isDirectory();
            }).collect(Collectors.toList());
        } else {
            files.add(source);
        }

        // 2. for get time
        long duration = 0l;
        VideoBean videoBean = null;
        for (File file : files) {
            videoBean = getVideoInfo(file.getPath());
            duration += videoBean.getDuration();
        }


        return duration / 3600000 + " hours";

    }

    /**
     * get video infomation
     *
     * @param path
     * @return
     */
    public static VideoBean getVideoInfo(String path) {
        File source = null;
        if ((source = new File(path)).isDirectory()) {
            throw new RuntimeException("Path is a directory");
        }
        Encoder encoder = new Encoder();
        FileChannel fc = null;
        try {
            MultimediaInfo m = null;
            try {
                // file contanis error codes will throw Exception
                m = encoder.getInfo(source);
            } catch (Exception e) {
                System.out.println("Current File name: " + source.getName());
                e.printStackTrace();
            }
            long ls = m.getDuration();
            String timeFormat = ls / 60000 + "分";
            FileInputStream fis = new FileInputStream(source);
            fc = fis.getChannel();
            BigDecimal fileSize = new BigDecimal(fc.size());
            String fileSizeFormat = fileSize.divide(new BigDecimal(1048576), 2, RoundingMode.HALF_UP) + "MB";
            int height = m.getVideo().getSize().getHeight();
            int width = m.getVideo().getSize().getWidth();

            VideoBean videoBean = new VideoBean(ls, timeFormat, fc.size(), fileSizeFormat, height, width);

            return videoBean;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != fc) {
                try {
                    fc.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


}
