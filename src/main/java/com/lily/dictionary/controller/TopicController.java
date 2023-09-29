package com.lily.dictionary.controller;

import com.lily.dictionary.dao.PostgresTopicDAO;
import com.lily.dictionary.model.Topic;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topics")
public class TopicController {

    private static PostgresTopicDAO postgresTopicDAO;

    public TopicController(PostgresTopicDAO postgresTopicDAO) {
        this.postgresTopicDAO = postgresTopicDAO;
    }

    @GetMapping("/{id}/all")
    public ResponseEntity getTopicsByUser(@PathVariable("id") int id) {
        List<Topic> topics = postgresTopicDAO.getTopicsByUser(id);
        return ResponseEntity.ok(topics);
    }

    @PostMapping("/create")
    public ResponseEntity createTopic(@RequestBody Topic topic) {
        postgresTopicDAO.createTopic(topic);
        return ResponseEntity.ok(topic);
    }

    @PostMapping("/update")
    public ResponseEntity updateTopic(@RequestBody Topic topic) {
        postgresTopicDAO.updateTopic(topic);
        return ResponseEntity.ok(topic);
    }

    @DeleteMapping("/{id}")
    public boolean deleteTopic(@PathVariable long id) {
        if (postgresTopicDAO.deleteTopic(id))
            return true;
        else return false;
    }
}
