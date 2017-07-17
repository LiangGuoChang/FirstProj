<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>添加新数据</title>
</head>
<body>

<%
	//获取open_id：
	String open_id=request.getParameter("open_id");
 	if(open_id==null){
 		open_id="";
 	}
 	byte[] b0=open_id.getBytes("ISO-8859-1");
 	open_id=new String(b0);
 	//获取lng
 	String lng=request.getParameter("lng");
 	if(lng==null){
 		lng="";
 	}
 	byte[] b1=lng.getBytes("ISO-8859-1");
 	lng=new String(b1);
 	//获取lat
 	String lat=request.getParameter("lat");
 	if(lat==null){
 		lat="";
 	}
 	byte[] b2=lat.getBytes("ISO-8859-1");
 	lat=new String(b2);
 	//获取bd09_lng
 	String bd09_lng=request.getParameter("bd09_lng");
 	if(bd09_lng==null){
 		bd09_lng="";
 	}
 	byte[] b3=bd09_lng.getBytes("ISO-8859-1");
 	bd09_lng=new String(b3);
 	//获取bd09_lat
 	String bd09_lat=request.getParameter("bd09_lat");
 	if(bd09_lat==null){
 		bd09_lat="";
 	}
 	byte[] b4=bd09_lat.getBytes("ISO-8859-1");
 	bd09_lat=new String(b4);
 	
 	if(open_id=="" || lng=="" || lat=="" || bd09_lng=="" || bd09_lat==""){
 		out.print("非法输入，无法添加数据");
 	}else{
 		//添加数据
 	 	Connection conn;
 	 	Statement sql;
 	 	ResultSet rs;
 	 	try{
 	 		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
 	 	}catch(ClassNotFoundException e){
 	 		out.print("ClassNotFoundException::"+"<br>"+e.getLocalizedMessage());
 	 	}
 	 	
 	 	try{
 			conn=DriverManager.getConnection("jdbc:odbc:Sqllgc","sa","sa123abc");
 			sql=conn.createStatement();
 			//添加数据的 Sql语句
 			String insert=
 			"insert into user_location values"+"("+"'"+open_id+"','"+lng+"','"+lat+"','"+bd09_lng+"','"+bd09_lat+"'"+")";
 			sql.executeUpdate(insert);//执行插入语句
 %>
 <p>添加记录后的数据：
<%
 			rs=sql.executeQuery("SELECT * FROM user_location");
 			out.print("<table border>");
 			out.print("<tr>");
 				out.print("<th width=100>"+"id");
 				out.print("<th width=100>"+"open_id");
 				out.print("<th width=200>"+"lng");
 				out.print("<th width=200>"+"lat");
 				out.print("<th width=200>"+"bd09_lng");
 				out.print("<th width=200>"+"bd09_lat");
 			out.print("</tr>");
 			while(rs.next()){
 				out.print("<tr>");
 				out.print("<td align=center>"+"<br>"+rs.getInt(1)+"</td>");
 				out.print("<td align=center>"+"<br>"+rs.getString("open_id")+"</td>");
 				out.print("<td align=center>"+"<br>"+rs.getString("lng")+"</td>");
 				out.print("<td align=center>"+"<br>"+rs.getString("lat")+"</td>");
 				out.print("<td align=center>"+"<br>"+rs.getString("bd09_lng")+"</td>");
 				out.print("<td align=center>"+"<br>"+rs.getString("bd09_lat")+"</td>");
 				out.print("</tr>");
 			}
 			out.print("</table>");
 			conn.close();
 		}catch(SQLException e){
 			out.print("<br>"+"SQLException::"+"<br>"+e.getMessage());
 		}
 	 	
 	}
 %>	
 	

</body>
</html>