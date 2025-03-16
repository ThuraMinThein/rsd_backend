package com.rsd.yaycha.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.rsd.yaycha.dto.CreateUserDTO;
import com.rsd.yaycha.dto.LoginDTO;
import com.rsd.yaycha.dto.RefreshTokenRequest;
import com.rsd.yaycha.dto.UserDTO;
import com.rsd.yaycha.dto.UserWithTokenDto;
import com.rsd.yaycha.entities.Follow;
import com.rsd.yaycha.entities.RefreshToken;
import com.rsd.yaycha.entities.User;
import com.rsd.yaycha.repositories.FollowRepository;
import com.rsd.yaycha.repositories.RefreshTokenRepository;
import com.rsd.yaycha.repositories.UserRepository;
import com.rsd.yaycha.utils.JwtService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

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

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    private String createRefreshToken(User user) {
        String generatedRefreshToken = jwtService.generateRefreshToken(user.getUserName());
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(generatedRefreshToken);
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24));
        refreshTokenRepository.save(refreshToken);
        return generatedRefreshToken;
    }

    public UserWithTokenDto createUser(CreateUserDTO userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User user = convertDtoToEntity(userDTO);
        user = userRepository.save(user);
        String accessToken = jwtService.generateToken(user.getUserName());
        String refreshToken = createRefreshToken(user);
        return convertEntityToTokenDto(user, accessToken, refreshToken);
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
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getUserName(), userDTO.getPassword()));

        if(!authentication.isAuthenticated())
            throw new RuntimeException("Invalid credentials");
            
        User user = userRepository.findByUserName(userDTO.getUserName());
        String accessToken = jwtService.generateToken(user.getUserName());
        String refreshToken = createRefreshToken(user);
        return convertEntityToTokenDto(user, accessToken, refreshToken);
    }

    
    public UserWithTokenDto refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();
        Optional<RefreshToken> refreshTokenFromDb = refreshTokenRepository.findByToken(refreshToken);

        if(refreshTokenFromDb.isEmpty() || refreshTokenFromDb.get().getExpiryDate().before(new Date()) || refreshTokenFromDb.get().isRevoked()) {
            throw new RuntimeException("Invalid refresh token");
        }

        RefreshToken validRefreshToken = refreshTokenFromDb.get();
        User user = validRefreshToken.getUser();

        User currentUser = getCurrentUser();
        String newJwt = jwtService.generateToken(currentUser.getUserName());
        String newRefreshToken = createRefreshToken(user);
        return convertEntityToTokenDto(user, newJwt, newRefreshToken);
    }

    public String logoutUser(HttpServletRequest httpServletRequest) {
        User currentUser = getCurrentUser();

        List<RefreshToken> refreshTokens = refreshTokenRepository.findByUser_UserName(currentUser.getUserName());

        for(RefreshToken refreshToken : refreshTokens) {
            refreshToken.setRevoked(true);
        }
        refreshTokenRepository.saveAll(refreshTokens);
        return "Logged out successfully";
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

    private UserWithTokenDto convertEntityToTokenDto(User user,  String accessToken, String refreshToken) {
        UserWithTokenDto userWithTokenDTO = new UserWithTokenDto();
        userWithTokenDTO.setId(user.getId());
        userWithTokenDTO.setName(user.getName());
        userWithTokenDTO.setUserName(user.getUserName());
        userWithTokenDTO.setAccessToken(accessToken);
        userWithTokenDTO.setRefreshToken(refreshToken);
        return userWithTokenDTO;
    }

}
