package com.yyp.helper.client.data;

import java.io.Serializable;

public class YogaTemplate implements Serializable {
    String key;
    String template;
    public YogaTemplate(){}
	public YogaTemplate(String key, String template) {
	    this.key = key;
	    this.template = template;
	}
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getTemplate() {
        return template;
    }
    public void setTemplate(String template) {
        this.template = template;
    }
    @Override
    public String toString() {
        return "YogaTemplate [key=" + key + ", template=" + template + "]";
    }
}