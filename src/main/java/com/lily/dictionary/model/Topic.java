package com.lily.dictionary.model;

public class Topic {

    private Long id;
    private String topic_name;
    private Long id_user;

    public Topic(String topic_name, Long id_user) {
        this.topic_name = topic_name;
        this.id_user = id_user;
    }

    public Topic() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopic_name() {
        return topic_name;
    }

    public void setTopic_name(String topic_name) {
        this.topic_name = topic_name;
    }

    public Long getId_user() {
        return id_user;
    }

    public void setId_user(Long id_user) {
        this.id_user = id_user;
    }
}
