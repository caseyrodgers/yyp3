package com.yyp.helper.client.data;

import java.io.Serializable;

/** Represents a single yoga pose and its source DSL.  Meaning what
 * part of the DSL was used to create this.  For example:
 * 
 * #4(dd)  would result in 4 down dogs, but they would each have the
 * save src.  
 * 
 * $RL(b,kc) - dd
 * b and kc would each have the same src
 * 
 * @author casey
 *
 */
public class YogaPostureExpanded implements Serializable {
	private String dslSrc;
	private int dslNum;
	private String posture;
	
	public YogaPostureExpanded(String dslSrc, int dslNum, String posture) {
		this.dslSrc = dslSrc;
		this.dslNum   = dslNum;
		this.posture = posture;
	}

	public String getDslSrc() {
		return dslSrc;
	}

	public void setDslSrc(String dslSrc) {
		this.dslSrc = dslSrc;
	}

	public int getDslNum() {
		return dslNum;
	}

	public void setDslNum(int dslNum) {
		this.dslNum = dslNum;
	}

	public String getPosture() {
		return posture;
	}

	public void setPosture(String posture) {
		this.posture = posture;
	}

	@Override
	public String toString() {
		return "YogaPostureExpanded [dslSrc=" + dslSrc + ", dslNum=" + dslNum
				+ ", posture=" + posture + "]";
	}
}
