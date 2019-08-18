package com.yyp.helper.client.data;

import java.io.Serializable;

import com.yyp.helper.client.data.YogaPosePool.Type;

/** Composite of a YogaSet and Type
 * 
 * @author casey
 *
 */
public class YogaSetAssigned implements Serializable {
    YogaSet set;
    Type type;
    public YogaSetAssigned(Type type, YogaSet set) {
        this.type = type;
        this.set = set;
    }
    public YogaSet getSet() {
        return set;
    }
    public void setSet(YogaSet set) {
        this.set = set;
    }
    public Type getType() {
        return type;
    }
    public void setType(Type type) {
        this.type = type;
    }
    @Override
    public String toString() {
        return "YogaSetAssigned [set=" + set + ", type=" + type + "]";
    }
}
