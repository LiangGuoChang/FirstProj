package lgc.interfaces;

import java.util.List;

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

/**
 * ���ںŸ߼��ӿڽӿ���
 * @author lgc
 *
 * 2017��7��20�� ����5:47:40
 */
public interface AdvancedInterface {

	/**
	 * ��װ�ı��ͷ���Ϣ
	 * 
	 * @param openId ��Ϣ���͵Ķ���
	 * @param content �ı�����
	 * @return String
	 */
	String makeTextCustomMessage(String openId,String content);
	
	/**
	 * ��װͼƬ�ͷ���Ϣ
	 * 
	 * @param openId ��Ϣ���Ͷ���
	 * @param mediaId ý���ļ�id
	 * @return String
	 */
	String makeImageCustomMessage(String openId, String mediaId);
	
	/**
	 * ��װ�����ͷ���Ϣ
	 * 
	 * @param openId ��Ϣ���Ͷ���
	 * @param mediaId ý���ļ�id
	 * @return String
	 */
	String makeVoiceCustomMessage(String openId, String mediaId);
	
	/**
	 * ��װ��Ƶ�ͷ���Ϣ
	 * 
	 * @param openId ��Ϣ���Ͷ���
	 * @param mediaId ý���ļ�id
	 * @param thumbMediaId ��Ƶ��Ϣ����ͼ��ý��id
	 * @return String
	 */
	String makeVideoCustomMessage(String openId, String mediaId, String thumbMediaId);
	
	/**
	 * ��װ���ֿͷ���Ϣ
	 * 
	 * @param openId ��Ϣ���Ͷ���
	 * @param music ���ֶ���
	 * @return String
	 */
	String makeMusicCustomMessage(String openId, Music music);
	
	/**
	 * ��װͼ�Ŀͷ���Ϣ      (�����ת������)
	 * 
	 * @param openId ��Ϣ���Ͷ���
	 * @param articleList ͼ����Ϣ�б�
	 * @return String
	 */
	String makeNewsCustomMessage(String openId, List<Article> articleList);
	
	/**
	 * ��װͼ�Ŀͷ���Ϣ  (��ת��ͼ��ҳ��)
	 * 
	 * @param openId  ��Ϣ���Ͷ���
	 * @param mediaId ý���ļ�id
	 * @return String
	 */
	String makeMpNewsCustomMessage(String openId,String mediaId);
	
	/**
	 * ��װ��ȯ�ͷ���Ϣ
	 * 
	 * @param openId  ��Ϣ���Ͷ���
	 * @param cardId  ����id
	 * @return String
	 */
	String makeWxCardCustomMessage(String openId,String cardId);
	
	/**
	 * ���Ϳͷ���Ϣ
	 * 
	 * @param accessToken �ӿڷ���ƾ֤
	 * @param jsonMsg json��ʽ�Ŀͷ���Ϣ������touser��msgtype����Ϣ���ݣ�
	 * @return true | false
	 */
	boolean sendCustomMessage(String accessToken, String jsonMsg);
	
	/**
	 * ��ȡ��ҳ��Ȩƾ֤
	 * 
	 * @param appId  ���ںŵ� appID
	 * @param appSecret ���ںŵ� appsecret
	 * @param code �û�ͬ����Ȩ����ȡ��code
	 * @return WeiXinOauth2Token  ������ҳ��Ȩ��Ϣ��ʵ��
	 */
	WeiXinOauth2Token getOauth2AccessToken(String appId,String appSecret,String code);
	
	/**
	 * ˢ����ҳ��Ȩƾ֤
	 *  
	 * @param appId ���ںŵ� appID
	 * @param refreshToken  ���ںŵ� appsecret
	 * @return WeiXinOauth2Token ������ҳ��Ȩ��Ϣ��ʵ��
	 */
	WeiXinOauth2Token refreshOauth2AccessToken(String appId,String refreshToken);
	
	/**
	 * ͨ����ҳ��Ȩ��ȡ�û���Ϣ
	 * 
	 * @param accessToken  ��ҳ��Ȩ�ӿڵ���ƾ֤
	 * @param openId �û���Ψһ��ʶ
	 * @param language ���ع��ҵ������԰汾��zh_CN ���壬zh_TW ���壬en Ӣ��
	 * @return SNSUserInfo �����û�ʵ������
	 */
	SNSUserInfo  getSNSUserInfo(String accessToken,String openId,String language);
	
