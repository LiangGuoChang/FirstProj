<%@page import="lgc.bean.pojo.SNSUserInfo"%>
<%@ page language="java" contentType="text/html; charset=gbk"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width,user-scalable=0">
<title>OAuth2.0��ҳ��Ȩ</title>
</head>
<body>
	 
<%!
	//�ж��Ա�
	public static String getStrSex(int sex){
		if(sex == 1){
			return "��";
		}else if(sex == 2){
			return "Ů";
		}else{
			return "δ֪";
		}
	}
%>

<%
	//��ȡ OAuthServlet ����Ĳ���
	SNSUserInfo snsUserInfo=(SNSUserInfo)request.getAttribute("snsUserInfo");
	if(null != snsUserInfo){
		out.print("�û�ͬ����Ȩ");
%>
	
	<table width="100%" cellpadding="0" cellspacing="0">
	
	<tr> <td width="20%">����</td> <td width="80%">ֵ</td> </tr>
	<tr> <td width="20%">openid</td> <td width="80%"><%=snsUserInfo.getOpenId() %></td> </tr>
	<tr> <td width="20%">�û���</td> <td width="80%"><%=snsUserInfo.getNickName() %></td> </tr>
	<tr> <td width="20%">�Ա�</td> <td width="80%"><%=getStrSex(snsUserInfo.getSex()) %></td> </tr>
	<tr> <td width="20%">����</td> <td width="80%"><%=snsUserInfo.getCountry() %></td> </tr>
	<tr> <td width="20%">ʡ��</td> <td width="80%"><%=snsUserInfo.getProvince() %></td> </tr>
	<tr> <td width="20%">����</td> <td width="80%"><%=snsUserInfo.getCity() %></td> </tr>
	<tr> <td width="20%">��Ȩ</td> <td width="80%"><%=snsUserInfo.getPrivilegeList() %></td> </tr>
	<tr> <td width="20%">ͷ��</td> <td width="80%"><%=snsUserInfo.getHeadImgUrl() %></td> </tr>
	
	</table>
<%
	}else
		out.print("�û���ͬ����Ȩ,δ��ȡ���û���Ϣ��");
%>


</body>
</html>