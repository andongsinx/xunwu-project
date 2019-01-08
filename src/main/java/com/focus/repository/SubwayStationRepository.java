package com.focus.repository;

import com.focus.entity.SubwayStation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Description：
 * @Author: shadow
 * @Date: create in 12:54 2019/1/6
 */
public interface SubwayStationRepository extends JpaRepository<SubwayStation,Long> {

    List<SubwayStation> findAllBySubwayId(Long subwayId);
}
