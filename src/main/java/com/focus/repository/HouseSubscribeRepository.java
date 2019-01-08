package com.focus.repository;

import com.focus.entity.HouseSubscribe;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description：
 * @Author: shadow
 * @Date: create in 21:57 2019/1/8
 */
public interface HouseSubscribeRepository extends JpaRepository<HouseSubscribe,Long> {

    HouseSubscribe findByHouseIdAndUserId(Long houseId,Long userId);
}
