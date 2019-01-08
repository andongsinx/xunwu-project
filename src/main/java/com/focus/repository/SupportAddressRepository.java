package com.focus.repository;

import com.focus.entity.SupportAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Description：
 * @Author: shadow
 * @Date: create in 12:55 2019/1/6
 */
public interface SupportAddressRepository extends JpaRepository<SupportAddress, Long> {
    /**
     * 通过行政级别查找地址
     *
     * @param level
     * @return
     */
    List<SupportAddress> findAllByLevel(String level);

    /**
     * 通过行政级别和所属上级区域查询
     *
     * @param region
     * @param belongTo
     * @return
     */
    List<SupportAddress> findAllByLevelAndBelongTo(String region, String belongTo);

    /**
     * 通过区域编号和行政级别查询
     *
     * @param enName
     * @param level
     * @return
     */
    SupportAddress findByEnNameAndLevel(String enName, String level);


    /**
     * 通过区域和所属上级区域查询
     *
     * @param enName
     * @param belongTo
     * @return
     */
    SupportAddress findByEnNameAndBelongTo(String enName, String belongTo);
}
