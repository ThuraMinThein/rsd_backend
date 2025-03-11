package com.rsd.yaycha.services;

import java.util.Date;
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
import com.rsd.yaycha.entities.Follow;
import com.rsd.yaycha.entities.User;
import com.rsd.yaycha.repositories.FollowRepository;
import com.rsd.yaycha.repositories.UserRepository;
import com.rsd.yaycha.utils.JwtService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowRepository followRepository;

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
    
    public UserDTO followUser(int id) {
        User user = findOneById(id);
        User currentUser = getCurrentUser();
        Follow follow = new Follow();
        follow.setFollower(currentUser);
        follow.setFollowing(user);
        follow.setCreatedAt(new Date());
        followRepository.save(follow);
        return convertEntityToDto(user);
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

    
    public List<User> getUserFollowers() {
        List<User> followers = followRepository.findFollowerByFollowingId(getCurrentUser().getId());
        return followers;
    }

    public List<User> getUserFollowings() {
        List<User> followings = followRepository.findFollowingByFollowerId(getCurrentUser().getId());
        return followings;
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
