package com.yyp.helper.client.data;


public class CustomClass {
	
	String className;
	String classDsl;
	boolean userCreated;
	
	public CustomClass() {}
	
	public CustomClass(String className, String classDsl) {
		this(className, classDsl, false);
	}
	
	public CustomClass(String className, String classDsl, boolean userCreated) {
		this.className = className;
		this.classDsl = classDsl;
		this.userCreated = userCreated;
	}

	public String getClassName() {
		return className;
	}

	public boolean isUserCreated() {
		return userCreated;
	}

	public void setUserCreated(boolean userCreated) {
		this.userCreated = userCreated;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getClassDsl() {
		return classDsl;
	}

	public void setClassDsl(String classDsl) {
		this.classDsl = classDsl;
	}

	@Override
	public String toString() {
		return "CustomClass [className=" + className + ", classDsl=" + classDsl
				+ ", userCreated=" + userCreated + "]";
	}
}
