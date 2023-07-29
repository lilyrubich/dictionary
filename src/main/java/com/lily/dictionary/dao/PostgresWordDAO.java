package com.lily.dictionary.dao;

import com.lily.dictionary.model.Word;
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

public class PostgresWordDAO implements WordDAO<Word> {

    private static final Logger log = LoggerFactory.getLogger(PostgresUserDAO.class);
    JdbcTemplate jdbcTemplate;
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public PostgresWordDAO(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    RowMapper<Word> rowMapper = (rs, rowNum) -> {
        Word word = new Word();
        word.setId(rs.getLong("id"));
        word.setForeign_word(rs.getString("topic_name"));
        word.setTranslation(rs.getString("translation"));
        word.setId_topic(rs.getLong("id_topic"));
        return word;
    };

    @Override
    public List<Word> getDictionaryByTopic(long id) {
        String sql = "select * from dictionary.word where id_topic = :id";
        return namedParameterJdbcTemplate.query(sql, Map.of("id", id), rowMapper);
    }

    @Override
    public List<Word> getDictionaryByUser(long id) {
        String sql = "select * from dictionary.word where id_user = :id";
        return namedParameterJdbcTemplate.query(sql, Map.of("id", id), rowMapper);
    }

    @Override
    public void createWord(Word word) {
        String sql = "insert into dictionary.word (foreign_word, translation, id_user) values (?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        int insert = jdbcTemplate.update(
                connection -> {
                    PreparedStatement prepareStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    prepareStatement.setString(1, word.getForeign_word());
                    prepareStatement.setString(2, word.getTranslation());
                    prepareStatement.setLong(3, word.getId_user());
                    return prepareStatement;
                }, keyHolder);

        if (insert == 1) {
            word.setId(Long.parseLong(keyHolder.getKeys().get("id").toString()));
            log.info("New topic created: " + word.getForeign_word() + " for user id = " + word.getId_user());
        }
    }

    @Override
    public void updateWord(Word word) {
        String sql = "update dictionary.word set foreign_word=:foreign_word, translation=:translation, id_topic=:id_topic where id_user=:id_user";
        int update = namedParameterJdbcTemplate.update(sql, Map.of(
                "foreign_word", word.getForeign_word(),
                "translation", word.getTranslation(),
                "id_topic", word.getId_topic(),
                "id_user", word.getId_user()));

        if (update == 1) {
            log.info("Word with id = " + word.getId() + " updated");
        }
    }

    @Override
    public boolean deleteWord(long id) {
        String sql = "delete from dictionary.word where id=?";
        if (jdbcTemplate.update(sql, id) >= 1) {
            log.info("Word with id = " + id + " deleted");
            return true;
        } else return false;
    }

    @Override
    public void addWordToTopic(Word word) {
        String sql = "update dictionary.word set id_topic=:id_topic where id=:id";
        int update = namedParameterJdbcTemplate.update(sql, Map.of("id_topic", word.getId_topic()));

        if (update == 1) {
            log.info("Word with id = " + word.getId() + " updated");
        }
    }

}
