package com.lily.dictionary.dao;

import com.lily.dictionary.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;


public class PostgresUserDAO implements UserDAO<User> {

    private static final Logger log = LoggerFactory.getLogger(PostgresUserDAO.class);
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public PostgresUserDAO(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    RowMapper<User> rowMapper = (rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setUser_name(rs.getString("user_name"));
        user.setPassword(rs.getString("password"));
        return user;
    };

    @Override
    public List<User> getUsers() {
        String sql = "select * from dictionary.user";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public void createUser(User user) {
        String sql = "insert into dictionary.user (user_name, password) values (?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        int insert = jdbcTemplate.update(
                connection -> {
                    PreparedStatement prepareStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    prepareStatement.setString(1, user.getUser_name());
                    prepareStatement.setString(2, user.getPassword());
                    return prepareStatement;
                }, keyHolder);

        if (insert == 1) {
            user.setId(Long.parseLong(keyHolder.getKeys().get("id").toString()));
            log.info("New user created: " + user.getUser_name());
        }
    }


    @Override
    public User getUser(long id) {
        String sql = "select * from dictionary.user where id=:id";
        return namedParameterJdbcTemplate.query(sql, Map.of("id", id), rowMapper).get(0);
    }

    @Override
    public void updateUser(User user) {
        String sql = "update dictionary.user set user_name=:user_name, password=:password where id=:id";
        int update = namedParameterJdbcTemplate.update(sql, Map.of(
                "user_name", user.getUser_name(),
                "password", user.getPassword(),
                "id", user.getId()));

        if (update == 1) {
            log.info("User with id = " + user.getId() + " updated");
        }
    }

    @Override
    public boolean deleteUser(long id) {
        String sqlDelWord = "delete from dictionary.word where id_user=?";
        String sqlDelTopic = "delete from dictionary.topic where id_user=?";
        String sqlDelUser = "delete from dictionary.user where id=?";
        int deleteWord = jdbcTemplate.update(sqlDelWord, id);
        int deleteTopic = jdbcTemplate.update(sqlDelTopic, id);
        if (jdbcTemplate.update(sqlDelUser, id) >= 1) {
            log.info("User with id = " + id + " and " + deleteTopic + " topics and " + deleteWord + " words deleted");
            return true;
        } else return false;
    }

}
