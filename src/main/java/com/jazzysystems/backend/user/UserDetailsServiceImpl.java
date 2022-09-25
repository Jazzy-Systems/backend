package com.jazzysystems.backend.user;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findUserByEmail(username);
        if (optionalUser.isPresent()) {
            return new UserDetailsImpl(optionalUser.get());
        } else {
            throw new UsernameNotFoundException("User Not Found");
        }
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email).get();
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User saveUser(UserDTO userDTO) {
        User user = userMapper.convertDTOtoUser(userDTO);
        return userRepository.save(user);

    }

}