	/**
	 * ������ʱ��������ά��
	 * 
	 * @param accessToken  �ӿڷ���ƾ֤ (CommonUtil.getToken)
	 * @param expireSeconds  �ö�ά����Чʱ�䣬����Ϊ��λ�� ��󲻳���2592000����30�죩�����ֶ���������Ĭ����Ч��Ϊ30�롣
	 * @param sceneId  ����ֵID����ʱ��ά��ʱΪ32λ��0����
	 * @return WeiXinTemporaryQRCode ����ʵ��
	 */
	WeiXinTemporaryQRCode createTemporaryQRCode(String accessToken,int expireSeconds,int sceneId);
	
	/**
	 * �������ô�������ά��
	 * sceneId ����ֵIDΪ  int ����
	 * @param accessToken �ӿڷ���ƾ֤ (CommonUtil.getToken)
	 * @param sceneId ����ֵID ���ö�ά��ʱ���ֵΪ100000��Ŀǰ����ֻ֧��1--100000��
	 * @return WeiXinPermanentQRCode ����ʵ��
	 * 
	 * @�����ύ POST �Ĳ��� �� {"action_name": "QR_LIMIT_SCENE", "action_info": {"scene": {"scene_id": 123}}}
	 */
	WeiXinPermanentQRCode createPermanentQRCode(String accessToken,int sceneId);
	
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
	WeiXinPermanentQRCode createPermanentQRCode(String accessToken,String sceneStr);
	
	/**
	 * ���� ticket ��ȡ��ά��
	 * 
	 * @param ticket ��ά��ticket
	 * @param savePath ����·��
	 * @return filePath �ļ���
	 */
	String getQRCode(String ticket,String savePath);
	
	/**
	 * ��ȡ�û�������Ϣ
	 * 
	 * @param accessToken �ӿڷ���ƾ֤  (CommonUtil.getToken)
	 * @param openId �û��ı�ʶ
	 * @return WeiXinUserInfo �����û���Ϣ����ʵ��
	 */
	WeiXinUserInfo getUserInfo(String accessToken,String openId);
	
	/**
	 * ��ȡ��ע�û��б�
	 * 
	 * @param accessToken �ӿ�ƾ֤
	 * @param nextOpenId ��һ����ȡ��OPENID������Ĭ�ϴ�ͷ��ʼ��ȡ
	 * @return ����WeiXinUserListʵ������
	 */
	WeiXinUserList getWeiXinUserList(String accessToken,String nextOpenId);
	
	/**
	 * ��ȡ�û�������Ϣ
	 * 
	 * @param accessToken �ӿ�ƾ֤
	 * @return ���� List<WeiXinGroups> �����б�
	 */
	List<WeiXinGroups> getWeiXinGroups(String accessToken);
	
	/**
	 * ��������
	 * @˵��  ��Ҫ POST ���� {"group":{"name":"groupName"}}
	 * 
	 * @param accessToken �ӿ�ƾ֤
	 * @param groupName ��������
	 * @return  WeiXinGroups ���ط���ʵ������
	 */
	WeiXinGroups createGroup(String accessToken,String groupName);
	
	/**
	 * �޸�ָ�� id �ķ���Ķ�Ӧ����
	 * @˵��  ��Ҫ POST ���� {"group":{"id":108,"name":"groupName"}}
	 * 
	 * @param accessToken �ӿ�ƾ֤
	 * @param groupId �����id
	 * @param groupName �޸ĺ�ķ�������
	 * @return ���� true ��ʾ�޸ĳɹ�;false ��ʾ�޸�ʧ��
	 */
	boolean updateGroup(String accessToken,int groupId,String groupName);
	
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
	boolean moveMemberGroup(String accessToken,String openId,int groupId);
	
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
	WeiXinMedia uploadTemporaryMedia(String accessToken, String type, String mediaFileUrl);
	
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
	String downLoadMedia(String accessToken, String mediaId, String savePath);
	
	/**
	 * ���Է���
	 * @return
	 */
	String getHello();
}
