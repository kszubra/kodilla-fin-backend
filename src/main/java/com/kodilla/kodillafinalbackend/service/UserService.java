package com.kodilla.kodillafinalbackend.service;

import com.kodilla.kodillafinalbackend.exceptions.UserNotFoundException;
import com.kodilla.kodillafinalbackend.domain.User;
import com.kodilla.kodillafinalbackend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    /**
     * Method checks if in database already exists user with email provided as @param
     * Email is unique in database, so can not exist two users with same email address
     *
     * @param email
     * @return true or false
     */
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * Wraps existsByEmail(String email) so that @param object can be user
     *
     * @param user which existence in database is checked by its id
     * @return
     */
    private boolean exists(User user) {
        return existsByEmail( user.getEmail() );
    }

    /**
     * Adds new user to the database. Before checks if it already exists. If exists, returns blank user.
     *
     * @param user
     * @return
     */
    public User addUser(final User user) {
        if( this.exists(user) ) {
            return new User();
        }

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    public void deleteUserById(final Long id) {
        userRepository.deleteById(id);
    }

    public User getUserById(final Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public User getUserByEmail(final String email) {
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }
}
