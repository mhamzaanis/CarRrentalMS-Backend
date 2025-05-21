package com.Buildex.RentalCarApp.Services;

import com.Buildex.RentalCarApp.Entities.User;
import com.Buildex.RentalCarApp.Repositories.BookingRepository;
import com.Buildex.RentalCarApp.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private final UserRepository userRepository;


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        try{
            return userRepository.save(user);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void deleteUser(Long id) {


        if (bookingRepository.findByUserId(id).isEmpty()) {
            userRepository.deleteById(id);
        } else {
            throw new IllegalStateException("Cannot delete user with existing bookings");
        }
    }

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
}