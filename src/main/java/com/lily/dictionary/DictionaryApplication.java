package com.lily.dictionary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DictionaryApplication {

	//private static DAO<User> dao;

	//public DictionaryApplication(DAO<User> dao){
	//	this.dao = dao;
	//}

	public static void main(String[] args) {

		SpringApplication.run(DictionaryApplication.class, args);

		//create user
		//User user = new User("SpringBootUser", "SpringPassword");
		//dao.create(user);

		//read users
		//List<User> users = dao.list();
		//users.forEach(System.out::println);
	}

}
