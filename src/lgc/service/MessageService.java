package lgc.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lgc.BaiduMap.BaiduMapUtil;
import lgc.BaiduMap.BaiduPoiPlace;
import lgc.bean.database.UserLocation;
import lgc.bean.database.UserLocationEvent;
import lgc.bean.pojo.Token;
import lgc.bean.pojo.WeiXinMedia;
import lgc.bean.pojo.WeiXinTemporaryQRCode;
import lgc.bean.response.Article;
import lgc.bean.response.Image;
import lgc.bean.response.ImageMessage;
import lgc.bean.response.NewsMessage;
import lgc.bean.response.TextMessage;
import lgc.util.AdvancedUtil;
import lgc.util.CommonUtil;
import lgc.util.DataBaseUtil;
import lgc.util.EmojiUtil;
import lgc.util.MessageUtil;
import lgc.weixin.WeiXinCommon;

/**
 * ��Ϣ���������
 * @author lgc
 *
 * @date 2017��6��7�� ����5:17:48
 */
public class MessageService {

	private static String access_token;
	
	static{
		//��ȡ�ӿ�ƾ֤
		Token token=CommonUtil.getToken(WeiXinCommon.appID, WeiXinCommon.appsecret);
		access_token=token.getAccess_token();
	}
	
	/**
	 * �����û�������,������Ϣ����
	 * 
	 * @param request �û�����
	 * @return String respXml �������ڻظ��û���xml��Ϣ
	 */
	public static String processRequest(HttpServletRequest request){
		//����xml��ʽ����Ϣ
		String respXml=null;
		//Ĭ�ϻظ�������
		String respContent="δ֪����Ϣ���ͣ�";
		
		try {
			//�����û���������Ϣ
			Map<String, String> reqMap= MessageUtil.parseXml(request);
			//��ȡ�����ڵ������
			String req_fromUserName=reqMap.get("FromUserName");//�û�΢�ź�
			String req_toUserName=reqMap.get("ToUserName");//������΢�ź�
			String req_msgType=reqMap.get("MsgType");//��Ϣ����
			String req_createTime=reqMap.get("CreateTime");//��Ϣ������ʱ��
			
			//�ظ���Ϣ
			String resp_fromUserName=req_toUserName;//������΢�ź�
			String resp_toUserName=req_fromUserName;//�û�΢�ź�
			
			//����һ�����ڻظ����ı���Ϣ
			TextMessage textMessage=new TextMessage();
			textMessage.setToUserName(resp_toUserName);
			textMessage.setFromUserName(resp_fromUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			
			//�ж��û��������Ϣ����
			switch (req_msgType) {
			case MessageUtil.REQ_MESSAGE_TYPE_TEXT://�ı���Ϣ
				//TODO
				String req_content=reqMap.get("Content").trim();//��ȡ�ı���Ϣ
				if (req_content.equals("����")) {
					//�ظ��ı���Ϣ����ʾ�û�����λ����Ϣ
					respContent=getUsage();
					textMessage.setContent(respContent);
					//���ı���Ϣ����ת��ΪXML��ʽ
					respXml=MessageUtil.messageToXml(textMessage);
				}else if (req_content.startsWith("����")) {
					String keyWord=req_content.replaceAll("����", "").trim();
					//��ȡ�û����һ�η��͵ĵ�ַ
					DataBaseUtil dataBaseUtil=new DataBaseUtil();
					UserLocation userLocation=dataBaseUtil.getLastLoaction(req_fromUserName);
					//δ��ȡ��,�ظ��ı���Ϣ����ʾ�û��ٴη���λ��
					if (userLocation==null) {
						respContent=getUsage();
						textMessage.setContent(respContent);
						//���ı���Ϣ����ת��ΪXML��ʽ
						respXml=MessageUtil.messageToXml(textMessage);
					}else {
						//��ȡ��������ת����ٶ�����ϵȥpoi�ܱ�
						List<BaiduPoiPlace> placeList=BaiduMapUtil.searchPoiPlace(keyWord, 
								userLocation.getBd09Lat(), 
								userLocation.getBd09Lng(), 
								WeiXinCommon.baiduAk);
						if (placeList==null || placeList.size()==0) {
							respContent=String.format("/�ѹ��������͵�λ�ø���δ��������%s����Ϣ��\n\n �����ٷ���һ��λ�ã�/:rose/:rose/:rose", keyWord);
						}else {
							//��ȡͼ���б�
							List<Article> articles=BaiduMapUtil.makeArticleList(placeList, userLocation.getBd09Lng().trim(), userLocation.getBd09Lat().trim());
							//����ͼ����Ϣ
							NewsMessage newsMessage=new NewsMessage();
							newsMessage.setFromUserName(resp_fromUserName);
							newsMessage.setToUserName(resp_toUserName);
							newsMessage.setCreateTime(new Date().getTime());
							newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
							newsMessage.setArticleCount(articles.size());
							newsMessage.setArticles(articles);
							//��ͼ����Ϣ����ת��ΪXML��ʽ
							respXml=MessageUtil.messageToXml(newsMessage);
						}
						
					}
				}else {
					//��������������,��ʹ���Զ����칦��
					respContent=ChatService.chat(req_fromUserName, req_createTime, req_content);
					textMessage.setContent(respContent);
					//���ı���Ϣ����ת��ΪXML��ʽ
					respXml=MessageUtil.messageToXml(textMessage);
				}
				break;
				
			case MessageUtil.REQ_MESSAGE_TYPE_IMAGE://ͼƬ��Ϣ
				//TODO
				respContent="�����͵���ͼƬ��Ϣ";
				//�����ı���Ϣ����
				textMessage.setContent(respContent);
				//���ı���Ϣ����ת��ΪXML��ʽ
				respXml=MessageUtil.messageToXml(textMessage);
				break;
				
			case MessageUtil.REQ_MESSAGE_TYPE_VOICE://������Ϣ
				//TODO
				//������MediaId
				String mediaId=reqMap.get("MediaId");
				//��΢�ŷ��������ظ����������ط�������
				AdvancedUtil advancedUtil=new AdvancedUtil();
				String downLoadFile=advancedUtil.getAdvancedMethod().downLoadMedia(access_token, mediaId, WeiXinCommon.downLoadFilePathComm+"/voice/");
				System.out.println("MessageService-downLoadFile::"+downLoadFile);
				
				respContent="�����͵���������Ϣ";
				//�����ı���Ϣ����
				textMessage.setContent(respContent);
				//���ı���Ϣ����ת��ΪXML��ʽ
				respXml=MessageUtil.messageToXml(textMessage);
				break;
				
			case MessageUtil.REQ_MESSAGE_TYPE_VIDEO://��Ƶ��Ϣ
				//TODO
				respContent="�����͵�����Ƶ��Ϣ";
				//�����ı���Ϣ����
				textMessage.setContent(respContent);
				//���ı���Ϣ����ת��ΪXML��ʽ
				respXml=MessageUtil.messageToXml(textMessage);
				break;
				
			case MessageUtil.REQ_MESSAGE_TYPE_SHORTVIDEO://С��Ƶ��Ϣ
				//TODO
				respContent="�����͵���С��Ƶ��Ϣ";
				//�����ı���Ϣ����
				textMessage.setContent(respContent);
				//���ı���Ϣ����ת��ΪXML��ʽ
				respXml=MessageUtil.messageToXml(textMessage);
				break;
				
			case MessageUtil.REQ_MESSAGE_TYPE_LOCATION://λ����Ϣ
				//TODO
				//��ȡ�û��ľ�γ��
				String lng=reqMap.get("Location_X").trim();
				String lat=reqMap.get("Location_Y").trim();
				System.out.println("Location_X::"+lng+"\n"+"Location_Y::"+lat);
				//ת���ɰٶ�����ϵ
				String bd09Lng=null;
				String bd09Lat=null;
				UserLocation userLocation=BaiduMapUtil.convertLatlng(lng, lat);
				if (userLocation != null) {
					bd09Lng=userLocation.getBd09Lng();
					bd09Lat=userLocation.getBd09Lat();
					System.out.println("bd09Lng::"+bd09Lng+"\n"+"bd09Lat::"+bd09Lat);
				}
				//�����û�λ����Ϣ�����ݿ�
				DataBaseUtil baseUtil=new DataBaseUtil();
				baseUtil.saveUserLocation(req_fromUserName, lng, lat, bd09Lng, bd09Lat);
				
				//�ظ��ı���Ϣ����ʾ�û���ȡλ�óɹ�
				StringBuffer buffer = new StringBuffer();
				buffer.append("[���]").append("�ɹ���������λ�ã�").append("\n\n");
				buffer.append("���������������ؼ��ʻ�ȡ�ܱ���Ϣ�ˣ����磺").append("\n");
				buffer.append("        ����ATM").append("\n");
				buffer.append("        ����KTV").append("\n");
				buffer.append("        ��������").append("\n");
				buffer.append("�����ԡ������������ֿ�ͷ��");
				respContent = buffer.toString();
				//�����ı���Ϣ����
				textMessage.setContent(respContent);
				//���ı���Ϣ����ת��ΪXML��ʽ
				respXml=MessageUtil.messageToXml(textMessage);
				break;
				
			case MessageUtil.REQ_MESSAGE_TYPE_LINK://������Ϣ
				//TODO
				respContent="�����͵���������Ϣ";
				//�����ı���Ϣ����
				textMessage.setContent(respContent);
				//���ı���Ϣ����ת��ΪXML��ʽ
				respXml=MessageUtil.messageToXml(textMessage);
				break;
				
			case MessageUtil.REQ_MESSAGE_TYPE_EVENT://�¼���Ϣ
				//��ȡ�û�������¼�����
				String eventType=reqMap.get("Event");
				
				switch (eventType) {
				case MessageUtil.EVENT_TYPE_SUBSCRIBE://��ע
					//TODO
					//�����ı���Ϣ����
					textMessage.setContent(respContent);
					respContent=getSubscribeMsg();
					//���ı���Ϣ����ת��ΪXML��ʽ
					respXml=MessageUtil.messageToXml(textMessage);
					break;
					
				case MessageUtil.EVENT_TYPE_UNSUBSCRIBE://ȡ����ע
					// TODO ȡ�����ĺ��û��������յ������˺ŷ��͵���Ϣ����˲���Ҫ�ظ�
					break;
					
				case MessageUtil.EVENT_TYPE_SCAN://ɨ���������ά��
					//TODO
					break;
					
				case MessageUtil.EVENT_TYPE_LOCATION://�ϱ�����λ��
					//�ظ��ı���Ϣ���û�
					textMessage.setContent("����λ���Ѿ����£�");
					respXml=MessageUtil.messageToXml(textMessage);
					//�����û�λ����Ϣ�����ݿ�
					UserLocationEvent location=new UserLocationEvent();
					String req_latitude=reqMap.get("Latitude");
					String req_longitude=reqMap.get("Longitude");
					String req_percision=reqMap.get("Precision");
					//TODO �˴���Ҫ����γ��ת��Ϊ����λ��
					String address="��ת��";
					location.setFromUserName(req_fromUserName);
					location.setCreateTime(Long.valueOf(req_createTime));
					location.setLatitude(req_latitude);
					location.setLongitude(req_longitude);
					location.setPrecision(req_percision);
					location.setAddress(address);
					//�����û�λ��
					int insertIndex=MessageUtil.insertLocationData(location);
					System.out.println("�����û�λ����Ϣ-insertIndex::"+insertIndex);
					break;
					
				case MessageUtil.EVENT_TYPE_CLICK://����˵���ȡ��Ϣ
					//�Զ���˵��е�EventKey����(�ڴ����˵����Զ����)
					String eventKey=reqMap.get("EventKey");
					if (eventKey.equals("open-open")) {
						//����ͼ����Ϣ��Article����
						Article article=new Article();
						article.setTitle("��ȿ�Դ");
						article.setDescription("��Դ�й�����������2008��8�£���Ŀǰ�й����Ŀ�Դ����������\n\n��Դ�й���Ŀ����Ϊ�й���IT������Ա�ṩһ��ȫ��ġ���ݸ��µ�����������Դ����Լ�������Դ�����ƽ̨��\n\n�������ϵĸĽ�,Ŀǰ��Դ�й������Ѿ��γ����ɿ�Դ����⡢���������Ѷ���������Ͳ��͵ȼ���Ƶ�����ݡ�");
						article.setPicUrl("https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=2516756084,25048807&fm=58&s=69801C715CA4D6014A8D91C60300F0B1&bpow=121&bpoh=75");
						article.setUrl("http://www.open-open.com/");
						List<Article> articles =new ArrayList<Article>();
						articles.add(article);
						//����ͼ����Ϣ
						NewsMessage newsMessage=new NewsMessage();
						newsMessage.setToUserName(resp_toUserName);
						newsMessage.setFromUserName(resp_fromUserName);
						newsMessage.setCreateTime(new Date().getTime());
						newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
						newsMessage.setArticleCount(articles.size());
						newsMessage.setArticles(articles);
						//��ͼ�Ķ���ת��ΪXML����
						respXml=MessageUtil.messageToXml(newsMessage);
						
					}else if (eventKey.equals("android-dev-tool")) {
						//����ͼ����Ϣ��Article����
						Article article=new Article();
						article.setTitle("AndroidDevTools");
						article.setDescription("�ռ�����Android���������Android SDK���������õ��Ĺ��ߡ�Android�����̡̳�Android��ƹ淶����ѵ�����زĵȡ�\n��ӭ����Ƽ��Լ���Android�����������õĺ��õĹ��ߡ�ѧϰ�����̡̳��õ�����زġ�\n�������ñ�վ�������ã�����Ե���ײ��ķ���ť���ѱ�վ�����罻���������С���͸������֪����");
						article.setPicUrl("http://www.androiddevtools.cn/static/image/androiddevtools_logo.svg");
						article.setUrl("http://www.androiddevtools.cn/");
						List<Article> articles =new ArrayList<Article>();
						articles.add(article);
						//����ͼ����Ϣ
						NewsMessage newsMessage=new NewsMessage();
						newsMessage.setToUserName(resp_toUserName);
						newsMessage.setFromUserName(resp_fromUserName);
						newsMessage.setCreateTime(new Date().getTime());
						newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
						newsMessage.setArticleCount(articles.size());
						newsMessage.setArticles(articles);
						//��ͼ�Ķ���ת��ΪXML����
						respXml=MessageUtil.messageToXml(newsMessage);
					}else if ("qrcode".equals(eventKey)) {
						//��ȡ��ʱ���ζ�ά��
						AdvancedUtil advancedUtil2=new AdvancedUtil();
						WeiXinTemporaryQRCode qrCode=advancedUtil2.getAdvancedMethod()
								.createTemporaryQRCode(access_token, 60, 170721);
						
						System.out.println("qrCode.getUrl()::"+qrCode.getUrl());
						System.out.println("qrCode.getTicket()::"+qrCode.getTicket());
						System.out.println("qrCode.getExpireSeconds()::"+qrCode.getExpireSeconds());
						
						String savePath=advancedUtil2.getAdvancedMethod().getQRCode(qrCode.getTicket(), WeiXinCommon.downloadQrCode);
						
						System.out.println("savePath::"+savePath);
						
						
						WeiXinMedia qrCodeMedia=advancedUtil2.getAdvancedMethod().uploadTemporaryMedia(access_token, "image", savePath);
						
						System.out.println("qrCodeMedia.getMediaId()::"+qrCodeMedia.getMediaId());
						
						Image qrCodeImg=new Image();
						qrCodeImg.setMediaId(qrCodeMedia.getMediaId());
						ImageMessage qrCodeMessage=new ImageMessage();
						qrCodeMessage.setFromUserName(resp_fromUserName);
						qrCodeMessage.setToUserName(resp_toUserName);;
						qrCodeMessage.setCreateTime(new Date().getTime());
						qrCodeMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_IMAGE);
						qrCodeMessage.setImage(qrCodeImg);
						//��ͼƬ����ת��ΪXML����
						respXml=MessageUtil.messageToXml(qrCodeMessage);
					}
					break;
					
				case MessageUtil.EVENT_TYPE_VIEW://����˵���תҳ��
					//�û�����˵� view �����ظ�
					respXml="success";
					break;
				}
				break;
			}
			
//			//�����ı���Ϣ����
//			textMessage.setContent(respContent);
//			//���ı���Ϣ����ת��ΪXML��ʽ
//			respXml=MessageUtil.messageToXml(textMessage);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return respXml;
	}
	
	/**
	 * ��ע����ʾ
	 * @return ��ע��
	 */
	private static String getSubscribeMsg(){
		StringBuffer buffer=new StringBuffer();
		buffer.append("��ӭ���Ĺ�ע��/:rose/:rose/:rose");
		buffer.append("\n\n");
		buffer.append("�ظ�����������ʼ���������������ܰɣ�");
		return buffer.toString();
	}
	
	/**
	 * �����ܱ�˵��
	 * @return ˵��
	 */
	private static String getUsage(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("�ܱ�����ʹ��˵��").append("\n\n");
		buffer.append("1�����͵���λ��").append("\n");
		buffer.append("������ڵײ��ġ�+����ť��ѡ��λ�á����㡰���͡�").append("\n\n");
		buffer.append("2��ָ���ؼ�������").append("\n");
		buffer.append("��ʽ������+�ؼ���\n���磺����ATM������KTV�����������ȵ�");
		return buffer.toString();
	}
}
