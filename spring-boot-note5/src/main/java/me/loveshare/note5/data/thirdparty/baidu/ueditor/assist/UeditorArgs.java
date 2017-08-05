package me.loveshare.note5.data.thirdparty.baidu.ueditor.assist;

import java.io.InputStream;

/**
 * Created by Tony on 2016/12/12.
 */
public class UeditorArgs {

    private String action;
    private String callback;
    private InputStream is;
    private long fileSize;
    private String orgFileName;
    private String newFileName;
    private String diskName;
    private String base64String;

    private String delimiter;
    private String prefix;
    private int index = 0;
    private int count = 20;

    private String type;

    public UeditorArgs() {
        super();
    }

    public UeditorArgs(String action, String callback, InputStream is, long fileSize, String diskName, String orgFileName, String newFileName) {
        super();
        this.action = action;
        this.callback = callback;
        this.is = is;
        this.fileSize = fileSize;
        this.diskName = diskName;
        this.orgFileName = orgFileName;
        this.newFileName = newFileName;
        if(orgFileName != null && orgFileName.length() != 0) this.type = orgFileName.substring(orgFileName.lastIndexOf("."));
    }

    public UeditorArgs(String delimiter, String prefix, int index, int count) {
        this.delimiter = delimiter;
        this.prefix = prefix;
        this.index = index;
        this.count = count;
    }

    public UeditorArgs(String action, String callback) {
        this.action = action;
        this.callback = callback;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public InputStream getIs() {
        return is;
    }

    public void setIs(InputStream is) {
        this.is = is;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getOrgFileName() {
        return orgFileName;
    }

    public void setOrgFileName(String orgFileName) {
        this.orgFileName = orgFileName;
    }

    public String getNewFileName() {
        return newFileName;
    }

    public void setNewFileName(String newFileName) {
        this.newFileName = newFileName;
    }

    public String getDiskName() {
        return diskName;
    }

    public void setDiskName(String diskName) {
        this.diskName = diskName;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBase64String() {
        return base64String;
    }

    public void setBase64String(String base64String) {
        this.base64String = base64String;
    }
}
