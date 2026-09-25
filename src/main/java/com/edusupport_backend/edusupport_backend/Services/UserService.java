package com.edusupport_backend.edusupport_backend.Services;





import com.edusupport_backend.edusupport_backend.Entity.User;

import java.util.List;

public interface UserService {

    User getUserById(Long id);

    User getUserByEmail(String email);

    List<User> getAllUsers();

    List<User> getUsersByRole(String role);

    User updateUser(Long id, User user);

    void deleteUser(Long id);
}