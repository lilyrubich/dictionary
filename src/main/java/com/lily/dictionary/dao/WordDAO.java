package com.lily.dictionary.dao;

import java.util.List;

public interface WordDAO<T> {

    List<T> getDictionaryByTopic(long id);

    List<T> getDictionaryByUser(long id);

    void createWord(T t);

    void updateWord(T t);

    boolean deleteWord(long id);

    void addWordToTopic(T t);
}
