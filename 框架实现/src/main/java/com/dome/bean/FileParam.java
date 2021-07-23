package com.dome.bean;

import java.io.InputStream;

/**
 * 封装文件参数
 *
 */
public class FileParam {
//    文件表单的字段名
    private String filedName;
//    表示要上传的文件的名称
    private String fileName;
//    表示要上传的文件的大小
    private long fileSize;
//    表示要上传文件的ContentType  表示要上传的文件的类型
    private String contentType;
//    表示要上传文件的输出流
    private InputStream inputStream;

    public FileParam(String filedName, String fileName, long fileSize, String contentType, InputStream inputStream) {
        this.filedName = filedName;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.contentType = contentType;
        this.inputStream = inputStream;
    }

    public String getFiledName() {
        return filedName;
    }

    public String getFileName() {
        return fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public String getContentType() {
        return contentType;
    }

    public InputStream getInputStream() {
        return inputStream;
    }
}
