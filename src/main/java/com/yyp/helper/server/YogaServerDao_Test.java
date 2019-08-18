package com.yyp.helper.server;


import com.yyp.helper.client.data.YogaClass;
import com.yyp.helper.client.data.YogaMasterData;
import com.yyp.helper.client.data.YogaPosePool.ClassType;
import com.yyp.helper.client.data.YogaPosePool.Level;


public class YogaServerDao_Test {
	static public void main(String [] args) {
		try {
			YogaMasterData data = YogaServerDao.getInstance().getPostureDatabase(1);
            System.out.println(String.format("Poses: %d, sets: %d, intentions; %d, pose pool: %d, templates: %d", data.getPoses().size(), data.getSets().size(), data.getIntensions().size(), data.getPosePool().size(),data.getTemplates().size()));
			
			assert data.getPoses().size() > 0;
			assert data.getSets().size() > 0;
			assert data.getIntensions().size() > 0;
			
			assert data.getSets().get(0).getPostures() != null;
            assert data.getTemplates().size() > 0;
            assert data.getIntensions().size() > 0;
			
			for(ClassType classType: ClassType.values()) {
    			assert data.getPosePool().get(2).getKey() != null;
    			
    			YogaClass yogaClass = new YogaClass(data,classType,Level.BEGINNER,"Hips");
                assert yogaClass.getPoses().size() > 0;
			}
			
			data.getTransition(ClassType.VINYASA,"pyr", "lo");
			
			
			
			assert(data.getCustomClasses().size() > 1);
			
			
			System.out.println("Test Complete Succesful");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
