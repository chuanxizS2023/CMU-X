package com.cmux.user.service;

import com.cmux.user.dto.JwtResponse;
import com.cmux.user.dto.LoginRequest;
import com.cmux.user.dto.RefreshTokenRequest;
import com.cmux.user.dto.SignUpRequest;
import com.cmux.user.dto.UserUpdateRequest;
import com.cmux.user.entity.User;
import com.cmux.user.repository.UserRepository;
import com.cmux.user.security.CustomUserDetails;
import com.cmux.user.utils.AccessTokenFactory;
import com.cmux.user.utils.JwtUtils;
import com.cmux.user.utils.RefreshTokenFactory;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private AccessTokenFactory accessTokenFactory;

    @Autowired
    private RefreshTokenFactory refreshTokenFactory;

    @Autowired
    private JwtUtils jwtUtils;


    public User registerUser(SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new RuntimeException("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new RuntimeException("Error: Email is already in use!");
        }

        // Creating user's account
        User user = new User(signUpRequest.getUsername(),
                             signUpRequest.getEmail(),
                             encoder.encode(signUpRequest.getPassword()));

        return userRepository.save(user);
    }

    public JwtResponse loginUser(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            CustomUserDetails userPrincipal = (CustomUserDetails) authentication.getPrincipal();
            String accessToken = accessTokenFactory.createToken(userPrincipal);
            String refreshToken = refreshTokenFactory.createToken(userPrincipal);
            return new JwtResponse(accessToken, refreshToken, userPrincipal.getUserId(),loginRequest.getUsername());
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid username or password", e);
        }
    }

    public JwtResponse refreshAccessToken(RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();
        if (!jwtUtils.validateJwtToken(refreshToken)) {
            throw new RuntimeException("Invalid refresh token");
        }

        long userId = jwtUtils.getUserIdFromJwtToken(refreshToken);
        String username = jwtUtils.getUserNameFromJwtToken(refreshToken);
        User user = getUserById(userId);
        UserDetails userDetails = new CustomUserDetails(user);
        CustomUserDetails customUserDetails = (CustomUserDetails) userDetails;
        String newAccessToken = accessTokenFactory.createToken(customUserDetails);

        return new JwtResponse(newAccessToken, refreshToken, userId, username);
    }

    public User getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        throw new RuntimeException("User not found");
    }

    public List<User> getUsersByIds(List<Long> ids) {
        return userRepository.findAllById(ids);
    }

    public User updateUserProfile(Long id, UserUpdateRequest updateRequest) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User currentUser = userOptional.get();
        
            if (updateRequest.getUsername() != null) {
                currentUser.setUsername(updateRequest.getUsername());
            }

            if (updateRequest.getEmail() != null) {
                currentUser.setEmail(updateRequest.getEmail());
            }

            return userRepository.save(currentUser);
        }
        throw new RuntimeException("User not found");
    }

}
