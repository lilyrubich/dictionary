package com.lily.dictionary.controller;


import com.lily.dictionary.dao.PostgresUserDAO;
import com.lily.dictionary.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private static PostgresUserDAO postgresUserDAO;

    public UserController(PostgresUserDAO postgresUserDAO) {
        this.postgresUserDAO = postgresUserDAO;
    }

    @GetMapping("/")
    public ResponseEntity getUsers() {
        List<User> users = postgresUserDAO.getUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/create")
    public ResponseEntity createUser(@RequestBody User user) {
        postgresUserDAO.createUser(user);
        return ResponseEntity.ok(user);
    }

    @GetMapping("")
    public ResponseEntity getUser(@RequestParam long id) {
        User user;
        user = postgresUserDAO.getUser(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/update")
    public ResponseEntity updateUser(@RequestBody User user) {
        postgresUserDAO.updateUser(user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public boolean deleteUser(@PathVariable long id) {
        if (postgresUserDAO.deleteUser(id))
            return true;
        else return false;
    }
}
