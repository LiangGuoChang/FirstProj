<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>连续和</title>
</head>
<body>

<p>获取文本框提交的信息
	<%
		String textContent=request.getParameter("boy");
		byte b[]=textContent.getBytes("ISO-8859-1");
		textContent=new String(b);
	%>
<br>
	<%=textContent %>
<p>获取按钮名称
	<%
		String btnName=request.getParameter("submit");
	%>
<br>
	<%=btnName %>

</body>
</html>