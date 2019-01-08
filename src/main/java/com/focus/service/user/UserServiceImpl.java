package com.focus.service.user;

import com.focus.entity.Role;
import com.focus.entity.User;
import com.focus.repository.RoleRepository;
import com.focus.repository.UserRepository;
import com.focus.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description：
 * @Author：shadow
 * @Date：ceate in 16:04 2018/12/31
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User findByName(String username) {
        User user = userRepository.findByName(username);
        if(user == null){
            return null;
        }
        List<Role> roleList = roleRepository.findAllByUserId(user.getId());
        if(roleList == null || roleList.isEmpty()){
            throw new DisabledException("权限非法");
        }
        List<GrantedAuthority> authorityList = new ArrayList<>();
        roleList.forEach(role->authorityList.add(new SimpleGrantedAuthority("ROLE_"+role.getName())));
        user.setAuthorityList(authorityList);
        return user;
    }
}
