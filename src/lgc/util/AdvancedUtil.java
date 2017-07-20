package lgc.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import lgc.bean.pojo.SNSUserInfo;
import lgc.bean.pojo.WeiXinGroups;
import lgc.bean.pojo.WeiXinMedia;
import lgc.bean.pojo.WeiXinOauth2Token;
import lgc.bean.pojo.WeiXinPermanentQRCode;
import lgc.bean.pojo.WeiXinTemporaryQRCode;
import lgc.bean.pojo.WeiXinUserInfo;
import lgc.bean.pojo.WeiXinUserList;
import lgc.bean.response.Article;
import lgc.bean.response.Music;
import lgc.interfaces.AdvancedInterface;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * �߼��ӿ���
 * @author lgc
 *
 * @date 2017��6��26�� ����2:35:52
 */
public class AdvancedUtil {

	/**
	 * @return ���� AdvancedInterface �ӿڵ�ʵ�������
	 */
	public AdvancedInterface getAdvancedMethod(){
		Advanced advanced=new Advanced();
		return advanced;
	}
	
	private class Advanced implements AdvancedInterface{
//		private static Logger log = LoggerFactory.getLogger(AdvancedUtil.class);
		
		/**
		 * ��װ�ı��ͷ���Ϣ
		 * 
		 * @param openId ��Ϣ���͵Ķ���
		 * @param content �ı�����
		 * @return String
		 */
		@Override
		public String makeTextCustomMessage(String openId,String content){
			content=content.replace("\"", "\\\"");
			String jsonMsg="{\"touser\":\"%s\",\"msgtype\":\"text\",\"text\":{\"content\":\"%s\"}}";
			return String.format(jsonMsg, openId,content);
		}
		
		/**
		 * ��װͼƬ�ͷ���Ϣ
		 * 
		 * @param openId ��Ϣ���Ͷ���
		 * @param mediaId ý���ļ�id
		 * @return String
		 */
		@Override
		public String makeImageCustomMessage(String openId, String mediaId) {
			String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"image\",\"image\":{\"media_id\":\"%s\"}}";
			return String.format(jsonMsg, openId, mediaId);
		}
		
		/**
		 * ��װ�����ͷ���Ϣ
		 * 
		 * @param openId ��Ϣ���Ͷ���
		 * @param mediaId ý���ļ�id
		 * @return String
		 */
		@Override
		public String makeVoiceCustomMessage(String openId, String mediaId) {
			String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"voice\",\"voice\":{\"media_id\":\"%s\"}}";
			return String.format(jsonMsg, openId, mediaId);
		}
		
		/**
		 * ��װ��Ƶ�ͷ���Ϣ
		 * 
		 * @param openId ��Ϣ���Ͷ���
		 * @param mediaId ý���ļ�id
		 * @param thumbMediaId ��Ƶ��Ϣ����ͼ��ý��id
		 * @return String
		 */
		@Override
		public String makeVideoCustomMessage(String openId, String mediaId, String thumbMediaId) {
			String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"video\",\"video\":{\"media_id\":\"%s\",\"thumb_media_id\":\"%s\"}}";
			return String.format(jsonMsg, openId, mediaId, thumbMediaId);
		}
		
		/**
		 * ��װ���ֿͷ���Ϣ
		 * 
		 * @param openId ��Ϣ���Ͷ���
		 * @param music ���ֶ���
		 * @return String
		 */
		@Override
		public String makeMusicCustomMessage(String openId, Music music) {
			String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"music\",\"music\":%s}";
			jsonMsg = String.format(jsonMsg, openId, JSONObject.fromObject(music).toString());
			// ��jsonMsg�е�thumbmediaid�滻Ϊthumb_media_id
			jsonMsg = jsonMsg.replace("thumbmediaid", "thumb_media_id");
			return jsonMsg;
		}
		
		/**
		 * ��װͼ�Ŀͷ���Ϣ      (�����ת������)
		 * 
		 * @param openId ��Ϣ���Ͷ���
		 * @param articleList ͼ����Ϣ�б�
		 * @return String
		 */
		@Override
		public String makeNewsCustomMessage(String openId, List<Article> articleList) {
			String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"news\",\"news\":{\"articles\":%s}}";
			jsonMsg = String.format(jsonMsg, openId, JSONArray.fromObject(articleList).toString().replaceAll("\"", "\\\""));
			// ��jsonMsg�е�picUrl�滻Ϊpicurl
			jsonMsg = jsonMsg.replace("picUrl", "picurl");
			return jsonMsg;
		}
		
		/**
		 * ��װͼ�Ŀͷ���Ϣ  (��ת��ͼ��ҳ��)
		 * 
		 * @param openId  ��Ϣ���Ͷ���
		 * @param mediaId ý���ļ�id
		 * @return String
		 */
		@Override
		public String makeMpNewsCustomMessage(String openId,String mediaId){
			String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"mpnews\",\"mpnews\":{\"media_id\":\"%s\"}}";
			return String.format(jsonMsg, openId, mediaId);
		}
		
