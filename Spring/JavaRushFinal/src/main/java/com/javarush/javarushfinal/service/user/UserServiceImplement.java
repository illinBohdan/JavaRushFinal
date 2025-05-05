package com.javarush.javarushfinal.service.user;

import com.javarush.javarushfinal.entity.User;
import com.javarush.javarushfinal.entity.UserRole;
import com.javarush.javarushfinal.repository.user.UserRepository;
import com.javarush.javarushfinal.repository.user.UserRepositoryWithJDBC;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.sql.SQLException;
import java.util.Optional;

@Service
public class UserServiceImplement implements UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepositoryWithJDBC userRepositoryWithJDBC;

    public UserServiceImplement(UserRepository userRepository, PasswordEncoder passwordEncoder, UserRepositoryWithJDBC userRepositoryWithJDBC) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepositoryWithJDBC = userRepositoryWithJDBC;
    }

    @Override
    public void registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Хешування пароля
        user.setUserRole(UserRole.USER);
        userRepository.save(user);
    }

    @Override
    public Optional<User> findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    /*@Override
    public boolean existsByUserName(String userName) {
        return userRepository.existsByUserName(userName);
    }*/

    @Override
    public boolean existsByUserName(String userName){
        return userRepositoryWithJDBC.existByUserNameJDBC(userName);
    }

    @Override
    public void checkUserName(User user, BindingResult result){//перевіряємо чи логін вільний
        if(existsByUserName(user.getUserName())){
            result.rejectValue("userName", "error.user", "Цей логін вже зайнятий!");
        }
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Користувача не знайдено"));
    }

    @Override
    public User getUserFromSpringSecurity() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        return userRepository.findByUserName(userName)
                .orElseThrow(()-> new UsernameNotFoundException("Користувача не знайдено з логіном:" + userName));
    }
}
