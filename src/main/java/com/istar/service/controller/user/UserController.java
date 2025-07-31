package com.istar.service.controller.user;

import com.istar.service.entity.user.User;
import com.istar.service.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String fullName
    ) {
        User user = userService.register(username, password, fullName);
        return ResponseEntity.ok(user);
    }
}