		/**
		 * ��װ��ȯ�ͷ���Ϣ
		 * 
		 * @param openId  ��Ϣ���Ͷ���
		 * @param cardId  ����id
		 * @return String
		 */
		@Override
		public String makeWxCardCustomMessage(String openId,String cardId){
			String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"wxcard\",\"wxcard\":{\"card_id\":\"%s\"}}";
			return String.format(jsonMsg, openId, cardId);
		}
		
		/**
		 * ���Ϳͷ���Ϣ
		 * 
		 * @param accessToken �ӿڷ���ƾ֤
		 * @param jsonMsg json��ʽ�Ŀͷ���Ϣ������touser��msgtype����Ϣ���ݣ�
		 * @return true | false
		 */
		@Override
		public boolean sendCustomMessage(String accessToken, String jsonMsg) {
//			log.info("��Ϣ���ݣ�{}", jsonMsg);
			boolean result = false;
			// ƴ�������ַ
			String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
			requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
			// ���Ϳͷ���Ϣ
			JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", jsonMsg);

			if (null != jsonObject) {
				int errorCode = jsonObject.getInt("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				if (0 == errorCode) {
					result = true;
//					log.info("�ͷ���Ϣ���ͳɹ� errcode:{} errmsg:{}", errorCode, errorMsg);
					System.out.println("�ͷ���Ϣ���ͳɹ� errcode::"+errorCode+"\n"+"errorMsg::"+ errorMsg);
				} else {
//					log.error("�ͷ���Ϣ����ʧ�� errcode:{} errmsg:{}", errorCode, errorMsg);
					System.out.println("�ͷ���Ϣ���ͳɹ� errcode::"+errorCode+"\n"+"errorMsg::"+ errorMsg);
				}
			}

			return result;
		}
		
		/**
		 * ��ȡ��ҳ��Ȩƾ֤
		 * 
		 * @param appId  ���ںŵ� appID
		 * @param appSecret ���ںŵ� appsecret
		 * @param code �û�ͬ����Ȩ����ȡ��code
		 * @return WeiXinOauth2Token  ������ҳ��Ȩ��Ϣ��ʵ��
		 */
		@Override
		public WeiXinOauth2Token getOauth2AccessToken(String appId,String appSecret,String code){
			WeiXinOauth2Token wot=null;
			//ƴ��������ַ
			String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
			requestUrl=requestUrl.replace("APPID", appId);
			requestUrl=requestUrl.replace("SECRET", appSecret);
			requestUrl=requestUrl.replace("CODE", code);
			//��ȡ��ҳ��Ȩƾ֤ 
			JSONObject jsonObject=CommonUtil.httpsRequest(requestUrl, "GET", null);
			if (jsonObject != null) {
				try {
					wot=new WeiXinOauth2Token();
					wot.setAccessToken(jsonObject.getString("access_token"));
					wot.setExpiresIn(jsonObject.getInt("expires_in"));
					wot.setOpenId(jsonObject.getString("openid"));
					wot.setRefreshToken(jsonObject.getString("refresh_token"));
					wot.setScope(jsonObject.getString("scope"));
				} catch (Exception e) {
					wot=null;
					int errcode=jsonObject.getInt("errcode");
					String errmsg=jsonObject.getString("errmsg");
					System.out.println("��ȡ��ҳ��Ȩƾ֤ʧ��"+"\n"+" errcode::"+errcode+"\n"+"errorMsg::"+ errmsg);
				}
				
			}
			
			return wot;
		}
		
		/**
		 * ˢ����ҳ��Ȩƾ֤
		 *  
		 * @param appId ���ںŵ� appID
		 * @param refreshToken  ���ںŵ� appsecret
		 * @return WeiXinOauth2Token  ������ҳ��Ȩ��Ϣ��ʵ��
		 */
		@Override
		public WeiXinOauth2Token refreshOauth2AccessToken(String appId,String refreshToken){
			WeiXinOauth2Token wot=null;
			//ƴ��������ַ 
			String requestUrl="https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
			requestUrl=requestUrl.replace("APPID", appId);
			requestUrl=requestUrl.replace("REFRESH_TOKEN", refreshToken);
			//ˢ����ҳ��Ȩƾ֤ 
			JSONObject jsonObject=CommonUtil.httpsRequest(requestUrl, "GET", null);
			if (jsonObject != null) {
				try {
					wot=new WeiXinOauth2Token();
					wot.setAccessToken(jsonObject.getString("access_token"));
					wot.setExpiresIn(jsonObject.getInt("expires_in"));
					wot.setOpenId(jsonObject.getString("openid"));
					wot.setRefreshToken(jsonObject.getString("refresh_token"));
					wot.setScope(jsonObject.getString("scope"));
				} catch (Exception e) {
					wot=null;
					int errcode=jsonObject.getInt("errcode");
					String errmsg=jsonObject.getString("errmsg");
					System.out.println("ˢ����ҳ��Ȩƾ֤ʧ��"+"\n"+" errcode::"+errcode+"\n"+"errorMsg::"+ errmsg);
				}
				
			}
			
			return wot;
		}
		
