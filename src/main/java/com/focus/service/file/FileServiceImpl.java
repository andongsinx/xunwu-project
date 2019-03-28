package com.focus.service.file;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSSClient;
import com.focus.base.ApiResponse;
import com.focus.base.FilePathHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

/**
 * @Description： 文件操作
 * @Author: shadow
 * @Date: create in 15:21 2019/1/5
 */
@Service
public class FileServiceImpl implements IFileService {


    @Value("${aliyun.oss.bucketName}")
    private String bucket;

    @Autowired
    private OSSClient ossClient;


    @Override
    public ApiResponse uploadFile(File file) {
        String fileName = generateFileKey(file.getName());
        if (StringUtils.isBlank(fileName)) {
            return ApiResponse.ofStatus(ApiResponse.Status.FILE_ERROR);
        }
        this.ossClient.putObject(bucket, fileName, file);
        String url = ossClient.generatePresignedUrl(bucket, fileName, expireTime(), HttpMethod.GET).toString();
        int retry = 0;
        while (StringUtils.isBlank(url) && retry < 3) {
            this.ossClient.putObject(bucket, generateFileKey(fileName), file).getResponse();
            url = ossClient.generatePresignedUrl(bucket, fileName, expireTime(), HttpMethod.GET).toString();
            retry++;
        }
        return ApiResponse.ofSuccess(url);
    }

    @Override
    public ApiResponse uploadFile(InputStream inputStream, String originalFileName) {
        String fileName = generateFileKey(originalFileName);
        if (StringUtils.isBlank(fileName)) {
            return ApiResponse.ofStatus(ApiResponse.Status.FILE_ERROR);
        }
        this.ossClient.putObject(bucket, fileName, inputStream);
        String url = ossClient.generatePresignedUrl(bucket, fileName, expireTime(), HttpMethod.GET).toString();
        int retry = 0;
        while (StringUtils.isBlank(url) && retry < 3) {
            this.ossClient.putObject(bucket, generateFileKey(fileName), inputStream).getResponse();
            url = ossClient.generatePresignedUrl(bucket, fileName, expireTime(), HttpMethod.GET).toString();
            retry++;
        }
        return ApiResponse.ofSuccess(url);
    }


    @Override
    public boolean deleteFile(String key) {
        this.ossClient.deleteObject(bucket, key);
        return true;
    }

    private String generateFileKey(String originalFileName) {
        if (!StringUtils.isNotBlank(originalFileName)) {
            return null;
        }
        String suffix = FilePathHelper.parseFileExtension(originalFileName);
        return DigestUtils.md5DigestAsHex(UUID.randomUUID().toString().getBytes()).substring(0, 8) + new Date().getTime()+"."+suffix;
    }

    private Date expireTime() {
        return new Date(System.currentTimeMillis() + 24 * 1000 * 3600 * 360);
    }


}
