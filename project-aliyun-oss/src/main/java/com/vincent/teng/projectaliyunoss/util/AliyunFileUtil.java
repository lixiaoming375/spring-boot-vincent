package com.vincent.teng.projectaliyunoss.util;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

/**
 * @author yuantongqin
 * 2019/4/4
 */
public class AliyunFileUtil {

    private static Logger LOGGER = LoggerFactory.getLogger(AliyunFileUtil.class);
    /**
     * 用InitiateMultipartUploadRequest来指定上传文件的名字和所属存储空间（Bucket）<br/>
     * 在InitiateMultipartUploadRequest中，也可以设置ObjectMeta，但是不必指定其中的ContentLength
     * <br/>
     *
     * @return 返回UploadId，它是区分分片上传事件的唯一标识
     */
    public static String claimUploadId(String bucketName, String key, OSSClient client) {
        InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest(bucketName, key);
        InitiateMultipartUploadResult result = client.initiateMultipartUpload(request);
        return result.getUploadId();
    }

    /**
     * 列出所有分块清单
     */
    public static void listAllParts(String uploadId, String bucketName, String fileKey, OSSClient client) {

        LOGGER.info("AliyunOSSUtil listAllParts start...");
        ListPartsRequest listPartsRequest = new ListPartsRequest(bucketName, fileKey, uploadId);
        PartListing partListing = client.listParts(listPartsRequest);

        int partCount = partListing.getParts().size();
        for (int i = 0; i < partCount; i++) {
            PartSummary partSummary = partListing.getParts().get(i);
            LOGGER.info("AliyunOSSUtil listAllParts partSummary...PartNumber[{}], ETag[{}]", partSummary.getPartNumber(), partSummary.getETag());
        }
    }

    /**
     * 上面代码中的 partETags 就是进行分片上传中保存的partETag的列表，<br/>
     * OSS收到用户提交的Part列表后，会逐一验证每个数据Part的有效性。<br/>
     * 当所有的数据Part验证通过后，OSS会将这些part组合成一个完整的文件
     *
     * @param uploadId
     */
    public static void completeMultipartUpload(String uploadId, String key, String bucketName, OSSClient client,
                                               List<PartETag> partETags) {
        Collections.sort(partETags, new Comparator<PartETag>() {
            @Override
            public int compare(PartETag p1, PartETag p2) {
                return p1.getPartNumber() - p2.getPartNumber();
            }
        });

        CompleteMultipartUploadRequest completeMultipartUploadRequest = new CompleteMultipartUploadRequest(bucketName,
                                                                                                           key, uploadId, partETags);
        client.completeMultipartUpload(completeMultipartUploadRequest);
    }


    public static String buildFileKey(String bucketName, String fileType) {
        UUID uuid = UUID.randomUUID();
        // 得到对象产生的ID
        String uuidStr = uuid.toString();
        uuidStr = uuidStr.replaceAll("-", "");
        //对应文件的名字
        return bucketName + "_" + uuidStr + "." + fileType;
    }

    public static String rebuildUrl(URL url) {
        String urlVal = url.toString().substring(0, url.toString().indexOf("?"));
        //modify by gexiaobing 将上传后的url由内部改成外部
        urlVal = urlVal.replace("http", "https");
        urlVal = urlVal.replace("-internal", "");

        return urlVal;
    }

    public static String getFileType(String originalFilename){
        String fileType = "default";
        if(!StringUtils.isEmpty(originalFilename)){
            int index = originalFilename.lastIndexOf(".");
            if(index > 0){
                fileType = originalFilename.substring(index+1);
            }
        }
        return fileType;
    }

}
