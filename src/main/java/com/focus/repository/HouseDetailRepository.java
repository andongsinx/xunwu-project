package com.focus.repository;

import com.focus.entity.HouseDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Description：
 * @Author: shadow
 * @Date: create in 20:31 2019/1/7
 */
public interface HouseDetailRepository extends JpaRepository<HouseDetail, Long> {

    HouseDetail findByHouseId(Long id);

    List<HouseDetail> findByHouseIdIn(List<Long> houseIds);
}
