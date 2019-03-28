package com.focus.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @Description：
 * @Author: shadow
 * @Date: create in 14:08 2019/1/6
 */
public class HousePictureDTO {

    private Long id;

    @JsonProperty(value = "house_id")
    private Long houseId;

    @JsonProperty(value = "file_url")
    private String fileUrl;

    @JsonProperty(value = "file_key")
    private String fileKey;

    private int width;

    private int height;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "HousePictureDTO{" +
                "id=" + id +
                ", houseId=" + houseId +
                ", fileUrl='" + fileUrl + '\'' +
                ", fileKey='" + fileKey + '\'' +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
