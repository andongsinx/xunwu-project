package com.focus.service.file;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;

import java.io.File;
import java.io.InputStream;

/**
 * @Description： 文件操作service
 * @Author: shadow
 * @Date: create in 15:17 2019/1/5
 */
public interface IFileService {


    /**
     * 通过文件上传
     *
     * @param file
     * @return
     */
    Response uploadFile(File file) throws QiniuException;

    /**
     * 通过流上传
     *
     * @param inputStream
     * @return
     */
    Response uploadFile(InputStream inputStream) throws QiniuException;

    /**
     * 删除文件
     * @param key
     * @return
     * @throws QiniuException
     */
    Response deleteFile(String key) throws QiniuException;


}
