package com.FLsolutions.schoolTrack.services;
import org.springframework.stereotype.Service;

import com.FLsolutions.schoolTrack.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
	

}
