package com.focus.repository;

import com.focus.entity.House;
import org.hibernate.annotations.SQLUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @Description：
 * @Author: shadow
 * @Date: create in 20:28 2019/1/7
 */
public interface HouseRepository extends JpaRepository<House,Long> , JpaSpecificationExecutor {

    @Modifying
    @Query("update House as house set house.cover=:cover where house.id=:id")
    void updateCover(@Param("id") long targetId, @Param("cover") String cover);
}

