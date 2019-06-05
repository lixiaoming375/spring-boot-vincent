package com.vincent.teng.projectaliyunoss.dto;


public class FileOSSUploadResultDTO {

    private String fileUrl;

    private String fileKey;

    private String bucketName;

    private String uploadId;

    private boolean uploadFlag;

    private String resultMsg;

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
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

    public String getUploadId() {
        return uploadId;
    }

    public void setUploadId(String uploadId) {
        this.uploadId = uploadId;
    }

    public boolean isUploadFlag() {
        return uploadFlag;
    }

    public void setUploadFlag(boolean uploadFlag) {
        this.uploadFlag = uploadFlag;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    @Override
    public String toString() {
        return "FileOSSUploadResultDTO{" +
                "fileUrl='" + fileUrl + '\'' +
                ", fileKey='" + fileKey + '\'' +
                ", bucketName='" + bucketName + '\'' +
                ", uploadId='" + uploadId + '\'' +
                ", uploadFlag=" + uploadFlag +
                ", resultMsg='" + resultMsg + '\'' +
                '}';
    }
}
