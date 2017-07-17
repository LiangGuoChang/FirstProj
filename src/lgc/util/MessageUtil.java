package lgc.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.binary.Token.StartNode;
import com.thoughtworks.xstream.io.xml.XppDriver;

import lgc.bean.database.UserLocationEvent;
import lgc.bean.response.Article;
import lgc.bean.response.ImageMessage;
import lgc.bean.response.MusicMessage;
import lgc.bean.response.NewsMessage;
import lgc.bean.response.TextMessage;
import lgc.bean.response.VideoMessage;
import lgc.bean.response.VoiceMessage;

import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;

/**
 * ��Ϣ��������
 * @author lgc
 *
 * @date 2017��6��7�� ����2:37:06
 */
public class MessageUtil {

	// ������Ϣ���ͣ��ı�
	public static final String REQ_MESSAGE_TYPE_TEXT="text";
	// ������Ϣ���ͣ�ͼƬ
	public static final String REQ_MESSAGE_TYPE_IMAGE="image";
	// ������Ϣ���ͣ�����
	public static final String REQ_MESSAGE_TYPE_VOICE="voice";
	// ������Ϣ���ͣ���Ƶ
	public static final String REQ_MESSAGE_TYPE_VIDEO="video";
	// ������Ϣ���ͣ�С��Ƶ
	public static final String REQ_MESSAGE_TYPE_SHORTVIDEO="shortvideo";
	// ������Ϣ���ͣ�λ��
	public static final String REQ_MESSAGE_TYPE_LOCATION="location";
	// ������Ϣ���ͣ�����
	public static final String REQ_MESSAGE_TYPE_LINK="link";
	
	// ������Ϣ���ͣ��¼�
	public static final String REQ_MESSAGE_TYPE_EVENT="event";
	
	// �¼����ͣ�subscribe(����)
	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";
	// �¼����ͣ�unsubscribe(ȡ������)
	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";
	// �¼����ͣ�scan(�û��ѹ�עʱ��ɨ���������ά��)
	public static final String EVENT_TYPE_SCAN = "SCAN";
	// �¼����ͣ�LOCATION(�ϱ�����λ��)
	public static final String EVENT_TYPE_LOCATION = "LOCATION";
	// �¼����ͣ�CLICK(����˵���ȡ��Ϣ)
	public static final String EVENT_TYPE_CLICK = "CLICK";
	// �¼����ͣ�VIEW(����˵���ת����)
	public static final String EVENT_TYPE_VIEW = "VIEW";
	
	// ��Ӧ��Ϣ���ͣ��ı�
	public static final String RESP_MESSAGE_TYPE_TEXT = "text";
	// ��Ӧ��Ϣ���ͣ�ͼƬ
	public static final String RESP_MESSAGE_TYPE_IMAGE = "image";
	// ��Ӧ��Ϣ���ͣ�����
	public static final String RESP_MESSAGE_TYPE_VOICE = "voice";
	// ��Ӧ��Ϣ���ͣ���Ƶ
	public static final String RESP_MESSAGE_TYPE_VIDEO = "video";
	// ��Ӧ��Ϣ���ͣ�����
	public static final String RESP_MESSAGE_TYPE_MUSIC = "music";
	// ��Ӧ��Ϣ���ͣ�ͼ��
	public static final String RESP_MESSAGE_TYPE_NEWS = "news";
	
	/**
	 * �����û���������Ϣ(XML)
	 * @throws IOException 
	 */
	public static Map<String, String> parseXml(HttpServletRequest request) throws Exception{
		//�½�HashMap���󣬴�Ž���������
		Map<String, String> msgMap=new HashMap<String,String>();
		//��HttpServletRequest��ȡ������
		InputStream iStream=request.getInputStream();
		//��ȡ������
		SAXReader reader=new SAXReader();
		Document document=reader.read(iStream);
		//����xml���ڵ�
		Element root=document.getRootElement();
		//�������ڵ�������ӽڵ�
		List<Element> elements=root.elements();
		//�����ӽڵ㣬��ŵ�HashMap��
		System.out.println("�û����󣺣�");
		for (Element element : elements) {
			msgMap.put(element.getName(), element.getText());
			
			System.out.println(element.getName()+"==>"+element.getText());
		}
		//�ͷ���Դ
		iStream.close();
		iStream=null;
		
		return msgMap;
	}

//========================��Ӧ�û���Ϣ========================
	
