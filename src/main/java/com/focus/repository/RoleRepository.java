package com.focus.repository;

import com.focus.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Description：
 * @Author：shadow
 * @Date：ceate in 16:01 2018/12/31
 */
public interface RoleRepository extends JpaRepository<Role,Long> {

    List<Role>  findAllByUserId(Long userId);

}
