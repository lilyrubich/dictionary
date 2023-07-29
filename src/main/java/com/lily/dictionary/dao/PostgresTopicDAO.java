package com.lily.dictionary.dao;


import com.lily.dictionary.model.Topic;
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

public class PostgresTopicDAO implements TopicDAO<Topic> {

    private static final Logger log = LoggerFactory.getLogger(PostgresUserDAO.class);
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private JdbcTemplate jdbcTemplate;

    public PostgresTopicDAO(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    RowMapper<Topic> rowMapper = (rs, rowNum) -> {
        Topic topic = new Topic();
        topic.setId(rs.getLong("id"));
        topic.setTopic_name(rs.getString("topic_name"));
        topic.setId_user(rs.getLong("id_user"));
        return topic;
    };

    @Override
    public List<Topic> getTopicsByUser(long id) {
        String sql = "select * from dictionary.topic where id_user = :id";
        return namedParameterJdbcTemplate.query(sql, Map.of("id", id), rowMapper);
    }

    @Override
    public void createTopic(Topic topic) {
        String sql = "insert into dictionary.topic (topic_name, id_user) values (?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        int insert = jdbcTemplate.update(
                connection -> {
                    PreparedStatement prepareStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    prepareStatement.setString(1, topic.getTopic_name());
                    prepareStatement.setLong(2, topic.getId_user());
                    return prepareStatement;
                }, keyHolder);

        if (insert == 1) {
            topic.setId(Long.parseLong(keyHolder.getKeys().get("id").toString()));
            log.info("New topic created: " + topic.getTopic_name() + " for user id = " + topic.getId_user());
        }
    }

    @Override
    public Topic getTopic(long id) {
        String sql = "select * from dictionary.topic where id=:id";
        return namedParameterJdbcTemplate.query(sql, Map.of("id", id), rowMapper).get(0);
    }

    @Override
    public void updateTopic(Topic topic) {
        String sql = "update dictionary.topic set topic_name=:topic_name, id_user=:id_user where id=:id";
        int update = namedParameterJdbcTemplate.update(sql, Map.of(
                "topic_name", topic.getTopic_name(),
                "id_user", topic.getId_user(),
                "id", topic.getId()));

        if (update == 1) {
            log.info("Topic with id = " + topic.getId() + " updated");
        }
    }

    @Override
    public boolean deleteTopic(long id) {
        String sqlDelWord = "delete from dictionary.word where id_topic=?";
        String sqlDelTopic = "delete from dictionary.topic where id=:id";
        int deleteWord = namedParameterJdbcTemplate.update(sqlDelWord, Map.of("id_topic", id));
        if (namedParameterJdbcTemplate.update(sqlDelTopic, Map.of("id", id)) >= 1) {
            log.info("Topic with id = " + id + " and " + deleteWord + " words deleted");
            return true;
        } else return false;
    }

}
