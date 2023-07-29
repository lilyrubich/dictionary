package com.lily.dictionary.model;

public class Word {

    private long id;
    private String foreign_word;
    private String translation;
    private long id_topic;
    private long id_user;

    public Word() {

    }

    public Word(String foreign_word, String translation, long id_topic, long id_user) {
        this.foreign_word = foreign_word;
        this.translation = translation;
        this.id_topic = id_topic;
        this.id_user = id_user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getForeign_word() {
        return foreign_word;
    }

    public void setForeign_word(String foreign_word) {
        this.foreign_word = foreign_word;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public long getId_topic() {
        return id_topic;
    }

    public void setId_topic(long id_topic) {
        this.id_topic = id_topic;
    }

    public long getId_user() {
        return id_user;
    }

    public void setId_user(long id_user) {
        this.id_user = id_user;
    }
}
