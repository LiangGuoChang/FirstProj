<%@page import="lgc.bean.pojo.SNSUserInfo"%>
<%@ page language="java" contentType="text/html; charset=gbk"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width,user-scalable=0">
<title>OAuth2.0网页授权</title>
</head>
<body>
	 
<%!
	//判断性别
	public static String getStrSex(int sex){
		if(sex == 1){
			return "男";
		}else if(sex == 2){
			return "女";
		}else{
			return "未知";
		}
	}
%>

<%
	//获取 OAuthServlet 传入的参数
	SNSUserInfo snsUserInfo=(SNSUserInfo)request.getAttribute("snsUserInfo");
	if(null != snsUserInfo){
		out.print("用户同意授权");
%>
	
	<table width="100%" cellpadding="0" cellspacing="0">
	
	<tr> <td width="20%">属性</td> <td width="80%">值</td> </tr>
	<tr> <td width="20%">openid</td> <td width="80%"><%=snsUserInfo.getOpenId() %></td> </tr>
	<tr> <td width="20%">用户名</td> <td width="80%"><%=snsUserInfo.getNickName() %></td> </tr>
	<tr> <td width="20%">性别</td> <td width="80%"><%=getStrSex(snsUserInfo.getSex()) %></td> </tr>
	<tr> <td width="20%">国家</td> <td width="80%"><%=snsUserInfo.getCountry() %></td> </tr>
	<tr> <td width="20%">省份</td> <td width="80%"><%=snsUserInfo.getProvince() %></td> </tr>
	<tr> <td width="20%">城市</td> <td width="80%"><%=snsUserInfo.getCity() %></td> </tr>
	<tr> <td width="20%">特权</td> <td width="80%"><%=snsUserInfo.getPrivilegeList() %></td> </tr>
	<tr> <td width="20%">头像</td> <td width="80%"><%=snsUserInfo.getHeadImgUrl() %></td> </tr>
	
	</table>
<%
	}else
		out.print("用户不同意授权,未获取到用户信息！");
%>


</body>
</html>