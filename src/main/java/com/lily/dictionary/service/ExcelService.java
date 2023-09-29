package com.lily.dictionary.service;

import com.lily.dictionary.helper.ExcelHelper;
import com.lily.dictionary.model.Word;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;

@Service
public class ExcelService {

    public ByteArrayInputStream load(List<Word> words) {

        ByteArrayInputStream in = ExcelHelper.tutorialsToExcel(words);
        return in;
    }

}
