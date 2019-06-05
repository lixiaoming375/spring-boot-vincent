package com.vincent.teng.projectaliyunoss.dto;

import java.util.Date;

/**
 * fileBytes,fileType必传
 */
public class FileOSSUploadDTO {

    /**
     * 文件byte大小
     */
    private byte[] fileBytes;

    private String fileKey;
    /**
     * png,jpg,
     * 文件的后缀
     */
    private String fileType;

    private String bucketName;

    private Date expireTime;

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public byte[] getFileBytes() {
        return fileBytes;
    }

    public void setFileBytes(byte[] fileBytes) {
        this.fileBytes = fileBytes;
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }
}
