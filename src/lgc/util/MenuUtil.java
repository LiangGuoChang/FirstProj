package lgc.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lgc.bean.menu.Menu;
import net.sf.json.JSONObject;

/**
 * �Զ���˵�����������
 * 
 * @author lgc
 *
 * @date 2017��6��9�� ����2:29:10
 */
public class MenuUtil {

//	private static Logger log = LoggerFactory.getLogger(MenuUtil.class);
	
	//�����˵�  POST����ʹ��httpsЭ�飩
	private final static String menu_create_url=" https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	//��ѯ�˵�  GET
	private final static String menu_get_url="https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
	//ɾ���˵� GET
	private final static String menu_delete_url="https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
	
	/**
	 * �����˵�
	 * @param menu �˵�����
	 * @param access_token ƾ֤
	 * @return true �ɹ�; false ʧ��
	 *   �ɹ�ʱ����      {"errcode":0,"errmsg":"ok"}
	 *   ʧ��ʱ����      {"errcode":40018,"errmsg":"invalid button name size"}
	 */
	public static boolean createMenu(Menu menu,String access_token){
		boolean result=false;
		String url=menu_create_url.replace("ACCESS_TOKEN", access_token);
		//���˵�����ת��Ϊjson����
		String jsonMenu=JSONObject.fromObject(menu).toString();
		System.out.println("jsonMenu::"+"\n"+jsonMenu);
		//����HTTPS��POST���󣬴����˵�
		JSONObject jsonResult= CommonUtil.httpsRequest(url, "POST", jsonMenu);
		if (jsonResult != null) {
			int errcode=jsonResult.getInt("errcode");
			String errmsg=jsonResult.getString("errmsg");
			if (errcode == 0) {
				result=true;
			}else {
				result=false;
//				log.error("�����˵�ʧ�� errcode:{} errmsg:{}", errcode, errmsg);
				System.out.println("�����˵�ʧ��::"+"\n"+"errcode::"+errcode+"\n"+"errmsg::"+errmsg);
			}
		}
		return result;
	}
 
	/**
	 * ��ѯ�˵�
	 * @param access_token ƾ֤
	 * @return ��ѯ�����ز˵�json���ݣ����򷵻�null
	 */
	public static String getMenu(String access_token){
		String result=null;
		String url=menu_get_url.replace("ACCESS_TOKEN", access_token);
		//����HTTPS��GET���󣬻�ȡ�˵�json����
		JSONObject jsonObject=CommonUtil.httpsRequest(url, "GET", null);
		if (jsonObject !=null) {
			result=jsonObject.toString();
		}
		return result;
	}
	
	
	public static boolean deleteMenu(String access_token){
		boolean result=false;
		String url=menu_delete_url.replace("ACCESS_TOKEN", access_token);
		//����HTTPS��GET����ɾ���˵�
		JSONObject jsonObject=CommonUtil.httpsRequest(url, "GET", null);
		if (jsonObject !=null) {
			int errcode=jsonObject.getInt("errcode");
			String errmsg=jsonObject.getString("errmsg");
			if (errcode == 0) {
				result=true;
			}else {
				result=false;
//				log.error("ɾ���˵�ʧ�� errcode:{} errmsg:{}", errcode, errmsg);
				System.out.println("ɾ���˵�ʧ��");
			}
		}
		return result;
		
	}
	
}
