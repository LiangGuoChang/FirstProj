package lgc.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lgc.bean.pojo.SNSUserInfo;
import lgc.bean.pojo.WeiXinOauth2Token;
import lgc.util.AdvancedUtil;
import lgc.weixin.WeiXinCommon;

/**
 * ��Ȩ��Ļص��������
 * 
 * @author lgc
 *
 * @date 2017��6��27�� ����3:04:41
 */
public class OAuthServlet extends HttpServlet {

	/**
	 * add generated serial Version ID
	 */
	private static final long serialVersionUID = -5888420896380854918L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		System.out.println("OAuthServlet doGet");
		
		//���ñ����ʽ����ֹ���ĳ�������
		req.setCharacterEncoding("gb2312");
		resp.setCharacterEncoding("gb2312");
		
		//�û�ͬ����Ȩ���ܹ���� code
		String code=req.getParameter("code");
		
		System.out.println("code::"+code);
		
		//�û�ͬ����Ȩ
		if (!(code.equals("authdeny"))) {
			//��ȡ��ҳ��Ȩƾ֤ access_token
			AdvancedUtil advancedUtil=new AdvancedUtil();
			WeiXinOauth2Token weiXinOauth2Token=advancedUtil.getAdvancedMethod().getOauth2AccessToken(WeiXinCommon.appID, WeiXinCommon.appsecret, code);
			String accessToken=weiXinOauth2Token.getAccessToken();
			//����û��ı�־
			String openID=weiXinOauth2Token.getOpenId();
			//��ȡ�û���Ϣ
			SNSUserInfo snsUserInfo=advancedUtil.getAdvancedMethod().getSNSUserInfo(accessToken, openID, "zh_CN");
			
			//����Ҫ���ݵ���Ϣ ,���û���Ϣ�ŵ� request �����У��������Դ��ݵ�Ŀ��ҳ��,
			req.setAttribute("snsUserInfo", snsUserInfo);
			//��ת��Ŀ��ҳ��
			req.getRequestDispatcher("snsUserInfo.jsp").forward(req, resp);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}

}
