package com.yyp.helper.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.yyp.gdata.GDataColumn;
import com.yyp.gdata.GDataRow;
import com.yyp.gdata.GDataSpreadSheet;
import com.yyp.helper.client.data.CustomClass;
import com.yyp.helper.client.data.UserInfo;
import com.yyp.helper.client.data.YogaIntention;
import com.yyp.helper.client.data.YogaMasterData;
import com.yyp.helper.client.data.YogaPose;
import com.yyp.helper.client.data.YogaPoseCompleted;
import com.yyp.helper.client.data.YogaPoseHistoryDay;
import com.yyp.helper.client.data.YogaPosePool;
import com.yyp.helper.client.data.YogaPosePool.Type;
import com.yyp.helper.client.data.YogaPoseToEdit;
import com.yyp.helper.client.data.YogaSet;
import com.yyp.helper.client.data.YogaTemplate;
import com.yyp.helper.client.data.YogaTransition;
import com.yyp.helper.client.tts.PoseToCreate.Breath;
import com.yyp.helper.server.tts.AudioCreator;

public class YogaServerDao {

	static private YogaServerDao __instance;

	static public YogaServerDao getInstance() {
		if (__instance == null) {
			__instance = new YogaServerDao();
		}
		return __instance;
	}

	static YogaMasterData __master;

	Logger __logger = Logger.getLogger(YogaServerDao.class);

	public void releaseResources() {
		__master = null;
	}

