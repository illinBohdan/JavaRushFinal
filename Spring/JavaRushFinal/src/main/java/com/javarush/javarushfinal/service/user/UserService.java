package com.javarush.javarushfinal.service.user;

import com.javarush.javarushfinal.entity.User;
import org.springframework.validation.BindingResult;

import java.sql.SQLException;
import java.util.Optional;

public interface UserService {
    void registerUser(User user);
    Optional<User> findByUserName(String userName);
    boolean existsByUserName(String userName) throws SQLException;
    void checkUserName(User user, BindingResult result);
    User findUserById(Long id);
    User getUserFromSpringSecurity();
}
