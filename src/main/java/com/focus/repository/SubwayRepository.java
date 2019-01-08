package com.focus.repository;

import com.focus.entity.Subway;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Description：
 * @Author: shadow
 * @Date: create in 12:53 2019/1/6
 */
public interface SubwayRepository extends JpaRepository<Subway,Long> {
    List<Subway> findAllByCityEnName(String cityEnName);
}
