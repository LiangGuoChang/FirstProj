<%@page import="java.util.StringTokenizer"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>练习</title>
</head>
<body>

<%
	String option[]=new String[7];
	int 题号=0;
	if(!(session.isNew())){
		Integer number=(Integer)session.getAttribute("序号");//获取题号
		if(number==null){
			number=new Integer(0);
		}
		number=new Integer(number.intValue()+1);//题号加1
		session.setAttribute("序号", number);//更新序号
		int i=0;
		String str=(String)session.getAttribute(""+number);//获取行号是number的文本
		if(str==null){
			str="#练习结束#练习结束#练习结束#练习结束#练习结束#再见#";
		}
		StringTokenizer tokenizer=new StringTokenizer(str,"#");//分析文本
		while(tokenizer.hasMoreTokens()){
			option[i]=tokenizer.nextToken();
			i++;
		}
		题号=number.intValue();
		session.setAttribute("答案"+题号, option[5]);//将答案存入session
		out.print("<br>"+"试题"+number+"<br>"+option[0]);
		out.print("<br>请选择您的答案");
		out.print("<form action=test.jsp method=post name=form>");
		out.print("<br>"+"<input type=radio name=R value=A>");
		out.print("A."+option[1]);
		out.print("<br>"+"<input type=radio name=R value=B>");
		out.print("B."+option[2]);
		out.print("<br>"+"<input type=radio name=R value=C>");
		out.print("C."+option[3]);
		out.print("<br>"+"<input type=radio name=R value=D>");
		out.print("D."+option[4]);
		out.print("<br>"+"<input type=submit name=submit value=提交答案>");
		out.print("</form");
	}
%>

<%
	String answer=request.getParameter("R");//获取客户提交的答案
	String 答案=(String)session.getAttribute("答案"+(题号-1));
	if(answer==null){
		answer="您没有给出选择";
	}
	if(answer.equals(答案)){
		Integer score=(Integer)session.getAttribute("score");
		score=new Integer(score.intValue()+1);
		session.setAttribute("score", score);
	}
	out.print("<br>"+"您现在的得分是："+session.getAttribute("score"));
	out.print("<br>"+"您上一题的答案是："+answer);
	out.print("<br>"+"上一题的正确答案是："+答案);
%>

</body>
</html>