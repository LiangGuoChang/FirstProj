<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>注册</title>
</head>
<body><font size=5>

<%!
	Hashtable hashtable=new Hashtable();
	public synchronized void putName(String s){
		hashtable.put(s, s);
	}
%>

<%
	String name=request.getParameter("name");
	if(name == null){
		name="";
	}
	byte b[]=name.getBytes();
	name=new String(b);
	if(!(hashtable.containsKey(name))){
		putName(name);
		out.print("<br>"+"您已注册成功");
		out.print("<br>"+"您注册的名字是"+name);
	}else{
		out.print("<br>"+"该名字已经存在，请您换个名字");
	}
%>

</font>
</body>
</html>