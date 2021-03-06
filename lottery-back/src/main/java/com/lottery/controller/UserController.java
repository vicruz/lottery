package com.lottery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lottery.config.CurrentUser;
import com.lottery.dto.LocalUser;
import com.lottery.service.UserService;
import com.lottery.utils.GeneralUtils;
 
@RestController
@RequestMapping("/api")
public class UserController {
 
	@Autowired
	private UserService userService;
	
    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getCurrentUser(@CurrentUser LocalUser user) {
        return ResponseEntity.ok(GeneralUtils.buildUserInfo(user));
    }
 
    @GetMapping("/all")
    public ResponseEntity<?> getContent() {
        return ResponseEntity.ok(userService.getAll());
    }
 
    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getUserContent() {
        return ResponseEntity.ok("User content goes here");
    }
 
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAdminContent() {
        return ResponseEntity.ok("Admin content goes here");
    }
 
    @GetMapping("/mod")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<?> getModeratorContent() {
        return ResponseEntity.ok("Moderator content goes here");
    }
    
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @PutMapping("/update/role/{userId}/{active}")
    public void updateRole(@PathVariable Long userId, @PathVariable Boolean active) {
        userService.updateAdminRole(userId, active);
    }
}
