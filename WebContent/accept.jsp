
<%@page import="java.io.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>提交文件</title>
</head>
<body>
<%
	try{
		InputStream in=request.getInputStream();
		File f=new File("C:/Users/Administrator/Desktop","english_copy.txt");
		FileOutputStream os=new FileOutputStream(f);
		byte b[]=new byte[1024];
		int n;
		while((n=in.read())!=-1){
			os.write(b, 0, n);
		}
		os.close();
		in.close();
	}catch(IOException e){
		
	}
out.print("文件已上传");
%>


</body>
</html>