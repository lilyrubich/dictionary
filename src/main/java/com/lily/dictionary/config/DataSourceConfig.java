package com.lily.dictionary.config;

import com.lily.dictionary.dao.PostgresTopicDAO;
import com.lily.dictionary.dao.PostgresUserDAO;
import com.lily.dictionary.dao.PostgresWordDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
public class DataSourceConfig {


    @Bean
    public PostgresUserDAO userDAO(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new PostgresUserDAO(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Bean
    public PostgresTopicDAO topicDAO(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new PostgresTopicDAO(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Bean
    public PostgresWordDAO wordDAO(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new PostgresWordDAO(jdbcTemplate, namedParameterJdbcTemplate);
    }

}
