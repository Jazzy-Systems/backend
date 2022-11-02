package com.jazzysystems.backend.user.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jazzysystems.backend.person.Person;
import com.jazzysystems.backend.person.repository.PersonRepository;
import com.jazzysystems.backend.user.User;
import com.jazzysystems.backend.user.UserDetailsImpl;
import com.jazzysystems.backend.user.UserMapper;
import com.jazzysystems.backend.user.dto.UserDTO;
import com.jazzysystems.backend.user.repository.UserRepository;
import com.jazzysystems.backend.resident.Resident;
import com.jazzysystems.backend.resident.repository.ResidentRepository;
import com.jazzysystems.backend.securityguard.SecurityGuard;
import com.jazzysystems.backend.securityguard.repository.SecurityGuardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ResidentRepository residentRepository;

    @Autowired
    private SecurityGuardRepository securityGuardRepository;

    @Autowired
    private UserMapper userMapper;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> optionalPerson = personRepository.findByEmail(username);
        if (optionalPerson.isPresent()) {
            Optional<User> optionalUser = userRepository.findUserByPerson(optionalPerson.get());
            if (optionalUser.isPresent()) {
                Boolean isUser = true;
                Optional <Resident> optionalResident = residentRepository.findByPerson(optionalPerson.get());
                Optional <SecurityGuard> optionalSecurityGuard = securityGuardRepository.findByPerson(optionalPerson.get());
                
                if(optionalResident.isPresent()) isUser = optionalResident.get().getIsResident();
                if(optionalSecurityGuard.isPresent()) isUser = optionalSecurityGuard.get().getIsActive();

                if(isUser){
                    return new UserDetailsImpl(optionalUser.get());
                }
                else  {
                    throw new UsernameNotFoundException("User is not available");    
                }
                
                
            } else {
                throw new UsernameNotFoundException("User Not Found");
            }
        } else {
            throw new UsernameNotFoundException("User Not Found");
        }

    }

    @Override
    public User findUserByEmail(String email) {
        Optional<Person> optionalPerson = personRepository.findByEmail(email);
        if (optionalPerson.isPresent()) {
            Optional<User> optionalUser = userRepository.findUserByPerson(optionalPerson.get());
            if (optionalUser.isPresent()) {
                return optionalUser.get();
            } else {
                throw new UsernameNotFoundException("User Not Found");
            }
        } else {
            throw new UsernameNotFoundException("User Not Found");
        }
    }

    @Override
    public Boolean existsByEmail(String email) {
        Optional<Person> optionalPerson = personRepository.findByEmail(email);
        if (optionalPerson.isPresent()) {
            Optional<User> optionalUser = userRepository.findUserByPerson(optionalPerson.get());
            return optionalUser.isPresent();
        }
        return false;
    }

    @Override
    public User saveUser(UserDTO userDTO) {
        User user = userMapper.convertDTOtoUser(userDTO);
        return userRepository.save(user);
    }

}
