package com.focus.service.file;

import com.focus.base.ApiResponse;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;

/**
 * @Description： 文件操作
 * @Author: shadow
 * @Date: create in 15:21 2019/1/5
 */
@Service
public class FileServiceImpl implements IFileService, InitializingBean {

    @Autowired
    private UploadManager uploadManager;

    @Autowired
    private BucketManager bucketManager;

    @Autowired
    private Auth auth;

    @Value("${qiniu.Bucket}")
    private String bucket;

    private StringMap putPolicy;

    @Override
    public Response uploadFile(File file) throws QiniuException {
        Response response = this.uploadManager.put(file, null, getUploadToken());
        int retry = 0;
        while (response.needRetry() && retry < 3) {
            response = this.uploadManager.put(file, null, getUploadToken());
            retry++;
        }
        return response;
    }

    @Override
    public Response uploadFile(InputStream inputStream) throws QiniuException {
        Response response = this.uploadManager.put(inputStream, null, getUploadToken(), null, null);
        int retry = 0;
        while (response.needRetry() && retry < 3) {
            response = this.uploadManager.put(inputStream, null, getUploadToken(), null, null);
            retry++;
        }
        return response;
    }

    @Override
    public Response deleteFile(String key) throws QiniuException {
        Response response = this.bucketManager.delete(bucket, key);
        int reTry = 0;
        while (response.needRetry() && reTry < 3) {
            response = this.bucketManager.delete(bucket, key);
            reTry++;
        }
        return response;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        this.putPolicy = new StringMap();
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"width\":$(imageInfo.width), \"height\":${imageInfo.height}}");
    }


    /**
     * 获取认证token
     *
     * @return
     */
    private String getUploadToken() {
        return this.auth.uploadToken(bucket, null, 3600, putPolicy);
    }

}
