package com.istar.service.controller.administrator.usersmanagement.auth;

import com.istar.service.entity.administrator.usersmanagement.permission.Role;
import com.istar.service.entity.administrator.usersmanagement.permission.RoleFeaturePermission;
import com.istar.service.entity.administrator.usersmanagement.user.User;
import com.istar.service.repository.administrator.usersmanagement.permission.RoleFeaturePermissionRepository;
import com.istar.service.repository.administrator.usersmanagement.user.UserRepository;
import com.istar.service.security.JwtUtils;
import com.istar.service.dto.administrator.usersmanagement.permission.FeaturePermissionDTO;
import com.istar.service.dto.administrator.usersmanagement.auth.UserInfoDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final RoleFeaturePermissionRepository roleFeaturePermissionRepository;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtUtils jwtUtils,
                          UserRepository userRepository,
                          RoleFeaturePermissionRepository roleFeaturePermissionRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
        this.roleFeaturePermissionRepository = roleFeaturePermissionRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String jwt = jwtUtils.generateJwtToken(userDetails.getUsername());
        Optional<User> optionalUser = userRepository.findByUsername(userDetails.getUsername());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            
            // Check both direct role and userRoles
            if (user.getRole() == null && (user.getUserRoles() == null || user.getUserRoles().isEmpty())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("User has no roles assigned. Please contact administrator.");
            }

            // Use direct role if available, otherwise use the first role from userRoles
            Role effectiveRole = user.getRole();
            if (effectiveRole == null && !user.getUserRoles().isEmpty()) {
                effectiveRole = user.getUserRoles().get(0).getRole();
                // Update the direct role reference
                user.setRole(effectiveRole);
                userRepository.save(user);
            }

            // ✅ Save token
            user.setLoginToken(jwt);
            userRepository.save(user);

            // ✅ Fetch Role-Feature-Permissions
            List<RoleFeaturePermission> permissions = roleFeaturePermissionRepository.findByRole(effectiveRole);

            List<FeaturePermissionDTO> permissionDTOs = permissions.stream().map(p -> {
                FeaturePermissionDTO dto = new FeaturePermissionDTO();
                dto.setRoleId(p.getRole().getId());
                dto.setFeatureId(p.getFeature().getId());
                dto.setIsSearch(p.getIsSearch());
                dto.setIsAdd(p.getIsAdd());
                dto.setIsViewed(p.getIsViewed());
                dto.setIsEdit(p.getIsEdit());
                dto.setIsApprove(p.getIsApprove());
                dto.setIsReject(p.getIsReject());
                dto.setIsDeleted(p.getIsDeleted());
                dto.setIsSave(p.getIsSave());
                dto.setIsClear(p.getIsClear());
                dto.setIsCancel(p.getIsCancel());
                dto.setIsProcess(p.getIsProcess());
                dto.setIsImport(p.getIsImport());
                dto.setIsExport(p.getIsExport());
                dto.setBStatus(p.getBStatus());
                return dto;
            }).collect(Collectors.toList());

            // ✅ Create response DTO
            UserInfoDTO response = new UserInfoDTO(
                    user.getId(),
                    user.getUsername(),
                    jwt,
                    effectiveRole.getName(),
                    permissionDTOs
            );

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid user");
    }

    // ✅ LOGOUT
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authHeader,
                                         HttpServletRequest request) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7).replace("\"", "").trim();

        try {
            String username = jwtUtils.getUserNameFromJwtToken(token);
            Optional<User> optionalUser = userRepository.findByUsername(username);

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                user.setLoginToken(null);
                userRepository.save(user);

                HttpSession session = request.getSession(false);
                if (session != null) session.invalidate();

                return ResponseEntity.ok("Logged out");
            } else {
                return ResponseEntity.status(401).body("Invalid token - user not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Token parsing failed: " + e.getMessage());
        }
    }

    // ✅ Login Request DTO
    public static class LoginRequest {
        private String username;
        private String password;

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}