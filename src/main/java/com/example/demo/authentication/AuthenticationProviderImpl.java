package com.example.demo.authentication;

import java.util.Collections;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.origin.SystemEnvironmentOrigin;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.example.demo.model.UserInfo;
import com.example.demo.repository.UserInfoRepository;

@Component
public class AuthenticationProviderImpl implements AuthenticationProvider {

    @Autowired
    private UserInfoRepository userRepository;

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        final UsernamePasswordAuthenticationToken upAuth = (UsernamePasswordAuthenticationToken) authentication;
        final String name = (String) authentication.getPrincipal();

        final String password = (String) upAuth.getCredentials();

        final UserInfo userInfo = userRepository.findByName(name);
        if (userInfo == null) throw new BadCredentialsException("illegal id or passowrd");    
        
        final String storedPassword = userRepository.findByName(name).getPassword();

        if (Objects.equals(password, "") || !Objects.equals(password, storedPassword)) {
            throw new BadCredentialsException("illegal id or passowrd");
        }
        
        final Object principal = authentication.getPrincipal();
        final UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(
            principal, authentication.getCredentials(),
            Collections.emptyList());
        result.setDetails(authentication.getDetails());

        return result;
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return true;
    }

}