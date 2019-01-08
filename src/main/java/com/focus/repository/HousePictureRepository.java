package com.focus.repository;

import com.focus.entity.HousePicture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Description：
 * @Author: shadow
 * @Date: create in 20:32 2019/1/7
 */
public interface HousePictureRepository extends JpaRepository<HousePicture, Long> {
    List<HousePicture> findAllByHouseId(Long id);
}
