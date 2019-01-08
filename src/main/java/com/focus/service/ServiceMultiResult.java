package com.focus.service;

import java.util.List;

/**
 * @Description： 通过多结果service返回结构
 * @Author: shadow
 * @Date: create in 12:42 2019/1/6
 */
public class ServiceMultiResult<T> {

    private long total;

    private List<T> result;

    public ServiceMultiResult(long total, List<T> result) {
        this.total = total;
        this.result = result;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public int getResultSize(){
        if(this.result == null || this.result.size() == 0){
            return 0;
        }
        return this.result.size();
    }

}
