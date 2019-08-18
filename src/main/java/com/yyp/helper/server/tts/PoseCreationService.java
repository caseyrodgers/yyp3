package com.yyp.helper.server.tts;

import org.apache.log4j.Logger;

import com.yyp.helper.client.data.YogaPose;
import com.yyp.helper.client.tts.PoseToCreate;
import com.yyp.helper.client.tts.PoseToCreate.Breath;
import com.yyp.helper.server.YogaServerDao;

public class PoseCreationService {
	
	static Logger __logger = Logger.getLogger(PoseCreationService.class);
	
	public YogaPose createPose(PoseToCreate poseToCreate) throws Exception {
		__logger.info("Creating new yoga pose: " + poseToCreate);

		
		YogaPose newPose = YogaServerDao.getInstance().createNewPose(poseToCreate.getKey(), poseToCreate.getNameEnglish(),poseToCreate.isAsymmetrical(), poseToCreate.getNameSanskrit(), poseToCreate.getBreath(), poseToCreate.getImageUrl(),poseToCreate.getTextAlign());

		/** install new pose audio */
		new AudioCreator().createPoseName(newPose.getPoseKey(), poseToCreate.getNameEnglish(), poseToCreate.getNameSanskrit());
		new AudioCreator().createPoseAlignment(newPose.getPoseKey(),poseToCreate.isAsymmetrical(), poseToCreate.getTextAlign());
		
		
		return newPose;
	}
	
	
	

	public static void main(String[] args) {
		try {
			PoseCreationService poseCreator = new PoseCreationService();
			poseCreator.createPose(new PoseToCreate("My Pose Name", "Ecaka posanim", "http://the_pose_image.png","The Pose Alignment Text",Breath.EXHALE,true));
			
			__logger.info("Pose created!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.exit(0);
	}
	

}
