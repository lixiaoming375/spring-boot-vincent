package com.vincent.teng.projectaliyunoss.service.aliyun;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PartETag;
import com.aliyun.oss.model.UploadPartRequest;
import com.aliyun.oss.model.UploadPartResult;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author yuantongqin
 * 2019/4/4
 */
public class AliyunFileOSSUploadTask implements Runnable{

    private byte[] bytes;
    private long startPos;
    private String bucketName;
    private long partSize;
    private int partNumber;
    private String uploadId;
    private String key;
    private OSSClient client;
    private List<PartETag> partETags;

    public AliyunFileOSSUploadTask(String bucketName, String key, OSSClient client,
                                   byte[] bytes, long startPos, long partSize, int partNumber,
                                   String uploadId,List<PartETag> partETags) {
        this.bucketName = bucketName;
        this.key = key;
        this.client = client;
        this.bytes = bytes;
        this.startPos = startPos;
        this.partSize = partSize;
        this.partNumber = partNumber;
        this.uploadId = uploadId;
        this.partETags = partETags;
    }

    @Override
    public void run() {
        InputStream instream = null;
        try {

            instream = new ByteArrayInputStream(bytes);
            instream.skip(this.startPos);

            UploadPartRequest uploadPartRequest = new UploadPartRequest();
            uploadPartRequest.setBucketName(bucketName);
            uploadPartRequest.setKey(key);
            uploadPartRequest.setUploadId(this.uploadId);
            uploadPartRequest.setInputStream(instream);
            uploadPartRequest.setPartSize(this.partSize);
            uploadPartRequest.setPartNumber(this.partNumber);

            UploadPartResult uploadPartResult = client.uploadPart(uploadPartRequest);
            System.out.println("Part#" + this.partNumber + " done\n");
            synchronized (partETags) {
                partETags.add(uploadPartResult.getPartETag());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (instream != null) {
                try {
                    instream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
