package com.focus.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @Description： 房屋实体
 * @Author: shadow
 * @Date: create in 12:22 2019/1/6
 */
@Entity
@Table(name = "house")
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private int price;

    private int area;

    private int room;

    private int floor;

    @Column(name = "total_floor")
    private int totalFloor;

    @Column(name = "watch_times")
    private int watchTimes;

    @Column(name = "build_year")
    private int buildYear;

    private int status;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "last_update_time")
    private Date lastUpdateTime;

    @Column(name = "city_en_name")
    private String cityEnName;

    @Column(name = "region_en_name")
    private String regionEnName;

    private String cover;

    private int direction;

    @Column(name = "distance_to_subway")
    private int distanceToSubway;

    private int parlour;

    private String district;

    @Column(name = "admin_id")
    private int adminId;

    private int bathroom;

    private String street;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getTotalFloor() {
        return totalFloor;
    }

    public void setTotalFloor(int totalFloor) {
        this.totalFloor = totalFloor;
    }

    public int getWatchTimes() {
        return watchTimes;
    }

    public void setWatchTimes(int watchTimes) {
        this.watchTimes = watchTimes;
    }

    public int getBuildYear() {
        return buildYear;
    }

    public void setBuildYear(int buildYear) {
        this.buildYear = buildYear;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getCityEnName() {
        return cityEnName;
    }

    public void setCityEnName(String cityEnName) {
        this.cityEnName = cityEnName;
    }

    public String getRegionEnName() {
        return regionEnName;
    }

    public void setRegionEnName(String regionEnName) {
        this.regionEnName = regionEnName;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getDistanceToSubway() {
        return distanceToSubway;
    }

    public void setDistanceToSubway(int distanceToSubway) {
        this.distanceToSubway = distanceToSubway;
    }

    public int getParlour() {
        return parlour;
    }

    public void setParlour(int parlour) {
        this.parlour = parlour;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public int getBathroom() {
        return bathroom;
    }

    public void setBathroom(int bathroom) {
        this.bathroom = bathroom;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
