package lgc.weixin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lgc.bean.menu.Button;
import lgc.bean.menu.ClickButton;
import lgc.bean.menu.ComplexButton;
import lgc.bean.menu.Menu;
import lgc.bean.menu.ViewButton;
import lgc.bean.pojo.Token;
import lgc.servlet.OAuthServlet;
import lgc.util.AdvancedUtil;
import lgc.util.CommonUtil;
import lgc.util.MenuUtil;

/**
 * 菜单管理类
 * 
 * @author lgc
 *
 * @date 2017年6月9日 上午9:48:56
 */
public class MenuManager {

//	private static Logger log = LoggerFactory.getLogger(MenuManager.class);
	
	/**
	 * 定义菜单
	 * @return
	 */
	private static Menu getMenu(){
		
		ClickButton b11=new ClickButton();
		b11.setName("深度开源");
		b11.setType("click");
		b11.setKey("open-open");
		
		ClickButton b12=new ClickButton();
		b12.setName("Android社区");
		b12.setType("click");
		b12.setKey("android-dev-tool");
		
		ViewButton b21=new ViewButton();
		b21.setName("淘宝");
		b21.setType("view");
		b21.setUrl("http://m.taobao.com");
		
		ViewButton b22=new ViewButton();
		b22.setName("京东");
		b22.setType("view");
		b22.setUrl("http://m.jd.com");
		
		ViewButton b31 = new ViewButton();
		b31.setName("多泡");
		b31.setType("view");
		b31.setUrl("http://www.duopao.com");

		ViewButton b32 = new ViewButton();
		b32.setName("一窝88");
		b32.setType("view");
		b32.setUrl("http://www.yi588.com");
		
		ViewButton b33=new ViewButton();
		String url0=WeiXinCommon.OAUTH_URL;
		String url1=CommonUtil.urlEncodingUTF8(WeiXinCommon.REDIRECT_URI);
		url0=url0.replace("APPID",WeiXinCommon.appID);
		url0=url0.replace("REDIRECT_URI", url1);
		
		b33.setName("身份认证");
		b33.setType("view");
		b33.setUrl(url0);
		
		System.out.println("url0::"+"\n"+url0);
		
		
		ComplexButton mainB1=new ComplexButton();
		mainB1.setName("技术交流");
		mainB1.setSub_button(new Button[]{b11,b12});
		
		ComplexButton mainB2=new ComplexButton();
		mainB2.setName("购物");
		mainB2.setSub_button(new Button[]{b21,b22});
		
		ComplexButton mainB3=new ComplexButton();
		mainB3.setName("游戏娱乐");
		mainB3.setSub_button(new Button[]{b31,b32,b33});
		
		Menu menu=new Menu();
		menu.setButton(new Button[]{mainB1,mainB2,mainB3});
		
		return menu;
	}
	
	
	public static void main(String[] args) {
		//第三方用户唯一凭证appID
		String appID="wxdfead60d5e0dbcd6";
		//第三方用户唯一凭证密钥appsecret
		String appsecret="92663408c73b91e5749a0c5de20bd953";
		
		//通过凭证 appID appsecret获取 access_token
		Token token=CommonUtil.getToken(appID, appsecret);
		
		
		if (token!=null) {
			String accessToken=token.getAccess_token();
			//创建菜单
			boolean createResult=MenuUtil.createMenu(getMenu(), accessToken);
			if (createResult) {
				System.out.println("菜单创建成功");
			}else {
				System.out.println("菜单创建失败");
			}
			
			//组装文本客服消息  oA1HcvwJj9KKuPC6fmRWm8h6Qv4I  oA1Hcv9PfGShFQfsHXEdjQrPGPmQ oA1Hcv3WBD5x2oZd42nR3Ioq0dVs oA1Hcv3fxuC-9dz52m-oDBgFAUPY
			AdvancedUtil advancedUtil=new AdvancedUtil();
			String customMsg=advancedUtil.getAdvancedMethod().makeTextCustomMessage("oA1HcvwJj9KKuPC6fmRWm8h6Qv4I", "糠糠,你在干嘛呀？");
			//发送客服消息
			boolean custom=advancedUtil.getAdvancedMethod().sendCustomMessage(accessToken, customMsg);
			System.out.println("发送客服消息：："+custom);
		}
		
		
	}
	
}
