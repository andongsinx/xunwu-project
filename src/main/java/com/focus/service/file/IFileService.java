package com.focus.service.file;

import com.aliyun.oss.OSSException;
import com.focus.base.ApiResponse;

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
    ApiResponse uploadFile(File file) throws OSSException;

    /**
     * 通过流上传
     *
     * @param inputStream
     * @return
     */
    ApiResponse uploadFile(InputStream inputStream,String originalFileName) throws OSSException;

    /**
     * 删除文件
     * @param key
     * @return
     * @throws
     */
    boolean deleteFile(String key) throws OSSException;


}
