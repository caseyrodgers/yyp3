package com.yyp.helper.client.tts;

/** Represents all the data needed to create a new 
 *  pose.
 *  
 * @author casey
 *
 */
public class PoseToCreate {

	public enum Breath {
		INHALE("I"),EXHALE("E"), NONE(null);
		
		private String label;
		private Breath(String label) {
			this.label = label;
		}
		public String getLabel() {
			return label;
		}
		public static Breath lookupBreath(String label) {
			if(label == null || label.length() == 0) {
				return NONE;
			}
			else if(label.equals(INHALE.getLabel())) {
				return INHALE;
			}
			else if(label.equals(EXHALE.getLabel())) {
				return EXHALE;
			}
			else {
				return NONE;
			}
		}
	};
	
	private String nameEnglish;
	private String key;
	private String nameSanskrit;
	private String textAlign;
	private String imageUrl;
	private boolean asymmetrical;
	private Breath breath;

	public PoseToCreate(String nameEnglish, String nameSanskrit, String imageUrl, String textAlign, Breath sayBreath, boolean isAsymmetrical) {
		this.nameEnglish = nameEnglish;
		this.nameSanskrit = nameSanskrit;
		this.imageUrl = imageUrl;
		this.textAlign = textAlign;
		this.breath = sayBreath;
		this.asymmetrical = isAsymmetrical;
	}

	public boolean isAsymmetrical() {
		return asymmetrical;
	}

	public void setAsymmetrical(boolean asymmetrical) {
		this.asymmetrical = asymmetrical;
	}



	public Breath getBreath() {
		return breath;
	}

	public void setBreath(Breath breath) {
		this.breath = breath;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getNameEnglish() {
		return nameEnglish;
	}

	public void setNameEnglish(String nameEnglish) {
		this.nameEnglish = nameEnglish;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getNameSanskrit() {
		return nameSanskrit;
	}

	public void setNameSanskrit(String nameSanskrit) {
		this.nameSanskrit = nameSanskrit;
	}

	public String getTextAlign() {
		return textAlign;
	}

	public void setTextAlign(String textAlign) {
		this.textAlign = textAlign;
	}
}
