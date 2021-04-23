package com.lottery.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

import com.lottery.dto.LocalUser;
import com.lottery.dto.SignUpRequest;
import com.lottery.dto.UserInfo;
import com.lottery.exceptions.UserAlreadyExistAuthenticationException;
import com.lottery.model.User;
 
/**
 * @author Victor
 */
public interface UserService {
 
    public User registerNewUser(SignUpRequest signUpRequest) throws UserAlreadyExistAuthenticationException;
 
    User findUserByEmail(String email);
 
    Optional<User> findUserById(Long id);
 
    LocalUser processUserRegistration(String registrationId, Map<String, Object> attributes, OidcIdToken idToken, OidcUserInfo userInfo);
    
    public List<UserInfo> getAll();
    
    public void updateAdminRole(Long id, boolean active);
}
