package com.focus.security;

import com.focus.entity.User;
import com.focus.repository.RoleRepository;
import com.focus.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Description：
 * @Author：shadow
 * @Date：ceate in 15:47 2018/12/31
 */
public class AuthProvider implements AuthenticationProvider {

    @Autowired
    private IUserService userService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String inputName = authentication.getName();
        String inputPassword = (String) authentication.getCredentials();
        User user = userService.findByName(inputName);
        if (user == null) {
            throw new AuthenticationCredentialsNotFoundException("authError");
        }

        if (this.passwordEncoder.matches(inputPassword, user.getPassword())) {
            return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        }
        throw new BadCredentialsException("authError");
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