		/**
		 * ͨ����ҳ��Ȩ��ȡ�û���Ϣ
		 * 
		 * @param accessToken  ��ҳ��Ȩ�ӿڵ���ƾ֤
		 * @param openId �û���Ψһ��ʶ
		 * @param language ���ع��ҵ������԰汾��zh_CN ���壬zh_TW ���壬en Ӣ��
		 * @return SNSUserInfo �����û�ʵ������
		 */
		@Override
		public SNSUserInfo  getSNSUserInfo(String accessToken,String openId,String language){
			SNSUserInfo user=null;
			//ƴ��������ַ      lang ��ʾ���ع��ҵ��������汾   zh_CN �������� ��zh_TW ���� ���� ��en Ӣ��
			String requestUrl="https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
			requestUrl=requestUrl.replace("ACCESS_TOKEN", accessToken);
			requestUrl=requestUrl.replace("OPENID", openId);
			requestUrl=requestUrl.replace("zh_CN", language);
			//��ȡ�û���Ϣ
			JSONObject jsonObject=CommonUtil.httpsRequest(requestUrl, "GET", null);
			if (jsonObject !=null) {
				try {
					user=new SNSUserInfo();
					user.setOpenId(jsonObject.getString("openid"));
					user.setNickName(jsonObject.getString("nickname"));
					user.setSex(jsonObject.getInt("sex"));
					user.setProvince(jsonObject.getString("province"));
					user.setCity(jsonObject.getString("city"));
					user.setCountry(jsonObject.getString("country"));
					user.setHeadImgUrl(jsonObject.getString("headimgurl"));
					user.setPrivilegeList(JSONArray.toList(jsonObject.getJSONArray("privilege"),List.class));
				} catch (Exception e) {
					user=null;
					int errcode=jsonObject.getInt("errcode");
					String errmsg=jsonObject.getString("errmsg");
					System.out.println("ˢ����ҳ��Ȩƾ֤ʧ��"+"\n"+" errcode::"+errcode+"\n"+"errorMsg::"+ errmsg);
				}
			}
			
			return user;
		}
		
