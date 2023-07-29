package com.lily.dictionary.dao;

import java.util.List;
import java.util.Optional;

public interface TopicDAO<T> {

    List<T> getTopicsByUser(long id);

    void createTopic(T t);

    T getTopic(long id);

    void updateTopic(T t);

    boolean deleteTopic(long id);

}
