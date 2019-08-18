package com.yyp.helper.client.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.yyp.helper.client.data.YogaPose.SayAlignment;
import com.yyp.helper.client.data.YogaPosePool.ClassType;


public class YogaMasterData implements Serializable {

	public YogaMasterData() {}
	
	List<YogaPose> poses = new ArrayList<YogaPose>();
	List<YogaSet> sets = new ArrayList<YogaSet>();
	List<YogaIntention> intensions = new ArrayList<YogaIntention>();
	List<YogaPosePool> posePool = new ArrayList<YogaPosePool>();
	List<YogaTemplate> templates = new ArrayList<YogaTemplate>();
	List<YogaTransition> transisions = new ArrayList<YogaTransition>();
	List<CustomClass> customClasses = new ArrayList<CustomClass>();
	
	public List<CustomClass> getCustomClasses() {
		return customClasses;
	}
	

	public List<YogaSet> getSets() {
        return sets;
    }

    public void setSets(List<YogaSet> sets) {
        this.sets = sets;
    }

    public List<YogaTransition> getTransisions() {
        return transisions;
    }

    public void setTransisions(List<YogaTransition> transisions) {
        this.transisions = transisions;
    }

    public List<YogaPose> getPoses() {
		return poses;
	}

	public void setPoses(List<YogaPose> poses) {
		this.poses = poses;
	}

	public List<YogaIntention> getIntensions() {
        return intensions;
    }

    public void setIntensions(List<YogaIntention> intensions) {
        this.intensions = intensions;
    }

    public List<YogaPosePool> getPosePool() {
        return posePool;
    }

    public void setPosePool(List<YogaPosePool> posePool) {
        this.posePool = posePool;
    }

    public List<YogaTemplate> getTemplates() {
        return templates;
    }

    public void setTemplates(List<YogaTemplate> templates) {
        this.templates = templates;
    }

    @Override
    public String toString() {
        return "YogaMasterData [poses=" + poses + ", sets=" + sets + ", intensions=" + intensions + ", posePool="
                + posePool + ", templates=" + templates + ", transisions=" + transisions + "]";
    }
    
    
    public YogaTransition getTransition(ClassType classType, String from, String to) {
        String transKey = from + " - " + to;
        for(YogaTransition t: getTransisions()) {
            if(t.getClassType() == classType && t.getTransition().equals(transKey)) {
                return t;
            }
        }
        return new YogaTransition(from + "-" + to,null, classType);
    }
    
    /** Returns a new, uniuqe YogaPose object of the type
     *  referenced by key.
     *  
     * @param key
     * @return
     */
    public YogaPose lookupPose(String key) {
    	int maxTime=0;
    	String p[] = key.split(":");
    	if(p.length > 1) {
    		key = p[0];
    		maxTime = Integer.parseInt(p[1]);
    	}
    	SayAlignment sayAlign=SayAlignment.AUTO;
    	if(p.length > 2) {
    		sayAlign = SayAlignment.values()[Integer.parseInt(p[2])];
    	}
    	
        for(YogaPose pose: getPoses()) {
            if(pose.getPoseKey().equals(key)) {
            	YogaPose poseNew = new YogaPose(pose.getPoseKey(),pose.getPoseName(),pose.getNameSanskrit(), null,pose.getBreath(), pose.getImage(),pose.getUserName());
            	poseNew.setLeftRight(pose.isAsymetrical());
            	poseNew.setMaxTime(maxTime);
            	poseNew.setSayAlignment(sayAlign);
            	
            	return poseNew;
            }
        }
        YogaPose yp = new YogaPose();
        yp.setPoseName("Not found: " + key);
        yp.setPoseKey(key);
        System.out.println("Yoga pose not found: " + key);
        return yp;
    }

}
