package com.lily.dictionary.controller;

import com.lily.dictionary.dao.PostgresWordDAO;
import com.lily.dictionary.model.Word;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/words")
public class WordController {

    private static PostgresWordDAO postgresWordDAO;

    public WordController(PostgresWordDAO postgresWordDAO) {
        this.postgresWordDAO = postgresWordDAO;
    }

    @GetMapping
    public ResponseEntity getDictionaryByUser(@RequestParam long id_user) {
        List<Word> words = postgresWordDAO.getDictionaryByUser(id_user);
        return ResponseEntity.ok(words);
    }

    @GetMapping
    public ResponseEntity getDictionaryByTopic(@RequestParam long id_topic) {
        List<Word> words = postgresWordDAO.getDictionaryByTopic(id_topic);
        return ResponseEntity.ok(words);
    }

    @PostMapping("/create")
    public ResponseEntity createWord(@RequestBody Word word) {
        postgresWordDAO.createWord(word);
        return ResponseEntity.ok(word);
    }

    @PostMapping("/update")
    public ResponseEntity updateWord(@RequestBody Word word) {
        postgresWordDAO.updateWord(word);
        return ResponseEntity.ok(word);
    }

    @DeleteMapping("/{id}")
    public boolean deleteWord(@PathVariable long id) {
        if (postgresWordDAO.deleteWord(id))
            return true;
        else return false;
    }

    @PostMapping("/addToTopic")
    public ResponseEntity addWordToTopic(@RequestBody Word word) {
        postgresWordDAO.addWordToTopic(word);
        return ResponseEntity.ok(word);
    }
}
