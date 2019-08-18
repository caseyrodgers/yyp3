package com.yyp.helper.client.data;

import java.io.Serializable;

public class YogaPosePool implements Serializable {
    static public enum Type{WARMUP,MAIN,COOLDOWN,CLOSING};
    static public enum Level{BEGINNER,INTERMEDIATE,ADVANCED};
    static public enum ClassType{VINYASA,YIN};
    
    Type type;
    ClassType classType;
    Level level;
    String key;
    String bodyPart;
    
    public YogaPosePool() {}
    
    /** Create a new YogaPosePool and deduce the ClassType by
     *  looking at the key.  If it is in the format:
     *  v:key, then it will be parsed by the type taken from the
     *  first char.  In this case a Vinyasa
     *  
     * @param type
     * @param level
     * @param key
     */
    public YogaPosePool(Type type,Level level, String key) {
        this.type = type;
        this.level = level;

        String p[] = key.split("\\:");
        if(p.length == 1) {
            this.key = key;
            this.classType = ClassType.VINYASA;
        }
        else {
            this.key = p[1];
            this.classType = mapClassType(p[0]);
        }
    }
    
    public ClassType getClassType() {
        return classType;
    }

    public void setClassType(ClassType classType) {
        this.classType = classType;
    }

    public String getBodyPart() {
        return bodyPart;
    }

    public void setBodyPart(String bodyPart) {
        this.bodyPart = bodyPart;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    /** Return key, concated as follows:
     *  levelCLASSTYPEsetkey
     *  
     *  (CLASSTYPE is not added if Vinyasa)
     *  
     * @return
     */
    public String getKey() {
        String keyV = classType != ClassType.VINYASA?classType.name().toLowerCase().substring(0,1):"";
        keyV = (level.ordinal()+1) + keyV + key;
        return keyV;
    }

    public void setKey(String key) {
        this.key = key;
    }
    

    public static Level mapLevel(String level) {
        if(level.toLowerCase().startsWith("a")) {
            return Level.ADVANCED;
        }
        else if(level.toLowerCase().startsWith("i")) {
            return Level.INTERMEDIATE;
        }
        else { 
            return Level.BEGINNER;
        }
    }
    

    public static ClassType mapClassType(String cType) {
        if(cType != null && cType.toLowerCase().startsWith("y")) {
            return ClassType.YIN;
        }
        else {
            return ClassType.VINYASA;
        }
    }


    @Override
    public String toString() {
        return "YogaPosePool [type=" + type + ", classType=" + classType + ", level=" + level + ", key=" + key
                + ", bodyPart=" + bodyPart + "]";
    }
}
