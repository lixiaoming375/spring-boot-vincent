package com.vincent.teng.projectaliyunoss.service.aliyun;


import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.utils.IOUtils;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PartETag;
import com.vincent.teng.projectaliyunoss.config.AliyunFileOSSConfig;
import com.vincent.teng.projectaliyunoss.dto.FileOSSDownLoadDTO;
import com.vincent.teng.projectaliyunoss.dto.FileOSSDownLoadResultDTO;
import com.vincent.teng.projectaliyunoss.dto.FileOSSUploadDTO;
import com.vincent.teng.projectaliyunoss.dto.FileOSSUploadResultDTO;
import com.vincent.teng.projectaliyunoss.util.AliyunFileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author yuantongqin
 * 2019/4/4
 */
@Component
@ConditionalOnClass(OSSClient.class)
@EnableConfigurationProperties(AliyunFileOSSConfig.class)
public class AliyunFileOSSClientService {

    private static Logger LOGGER = LoggerFactory.getLogger(AliyunFileOSSClientService.class);

    @Autowired
    private AliyunFileOSSConfig ossConfig;
    private String key = "fileName";

    public FileOSSUploadResultDTO upload(FileOSSUploadDTO fileObject) {
        FileOSSUploadResultDTO resultObject = new FileOSSUploadResultDTO();
        if (Objects.isNull(fileObject)) {
            resultObject.setUploadFlag(false);
            resultObject.setResultMsg("上传参数为空");
            return resultObject;
        }

        boolean uploadFlag = false;

        //partETags是PartETag的集合。PartETag由分片的ETag和分片号组成。
        List<PartETag> partETags = Collections.synchronizedList(new ArrayList<>());
        //默认开启的最大线程数目
        int localMaxThreadAccount = 5;

        int maxFileLength = ossConfig.getMaxFileLength();
        int partSize = ossConfig.getPartSize();
        int maxThreadAccount = ossConfig.getMaxThreadAccount();

        if (maxThreadAccount > 0) {
            localMaxThreadAccount = maxThreadAccount;
        }
        byte[] fileBytes = fileObject.getFileBytes();
        String fileKey = AliyunFileUtil.buildFileKey(ossConfig.getBucketName(), fileObject.getFileType());
        fileObject.setFileType(fileKey);

        if (StringUtils.isEmpty(fileKey)) {
            LOGGER.error("AliyunFileOSSClientService upload fileKey not exist...");
//            throw FileOSSException.UPLOAD_FILE_IS_NOT_NULL.get();
        }

        String resultMsg = "成功";
        String bucketName = ossConfig.getBucketName();
        OSSClient ossClient = null;

        try {
            ClientConfiguration configuration = new ClientConfiguration();
            configuration.setIdleConnectionTime(1000);
            ossClient = new OSSClient(ossConfig.getEndpoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret(), configuration);
            String uploadId = AliyunFileUtil.claimUploadId(bucketName, fileKey, ossClient);
            int fileLength = fileBytes.length;

            if (fileLength > maxFileLength) {
                resultObject.setUploadFlag(false);
                resultMsg = "上传文件内容不得大于:" + maxFileLength;
                resultObject.setResultMsg(resultMsg);
                return resultObject;
            }
            // 计算分片数
            int partCount = (fileLength / partSize);
            if (fileLength % partSize != 0) {
                partCount++;
            }
            LOGGER.info("AliyunFileOSSClientService upload partCount...fileKey[{}], bucketName[{}], partCount[{}]",
                        fileKey, bucketName, partCount);
            //如果文件切割的份数小于最大线程数，则最大线程数目等于文件切割份数
            if (partCount <= localMaxThreadAccount) {
                localMaxThreadAccount = partCount;
            }

            ExecutorService executorService =
                    Executors.newFixedThreadPool(localMaxThreadAccount);

            for (int i = 0; i < partCount; i++) {
                long startPos = i * partSize;
                long curPartSize = (i + 1 == partCount) ? (fileLength - startPos) : partSize;

                LOGGER.info("AliyunFileOSSClientService upload execute start... fileKey[{}], partCount[{}]", fileKey, partCount);
                executorService.execute(new AliyunFileOSSUploadTask(ossConfig.getBucketName(), fileKey, ossClient,
                                                                    fileBytes, startPos, curPartSize,
                                                                    i+1, uploadId, partETags));
            }

            executorService.shutdown();

            // 检测当前executorservice是否关闭，如果关闭返回true
            while (!executorService.isTerminated()) {
                LOGGER.warn("AliyunFileOSSClientService upload executorService not isTerminated...fileKey[{}]", fileKey);
                try {
                    // 如果没有关闭则等待五秒后关闭
                    executorService.awaitTermination(1, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    LOGGER.error("AliyunFileOSSClientService upload executorService awaitTermination exception...fileKey[{}]", fileKey);
                }
            }
            // 等所有任务都执行完成后判断执行的分片数量和之前的分片数量是否一直
            // 一定要等所有的线程都完成了才能进行这个判断,主要是为了满足所有切片都已经上传了
            if (partETags.size() != partCount) {
                LOGGER.error("AliyunFileOSSClientService upload part fail... fileKey[{}], partETagsSize[{}], partCount[{}]",
                             fileKey, partETags.size(), partCount);
//                throw FileOSSException.UPLOAD_FILE_PART_FAIL.get();
            }
            LOGGER.warn("AliyunFileOSSClientService upload successful... fileKey[{}], partETagsSize[{}], partCount[{}]",
                        fileKey, partETags.size(), partCount);

            if (LOGGER.isDebugEnabled()) {
                //日志级别为 DEBUG 则调用列出所有上传分块信息。。。
                AliyunFileUtil.listAllParts(uploadId, bucketName, fileKey, ossClient);
            }

            AliyunFileUtil.completeMultipartUpload(uploadId, fileKey, bucketName, ossClient, partETags);

            //获取过期时间。。。
            Date expiration = null;
            if(fileObject.getExpireTime() == null){
                Calendar calendar = Calendar.getInstance();
                //推迟10年
                calendar.add(Calendar.YEAR, 10);
                expiration = calendar.getTime();
            }else{
                expiration = fileObject.getExpireTime();
            }
            URL fileUrl = ossClient.generatePresignedUrl(bucketName, fileKey, expiration);
            LOGGER.info("url路径:"+fileUrl.toString());
            String reFileUrl = AliyunFileUtil.rebuildUrl(fileUrl);
            LOGGER.info("AliyunFileOSSClientService upload fileKey[{}] rebuildUrl[{}]", fileKey, reFileUrl);

            resultObject.setFileKey(fileKey);
            resultObject.setBucketName(bucketName);
            resultObject.setFileUrl(reFileUrl);
            resultObject.setUploadId(uploadId);

            uploadFlag = true;
        } catch (OSSException oe) {
            String errorMessage = oe.getErrorMessage();
            String errorCode = oe.getErrorCode();
            resultMsg = "【上传】" + errorMessage + ":" + errorCode;
        } catch (Exception e) {
            resultMsg = "【上传】上传失败";
            LOGGER.error("上传失败[{}]",e);
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }

        resultObject.setUploadFlag(uploadFlag);
        resultObject.setResultMsg(resultMsg);
        return resultObject;
    }


    public FileOSSDownLoadResultDTO download(FileOSSDownLoadDTO downLoadObject) {

        String buckName = downLoadObject.getBuckName();
        String fileKey = downLoadObject.getFileKey();

        // 创建OSSClient实例
        OSSClient ossClient = new OSSClient(ossConfig.getEndpoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());

        OSSObject fileObject = ossClient.getObject(buckName, fileKey);

        if (Objects.isNull(fileObject)) {
            LOGGER.info("AliyunFileOSSClientService download fileObject is null... buckName[{}] fileKey[{}]", buckName, fileKey);
//            throw FileOSSException.DOWNLOAD_OBJECT_FAIL.get();
        }
        FileOSSDownLoadResultDTO resultObject = new FileOSSDownLoadResultDTO();
        InputStream objectContent = fileObject.getObjectContent();

        if (Objects.isNull(objectContent)) {
            LOGGER.info("AliyunFileOSSClientService download objectContent is null... buckName[{}] fileKey[{}]", buckName, fileKey);
//            throw FileOSSException.DOWNLOAD_FAIL_IS_NULL.get();
        }
        byte[] fileBytes = null;
        try {
            fileBytes = IOUtils.readStreamAsByteArray(objectContent);
        } catch (IOException e) {
            LOGGER.error("AliyunFileOSSClientService download toByteArray exception...buckName[{}] fileKey[{}]", buckName, fileKey);
//            throw FileOSSException.DOWNLOAD_TRANSFORM_FAIL.get();
        }
        resultObject.setFileBytes(fileBytes);

        return resultObject;
    }


}
