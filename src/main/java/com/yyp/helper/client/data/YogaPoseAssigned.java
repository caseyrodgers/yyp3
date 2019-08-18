package com.yyp.helper.client.data;

import java.io.Serializable;
import java.util.List;

import com.yyp.helper.client.data.YogaPosePool.Type;

public class YogaPoseAssigned implements Serializable {
	
    YogaPose pose;
    Type type;
    String info;
    int numSeconds;

    public YogaPoseAssigned() {}
    
	public YogaPoseAssigned(YogaPose pose, Type type) {
	    this.pose = pose;
	    this.type = type;
	}

    public YogaPose getPose() {
        return pose;
    }

    public void setPose(YogaPose pose) {
        this.pose = pose;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }


	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
	
    @Override
	public String toString() {
		return "YogaPoseAssigned [pose=" + pose + ", type=" + type + ", info="
				+ info + ", numSeconds=" + numSeconds + "]";
	}

	public int getNumSeconds() {
		return numSeconds;
	}

	public void setNumSeconds(int numSeconds) {
		this.numSeconds = numSeconds;
	}

	
	static public int getPoseCountOfType(Type type, List<YogaPoseAssigned> poses) {
		int cnt=0;
		for(YogaPoseAssigned p: poses) {
			if(p.getType() == type) {
				cnt++;
			}
		}
		return cnt;
	}
}
