package com.focus.repository;

import com.focus.entity.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Description：
 * @Author: shadow
 * @Date: create in 20:28 2019/1/7
 */
public interface HouseRepository extends JpaRepository<House,Long> , JpaSpecificationExecutor {
}

