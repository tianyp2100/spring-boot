package me.loveshare.note5.data.util;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.text.DecimalFormat;

/**
 * Created by Tony on 2017/6/9.
 * 磁盘操作
 */
public class DiskUtils {

    public static final String B_T = "B";
    /**KB unit size*/
    public static final long K = 1024;
    public static final String K_T = "K";
    /**MB unit size*/
    public static final long M = 1048576;
    public static final String M_T = "M";
    /**GB unit size*/
    public static final long G = 1073741824;
    public static final String G_T = "G";
    /**Format 2 bit decimals*/
    public static final DecimalFormat DF = new DecimalFormat("#.00");

//    public static void main(String[] args) {
//        printDiskInfo();
//        System.out.println(maxDiskName());
//        System.out.println(size(maxFreeSpace()));
//    }

    /**
     * 打印磁盘信息
     * */
    public static final void printDiskInfo(){
        loop(0);
    }
    /**
     * 获取某个磁盘最大剩余
     * */
    public static final long maxFreeSpace(){
        return (long) loop(1);
    }
    /**
     * 获取某个磁盘最大剩余的名称
     * */
    public static final String maxDiskName(){
        return (String) loop(2);
    }

    /**
     * if resFlag = 0, return null, but printDiskInfo;
     * if resFlag = 1, return maxFreeSpace;
     * if resFlag = 2, return maxDiskName;
     */
    public static final Object loop(int resFlag){
        FileSystemView fsv = getFileSystemView();
        File[] fs = listFiles(fsv);
        long maxFreeSpace = 0;
        String maxDiskName = null;
        StringBuilder su = new StringBuilder();
        for (int i = 0, len = fs.length; i < len; i++) {
            long freeSpace = fs[i].getFreeSpace();
            String diskName = fsv.getSystemDisplayName(fs[i]);
            if(freeSpace > maxFreeSpace){
                maxFreeSpace = freeSpace;
                maxDiskName = diskName;
            }
            su.append("磁盘：").append(diskName).append("，总大小：").append(size(fs[i].getTotalSpace())).append("，剩余：").append(size(freeSpace)).append("\n");
        }
        switch (resFlag){
            case 0: {
                System.out.println(su.toString());
                return null;
            }
            case 1: {
                return maxFreeSpace;
            }
            case 2:{
                return maxDiskName;
            }
            default:{
                return null;
            }
        }
    }

    public static final FileSystemView getFileSystemView(){
        // 当前文件系统类
        return FileSystemView.getFileSystemView();
    }

    public static final File[] listFiles(FileSystemView fsv){
        // 列出所有windows 磁盘
        return File.listRoots();
    }

    public static String size(long size) {
        String fileSizeString = "";
        if (size < K) {
            fileSizeString = concat((double) size, B_T);
        } else if (size < M) {
            fileSizeString = concat((double) size / K, K_T);
        } else if (size < G) {
            fileSizeString = concat((double) size / M, M_T);
        } else {
            fileSizeString = concat((double) size / G, G_T);
        }
        return fileSizeString;
    }

    public static final String format(double size){
        return DF.format((double) size);
    }
    public static final String concat(double size, String unitStr){
        return new StringBuilder(format(size)).append(unitStr).toString();
    }
}
