<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<%@page import="java.sql.*"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<title>首页</title>
</head>
<body>
<form action="WxServlet" method="get"> 
</form> 

<font size=1> 

<p>添加新数据到数据库
<form action="InsertNewData.jsp" method="post">
	open_id：
	<input type="text" name="open_id">
	<br><br>
	lng:
	<input type="text" name="lng">
	<br><br>
	lat:
	<input type="text" name="lat">
	<br><br>
	bd09_lng:
	<input type="text" name="bd09_lng">
	<br><br>
	bd09_lat:
	<input type="text" name="bd09_lat">
	<br><br>
	<input type="submit" name="sub" value="提交添加">
</form>

<p>添加数据前的记录是：

<%!
	//声明一个共享的链接变量
	Connection conn =null;
%>

<%
	
	Statement sql=null;
	ResultSet rs=null;
	
	//第一个用户建立链接
	if(conn == null){
		try{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		}catch(ClassNotFoundException e){
			out.print("ClassNotFoundException::"+"<br>"+e.getLocalizedMessage());
		}
		try{
			//链接数据库
			conn=DriverManager.getConnection("jdbc:odbc:Sqllgc","sa","sa123abc");
			
			//链接 excel 表
			//conn=DriverManager.getConnection("jdbc:odbc:excel","","");
			
			sql=conn.createStatement();
			//数据库表 user_location
			rs=sql.executeQuery("SELECT * FROM user_location");
			
			//excel  工作区 testblock
			//rs=sql.executeQuery("SELECT * FROM testblock");
			
			out.print("第一个客户");
			
		}catch(SQLException e){
			out.print("<br>"+"SQLException::"+"<br>"+e.getMessage());
		}
	}
	//其它客户通过同步块使用这个链接
	else{
		synchronized(conn){
			try{
				
				sql=conn.createStatement();
				//数据库表 user_location
				//rs=sql.executeQuery("SELECT * FROM user_location");
				
				//excel  工作区 testblock
				rs=sql.executeQuery("SELECT * FROM testblock");
				
				out.print("不是第一个客户");
			}catch(SQLException e){
				out.print("<br>"+"SQLException::"+"<br>"+e.getMessage());
			}
		}
	}
	
	try{
		out.print("<table border>");
		out.print("<tr>");
		
			out.print("<th width=100>"+"id");
			out.print("<th width=100>"+"open_id");
			out.print("<th width=200>"+"lng");
			out.print("<th width=200>"+"lat");
			out.print("<th width=200>"+"bd09_lng");
			out.print("<th width=200>"+"bd09_lat");
			
			/* out.print("<th width=100>"+"地区代码");
			out.print("<th width=100>"+"地区名称（县、市、区）");
			out.print("<th width=200>"+"师范生派遣单位名称");
			out.print("<th width=200>"+"师范生派遣单位主管部门");
			out.print("<th width=200>"+"师范生派遣单位办公电话");
			out.print("<th width=200>"+"师范生派遣单位地址"); */
			
			
		out.print("</tr>");
		while(rs.next()){
			out.print("<tr>");
			out.print("<td align=center>"+"<br>"+rs.getInt(1)+"</td>");
			out.print("<td align=center>"+"<br>"+rs.getString(2)+"</td>");
			out.print("<td align=center>"+"<br>"+rs.getString(3)+"</td>");
			out.print("<td align=center>"+"<br>"+rs.getString(4)+"</td>");
			out.print("<td align=center>"+"<br>"+rs.getString(5)+"</td>");
			out.print("<td align=center>"+"<br>"+rs.getString(6)+"</td>");
			out.print("</tr>");
		}
		out.print("</table>");
		//conn.close();
	}catch(SQLException e){
		out.print("<br>"+"SQLException::"+"<br>"+e.getMessage());
	}
	
%>


</font>

</body>
</html>