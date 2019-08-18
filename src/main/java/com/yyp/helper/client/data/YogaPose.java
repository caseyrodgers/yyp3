package com.yyp.helper.client.data;

import java.io.Serializable;

import com.yyp.helper.client.tts.PoseToCreate.Breath;

public class YogaPose implements Serializable {
	
	String userName;
	String poseName;
	String poseKey;
	boolean leftRight;
	int maxTime;
	Breath breath;

	SayAlignment sayAlignment;
	private String nameSanskrit;
	
	public enum SayAlignment{AUTO, ALWAYS, NEVER}
	
	public YogaPose() {}

	public YogaPose(String poseName, YogaPose pose) {
		this.poseName = poseName;
		this.breath = pose.getBreath();
		this.image = pose.getImage();
		
		this.maxTime = pose.getMaxTime();
		this.poseKey = pose.getPoseKey();
		this.leftRight = pose.isAsymetrical();
		this.sayAlignment = pose.getSayAlignment();
	}

	public YogaPose(String key,String name,String nameSanskrit, String isAsymetrical, Breath breath, String image, String userName) {
		this.poseName = name;
		this.nameSanskrit = nameSanskrit;
		this.breath = breath;
		this.image = image;
		this.sayAlignment = SayAlignment.AUTO;
		this.userName = userName;
		
		//System.out.println("Name: " + name + ", breath: " + breath);
		String p[] = key.split(":");
		if(p.length > 1) {
			maxTime = Integer.parseInt(p[1]);
			this.poseKey = p[0];
		}
		else {
			this.poseKey = key;
		}
		
		if(isAsymetrical != null && isAsymetrical.toLowerCase().startsWith("t")) {
		    this.leftRight = true;
		}
	}
	
	
	public String getNameSanskrit() {
		return nameSanskrit;
	}

	public void setNameSanskrit(String nameSanskrit) {
		this.nameSanskrit = nameSanskrit;
	}

	public void setSayAlignment(SayAlignment sayAlignment) {
		this.sayAlignment = sayAlignment;
	}

	public SayAlignment getSayAlignment() {
		return this.sayAlignment;
	}
	
	public Breath getBreath() {
		return breath;
	}

	public void setBreath(Breath breath) {
		this.breath = breath;
	}

	public int getMaxTime() {
		return maxTime;
	}

	public void setMaxTime(int maxTime) {
		this.maxTime = maxTime;
	}

	public boolean isLeftRight() {
		return leftRight;
	}

	
	public boolean isAsymetrical() {
        return leftRight;
    }

    public void setLeftRight(boolean leftRight) {
        this.leftRight = leftRight;
    }

    String image;
		

	public String getPoseKey() {
		return poseKey;
	}

	public void setPoseKey(String poseKey) {
		this.poseKey = poseKey;
	}


	public String getPoseName() {
		return poseName;
	}

	public void setPoseName(String poseName) {
		this.poseName = poseName;
	}

	public String getImage() {
    	return image;
    }

	public void setImage(String image) {
    	this.image = image;
    }
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof String) {
			String p[] = ((String) obj).split(":");
			String val=null;
			if(p.length > 1) {
				val=p[0];
			}
			else {
				val = (String)obj;
			}
			return getPoseKey().equals(val);
		}
		else {
			return super.equals(obj);
		}
	}

	public String getThumbnailImage() {
		return this.image;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "YogaPose [poseName=" + poseName + ", poseKey=" + poseKey
				+ ", leftRight=" + leftRight + ", maxTime=" + maxTime
				+ ", image=" + image + "]";
	}
}
