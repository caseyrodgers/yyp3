package com.yyp.helper.client.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.yyp.helper.client.data.YogaPosePool.Type;

public class YogaSet implements Serializable {
    Type level;
    String label;
    String postures;
    String setKeys;

    // Logger logger = Logger.getLogger(YogaSet.class.getName());
    
    public YogaSet() {}
    
    public YogaSet(String setKeys, String label, String postures, Type level) {
        this.setKeys = setKeys;
        this.label = label;
        this.postures = postures;
        this.level = level;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Type getType() {
        return level;
    }

    public void setType(Type level) {
        this.level = level;
    }

    public String getPostures() {
        return postures;
    }
    
    /** Return list of posture keys after template expansion
     * 
     * @param data
     * @return
     */
    public List<String> getPostureList(YogaMasterData data) {
        List<String> postureList = new ArrayList<String>();
        String ps[] = replaceTemplates(data, postures).split("-");
        for(String p: ps) {
            postureList.add(p.trim());
        }
        return postureList;
    }
    
    private String replaceTemplates(YogaMasterData data, String postures) {
        try {
	        int si=0;
	        while((si = postures.indexOf("$T")) > -1) {
	            int se = postures.indexOf(")", si);
	            if(se > -1) {
	                String templateKey = postures.substring(si+3, se);
	                String template = getTemplate(data,templateKey);
	                if(template != null) { 
	                	postures = postures.replace("$T(" + templateKey + ")", template);
	                }
	                else {
	                	postures = "UNKNOWN TEMPLATE '" + templateKey + "'";
	                }
	            }
	        }
        }
        catch(Exception e) {
        	e.printStackTrace();
        	// logger.log(Level.SEVERE, "Error replacing templates", e);
        }
        return postures;
    }
    
    
    public String getTemplate(YogaMasterData data, String key) {
        for(YogaTemplate t: data.getTemplates() ) {
            if(t.getKey().equals(key)) {
                return t.getTemplate();
            }
        }
        return null;
    }
        

    public void setPostures(String postures) {
        this.postures = postures;
    }

    
    /** Parse yoga pose sequence
     * 
     *  ab - cd - fdf - $LR(ce, dw, ug) -
     * @return
     */
    public List<YogaPose> getPoses(YogaMasterData data) {
        List<YogaPose> poses = new ArrayList<YogaPose>();
        
        for(String p: getPostureList(data)) {
            if(p.startsWith("$")) {
                poses.addAll(processDirective(p, data));
            }
            else if(p.startsWith("#")) {
                poses.addAll(processRepeat(p, data));
            }
            else {
                YogaPose pose = data.lookupPose(p);
                poses.add(pose);
            }
        }
        return poses;
    }
    
    
    private List<YogaPose> processDirective(String directive, YogaMasterData data) {
        List<YogaPose> poses = new ArrayList<YogaPose>();
        if(directive.toLowerCase().startsWith("$rl")) {
            int begList = directive.indexOf("(");
            int endList = directive.indexOf(")");
            String list = directive.substring(begList+1,endList);
            
            String pos[] = list.split(",");
            
            // once for left
            for(String p: pos) {
                p = p.trim();
                YogaPose pose = data.lookupPose(p);
                String tag = pose.isAsymetrical()?" (Right)":"";
                YogaPose poseToAdd = new YogaPose(pose.getPoseName() + tag, pose);
                poses.add( poseToAdd);
            }
            
            // once for right
            for(String p: pos) {
                p = p.trim();
                YogaPose pose = data.lookupPose(p);
                String tag = pose.isAsymetrical()?" (Left)":"";
                YogaPose poseToAdd = new YogaPose(pose.getPoseName() + tag, pose);
                poses.add( poseToAdd);
            }
        }
        return poses;
    }
    
    private List<YogaPose> processRepeat(String directive, YogaMasterData data) {
        List<YogaPose> poses = new ArrayList<YogaPose>();
 
        try {
            int begList = directive.indexOf("(");
            int endList = directive.indexOf(")");
            
            String stimes = directive.substring(1, begList);
            int times = Integer.parseInt(stimes);
            
            String list = directive.substring(begList+1,endList);
                
            String pos[] = list.split(",");
            for(int i=0;i<times;i++) {
                // once for left
                for(String p: pos) {
                    p = p.trim();
                    YogaPose pose = data.lookupPose(p);
                    YogaPose np = new YogaPose(pose.getPoseName() + " (count=" + (i+1) + ")", pose);
                    poses.add( np );
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return poses;
    }
    


    public String getSetKeys() {
        return setKeys;
    }

    /** See if key is in this set's key list
     * 
     * @param key
     * @return
     */
    public boolean isMatch(String key) {
        if(setKeys != null) {
            for(String sk: setKeys.split(" ")) {
                if(sk.equals(key)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /** strip off trailing ordinal on value
     * 
     * @param s
     * @return
     */
    private String stripOffOrdinal(String s) {
        if(true)
            return s;
        
       for(int l=s.length();l>0;l--) {
           if(!Character.isDigit(s.charAt(l-1))) {
               return s.substring(l-1);
           }
       }
       return s;
    }

    public void setSetKeys(String setKeys) {
        this.setKeys = setKeys;
    }

    @Override
    public String toString() {
        return "YogaSet [level=" + level + ", label=" + label + ", postures=" + postures + ", setKeys=" + setKeys + "]";
    }
    
}
