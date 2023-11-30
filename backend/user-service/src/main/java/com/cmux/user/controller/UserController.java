package com.cmux.user.controller;

import com.cmux.user.dto.UserUpdateRequest;
import com.cmux.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.cmux.user.entity.User; 
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        System.out.println("user: " + user);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    // Retrieve a list of users by a list of user IDs
    @PostMapping("/users")
    public ResponseEntity<?> getUsersByIds(@RequestBody List<Long> ids) {
        List<User> users = userService.getUsersByIds(ids);
        return ResponseEntity.ok(users);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserUpdateRequest updateRequest) {
        User updatedUser = userService.updateUserProfile(id, updateRequest);
        if (updatedUser == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedUser);
    }
}
