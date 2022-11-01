package com.jazzysystems.backend.user.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jazzysystems.backend.auth.Authentication;
import com.jazzysystems.backend.auth.dto.RecoverPasswordDTO;
import com.jazzysystems.backend.person.Person;
import com.jazzysystems.backend.person.repository.PersonRepository;
import com.jazzysystems.backend.user.User;
import com.jazzysystems.backend.user.UserDetailsImpl;
import com.jazzysystems.backend.user.UserMapper;
import com.jazzysystems.backend.user.dto.UserDTO;
import com.jazzysystems.backend.user.repository.UserRepository;
import com.jazzysystems.backend.util.SecurityCodeGenerator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Authentication autentication;

    @Autowired
    private SecurityCodeGenerator securityCodeGenerator;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> optionalPerson = personRepository.findByEmail(username);
        if (optionalPerson.isPresent()) {
            Optional<User> optionalUser = userRepository.findUserByPerson(optionalPerson.get());
            if (optionalUser.isPresent()) {
                return new UserDetailsImpl(optionalUser.get());
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

    @Override
    public void recoverPassword(RecoverPasswordDTO recoverPasswordDTO) {
        int LEN = 10;
        User user = this.findUserByEmail(recoverPasswordDTO.getEmail());
        String password = securityCodeGenerator.generatePassword(LEN);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    @Override
    public void changePassword(RecoverPasswordDTO recoverPasswordDTO) {
        User user = this.findUserByEmail(recoverPasswordDTO.getEmail());
        if (user.equals(autentication.getUser()) &&
                passwordEncoder.matches(
                        recoverPasswordDTO.getCurrentPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(recoverPasswordDTO.getNewPassword()));
            userRepository.save(user);
        } else {
            throw new UsernameNotFoundException("Datos incorrectos");
        }
    }

}
