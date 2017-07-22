package lgc.weixin;

/**
 * 微信公众号共同属性
 * 
 * @author lgc
 *
 * @date 2017年7月4日 上午8:54:01
 */
public class WeiXinCommon {

	/***
	 * 公众号的URL 
	 * 
	 * 新浪云的域名 https://newtkwx.applinzi.com/
	 * ngrokcc的域名 http://newtkwx.ngrok.cc
	 * ngrok.plub 域名 http://newtkwx.ngrok.club
	 * 
	 */
	public static final String WEIXIN_URL="http://newtkwx.ngrok.cc/FirstProj/WxServlet";
	public static final String REDIRECT_URI="http://newtkwx.ngrok.cc/FirstProj/OAuthServlet";
	public static final String OAUTH_URL="http://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";

	/**
	 * 公众号的  appID
	 */
	public static final String appID="wxdfead60d5e0dbcd6";
	/**
	 * 公众号的appsecret
	 */
	public static final String appsecret="92663408c73b91e5749a0c5de20bd953";
	
	/**
	 * 从微信服务器中下载的媒体文件存放在本地服务器的根目录
	 */
	public static final String downLoadFilePathComm="D:/soft/Tomcat/tomcat8044/apache-tomcat-8.0.44/webapps/ROOT/WeiXinMedia/usersMedia";
	public static final String downloadQrCode="D:/soft/Tomcat/tomcat8044/apache-tomcat-8.0.44/webapps/ROOT/WeiXinMedia/qrcodes";
	public static final String qrCodeRoot="http://newtkwx.ngrok.cc/WeiXinMedia/qrcodes/";
	
	/**
	 * 百度地图开发服务端ak CA21bdecc75efc1664af5a195c30bb4e 
	 * wk5Al4x4OXuOOjLkB6xpyzsKxBiUrnCl
	 * 
	 * S0D9rYXOzhqervq5kOkwlOFfCPQZSR5H
	 */
	public static final String baiduAk="CA21bdecc75efc1664af5a195c30bb4e";
	
	public static final String wxAk="S0D9rYXOzhqervq5kOkwlOFfCPQZSR5H";
	
}
