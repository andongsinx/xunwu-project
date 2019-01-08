package com.focus.service;

import com.focus.web.dto.HouseDTO;

/**
 * @Description： 服务接口通用结构
 * @Author: shadow
 * @Date: create in 12:45 2019/1/6
 */
public class ServiceResult<T> {

    private boolean success;

    private String message;

    private T result;

    public ServiceResult(boolean success) {
        this.success = success;
    }

    public ServiceResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }


    public ServiceResult(boolean success, String message, T result) {
        this.success = success;
        this.message = message;
        this.result = result;
    }

    public static ServiceResult ofMessage(Message message){
        return new ServiceResult(false,message.getValue());
    }

    public static ServiceResult<HouseDTO> notFound() {
        return new ServiceResult<>(false,"data not found!",null);
    }

    public static ServiceResult of(Object result) {
        return new ServiceResult(true,null,result);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public enum Message{
        NOT_FOUND("Not Found Resource!"),
        NOT_LOGIN("User not login!");

        private String value;

        Message(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