	public YogaMasterData getPostureDatabase(int userId) throws Exception {
		if (__master == null) {
			try {
				YogaMasterData data = new YogaMasterData();
				__master = data;
				data.getPoses().addAll(getSystemPoses());
				data.getPoses().addAll(getUserPoses());
				data.getTemplates().addAll(getTemplates());
				
				if(true) {
					return data;
				}
				
				String userName = YogaServerProperties.getInstance()
						.getProperty("userName");
				String password = YogaServerProperties.getInstance()
						.getProperty("password");
				String docName = YogaServerProperties.getInstance()
						.getProperty("docName");

				GDataSpreadSheet gdoc = new GDataSpreadSheet(userName,
						password, docName);

				Map<String, List<GDataRow>> postureMap = gdoc.getPostureMap(3,
						postureKeyFields);

				List<YogaPose> yPoseDb = new ArrayList<YogaPose>();
				for (String poseName : postureMap.keySet()) {
					YogaPose pose = extractYogaPose(poseName,
							postureMap.get(poseName));
					yPoseDb.add(pose);
				}
				data.getPoses().addAll(yPoseDb);

				

				/**
				 * Read warm up sets
				 * 
				 */
				List<YogaSet> warmUpSetDb = new ArrayList<YogaSet>();
				Map<String, List<GDataRow>> setWarmUpMap = gdoc
						.getSetKeyWarmUp(4, setKeyWarmUpFields);
				for (String setKey : setWarmUpMap.keySet()) {
					YogaSet pose = extractYogaSet(setKey,
							setWarmUpMap.get(setKey), Type.WARMUP);
					warmUpSetDb.add(pose);
				}
				data.getSets().addAll(warmUpSetDb);

				/**
				 * Read main sets
				 * 
				 */
				List<YogaSet> mainSetDb = new ArrayList<YogaSet>();
				Map<String, List<GDataRow>> mainMap = gdoc.getSetKeyWarmUp(4,
						setKeyMainFields);
				for (String setKey : mainMap.keySet()) {
					YogaSet pose = extractYogaSet(setKey, mainMap.get(setKey),
							Type.MAIN);
					mainSetDb.add(pose);
				}
				data.getSets().addAll(mainSetDb);

				/**
				 * Read cool down sets
				 * 
				 */
				List<YogaSet> coolDownSetDb = new ArrayList<YogaSet>();
				Map<String, List<GDataRow>> coolDownMap = gdoc.getSetKeyWarmUp(
						4, setKeyCoolDownFields);
				for (String setKey : coolDownMap.keySet()) {
					YogaSet pose = extractYogaSet(setKey,
							coolDownMap.get(setKey), Type.COOLDOWN);
					coolDownSetDb.add(pose);
				}
				data.getSets().addAll(coolDownSetDb);

				/**
				 * Read closing sets
				 * 
				 */
				List<YogaSet> closeDownSetDb = new ArrayList<YogaSet>();
				Map<String, List<GDataRow>> closeDownMap = gdoc
						.getSetKeyWarmUp(4, setKeyCloseDownFields);
				for (String setKey : coolDownMap.keySet()) {
					YogaSet pose = extractYogaSet(setKey,
							closeDownMap.get(setKey), Type.CLOSING);
					closeDownSetDb.add(pose);
				}
				data.getSets().addAll(closeDownSetDb);

				/**
				 * Intention key list
				 * 
				 */
				List<YogaIntention> intentionSetDb = new ArrayList<YogaIntention>();
				Map<String, List<GDataRow>> intensionKeyMap = gdoc
						.getSetKeyWarmUp(5, intentionKeyFields);
				for (String setKey : intensionKeyMap.keySet()) {
					YogaIntention pose = extractYogaIntension(setKey,
							intensionKeyMap.get(setKey));
					intentionSetDb.add(pose);
				}
				data.getIntensions().addAll(intentionSetDb);

				/**
				 * Read Level 1 poses
				 * 
				 */
				List<YogaPosePool> keyPoolDb = new ArrayList<YogaPosePool>();
				Map<String, List<GDataRow>> level1PoolKeyMap = gdoc
						.getSetKeyWarmUp(0, level1PoseKeyFields);
				for (String setKey : level1PoolKeyMap.keySet()) {
					YogaPosePool pose = extractYogaLevelPool(
							YogaPosePool.Level.BEGINNER,
							YogaPosePool.Type.WARMUP, setKey,
							level1PoolKeyMap.get(setKey));
					if (pose != null) {
						keyPoolDb.add(pose);
					}
				}
				for (String setKey : level1PoolKeyMap.keySet()) {
					YogaPosePool pose = extractYogaLevelPool(
							YogaPosePool.Level.BEGINNER,
							YogaPosePool.Type.MAIN, setKey,
							level1PoolKeyMap.get(setKey));
					if (pose != null) {
						keyPoolDb.add(pose);
					}
				}
				for (String setKey : level1PoolKeyMap.keySet()) {
					YogaPosePool pose = extractYogaLevelPool(
							YogaPosePool.Level.BEGINNER,
							YogaPosePool.Type.COOLDOWN, setKey,
							level1PoolKeyMap.get(setKey));
					if (pose != null) {
						keyPoolDb.add(pose);
					}
				}
				for (String setKey : level1PoolKeyMap.keySet()) {
					YogaPosePool pose = extractYogaLevelPool(
							YogaPosePool.Level.BEGINNER,
							YogaPosePool.Type.CLOSING, setKey,
							level1PoolKeyMap.get(setKey));
					if (pose != null) {
						keyPoolDb.add(pose);
					}
				}

				/**
				 * Read Level 2 poses
				 * 
				 */
				Map<String, List<GDataRow>> level2PoolKeyMap = gdoc
						.getSetKeyWarmUp(1, level2PoseKeyFields);
				for (String setKey : level2PoolKeyMap.keySet()) {
					YogaPosePool pose = extractYogaLevelPool(
							YogaPosePool.Level.INTERMEDIATE,
							YogaPosePool.Type.WARMUP, setKey,
							level2PoolKeyMap.get(setKey));
					if (pose != null) {
						keyPoolDb.add(pose);
					}
				}
				for (String setKey : level2PoolKeyMap.keySet()) {
					YogaPosePool pose = extractYogaLevelPool(
							YogaPosePool.Level.INTERMEDIATE,
							YogaPosePool.Type.MAIN, setKey,
							level2PoolKeyMap.get(setKey));
					if (pose != null) {
						keyPoolDb.add(pose);
					}
				}
				for (String setKey : level2PoolKeyMap.keySet()) {
					YogaPosePool pose = extractYogaLevelPool(
							YogaPosePool.Level.INTERMEDIATE,
							YogaPosePool.Type.COOLDOWN, setKey,
							level2PoolKeyMap.get(setKey));
					if (pose != null) {
						keyPoolDb.add(pose);
					}
				}

				/**
				 * Read Level 3 poses
				 * 
				 */
				Map<String, List<GDataRow>> level3PoolKeyMap = gdoc
						.getSetKeyWarmUp(2, level3PoseKeyFields);
				for (String setKey : level3PoolKeyMap.keySet()) {
					YogaPosePool pose = extractYogaLevelPool(
							YogaPosePool.Level.ADVANCED,
							YogaPosePool.Type.WARMUP, setKey,
							level3PoolKeyMap.get(setKey));
					if (pose != null) {
						keyPoolDb.add(pose);
					}
				}
				for (String setKey : level3PoolKeyMap.keySet()) {
					YogaPosePool pose = extractYogaLevelPool(
							YogaPosePool.Level.ADVANCED,
							YogaPosePool.Type.MAIN, setKey,
							level3PoolKeyMap.get(setKey));
					if (pose != null) {
						keyPoolDb.add(pose);
					}
				}
				for (String setKey : level3PoolKeyMap.keySet()) {
					YogaPosePool pose = extractYogaLevelPool(
							YogaPosePool.Level.ADVANCED,
							YogaPosePool.Type.COOLDOWN, setKey,
							level3PoolKeyMap.get(setKey));
					if (pose != null) {
						keyPoolDb.add(pose);
					}
				}

				data.getPosePool().addAll(keyPoolDb);

				List<YogaTemplate> templates = new ArrayList<YogaTemplate>();
				/** Read the templates */
				Map<String, List<GDataRow>> templateMap = gdoc.getTemplates(5,
						templateFields);
				List<YogaTemplate> templatesDb = new ArrayList<YogaTemplate>();
				for (String rowKey : templateMap.keySet()) {
					YogaTemplate template = extractTemplate(templateMap
							.get(rowKey));
					templates.add(template);
				}
				data.getTemplates().addAll(templates);

				List<YogaTransition> transisions = new ArrayList<YogaTransition>();
				/** Read the transitions */
				Map<String, List<GDataRow>> transisionMap = gdoc.getTemplates(
						6, transisionFields);
				List<YogaTransition> transisinoDb = new ArrayList<YogaTransition>();
				for (String rowKey : transisionMap.keySet()) {
					YogaTransition template = extractTransision(transisionMap
							.get(rowKey));
					transisions.add(template);
				}
				data.getTransisions().addAll(transisions);

				List<CustomClass> customClasses = new ArrayList<CustomClass>();
				/** Read the custom classes */
				Map<String, List<GDataRow>> customMap = gdoc.getTemplates(9,
						customClassFields);
				for (String rowKey : customMap.keySet()) {
					CustomClass customClass = extractCustomClass(customMap
							.get(rowKey));
					customClasses.add(customClass);
				}
				__master.getCustomClasses().addAll(customClasses);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		/**
		 * add any user custom created classes
		 * 
		 */

		// first remove all existing CC
		List<CustomClass> copyClasses = new ArrayList<CustomClass>();
		copyClasses.addAll(__master.getCustomClasses());
		for (CustomClass cc : copyClasses) {
			if (cc.isUserCreated()) {
				__master.getCustomClasses().remove(cc);
			}
		}
		__master.getCustomClasses().addAll(getCustomClassDefinitions(userId));

		return __master;
	}

	static List<GDataColumn> transisionFields = new ArrayList<GDataColumn>() {
		{
			add(new GDataColumn("transision", 1));
			add(new GDataColumn("queue", 2));
			add(new GDataColumn("class_type", 3));
		}
	};

	static List<GDataColumn> customClassFields = new ArrayList<GDataColumn>() {
		{
			add(new GDataColumn("name", 1));
			add(new GDataColumn("custom_class", 2));
		}
	};

	static List<GDataColumn> postureKeyFields = new ArrayList<GDataColumn>() {
		{
			add(new GDataColumn("posture", 1));
			add(new GDataColumn("key", 2));
			add(new GDataColumn("asymetrical", 3));
			add(new GDataColumn("breath", 15));
			add(new GDataColumn("image", 16));
		}
	};

	static List<GDataColumn> setKeyWarmUpFields = new ArrayList<GDataColumn>() {
		{
			add(new GDataColumn("label", 2));
			add(new GDataColumn("annotation", 3));
			add(new GDataColumn("postures", 4));
		}
	};

	static List<GDataColumn> setKeyMainFields = new ArrayList<GDataColumn>() {
		{
			add(new GDataColumn("label", 5));
			add(new GDataColumn("annotation", 6));
			add(new GDataColumn("postures", 7));
		}
	};

	static List<GDataColumn> setKeyCoolDownFields = new ArrayList<GDataColumn>() {
		{
			add(new GDataColumn("label", 8));
			add(new GDataColumn("annotation", 9));
			add(new GDataColumn("postures", 10));
		}
	};

	static List<GDataColumn> setKeyCloseDownFields = new ArrayList<GDataColumn>() {
		{
			add(new GDataColumn("label", 11));
			add(new GDataColumn("annotation", 12));
			add(new GDataColumn("postures", 13));
		}
	};

	static List<GDataColumn> intentionKeyFields = new ArrayList<GDataColumn>() {
		{
			add(new GDataColumn("level_body_part", 1));
			add(new GDataColumn("key", 2));
			add(new GDataColumn("level_intention", 3));
			add(new GDataColumn("combination", 4));
		}
	};

	static List<GDataColumn> level1PoseKeyFields = new ArrayList<GDataColumn>() {
		{
			add(new GDataColumn("key", 1));
		}
	};

	static List<GDataColumn> level2PoseKeyFields = new ArrayList<GDataColumn>() {
		{
			add(new GDataColumn("key", 2));
		}
	};

	static List<GDataColumn> level3PoseKeyFields = new ArrayList<GDataColumn>() {
		{
			add(new GDataColumn("key", 1));
		}
	};

	static List<GDataColumn> templateFields = new ArrayList<GDataColumn>() {
		{
			add(new GDataColumn("template", 1));
			add(new GDataColumn("key", 2));
		}
	};

	private YogaTemplate extractTemplate(List<GDataRow> rows) throws Exception {
		try {
			String key = null;
			String template = null;
			for (GDataRow row : rows) {
				GDataColumn col = row.getCol();
				// System.out.println(col + " = " + row.getValue());

				if (col.getColName().equals("key")) {
					key = row.getValue();
				} else if (col.getColName().equals("template")) {
					template = row.getValue();
				}
			}
			if (key != null) {
				return new YogaTemplate(key, template);
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	private YogaTransition extractTransision(List<GDataRow> rows)
			throws Exception {
		try {
			String transision = null;
			String queue = null;
			String classType = null;
			for (GDataRow row : rows) {
				GDataColumn col = row.getCol();
				// System.out.println(col + " = " + row.getValue());

				if (col.getColName().equals("transision")) {
					transision = row.getValue();
				} else if (col.getColName().equals("queue")) {
					queue = row.getValue();
				} else if (col.getColName().equals("class_type")) {
					classType = row.getValue();
				}
			}
			if (transision != null) {
				return new YogaTransition(transision, queue, YogaPosePool.mapClassType(classType));
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	private CustomClass extractCustomClass(List<GDataRow> rows)
			throws Exception {
		try {
			String name = null;
			String classDsl = null;
			for (GDataRow row : rows) {
				GDataColumn col = row.getCol();
				// System.out.println(col + " = " + row.getValue());

				if (col.getColName().equals("name")) {
					name = row.getValue();
				} else if (col.getColName().equals("custom_class")) {
					classDsl = row.getValue();
				}
			}
			if (name != null) {
				return new CustomClass(name, classDsl);
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	private YogaPosePool extractYogaLevelPool(YogaPosePool.Level level,
			YogaPosePool.Type type, String setKey, List<GDataRow> rows)
			throws Exception {
		try {
			String key = null;
			for (GDataRow row : rows) {
				GDataColumn col = row.getCol();
				// System.out.println(col + " = " + row.getValue());

				if (col.getColName().equals("key"))
					key = row.getValue();
			}
			if (key != null) {
				return new YogaPosePool(type, level, key);
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	private YogaSet extractYogaSet(String poseName, List<GDataRow> rows,
			Type level) throws Exception {
		try {
			String label = null;
			String annotation = null;
			String postures = null;
			for (GDataRow row : rows) {
				GDataColumn col = row.getCol();
				// System.out.println(col + " = " + row.getValue());

				if (col.getColName().equals("label"))
					label = row.getValue();
				else if (col.getColName().equals("annotation")) {
					annotation = row.getValue();
				} else if (col.getColName().equals("postures")) {
					postures = row.getValue();
				}
			}
			return new YogaSet(annotation, label, postures, level);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	private YogaIntention extractYogaIntension(String poseName,
			List<GDataRow> rows) throws Exception {
		try {
			String bodyPart = null;
			String key = null;
			String intension = null;
			String combination = null;

			for (GDataRow row : rows) {
				GDataColumn col = row.getCol();
				// System.out.println(col + " = " + row.getValue());

				if (col.getColName().equals("level_body_part"))
					bodyPart = row.getValue(); // extract level
				else if (col.getColName().equals("key")) {
					key = row.getValue();
				} else if (col.getColName().equals("intension")) {
					intension = row.getValue();
				} else if (col.getColName().equals("combination")) {
					combination = row.getValue();
				}
			}
			return new YogaIntention(bodyPart, key, intension, combination);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * Extract data for a single posture key
	 * 
	 * @param poseName
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	private YogaPose extractYogaPose(String poseName, List<GDataRow> rows)
			throws Exception {
		try {
			String poseKey = null;
			String asymetrical = null;
			Breath breath = null;
			String image = null;
			String sanskrit = null;
			for (GDataRow row : rows) {
				GDataColumn col = row.getCol();
				String value = row.getValue().trim();

				if (col.getColName().equals("key")) {
					poseKey = value;
				} else if (col.getColName().equals("asymetrical")) {
					asymetrical = row.getValue();
				} else if (col.getColName().equals("breath")) {
					breath = Breath.lookupBreath(row.getValue());
				} else if (col.getColName().equals("image")) {
					image = row.getValue();
				}
			}
			
			
			String serverUrl = YogaServerProperties.getInstance().getProperty("yyp.server");
			YogaPose pose = new YogaPose(poseKey, poseName, sanskrit, asymetrical, breath, image,"base");
			return pose;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void deleteCustomClassDefinition(int uid, String className) {
		Connection conn = null;
		PreparedStatement ps;
		try {
			conn = ConnectionManager.getNewConnection();

			ps = conn
					.prepareStatement("delete from CUSTOM_CLASS where uid = ? and class_name = ?");

			ps.setInt(1, uid);
			ps.setString(2, className);

			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.releaseResources(null, null, conn);
		}
	}

	public void saveCustomClassDefinition(int uid, String className,
			String classDsl) {
		Connection conn = null;
		PreparedStatement ps;
		try {
			conn = ConnectionManager.getNewConnection();

			conn.createStatement().executeUpdate(
					"delete from CUSTOM_CLASS where uid = " + uid
							+ " and class_name = '" + className + "'");

			ps = conn
					.prepareStatement("insert into CUSTOM_CLASS(uid,class_name,class_dsl)values(?,?,?)");

			ps.setInt(1, uid);
			ps.setString(2, className);
			ps.setString(3, classDsl);

			if (ps.executeUpdate() != 1) {
				System.out.println("Save to CUSTOM_CLASS failed");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.releaseResources(null, null, conn);
		}

	}

	public List<CustomClass> getCustomClassDefinitions(int userId) {
		List<CustomClass> classes = new ArrayList<CustomClass>();

		if (userId == 0) {
			userId = 1; // *default*/
		}

		Connection conn = null;
		PreparedStatement ps;
		try {
			conn = ConnectionManager.getNewConnection();
			ps = conn
					.prepareStatement("select * from CUSTOM_CLASS where uid = ? order by class_name");
			ps.setInt(1, userId);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				CustomClass cc = new CustomClass(rs.getString("class_name"),
						rs.getString("class_dsl"), true);
				classes.add(cc);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.releaseResources(null, null, conn);
		}

		return classes;

	}

	public UserInfo loginUser(String user, String pass) {
		Connection conn = null;
		PreparedStatement ps;
		try {
			conn = ConnectionManager.getNewConnection();
			ps = conn
					.prepareStatement("select * from USER where user_name = ? and password = ?");
			ps.setString(1, user);
			ps.setString(2, pass);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return new UserInfo(user, pass, rs.getInt("uid"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.releaseResources(null, null, conn);
		}
		return null;
	}

	public void savePoseHistory(int uid, String className,
			List<YogaPoseCompleted> posesDone) {
		Connection conn = null;
		PreparedStatement psI = null, psI2 = null;
		try {
			conn = ConnectionManager.getNewConnection();

			psI2 = conn
					.prepareStatement("insert into CLASS_HISTORY(class_name, class_completed)values(?,now())");
			psI2.setString(1, className);
			psI2.executeUpdate();

			psI = conn
					.prepareStatement("insert into POSE_HISTORY(uid,class_name, pose_id,pose_date,seconds_held)values(?,?,?,now(),?)");
			for (YogaPoseCompleted yc : posesDone) {
				psI.setInt(1, uid);
				psI.setString(2, className);
				psI.setString(3, yc.getPoseId());
				psI.setInt(4, yc.getSecondsHeld());

				if (psI.executeUpdate() != 1) {
					System.out.println("Could not savePoseHistory: " + yc);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.releaseResources(null, psI, conn);
		}
	}

	public List<YogaPoseCompleted> getPoseHistory(int uid) {
		List<YogaPoseCompleted> classes = new ArrayList<YogaPoseCompleted>();

		Connection conn = null;
		PreparedStatement ps;
		try {
			conn = ConnectionManager.getNewConnection();
			ps = conn
					.prepareStatement("select uid, pose_id, count(*) as num_times, sum(seconds_held) as total_held from   POSE_HISTORY where uid = ? group by uid,pose_id");
			ps.setInt(1, uid);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				YogaPoseCompleted c = new YogaPoseCompleted(
						rs.getString("pose_id"), rs.getInt("total_held"));
				c.setTimesHeld(rs.getInt("num_times"));
				classes.add(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.releaseResources(null, null, conn);
		}

		return classes;
	}

	public List<YogaPoseHistoryDay> getPoseHistoryLastSevenDays(int uid) {
		List<YogaPoseHistoryDay> days = new ArrayList<YogaPoseHistoryDay>();

		Connection conn = null;
		PreparedStatement ps;
		try {
			conn = ConnectionManager.getNewConnection();
			String sql = "select DATE_FORMAT(pose_date, '%W') as yoga_day , sum(seconds_held) / 1000 as yoga_seconds "
					+ " from POSE_HISTORY "
					+ " where pose_date between date_sub(now(),INTERVAL 1 WEEK) and now() "
					+ " and uid = ? " + " group by date(pose_date) ";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, uid);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {

				YogaPoseHistoryDay day = new YogaPoseHistoryDay(
						rs.getString("yoga_day"), rs.getInt("yoga_seconds"));
				days.add(day);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.releaseResources(null, null, conn);
		}

		return days;
	}

	//
	// public UserInfo login(String u, String p) {
	// UserInfo ui = new UserInfo();
	// try {
	// String url = "jdbc:mysql://localhost/test";
	// Class.forName ("com.mysql.jdbc.Driver").newInstance ();
	// conn = DriverManager.getConnection (url, userName, password);
	//
	// }
	// catch(Exception e) {
	// e.printStackTrace();
	// }
	// return ui;
	// }

	static public void main(String as[]) {
		// new YogaServerDao().saveCustomClassDefinition(0, "test","sus");
		new YogaServerDao().loginUser("casey", "casey");
	}

	/**
	 * Return the class DSL for this class or null
	 */
	public String getClassDsl(String userName, String className) {

		String sql = "select class_dsl " + " from CUSTOM_CLASS cc "
				+ " JOIN USER u on u.uid = cc.uid " + " where u.user_name = ? "
				+ " and     cc.class_name = ?";

		Connection conn = null;
		PreparedStatement ps;
		try {
			conn = ConnectionManager.getNewConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, userName);
			ps.setString(2, className);
			ResultSet rs = ps.executeQuery();
			if (rs.first()) {
				return rs.getString("class_dsl");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.releaseResources(null, null, conn);
		}
		return null;
	}

	/**
	 * Try to create a new pose key by using pieces of the pose name
	 * 
	 * This only looks for a key that can be used, it does not not create a new
	 * pose.
	 * 
	 * 
	 * @param nameEnglish
	 * @return
	 * @throws Exception
	 */
	public String createNewPoseKey(String nameEnglish) throws Exception {
		try {
			for (int i = 1; i < 5; i++) {
				String potenialId = getFirstLettersOfEachWord(i, nameEnglish);
				if (!idDoesExist(potenialId)) {
					return potenialId;
				}
			}

			throw new Exception(
					"Could not create a new pose key (out of tries)");
		} catch (Exception e) {
			__logger.error("Could not create new pose: " + nameEnglish);
			throw e;
		}
	}

	public void deletePose(String key) throws Exception {
		Connection conn = null;
		PreparedStatement ps;
		try {
			String sql = "delete from POSE where pose_key = ? and user_name is null";
			conn = ConnectionManager.getNewConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, key);
			int cnt = ps.executeUpdate();
			if (cnt != 1) {
				throw new Exception("Could not delete pose: " + key);
			}
			prunePoseFromInternal(key);
			new AudioCreator().deletePoseAudio(key);
		} finally {
			ConnectionManager.releaseResources(null, null, conn);
		}
	}

	private void prunePoseFromInternal(String key) throws Exception {
		List<YogaPose> poses = getPostureDatabase(0).getPoses();
		for (int i = 0; i < poses.size(); i++) {
			if (poses.get(i).getPoseKey().equals(key)) {
				poses.remove(i);
				break;
			}
		}
	}
	
	
	private void addPoseToInternal(YogaPose pose) throws Exception {
		List<YogaPose> poses = getPostureDatabase(0).getPoses();
		poses.add(pose);
	}


	private boolean idDoesExist(String potenialId) throws Exception {
		Connection conn = null;
		PreparedStatement ps;
		try {
			String sql = "select count(*) as cnt from POSE where pose_key = ?";
			conn = ConnectionManager.getNewConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, potenialId);
			ResultSet rs = ps.executeQuery();
			rs.first();
			return rs.getInt("cnt") > 0;
		} catch (Exception e) {
			__logger.error("Error checking if id exists: " + potenialId, e);
			throw e;
		} finally {
			ConnectionManager.releaseResources(null, null, conn);
		}
	}

	private String getFirstLettersOfEachWord(int len, String name) {
		String p[] = name.toLowerCase().split(" ");

		String key = "";
		for (int i = 0; i < p.length; i++) {
			if (key.length() > 0) {
				key += "_";
			}
			key += p[i].length() >= len ? p[i].substring(0, len) : p[i];
		}
		return key;
	}

	public YogaPose createNewPose(String key, String nameEnglish,
			boolean isAsymmetrical, String nameSanskrit, Breath breath,
			String imageUrl, String alignText) throws Exception {

		String newPoseKey = null;

		if (key == null || key.length() == 0) {
			newPoseKey = createNewPoseKey(nameEnglish);
		} else {
			newPoseKey = key;
			YogaPose thePose = getPoseFromDb(key);
			if (thePose != null) {
				if(thePose.getUserName() != null) {
					if(thePose.getUserName().length() > 0) {
						throw new Exception("Could not overwrite existing base pose");
					}
				}
				deletePose(key);
			}
		}

		Connection conn = null;
		PreparedStatement ps;
		try {
			conn = ConnectionManager.getNewConnection();

			String sql = "insert into POSE(pose_key, is_asym, pose_name, pose_name_sanskrit, image_url, breath, pose_align_text, time_created) "
					+ "values(?,?,?,?,?,?,?,now())";

			ps = conn.prepareStatement(sql);
			ps.setString(1, newPoseKey);
			ps.setInt(2, isAsymmetrical ? 1 : 0);
			ps.setString(3, nameEnglish);
			ps.setString(4, nameSanskrit);
			ps.setString(5, imageUrl);
			ps.setString(6, breath.getLabel());
			ps.setString(7, alignText);
			int cnt = ps.executeUpdate();
			if (cnt != 1) {
				throw new Exception("Could not create new pose: " + nameEnglish);
			}
		} catch (Exception e) {
			__logger.error("Error creating new pose", e);
			throw e;
		} finally {
			ConnectionManager.releaseResources(null, null, conn);
		}
		YogaPose pose = getPoseFromDb(newPoseKey);
		addPoseToInternal(pose);
		return pose;
	}
	
	
	private Collection<? extends YogaPose> getSystemPoses() {
		Connection conn = null;
		PreparedStatement ps;
		List<YogaPose> poses = new ArrayList<YogaPose>();
		try {
			conn = ConnectionManager.getNewConnection();

			String sql = "select * from POSE where user_name is NOT null";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				YogaPose pose = new YogaPose(rs.getString("pose_key"), 
						rs.getString("pose_name"), rs.getString("pose_name_sanskrit"),
						rs.getInt("is_asym") != 0 ? "t"
						: "f", Breath.lookupBreath(rs.getString("breath")), rs
						.getString("image_url"), rs.getString("user_name"));
				poses.add(pose);
			}

		} catch (Exception e) {
			__logger.error("Error reading pose", e);
		} finally {
			ConnectionManager.releaseResources(null, null, conn);
		}

		return poses;
	}

	private Collection<? extends YogaPose> getUserPoses() {
		Connection conn = null;
		PreparedStatement ps;
		List<YogaPose> poses = new ArrayList<YogaPose>();
		try {
			conn = ConnectionManager.getNewConnection();

			String sql = "select * from POSE where user_name is null";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				YogaPose pose = new YogaPose(rs.getString("pose_key"), 
						rs.getString("pose_name"), rs.getString("pose_name_sanskrit"),
						rs.getInt("is_asym") != 0 ? "t"
						: "f", Breath.lookupBreath(rs.getString("breath")), rs
						.getString("image_url"), rs.getString("user_name"));
				poses.add(pose);
			}

		} catch (Exception e) {
			__logger.error("Error reading pose", e);
		} finally {
			ConnectionManager.releaseResources(null, null, conn);
		}

		return poses;
	}

	public YogaPose getPoseFromDb(String poseKey) throws Exception {
		Connection conn = null;
		PreparedStatement ps;
		try {
			conn = ConnectionManager.getNewConnection();

			String sql = "select * from POSE where pose_key = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, poseKey);
			ResultSet rs = ps.executeQuery();
			if (rs.first()) {
				YogaPose pose = new YogaPose(poseKey,rs.getString("pose_name"),rs.getString("pose_name_sanskrit"),
						rs.getInt("is_asym") != 0 ? "t" : "f",
						Breath.lookupBreath(rs.getString("breath")),
						rs.getString("image_url"), rs.getString("user_name"));
			}
		} catch (Exception e) {
			__logger.error("Error reading pose", e);
			throw e;
		} finally {
			ConnectionManager.releaseResources(null, null, conn);
		}
		
		return null;
	}

	/** REturn the yoga pose, with all known alignment text.
	 * 
	 * Used to edit currrent pose
	 * 
	 * @param poseKey
	 * @return
	 */
	public YogaPoseToEdit getPoseToEdit(String poseKey) throws Exception {
		YogaPose pose = getPoseFromDb(poseKey);
		
		Connection conn = null;
		PreparedStatement ps=null;
		String alignText = "";
		try {
			conn = ConnectionManager.getNewConnection();

			String sql = "select pose_align_text from POSE where pose_key = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, poseKey);
			ResultSet rs = ps.executeQuery();
			if (rs.first()) {
				alignText = rs.getString("pose_align_text");
			}
		} finally {
			ConnectionManager.releaseResources(null, null, conn);
		}
		
		YogaPoseToEdit poseToEdit = new YogaPoseToEdit(pose, alignText);
		return poseToEdit;
	}

	public List<YogaTemplate> getTemplates() throws Exception {
		
		ArrayList<YogaTemplate> list = new ArrayList<YogaTemplate>();
		
		Connection conn = null;
		PreparedStatement ps=null;
		try {
			conn = ConnectionManager.getNewConnection();

			String sql = "select template, template_key from POSE_TEMPLATE";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				list.add(new YogaTemplate(rs.getString("template_key"), rs.getString("template")));
			}
			return list;
			
		} finally {
			ConnectionManager.releaseResources(null, null, conn);
		}
	}
}