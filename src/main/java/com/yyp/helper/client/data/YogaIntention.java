package com.yyp.helper.client.data;

import java.io.Serializable;

public class YogaIntention implements Serializable {
    static public enum Level{BEGINNER,INTERMEDIATE,EXPERT};

    Level level;
    String bodyPart;
    String key;
    String intention;
    String combination;
    
    public YogaIntention() {}
    
    public YogaIntention(String bodyPart, String key, String intention, String combination) {
        this.bodyPart = bodyPart;
        this.key = key;
        this.intention = intention;
        this.combination = combination;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public String getBodyPart() {
        return bodyPart;
    }

    public void setBodyPart(String bodyPart) {
        this.bodyPart = bodyPart;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getIntention() {
        return intention;
    }

    public void setIntention(String intention) {
        this.intention = intention;
    }

    public String getCombination() {
        return combination;
    }

    public void setCombination(String combination) {
        this.combination = combination;
    }

    @Override
    public String toString() {
        return "YogaIntention [level=" + level + ", bodyPart=" + bodyPart + ", key=" + key + ", intention=" + intention
                + ", combination=" + combination + "]";
    }
    
}