		/**
		 * ������ʱ��������ά��
		 * 
		 * @param accessToken  �ӿڷ���ƾ֤ (CommonUtil.getToken)
		 * @param expireSeconds  �ö�ά����Чʱ�䣬����Ϊ��λ�� ��󲻳���2592000����30�죩�����ֶ���������Ĭ����Ч��Ϊ30�롣
		 * @param sceneId  ����ֵID����ʱ��ά��ʱΪ32λ��0����
		 * @return WeiXinTemporaryQRCode ����ʵ��
		 */
		@Override
		public WeiXinTemporaryQRCode createTemporaryQRCode(String accessToken,int expireSeconds,int sceneId){
			WeiXinTemporaryQRCode weiXinQRCode=null;
			//ƴ��������ַ
			String requestUrl="https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKENPOST";
			requestUrl=requestUrl.replace("TOKENPOST", accessToken);
			//��Ҫ�ύ����
			String jsonMsg="{\"expire_seconds\": %d, \"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": %d}}}";
			//��ȡ��ά����Ϣ
			JSONObject jsonObject=CommonUtil.httpsRequest(requestUrl, "POST", String.format(jsonMsg, expireSeconds,sceneId));
			if (jsonObject != null) {
				try {
					weiXinQRCode=new WeiXinTemporaryQRCode();
					weiXinQRCode.setTicket(jsonObject.getString("ticket"));
					weiXinQRCode.setExpireSeconds(jsonObject.getInt("expire_seconds"));
					weiXinQRCode.setUrl(jsonObject.getString("url"));
					
					System.out.println("������ʱ��������ά��ɹ���");
					System.out.println(jsonObject.toString());
					
				} catch (Exception e) {
					weiXinQRCode=null;
					int errcode=jsonObject.getInt("errcode");
					String errmsg=jsonObject.getString("errmsg");
					System.out.println("������ʱ��������ά��ʧ��"+"\n"+" errcode::"+errcode+"\n"+"errorMsg::"+ errmsg);
				}
			}
			return weiXinQRCode;
			
		}
		
		/**
		 * �������ô�������ά��
		 * sceneId ����ֵIDΪ  int ����
		 * @param accessToken �ӿڷ���ƾ֤ (CommonUtil.getToken)
		 * @param sceneId ����ֵID ���ö�ά��ʱ���ֵΪ100000��Ŀǰ����ֻ֧��1--100000��
		 * @return WeiXinPermanentQRCode ����ʵ��
		 * 
		 * @�����ύ POST �Ĳ��� �� {"action_name": "QR_LIMIT_SCENE", "action_info": {"scene": {"scene_id": 123}}}
		 */
		@Override
		public WeiXinPermanentQRCode createPermanentQRCode(String accessToken,int sceneId){
			WeiXinPermanentQRCode weiXinQRCode=null;
			//ƴ��������ַ
			String requestUrl="https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKENPOST";
			requestUrl=requestUrl.replace("TOKENPOST", accessToken);
			//��Ҫ�ύ�Ĳ���
			String jsonMsg="{\"action_name\": \"QR_LIMIT_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": %d}}}";
			//��ȡ��ά����Ϣ
			JSONObject jsonObject=CommonUtil.httpsRequest(requestUrl, "POST", String.format(jsonMsg, sceneId));
			
			if (jsonObject != null) {
				try {
					weiXinQRCode=new WeiXinPermanentQRCode();
					weiXinQRCode.setTicket(jsonObject.getString("ticket"));
					weiXinQRCode.setUrl(jsonObject.getString("url"));
					
					System.out.println("�������ô�������ά��ɹ���");
					System.out.println(jsonObject.toString());
					
				} catch (Exception e) {
					weiXinQRCode=null;
					int errcode=jsonObject.getInt("errcode");
					String errmsg=jsonObject.getString("errmsg");
					System.out.println("�������ô�������ά��ʧ��"+"\n"+" errcode::"+errcode+"\n"+"errorMsg::"+ errmsg);
					System.out.println(e.getMessage());
				}
			}
			return weiXinQRCode;
			
		}
		
		/**
		 * �������ô�������ά��
		 * sceneStr ����ֵIDΪ  String ����
		 * 
		 * @param accessToken �ӿڷ���ƾ֤  (CommonUtil.getToken)
		 * @param sceneStr ����ֵID���ַ�����ʽ��ID�����ַ������ͣ���������Ϊ1��64�������ö�ά��֧�ִ��ֶ�   
		 * @return WeiXinPermanentQRCode ����ʵ��
		 * 
		 *  @�����ύ POST �Ĳ��� �� {"action_name": "QR_LIMIT_STR_SCENE", "action_info": {"scene": {"scene_str": "123"}}}
		 */
		@Override
		public WeiXinPermanentQRCode createPermanentQRCode(String accessToken,String sceneStr){
			WeiXinPermanentQRCode weiXinQRCode=null;
			//ƴ��������ַ
			String requestUrl="https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKENPOST";
			requestUrl=requestUrl.replace("TOKENPOST", accessToken);
			//��Ҫ�ύ�Ĳ��� 
			String jsonMsg="{\"action_name\": \"QR_LIMIT_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": %s}}}";
			//��ȡ��ά����Ϣ
			JSONObject jsonObject=CommonUtil.httpsRequest(requestUrl, "POST", String.format(jsonMsg, sceneStr));
			if (jsonObject != null) {
				try {
					weiXinQRCode=new WeiXinPermanentQRCode();
					weiXinQRCode.setTicket(jsonObject.getString("ticket"));
					weiXinQRCode.setUrl(jsonObject.getString("url"));
					
					System.out.println("�������ô�������ά��ɹ���");
					System.out.println(jsonObject.toString());
					
				} catch (Exception e) {
					weiXinQRCode=null;
					int errcode=jsonObject.getInt("errcode");
					String errmsg=jsonObject.getString("errmsg");
					System.out.println("�������ô�������ά��ʧ��"+"\n"+" errcode::"+errcode+"\n"+"errorMsg::"+ errmsg);
				}
			}
			return weiXinQRCode;
			
		}
		
		/**
		 * ���� ticket ��ȡ��ά��
		 * 
		 * @param ticket ��ά��ticket
		 * @param savePath ����·��
		 * @return filePath �ļ���
		 */
		@Override
		public String getQRCode(String ticket,String savePath){
			String filePath=null;
			//ƴ��������ַ
			String requestUrl="https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";
			//�� ticket ���� URL ���룬�Ա�����ֿո�
			ticket=CommonUtil.urlEncodingUTF8(ticket);
			requestUrl=requestUrl.replace("TICKET", ticket);
			try {
				URL url=new URL(requestUrl);
				HttpsURLConnection connection=(HttpsURLConnection) url.openConnection();
				connection.setDoInput(true);
				connection.setRequestMethod("GET");
				if (!savePath.endsWith("/")) {
					savePath+="/";
				}
				//�� ticket ��Ϊ�ļ���
				filePath=savePath+ticket+".jpg";
				//��΢�ŷ��������ص���������д���ļ���
				InputStream in=connection.getInputStream();
				BufferedInputStream bStream=new BufferedInputStream(in);
				FileOutputStream fos=new FileOutputStream(new File(filePath));
				byte[] bytes=new byte[1024*4];
				int size=0;
				while ((size=bStream.read(bytes))!=-1) {
					fos.write(bytes, 0, size);
				}
				
				fos.close();
				bStream.close();
				in.close();
				connection.disconnect();
				
				System.out.println("����ticket��ȡ��ά��ɹ�"+"\n"+"filePath::"+filePath);
				
			} catch (Exception e) {
				filePath=null;
				System.out.println("����ticket��ȡ��ά��ʧ��::"+e.getMessage());
			}
			
			
			return filePath;
			
		}
		
		/**
		 * ��ȡ�û�������Ϣ
		 * 
		 * @param accessToken �ӿڷ���ƾ֤  (CommonUtil.getToken)
		 * @param openId �û��ı�ʶ
		 * @return WeiXinUserInfo �����û���Ϣ����ʵ��
		 */
		@Override
		public WeiXinUserInfo getUserInfo(String accessToken,String openId){
			WeiXinUserInfo userInfo=null;
			//ƴ�������ַ
			String requestUrl="https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
			requestUrl=requestUrl.replace("ACCESS_TOKEN", accessToken);
			requestUrl=requestUrl.replaceAll("OPENID", openId);
			
			System.out.println("requestUrl::"+requestUrl);
			
			//�������󣬻�ȡ�û���Ϣ
			JSONObject jsonObject=CommonUtil.httpsRequest(requestUrl, "GET", null);
			if (jsonObject != null) {
				try {
					userInfo=new WeiXinUserInfo();
					
					if (jsonObject.containsKey("unionid")) {
						userInfo.setUnionId(jsonObject.getString("unionid"));
					}else {
						userInfo.setUnionId("���ں�δ�󶨵�΢�ſ���ƽ̨");
					}
					userInfo.setSubscribe(jsonObject.getInt("subscribe"));
					userInfo.setOpenId(jsonObject.getString("openid"));
					userInfo.setNickName(jsonObject.getString("nickname"));
					userInfo.setSex(jsonObject.getInt("sex"));
					userInfo.setCity(jsonObject.getString("city"));
					userInfo.setProvince(jsonObject.getString("province"));
					userInfo.setCountry(jsonObject.getString("country"));
					userInfo.setLanguage(jsonObject.getString("language"));
					userInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
					userInfo.setSubscribeTime(jsonObject.getString("subscribe_time"));
					
					
					userInfo.setRemark(jsonObject.getString("remark"));
					userInfo.setGroupId(jsonObject.getString("groupid"));
					userInfo.setTagIdList(JSONArray.toList(jsonObject.getJSONArray("tagid_list"),List.class));
					
					System.out.println("��ȡ�û���Ϣ�ɹ���");
					System.out.println("getUserInfo::"+jsonObject.toString());
					
				} catch (Exception e) {
					userInfo=null;
					if (jsonObject.getInt("subscribe") == 0) {
						System.out.println("�û�δ��ע�ù��ںţ�"+"\n"+"openid::"+jsonObject.getString("openid"));
						System.out.println("unionid::"+jsonObject.getString("unionid"));
					}else {
						int errcode=jsonObject.getInt("errcode");
						String errmsg=jsonObject.getString("errmsg");
						System.out.println("��ȡ�û���Ϣʧ�ܣ���"+"\n"+"errcode::"+errcode+"\n"+"errmsg::"+errmsg);
						
						System.out.println("getUserInfo::"+jsonObject.toString());
						
						System.out.println("��ȡ�û���Ϣʧ��::"+e.getMessage());
					}
				}
			}
			return userInfo;
		}
		
		 
		/**
		 * ��ȡ��ע�û��б�
		 * 
		 * @param accessToken �ӿ�ƾ֤
		 * @param nextOpenId ��һ����ȡ��OPENID������Ĭ�ϴ�ͷ��ʼ��ȡ
		 * @return ����WeiXinUserListʵ������
		 */
		@Override
		public WeiXinUserList getWeiXinUserList(String accessToken,String nextOpenId){
			WeiXinUserList userList=null;
			//ƴ��������ַ
			if (nextOpenId == null) {
				nextOpenId="";
			}
			String requestUrl="https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
			requestUrl=requestUrl.replace("ACCESS_TOKEN", accessToken);
			requestUrl=requestUrl.replace("NEXT_OPENID", nextOpenId);
			//��������
			JSONObject jsonObject=CommonUtil.httpsRequest(requestUrl, "GET", null);
			if (jsonObject != null) {
				try {
					userList=new WeiXinUserList();
					userList.setTotal(jsonObject.getInt("total"));
					userList.setCount(jsonObject.getInt("count"));
					userList.setData(JSONArray.toList(jsonObject.getJSONObject("data").getJSONArray("openid"), List.class));
					userList.setNextOpenId(jsonObject.getString("next_openid"));
					
					System.out.println("��ȡ��ע�û��б�ɹ���");
					System.out.println("getWeiXinUserList::"+jsonObject.toString());
					
				} catch (Exception e) {
					userList=null;
					int errcode=jsonObject.getInt("errcode");
					String errmsg=jsonObject.getString("errmsg");
					System.out.println("��ȡ��ע�û��б�ʧ�ܣ���"+"\n"+"errcode::"+errcode+"\n"+"errmsg::"+errmsg);
					
					System.out.println("getUserInfo::"+jsonObject.toString());
					
					System.out.println("��ȡ��ע�û��б�ʧ��::"+e.getMessage());
				}
			}
			
			return userList;
		}
		
		/**
		 * ��ȡ�û�������Ϣ
		 * 
		 * @param accessToken �ӿ�ƾ֤
		 * @return ���� List<WeiXinGroups> �����б�
		 */
		@Override
		public List<WeiXinGroups> getWeiXinGroups(String accessToken){
			List<WeiXinGroups> groups=null;
			//ƴ��������ַ
			String requestUrl="https://api.weixin.qq.com/cgi-bin/groups/get?access_token=ACCESS_TOKEN";
			requestUrl=requestUrl.replace("ACCESS_TOKEN", accessToken);
			//��������
			JSONObject jsonObject=CommonUtil.httpsRequest(requestUrl, "GET", null);
			if (jsonObject != null) {
				try {
					groups=JSONArray.toList(jsonObject.getJSONArray("groups"),WeiXinGroups.class);
					
					System.out.println("��ѯ����ɹ���");
					System.out.println("getWeiXinGroups::"+jsonObject.toString());
				} catch (Exception e) {
					groups=null;
					int errcode=jsonObject.getInt("errcode");
					String errmsg=jsonObject.getString("errmsg");
					System.out.println("��ѯ����ʧ�ܣ���"+"\n"+"errcode::"+errcode+"\n"+"errmsg::"+errmsg);
					
					System.out.println("getUserInfo::"+jsonObject.toString());
					
					System.out.println("��ѯ����ʧ��::"+e.getMessage());
				}
			}
			return groups;
		}
		
		/**
		 * ��������
		 * @˵��  ��Ҫ POST ���� {"group":{"name":"groupName"}}
		 * 
		 * @param accessToken �ӿ�ƾ֤
		 * @param groupName ��������
		 * @return  WeiXinGroups ���ط���ʵ������
		 */
		@Override
		public WeiXinGroups createGroup(String accessToken,String groupName){
			WeiXinGroups groups=null;
			//ƴ��������ַ
			String requestUrl = "https://api.weixin.qq.com/cgi-bin/groups/create?access_token=ACCESS_TOKEN";
			requestUrl=requestUrl.replace("ACCESS_TOKEN", accessToken);
			//��Ҫ�ύ������
			String postStr="{\"group\":{\"name\":\"%s\"}}";
			postStr=String.format(postStr, groupName);
			//��������
			JSONObject jsonObject=CommonUtil.httpsRequest(requestUrl, "POST", postStr);
			if (jsonObject != null) {
				try {
					groups=new WeiXinGroups();
					groups.setId(jsonObject.getJSONObject("group").getInt("id"));
					groups.setName(jsonObject.getJSONObject("group").getString("name"));
					
					System.out.println("��������ɹ���");
					System.out.println("createGroup::"+jsonObject.toString());
				} catch (Exception e) {
					groups=null;
					int errcode=jsonObject.getInt("errcode");
					String errmsg=jsonObject.getString("errmsg");
					System.out.println("��������ʧ�ܣ���"+"\n"+"errcode::"+errcode+"\n"+"errmsg::"+errmsg);
					
					System.out.println("getUserInfo::"+jsonObject.toString());
					
					System.out.println("��������ʧ��::"+e.getMessage());
				}
			}
			return groups;
		}
		
		/**
		 * �޸�ָ�� id �ķ���Ķ�Ӧ����
		 * @˵��  ��Ҫ POST ���� {"group":{"id":108,"name":"groupName"}}
		 * 
		 * @param accessToken �ӿ�ƾ֤
		 * @param groupId �����id
		 * @param groupName �޸ĺ�ķ�������
		 * @return ���� true ��ʾ�޸ĳɹ�;false ��ʾ�޸�ʧ��
		 */
		@Override
		public boolean updateGroup(String accessToken,int groupId,String groupName){
			boolean result=false;
			//ƴ��������ַ
			String requestUrl = "https://api.weixin.qq.com/cgi-bin/groups/update?access_token=ACCESS_TOKEN";
			requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
			// ��Ҫ POST ��json����
			String jsonData = "{\"group\": {\"id\": %d, \"name\": \"%s\"}}";
			jsonData=String.format(jsonData, groupId,groupName);
			//��������
			JSONObject jsonObject=CommonUtil.httpsRequest(requestUrl, "POST", jsonData);
			if (jsonObject != null) {
				int errcode=jsonObject.getInt("errcode");
				String errmsg=jsonObject.getString("errmsg");
				if (errcode == 0) {
					result=true;
					System.out.println("�޸ķ���ɹ�����"+"\n"+"errcode::"+errcode+"\n"+"errmsg::"+errmsg);
				}else {
					result=false;
					System.out.println("�޸ķ���ʧ�ܣ���"+"\n"+"errcode::"+errcode+"\n"+"errmsg::"+errmsg);
				}
			}
			return result;
		}
		
		/**
		 * �ƶ�ָ��openId���û���ָ��groupId�ķ�����
		 * 
		 * @param accessToken �ӿ�ƾ֤
		 * @param opendId �û���ʶ
		 * @param groupId �����id
		 * @return true ��ʾ�ƶ��ɹ�,false ��ʾ�ƶ�ʧ��
		 * 
		 * @˵�� ��Ҫ�ύ��json����  "{\"openid\":\"%s\",\"to_groupid\":%d}"
		 */
		@Override
		public boolean moveMemberGroup(String accessToken,String openId,int groupId){
			boolean result=false;
			// ƴ�������ַ
			String requestUrl = "https://api.weixin.qq.com/cgi-bin/groups/members/update?access_token=ACCESS_TOKEN";
			requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
			// ��Ҫ�ύ��json����
			String jsonData = "{\"openid\":\"%s\",\"to_groupid\":%d}";
			jsonData=String.format(jsonData, openId,groupId);
			//��������
			JSONObject jsonObject=CommonUtil.httpsRequest(requestUrl, "POST", jsonData);
			if (jsonObject != null) {
				int errcode=jsonObject.getInt("errcode");
				String errmsg=jsonObject.getString("errmsg");
				if (errcode == 0) {
					result=true;
					System.out.println("�ƶ��û��ɹ�����"+"\n"+"errcode::"+errcode+"\n"+"errmsg::"+errmsg);
				}else {
					result=false;
					System.out.println("�ƶ��û�ʧ�ܣ���"+"\n"+"errcode::"+errcode+"\n"+"errmsg::"+errmsg);
				}
			}
			return result;
		}
		
		/**
		 * �ϴ�ý���ļ���΢�ŷ�����
		 * 
		 * @param accessToken �ӿ�ƾ֤
		 * @param type ý���ļ�����( ��ͼƬ��image����������voice������Ƶ��video��������ͼ��thumb��)
		 * @param mediaFileUrl ý���ļ���·��
		 * @return WeiXinMedia ����WeiXinMedia����ʵ��
		 * 
		 * @˵�� http����ʽ��POST/FORM��ʹ��https
		 * Ŀǰֻ�ɹ��ϴ��� image ����
		 * 
		 */
		@Override
		public WeiXinMedia uploadTemporaryMedia(String accessToken, String type, String mediaFileUrl){
			WeiXinMedia weiXinMedia=null;
			//ƴ��������ַ
			String requestUrl="http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
			requestUrl=requestUrl.replace("ACCESS_TOKEN", accessToken).replace("TYPE", type);
			// �������ݷָ���
//			String boundary = "------------"+CommonUtil.getUUID();
			String boundary = "------------"+System.currentTimeMillis();

			try {
				URL url=new URL(requestUrl);
				HttpURLConnection connection=(HttpURLConnection) url.openConnection();
				connection.setDoOutput(true);
				connection.setDoInput(true);
				connection.setRequestMethod("POST");
				connection.setUseCaches(false);//post��ʽ����ʹ�û���
				//��������ͷContent-Type
				connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
				//��ȡý���ļ��ϴ��������(����΢�ŷ�����д����)
				OutputStream outputStream=connection.getOutputStream();
				
				//��ȡ���ý���ļ�
				URL mediaUrl=new URL(mediaFileUrl);
				HttpURLConnection mediaConn=(HttpURLConnection) mediaUrl.openConnection();
				mediaConn.setDoOutput(true);
				mediaConn.setRequestMethod("GET");
				//������ͷ�л�ȡ����
				String content_type=mediaConn.getHeaderField("Content-Type");
				String header=mediaConn.getHeaderField(null);
				
				System.out.println("header::"+header);
				System.out.println("getContenttype::"+mediaConn.getContentType());
//				content_type="audio/amr";
				System.out.println("content_type::"+content_type);
				
				//���������ж��ļ���չ��
				String fileExt=CommonUtil.getFileExt(content_type);
				
				//�����忪ʼ����д��΢�ŷ�����
				outputStream.write(("--" + boundary + "\r\n").getBytes());
//				String Disposition=String.format("Content-Disposition: form-data; name=\"media\"; filename=\"file1%s\"\r\n", fileExt);
				String Disposition=String.format("Content-Disposition: form-data; name=\"media\"; filename=\"file3%s\"\r\n", fileExt);
				String Type=String.format("Content-Type: %s\r\n\r\n", content_type);
				System.out.println("Disposition::"+Disposition);
				System.out.println("Type::"+Type);
				outputStream.write(String.format("Content-Disposition: form-data; name=\"media\"; filename=\"file3%s\"\r\n", fileExt).getBytes());
				outputStream.write(String.format("Content-Type: %s\r\n\r\n", content_type).getBytes());
				
				//ý���ļ�����д��΢�ŷ�����
				BufferedInputStream bis=new BufferedInputStream(mediaConn.getInputStream());
				byte[] bytes=new byte[8096];
				int size=0;
				while ((size=bis.read(bytes))!=-1) {
					outputStream.write(bytes, 0, size);
				}
				
				//�������������д��΢�ŷ�����
				outputStream.write(("\r\n"+"--"+ boundary + "--\r\n").getBytes());
				outputStream.flush();
				outputStream.close();
				bis.close();
				mediaConn.disconnect();
				
				//��ȡý���ļ��ϴ���������(��΢�ŷ������ж�ȡ)
				InputStream inputStream=connection.getInputStream();
				InputStreamReader inputStreamReader=new InputStreamReader(inputStream,"UTF-8");
				BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
				StringBuffer buffer=new StringBuffer();
				String str=null;
				
				while (true) {
					System.out.println("available::"+inputStream.available());
					if (inputStream.available()>0) {
						while ((str=bufferedReader.readLine())!=null) {
							buffer.append(str);
						}
					}else if(inputStream.available()==0){
						System.out.println("����������������ж�");
						break;
					}
				}
				
				bufferedReader.close();
				inputStreamReader.close();
				//�ͷ���Դ
				inputStream.close();
				inputStream=null;
				
				System.out.println("buffer::"+buffer.toString());
				
				//�����������õ����ؽ��
				JSONObject jsonObject=JSONObject.fromObject(buffer.toString());
				
				System.out.println("jsonObject::"+jsonObject);
				
				weiXinMedia=new WeiXinMedia();
				weiXinMedia.setMediaType(jsonObject.getString("type"));
				if ("thumb".equals(type)) {
					weiXinMedia.setMediaId(jsonObject.getString("thumb_media_id"));
				}else {
					weiXinMedia.setMediaId(jsonObject.getString("media_id"));
				}
				weiXinMedia.setCreateAt(jsonObject.getInt("created_at"));
				
				System.out.println("uploadTemporaryMedia--�ɹ��ϴ���ʱ�ļ�����"+"\n"+jsonObject);
				
			}catch (SocketException e) {
				System.out.println("uploadTemporaryMedia--�ϴ���ʱ�ļ�ʧ�ܣ���"+"\n"+e.toString());
			}catch (Exception e) {
				weiXinMedia=null;
				System.out.println("uploadTemporaryMedia--�ϴ���ʱ�ļ�ʧ�ܣ���"+"\n"+e.toString());
			}
			
			return weiXinMedia;
		}
		
		/**
		 * ��΢�ŷ����������ض�Ӧ mediaId ��ý���ļ�
		 * 
		 * @param accessToken �ӿ�ƾ֤
		 * @param mediaId ý���ļ�id
		 * @param savePath �����ļ�λ��
		 * @return filePath �����ļ���·��
		 * 
		 * @˵�� �������û����͹�����ý���ļ�
		 */
		@Override
		public String downLoadMedia(String accessToken, String mediaId, String savePath){
			String filePath=null;
			// ƴ�������ַ
			String requestUrl = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
			requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("MEDIA_ID", mediaId);
			try {
				URL url=new URL(requestUrl);
				HttpURLConnection connection=(HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");
				connection.setDoInput(true);
				if (!savePath.endsWith("/")) {
					savePath+="/";
				}
				//�����������ͻ�ȡ�ļ���չ������Ϊ�ļ���
				String fileExt=CommonUtil.getFileExt(connection.getHeaderField("Content-Type"));
				filePath=savePath+mediaId+fileExt;
				
				InputStream inputStream=connection.getInputStream();
				BufferedInputStream bis=new BufferedInputStream(inputStream);
				FileOutputStream fileOutputStream=new FileOutputStream(new File(filePath));
				int size=0;
				byte bytes[]=new byte[1024*4];
				while ((size=bis.read(bytes)) != -1) {
					fileOutputStream.write(bytes, 0, size);
				}
				//�ͷ���Դ
				fileOutputStream.close();
				bis.close();
				inputStream.close();
				inputStream=null;
				connection.disconnect();
				System.out.println("�ļ����سɹ�-filePath::"+filePath);
			} catch (Exception e) {
				filePath=null;
				System.out.println("�ļ�����ʧ��-filePath::"+filePath);
				System.out.println("downLoadMedia::"+e.toString());
			}
			
			return filePath;
		}

		/**
		 * ���Է���
		 */
		@Override
		public String getHello(){
			return "getHello";
		}
	}
	
}
