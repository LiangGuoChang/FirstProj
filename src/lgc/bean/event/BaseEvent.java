package lgc.bean.event;

/**
 * �¼�����   �û�---->���ں�
 * @author Administrator
 *
 */
public class BaseEvent {
	
	//������΢�ź�
	private String ToUserName;
	//���ͷ�΢�ź�(һ��OpenID ���磺oA1HcvwJj9KKuPC6fmRWm8h6Qv4I)
	private String FromUserName;
	//����ʱ��
	private long CreateTime;
	//��Ϣ����(event)
	private String MsgType;
	//�¼�����
    //subscribe(����)��unsubscribe(ȡ������)��SCAN(�û��ѹ�ע ʱɨ���ά��)��
	//LOCATION(�ϱ�λ��)��CLICK(����˵���ȡ��Ϣ)��VIEW(����˵���ת����)
	private String Event;
		
	public String getToUserName() {
		return ToUserName;
	}
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	public String getFromUserName() {
		return FromUserName;
	}
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	public long getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(long createTime) {
		CreateTime = createTime;
	}
	public String getMsgType() {
		return MsgType;
	}
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
	public String getEvent() {
		return Event;
	}
	public void setEvent(String event) {
		Event = event;
	}
	
	
	
}
