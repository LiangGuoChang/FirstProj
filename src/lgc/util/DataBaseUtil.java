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
 * 数据库操作类
 * 
 * @author lgc
 *
 * @date 2017年7月3日 下午4:25:16
 */
public class DataBaseUtil {

	private static Connection conn;
	private static Statement sql;
	
	/**
	 * 链接数据库
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
	 * 向数据库插入用户发送过来的位置信息
	 * @param openID 用户标识
	 * @param lng 用户的经度
	 * @param lat 用户的维度
	 * @param bd09Lng 转换后的百度坐标系经度
	 * @param bd09Lat 转换后的百度坐标系维度
	 * @return int 插入的位置
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
			//释放资源
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return insertIndex;
	}
	
	/**
	 * 获取用户最后一次发送的地理位置，用于poi用户周边
	 * @param openId 用户唯一标识
	 * @return UserLocation 返回用户位置信息对象
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
			//释放资源
			rs.close();
//			ps.close();
			conn.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return userLocation;
	}
	
	
	/**
	 * 向远程数据库新建数据表
	 * 
	 * @param createSqlStr 创建数据表Sql语句
	 * 
	 *@说明 如: String sql = "CREATE TABLE REGISTRATION " +
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
	 * 增删改数据表
	 * @param strSql SQL语句   
	 * 
	 * @插入语句 "insert into user_location values"+"("+"'"+open_id+"','"+lng+"','"+lat+"','"+bd09_lng+"','"+bd09_lat+"'"+")"
	 * @删除语句 "delete from user_location where open_id"+"="+"'"+number+"'"
	 * @更新语句 "update user_location set bd09_lng = "+newbd09_lng+"where open_id="+"'"+user_open_id+"'"
	 * 
	 * @return insertIndex 操作的位置
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
	 * 顺序查询数据表
	 * 
	 * @param strSql 查询语句
	 * @查询语句 "select * from user_location"
	 * 
	 * @return ResultSet 返回结果集对象实例
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
