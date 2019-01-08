package com.focus.repository;

import com.focus.entity.HouseTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Description：
 * @Author: shadow
 * @Date: create in 20:53 2019/1/7
 */
public interface HouseTagRepository extends JpaRepository<HouseTag, Long> {
    List<HouseTag> findAllByHouseId(Long id);
}
