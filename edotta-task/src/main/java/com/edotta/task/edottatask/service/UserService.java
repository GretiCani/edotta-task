package com.edotta.task.edottatask.service;


import org.springframework.security.core.userdetails.UserDetailsService;

import com.edotta.task.edottatask.model.User;
import com.edotta.task.edottatask.web.dto.UserRegistrationDto;

public interface UserService extends UserDetailsService {

	User findByEmail(String email);

    User save(UserRegistrationDto registration);
}