	/**
	 * ��չxstreamʹ��֧��CDATA
	 * ���ڽ�����ת��ΪXML,��Ӧ�û�(������Ϣ���û�)
	 */
	private static XStream xStream=new XStream(new XppDriver(){
		public HierarchicalStreamWriter createWriter(Writer out){
			return new PrettyPrintWriter(out){
				
				// ������xml�ڵ��ת��������CDATA���
				boolean cdata=true;
				
				public void startNode(String name,Class clazz){
					super.startNode(name, clazz);
				}
				
				protected void writeText(com.thoughtworks.xstream.core.util.QuickWriter writer, String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					}else {
						writer.write(text);
					}
				};
			};
			
		}
	});
	
	/**
	 * �ı���Ϣ����ת��ΪXML
	 * @param textMessage
	 * @return xml
	 */
	public static String messageToXml(TextMessage textMessage){
		xStream.alias("xml", textMessage.getClass());
		return xStream.toXML(textMessage);
	}
	
	/**
	 * ͼƬ��Ϣ����ת��ΪXML
	 * @param imageMessage
	 * @return xml
	 */
	public static String messageToXml(ImageMessage imageMessage){
		xStream.alias("xml", imageMessage.getClass());
		return xStream.toXML(imageMessage);
	}
	
	/**
	 * ������Ϣ����ת��ΪXML
	 * @param voiceMessage
	 * @return xml
	 */
	public static String messageToXml(VoiceMessage voiceMessage){
		xStream.alias("xml", voiceMessage.getClass());
		return xStream.toXML(voiceMessage);
	}
	
	/**
	 * ��Ƶ��Ϣ����ת��ΪXML
	 * @param videoMessage
	 * @return xml
	 */
	public static String messageToXml(VideoMessage videoMessage){
		xStream.alias("xml", videoMessage.getClass());
		return xStream.toXML(videoMessage);
	}
	
	/**
	 * ������Ϣ����ת��ΪXML
	 * @param musicMessage
	 * @return xml
	 */
	public static String messageToXml(MusicMessage musicMessage){
		xStream.alias("xml", musicMessage.getClass());
		return xStream.toXML(musicMessage);
	}
	
	/**
	 * ͼ����Ϣ����ת��ΪXML
	 * @param newsMessage
	 * @return xml
	 */
	public static String messageToXml(NewsMessage newsMessage){
		xStream.alias("xml", newsMessage.getClass());
		xStream.alias("item", new Article().getClass());
		return xStream.toXML(newsMessage);
	}
	
	//======================�����û�����Ϣ�����ݿ�=====================
	
	/**
	 * �����û�λ����Ϣ�����ݿ�
	 * 
	 * @param location λ����Ϣ����
	 * @return insertIndex ���ز����λ��
	 */
	public static int insertLocationData(UserLocationEvent location){
		int insertIndex=-1;
		DataBaseUtil dataBaseUtil=new DataBaseUtil();
		String userName=location.getFromUserName();
		long createTime=location.getCreateTime();
		String latitude=location.getLatitude();
		String longitude=location.getLongitude();
		String precision=location.getPrecision();
		String address=location.getAddress();
		String insertSql="insert into user_location_event values"+"("+"'"+userName+"','"+createTime+"','"+latitude+"','"+longitude+"','"+precision+"','"+address+"'"+")";
		insertIndex=dataBaseUtil.cruNewData(insertSql);
		return insertIndex;
	}
	
	
}
