package com.rsd.yaycha.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.rsd.yaycha.dto.CreateUserDTO;
import com.rsd.yaycha.dto.LoginDTO;
import com.rsd.yaycha.dto.UserDTO;
import com.rsd.yaycha.dto.UserWithTokenDto;
import com.rsd.yaycha.entities.User;
import com.rsd.yaycha.repositories.UserRepository;
import com.rsd.yaycha.utils.JwtService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;
    
    @Autowired
    private AuthenticationManager authenticationManager;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    public UserWithTokenDto createUser(CreateUserDTO userDTO) {

        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User user = convertDtoToEntity(userDTO);
        user = userRepository.save(user);
        String accessToken = jwtService.generateToken(user.getUserName());
        return convertEntityToTokenDto(user, accessToken);
    }

    public UserWithTokenDto loginUser(LoginDTO userDTO) {
        // authenticationManager.authenticate(
        //         new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
        //                 userDTO.getUserName(), userDTO.getPassword()));

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getUserName(), userDTO.getPassword()));

        if(!authentication.isAuthenticated())
            throw new RuntimeException("Invalid credentials");
            
        User user = userRepository.findByUserName(userDTO.getUserName());
        String accessToken = jwtService.generateToken(user.getUserName());
        return convertEntityToTokenDto(user, accessToken);
    }

    
    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findOneById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with this id"));
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUserName(username);
        return user;
    }
    
    public UserDTO deleteUser(int id) {
        User user = findOneById(id);
        if(user == null) {
            throw new EntityNotFoundException("User not found");
        }
        userRepository.delete(user);
        return convertEntityToDto(user);
    }


    //utils
    private User convertDtoToEntity(CreateUserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setBio(userDTO.getBio());
        user.setUserName(userDTO.getUserName());
        user.setPassword(userDTO.getPassword());
        return user;
    }

    private UserDTO convertEntityToDto(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setBio(user.getBio());
        userDTO.setUserName(user.getUserName());
        return userDTO;
    }

    private UserWithTokenDto convertEntityToTokenDto(User user,  String accessToken) {
        UserWithTokenDto userWithTokenDTO = new UserWithTokenDto();
        userWithTokenDTO.setId(user.getId());
        userWithTokenDTO.setName(user.getName());
        userWithTokenDTO.setUserName(user.getUserName());
        userWithTokenDTO.setAccessToken(accessToken);
        return userWithTokenDTO;
    }

}
