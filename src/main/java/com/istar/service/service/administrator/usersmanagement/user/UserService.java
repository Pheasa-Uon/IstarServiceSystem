package com.istar.service.service.administrator.usersmanagement.user;

import com.istar.service.entity.administrator.usersmanagement.user.User;
import java.util.List;

public interface UserService {
    User saveUser(User user);
    User updateUser(Long id, User user);
    User findByUsername(String username);
    List<User> getAllUsers();
    User getUserById(Long id);
    User resetPassword(Long id);
    List<User> searchUsers(String keyword);

    void softDeleteUser(Long id);
}

