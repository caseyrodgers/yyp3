package com.yyp.helper.client.data;

import java.io.Serializable;

import com.yyp.helper.client.data.YogaPosePool.ClassType;


public class YogaTransition implements Serializable {
    
    String transition;
    String queue;
    ClassType classType;

    public YogaTransition() {}
    
    public YogaTransition(String transition, String queue, ClassType classType) {
        this.transition = transition;
        this.queue = queue;
        this.classType = classType;
    }

    public String getTransition() {
        return transition;
    }

    public void setTransition(String transision) {
        this.transition = transision;
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public ClassType getClassType() {
        return classType;
    }

    public void setClassType(ClassType classType) {
        this.classType = classType;
    }

    @Override
    public String toString() {
        return "YogaTransition [transition=" + transition + ", queue=" + queue + ", classType=" + classType + "]";
    }
}
