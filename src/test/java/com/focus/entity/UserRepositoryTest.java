package com.focus.entity;

import com.focus.ApplicationTests;
import com.focus.repository.UserRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description：
 * @Author：shadow
 * @Date：ceate in 14:20 2018/12/31
 */
public class UserRepositoryTest extends ApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSelectOne(){
        User user = userRepository.getOne(1L);
        System.out.println(user.getAvatar());
    }
}
