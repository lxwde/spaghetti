package com.zpmc.ztos.infra.business.service;

import com.zpmc.ztos.infra.business.config.Constants;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO: read user name and password hash from db table

		if ("admin".equals(username)) {
			return new User("admin", "$2a$10$Q6ZDSHV61k.2U9FV8aFf7.ahB7XjmzJiqn4NB.3tml1yhTTS2IxpS",
					Arrays.asList(() -> Constants.ADMIN));
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}

}