package com.yyp.helper.client.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import com.yyp.helper.client.data.YogaPosePool.ClassType;
import com.yyp.helper.client.data.YogaPosePool.Level;
import com.yyp.helper.client.data.YogaPosePool.Type;

public class YogaClass implements Serializable {
	YogaMasterData masterData;
	Level level;
	ClassType classType;
	String part;
	String keyWu;
	String keyM;
	String keyCd;
	String keyClose;

	YogaSetAssigned wuSet;
	YogaSetAssigned mSet;
	YogaSetAssigned cdSet;
	YogaSetAssigned closeSet;

	String classDsl;
	String className;

	Logger logger = Logger.getLogger(YogaClass.class.getName());
	
	List<YogaPoseAssigned> poses = new ArrayList<YogaPoseAssigned>();

	public YogaClass(YogaMasterData data, ClassType classType, Level level,	String part) {
		this.masterData = data;
		this.classType = classType;
		this.level = level;
		this.part = part;

		this.className = "Auto Generated " + level.toString() + " Class";
		extractKeySetsToUse();
		assert keyWu != null && keyM != null && keyCd != null
				&& keyClose != null;

		readPosturesFromSets();
		lookupPostures();
		
		
		classDsl 	= wuSet.getSet().getPostures() + " - " + mSet.getSet().getPostures() + " - " + cdSet.getSet().getPostures() +  (closeSet!=null?" - " + closeSet.getSet().getPostures():"");

	}

	public YogaClass(YogaMasterData data, String classDsl) {
		this.masterData = data;
		this.classDsl = classDsl;

		YogaSet set = new YogaSet("", "custom", classDsl, null);
		poses.addAll(createYogaAssigned(Type.MAIN, new YogaSetAssigned(Type.MAIN, set)));
	}

	public List<YogaPoseAssigned> getPoses() {
		return poses;
	}
	
	public String getClassDsl() {
		return classDsl;
	}

	/**
	 * Find the yoga poses for each specific phase of the class.
	 */
	private void readPosturesFromSets() {
		wuSet = getKeySet(Type.WARMUP, keyWu);
		mSet = getKeySet(Type.MAIN, keyM);
		cdSet = getKeySet(Type.COOLDOWN, keyCd);
		closeSet = getKeySet(Type.CLOSING, keyClose);
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	private void lookupPostures() {
		poses.clear();
		poses.addAll(createYogaAssigned(Type.WARMUP, wuSet));
		poses.addAll(createYogaAssigned(Type.MAIN, mSet));
		poses.addAll(createYogaAssigned(Type.COOLDOWN, cdSet));
		
		if(closeSet != null) {
			poses.addAll(createYogaAssigned(Type.CLOSING, closeSet));
		}
	}

	public void setPosesFromDsl(String poseDsl) {
		poses.clear();

		logger.info("Assigning custom poses: " + poseDsl);
		List<YogaPoseAssigned> assigned = new ArrayList<YogaPoseAssigned>();
		YogaSet set = new YogaSet("", "custom", poseDsl, Type.MAIN);
		for (YogaPose p : set.getPoses(masterData)) {
			assigned.add(new YogaPoseAssigned(p, Type.MAIN));
		}
		poses.addAll(assigned);
	}

	private List<YogaPoseAssigned> createYogaAssigned(Type type,YogaSetAssigned set) {
		//System.out.println("Assigning poses: " + type + ", " + set.getSet().getSetKeys());
		List<YogaPoseAssigned> assigned = new ArrayList<YogaPoseAssigned>();
		for (YogaPose p : set.getSet().getPoses(masterData)) {
			assigned.add(new YogaPoseAssigned(p, type));
		}
		return assigned;
	}

	private YogaSetAssigned getKeySet(Type type, String key) {

		for (YogaSet set : masterData.getSets()) {
			if (set.getType() == type) {
				if (set.isMatch(key)) {
					return new YogaSetAssigned(type, set);
				}
			}
		}

		System.out.println("Could not find set: " + type + ", " + key);
		return null;
	}

	private String chooseRandKeySet(Level level, ClassType classType, Type type) {
		List<YogaPosePool> pool = new ArrayList<YogaPosePool>();
		for (YogaPosePool pp : masterData.getPosePool()) {

			if (classType == pp.getClassType() && pp.getLevel() == level
					&& pp.getType() == type) {
				pool.add(pp);
			}
		}
		if (pool.size() > 0) {

//			// force always return first
//			if (true) {
//				return pool.get(pool.size() - 1).getKey();
//			}

			int which = 0;
			try {
				which = new Random().nextInt(pool.size());
			} catch (Throwable th) {
				which = new Random().nextInt(pool.size());
			}
			return pool.get(which).getKey();
		} else {
			System.out.println("No key set found for: " + type);
			return null;
		}
	}

	/**
	 * Extract the warmup-up, main and cooldown keys to use from master db.
	 * Stores values in keyWu, keyM and keyCd;
	 */
	private void extractKeySetsToUse() {
		keyWu = chooseRandKeySet(this.level, this.classType, Type.WARMUP);
		keyM = chooseRandKeySet(this.level, this.classType, Type.MAIN);
		keyCd = chooseRandKeySet(this.level, this.classType, Type.COOLDOWN);
		keyClose = chooseRandKeySet(this.level, this.classType, Type.CLOSING);

		logger.info("YogaClass: " + toString());
	}

	public String toString() {
		return "wu=" + keyWu + ", m=" + keyM + ", cd="	+ keyCd + ", keyClose=" + keyClose;
	}

	public String getClassMinutes() {
//		int totalTime = MySettings.getInstance().getClassLength();
//		ClassTiming timing = new ClassTiming(MySettings.getProperty("classLength"), totalTime,
//				YogaPoseAssigned.getPoseCountOfType(Type.WARMUP,poses),
//				YogaPoseAssigned.getPoseCountOfType(Type.MAIN,poses),
//				YogaPoseAssigned.getPoseCountOfType(Type.COOLDOWN,poses),
//				YogaPoseAssigned.getPoseCountOfType(Type.CLOSING,poses)
//				);
//
//		int count=0;
//		int totSecs=0;
//		int totBreaths=0;
//		for(final YogaPoseAssigned pa: poses) {
//			count++;
//            totSecs += timing.getNumberOfSeconds(pa);
//            totBreaths += timing.getNumberOfBreaths(pa);
//		}
		
		int totSecs = 100;
		int totBreaths = 100;
		return "Minutes: " + (totSecs/60) + ", breaths: " + totBreaths;
	}
}
