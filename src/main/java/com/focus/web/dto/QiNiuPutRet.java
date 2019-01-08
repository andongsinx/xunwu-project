package com.focus.web.dto;

/**
 * @Description： 七牛文件属性
 * @Author: shadow
 * @Date: create in 17:25 2019/1/5
 */
public class QiNiuPutRet {

    public String key;
    public String hash;
    public String bucket;
    public int width;
    public int height;

    @Override
    public String toString() {
        return "QiNiuPutRet{" +
                "key='" + key + '\'' +
                ", hash='" + hash + '\'' +
                ", bucket='" + bucket + '\'' +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
