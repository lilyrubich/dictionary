package com.lily.dictionary.dao;

import com.lily.dictionary.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO<T> {


    List<T> getUsers();

    void createUser(T t);

    T getUser(long id);

    void updateUser(T t);

    boolean deleteUser(long id);

}
