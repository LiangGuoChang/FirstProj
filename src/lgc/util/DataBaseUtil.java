package lgc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import lgc.bean.database.Knowledge;
import lgc.bean.database.UserLocation;


/**
 * ���ݿ������
 * 
 * @author lgc
 *
 * @date 2017��7��3�� ����4:25:16
 */
public class DataBaseUtil {

	private static Connection conn;
	private static Statement sql;
	
	/**
	 * �������ݿ�
	 */
	public DataBaseUtil(){
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		} catch (ClassNotFoundException e) {
			System.out.print("DataBaseUtil-ClassNotFoundException::"+"\n"+e.getLocalizedMessage());
			e.printStackTrace();
		}
		try {
			conn=DriverManager.getConnection("jdbc:odbc:Sqllgc","sa","sa123abc");
			sql=conn.createStatement();
		} catch (SQLException e) {
			System.out.print("DataBaseUtil-SQLException::"+"\n"+e.getLocalizedMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * �ͷ� jdbc ��Դ
	 * 
	 * @param con ���ݿ�����
	 * @param ps
	 * @param rs ���ݿ�����
	 */
	private static void releaseResources(Connection con,PreparedStatement ps,ResultSet rs){
		try {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("releaseResources::\n"+e.toString());
		}
	}
	
	/**
	 * ��ȡ֪ʶ�ʴ���е����м�¼
	 * 
	 * @return List<Knowledge>
	 */
	public List<Knowledge> findAllKnowledge(){
		List<Knowledge> knowledges=new ArrayList<Knowledge>();
		String sqlStr = "select * from knowledge";
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			ps=conn.prepareStatement(sqlStr);
			rs=ps.executeQuery();
			while (rs.next()) {
				Knowledge knowledge=new Knowledge();
				knowledge.setId(rs.getInt("id"));
				knowledge.setQuestion(rs.getString("question"));
				knowledge.setAnswer(rs.getString("answer"));
				knowledge.setCategory(rs.getInt("category"));
				knowledges.add(knowledge);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("findAllKnowledge::\n"+e.toString());
		}finally {
			//�ͷ���Դ
			releaseResources(conn, ps, rs);
		}
		return knowledges;
	}
	
	/**
	 * ��ȡ��һ�������¼������
	 * @param openId �û�id
	 * @return int �������� 1-��ͨ���죬2-Ц����3-������
	 */
	public int getLastCategory(String openId){
		int category=-1;
		String sqlStr="select top 1 chat_category from chat_log where open_id=? order by id desc";
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			ps=conn.prepareStatement(sqlStr);
			ps.setString(1, openId);
			rs=ps.executeQuery();
			if (rs.next()) {
				category=rs.getInt("chat_category");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getLastCategory::\n"+e.toString());
		}finally {
			//�ͷ���Դ
			releaseResources(conn, ps, rs);
		}
		return category;
	}
	
	/**
	 * �����ʴ�֪ʶid�������ȡһ���𰸻ظ��û�
	 * @param pid �ʴ�֪ʶid
	 * @return String ���ػش�����
	 */
	public String getOneKnowledgeSub(int pid){
		String knowledgeAnswer="";
		String sqlStr="select top 1 answer from knowledge_sub where pid=? order by rand()";
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			ps=conn.prepareStatement(sqlStr);
			ps.setInt(1, pid);
			rs=ps.executeQuery();
			if (rs.next()) {
				knowledgeAnswer=rs.getString("answer");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getOneKnowledgeSub::\n"+e.toString());
		}finally {
			//�ͷ���Դ
			releaseResources(conn, ps, rs);
		}
		return knowledgeAnswer;
	}
	
	/**
	 * �����ȡһ��Ц��
	 * @return
	 */
	public String getJoke(){
		String jokeContent="";
		String sqlStr="SELECT TOP 1 joke_content FROM joke ORDER BY NEWID()";
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			ps=conn.prepareStatement(sqlStr);
			rs=ps.executeQuery();
			if (rs.next()) {
				jokeContent=rs.getString("joke_content");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getJoke::\n"+e.toString());
		}finally {
			//�ͷ���Դ
			releaseResources(conn, ps, rs);
		}
		return jokeContent;
	}
	
	/**
	 * ���������¼
	 * @param open_id �û�id
	 * @param create_time ����ʱ��
	 * @param req_msg �û����͵���Ϣ
	 * @param resp_msg ���ںŻظ�����Ϣ
	 * @param chat_category ������Ϣ���ͣ�1-��ͨ���죬2-Ц����3-������
	 * @return
	 */
	public int saveChatLog(String open_id,String create_time,String req_msg,String resp_msg,int chat_category){
		String sqlStr="insert into chat_log(open_id, create_time, req_msg, resp_msg, chat_category) values(?, ?, ?, ?, ?)";
		int insert = -1;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			ps=conn.prepareStatement(sqlStr);
			ps.setString(1, open_id);
			ps.setString(2, create_time);
			ps.setString(3, req_msg);
			ps.setString(4, resp_msg);
			ps.setInt(5, chat_category);
			insert=ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("saveChatLog::\n"+e.toString());
		}finally {
			//�ͷ���Դ
			releaseResources(conn, ps, rs);
		}
		return insert;
	}
	
	/**
	 * �����ݿ�����û����͹�����λ����Ϣ
	 * @param openID �û���ʶ
	 * @param lng �û��ľ���
	 * @param lat �û���ά��
	 * @param bd09Lng ת����İٶ�����ϵ����
	 * @param bd09Lat ת����İٶ�����ϵά��
	 * @return int �����λ��
	 */
	public int saveUserLocation(String openID,String lng,String lat,String bd09Lng,String bd09Lat){
		String sqlStr = "insert into user_location(open_id, lng, lat, bd09_lng, bd09_lat) values (?, ?, ?, ?, ?)";
		int insertIndex=-1;
		PreparedStatement ps=null;
		try {
			ps=conn.prepareStatement(sqlStr);
			ps.setString(1, openID);
			ps.setString(2, lng);
			ps.setString(3, lat);
			ps.setString(4, bd09Lng);
			ps.setString(5, bd09Lat);
			insertIndex=ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("saveUserLocation::\n"+e.toString());
		}finally {
			//�ͷ���Դ
			releaseResources(conn, ps, null);
		}
		return insertIndex;
	}
	
	/**
	 * ��ȡ�û����һ�η��͵ĵ���λ�ã�����poi�û��ܱ�
	 * @param openId �û�Ψһ��ʶ
	 * @return UserLocation �����û�λ����Ϣ����
	 */
	public UserLocation getLastLoaction(String openId){
		UserLocation userLocation=null;
		String sqlStr = "select top 1 * from user_location where open_id=? order by id desc";
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			ps=conn.prepareStatement(sqlStr);
			ps.setString(1, openId);
			rs=ps.executeQuery();
			if (rs.next()) {
				userLocation=new UserLocation();
				userLocation.setOpenId(rs.getString("open_id"));
				userLocation.setLng(rs.getString("lng"));
				userLocation.setLat(rs.getString("lat"));
				userLocation.setBd09Lng(rs.getString("bd09_lng"));
				userLocation.setBd09Lat(rs.getString("bd09_lat"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getLastLoaction::\n"+e.toString());
		}finally {
			//�ͷ���Դ
			releaseResources(conn, ps, rs);
		}
		return userLocation;
	}
	
	
	/**
	 * ��Զ�����ݿ��½����ݱ�
	 * 
	 * @param createSqlStr �������ݱ�Sql���
	 * 
	 *@˵�� ��: String sql = "CREATE TABLE REGISTRATION " +
     *               "(id INTEGER not NULL, " +
     *               " first VARCHAR(255), " + 
     *               " last VARCHAR(255), " + 
     *               " age INTEGER, " + 
     *               " PRIMARY KEY ( id ))"; 
     *               
     *@return createIndex
	 */
	public static int createTable2RemoteDatabase(String createSqlStr){
		int createIndex=-1;
		try {
			createIndex=sql.executeUpdate(createSqlStr);
		} catch (SQLException e) {
			System.out.println("DataBaseUtil-createTable2RemoteDatabase::"+e.toString());
			e.printStackTrace();
		}
		return createIndex;
	}
	
	/**
	 * ��ɾ�����ݱ�
	 * @param strSql SQL���   
	 * 
	 * @������� "insert into user_location values"+"("+"'"+open_id+"','"+lng+"','"+lat+"','"+bd09_lng+"','"+bd09_lat+"'"+")"
	 * @ɾ����� "delete from user_location where open_id"+"="+"'"+number+"'"
	 * @������� "update user_location set bd09_lng = "+newbd09_lng+"where open_id="+"'"+user_open_id+"'"
	 * 
	 * @return insertIndex ������λ��
	 */
	public  int cruNewData(String strSql){
		int index=0;
		try {
			index=sql.executeUpdate(strSql);
		} catch (SQLException e) {
			System.out.print("DataBaseUtil-cruNewData-SQLException::"+"\n"+e.getLocalizedMessage());
			e.printStackTrace();
		}
		return index;
	}
	
	
	/**
	 * ˳���ѯ���ݱ�
	 * 
	 * @param strSql ��ѯ���
	 * @��ѯ��� "select * from user_location"
	 * 
	 * @return ResultSet ���ؽ��������ʵ��
	 */
	public static ResultSet queryData(String strSql){
		ResultSet rs=null;
		try {
			rs=sql.executeQuery(strSql);
		} catch (SQLException e) {
			System.out.print("DataBaseUtil-queryData-SQLException::"+"\n"+e.getLocalizedMessage());
			e.printStackTrace();
		}
		return rs;
	}
}
