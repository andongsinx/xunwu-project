package com.focus.repository;

import com.focus.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description：
 * @Author：shadow
 * @Date：ceate in 14:21 2018/12/31
 */
public interface UserRepository extends JpaRepository<User,Long> {

    User findByName(String username);

}
