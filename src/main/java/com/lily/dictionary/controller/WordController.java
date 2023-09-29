package com.lily.dictionary.controller;

import com.lily.dictionary.dao.PostgresWordDAO;
import com.lily.dictionary.model.Word;
import com.lily.dictionary.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/words")
public class WordController {

    @Autowired
    ExcelService fileService;
    private static PostgresWordDAO postgresWordDAO;

    public WordController(PostgresWordDAO postgresWordDAO) {
        this.postgresWordDAO = postgresWordDAO;
    }

    @GetMapping("/getDictionary")
    public ResponseEntity getDictionaryByUser(@RequestParam long id_user) {
        List<Word> words = postgresWordDAO.getDictionaryByUser(id_user);
        return ResponseEntity.ok(words);
    }

    @GetMapping("/getDictionaryByTopic")
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

    @GetMapping("/downloadDictionary")
    public ResponseEntity getFile(@RequestParam long id_user) {
        String filename = "dictionary.xlsx";
        List<Word> words = postgresWordDAO.getDictionaryByUser(id_user);
        //ResponseEntity.ok(words);
        InputStreamResource file = new InputStreamResource(fileService.load(words));

        return (ResponseEntity) ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
    }

}
