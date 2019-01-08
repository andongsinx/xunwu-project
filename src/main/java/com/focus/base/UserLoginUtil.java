package com.focus.base;

import com.focus.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @Description：
 * @Author: shadow
 * @Date: create in 20:22 2019/1/7
 */
public class UserLoginUtil {

    public static int getLoginUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null && authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            return user.getId().intValue();
        }
        return -1;
    }
}
