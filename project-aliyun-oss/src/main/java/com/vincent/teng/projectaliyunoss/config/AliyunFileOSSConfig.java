package com.vincent.teng.projectaliyunoss.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author yuantongqin
 * 2019/4/4
 */
@ConfigurationProperties(prefix = "aliyun.oss")
public class AliyunFileOSSConfig {


    private String endpoint;

    private String accessKeyId;

    private String accessKeySecret;

    private String prefixUrl;

    private int maxThreadAccount;

    private int partSize;

    private int maxFileLength;

    private String bucketName;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getPrefixUrl() {
        return prefixUrl;
    }

    public void setPrefixUrl(String prefixUrl) {
        this.prefixUrl = prefixUrl;
    }

    public int getMaxThreadAccount() {
        return maxThreadAccount;
    }

    public void setMaxThreadAccount(int maxThreadAccount) {
        this.maxThreadAccount = maxThreadAccount;
    }

    public int getPartSize() {
        return partSize;
    }

    public void setPartSize(int partSize) {
        this.partSize = partSize;
    }

    public int getMaxFileLength() {
        return maxFileLength;
    }

    public void setMaxFileLength(int maxFileLength) {
        this.maxFileLength = maxFileLength;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
}
