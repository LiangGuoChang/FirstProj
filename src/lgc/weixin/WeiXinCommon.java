package lgc.weixin;

/**
 * ΢�Ź��ںŹ�ͬ����
 * 
 * @author lgc
 *
 * @date 2017��7��4�� ����8:54:01
 */
public class WeiXinCommon {

	/***
	 * ���ںŵ�URL 
	 * 
	 * �����Ƶ����� https://newtkwx.applinzi.com/
	 * ngrokcc������ http://newtkwx.ngrok.cc
	 * ngrok.plub ���� http://newtkwx.ngrok.club
	 * 
	 */
	public static final String WEIXIN_URL="http://newtkwx.ngrok.cc/FirstProj/WxServlet";
	public static final String REDIRECT_URI="http://newtkwx.ngrok.cc/FirstProj/OAuthServlet";
	public static final String OAUTH_URL="http://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";

	/**
	 * ���ںŵ�  appID
	 */
	public static final String appID="wxdfead60d5e0dbcd6";
	/**
	 * ���ںŵ�appsecret
	 */
	public static final String appsecret="92663408c73b91e5749a0c5de20bd953";
	
	/**
	 * ��΢�ŷ����������ص�ý���ļ�����ڱ��ط������ĸ�Ŀ¼
	 */
	public static final String downLoadFilePathComm="D:/soft/Tomcat/tomcat8044/apache-tomcat-8.0.44/webapps/ROOT/WeiXinMedia/usersMedia";
	public static final String downloadQrCode="D:/soft/Tomcat/tomcat8044/apache-tomcat-8.0.44/webapps/ROOT/WeiXinMedia/qrcodes";
	public static final String qrCodeRoot="http://newtkwx.ngrok.cc/WeiXinMedia/qrcodes/";
	
	/**
	 * �ٶȵ�ͼ���������ak CA21bdecc75efc1664af5a195c30bb4e 
	 * wk5Al4x4OXuOOjLkB6xpyzsKxBiUrnCl
	 * 
	 * S0D9rYXOzhqervq5kOkwlOFfCPQZSR5H
	 */
	public static final String baiduAk="CA21bdecc75efc1664af5a195c30bb4e";
	
	public static final String wxAk="S0D9rYXOzhqervq5kOkwlOFfCPQZSR5H";
	
}
