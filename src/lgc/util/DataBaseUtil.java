package lgc.util;


import java.nio.file.attribute.UserPrincipalLookupService;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
		try {
			PreparedStatement ps=conn.prepareStatement(sqlStr);
			ps.setString(1, openID);
			ps.setString(2, lng);
			ps.setString(3, lat);
			ps.setString(4, bd09Lng);
			ps.setString(5, bd09Lat);
			insertIndex=ps.executeUpdate();
			//�ͷ���Դ
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
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
//		String sqlStr = "select open_id, lng, lat, bd09_lng, bd09_lat from user_location where open_id=? order by id desc limit 1";
		
		//select * from table order by id desc limit 1
		String sqlStr = "select top 1 * from user_location where open_id=? order by id desc";
		
		try {
			PreparedStatement ps=conn.prepareStatement(sqlStr);
			ps.setString(1, openId);
			
			
//			ResultSet rs=queryData(sqlStr);
			ResultSet rs=ps.executeQuery();
			
			if (rs.next()) {
				userLocation=new UserLocation();
				userLocation.setOpenId(rs.getString("open_id"));
				userLocation.setLng(rs.getString("lng"));
				userLocation.setLat(rs.getString("lat"));
				userLocation.setBd09Lng(rs.getString("bd09_lng"));
				userLocation.setBd09Lat(rs.getString("bd09_lat"));
			}
			//�ͷ���Դ
			rs.close();
//			ps.close();
			conn.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
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
