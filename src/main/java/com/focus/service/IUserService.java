package com.focus.service;

import com.focus.entity.User;

/**
 * @Description：
 * @Author：shadow
 * @Date：ceate in 16:03 2018/12/31
 */
public interface IUserService {

    User findByName(String username);

}
